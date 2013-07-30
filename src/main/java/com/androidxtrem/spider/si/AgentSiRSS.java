package com.androidxtrem.spider.si;

import com.androidxtrem.spider.agent.Agent;
import com.socialintellegentia.commonhelpers.hibernate.SpiderPersistence;
import com.socialintellegentia.commonhelpers.rss.RSSHelper;

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


    private long minutesInCache;

    public AgentSiRSS(String workingFolder, String cacheFolder, long minutesInCache) throws IOException, InterruptedException {
        super(workingFolder, cacheFolder, minutesInCache);
        this.minutesInCache = minutesInCache;
    }

    @Override
    public void run()
    {

        SpiderPersistence pers = new SpiderPersistence();

        try
        {


            clearNewLinks();
            String url = getUrl();
            StringBuffer rawnews = getRequestXML(url, minutesInCache);
            if (!RSSHelper.isXMLRSS(rawnews.toString()))
            {
                String infoMessage = "[AgentSiRSS] --> Not a valid RSS source " + getSeed();
                log.warn(infoMessage);
                pers.saveUrlToBlackList(getSeed(),infoMessage);
                return;
            }

            if(rawnews == null)
            {
                String infoMessage = "[AgentSiRSS] --> Error requesting \"" + url + "\" from \"" + getProxy() + "\"";
                log.warn(infoMessage);
                pers.saveUrlToBlackList(url,infoMessage);
                setProxy(null);
            }
            else
            {
                String name = url.replaceAll("[\\\\/:\\*\"\\?<>\\|]", "");
                saveResult(rawnews, name);
                log.info("[AgentSiRSS] --> Save founded seed \"" + name + "\"");
            }
        }
        catch(Exception e)
        {
            log.warn("[AgentSiRSS] --> getRequest error: class(" + e.getClass().toString() + "), message(" + e.getMessage() + ")");
            e.printStackTrace();
        }


    }

}
