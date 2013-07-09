package com.apiumtech.spider.si;

import com.apiumtech.spider.*;
import com.apiumtech.spider.agent.Agent;
import com.apiumtech.spider.agent.AgentsManager;
import com.socialintellegentia.commonhelpers.rss.Feed;
import com.socialintellegentia.commonhelpers.rss.FrontEndItem;
import com.socialintellegentia.commonhelpers.rss.RSSFrontEndHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: fjhidalgo
 * Date: 5/29/13
 * Time: 6:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class SpiderSiRSS extends AgentsManager implements SpiderConfig {

    private AnonymousProxyManager proxyManager = null;
    int maxProxyThreads = 1;

    public SpiderSiRSS() throws IOException
    {
        proxyManager = new AnonymousProxyManager(cacheFolder, maxProxyThreads);
    }

    public void stop()
    {
        proxyManager.stop();
    }


    void getNewsFromRSSserver(String rss_server) throws IOException, InterruptedException
    {
        /////////////////////////////////////////////////////////
        // Parameters
        /////////////////////////////////////////////////////////
        String jobName = "SpiderSiRSS";
        String workingFolder = WORKING_FOLDER + "/spider/" + jobName;
        int maxSpiderThreads = 20;
        int milisecondsBetweenQueries = 1000;

        /////////////////////////////////////////////////////////
        // Working Folders
        /////////////////////////////////////////////////////////
        FileHelper.createFolder(workingFolder);
        FileHelper.createFolder(cacheFolder);

        /////////////////////////////////////////////////////////
        // Create Spiders
        /////////////////////////////////////////////////////////
        List<Agent> spiderList = new ArrayList<Agent>();
        for(int i = 0; i < maxSpiderThreads; i++) {
            spiderList.add(new AgentSiRSS(workingFolder, cacheFolder, Long.MAX_VALUE));
        }

        /////////////////////////////////////////////////////////
        // Create Downloader
        /////////////////////////////////////////////////////////
        HttpDownloader downloader = new HttpDownloader(cacheFolder, 10 * 24 * 60, 10000, 60000);

        /////////////////////////////////////////////////////////
        // Add Seeds
        /////////////////////////////////////////////////////////
        StringBuffer html = downloader.getRequest(rss_server);
        RSSFrontEndHelper rssFrontEndHelper = new RSSFrontEndHelper();

        if (html!=null)
        {
            System.out.println("creating front end...");
            FrontEndItem frontEndItem = rssFrontEndHelper.getFrontEndItemFromHtml(html.toString());
            System.out.println("feeds added");
            int i = 0;
            for(Feed feed : frontEndItem.getFeedList())
            {
                System.out.println("adding seeds... "+i+"/"+frontEndItem.getFeedList().size());
                addNewSeed(new Seed(feed.getLink(),100));
                System.out.println("added "+i+"/"+frontEndItem.getFeedList().size());
                i++;
            }

        } else
        {
            System.out.println("error :"+rss_server);
        }

        /////////////////////////////////////////////////////////
        // Start Agents
        /////////////////////////////////////////////////////////
        startAgentsManager(milisecondsBetweenQueries, proxyManager, spiderList);
        while(getSeedCount() > 0) Thread.sleep(50);
        stopAgentsManager();
        proxyManager.stop();
    }




}
