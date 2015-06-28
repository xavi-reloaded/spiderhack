package com.wasshot.feederwerk.model;

import com.rometools.rome.feed.synd.*;
import com.wasshot.feederwerk.feed.FeedUtils;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Entry implements Serializable {

	public static Entry build(FeedEntryStatus status, String publicUrl, boolean proxyImages) {
		Entry entry = new Entry();

		FeedEntry feedEntry = status.getEntry();
		FeedSubscription sub = status.getSubscription();
		FeedEntryContent content = feedEntry.getContent();

		entry.setId(String.valueOf(feedEntry.getId()));
		entry.setGuid(feedEntry.getGuid());
		entry.setRead(status.isRead());
		entry.setStarred(status.isStarred());
		entry.setMarkable(status.isMarkable());
		entry.setDate(feedEntry.getUpdated());
		entry.setInsertedDate(feedEntry.getInserted());
		entry.setUrl(feedEntry.getUrl());
		entry.setFeedName(sub.getTitle());
		entry.setFeedId(String.valueOf(sub.getId()));
		entry.setFeedUrl(sub.getFeed().getUrl());
		entry.setFeedLink(sub.getFeed().getLink());
		entry.setIconUrl(FeedUtils.getFaviconUrl(sub, publicUrl));
		entry.setTags(status.getTags().stream().map(t -> t.getName()).collect(Collectors.toList()));

		if (content != null) {
			entry.setRtl(FeedUtils.isRTL(feedEntry));
			entry.setTitle(content.getTitle());
			entry.setContent(FeedUtils.proxyImages(content.getContent(), publicUrl, proxyImages));
			entry.setAuthor(content.getAuthor());
			entry.setEnclosureUrl(content.getEnclosureUrl());
			entry.setEnclosureType(content.getEnclosureType());
			entry.setCategories(content.getCategories());
		}

		return entry;
	}

	public SyndEntry asRss() {
		SyndEntry entry = new SyndEntryImpl();

		entry.setUri(getGuid());
		entry.setTitle(getTitle());

		SyndContentImpl content = new SyndContentImpl();
		content.setValue(getContent());
		entry.setContents(Arrays.<SyndContent> asList(content));

		if (getEnclosureUrl() != null) {
			SyndEnclosureImpl enclosure = new SyndEnclosureImpl();
			enclosure.setType(getEnclosureType());
			enclosure.setUrl(getEnclosureUrl());
			entry.setEnclosures(Arrays.<SyndEnclosure> asList(enclosure));
		}

		entry.setLink(getUrl());
		entry.setPublishedDate(getDate());
		return entry;
	}

	@ApiModelProperty("entry id")
	private String id;

	@ApiModelProperty("entry guid")
	private String guid;

	@ApiModelProperty("entry title")
	private String title;

	@ApiModelProperty("entry content")
	private String content;

	@ApiModelProperty("comma-separated list of categories")
	private String categories;

	@ApiModelProperty("wether entry content and title are rtl")
	private boolean rtl;

	@ApiModelProperty("entry author")
	private String author;

	@ApiModelProperty("entry enclosure url, if any")
	private String enclosureUrl;

	@ApiModelProperty("entry enclosure mime type, if any")
	private String enclosureType;

	@ApiModelProperty("entry publication date")
	private Date date;

	@ApiModelProperty("entry insertion date in the database")
	private Date insertedDate;

	@ApiModelProperty("feed id")
	private String feedId;

	@ApiModelProperty("feed name")
	private String feedName;

	@ApiModelProperty("this entry's feed url")
	private String feedUrl;

	@ApiModelProperty("this entry's website url")
	private String feedLink;

	@ApiModelProperty(value = "The favicon url to use for this feed")
	private String iconUrl;

	@ApiModelProperty("entry url")
	private String url;

	@ApiModelProperty("read sttaus")
	private boolean read;

	@ApiModelProperty("starred status")
	private boolean starred;

	@ApiModelProperty("wether the entry is still markable (old entry statuses are discarded)")
	private boolean markable;

	@ApiModelProperty("tags")
	private List<String> tags;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public boolean isRtl() {
        return rtl;
    }

    public void setRtl(boolean rtl) {
        this.rtl = rtl;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getEnclosureUrl() {
        return enclosureUrl;
    }

    public void setEnclosureUrl(String enclosureUrl) {
        this.enclosureUrl = enclosureUrl;
    }

    public String getEnclosureType() {
        return enclosureType;
    }

    public void setEnclosureType(String enclosureType) {
        this.enclosureType = enclosureType;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getInsertedDate() {
        return insertedDate;
    }

    public void setInsertedDate(Date insertedDate) {
        this.insertedDate = insertedDate;
    }

    public String getFeedId() {
        return feedId;
    }

    public void setFeedId(String feedId) {
        this.feedId = feedId;
    }

    public String getFeedName() {
        return feedName;
    }

    public void setFeedName(String feedName) {
        this.feedName = feedName;
    }

    public String getFeedUrl() {
        return feedUrl;
    }

    public void setFeedUrl(String feedUrl) {
        this.feedUrl = feedUrl;
    }

    public String getFeedLink() {
        return feedLink;
    }

    public void setFeedLink(String feedLink) {
        this.feedLink = feedLink;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
