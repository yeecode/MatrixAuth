package com.github.yeecode.matrixauth.server.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AuthDao {
    String fastQueryPermissionCodesByFullUserKey(@Param("fullUserKey") String fullUserKey);

    List<String> queryPermKeysByUserKey(@Param("appName") String appName, @Param("userKey") String userKey);

    List<String> queryUserKeyByPermKey( @Param("appName") String appName, @Param("permKey") String permKey);

    List<String> queryUserKeyByRoleName(@Param("roleName") String roleName, @Param("appName") String appName);
}
