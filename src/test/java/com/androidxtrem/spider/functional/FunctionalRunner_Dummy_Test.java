package com.androidxtrem.spider.functional;

import com.androidxtrem.spider.DummySpider;
import com.androidxtrem.spider.SpiderWasshotFeed;
import org.testng.annotations.Test;

/**
 * Created by apium on 6/29/15.
 */
public class FunctionalRunner_Dummy_Test {

    @Test
    public void test_functional_withProxies() throws Exception {
        DummySpider sut = new DummySpider();
        sut.methodainen();
        Thread.sleep(100000);
    }

    @Test
    public void test_WasshotRobot() throws Exception {
        SpiderWasshotFeed spider = new SpiderWasshotFeed();
        spider.getNewsFromRSSserver("http://www.techlearning.com/rss");
    }
}
