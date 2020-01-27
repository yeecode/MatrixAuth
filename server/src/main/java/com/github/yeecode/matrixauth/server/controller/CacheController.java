package com.github.yeecode.matrixauth.server.controller;

import com.github.yeecode.matrixauth.server.business.CacheBusiness;
import com.github.yeecode.matrixauth.server.constant.Sentence;
import com.github.yeecode.matrixauth.server.model.CacheModel;
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
@RequestMapping("/cache")
public class CacheController {
    @Autowired
    private TokenValidator tokenValidator;
    @Autowired
    private CacheBusiness cacheBusiness;

    @RequestMapping("/add")
    public Result add(@NotBlank String cacheName,
                      @NotBlank String cacheUrl,
                      String cachePassword,
                      String adminToken) {
        if (tokenValidator.checkAdminToken(adminToken)) {
            return cacheBusiness.add(new CacheModel(cacheName, cacheUrl, cachePassword));
        } else {
            return ResultUtil.getFailResult(Sentence.ILLEGAL_ADMIN_TOKEN);
        }
    }

    @RequestMapping("/deleteByName")
    public Result deleteByName(@NotBlank String cacheName,
                               String adminToken) {
        if (tokenValidator.checkAdminToken(adminToken)) {
            return cacheBusiness.deleteByName(cacheName);
        } else {
            return ResultUtil.getFailResult(Sentence.ILLEGAL_ADMIN_TOKEN);
        }
    }

    @RequestMapping("/update")
    public Result update(@NotBlank String cacheName,
                         @NotBlank String cacheUrl,
                         String cachePassword,
                         String adminToken) {
        if (tokenValidator.checkAdminToken(adminToken)) {
            return cacheBusiness.updateByName(new CacheModel(cacheName, cacheUrl, cachePassword));
        } else {
            return ResultUtil.getFailResult(Sentence.ILLEGAL_ADMIN_TOKEN);
        }
    }

    @RequestMapping("/queryAll")
    public Result update(String adminToken) {
        if (tokenValidator.checkAdminToken(adminToken)) {
            return cacheBusiness.queryAll();
        } else {
            return ResultUtil.getFailResult(Sentence.ILLEGAL_ADMIN_TOKEN);
        }
    }

    @RequestMapping("/queryByName")
    public Result queryByName(@NotBlank String cacheName,
                              String adminToken) {
        if (tokenValidator.checkAdminToken(adminToken)) {
            return cacheBusiness.queryByName(cacheName);
        } else {
            return ResultUtil.getFailResult(Sentence.ILLEGAL_ADMIN_TOKEN);
        }
    }
}
