package com.androidxtrem.spider.core.proxy;

import org.junit.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.Proxy;
import java.util.List;

import static org.testng.Assert.*;

public class FixedKnownProxiesTest {


    private FixedKnownProxies sut;

    @BeforeMethod
    public void setUp() throws Exception {
        sut = new FixedKnownProxies();

    }

    @Test
    public void testGetProxyList_proxyIsHTTP() throws Exception {
        List<Proxy> actual = sut.getProxyList();
        Assert.assertEquals(actual.get(0).type().toString(), "HTTP");
    }

    @Test
    public void testGetProxyList_default_allProxiesLoaded() throws Exception {
        List<Proxy> actual = sut.getProxyList();
        Assert.assertEquals(actual.size(),14);
    }

}