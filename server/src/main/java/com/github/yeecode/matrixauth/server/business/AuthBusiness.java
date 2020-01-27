package com.github.yeecode.matrixauth.server.business;

import com.github.yeecode.matrixauth.server.cacheclient.CacheClient;
import com.github.yeecode.matrixauth.server.constant.RequestSource;
import com.github.yeecode.matrixauth.server.constant.RoleType;
import com.github.yeecode.matrixauth.server.constant.Sentence;
import com.github.yeecode.matrixauth.server.dao.ApplicationDao;
import com.github.yeecode.matrixauth.server.dao.AuthDao;
import com.github.yeecode.matrixauth.server.dao.RoleDao;
import com.github.yeecode.matrixauth.server.dao.RoleXPermissionDao;
import com.github.yeecode.matrixauth.server.dao.UserXRoleDao;
import com.github.yeecode.matrixauth.server.model.ApplicationModel;
import com.github.yeecode.matrixauth.server.model.RoleModel;
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
import java.util.List;

@Service
public class AuthBusiness {
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

    public Result addUserXRole(String appToken, String appName, String userKey, Integer roleId, RequestSource requestSource) {
        try {
            ApplicationModel applicationModel = applicationDao.queryByName(appName);
            if (applicationModel == null) {
                return ResultUtil.getFailResult(Sentence.NONE_APP_FOUND);
            }
            CacheClient cacheClient = tenantSwitcher.switchByApplication(applicationModel);
            RoleModel roleModel = roleDao.queryById(roleId);
            if (roleModel == null) {
                return ResultUtil.getFailResult(Sentence.NONE_OBJECT_FOUND);
            }

            if (RoleType.valueOf(roleModel.getType()).isSupportedRequestSources(requestSource)) {
                if (tokenValidator.checkApplicationToken(appToken, applicationModel)) {
                    Integer count = proxySelf.addUserXRole(appName, userKey, roleId, cacheClient);
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

    public Result deleteUserXRole(String appToken, String appName, String userKey, Integer roleId, RequestSource requestSource) {
        try {
            ApplicationModel applicationModel = applicationDao.queryByName(appName);
            if (applicationModel == null) {
                return ResultUtil.getFailResult(Sentence.NONE_APP_FOUND);
            }
            CacheClient cacheClient = tenantSwitcher.switchByApplication(applicationModel);
            RoleModel roleModel = roleDao.queryById(roleId);
            if (RoleType.valueOf(roleModel.getType()).isSupportedRequestSources(requestSource)) {
                if (tokenValidator.checkApplicationToken(appToken, applicationModel)) {
                    Integer count = proxySelf.deleteUserXRole(appName, userKey, roleId, cacheClient);
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

    public Result addRoleXPermission(String appToken, String appName, Integer roleId, Integer permissionId) {
        try {
            ApplicationModel applicationModel = applicationDao.queryByName(appName);
            if (applicationModel == null) {
                return ResultUtil.getFailResult(Sentence.NONE_APP_FOUND);
            }

            if (tokenValidator.checkApplicationToken(appToken, applicationModel)) {
                CacheClient cacheClient = tenantSwitcher.switchByApplication(applicationModel);
                Integer count = proxySelf.addRoleXPermission(roleId, permissionId, cacheClient);
                return ResultUtil.getSuccessResult(count);
            } else {
                return ResultUtil.getFailResult(Sentence.ILLEGAL_APP_TOKEN);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResultUtil.getFailResult(Sentence.UNKNOWN_ERROR, ex.getMessage());
        }
    }

    public Result delRoleXPermission(String appToken, String appName, Integer roleId, Integer permissionId) {
        try {
            ApplicationModel applicationModel = applicationDao.queryByName(appName);
            if (applicationModel == null) {
                return ResultUtil.getFailResult(Sentence.NONE_APP_FOUND);
            }

            if (tokenValidator.checkApplicationToken(appToken, applicationModel)) {
                CacheClient cacheClient = tenantSwitcher.switchByApplication(applicationModel);
                Integer count = proxySelf.delRoleXPermission(roleId, permissionId, cacheClient);
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
    public Integer addUserXRole(String appName, String userKey, Integer roleId, CacheClient cacheClient) {
        String fullUserKey = FullUserKeyGenerator.getFullUserKey(appName, userKey);
        Integer count = userXRoleDao.add(fullUserKey, roleId);
        List<String> affectedUsers = new ArrayList<>();
        affectedUsers.add(fullUserKey);
        refreshUserXPermissions(affectedUsers, cacheClient);
        return count;
    }

    @Transactional
    public Integer deleteUserXRole(String appName, String userKey, Integer roleId, CacheClient cacheClient) {
        String fullUserKey = FullUserKeyGenerator.getFullUserKey(appName, userKey);
        Integer count = userXRoleDao.delete(fullUserKey, roleId);
        List<String> affectedUsers = new ArrayList<>();
        affectedUsers.add(fullUserKey);
        refreshUserXPermissions(affectedUsers, cacheClient);
        return count;
    }

    @Transactional
    public Integer addRoleXPermission(Integer roleId, Integer permissionId, CacheClient cacheClient) {
        Integer count = roleXPermissionDao.add(roleId, permissionId);
        List<String> affectedUsers = authDao.queryFullUserKeyByRoleId(roleId);
        refreshUserXPermissions(affectedUsers, cacheClient);
        return count;
    }

    @Transactional
    public Integer delRoleXPermission(Integer roleId, Integer permissionId, CacheClient cacheClient) {
        List<String> affectedUsers = authDao.queryFullUserKeyByRoleId(roleId);
        Integer count = roleXPermissionDao.delete(roleId, permissionId);
        refreshUserXPermissions(affectedUsers, cacheClient);
        return count;
    }

    private void refreshUserXPermissions(List<String> affectedUsers, CacheClient cacheClient) {
        for (String fullUserKey : affectedUsers) {
            String permissions = StringUtils.join(authDao.queryPermissionCodesByFullUserKey(fullUserKey), ';');
            userXPermissionBusiness.addOrUpdatePermissionsByFullUserKey(fullUserKey, permissions);
            cacheClient.addOrUpdate(fullUserKey, permissions);
        }
    }
}
