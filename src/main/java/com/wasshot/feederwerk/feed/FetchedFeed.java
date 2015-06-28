package com.wasshot.feederwerk.feed;

import com.wasshot.feederwerk.model.Feed;
import com.wasshot.feederwerk.model.FeedEntry;

import java.util.ArrayList;
import java.util.List;

public class FetchedFeed {

    private Feed feed = new Feed();
    private List<FeedEntry> entries = new ArrayList<>();
    private String title;
    private String urlAfterRedirect;
    private long fetchDuration;

    public Feed getFeed() {
        return feed;
    }

    public void setFeed(Feed feed) {
        this.feed = feed;
    }

    public List<FeedEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<FeedEntry> entries) {
        this.entries = entries;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrlAfterRedirect() {
        return urlAfterRedirect;
    }

    public void setUrlAfterRedirect(String urlAfterRedirect) {
        this.urlAfterRedirect = urlAfterRedirect;
    }

    public long getFetchDuration() {
        return fetchDuration;
    }

    public void setFetchDuration(long fetchDuration) {
        this.fetchDuration = fetchDuration;
    }



}
