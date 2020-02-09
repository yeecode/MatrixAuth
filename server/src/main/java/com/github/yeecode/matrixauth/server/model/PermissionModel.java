package com.github.yeecode.matrixauth.server.model;

public class PermissionModel {
    private String appName;
    private String key;
    private String name;
    private String description;

    public PermissionModel() {
    }

    public PermissionModel(String appName, String key, String name, String description) {
        this.appName = appName;
        this.name = name;
        this.key = key;
        this.description = description;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
