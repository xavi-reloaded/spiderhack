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
        System.out.println("Creating spider list...");

        List<Agent> spiderList = new ArrayList<Agent>();
        for(int i = 0; i < maxSpiderThreads; i++) {
            spiderList.add(new AgentSiRSS(workingFolder, cacheFolder, Long.MAX_VALUE));
        }
        System.out.println("Spider list created");
        /////////////////////////////////////////////////////////
        // Create Downloader
        /////////////////////////////////////////////////////////
        HttpDownloader downloader = new HttpDownloader(cacheFolder, 10 * 24 * 60, 10000, 60000);

        /////////////////////////////////////////////////////////
        // Add Seeds
        /////////////////////////////////////////////////////////
        String html = downloader.getRequest(rss_server);
        System.out.println("HTML: \n[\n"+html.subSequence(0,50)+"\n"+html.subSequence(html.length()-50, html.length())+"\n]\n");

        RSSFrontEndHelper rssFrontEndHelper = new RSSFrontEndHelper();
        if (html!=null)
        {
            FrontEndItem frontEndItem = rssFrontEndHelper.getFrontEndItemFromHtml(html);
            int i = 0;
            for(Feed feed : frontEndItem.getFeedList())
            {
                System.out.println("Adding seeds..."+i+"/"+frontEndItem.getFeedList().size());
                addNewSeed(new Seed(feed.getLink(),100));
                System.out.println("Added: "+i+"/"+frontEndItem.getFeedList().size());
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
        //proxyManager.stop();
    }




}
