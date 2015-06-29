package com.androidxtrem.spider;

import com.androidxtrem.commonsHelpers.FileHelper;
import com.androidxtrem.spider.core.*;
import com.androidxtrem.spider.agent.RobotWasshotFeed;
import com.androidxtrem.spider.core.proxy.AnonymousProxyManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by apium on 6/28/15.
 */
public class DummySpider extends RobotExerciser implements SpiderConfig {

    private AnonymousProxyManager proxyManager = null;
    int maxProxyThreads = 3;

    public DummySpider() throws IOException
    {
        proxyManager = new AnonymousProxyManager(CACHE_FOLDER, maxProxyThreads);
    }

    public void methodainen() throws IOException, InterruptedException {

        String folder = WORKING_FOLDER + "/spider/__TEST__";
        FileHelper.createFolder(folder);
        FileHelper.createFolder(CACHE_FOLDER);

        addNewSeed(new Seed("http://www.techlearning.com/rss", 1));


        List<Robot> spiderList = new ArrayList<Robot>();
        for(int i = 0; i < 5; i++) {
            spiderList.add(new RobotWasshotFeed(folder, CACHE_FOLDER, Long.MAX_VALUE));
        }


        startAgentsManager(0, proxyManager, spiderList);





    }
}
