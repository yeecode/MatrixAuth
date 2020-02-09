package com.github.yeecode.matrixauth.server.model;

public class RoleModel {
    private String appName;
    private String name;
    private String type;
    private String description;

    public RoleModel() {
    }

    public RoleModel(String appName, String name, String description, String type) {
        this.appName = appName;
        this.name = name;
        this.description = description;
        this.type = type;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
