package com.github.yeecode.matrixauth.client.setup;

import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Set;

@Component
public interface MatrixAuthSetup {
    String getCurrentUserKey();

    Boolean handleLocalPerm(Method method, Object[] args,String[] permissionsInAnnotation, Set<String> permissionsUserOwned);
}
