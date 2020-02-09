package com.github.yeecode.matrixauth.server.controller;

import com.github.yeecode.matrixauth.server.business.AuthBusiness;
import com.github.yeecode.matrixauth.server.constant.RequestSource;
import com.github.yeecode.matrixauth.server.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Validated
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthBusiness authBusiness;

    @RequestMapping("/addUserXRole")
    public Result addUserXRole(@NotBlank String appName,
                               @NotBlank String userKey,
                               @NotNull String roleName,
                               String requestSource,
                               String appToken) {
        RequestSource rs;
        if (requestSource == null) {
            rs = RequestSource.Interface;
        } else {
            rs = RequestSource.valueOf(requestSource);
        }
        return authBusiness.addUserXRole(appToken, appName, userKey, roleName, rs);
    }

    @RequestMapping("/deleteUserXRole")
    public Result deleteUserXRole(@NotBlank String appName,
                                  @NotBlank String userKey,
                                  @NotNull String roleName,
                                  String requestSource,
                                  String appToken) {
        RequestSource rs;
        if (requestSource == null) {
            rs = RequestSource.Interface;
        } else {
            rs = RequestSource.valueOf(requestSource);
        }
        return authBusiness.deleteUserXRole(appToken, appName, userKey, roleName, rs);
    }

    @RequestMapping("/addRoleXPermission")
    public Result addRoleXPermission(@NotBlank String appName,
                                     @NotNull String roleName,
                                     @NotNull String permKey,
                                     String appToken) {
        return authBusiness.addRoleXPermission(appToken, appName, roleName, permKey);
    }

    @RequestMapping("/deleteRoleXPermission")
    public Result deleteRoleXPermission(@NotBlank String appName,
                                     @NotNull String roleName,
                                     @NotNull String permKey,
                                     String appToken) {
        return authBusiness.deleteRoleXPermission(appToken, appName, roleName, permKey);
    }

    @RequestMapping("/fastQueryPermissionCodesByUserKey")
    public Result fastQueryPermissionCodesByUserKey(@NotBlank String appName,
                                                    @NotBlank String userKey) {
        return authBusiness.fastQueryPermissionCodesByUserKey(appName, userKey);
    }
}
