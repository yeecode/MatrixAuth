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
                      @NotBlank String permissionName,
                      @NotBlank String permissionCode,
                      String description,
                      String appToken) {
        return permissionBusiness.add(appToken, new PermissionModel(appName, permissionName, permissionCode, description));
    }

    @RequestMapping("/deleteByIdAndAppName")
    public Result deleteByIdAndAppName(@NotBlank String appName,
                                       @NotNull Integer permissionId,
                                       String appToken) {
        return permissionBusiness.deleteByIdAndAppName(appToken, new PermissionModel(permissionId, appName, null, null, null));
    }

    @RequestMapping("/updateByIdAndAppName")
    public Result updateByIdAndAppName(@NotBlank String appName,
                                       @NotNull Integer permissionId,
                                       @NotBlank String permissionName,
                                       String permissionCode,
                                       String description,
                                       String appToken) {
        return permissionBusiness.updateByIdAndAppName(appToken, new PermissionModel(permissionId, appName, permissionName, permissionCode, description));
    }

    @RequestMapping("/queryByAppName")
    public Result queryByAppName(@NotBlank String appName,
                                 String appToken) {
        return permissionBusiness.queryByAppName(appToken, appName);
    }

    @RequestMapping("/queryByIdAndAppName")
    public Result queryByIdAndAppName(@NotBlank String appName,
                                      @NotNull Integer permissionId,
                                      String appToken) {
        return permissionBusiness.queryByIdAndAppName(appToken, new PermissionModel(permissionId, appName, null, null, null));
    }
}
