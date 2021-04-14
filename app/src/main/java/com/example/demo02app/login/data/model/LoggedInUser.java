package com.example.demo02app.login.data.model;

public class LoggedInUser {
    private String username;
    private String password;
    private Integer permission;
    private boolean isLogout;

    public LoggedInUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public LoggedInUser(String username, String password, Integer permission, boolean isLogout) {
        this.username = username;
        this.password = password;
        this.permission = permission;
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

    public Integer getPermission() {
        return permission;
    }

    public void setPermission(Integer permission) {
        this.permission = permission;
    }

    public boolean isLogout() {
        return isLogout;
    }

    public void setLogout(boolean logout) {
        isLogout = logout;
    }

    @Override
    public String toString() {
        return "LoggedInUser{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", permission=" + permission +
                ", isLogout=" + isLogout +
                '}';
    }
}
