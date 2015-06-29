package com.androidxtrem.spider.core.proxy;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Proxy;
import java.util.Collection;
import java.util.List;

/**
 * Created by apium on 6/29/15.
 */
public interface IProxyRetriever {

    List<Proxy> getProxyList() throws IOException;
    String decodeProxyList(String str, long mod) throws UnsupportedEncodingException;
}
