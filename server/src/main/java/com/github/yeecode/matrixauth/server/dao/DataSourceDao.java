package com.github.yeecode.matrixauth.server.dao;

import com.github.yeecode.matrixauth.server.model.DataSourceModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DataSourceDao {
    Integer add(DataSourceModel cacheModel);

    Integer deleteByName(@Param("name") String name);

    Integer updateByName(DataSourceModel cacheModel);

    List<DataSourceModel> queryAll();

    DataSourceModel queryByName(@Param("name") String name);
}
