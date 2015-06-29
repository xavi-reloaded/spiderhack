package com.androidxtrem.spider.core.proxy;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by apium on 6/29/15.
 */
public class FixedKnownProxies extends ProxyRetriever {


    public FixedKnownProxies() {
        super();
    }

    @Override
    public List<Proxy> getProxyList() throws IOException {
        List<Proxy> proxies = new ArrayList<>();
        for (KnownProxies knownProxies : getKwnownProxies()) {
            proxies.add(CreateProxyObject(knownProxies.getProxyhost(), knownProxies.getProxyport()));
        }
        return proxies;
    }

    @Override
    public String decodeProxyList(String str, long mod) throws UnsupportedEncodingException {
        return null;
    }

    private List<KnownProxies> getKwnownProxies() {
        List<KnownProxies> kwnownProxies = new ArrayList<>();
        for (String p : getP()){
            kwnownProxies.add(new KnownProxies(p));
        }
        return kwnownProxies;
    }



    private List<String> getP(){

        List<String> p = new ArrayList<>();
        p.add("117.178.242.12:8123");
        p.add("111.9.79.141:8123");
        p.add("203.223.45.110:80");
        p.add("213.135.234.6:81");
        p.add("194.213.60.227:8585");
        p.add("110.74.193.187:8080");
        p.add("117.164.204.203:8123");
        p.add("60.2.193.194:9797");
        p.add("139.214.113.66:55336");
        p.add("115.28.50.203:8877");
        p.add("115.236.9.72:9090");
        p.add("200.54.110.196:3128");
        p.add("58.20.38.146:80");
        p.add("112.25.62.4:55336");
        return p;
    }

}
