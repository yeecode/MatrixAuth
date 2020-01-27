package com.github.yeecode.matrixauth.server.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class MainController {
    @RequestMapping("")
    public String main() {
        return "<div>MatrixAuthServer starts successfully! </div>" +
                "<div>You can learn more about using MatrixAuth at the following URL: </div>" +
                "<div><a href='http://matrixauth.top'>http://matrixauth.top</a></div>";
    }
}
