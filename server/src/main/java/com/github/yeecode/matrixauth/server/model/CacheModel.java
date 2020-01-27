package com.github.yeecode.matrixauth.server.model;

public class CacheModel {
    private String name;
    private String url;
    private String password;

    public CacheModel() {
    }

    public CacheModel(String name, String url, String password) {
        this.name = name;
        this.url = url;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
