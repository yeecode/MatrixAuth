package com.github.yeecode.matrixauth.server.dao;

import com.github.yeecode.matrixauth.server.model.PermissionModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PermissionDao {
    Integer add(PermissionModel cacheModel);

    Integer deleteByKey(@Param("key") String key, @Param("appName") String appName);

    Integer updateByKey(PermissionModel cacheModel);

    List<PermissionModel> queryByAppName(@Param("appName") String appName);

    PermissionModel queryByKey(@Param("key") String key, @Param("appName") String appName);
}
