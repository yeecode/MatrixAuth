package com.github.yeecode.matrixauth.server.dao;

import com.github.yeecode.matrixauth.server.model.PermissionModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PermissionDao {
    Integer add(PermissionModel cacheModel);

    Integer deleteById(@Param("id") Integer id);

    Integer updateById(PermissionModel cacheModel);

    List<PermissionModel> queryByAppName(@Param("name") String name);

    PermissionModel queryById(@Param("id") Integer id);
}
