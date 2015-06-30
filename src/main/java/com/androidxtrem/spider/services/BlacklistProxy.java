package com.androidxtrem.spider.services;

import java.net.Proxy;

/**
 * Created by apium on 6/30/15.
 */
public class BlacklistProxy {

    private final JedisPersistence persistence;

    public BlacklistProxy() {
        persistence = new JedisPersistence();
    }

    public void add(Proxy proxy) {
        persistence.set(proxy.toString());
    }
}
