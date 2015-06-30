package com.androidxtrem.spider.core.proxy;

import com.androidxtrem.spider.core.HttpDownloader;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.net.Proxy;

/**
 * Created by apium on 6/30/15.
 */
public class ProxyTester {


    protected Log log = LogFactory.getLog(ProxyTester.class);

    public ProxyTester() {

    }

    public boolean test(Proxy proxy) {

        if (blacklist.)


        boolean proxyWorks = false;
        try
        {
            HttpDownloader downloader = new HttpDownloader(null, -1, 5000, 10000);
            downloader.setProxy(proxy);
            String response = downloader.getRequest("http://apiumtech.com", -1, false).toString();
            if(response != null && response.length() > 0) proxyWorks = true;
        }
        catch (Exception e)
        {
//            e.printStackTrace( );
            log.error("Proxy "+proxy.toString() + " is disabled");
            log.info("Add proxy to blacklist... " + proxy.toString());
            return proxyWorks;
        }
        return proxyWorks;

    }
}
