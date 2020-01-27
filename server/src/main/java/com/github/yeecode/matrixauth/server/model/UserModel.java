package com.github.yeecode.matrixauth.server.model;

public class UserModel {
    private Integer id;
    private String appName;
    private String key;
    private String name;

    public UserModel() {
    }

    public UserModel(String appName, String key, String name) {
        this.appName = appName;
        this.key = key;
        this.name = name;
    }

    public UserModel(Integer id, String appName, String key, String name) {
        this.id = id;
        this.appName = appName;
        this.key = key;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
