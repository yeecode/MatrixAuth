package com.github.yeecode.matrixauth.server.business;

import com.github.yeecode.matrixauth.server.constant.Sentence;
import com.github.yeecode.matrixauth.server.dao.CacheDao;
import com.github.yeecode.matrixauth.server.model.CacheModel;
import com.github.yeecode.matrixauth.server.util.Result;
import com.github.yeecode.matrixauth.server.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CacheBusiness {
    @Autowired
    private CacheDao cacheDao;

    public Result add(CacheModel cacheModel) {
        try {
            cacheDao.add(cacheModel);
            return ResultUtil.getSuccessResult();
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResultUtil.getFailResult(Sentence.UNKNOWN_ERROR, ex.getMessage());
        }
    }

    public Result deleteByName(String name) {
        try {
            cacheDao.deleteByName(name);
            return ResultUtil.getSuccessResult();
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResultUtil.getFailResult(Sentence.UNKNOWN_ERROR, ex.getMessage());
        }
    }

    public Result updateByName(CacheModel cacheModel) {
        try {
            cacheDao.updateByName(cacheModel);
            return ResultUtil.getSuccessResult();
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResultUtil.getFailResult(Sentence.UNKNOWN_ERROR, ex.getMessage());
        }
    }

    public Result queryAll() {
        try {
            List<CacheModel> cacheModelList = cacheDao.queryAll();
            return ResultUtil.getSuccessResult(cacheModelList);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResultUtil.getFailResult(Sentence.UNKNOWN_ERROR, ex.getMessage());
        }
    }

    public Result queryByName(String name) {
        try {
            CacheModel cacheModel = cacheDao.queryByName(name);
            return ResultUtil.getSuccessResult(cacheModel);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResultUtil.getFailResult(Sentence.UNKNOWN_ERROR, ex.getMessage());
        }
    }
}
