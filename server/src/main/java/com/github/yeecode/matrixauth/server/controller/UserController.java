package com.github.yeecode.matrixauth.server.controller;

import com.github.yeecode.matrixauth.server.business.UserBusiness;
import com.github.yeecode.matrixauth.server.model.UserModel;
import com.github.yeecode.matrixauth.server.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

@Validated
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserBusiness userBusiness;

    @RequestMapping("/add")
    public Result add(@NotBlank String appName,
                      @NotBlank String userKey,
                      String userName,
                      String appToken) {
        return userBusiness.add(appToken, new UserModel(appName, userKey, userName));
    }

    @RequestMapping("/deleteByKeyAndAppName")
    public Result deleteByKeyAndAppName(@NotBlank String appName,
                                        @NotBlank String userKey,
                                        String appToken) {
        return userBusiness.deleteByKeyAndAppName(appToken, new UserModel(null, appName, userKey, null));
    }

    @RequestMapping("/updateByKeyAndAppName")
    public Result updateByKeyAndAppName(@NotBlank String appName,
                                        @NotBlank String userKey,
                                        String userName,
                                        String appToken) {
        return userBusiness.updateByKeyAndAppName(appToken, new UserModel(null, appName, userKey, userName));
    }

    @RequestMapping("/queryByAppName")
    public Result queryByAppName(@NotBlank String appName,
                                 String appToken) {
        return userBusiness.queryByAppName(appToken, appName);
    }

    @RequestMapping("/queryByKeyAndAppName")
    public Result queryByKeyAndAppName(@NotBlank String appName,
                                       @NotBlank String userKey,
                                       String appToken) {
        return userBusiness.queryByKeyAndAppName(appToken, new UserModel(null, appName, userKey, null));
    }
}
