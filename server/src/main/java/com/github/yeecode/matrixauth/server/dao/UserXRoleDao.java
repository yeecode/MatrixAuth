package com.github.yeecode.matrixauth.server.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserXRoleDao {
    Integer add(@Param("fullUserKey") String fullUserKey, @Param("roleId") Integer roleId);

    Integer delete(@Param("fullUserKey") String fullUserKey, @Param("roleId") Integer roleId);

    Integer deleteByFullUserKey(@Param("fullUserKey") String fullUserKey);

    Integer deleteByRoleId(@Param("roleId") Integer roleId);
}
