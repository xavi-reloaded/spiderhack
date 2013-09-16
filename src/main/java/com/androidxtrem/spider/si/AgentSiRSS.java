package com.androidxtrem.spider.si;

import com.androidxtrem.commonsHelpers.FileHelper;
import com.androidxtrem.spider.agent.Agent;
import com.socialintellegentia.commonhelpers.hibernate.SpiderPersistence;
import com.socialintellegentia.commonhelpers.rss.RSSHelper;
import com.socialintellegentia.processes.ProcessRSS;

import java.io.File;
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
    private int m_milisecondsBetweenQueries = 1000;

    public AgentSiRSS(String workingFolder, String cacheFolder, long minutesInCache, SpiderPersistence persistence) throws IOException, InterruptedException {
        super(workingFolder, cacheFolder, minutesInCache);
        this.minutesInCache = minutesInCache;
        this.persistence = persistence;
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
                writeErrorFile(name, "--nein---");
                return;
            }
            else
            {
                if (!RSSHelper.isXMLRSS(rawnews.toString()) && !RSSHelper.isRssLandingPage(rawnews.toString()))
                {
                    String infoMessage = "[AgentSiRSS] --> Not a valid RSS source " + getSeed();
                    log.warn(infoMessage);
                    persistence.saveUrlToBlackList(getSeed(), infoMessage);
                    writeErrorFile(name, rawnews.toString());
                    return;
                }
                saveResult(rawnews, name);
                log.info("[AgentSiRSS] --> Save founded seed \"" + name + "\"");
            }


//            try {
//                processRSS.processRSSfromWorkingDirectory(getWorkingFolder());
//            } catch (Exception e) {
//                log.error("[AgentSiRSS] --> Error processing \"" + name + "\"");
//            }

            Thread.sleep(m_milisecondsBetweenQueries);
        }
        catch(Exception e)
        {
            log.warn("[AgentSiRSS] --> getRequest error: class(" + e.getClass().toString() + "), message(" + e.getMessage() + ")");
            e.printStackTrace();
        }


    }

    private void writeErrorFile(String name, String rawnews) throws IOException, InterruptedException {
        String sep = System.getProperty("file.separator");
        String folder = getWorkingFolder() + sep + "error";
        String outputFile = folder + sep + name + ".ERROR";
        if (!FileHelper.fileExists(folder)) FileHelper.createFolder(folder);
        if(!FileHelper.fileExists(outputFile))
        {
            FileHelper.stringToFile(rawnews.toString(), outputFile);
        }
    }

}
