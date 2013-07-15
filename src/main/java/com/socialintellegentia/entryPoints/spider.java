package com.socialintellegentia.entryPoints;

import com.apiumtech.spider.si.SpiderSiRSS;
import com.socialintellegentia.commonhelpers.rss.Feed;
import com.socialintellegentia.processes.ProcessRSS;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jonny
 * Date: 12/07/13
 * Time: 17:33
 * To change this template use File | Settings | File Templates.
 */
public class spider {



    public static void main(String[] args)
    {
        try
        {
            SpiderSiRSS runner = new SpiderSiRSS();
            runner.getNewsFromRSSserver("http://www.rtve.es/rss/");
            runner.stop();

//            runner.getNewsFromRSSserver("http://www.foxnews.com/about/rss/");
//            runner.stop();
//
//            runner.getNewsFromRSSserver("http://rss.elmundo.es/rss/");
//            runner.stop();




            String workingFolder = runner.getWorkingFolder();

            boolean keepCacheFiles = false;
            ProcessRSS process = new ProcessRSS(keepCacheFiles);
//            String workingFolder = "sd_spider/spider/SpiderSiRSS";
            List<Feed> feeds = process.getFeedsFromSeedsByPath(workingFolder);

            for (Feed feed : feeds) {
                process.loadFeedInServer(feed);
            }



        }
        catch (Exception e){
            System.out.println("Error when running spider: " + e.getMessage());

        }
    }



}
