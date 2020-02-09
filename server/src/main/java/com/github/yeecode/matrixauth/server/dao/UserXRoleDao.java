package com.github.yeecode.matrixauth.server.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserXRoleDao {
    Integer add(@Param("appName") String appName, @Param("userKey") String userKey, @Param("roleName") String roleName);

    Integer delete(@Param("appName") String appName, @Param("userKey") String userKey, @Param("roleName") String roleName);

    Integer deleteByUserKey(@Param("appName") String appName, @Param("userKey") String userKey);

    Integer deleteByRoleName(@Param("appName") String appName, @Param("roleName") String roleName);
}
