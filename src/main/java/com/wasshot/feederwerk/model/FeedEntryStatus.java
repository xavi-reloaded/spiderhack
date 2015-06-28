package com.wasshot.feederwerk.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "FEEDENTRYSTATUSES")
@SuppressWarnings("serial")
@Getter
@Setter
public class FeedEntryStatus extends AbstractModel {

	private FeedSubscription subscription;
	private FeedEntry entry;
	private boolean read;
	private boolean starred;
	private boolean markable;
	private List<FeedEntryTag> tags = new ArrayList<>();
	private User user;
	private Date entryInserted;
	private Date entryUpdated;

	public FeedEntryStatus() {

	}


	public FeedEntryStatus(User user, FeedSubscription subscription, FeedEntry entry) {
		setUser(user);
		setSubscription(subscription);
		setEntry(entry);
		setEntryInserted(entry.getInserted());
		setEntryUpdated(entry.getUpdated());
	}

    public FeedSubscription getSubscription() {
        return subscription;
    }

    public void setSubscription(FeedSubscription subscription) {
        this.subscription = subscription;
    }

    public FeedEntry getEntry() {
        return entry;
    }

    public void setEntry(FeedEntry entry) {
        this.entry = entry;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public boolean isStarred() {
        return starred;
    }

    public void setStarred(boolean starred) {
        this.starred = starred;
    }

    public boolean isMarkable() {
        return markable;
    }

    public void setMarkable(boolean markable) {
        this.markable = markable;
    }

    public List<FeedEntryTag> getTags() {
        return tags;
    }

    public void setTags(List<FeedEntryTag> tags) {
        this.tags = tags;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getEntryInserted() {
        return entryInserted;
    }

    public void setEntryInserted(Date entryInserted) {
        this.entryInserted = entryInserted;
    }

    public Date getEntryUpdated() {
        return entryUpdated;
    }

    public void setEntryUpdated(Date entryUpdated) {
        this.entryUpdated = entryUpdated;
    }
}
