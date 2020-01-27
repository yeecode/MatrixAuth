package com.github.yeecode.matrixauth.server.controller;

import com.github.yeecode.matrixauth.server.business.DataSourceBusiness;
import com.github.yeecode.matrixauth.server.constant.Sentence;
import com.github.yeecode.matrixauth.server.model.DataSourceModel;
import com.github.yeecode.matrixauth.server.util.Result;
import com.github.yeecode.matrixauth.server.util.ResultUtil;
import com.github.yeecode.matrixauth.server.util.TokenValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

@Validated
@RestController
@RequestMapping("/datasource")
public class DataSourceController {
    @Autowired
    private TokenValidator tokenValidator;
    @Autowired
    private DataSourceBusiness dataSourceBusiness;

    @RequestMapping("/add")
    public Result add(@NotBlank String dataSourceName,
                      @NotBlank String dataSourceUrl,
                      @NotBlank String dataSourceDriver,
                      String dataSourceUserName,
                      String dataSourcePassword,
                      String adminToken) {
        if (tokenValidator.checkAdminToken(adminToken)) {
            return dataSourceBusiness.add(new DataSourceModel(dataSourceName, dataSourceUrl, dataSourceDriver, dataSourceUserName, dataSourcePassword));
        } else {
            return ResultUtil.getFailResult(Sentence.ILLEGAL_ADMIN_TOKEN);
        }
    }

    @RequestMapping("/deleteByName")
    public Result deleteByName(@NotBlank String dataSourceName,
                               String adminToken) {
        if (tokenValidator.checkAdminToken(adminToken)) {
            return dataSourceBusiness.deleteByName(dataSourceName);
        } else {
            return ResultUtil.getFailResult(Sentence.ILLEGAL_ADMIN_TOKEN);
        }
    }

    @RequestMapping("/update")
    public Result update(@NotBlank String dataSourceName,
                         @NotBlank String dataSourceUrl,
                         @NotBlank String dataSourceDriver,
                         String dataSourceUserName,
                         String dataSourcePassword,
                         String adminToken) {
        if (tokenValidator.checkAdminToken(adminToken)) {
            return dataSourceBusiness.updateByName(new DataSourceModel(dataSourceName, dataSourceUrl, dataSourceDriver, dataSourceUserName, dataSourcePassword));
        } else {
            return ResultUtil.getFailResult(Sentence.ILLEGAL_ADMIN_TOKEN);
        }
    }

    @RequestMapping("/queryAll")
    public Result update(String adminToken) {
        if (tokenValidator.checkAdminToken(adminToken)) {
            return dataSourceBusiness.queryAll();
        } else {
            return ResultUtil.getFailResult(Sentence.ILLEGAL_ADMIN_TOKEN);
        }
    }

    @RequestMapping("/queryByName")
    public Result queryByName(@NotBlank String dataSourceName,
                              String adminToken) {
        if (tokenValidator.checkAdminToken(adminToken)) {
            return dataSourceBusiness.queryByName(dataSourceName);
        } else {
            return ResultUtil.getFailResult(Sentence.ILLEGAL_ADMIN_TOKEN);
        }
    }
}
