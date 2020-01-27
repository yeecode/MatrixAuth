package com.github.yeecode.matrixauth.client.aop;

import com.github.yeecode.matrixauth.client.annotation.LocalPerm;
import com.github.yeecode.matrixauth.client.annotation.Perm;
import com.github.yeecode.matrixauth.client.constant.Sentence;
import com.github.yeecode.matrixauth.client.permission.PermissionHandler;
import com.github.yeecode.matrixauth.client.setup.MatrixAuthSetup;
import com.github.yeecode.matrixauth.client.util.ResultUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;

@Component
public class MatrixAuthAop {
    @Autowired
    private PermissionHandler permissionHandler;
    @Autowired(required = false)
    private MatrixAuthSetup matrixAuthSetup;

    public Object judgeAuth(ProceedingJoinPoint point) throws Throwable {
        Signature signature = point.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        if (!method.isAnnotationPresent(Perm.class) && !method.isAnnotationPresent(LocalPerm.class)) {
            return point.proceed();
        } else {
            Boolean permPassFlag = false;
            Boolean localPermPassFlag = false;
            if (method.isAnnotationPresent(Perm.class)) {
                permPassFlag = judgePerm(method);
            }
            if (method.isAnnotationPresent(LocalPerm.class)) {
                localPermPassFlag = judgeLocalPerm(method, point.getArgs());
            }

            if (permPassFlag || localPermPassFlag) {
                return point.proceed();
            } else {
                return ResultUtil.getFailResult(Sentence.NO_PERMISSION);
            }
        }
    }

    private Boolean judgePerm(Method method) {
        String userKey = matrixAuthSetup.getCurrentUserKey();
        Set<String> permSet = permissionHandler.getPermissionSet(userKey);
        Perm permString = method.getAnnotation(Perm.class);
        String[] permsInAnnotation = permString.value();
        permSet.retainAll(Arrays.asList(permsInAnnotation));// 交集
        return !CollectionUtils.isEmpty(permSet);
    }

    private Boolean judgeLocalPerm(Method method, Object[] args) {
        String userKey = matrixAuthSetup.getCurrentUserKey();
        Set<String> permSet = permissionHandler.getPermissionSet(userKey);
        LocalPerm permString = method.getAnnotation(LocalPerm.class);
        String[] permsInAnnotation = permString.value();
        return matrixAuthSetup.handleLocalPerm(method, args,permsInAnnotation,permSet);
    }

}
