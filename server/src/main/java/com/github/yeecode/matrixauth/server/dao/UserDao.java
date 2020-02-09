package com.github.yeecode.matrixauth.server.dao;

import com.github.yeecode.matrixauth.server.model.UserModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserDao {
    Integer add(UserModel cacheModel);

    Integer deleteByKey(@Param("key") String key, @Param("appName") String appName);

    Integer updateByKey(UserModel cacheModel);

    List<UserModel> queryByAppName(@Param("appName") String appName);

    UserModel queryByKey(@Param("key") String key, @Param("appName") String appName);
}
