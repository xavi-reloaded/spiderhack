package com.wasshot.feederwerk.model;

import java.util.Set;

public class FeedSubscription extends AbstractModel {

	private User user;
	private Feed feed;
	private String title;
	private FeedCategory category;
	private Set<FeedEntryStatus> statuses;
	private Integer position;
	private String filter;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Feed getFeed() {
        return feed;
    }

    public void setFeed(Feed feed) {
        this.feed = feed;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public FeedCategory getCategory() {
        return category;
    }

    public void setCategory(FeedCategory category) {
        this.category = category;
    }

    public Set<FeedEntryStatus> getStatuses() {
        return statuses;
    }

    public void setStatuses(Set<FeedEntryStatus> statuses) {
        this.statuses = statuses;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }
}
