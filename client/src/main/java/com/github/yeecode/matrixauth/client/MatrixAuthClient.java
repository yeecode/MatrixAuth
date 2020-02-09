package com.github.yeecode.matrixauth.client;

import com.github.yeecode.matrixauth.client.bean.HttpClientBean;
import com.github.yeecode.matrixauth.client.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MatrixAuthClient {
    @Autowired
    private HttpClientBean httpClientBean;

    public Result addUserXRole(String userKey, String roleName) {
        return httpClientBean.addUserXRole(userKey, roleName);
    }

    public Result deleteUserXRole(String userKey, String roleName) {
        return httpClientBean.deleteUserXRole(userKey, roleName);
    }

    public Result addUser(String userKey, String userName) {
        return httpClientBean.addUser(userKey, userName);
    }

    public Result deleteUser(String userKey) {
        return httpClientBean.deleteUser(userKey);
    }

    public Result updateUser(String userKey, String userName) {
        return httpClientBean.updateUser(userKey, userName);
    }
}
