package com.androidxtrem.spider.services;

import redis.clients.jedis.Jedis;

/**
 * Created by Xavi the Hut on 21/04/14.
 */
public class JedisPersistence implements IJedisBasics {

    public final static String PROXY_BLACKLIST = "proxy-blacklist";
    private static final int PROXY_BLACKLIST_DATABASE_INDEX = 9;
    private Jedis jedis;

    public JedisPersistence() {
        jedis = new Jedis("127.0.0.1");
        jedis.set("port","6379");
        jedis.select(PROXY_BLACKLIST_DATABASE_INDEX);
    }

    public int set(String message) {
        if (!jedis.isConnected()) return -1;
        String set = jedis.set(message,"blacklist");
        return set.equals("OK") ? 1 : 0;
    }

    public String get(String message) {
        message = jedis.get(message);
        return message==null ? "" : message;
    }

    @Override
    public void reset() {
        if (jedis.isConnected())
        jedis.del(PROXY_BLACKLIST);
    }

    @Override
    public boolean isConnected() {
        return jedis.isConnected();
    }


}
