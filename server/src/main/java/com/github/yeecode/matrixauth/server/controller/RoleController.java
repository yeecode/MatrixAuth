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
                      Integer parentId,
                      String description,
                      @Pattern(regexp = "InterfaceControlled|BusinessAppControlled|InterfaceAndBusinessAppControlled") String type,
                      String appToken) {
        return roleBusiness.add(appToken, new RoleModel(appName, roleName, parentId, description, type));
    }

    @RequestMapping("/deleteByIdAndAppName")
    public Result deleteByIdAndAppName(@NotBlank String appName,
                                       @NotNull Integer roleId,
                                       String appToken) {
        return roleBusiness.deleteByIdAndAppName(appToken, new RoleModel(roleId, appName, null, null, null, null));
    }

    @RequestMapping("/updateByIdAndAppName")
    public Result updateByIdAndAppName(@NotBlank String appName,
                                       @NotNull Integer roleId,
                                       String roleName,
                                       Integer parentId,
                                       String description,
                                       @Pattern(regexp = "InterfaceControlled|BusinessAppControlled|InterfaceAndBusinessAppControlled") String type,
                                       String appToken) {
        return roleBusiness.updateByIdAndAppName(appToken, new RoleModel(roleId, appName, roleName, parentId, description, type));
    }

    @RequestMapping("/queryByAppName")
    public Result queryByAppName(@NotBlank String appName,
                                 String appToken) {
        return roleBusiness.queryByAppName(appToken, appName);
    }

    @RequestMapping("/queryByIdAndAppName")
    public Result queryByIdAndAppName(@NotBlank String appName,
                                      @NotNull Integer roleId,
                                      String appToken) {
        return roleBusiness.queryByIdAndAppName(appToken, new RoleModel(roleId, appName, null, null, null, null));
    }
}
