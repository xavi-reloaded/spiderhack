package com.apiumtech.spider;

import junit.framework.Assert;

/**
 * Created with IntelliJ IDEA.
 * User: fjhidalgo
 * Date: 7/30/12
 * Time: 3:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class HttpDownloaderTest {
    private HttpDownloader sut;

    @org.testng.annotations.BeforeMethod
    public void setUp() throws Exception {
        sut = new HttpDownloader("/media/DioCane/sd_spider/spidercache_paginasamarillases", 10 * 24 * 60, 10000, 60000);

    }

    @org.testng.annotations.Test
    public void testSetProxy() throws Exception {

    }

    @org.testng.annotations.Test
    public void testGetProxy() throws Exception {

    }

    @org.testng.annotations.Test
    public void testIsInCache() throws Exception {

    }

    @org.testng.annotations.Test
    public void testGetRequest() throws Exception {
        String url = "http://www.paginasamarillas.es/search/colegio-publico/all-ma/all-pr/all-is/all-ci/all-ba/all-pu/all-nc/1";
        String actual = sut.getRequest(url, -1);
        Assert.assertTrue(actual, actual.startsWith("\r\n<!DOCTYPE html PUBLIC"));
    }

    @org.testng.annotations.Test
    public void testGetRequestBinary() throws Exception {

    }

    @org.testng.annotations.Test
    public void testPostRequest() throws Exception {

    }
}
