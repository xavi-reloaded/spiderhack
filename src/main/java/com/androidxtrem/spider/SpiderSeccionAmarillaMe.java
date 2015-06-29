package com.androidxtrem.spider;

import com.androidxtrem.commonsHelpers.FileHelper;
import com.androidxtrem.spider.core.*;
import com.androidxtrem.spider.agent.RobotSeccionAmarillaMe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SpiderSeccionAmarillaMe extends RobotExerciser implements SpiderConfig
{
    String cacheFolder = WORKING_FOLDER + "/spidercache_seccionamarillame";
    private AnonymousProxyManager proxyManager = null;
    int maxProxyThreads = 5;

    public SpiderSeccionAmarillaMe() throws IOException
    {
        //proxyManager = new AnonymousProxyManager(cachefolder, maxProxyThreads);
    }

    public void stop()
    {
        //proxyManager.stop();
    }

    private void processPage(String language, String pageContents, int maxBooks)
    {
    }

    void search(String group) throws IOException, InterruptedException
    {
        /////////////////////////////////////////////////////////
        // Parameters
        /////////////////////////////////////////////////////////
        String jobName = "seccionamarillame_" + group.replace("[\\-]", "_");
//        String workingfolder = "/home/xavi/.gvfs/processes/" + jobName;
        String workingFolder = WORKING_FOLDER + "/spider_me/" + jobName;
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
        List<Robot> spiderList = new ArrayList<Robot>();
        for(int i = 0; i < maxSpiderThreads; i++) {
            spiderList.add(new RobotSeccionAmarillaMe(workingFolder, cacheFolder, Long.MAX_VALUE));
        }

        /////////////////////////////////////////////////////////
        // Create Downloader
        /////////////////////////////////////////////////////////
        HttpDownloader downloader = new HttpDownloader(cacheFolder, 10 * 24 * 60, 10000, 60000);


        /////////////////////////////////////////////////////////
        // Add Seeds
        /////////////////////////////////////////////////////////


        String requestUrl = "http://www.seccionamarilla.com.mx/resultados/"+ group + "/distrito-federal/";
        //String html = downloader.getRequest(requestUrl);

        String html = downloader.getRequest(requestUrl).toString();

        if (html!=null) {
            int numOfItems = Integer.parseInt("0" + RegExpHelper.getFirstMatch(html, "</strong></h1>&nbsp;\\(([0-9]+)\\)</div>", 1));
            for(int i = 1; i <= (numOfItems / 15)/*items per page*/ + 1; i++)
            {
                addNewSeed(new Seed("http://www.paginasamarillas.es/search/" + group + "/all-ma/all-pr/all-is/all-ci/all-ba/all-pu/all-nc/" + i, 100));
            }
        } else {
            System.out.println("error :"+requestUrl);
        }


//            for(int i = 1; i <= 400 ; i++)
//        {
//            addNewSeed(new Seed(requestUrl + i, 100));
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
