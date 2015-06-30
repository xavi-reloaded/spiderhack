package com.androidxtrem.spider.core.proxy;

import com.androidxtrem.spider.core.HttpDownloader;
import com.androidxtrem.spider.core.SpiderConfig;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by apium on 6/29/15.
 */
public class ProxyListManager implements SpiderConfig{
    List<IProxyRetriever> proxyRetrieverList;
    public ProxyTester proxyTester;

    public ProxyListManager() {

        proxyRetrieverList = new ArrayList<>();
        proxyRetrieverList.add(new FixedKnownProxies());
//        proxyRetrieverList.add(new LocalProxy());
        proxyRetrieverList.add(new ProxiesRoseInstrument());

        proxyTester = new ProxyTester();

    }

    protected List<Proxy> getProxyList(String cacheFolder) throws IOException
    {
        List<Proxy> proxyList = new ArrayList<>();
        for (IProxyRetriever retriever : proxyRetrieverList) {

            proxyList.addAll( retriever.getProxyList() );

        }

        System.out.println(proxyList.size() + " proxies founded");

        return randomizelist(proxyList);
    }



    protected List<Proxy> cleanlist(List<Proxy> list)
    {
        for(int i = 0; i < list.size(); i++)
        {
            for(int j = 0; j < list.size(); j++)
            {
                if(i != j)
                {
                    if(list.get(i) != null && list.get(j) != null)
                    {
                        if(list.get(i).toString().compareTo(list.get(j).toString()) == 0)
                        {
                            list.set(i, null);
                            break;
                        }
                    }
                }
            }
        }
        for(int i = 0; i < list.size(); i++)
        {
            if(list.get(i) == null)
                list.remove(i);
        }
        return list;
    }

    protected List<Proxy> randomizelist(List<Proxy> list)
    {
        List<Proxy> newList = new ArrayList<Proxy>();
        while (list.size() > 0)
        {
            int i = (int) (Math.random() * (double) list.size());
            newList.add(list.get(i));
            list.remove(i);
        }
        return newList;
    }


    protected boolean testProxy(Proxy proxy)
    {
        return proxyTester.test(proxy);    }
}
