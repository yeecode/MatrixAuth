package com.github.yeecode.matrixauth.server.controller;

import com.github.yeecode.matrixauth.server.business.RoleBusiness;
import com.github.yeecode.matrixauth.server.model.RoleModel;
import com.github.yeecode.matrixauth.server.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Validated
@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleBusiness roleBusiness;

    @RequestMapping("/add")
    public Result add(@NotBlank String appName,
                      @NotBlank String roleName,
                      String description,
                      @Pattern(regexp = "InterfaceControlled|BusinessAppControlled|InterfaceAndBusinessAppControlled") String type,
                      String appToken) {
        return roleBusiness.add(appToken, new RoleModel(appName, roleName, description, type));
    }

    @RequestMapping("/deleteByName")
    public Result deleteByName(@NotBlank String appName,
                                       @NotNull String roleName,
                                       String appToken) {
        return roleBusiness.deleteByName(appToken, new RoleModel(appName, roleName, null, null));
    }

    @RequestMapping("/updateByName")
    public Result updateByName(@NotBlank String appName,
                                       String roleName,
                                       String description,
                                       @Pattern(regexp = "InterfaceControlled|BusinessAppControlled|InterfaceAndBusinessAppControlled") String type,
                                       String appToken) {
        return roleBusiness.updateByName(appToken, new RoleModel(appName, roleName, description, type));
    }

    @RequestMapping("/queryByAppName")
    public Result queryBy(@NotBlank String appName,
                                 String appToken) {
        return roleBusiness.queryByAppName(appToken, appName);
    }

    @RequestMapping("/queryByName")
    public Result queryByName(@NotBlank String appName,
                                      @NotNull String roleName,
                                      String appToken) {
        return roleBusiness.queryByName(appToken, new RoleModel(appName, roleName, null, null));
    }
}
