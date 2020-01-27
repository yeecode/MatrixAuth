package com.github.yeecode.matrixauth.server.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserXPermissionDao {
    Integer addOrUpdate(@Param("fullUserKey") String fullUserKey, @Param("permissions") String permissions);

    Integer deleteByFullUserKey(@Param("fullUserKey") String fullUserKey);
}
