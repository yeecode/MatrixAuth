package com.github.yeecode.matrixauth.server.cacheclient;

import org.springframework.beans.factory.annotation.Value;
import redis.clients.jedis.Jedis;

import java.io.IOException;

public class RedisCacheClient extends CacheClient {
    private Jedis jedis;

    public RedisCacheClient(String url, String password) {
        String address = url.split(":")[0];
        int port = Integer.parseInt(url.split(":")[1]);
        jedis = new Jedis(address, port);
        if (password != null && password.length() > 0) {
            jedis.auth(password);
        }
    }

    public void addOrUpdate(String fullUserKey, String permissions) {
        jedis.set(fullUserKey, permissions);
    }

    public void delete(String fullUserKey) {
        jedis.del(fullUserKey);
    }

    @Override
    public void close() throws IOException {
        jedis.close();
    }
}
