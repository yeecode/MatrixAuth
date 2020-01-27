package com.github.yeecode.matrixauth.server.model;

public class DataSourceModel {
    private String name;
    private String url;
    private String driver;
    private String userName;
    private String password;

    public DataSourceModel() {
    }

    public DataSourceModel(String name, String url, String driver, String userName, String password) {
        this.name = name;
        this.url = url;
        this.driver = driver;
        this.userName = userName;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
