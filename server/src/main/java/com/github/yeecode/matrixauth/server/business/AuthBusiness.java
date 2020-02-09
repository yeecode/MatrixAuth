package com.github.yeecode.matrixauth.server.business;

import com.github.yeecode.matrixauth.server.cacheclient.CacheClient;
import com.github.yeecode.matrixauth.server.constant.RequestSource;
import com.github.yeecode.matrixauth.server.constant.RoleType;
import com.github.yeecode.matrixauth.server.constant.Sentence;
import com.github.yeecode.matrixauth.server.dao.*;
import com.github.yeecode.matrixauth.server.model.ApplicationModel;
import com.github.yeecode.matrixauth.server.model.PermissionModel;
import com.github.yeecode.matrixauth.server.model.RoleModel;
import com.github.yeecode.matrixauth.server.model.UserModel;
import com.github.yeecode.matrixauth.server.tenant.TenantSwitcher;
import com.github.yeecode.matrixauth.server.util.FullUserKeyGenerator;
import com.github.yeecode.matrixauth.server.util.Result;
import com.github.yeecode.matrixauth.server.util.ResultUtil;
import com.github.yeecode.matrixauth.server.util.TokenValidator;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class AuthBusiness {
    @Autowired
    private UserDao userDao;
    @Autowired
    private PermissionDao permissionDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private TokenValidator tokenValidator;
    @Autowired
    private ApplicationDao applicationDao;
    @Autowired
    private UserXRoleDao userXRoleDao;
    @Autowired
    private RoleXPermissionDao roleXPermissionDao;
    @Autowired
    private AuthDao authDao;
    @Autowired
    private TenantSwitcher tenantSwitcher;
    @Autowired
    private UserXPermissionBusiness userXPermissionBusiness;
    @Autowired
    private ApplicationContext context;
    private AuthBusiness proxySelf;

    @PostConstruct
    public void setSelf() {
        proxySelf = context.getBean(AuthBusiness.class);
    }

    public Result addUserXRole(String appToken, String appName, String userKey, String roleName, RequestSource requestSource) {
        try {
            ApplicationModel applicationModel = applicationDao.queryByName(appName);
            if (applicationModel == null) {
                return ResultUtil.getFailResult(Sentence.NONE_APP_FOUND);
            }
            CacheClient cacheClient = tenantSwitcher.switchByApplication(applicationModel);
            UserModel userModel = userDao.queryByKey(userKey,appName);
            RoleModel roleModel = roleDao.queryByName(roleName,appName);
            if (userModel == null || roleModel == null) {
                return ResultUtil.getFailResult(Sentence.NONE_OBJECT_FOUND);
            }

            if (RoleType.valueOf(roleModel.getType()).isSupportedRequestSources(requestSource)) {
                if (tokenValidator.checkApplicationToken(appToken, applicationModel)) {
                    Integer count = proxySelf.addUserXRole(appName, userKey, roleName, cacheClient);
                    return ResultUtil.getSuccessResult(count);
                } else {
                    return ResultUtil.getFailResult(Sentence.ILLEGAL_APP_TOKEN);
                }
            } else {
                return ResultUtil.getFailResult(Sentence.REQUEST_SOURCE_CONFLICT_ROLE_TYPE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResultUtil.getFailResult(Sentence.UNKNOWN_ERROR, ex.getMessage());
        }
    }

    public Result deleteUserXRole(String appToken, String appName, String userKey, String roleName, RequestSource requestSource) {
        try {
            ApplicationModel applicationModel = applicationDao.queryByName(appName);
            if (applicationModel == null) {
                return ResultUtil.getFailResult(Sentence.NONE_APP_FOUND);
            }
            CacheClient cacheClient = tenantSwitcher.switchByApplication(applicationModel);
            UserModel userModel = userDao.queryByKey(userKey,appName);
            RoleModel roleModel = roleDao.queryByName(roleName,appName);
            if (userModel == null || roleModel == null) {
                return ResultUtil.getFailResult(Sentence.NONE_OBJECT_FOUND);
            }

            if (RoleType.valueOf(roleModel.getType()).isSupportedRequestSources(requestSource)) {
                if (tokenValidator.checkApplicationToken(appToken, applicationModel)) {
                    Integer count = proxySelf.deleteUserXRole(appName, userKey, roleName, cacheClient);
                    return ResultUtil.getSuccessResult(count);
                } else {
                    return ResultUtil.getFailResult(Sentence.ILLEGAL_APP_TOKEN);
                }
            } else {
                return ResultUtil.getFailResult(Sentence.REQUEST_SOURCE_CONFLICT_ROLE_TYPE);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            return ResultUtil.getFailResult(Sentence.UNKNOWN_ERROR, ex.getMessage());
        }
    }

    public Result addRoleXPermission(String appToken, String appName, String roleName, String permKey) {
        try {
            ApplicationModel applicationModel = applicationDao.queryByName(appName);
            if (applicationModel == null) {
                return ResultUtil.getFailResult(Sentence.NONE_APP_FOUND);
            }

            if (tokenValidator.checkApplicationToken(appToken, applicationModel)) {
                CacheClient cacheClient = tenantSwitcher.switchByApplication(applicationModel);
                RoleModel roleModel = roleDao.queryByName(roleName,appName);
                PermissionModel permissionModel = permissionDao.queryByKey(permKey,appName);
                if (roleModel == null || permissionModel == null) {
                    return ResultUtil.getFailResult(Sentence.NONE_OBJECT_FOUND);
                }
                Integer count = proxySelf.addRoleXPermission(appName, roleName, permKey, cacheClient);
                return ResultUtil.getSuccessResult(count);
            } else {
                return ResultUtil.getFailResult(Sentence.ILLEGAL_APP_TOKEN);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResultUtil.getFailResult(Sentence.UNKNOWN_ERROR, ex.getMessage());
        }
    }

    public Result deleteRoleXPermission(String appToken, String appName, String roleName, String permKey) {
        try {
            ApplicationModel applicationModel = applicationDao.queryByName(appName);
            if (applicationModel == null) {
                return ResultUtil.getFailResult(Sentence.NONE_APP_FOUND);
            }

            if (tokenValidator.checkApplicationToken(appToken, applicationModel)) {
                CacheClient cacheClient = tenantSwitcher.switchByApplication(applicationModel);
                RoleModel roleModel = roleDao.queryByName(roleName,appName);
                PermissionModel permissionModel = permissionDao.queryByKey(permKey,appName);
                if (roleModel == null || permissionModel == null) {
                    return ResultUtil.getFailResult(Sentence.NONE_OBJECT_FOUND);
                }
                Integer count = proxySelf.deleteRoleXPermission(appName, roleName, permKey, cacheClient);
                return ResultUtil.getSuccessResult(count);
            } else {
                return ResultUtil.getFailResult(Sentence.ILLEGAL_APP_TOKEN);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResultUtil.getFailResult(Sentence.UNKNOWN_ERROR, ex.getMessage());
        }
    }

    public Result fastQueryPermissionCodesByUserKey(String appName, String userKey) {
        try {
            ApplicationModel applicationModel = applicationDao.queryByName(appName);
            if (applicationModel == null) {
                return ResultUtil.getFailResult(Sentence.NONE_APP_FOUND);
            }
            tenantSwitcher.switchByApplication(applicationModel);
            String fullUserKey = FullUserKeyGenerator.getFullUserKey(appName, userKey);
            String permissionKeyListString = authDao.fastQueryPermissionCodesByFullUserKey(fullUserKey);
            return ResultUtil.getSuccessResult(permissionKeyListString.split(";"));
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResultUtil.getFailResult(Sentence.UNKNOWN_ERROR, ex.getMessage());
        }
    }

    @Transactional
    public Integer addUserXRole(String appName, String userKey, String roleName, CacheClient cacheClient) {
        Integer count = userXRoleDao.add(appName,userKey, roleName);
        List<String> affectedUserKeys = new ArrayList<>();
        affectedUserKeys.add(userKey);
        refreshUserXPermissions(appName,affectedUserKeys, cacheClient);
        return count;
    }

    @Transactional
    public Integer deleteUserXRole(String appName, String userKey, String roleName, CacheClient cacheClient) {
        Integer count = userXRoleDao.delete(appName,userKey, roleName);
        List<String> affectedUserKeys = new ArrayList<>();
        affectedUserKeys.add(userKey);
        refreshUserXPermissions(appName,affectedUserKeys, cacheClient);
        return count;
    }

    @Transactional
    public Integer addRoleXPermission(String appName, String roleName, String permKey, CacheClient cacheClient) {
        Integer count = roleXPermissionDao.add(appName, roleName, permKey);
        List<String> affectedUserKeys = authDao.queryUserKeyByRoleName(roleName, appName);
        refreshUserXPermissions(appName, affectedUserKeys, cacheClient);
        return count;
    }

    @Transactional
    public Integer deleteRoleXPermission(String appName, String roleName, String permKey, CacheClient cacheClient) {
        List<String> affectedUserKeys = authDao.queryUserKeyByRoleName(roleName, appName);
        Integer count = roleXPermissionDao.delete(appName, roleName, permKey);
        refreshUserXPermissions(appName, affectedUserKeys, cacheClient);
        return count;
    }

    private void refreshUserXPermissions(String appName, List<String> affectedUserKeys, CacheClient cacheClient) {
        for (String userKey : affectedUserKeys) {
            String permissionKeys = StringUtils.join(new HashSet<>(authDao.queryPermKeysByUserKey(appName,userKey)), ';');
            String fullUserKey = FullUserKeyGenerator.getFullUserKey(appName,userKey);
            userXPermissionBusiness.addOrUpdatePermissionsByFullUserKey(fullUserKey, permissionKeys);
            cacheClient.addOrUpdate(fullUserKey, permissionKeys);
        }
    }
}
