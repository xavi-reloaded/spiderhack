package com.socialintellegentia.entryPoints;

import com.apiumtech.spider.si.SpiderSiRSS;

/**
 * Created with IntelliJ IDEA.
 * User: jonny
 * Date: 12/07/13
 * Time: 17:33
 * To change this template use File | Settings | File Templates.
 */
public class runSpider {



    public static void main(String[] args)
    {
        try
        {
            SpiderSiRSS runner = new SpiderSiRSS();
            String rssServer = "http://www.foxnews.com/about/rss/";
            runner.getNewsFromRSSserver(rssServer);
        }
        catch (Exception e){
            System.out.println("Error when running spider: " + e.getMessage());
        }
    }



}
