package com.androidxtrem.spider.si;

import com.androidxtrem.commonsHelpers.FileHelper;
import com.androidxtrem.spider.AnonymousProxyManager;
import com.androidxtrem.spider.HttpDownloader;
import com.androidxtrem.spider.Seed;
import com.androidxtrem.spider.SpiderConfig;
import com.androidxtrem.spider.agent.Agent;
import com.androidxtrem.spider.agent.AgentsManager;
import com.socialintellegentia.commonhelpers.hibernate.SpiderPersistence;
import com.socialintellegentia.commonhelpers.logger.LoggerHelper;
import com.socialintellegentia.commonhelpers.rss.Feed;
import com.socialintellegentia.commonhelpers.rss.FrontEndItem;
import com.socialintellegentia.commonhelpers.rss.RSSFrontEndHelper;
import com.socialintellegentia.commonhelpers.rss.RSSHelper;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;

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
        proxyManager = new AnonymousProxyManager(CACHE_FOLDER, maxProxyThreads);
    }

    public void stop()
    {
        proxyManager.stop();
    }


    public void getNewsFromRSSserver(String rss_server) throws IOException, InterruptedException
    {
        log.debug(LOG_PREFIX + "Begint Treatment of " + rss_server );
        /////////////////////////////////////////////////////////
        // Parameters
        /////////////////////////////////////////////////////////
        String workingFolder = getWorkingFolder();
        int maxSpiderThreads = 20;
        int milisecondsBetweenQueries = 1000;

        /////////////////////////////////////////////////////////
        // Working Folders
        /////////////////////////////////////////////////////////
        FileHelper.createFolder(workingFolder);
        FileHelper.createFolder(CACHE_FOLDER);

        /////////////////////////////////////////////////////////
        // Create Spiders
        /////////////////////////////////////////////////////////
        List<Agent> spiderList = new ArrayList<Agent>();
        for(int i = 0; i < maxSpiderThreads; i++) {
            spiderList.add(new AgentSiRSS(workingFolder, CACHE_FOLDER, Long.MAX_VALUE));
        }

        /////////////////////////////////////////////////////////
        // Create Downloader
        /////////////////////////////////////////////////////////
        HttpDownloader downloader = new HttpDownloader(CACHE_FOLDER, 10 * 24 * 60, 10000, 60000);

        /////////////////////////////////////////////////////////
        // Add Seeds
        /////////////////////////////////////////////////////////
        StringBuffer html = downloader.getRequest(rss_server);

        if (html==null)
        {
            log.warn(LOG_PREFIX + " can not get rss's from " + rss_server);
            SpiderPersistence pers = new SpiderPersistence();
            pers.saveUrlToBlackList(rss_server);
            return;
        }

        if (RSSHelper.isXMLRSS(html.toString()))
        {
            addNewSeed(new Seed(rss_server, 100));
        }
        else
        {
            RSSFrontEndHelper rssFrontEndHelper = new RSSFrontEndHelper(rss_server);
            DateTime dtBegin = new DateTime();
            log.debug(LOG_PREFIX + "Request " + rss_server + " in: " + fmt.print(dtBegin) + "\n");

            FrontEndItem frontEndItem = rssFrontEndHelper.getFrontEndItemFromHtml(html);
            log.debug(LOG_PREFIX + "got [" + frontEndItem.getFeedList().size() + "] feeds from " + rss_server);
            StringBuilder builder = new StringBuilder("process feeds from" + rss_server +"\n");
            for(Feed feed : frontEndItem.getFeedList())
            {
                addNewSeed(new Seed(feed.getLink(), 100));
                builder.append("Added feed " + feed.toString() + "\n");
            }
            log.debug(LOG_PREFIX + builder.toString());

            String tag = "[socialintellegentia-spider] --> Request " + rss_server ;
            LoggerHelper.writeTimmingToLog(dtBegin, tag);

        }



        /////////////////////////////////////////////////////////
        // Start Agents
        /////////////////////////////////////////////////////////
        DateTime dtBegin = new DateTime();
        log.debug("[AgentSiRSS] --> Request " + rss_server + " in: " + fmt.print(dtBegin) + "\n");

        startAgentsManager(milisecondsBetweenQueries, proxyManager, spiderList);
        while(getSeedCount() > 0) Thread.sleep(50);
        stopAgentsManager();
        proxyManager.stop();

        String tag = "[AgentSiRSS] --> Request " + rss_server ;
        LoggerHelper.writeTimmingToLog(dtBegin, tag);
    }

    public String getWorkingFolder() {
        String jobName = "SpiderSiRSS";
        return WORKING_FOLDER + "/spider/" + jobName;
    }


}
