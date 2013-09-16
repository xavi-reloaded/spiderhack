package com.androidxtrem.spider.si;

import com.androidxtrem.spider.agent.Agent;
import com.socialintellegentia.commonhelpers.hibernate.SpiderPersistence;
import com.socialintellegentia.commonhelpers.rss.RSSHelper;
import com.socialintellegentia.processes.ProcessRSS;

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
    private SpiderPersistence persistence;
    private ProcessRSS processRSS;

    public AgentSiRSS(String workingFolder, String cacheFolder, long minutesInCache, SpiderPersistence persistence, ProcessRSS processRSS) throws IOException, InterruptedException {
        super(workingFolder, cacheFolder, minutesInCache);
        this.minutesInCache = minutesInCache;
        this.persistence = persistence;
        this.processRSS = processRSS;
    }

    @Override
    public void run()
    {

        try
        {
            String url = getUrl();
            String name = url.replaceAll("[\\\\/:\\*\"\\?<>\\|]", "");
            StringBuffer rawnews = getRequestXML(url, minutesInCache);

            if(rawnews == null)
            {
                String infoMessage = "[AgentSiRSS] --> Error requesting \"" + url + "\" from \"" + getProxy() + "\"";
                log.warn(infoMessage);
                persistence.saveUrlToBlackList(url, infoMessage);
//                setProxy(null);
                saveResult(rawnews, name+".NULL.ERROR");
                return;
            }
            else
            {
                if (!RSSHelper.isXMLRSS(rawnews.toString()) && !RSSHelper.isRssLandingPage(rawnews.toString()))
                {
                    String infoMessage = "[AgentSiRSS] --> Not a valid RSS source " + getSeed();
                    log.warn(infoMessage);
                    persistence.saveUrlToBlackList(getSeed(), infoMessage);
                    saveResult(rawnews, name+".NOT.VALID.ERROR");
                    return;
                }
                saveResult(rawnews, name);
                log.info("[AgentSiRSS] --> Save founded seed \"" + name + "\"");
            }


            try {
                processRSS.processRSSfromWorkingDirectory(getWorkingFolder());
            } catch (Exception e) {
                log.error("[AgentSiRSS] --> Error processing \"" + name + "\"");
            }
        }
        catch(Exception e)
        {
            log.warn("[AgentSiRSS] --> getRequest error: class(" + e.getClass().toString() + "), message(" + e.getMessage() + ")");
            e.printStackTrace();
        }


    }

}
