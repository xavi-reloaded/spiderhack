package com.androidxtrem.spider.actors;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.wasshot.feederwerk.HttpGetter;
import com.wasshot.feederwerk.feed.FeedFetcher;
import com.wasshot.feederwerk.feed.FeedParser;
import com.wasshot.feederwerk.feed.FetchedFeed;
import com.wasshot.feederwerk.model.FeedEntry;

public class FeedFecherActor extends UntypedActor {

    LoggingAdapter log = Logging.getLogger(getContext().system(), this);
    private FeedFetcher feedFetcher;

    ActorRef feedRetrieverActor = getContext().actorOf(new Props(FeedRetrieverActor.class));

    public FeedFecherActor() {
        feedFetcher = new FeedFetcher(new FeedParser(),new HttpGetter());
    }

    public void onReceive(Object url) throws Exception {
        if (url instanceof String) {
            System.out.println("FeedFecherActor");
            FetchedFeed fetchedFeed = feedFetcher.fetch((String) url);
            for (FeedEntry feedEntry : fetchedFeed.getEntries()) {
                feedRetrieverActor.tell(feedEntry,getSelf());
            }
        }
    }
}