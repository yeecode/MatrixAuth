package com.github.yeecode.matrixauth.client.bean;

import com.github.yeecode.matrixauth.client.config.MatrixAuthConfig;
import com.github.yeecode.matrixauth.client.util.Result;
import com.github.yeecode.matrixauth.client.util.ResultUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class HttpClientBean {
    private static final Log LOGGER = LogFactory.getLog(HttpClientBean.class);

    @Autowired
    private MatrixAuthConfig matrixAuthConfig;

    public Result addUserXRole(String userKey, String roleName) {
        return operateUserXRole(userKey, roleName, "/auth/addUserXRole");
    }

    public Result deleteUserXRole(String userKey, String roleName) {
        return operateUserXRole(userKey, roleName, "/auth/deleteUserXRole");
    }

    public synchronized Result addUser(String userKey, String userName) {
        List<NameValuePair> paramsList = new ArrayList<>();
        paramsList.add(new BasicNameValuePair("userKey", userKey));
        paramsList.add(new BasicNameValuePair("userName", userName));
        paramsList.add(new BasicNameValuePair("appName", matrixAuthConfig.getApplicationName()));
        paramsList.add(new BasicNameValuePair("appToken", matrixAuthConfig.getApplicationToken()));
        return sendPost(matrixAuthConfig.getServerUrl() + "user/add", paramsList);
    }

    public synchronized Result deleteUser(String userKey) {
        List<NameValuePair> paramsList = new ArrayList<>();
        paramsList.add(new BasicNameValuePair("userKey", userKey));
        paramsList.add(new BasicNameValuePair("appName", matrixAuthConfig.getApplicationName()));
        paramsList.add(new BasicNameValuePair("appToken", matrixAuthConfig.getApplicationToken()));
        return sendPost(matrixAuthConfig.getServerUrl() + "user/delete", paramsList);
    }

    public synchronized Result updateUser(String userKey, String userName) {
        List<NameValuePair> paramsList = new ArrayList<>();
        paramsList.add(new BasicNameValuePair("userKey", userKey));
        paramsList.add(new BasicNameValuePair("userName", userName));
        paramsList.add(new BasicNameValuePair("appName", matrixAuthConfig.getApplicationName()));
        paramsList.add(new BasicNameValuePair("appToken", matrixAuthConfig.getApplicationToken()));
        return sendPost(matrixAuthConfig.getServerUrl() + "user/update", paramsList);
    }

    private synchronized Result operateUserXRole(String userKey, String roleName, String urlTail) {
        List<NameValuePair> paramsList = new ArrayList<>();
        paramsList.add(new BasicNameValuePair("userKey", userKey));
        paramsList.add(new BasicNameValuePair("roleName", roleName));
        paramsList.add(new BasicNameValuePair("appName", matrixAuthConfig.getApplicationName()));
        paramsList.add(new BasicNameValuePair("appToken", matrixAuthConfig.getApplicationToken()));
        paramsList.add(new BasicNameValuePair("requestSource","BusinessApp"));
        return sendPost(matrixAuthConfig.getServerUrl() + urlTail, paramsList);
    }

    private synchronized Result sendPost(String url, List<NameValuePair> nameValuePairList) {
        CloseableHttpResponse response = null;
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost post = new HttpPost(url);
            StringEntity entity = new UrlEncodedFormEntity(nameValuePairList, "UTF-8");
            post.setEntity(entity);
            post.setHeader(new BasicHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8"));
            post.setHeader(new BasicHeader("Accept", "text/html, application/xhtml+xml, application/json"));
            response = client.execute(post);
            int statusCode = response.getStatusLine().getStatusCode();
            String result = EntityUtils.toString(response.getEntity(), "UTF-8");
            if (200 == statusCode) {
                return ResultUtil.getSuccessResult(result);
            } else {
                return ResultUtil.getFailResult("statusCode:" + statusCode, result);
            }
        } catch (Exception e) {
            LOGGER.error("send post error", e);
            return ResultUtil.getFailResult("error", e.getMessage());
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (Exception ex) {
                    LOGGER.error("close response error", ex);
                }
            }
        }
    }
}
