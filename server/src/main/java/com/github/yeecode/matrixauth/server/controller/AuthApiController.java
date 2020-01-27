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
@RequestMapping("/authApi")
public class AuthApiController {
    @Autowired
    private AuthBusiness authBusiness;

    @RequestMapping("/addUserXRole")
    public Result addUserXRole(@NotBlank String appName,
                               @NotBlank String userKey,
                               @NotNull Integer roleId,
                               String appToken) {
        return authBusiness.addUserXRole(appToken, appName, userKey, roleId, RequestSource.BusinessApp);
    }

    @RequestMapping("/deleteUserXRole")
    public Result deleteUserXRole(@NotBlank String appName,
                                  @NotBlank String userKey,
                                  @NotNull Integer roleId,
                                  String appToken) {
        return authBusiness.deleteUserXRole(appToken, appName, userKey, roleId, RequestSource.BusinessApp);
    }
}
