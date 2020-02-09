package com.github.yeecode.matrixauth.server.controller;

import com.github.yeecode.matrixauth.server.business.PermissionBusiness;
import com.github.yeecode.matrixauth.server.model.PermissionModel;
import com.github.yeecode.matrixauth.server.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Validated
@RestController
@RequestMapping("/permission")
public class PermissionController {
    @Autowired
    private PermissionBusiness permissionBusiness;

    @RequestMapping("/add")
    public Result add(@NotBlank String appName,
                      @NotBlank String permKey,
                      @NotBlank String name,
                      String description,
                      String appToken) {
        return permissionBusiness.add(appToken, new PermissionModel(appName, permKey, name, description));
    }

    @RequestMapping("/deleteByKey")
    public Result deleteByKey(@NotBlank String appName,
                              @NotNull String permKey,
                              String appToken) {
        return permissionBusiness.deleteByKey(appToken, new PermissionModel(appName, permKey, null, null));
    }

    @RequestMapping("/updateByKey")
    public Result updateByKey(@NotBlank String appName,
                              @NotNull String permKey,
                              @NotBlank String name,
                              String description,
                              String appToken) {
        return permissionBusiness.updateByKey(appToken, new PermissionModel(appName, permKey, name, description));
    }

    @RequestMapping("/queryByAppName")
    public Result queryByAppName(@NotBlank String appName,
                                 String appToken) {
        return permissionBusiness.queryByAppName(appToken, appName);
    }

    @RequestMapping("/queryByKey")
    public Result queryByKey(@NotBlank String appName,
                             @NotNull String permKey,
                             String appToken) {
        return permissionBusiness.queryByKey(appToken, new PermissionModel(appName, permKey, null, null));
    }
}
