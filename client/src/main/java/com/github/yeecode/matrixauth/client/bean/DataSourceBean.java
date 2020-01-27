package com.github.yeecode.matrixauth.client.bean;

import com.github.yeecode.matrixauth.client.config.MatrixAuthConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.sql.*;

@Component
public class DataSourceBean {
    private Connection connection;
    private PreparedStatement statement;
    private boolean initFlag = false;
    @Autowired
    private MatrixAuthConfig matrixAuthConfig;

    private void init() {
        try {
            Class.forName(matrixAuthConfig.getDataSourceDriver());
            connection = DriverManager.getConnection(
                    matrixAuthConfig.getDataSourceUrl(),
                    matrixAuthConfig.getDataSourceUserName(),
                    matrixAuthConfig.getDataSourcePassword());
            statement = connection.prepareStatement("SELECT `permissions` FROM `user_x_permission` WHERE `fullUserKey`= ?");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        initFlag = true;
    }


    public String queryPermissionsByFullUserKey(String fullUserKey) {
        try {
            if (!initFlag) {
                init();
            }
            if (statement != null) {
                statement.setString(1, fullUserKey);
                ResultSet resultSet = statement.executeQuery();
                resultSet.next();
                return resultSet.getString("permissions");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
