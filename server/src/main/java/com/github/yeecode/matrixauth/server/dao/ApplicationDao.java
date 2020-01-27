package com.github.yeecode.matrixauth.server.dao;

import com.github.yeecode.matrixauth.server.model.ApplicationModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ApplicationDao {
    Integer add(ApplicationModel applicationModel);

    Integer deleteByName(@Param("name") String name);

    Integer updateByName(ApplicationModel applicationModel);

    List<ApplicationModel> queryAll();

    ApplicationModel queryByName(@Param("name") String name);
}
