package com.wasshot.feederwerk.model;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.time.DateUtils;

import javax.persistence.*;
import java.util.Date;

public class User extends AbstractModel {

    private String name;
    private String email;
    private byte[] password;
    private String apiKey;
    private byte[] salt;
    private boolean disabled;
    private Date lastLogin;
    private Date created;
    private String recoverPasswordToken;
    private Date recoverPasswordTokenDate;
    private Date lastFullRefresh;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getPassword() {
        return password;
    }

    public void setPassword(byte[] password) {
        this.password = password;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getRecoverPasswordToken() {
        return recoverPasswordToken;
    }

    public void setRecoverPasswordToken(String recoverPasswordToken) {
        this.recoverPasswordToken = recoverPasswordToken;
    }

    public Date getRecoverPasswordTokenDate() {
        return recoverPasswordTokenDate;
    }

    public void setRecoverPasswordTokenDate(Date recoverPasswordTokenDate) {
        this.recoverPasswordTokenDate = recoverPasswordTokenDate;
    }

    public Date getLastFullRefresh() {
        return lastFullRefresh;
    }

    public void setLastFullRefresh(Date lastFullRefresh) {
        this.lastFullRefresh = lastFullRefresh;
    }



	public boolean shouldRefreshFeedsAt(Date when) {
		return (lastFullRefresh == null || lastFullRefreshMoreThan30MinutesBefore(when));
	}

	private boolean lastFullRefreshMoreThan30MinutesBefore(Date when) {
		return lastFullRefresh.before(DateUtils.addMinutes(when, -30));
	}

}
