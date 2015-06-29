package com.androidxtrem.spider.actors;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.wasshot.feederwerk.HttpGetter;
import com.wasshot.feederwerk.feed.FeedFetcher;
import com.wasshot.feederwerk.feed.FeedParser;
import com.wasshot.feederwerk.feed.FeedUtils;
import com.wasshot.feederwerk.feed.FetchedFeed;
import com.wasshot.feederwerk.model.FeedEntry;

import java.nio.charset.Charset;

public class FeedRetrieverActor extends UntypedActor {

    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    public void onReceive(Object feedEntry) throws Exception {
        if (feedEntry instanceof FeedEntry) {
            String url = ((FeedEntry) feedEntry).getUrl();
            System.out.println("getting url: " + url);

            HttpGetter httpGetter = new HttpGetter();
            HttpGetter.HttpResult result = httpGetter.getBinary(url, 5000);
            byte[] content = result.getContent();
            Charset encoding = FeedUtils.guessEncoding(content);
            String html = FeedUtils.trimInvalidXmlCharacters(new String(content, encoding));
        }
    }
}