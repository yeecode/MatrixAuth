package com.github.yeecode.matrixauth.server.dao;

import com.github.yeecode.matrixauth.server.model.CacheModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CacheDao {
    Integer add(CacheModel cacheModel);

    Integer deleteByName(@Param("name") String name);

    Integer updateByName(CacheModel cacheModel);

    List<CacheModel> queryAll();

    CacheModel queryByName(@Param("name") String name);
}
