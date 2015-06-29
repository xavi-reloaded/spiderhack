package com.androidxtrem.spider.actors;

import static akka.pattern.Patterns.ask;
import static akka.pattern.Patterns.pipe;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;


import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.wasshot.feederwerk.feed.FetchedFeed;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.dispatch.Futures;
import akka.dispatch.Mapper;
import akka.util.Timeout;


public class ProcessFeedsActor extends UntypedActor {
    final Timeout t = new Timeout(Duration.create(5, TimeUnit.SECONDS));
    LoggingAdapter log = Logging.getLogger(getContext().system(), this);
    ActorRef feedFetcherActor = getContext().actorOf(new Props(FeedFecherActor.class));
    ActorRef feedRetrieverActor = getContext().actorOf(new Props(FeedRetrieverActor.class));

    public void onReceive(Object url) throws Exception {
        if (url instanceof String) {
            System.out.println("Received url to process: {} " + url);
            feedFetcherActor.tell(url);

//            log.info("Received url to process: {}", url);
//            getSender().tell(url, getSelf());

//            feedFetcherActor.tell(url,getSelf());

//            final ArrayList<Future<Object>> futures = new ArrayList<Future<Object>>();
////            // make concurrent calls to actors
//            futures.add(ask(feedFetcherActor, url, t));
//            futures.add(ask(feedRetrieverActor, url, t));

//            // set the sequence in which the reply are expected
//            final Future<Iterable<Object>> aggregate = Futures.sequence(
//                    futures, getContext().system().dispatcher());
////
//            // once the replies comes back, we loop through the Iterable to
//            // get the replies in same order
//
//            final Future<OrderHistory> aggResult = aggregate.map(
//                    new Mapper<Iterable<Object>, OrderHistory>() {
//                        public OrderHistory apply(Iterable<Object> coll) {
//                            final Iterator<Object> it = coll.iterator();
//                            final Order order = (Order) it.next();
//                            final Address address = (Address) it.next();
//                            return new OrderHistory(order, address);
//                        }
//                    }, getContext().system().dispatcher());
//
//
//
//            // aggregated result is piped to another actor
//            final Future<Object> future = ask(feedFetcherActor, url, t);
//            FetchedFeed result = (FetchedFeed) Await.result(future, Duration.create(50, TimeUnit.SECONDS));
//            pipe(future, getContext().system().dispatcher()).to(feedRetrieverActor);


        }
    }
}