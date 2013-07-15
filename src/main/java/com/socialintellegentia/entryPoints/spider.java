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

//            runner.getNewsFromRSSserver("http://news.bbc.co.uk/2/hi/help/rss/");
//            runner.getNewsFromRSSserver("http://www.foxnews.com/about/rss/");
            runner.getNewsFromRSSserver("http://rss.elmundo.es/rss/");

            String workingFolder = runner.getWorkingFolder();

            ProcessRSS process = new ProcessRSS();
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
