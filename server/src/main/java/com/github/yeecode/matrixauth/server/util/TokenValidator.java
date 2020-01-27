package com.github.yeecode.matrixauth.server.util;

import com.github.yeecode.matrixauth.server.model.ApplicationModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TokenValidator {
    @Value("${matrixauth.server.adminToken}")
    private String adminTokenInProperties;

    public boolean checkAdminToken(String adminToken) {
        return this.adminTokenInProperties == null || this.adminTokenInProperties.equals(adminToken);
    }

    public boolean checkApplicationToken(String appToken, ApplicationModel applicationModel) {
        return applicationModel.getToken() == null || applicationModel.getToken().equals("") || applicationModel.getToken().equals(appToken);
    }
}
