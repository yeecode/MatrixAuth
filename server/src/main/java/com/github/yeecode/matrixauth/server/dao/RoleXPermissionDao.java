package com.github.yeecode.matrixauth.server.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RoleXPermissionDao {
    Integer add(@Param("appName") String appName, @Param("roleName") String roleName, @Param("permKey") String permKey);

    Integer delete(@Param("appName") String appName, @Param("roleName") String roleName, @Param("permKey") String permKey);

    Integer deleteByRoleName(@Param("appName") String appName, @Param("roleName") String roleName);

    Integer deleteByPermKey(@Param("appName") String appName, @Param("permKey") String permKey);
}
