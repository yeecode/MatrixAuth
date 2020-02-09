package com.github.yeecode.matrixauth.demo.controller;

import com.github.yeecode.matrixauth.client.MatrixAuthClient;
import com.github.yeecode.matrixauth.client.annotation.LocalPerm;
import com.github.yeecode.matrixauth.client.annotation.Perm;
import com.github.yeecode.matrixauth.client.util.Result;
import com.github.yeecode.matrixauth.client.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class MainController {
    @Autowired
    private MatrixAuthClient matrixAuthClient;

    @RequestMapping("/01")
    @Perm({"PM_01"})
    public Result interface01() {
        return ResultUtil.getSuccessResult("Interface 01 operated successfully");
    }

    @RequestMapping("/02")
    @Perm({"PM_02"})
    public Result interface02() {
        return ResultUtil.getSuccessResult("Interface 02 operated successfully");
    }

    @RequestMapping("/03")
    @LocalPerm({"PM_02"})
    public Result interface03(String value01, Integer value02) {
        return ResultUtil.getSuccessResult("Interface 02 operated successfully");
    }

    @RequestMapping("/addUserXRole")
    public Result addUserXRole() {
        return matrixAuthClient.addUserXRole("u01", "role01");
    }

    @RequestMapping("/deleteUserXRole")
    public Result deleteUserXRole() {
        return matrixAuthClient.deleteUserXRole("u01", "role01");
    }

    @RequestMapping("/addUser")
    public Result addUser() {
        return matrixAuthClient.addUser("u01", "Yee");
    }

    @RequestMapping("/deleteUser")
    public Result deleteUser() {
        return matrixAuthClient.deleteUser("u01");
    }

    @RequestMapping("/updateUser")
    public Result updateUser() {
        return matrixAuthClient.updateUser("u01", "yee");
    }
}
