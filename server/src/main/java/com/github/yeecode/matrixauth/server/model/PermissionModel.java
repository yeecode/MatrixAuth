package com.github.yeecode.matrixauth.server.model;

public class PermissionModel {
    private Integer id;
    private String appName;
    private String name;
    private String code;
    private String description;

    public PermissionModel() {
    }

    public PermissionModel(String appName, String name, String code, String description) {
        this.appName = appName;
        this.name = name;
        this.code = code;
        this.description = description;
    }

    public PermissionModel(Integer id, String appName, String name, String code, String description) {
        this.id = id;
        this.appName = appName;
        this.name = name;
        this.code = code;
        this.description = description;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
