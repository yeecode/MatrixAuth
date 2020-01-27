package com.github.yeecode.matrixauth.server.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AuthDao {
    String fastQueryPermissionCodesByFullUserKey(@Param("fullUserKey") String fullUserKey);

    List<String> queryPermissionCodesByFullUserKey(@Param("fullUserKey") String fullUserKey);

    List<String> queryFullUserKeyByPermissionId(@Param("permissionId") Integer permissionId);

    List<String> queryFullUserKeyByRoleId(@Param("roleId") Integer roleId);
}
