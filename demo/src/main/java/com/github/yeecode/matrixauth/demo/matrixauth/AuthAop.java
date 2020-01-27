package com.github.yeecode.matrixauth.demo.matrixauth;

import com.github.yeecode.matrixauth.client.aop.MatrixAuthAop;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuthAop {
    @Autowired
    private MatrixAuthAop matrixAuthAop;

    @Pointcut("execution(public * com.github.yeecode.matrixauth.demo.controller.*.*(..))")
    public void inController() {
    }

    @Around("inController()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        return matrixAuthAop.judgeAuth(joinPoint);
    }
}
