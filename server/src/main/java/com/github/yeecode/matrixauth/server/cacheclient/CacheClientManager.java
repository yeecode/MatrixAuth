package com.github.yeecode.matrixauth.server.cacheclient;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CacheClientManager {
    private Map<String, CacheClient> redisClientMap = new HashMap<>();

    public CacheClient getRedisClient(String url, String password) {
        String key = getCacheClientKey(url, password);
        if (redisClientMap.containsKey(key)) {
            return redisClientMap.get(key);
        } else {
            RedisCacheClient redisCacheClient = new RedisCacheClient(url, password);
            redisClientMap.putIfAbsent(key, redisCacheClient);
            return redisCacheClient;
        }
    }

    private String getCacheClientKey(String url, String password) {
        return url + password;
    }
}
