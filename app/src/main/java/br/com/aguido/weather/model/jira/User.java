package br.com.aguido.weather.model.jira;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User implements Serializable{

    @SerializedName("self")
    @Expose
    private String self;
    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("accountId")
    @Expose
    private String accountId;
    @SerializedName("emailAddress")
    @Expose
    private String emailAddress;
    @SerializedName("avatarUrls")
    @Expose
    private AvatarUrls avatarUrls;
    @SerializedName("displayName")
    @Expose
    private String displayName;
    @SerializedName("active")
    @Expose
    private boolean active;
    @SerializedName("timeZone")
    @Expose
    private String timeZone;
    @SerializedName("locale")
    @Expose
    private String locale;
    @SerializedName("groups")
    @Expose
    private Groups groups;
    @SerializedName("applicationRoles")
    @Expose
    private ApplicationRoles applicationRoles;
    @SerializedName("expand")
    @Expose
    private String expand;

    private String username;
    private String pass;
    private String pic;

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getUser() {
        return username;
    }

    public void setUser(String user) {
        this.username = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getSelf() {
        return self;
    }

    public void setSelf(String self) {
        this.self = self;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public AvatarUrls getAvatarUrls() {
        return avatarUrls;
    }

    public void setAvatarUrls(AvatarUrls avatarUrls) {
        this.avatarUrls = avatarUrls;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public Groups getGroups() {
        return groups;
    }

    public void setGroups(Groups groups) {
        this.groups = groups;
    }

    public ApplicationRoles getApplicationRoles() {
        return applicationRoles;
    }

    public void setApplicationRoles(ApplicationRoles applicationRoles) {
        this.applicationRoles = applicationRoles;
    }

    public String getExpand() {
        return expand;
    }

    public void setExpand(String expand) {
        this.expand = expand;
    }

}