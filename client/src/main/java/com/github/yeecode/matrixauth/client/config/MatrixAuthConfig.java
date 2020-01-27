package com.github.yeecode.matrixauth.client.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MatrixAuthConfig {
    @Value("${yeecode.matrixauth.applicationName}")
    private String applicationName;

    @Value("${yeecode.matrixauth.serverUrl}")
    private String serverUrl; // HalfControl模式下使用
    @Value("${yeecode.matrixauth.applicationToken}")
    private String applicationToken; // HalfControl模式下使用

    @Value("${yeecode.matrixauth.datasource.url}")
    private String dataSourceUrl;
    @Value("${yeecode.matrixauth.datasource.driver}")
    private String dataSourceDriver;
    @Value("${yeecode.matrixauth.datasource.userName}")
    private String dataSourceUserName;
    @Value("${yeecode.matrixauth.datasource.password}")
    private String dataSourcePassword;

    @Value("${yeecode.matrixauth.cacheclient.url}")
    private String cacheUrl;
    @Value("${yeecode.matrixauth.cacheclient.password}")
    private String cachePassword;
    @Value("${yeecode.matrixauth.cacheclient.duration}")
    private Integer cacheDuration;

    public String getApplicationName() {
        return applicationName;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public String getApplicationToken() {
        return applicationToken;
    }

    public String getCacheUrl() {
        return cacheUrl;
    }

    public String getCachePassword() {
        return cachePassword;
    }

    public Integer getCacheDuration() {
        return cacheDuration;
    }

    public String getDataSourceUrl() {
        return dataSourceUrl;
    }

    public String getDataSourceDriver() {
        return dataSourceDriver;
    }

    public String getDataSourceUserName() {
        return dataSourceUserName;
    }

    public String getDataSourcePassword() {
        return dataSourcePassword;
    }
}
