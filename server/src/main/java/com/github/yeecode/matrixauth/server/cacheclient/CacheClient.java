package com.github.yeecode.matrixauth.server.cacheclient;

import java.io.Closeable;

public abstract class CacheClient implements Closeable {
    public abstract void addOrUpdate(String fullUserKey, String permissions);

    public abstract void delete(String fullUserKey);
}
