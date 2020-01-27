package com.github.yeecode.matrixauth.server.model;

public class ApplicationModel {
    private String name;
    private String token;
    private String dataSourceName;
    private String cacheName;

    public ApplicationModel() {
    }

    public ApplicationModel(String name, String token, String dataSourceName, String cacheName) {
        this.name = name;
        this.token = token;
        this.dataSourceName = dataSourceName;
        this.cacheName = cacheName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDataSourceName() {
        return dataSourceName;
    }

    public void setDataSourceName(String dataSourceName) {
        this.dataSourceName = dataSourceName;
    }

    public String getCacheName() {
        return cacheName;
    }

    public void setCacheName(String cacheName) {
        this.cacheName = cacheName;
    }
}
