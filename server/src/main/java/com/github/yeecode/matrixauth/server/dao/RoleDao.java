package com.github.yeecode.matrixauth.server.dao;

import com.github.yeecode.matrixauth.server.model.RoleModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RoleDao {
    Integer add(RoleModel cacheModel);

    Integer deleteById(@Param("id") Integer id);

    Integer updateById(RoleModel cacheModel);

    List<RoleModel> queryByAppName(@Param("name") String name);

    RoleModel queryById(@Param("id") Integer id);
}
