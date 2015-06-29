package com.androidxtrem.spider.core.proxy;

import com.androidxtrem.spider.RegExpHelper;
import com.androidxtrem.spider.core.SpiderConfig;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by apium on 6/29/15.
 */
public abstract class ProxyRetriever implements IProxyRetriever, SpiderConfig {

    protected String proxyWebPublisher;

    public ProxyRetriever() {}

    public ProxyRetriever(String proxyWebPublisher) {
        this.proxyWebPublisher = proxyWebPublisher;
    }

    protected List<Proxy> getProxies(String text, String pat)
    {
        List<Proxy> proxies = new ArrayList<Proxy>();
        List<List<String>> matchList = RegExpHelper.getMatches(text, pat, true);
        //
        for(int i = 0; i < matchList.size(); i++)
        {
            if(matchList.get(i).size() == 3)
            {
                String proxyhost = matchList.get(i).get(1);
                Integer proxyport = new Integer(matchList.get(i).get(2));
                System.out.println("Adding proxy " + i + "/" + matchList.size() + " " + proxyhost + ":" + proxyport);
                proxies.add(CreateProxyObject(proxyhost, proxyport));
            }
        }

        return proxies;
    }

    protected Proxy CreateProxyObject(String proxyhost, Integer proxyport) {
        return new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyhost, proxyport));
    }

    protected class KnownProxies {
        private final String proxyhost;
        private final Integer proxyport;

        public KnownProxies(String proxyhost, Integer proxyport) {
            this.proxyhost = proxyhost;
            this.proxyport = proxyport;
        }

        public KnownProxies(String p) {
            this.proxyhost = p.split("\\:")[0];
            this.proxyport = Integer.parseInt(p.split("\\:")[1]);
        }

        public String getProxyhost() {
            return proxyhost;
        }

        public Integer getProxyport() {
            return proxyport;
        }
    }
}
