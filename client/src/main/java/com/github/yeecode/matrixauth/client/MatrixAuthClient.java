package com.github.yeecode.matrixauth.client;

import com.github.yeecode.matrixauth.client.bean.HttpClientBean;
import com.github.yeecode.matrixauth.client.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MatrixAuthClient {
    @Autowired
    private HttpClientBean httpClientBean;

    public Result addUserXRole(String userKey, String roleId) {
        return httpClientBean.addUserXRole(userKey, roleId);
    }

    public Result deleteUserXRole(String userKey, String roleId) {
        return httpClientBean.deleteUserXRole(userKey, roleId);
    }
}
