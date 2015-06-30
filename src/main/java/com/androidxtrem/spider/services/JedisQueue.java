package com.androidxtrem.spider.services;

import redis.clients.jedis.Jedis;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Xavi the Hut on 21/04/14.
 */
public class JedisQueue implements IQueueAPI {

    private Jedis jedis;

    public JedisQueue() {
        jedis = new Jedis("127.0.0.1");
        jedis.set("port","6379");
    }

    @Override
    public int addMessageToQueue(String message) {
        if (!jedis.isConnected()) return -1;
        final Long bitcount = jedis.rpush(PROXY_BLACKLIST, message);
        return new BigDecimal(bitcount).intValueExact();
    }

    @Override
    public String getMessageFromQueue() {
        if (!jedis.isConnected()) return "";
        List<String> message = jedis.blpop(0, PROXY_BLACKLIST);
        if (message==null) return null;
        if (message.size()<2) return null;
        return message.get(1);
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
