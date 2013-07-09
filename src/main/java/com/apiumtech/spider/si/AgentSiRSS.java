package com.apiumtech.spider.si;

import com.apiumtech.spider.agent.Agent;
import com.socialintellegentia.commonhelpers.rss.Feed;
import com.socialintellegentia.commonhelpers.rss.FeedMessage;
import com.socialintellegentia.commonhelpers.rss.RSSFeedParser;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: fjhidalgo
 * Date: 5/29/13
 * Time: 6:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class AgentSiRSS extends Agent
{
    private RSSFeedParser parser;
    private Feed feed;
    private FeedMessage feedMessage;


    public AgentSiRSS(String workingFolder, String cacheFolder, long minutesInCache) throws IOException, InterruptedException {
        super(workingFolder, cacheFolder, minutesInCache);
        parser = new RSSFeedParser();
        feed = new Feed();
    }

    @Override

    public void run()
    {
        try
        {
            clearNewLinks();

            String url = getUrl();
            String rawnews = getRequest(url);

            if(rawnews == null)
            {
                System.out.println("Error requesting \"" + url + "\" from \"" + getProxy() + "\"");
                setProxy(null);
            }
            else
            {
//                String name = RegExpHelper.getFirstMatch(url, "/([^\"/]+)\\.txt", 1);
                String name = url.replaceAll("[\\\\/:\\*\"\\?<>\\|]", "");

//                feed = parser.readFeed(rawnews);

                saveResult(rawnews, name);

//
//                for (FeedMessage feedMessage : feed.getMessages() ) {
//                    System.out.println(feedMessage);
//                }

                System.out.println(feed.toString());
            }
        }
        catch(Exception e)
        {
            System.out.println("getRequest error: class(" + e.getClass().toString() + "), message(" + e.getMessage() + ")");
            e.printStackTrace();
        }
    }

}
