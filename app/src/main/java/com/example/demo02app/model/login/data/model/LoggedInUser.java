package com.example.demo02app.model.login.data.model;

public class LoggedInUser {
    private String userId;
    private String username;
    private String password;
    private Integer identity;
    private boolean isLogout;

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

    @Override
    public String toString() {
        return "LoggedInUser{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", permission=" + identity +
                ", isLogout=" + isLogout +
                '}';
    }
}
