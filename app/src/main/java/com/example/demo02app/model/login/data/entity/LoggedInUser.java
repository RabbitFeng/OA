package com.example.demo02app.model.login.data.entity;

public class LoggedInUser {
    private String userId;
    private String username;
    private String password;
    private Integer identity;
    private boolean isLogout;
    private String profilePicUri;
    private String realName;

    public LoggedInUser() {
    }

    public LoggedInUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public LoggedInUser(String userId, String username, String password, Integer identity, boolean isLogout) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.identity = identity;
        this.isLogout = isLogout;
    }

    public LoggedInUser(String userId, String username, String password, Integer identity, boolean isLogout, String profilePicUri, String realName) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.identity = identity;
        this.isLogout = isLogout;
        this.profilePicUri = profilePicUri;
        this.realName = realName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getIdentity() {
        return identity;
    }

    public void setIdentity(Integer identity) {
        this.identity = identity;
    }

    public boolean isLogout() {
        return isLogout;
    }

    public void setLogout(boolean logout) {
        isLogout = logout;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProfilePicUri() {
        return profilePicUri;
    }

    public void setProfilePicUri(String profilePicUri) {
        this.profilePicUri = profilePicUri;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    @Override
    public String toString() {
        return "LoggedInUser{" +
                "userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", identity=" + identity +
                ", isLogout=" + isLogout +
                ", profilePicUri='" + profilePicUri + '\'' +
                ", realName='" + realName + '\'' +
                '}';
    }
}
