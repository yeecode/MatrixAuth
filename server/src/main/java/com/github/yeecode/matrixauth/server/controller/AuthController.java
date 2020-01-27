package com.github.yeecode.matrixauth.server.controller;

import com.github.yeecode.matrixauth.server.business.AuthBusiness;
import com.github.yeecode.matrixauth.server.constant.RequestSource;
import com.github.yeecode.matrixauth.server.constant.RoleType;
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
                               @NotNull Integer roleId,
                               String appToken) {
        return authBusiness.addUserXRole(appToken, appName, userKey, roleId, RequestSource.Interface);
    }

    @RequestMapping("/deleteUserXRole")
    public Result deleteUserXRole(@NotBlank String appName,
                                  @NotBlank String userKey,
                                  @NotNull Integer roleId,
                                  String appToken) {
        return authBusiness.deleteUserXRole(appToken, appName, userKey, roleId, RequestSource.Interface);
    }

    @RequestMapping("/addRoleXPermission")
    public Result addRoleXPermission(@NotBlank String appName,
                                     @NotNull Integer roleId,
                                     @NotNull Integer permissionId,
                                     String appToken) {
        return authBusiness.addRoleXPermission(appToken, appName, roleId, permissionId);
    }

    @RequestMapping("/delRoleXPermission")
    public Result delRoleXPermission(@NotBlank String appName,
                                     @NotNull Integer roleId,
                                     @NotNull Integer permissionId,
                                     String appToken) {
        return authBusiness.delRoleXPermission(appToken, appName, roleId, permissionId);
    }

    @RequestMapping("/fastQueryPermissionCodesByUserKey")
    public Result fastQueryPermissionCodesByUserKey(@NotBlank String appName,
                                                    @NotBlank String userKey) {
        return authBusiness.fastQueryPermissionCodesByUserKey(appName, userKey);
    }
}
