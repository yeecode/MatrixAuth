package com.github.yeecode.matrixauth.server.aop;

import com.github.yeecode.dynamicdatasource.DynamicDataSource;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DatabaseSwitcher {
    @Autowired
    private DynamicDataSource dynamicDataSource;

    @Pointcut("execution(public * com.github.yeecode.matrixauth.server.controller.*.*(..))")
    public void inController() {
    }

    @Before("inController()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        dynamicDataSource.switchDefaultDataSource();
    }
}
