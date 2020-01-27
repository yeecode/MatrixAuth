package com.github.yeecode.matrixauth.server.business;

import com.github.yeecode.matrixauth.server.constant.Sentence;
import com.github.yeecode.matrixauth.server.dao.ApplicationDao;
import com.github.yeecode.matrixauth.server.model.ApplicationModel;
import com.github.yeecode.matrixauth.server.util.Result;
import com.github.yeecode.matrixauth.server.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationBusiness {
    @Autowired
    private ApplicationDao applicationDao;

    public Result add(ApplicationModel applicationModel) {
        try {
            applicationDao.add(applicationModel);
            return ResultUtil.getSuccessResult();
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResultUtil.getFailResult(Sentence.UNKNOWN_ERROR, ex.getMessage());
        }
    }

    public Result deleteByName(String name) {
        try {
            applicationDao.deleteByName(name);
            return ResultUtil.getSuccessResult();
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResultUtil.getFailResult(Sentence.UNKNOWN_ERROR, ex.getMessage());
        }
    }

    public Result updateByName(ApplicationModel applicationModel) {
        try {
            applicationDao.updateByName(applicationModel);
            return ResultUtil.getSuccessResult();
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResultUtil.getFailResult(Sentence.UNKNOWN_ERROR, ex.getMessage());
        }
    }

    public Result queryAll() {
        try {
            List<ApplicationModel> applicationModelList = applicationDao.queryAll();
            return ResultUtil.getSuccessResult(applicationModelList);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResultUtil.getFailResult(Sentence.UNKNOWN_ERROR, ex.getMessage());
        }
    }

    public Result queryByName(String name) {
        try {
            ApplicationModel applicationModel = applicationDao.queryByName(name);
            return ResultUtil.getSuccessResult(applicationModel);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResultUtil.getFailResult(Sentence.UNKNOWN_ERROR, ex.getMessage());
        }
    }
}
