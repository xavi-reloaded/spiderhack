package com.apiumtech.spider.si;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created with IntelliJ IDEA.
 * User: fjhidalgo
 * Date: 5/29/13
 * Time: 6:09 PM
 * To change this template use File | Settings | File Templates.
 */

public class AgentSiRSS_Test
{
    private AgentSiRSS sut;

    @BeforeMethod
    public void setUp() throws Exception {
        String testWorkingFolder = "tempWorkingFolder";
        String testCacheFolder = "tempWorkingFolder";
        sut = new AgentSiRSS(testWorkingFolder, testCacheFolder, Long.MAX_VALUE);

    }

    @Test
    public void test_Name() throws Exception {

        String url = "http://feeds.foxnews.com/foxnews/latest";

        for (String link : sut.getNewLinks()) {
            System.out.println(link);
        }
    }
}
