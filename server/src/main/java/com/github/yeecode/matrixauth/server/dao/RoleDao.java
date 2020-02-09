package com.github.yeecode.matrixauth.server.dao;

import com.github.yeecode.matrixauth.server.model.RoleModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RoleDao {
    Integer add(RoleModel cacheModel);

    Integer deleteByName(@Param("name") String name, @Param("appName") String appName);

    Integer updateByName(RoleModel cacheModel);

    List<RoleModel> queryByAppName(@Param("name") String name);

    RoleModel queryByName(@Param("name") String name, @Param("appName") String appName);
}
