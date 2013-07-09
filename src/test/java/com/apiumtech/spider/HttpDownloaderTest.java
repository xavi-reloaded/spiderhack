package com.apiumtech.spider;

import junit.framework.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created with IntelliJ IDEA.
 * User: fjhidalgo
 * Date: 7/30/12
 * Time: 3:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class HttpDownloaderTest {
    private HttpDownloader sut;

    @BeforeMethod
    public void setUp() throws Exception {
        sut = new HttpDownloader("/media/DioCane/sd_spider/spidercache_paginasamarillases", 10 * 24 * 60, 10000, 60000);

    }

    @Test
    public void testSetProxy() throws Exception {

    }

    @Test
    public void testGetProxy() throws Exception {

    }

    @Test
    public void testIsInCache() throws Exception {

    }

    @Test
    public void testGetRequest() throws Exception {
        String url = "http://www.paginasamarillas.es/search/colegio-publico/all-ma/all-pr/all-is/all-ci/all-ba/all-pu/all-nc/1";
        StringBuffer actual = sut.getRequest(url, -1);
        Assert.assertTrue(actual.toString(), actual.toString().startsWith("\r\n<!DOCTYPE html PUBLIC"));
    }

    @Test
    public void testGetRequestBinary() throws Exception {

    }

    @Test
    public void testPostRequest() throws Exception {

    }

    @Test
    public void test_getCacheFileName_url_parsedUrlWritebleToFile() throws Exception {

        String url = "http://feeds.foxnews.com/foxnews/latests";
        String actual = sut.getCacheFileName(url);
        String expected = "/media/diocane/sd_spider/spidercache_paginasamarillases/httpfeeds.foxnews.comfoxnewslatests.html";
        Assert.assertEquals("",expected, actual);
    }
}
