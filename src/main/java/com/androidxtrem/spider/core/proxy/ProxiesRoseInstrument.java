package com.androidxtrem.spider.core.proxy;

import com.androidxtrem.commonsHelpers.FileHelper;
import com.androidxtrem.spider.RegExpHelper;
import com.androidxtrem.spider.core.HttpDownloader;
import com.androidxtrem.spider.core.SpiderConfig;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by apium on 6/29/15.
 */
public class ProxiesRoseInstrument extends ProxyRetriever {


    public ProxiesRoseInstrument() {
        super("http://tools.rosinstrument.com/proxy/?rule2");
    }

    public List<Proxy> getProxyList() throws IOException
    {
        HttpDownloader downloader = new HttpDownloader(CACHE_FOLDER, 60 * 24, 5000, 10000);

        String response = downloader.getRequest(proxyWebPublisher).toString();

        Long modulus = new Long(RegExpHelper.getFirstMatch(response, "Math.sqrt\\(([0-9]+)\\)", 1));
        String rawProxyList = RegExpHelper.getFirstMatch(response, "hideText\\('([^']*)'\\)", 1);

        rawProxyList = decodeProxyList(rawProxyList, modulus);

        List<Proxy> proxyList = getProxies(rawProxyList, "#([0-9a-zA-Z.-_]+):([0-9]+)#");


        System.out.println(proxyList.size() + " founded in " + proxyWebPublisher);

        return proxyList;
    }


    public String decodeProxyList(String str, long mod) throws UnsupportedEncodingException
    {
        for(int i = 16; i < 256; i++)
        {
            str = str.replaceAll("%" + Integer.toHexString(i), new Character((char) i).toString());
        }
        byte[] bytes = str.getBytes();
        for(int i = 0; i < bytes.length; i++)
        {
            long modulus = Math.round(Math.sqrt(mod));
            bytes[i] = (byte) (bytes[i] ^ (byte) modulus);
        }

        return new String(bytes, "UTF-8");
    }




//    protected List<Proxy> getSshProxy(String cachefolder) throws IOException
//    {
//        HttpDownloader downloader = new HttpDownloader(cachefolder, 60 * 24, 5000, 10000);
//        StringBuffer response = downloader.getRequest("http://www.sshproxy.info/2015/04/update-1000-proxylist-21-april-2015.html");
//        List<Proxy> proxyList = getProxies(response.toString(), "([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}):([0-9]+)");
//        System.out.println(proxyList.size() + " proxies has been found from http://www.sshproxy.info/2015/04/update-1000-proxylist-21-april-2015.html");
//        return proxyList;
//    }
//
//
//
//    protected List<Proxy> getProxyList_workingproxiesorg(String cachefolder) throws IOException
//    {
//        HttpDownloader downloader = new HttpDownloader(cachefolder, 60 * 24, 5000, 10000);
//        String pattern = "<font color=\"#333333\">([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3})</font></td><td><font color=\"#333333\">([0-9]{2,5})[ \\r\\n\\t]*</font>";
//        List<Proxy> proxyList = new ArrayList<Proxy>();
//        for(int i = 1; i <= 10; i++)
//        {
//            StringBuffer response = downloader.getRequest("http://www.proxyleech.com/page/" + i + ".php");
//            proxyList.addAll(getProxies(response.toString(), pattern));
//        }
//        System.out.println(proxyList.size() + " proxies has been found from http://www.proxyleech.com");
//        return proxyList;
//    }


}
