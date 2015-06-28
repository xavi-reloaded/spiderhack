package com.wasshot.feederwerk.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

public class Feed extends AbstractModel {

    private String url;
    private String urlAfterRedirect;
    private String normalizedUrl;
    private String normalizedUrlHash;
    private String link;
    private Date lastUpdated;
    private Date lastPublishedDate;
    private Date lastEntryDate;
    private String message;
    private int errorCount;
    private Date disabledUntil;
    private String lastModifiedHeader;
    private String etagHeader;
    private Long averageEntryInterval;
    private String lastContentHash;
    private String pushHub;
    private String pushTopic;
    private String pushTopicHash;
    private Date pushLastPing;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlAfterRedirect() {
        return urlAfterRedirect;
    }

    public void setUrlAfterRedirect(String urlAfterRedirect) {
        this.urlAfterRedirect = urlAfterRedirect;
    }

    public String getNormalizedUrl() {
        return normalizedUrl;
    }

    public void setNormalizedUrl(String normalizedUrl) {
        this.normalizedUrl = normalizedUrl;
    }

    public String getNormalizedUrlHash() {
        return normalizedUrlHash;
    }

    public void setNormalizedUrlHash(String normalizedUrlHash) {
        this.normalizedUrlHash = normalizedUrlHash;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Date getLastPublishedDate() {
        return lastPublishedDate;
    }

    public void setLastPublishedDate(Date lastPublishedDate) {
        this.lastPublishedDate = lastPublishedDate;
    }

    public Date getLastEntryDate() {
        return lastEntryDate;
    }

    public void setLastEntryDate(Date lastEntryDate) {
        this.lastEntryDate = lastEntryDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(int errorCount) {
        this.errorCount = errorCount;
    }

    public Date getDisabledUntil() {
        return disabledUntil;
    }

    public void setDisabledUntil(Date disabledUntil) {
        this.disabledUntil = disabledUntil;
    }

    public String getLastModifiedHeader() {
        return lastModifiedHeader;
    }

    public void setLastModifiedHeader(String lastModifiedHeader) {
        this.lastModifiedHeader = lastModifiedHeader;
    }

    public String getEtagHeader() {
        return etagHeader;
    }

    public void setEtagHeader(String etagHeader) {
        this.etagHeader = etagHeader;
    }

    public Long getAverageEntryInterval() {
        return averageEntryInterval;
    }

    public void setAverageEntryInterval(Long averageEntryInterval) {
        this.averageEntryInterval = averageEntryInterval;
    }

    public String getLastContentHash() {
        return lastContentHash;
    }

    public void setLastContentHash(String lastContentHash) {
        this.lastContentHash = lastContentHash;
    }

    public String getPushHub() {
        return pushHub;
    }

    public void setPushHub(String pushHub) {
        this.pushHub = pushHub;
    }

    public String getPushTopic() {
        return pushTopic;
    }

    public void setPushTopic(String pushTopic) {
        this.pushTopic = pushTopic;
    }

    public String getPushTopicHash() {
        return pushTopicHash;
    }

    public void setPushTopicHash(String pushTopicHash) {
        this.pushTopicHash = pushTopicHash;
    }

    public Date getPushLastPing() {
        return pushLastPing;
    }

    public void setPushLastPing(Date pushLastPing) {
        this.pushLastPing = pushLastPing;
    }
}
