package com.github.yeecode.matrixauth.client.util;

public class FullUserKeyGenerator {
    public static String getFullUserKey(String appName, String userKey) {
        return appName + "-" + userKey;
    }
}
