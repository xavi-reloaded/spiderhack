package com.androidxtrem.spider;

import com.androidxtrem.commonsHelpers.FileHelper;
import com.androidxtrem.spider.core.*;
import com.androidxtrem.spider.agent.RobotWasshotFeed;
import com.androidxtrem.spider.core.proxy.AnonymousProxyManager;
import com.rometools.rome.io.FeedException;
import com.wasshot.feederwerk.feed.FeedParser;
import com.wasshot.feederwerk.feed.FetchedFeed;
import com.wasshot.feederwerk.model.FeedEntry;
import org.joda.time.DateTime;

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
public class SpiderWasshotFeed extends RobotExerciser implements SpiderConfig {

    private AnonymousProxyManager proxyManager = null;
    private FeedParser parser;
    int maxProxyThreads = 3;

    public SpiderWasshotFeed() throws IOException
    {
        proxyManager = new AnonymousProxyManager(CACHE_FOLDER, maxProxyThreads);
        parser = new FeedParser();
    }

    public void stop()
    {
        proxyManager.stop();
    }


    public void getNewsFromRSSserver(String rss_server) throws IOException, InterruptedException, FeedException {
        log.debug(LOG_PREFIX + "Begint Treatment of " + rss_server );

        String workingFolder = getWorkingFolder();
        int maxSpiderThreads = 10;
        int milisecondsBetweenQueries = 3000;

        FileHelper.createFolder(workingFolder);
        FileHelper.createFolder(CACHE_FOLDER);

        List<Robot> spiderList = new ArrayList<Robot>();
        for(int i = 0; i < maxSpiderThreads; i++) {
            spiderList.add(new RobotWasshotFeed(workingFolder, CACHE_FOLDER));
        }

        HttpDownloader downloader = new HttpDownloader(CACHE_FOLDER, 10 * 24 * 60, 10000, 60000);

        StringBuffer html = downloader.getRequest(rss_server);

        if (html==null||"".equals(html))
        {
            log.warn(LOG_PREFIX + " can not get rss's from " + rss_server);
            return;
        }

        FetchedFeed frontEndItem = parser.parse(rss_server, String.valueOf(html).getBytes());
        log.debug(LOG_PREFIX + "got [" + frontEndItem.getEntries().size() + "] feeds from " + rss_server);

        for(FeedEntry feed : frontEndItem.getEntries())
        {
            addNewSeed(new Seed(feed.getUrl(), 10));
        }

        DateTime dtBegin = new DateTime();
        log.debug("[Agent-Wasshot] --> Request " + rss_server + " in: " + fmt.print(dtBegin) + "\n");


        int avoidInfiniteLoop=0;
        Thread.sleep(5000);

        while(getSeedCount() > 0 && avoidInfiniteLoop>1000) {
            Thread.sleep(5000);
            avoidInfiniteLoop++;
        }

        startAgentsManager(milisecondsBetweenQueries, proxyManager, spiderList);
//        stopAgentsManager();
//        proxyManager.stop();
    }

    public String getWorkingFolder() {
        String jobName = "wasshotfeeds";
        return WORKING_FOLDER + "/spider/" + jobName;
    }

}
