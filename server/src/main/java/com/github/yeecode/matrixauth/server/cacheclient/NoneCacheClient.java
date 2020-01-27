package com.github.yeecode.matrixauth.server.cacheclient;

import java.io.IOException;

public class NoneCacheClient extends CacheClient {
    @Override
    public void addOrUpdate(String fullUserKey, String permissions) {

    }

    @Override
    public void delete(String fullUserKey) {

    }

    @Override
    public void close() throws IOException {

    }
}
