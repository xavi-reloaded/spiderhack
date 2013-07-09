package com.apiumtech.spider.si;

import com.apiumtech.spider.agent.Agent;

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
                String name = url.replaceAll("[\\\\/:\\*\"\\?<>\\|]", "");
                saveResult(rawnews, name);
            }
        }
        catch(Exception e)
        {
            System.out.println("getRequest error: class(" + e.getClass().toString() + "), message(" + e.getMessage() + ")");
            e.printStackTrace();
        }
    }

}
