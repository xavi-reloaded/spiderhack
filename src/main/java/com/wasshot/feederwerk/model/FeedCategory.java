package com.wasshot.feederwerk.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

public class FeedCategory extends AbstractModel {

	private String name;
	private User user;
	private FeedCategory parent;
	private Set<FeedCategory> children;
	private Set<FeedSubscription> subscriptions;
	private boolean collapsed;
	private Integer position;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public FeedCategory getParent() {
        return parent;
    }

    public void setParent(FeedCategory parent) {
        this.parent = parent;
    }

    public Set<FeedCategory> getChildren() {
        return children;
    }

    public void setChildren(Set<FeedCategory> children) {
        this.children = children;
    }

    public Set<FeedSubscription> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(Set<FeedSubscription> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public boolean isCollapsed() {
        return collapsed;
    }

    public void setCollapsed(boolean collapsed) {
        this.collapsed = collapsed;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }
}
