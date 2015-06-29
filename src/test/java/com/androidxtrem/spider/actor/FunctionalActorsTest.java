package com.androidxtrem.spider.actor;

import akka.actor.Props;
import com.androidxtrem.spider.actors.ProcessFeedsActor;
import org.testng.annotations.Test;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;


/**
 * Created by apium on 6/29/15.
 */
public class FunctionalActorsTest {

    @Test
    public void test_() throws Exception {
        ActorSystem _system = ActorSystem.create("MySystem");
        ActorRef myActor = _system.actorOf(new Props(ProcessFeedsActor.class));

        myActor.tell("http://www.techlearning.com/rss");

        Thread.sleep(10000);

        _system.shutdown();

    }
}
