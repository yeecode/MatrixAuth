package com.github.yeecode.matrixauth.client.permission;

import com.github.yeecode.matrixauth.client.bean.DataSourceBean;
import com.github.yeecode.matrixauth.client.bean.RedisClientBean;
import com.github.yeecode.matrixauth.client.config.MatrixAuthConfig;
import com.github.yeecode.matrixauth.client.util.FullUserKeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
public class PermissionHandler {
    @Autowired
    private DataSourceBean dataSourceBean;
    @Autowired
    private RedisClientBean redisClientBean;
    @Autowired
    private MatrixAuthConfig matrixAuthConfig;

    public Set<String> getPermissionSet(String userKey) {
        String fullUserKey = FullUserKeyGenerator.getFullUserKey(matrixAuthConfig.getApplicationName(), userKey);
        String permString = redisClientBean.query(fullUserKey);
        if (permString == null) {
            permString = dataSourceBean.queryPermissionsByFullUserKey(fullUserKey);
            redisClientBean.addOrUpdate(fullUserKey, permString == null ? "" : permString);
        }
        if (permString == null) {
            return new HashSet<>();
        } else {
            return new HashSet<>(Arrays.asList(permString.split(";")));
        }
    }
}
