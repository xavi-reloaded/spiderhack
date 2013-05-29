package com.apiumtech.spider.si;

import com.apiumtech.spider.*;
import com.apiumtech.spider.agent.Agent;
import com.apiumtech.spider.agent.AgentsManager;

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
    int maxProxyThreads = 5;

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
        String requestUrl = rss_server;
        String html = downloader.getRequest(requestUrl);

        addNewSeed(new Seed(rss_server+"/foxnews/latest", 100));

        //TODO: get rss feeds(seeds)

//        if (html!=null) {
//            int numOfItems = Integer.parseInt("0" + RegExpHelper.getFirstMatch(html, "</strong></h1>&nbsp;\\(([0-9]+)\\)</div>", 1));
//            for(int i = 1; i <= (numOfItems / 15)/*items per page*/ + 1; i++)
//            {
//                addNewSeed(new Seed("http://www.paginasamarillas.es/search/" + group + "/all-ma/all-pr/all-is/all-ci/all-ba/all-pu/all-nc/" + i, 100));
//            }
//        } else {
//            System.out.println("error :"+requestUrl);
//        }

        /////////////////////////////////////////////////////////
        // Start Agents
        /////////////////////////////////////////////////////////
        startAgentsManager(milisecondsBetweenQueries, proxyManager, spiderList);
        while(getSeedCount() > 0) Thread.sleep(50);
        stopAgentsManager();
        //proxyManager.stop();
    }




}
