package com.apiumtech.spider.si;

import com.apiumtech.spider.RegExpHelper;
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


    public AgentSiRSS(String workingFolder, String cacheFolder, long minutesInCache) throws IOException, InterruptedException {
        super(workingFolder, cacheFolder, minutesInCache);
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
                String name = RegExpHelper.getFirstMatch(url, "/([^\"/]+)\\.txt\\.utf8", 1);
//                saveResult(rawnews, name);
                RSSFeedParser parser = new RSSFeedParser();
                Feed feed = parser.readFeed(rawnews);
                System.out.println(feed);
                for (FeedMessage message : feed.getMessages()) {
                    System.out.println(message);

                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

}
