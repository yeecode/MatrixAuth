package com.github.yeecode.matrixauth.server.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RoleXPermissionDao {
    Integer add(@Param("roleId") Integer roleId, @Param("permissionId") Integer permissionId);

    Integer delete(@Param("roleId") Integer roleId, @Param("permissionId") Integer permissionId);

    Integer deleteByRoleId(@Param("roleId") Integer roleId);

    Integer deleteByPermissionId(@Param("permissionId") Integer permissionId);
}
