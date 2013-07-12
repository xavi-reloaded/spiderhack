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

public class SpiderSiRSS_Test
{
    private SpiderSiRSS sut;

    @BeforeMethod
    public void setUp() throws Exception {
        sut = new SpiderSiRSS();
    }


    @Test(enabled = false)
    public void test_getNewsFromRSSserver_foxRSS_searchRSSfeeds() throws Exception {
        String rssServer = "http://www.foxnews.com/about/rss/";
        sut.getNewsFromRSSserver(rssServer);

    }

    @Test(enabled = false)
    public void test_getNewsFromRSSserver_BBC_functional() throws Exception {
        String rssServer = "http://news.bbc.co.uk/2/hi/help/rss/";
        sut.getNewsFromRSSserver(rssServer);
    }

    @Test(enabled = false)
    public void test_getNewsFromRSSserver_ElMundo_functional() throws Exception {
        String rssServer = "http://rss.elmundo.es/rss/";
        sut.getNewsFromRSSserver(rssServer);
    }
}
