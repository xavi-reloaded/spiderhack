package com.wasshot.feederwerk.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

public class FeedEntryTag extends AbstractModel {

	private User user;
	private FeedEntry entry;
	private String name;

	public FeedEntryTag() {
	}

	public FeedEntryTag(User user, FeedEntry entry, String name) {
		this.name = name;
		this.entry = entry;
		this.user = user;
	}

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public FeedEntry getEntry() {
        return entry;
    }

    public void setEntry(FeedEntry entry) {
        this.entry = entry;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
