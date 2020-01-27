package com.github.yeecode.matrixauth.server.business;

import com.github.yeecode.matrixauth.server.constant.Sentence;
import com.github.yeecode.matrixauth.server.dao.DataSourceDao;
import com.github.yeecode.matrixauth.server.model.DataSourceModel;
import com.github.yeecode.matrixauth.server.util.Result;
import com.github.yeecode.matrixauth.server.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataSourceBusiness {
    @Autowired
    private DataSourceDao dataSourceDao;

    public Result add(DataSourceModel dataSourceModel) {
        try {
            dataSourceDao.add(dataSourceModel);
            return ResultUtil.getSuccessResult();
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResultUtil.getFailResult(Sentence.UNKNOWN_ERROR, ex.getMessage());
        }
    }

    public Result deleteByName(String name) {
        try {
            dataSourceDao.deleteByName(name);
            return ResultUtil.getSuccessResult();
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResultUtil.getFailResult(Sentence.UNKNOWN_ERROR, ex.getMessage());
        }
    }

    public Result updateByName(DataSourceModel dataSourceModel) {
        try {
            dataSourceDao.updateByName(dataSourceModel);
            return ResultUtil.getSuccessResult();
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResultUtil.getFailResult(Sentence.UNKNOWN_ERROR, ex.getMessage());
        }
    }

    public Result queryAll() {
        try {
            List<DataSourceModel> dataSourceModelList = dataSourceDao.queryAll();
            return ResultUtil.getSuccessResult(dataSourceModelList);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResultUtil.getFailResult(Sentence.UNKNOWN_ERROR, ex.getMessage());
        }
    }

    public Result queryByName(String name) {
        try {
            DataSourceModel dataSourceModel = dataSourceDao.queryByName(name);
            return ResultUtil.getSuccessResult(dataSourceModel);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResultUtil.getFailResult(Sentence.UNKNOWN_ERROR, ex.getMessage());
        }
    }
}
