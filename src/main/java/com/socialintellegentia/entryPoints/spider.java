package com.socialintellegentia.entryPoints;

import com.apiumtech.spider.si.SpiderSiRSS;

/**
 * Created with IntelliJ IDEA.
 * User: jonny
 * Date: 12/07/13
 * Time: 17:33
 * To change this template use File | Settings | File Templates.
 */
public class spider {



    public static void main(String[] args)
    {
        try
        {
            SpiderSiRSS runner = new SpiderSiRSS();

            runner.getNewsFromRSSserver("http://news.bbc.co.uk/2/hi/help/rss/");
            runner.getNewsFromRSSserver("http://www.foxnews.com/about/rss/");
            runner.getNewsFromRSSserver("http://rss.elmundo.es/rss/");

        }
        catch (Exception e){
            System.out.println("Error when running spider: " + e.getMessage());
        }
    }



}
