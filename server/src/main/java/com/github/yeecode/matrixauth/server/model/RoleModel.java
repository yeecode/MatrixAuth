package com.github.yeecode.matrixauth.server.model;

public class RoleModel {
    private Integer id;
    private String appName;
    private String name;
    private Integer parentId;
    private String description;
    private String type;

    public RoleModel() {
    }

    public RoleModel(String appName, String name, Integer parentId, String description, String type) {
        this.appName = appName;
        this.name = name;
        this.parentId = parentId;
        this.description = description;
        this.type = type;
    }

    public RoleModel(Integer id, String appName, String name, Integer parentId, String description, String type) {
        this.id = id;
        this.appName = appName;
        this.name = name;
        this.parentId = parentId;
        this.description = description;
        this.type = type;
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

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
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
