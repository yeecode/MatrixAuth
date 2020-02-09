package com.github.yeecode.matrixauth.server.business;

import com.github.yeecode.matrixauth.server.dao.AuthDao;
import com.github.yeecode.matrixauth.server.dao.UserXPermissionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserXPermissionBusiness {
    @Autowired
    private UserXPermissionDao userXPermissionDao;
    @Autowired
    private AuthDao authDao;

    void addOrUpdatePermissionsByFullUserKey(String fullUserKey, String permissionKeys) {
        userXPermissionDao.addOrUpdate(fullUserKey, permissionKeys);
    }
}
