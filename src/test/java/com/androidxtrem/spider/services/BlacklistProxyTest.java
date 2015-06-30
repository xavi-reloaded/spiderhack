package com.androidxtrem.spider.services;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.InetSocketAddress;
import java.net.Proxy;

import static org.testng.Assert.*;

public class BlacklistProxyTest {


    private BlacklistProxy sut;

    @BeforeMethod
    public void setUp() throws Exception {

        sut = new BlacklistProxy();
    }

    @Test
    public void test_isBlacklisted_inDB_true() throws Exception {

        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("0.0.0.0", 12345));
        insertProxy(proxy);


        Boolean actual = sut.isBlacklisted(proxy);

    }

    private void insertProxy(Proxy proxy) {
        sut.add(proxy);
    }
}