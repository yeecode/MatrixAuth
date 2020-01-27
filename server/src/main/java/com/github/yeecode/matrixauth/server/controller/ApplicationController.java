package com.github.yeecode.matrixauth.server.controller;

import com.github.yeecode.matrixauth.server.business.ApplicationBusiness;
import com.github.yeecode.matrixauth.server.constant.Sentence;
import com.github.yeecode.matrixauth.server.model.ApplicationModel;
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
@RequestMapping("/application")
public class ApplicationController {
    @Autowired
    private TokenValidator tokenValidator;
    @Autowired
    private ApplicationBusiness applicationBusiness;

    @RequestMapping("/add")
    public Result add(@NotBlank String appName,
                      String appToken,
                      String dataSourceName,
                      String cacheName,
                      String adminToken) {
        if (tokenValidator.checkAdminToken(adminToken)) {
            return applicationBusiness.add(new ApplicationModel(appName, appToken, dataSourceName, cacheName));
        } else {
            return ResultUtil.getFailResult(Sentence.ILLEGAL_ADMIN_TOKEN);
        }
    }

    @RequestMapping("/deleteByName")
    public Result deleteByName(@NotBlank String appName,
                               String adminToken) {
        if (tokenValidator.checkAdminToken(adminToken)) {
            return applicationBusiness.deleteByName(appName);
        } else {
            return ResultUtil.getFailResult(Sentence.ILLEGAL_ADMIN_TOKEN);
        }
    }

    @RequestMapping("/update")
    public Result update(@NotBlank String appName,
                         String appToken,
                         @NotBlank String dataSourceName,
                         String cacheName,
                         String adminToken) {
        if (tokenValidator.checkAdminToken(adminToken)) {
            return applicationBusiness.updateByName(new ApplicationModel(appName, appToken, dataSourceName, cacheName));
        } else {
            return ResultUtil.getFailResult(Sentence.ILLEGAL_ADMIN_TOKEN);
        }
    }

    @RequestMapping("/queryAll")
    public Result update(String adminToken) {
        if (tokenValidator.checkAdminToken(adminToken)) {
            return applicationBusiness.queryAll();
        } else {
            return ResultUtil.getFailResult(Sentence.ILLEGAL_ADMIN_TOKEN);
        }
    }

    @RequestMapping("/queryByName")
    public Result queryByName(@NotBlank String appName,
                              String adminToken) {
        if (tokenValidator.checkAdminToken(adminToken)) {
            return applicationBusiness.queryByName(appName);
        } else {
            return ResultUtil.getFailResult(Sentence.ILLEGAL_ADMIN_TOKEN);
        }
    }
}
