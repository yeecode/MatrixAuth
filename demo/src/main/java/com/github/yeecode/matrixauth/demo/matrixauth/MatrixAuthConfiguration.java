package com.github.yeecode.matrixauth.demo.matrixauth;


import com.github.yeecode.matrixauth.client.MatrixAuthClient;
import com.github.yeecode.matrixauth.client.setup.MatrixAuthSetup;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Set;

@Component
public class MatrixAuthConfiguration implements MatrixAuthSetup {
    @Override
    public String getCurrentUserKey() {
        return "user01";
    }

    @Override
    public Boolean handleLocalPerm(Method method, Object[] args, String[] permissionsInAnnotation, Set<String> permissionsUserOwned) {
        System.out.println("****************************************");
        System.out.println("Here you can make more detailed judgments with business information. \n" +
                "The information that may be needed in the judgment and how to obtain it are as follows:");
        System.out.println("****************************************");
        System.out.println("methodName:" + method.getName());
        System.out.println("methodClassName:" + method.getDeclaringClass().getName());
        System.out.println("methodArgTypes:");
        for (Parameter parameter : method.getParameters()) {
            System.out.println("--" + parameter.getType());
        }
        System.out.println("methodArgs:");
        for (Object parameter : args) {
            System.out.println("--" + parameter.toString());
        }
        System.out.println("annotationPermissions:");
        for (String perm : permissionsInAnnotation) {
            System.out.println("--" + perm);
        }
        System.out.println("ownedPermissions:" + permissionsUserOwned);
        System.out.println("****************************************");
        System.out.println("After the judgment, if it returns true, it means that the current method is allowed to be executed; \n" +
                "if it returns false, it means that it is refused to execute the current method.");
        System.out.println("****************************************");
        return true;
    }
}
