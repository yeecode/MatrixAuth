package com.github.yeecode.matrixauth.client.bean;

import com.github.yeecode.matrixauth.client.config.MatrixAuthConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

@Component
public class RedisClientBean {
    private Jedis jedis;
    private boolean initFlag = false;
    @Autowired
    private MatrixAuthConfig matrixAuthConfig;
    private Integer cacheDuration;


    private void init() {
        if (matrixAuthConfig.getCacheUrl() != null && !matrixAuthConfig.getCacheUrl().equals("")) {
            String address = matrixAuthConfig.getCacheUrl().split(":")[0];
            int port = Integer.parseInt(matrixAuthConfig.getCacheUrl().split(":")[1]);
            jedis = new Jedis(address, port);
            if (matrixAuthConfig.getCachePassword() != null && matrixAuthConfig.getCachePassword().length() > 0) {
                jedis.auth(matrixAuthConfig.getCachePassword());
            }
            cacheDuration = matrixAuthConfig.getCacheDuration() == null ? 43200 : matrixAuthConfig.getCacheDuration();
        }
        initFlag = true;
    }

    public void addOrUpdate(String fullUserKey, String permissions) {
        if (!initFlag) {
            init();
        }
        if (jedis != null) {
            jedis.setex(fullUserKey, cacheDuration, permissions);
        }
    }

    public String query(String fullUserKey) {
        if (!initFlag) {
            init();
        }
        if (jedis != null) {
            return jedis.get(fullUserKey);
        }
        return null;
    }

    public void close() {
        if (jedis != null) {
            jedis.close();
        }
    }
}
