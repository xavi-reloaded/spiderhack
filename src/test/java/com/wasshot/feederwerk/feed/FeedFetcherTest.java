package com.wasshot.feederwerk.feed;

import com.wasshot.feederwerk.HttpGetter;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Date;

import static org.testng.Assert.*;

public class FeedFetcherTest {

    @Test
    public void testFetch() throws Exception {
        FeedFetcher sut = new FeedFetcher(new FeedParser(),new HttpGetter());
        FetchedFeed actual = sut.fetch("http://www.techlearning.com/rss", true, "", "", new Date(), "");
        Assert.assertEquals(actual.getTitle(),"Techlearning RSS Feed");
    }
}

/*
        TechLEARNing.com
        http://www.techlearning.com/RSS

        Wired Top Stories
        http://feeds.wired.com/wired/index

        New York Times - Technology
        http://feeds.nytimes.com/nyt/rss/Technology

        NPR: Technology
        http://www.npr.org/rss/rss.php?id=1019

        TIME.com Gadget of the Week
        http://feeds.feedburner.com/time/gadgetoftheweek

        Surfing the Net with Kids
        http://feeds.surfnetkids.com/SurfingTheNetWithKids

        Macworld
        http://rss.macworld.com/macworld/feeds/main

        PC World Magazine Latest News
        http://feeds.pcworld.com/pcworld/latestnews

        Techworld.com
        http://rss.feedsportal.com/c/270/f/3547/index.rss

*/