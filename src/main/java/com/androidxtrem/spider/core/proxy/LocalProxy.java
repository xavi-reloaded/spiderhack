package com.androidxtrem.spider.core.proxy;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by apium on 6/29/15.
 */
public class LocalProxy extends ProxyRetriever {

    @Override
    public List<Proxy> getProxyList() throws IOException {
        List<Proxy> proxies = new ArrayList<>();
        proxies.add(CreateProxyObject("127.0.0.1", 8080));
        return proxies;
    }

    @Override
    public String decodeProxyList(String str, long mod) throws UnsupportedEncodingException {
        return null;
    }
}
