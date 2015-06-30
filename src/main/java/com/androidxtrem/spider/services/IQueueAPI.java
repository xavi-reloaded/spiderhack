package com.androidxtrem.spider.services;

/**
 * Created by Xavi the Hut on 21/04/14.
 */
public interface IQueueAPI extends IJedisBasics {
    public final static String PROXY_BLACKLIST = "proxy-blacklist";

    int addMessageToQueue(String message);
    String getMessageFromQueue();

}
