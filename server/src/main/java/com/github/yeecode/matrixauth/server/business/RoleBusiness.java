package com.github.yeecode.matrixauth.server.business;

import com.github.yeecode.matrixauth.server.cacheclient.CacheClient;
import com.github.yeecode.matrixauth.server.constant.Sentence;
import com.github.yeecode.matrixauth.server.dao.ApplicationDao;
import com.github.yeecode.matrixauth.server.dao.AuthDao;
import com.github.yeecode.matrixauth.server.dao.RoleDao;
import com.github.yeecode.matrixauth.server.dao.RoleXPermissionDao;
import com.github.yeecode.matrixauth.server.dao.UserXPermissionDao;
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
import java.util.List;

@Service
public class RoleBusiness {
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private RoleXPermissionDao roleXPermissionDao;
    @Autowired
    private UserXPermissionDao userXPermissionDao;
    @Autowired
    private UserXRoleDao userXRoleDao;
    @Autowired
    private AuthDao authDao;
    @Autowired
    private TokenValidator tokenValidator;
    @Autowired
    private ApplicationDao applicationDao;
    @Autowired
    private TenantSwitcher tenantSwitcher;
    @Autowired
    private UserXPermissionBusiness userXPermissionBusiness;
    @Autowired
    private ApplicationContext context;
    private RoleBusiness proxySelf;

    @PostConstruct
    public void setSelf() {
        proxySelf = context.getBean(RoleBusiness.class);
    }

    public Result add(String appToken, RoleModel roleModel) {
        try {
            ApplicationModel applicationModel = applicationDao.queryByName(roleModel.getAppName());
            if (applicationModel == null) {
                return ResultUtil.getFailResult(Sentence.NONE_APP_FOUND);
            }

            if (tokenValidator.checkApplicationToken(appToken, applicationModel)) {
                tenantSwitcher.switchByApplication(applicationModel);
                return ResultUtil.getSuccessResult(roleDao.add(roleModel));
            } else {
                return ResultUtil.getFailResult(Sentence.ILLEGAL_APP_TOKEN);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResultUtil.getFailResult(Sentence.UNKNOWN_ERROR, ex.getMessage());
        }
    }

    public Result deleteByName(String appToken, RoleModel roleModel) {
        try {
            ApplicationModel applicationModel = applicationDao.queryByName(roleModel.getAppName());
            if (applicationModel == null) {
                return ResultUtil.getFailResult(Sentence.NONE_APP_FOUND);
            }
            if (tokenValidator.checkApplicationToken(appToken, applicationModel)) {
                CacheClient cacheClient = tenantSwitcher.switchByApplication(applicationModel);
                Integer count = proxySelf.deleteByName(roleModel.getAppName(),roleModel.getName(), cacheClient);
                return ResultUtil.getSuccessResult(count);
            } else {
                return ResultUtil.getFailResult(Sentence.ILLEGAL_APP_TOKEN);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResultUtil.getFailResult(Sentence.UNKNOWN_ERROR, ex.getMessage());
        }
    }

    public Result updateByName(String appToken, RoleModel roleModel) {
        try {
            ApplicationModel applicationModel = applicationDao.queryByName(roleModel.getAppName());
            if (applicationModel == null) {
                return ResultUtil.getFailResult(Sentence.NONE_APP_FOUND);
            }

            if (tokenValidator.checkApplicationToken(appToken, applicationModel)) {
                tenantSwitcher.switchByApplication(applicationModel);
                return ResultUtil.getSuccessResult(roleDao.updateByName(roleModel));
            } else {
                return ResultUtil.getFailResult(Sentence.ILLEGAL_APP_TOKEN);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResultUtil.getFailResult(Sentence.UNKNOWN_ERROR, ex.getMessage());
        }
    }

    public Result queryByAppName(String appToken, String appName) {
        try {
            ApplicationModel applicationModel = applicationDao.queryByName(appName);
            if (tokenValidator.checkApplicationToken(appToken, applicationModel)) {
                tenantSwitcher.switchByApplication(applicationModel);
                List<RoleModel> roleModelList = roleDao.queryByAppName(appName);
                return ResultUtil.getSuccessResult(roleModelList);
            } else {
                return ResultUtil.getFailResult(Sentence.ILLEGAL_APP_TOKEN);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResultUtil.getFailResult(Sentence.UNKNOWN_ERROR, ex.getMessage());
        }
    }

    public Result queryByName(String appToken, RoleModel roleModel) {
        try {
            ApplicationModel applicationModel = applicationDao.queryByName(roleModel.getAppName());
            if (tokenValidator.checkApplicationToken(appToken, applicationModel)) {
                tenantSwitcher.switchByApplication(applicationModel);
                RoleModel roleModelResult = roleDao.queryByName(roleModel.getName(), roleModel.getAppName());
                return ResultUtil.getSuccessResult(roleModelResult);
            } else {
                return ResultUtil.getFailResult(Sentence.ILLEGAL_APP_TOKEN);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResultUtil.getFailResult(Sentence.UNKNOWN_ERROR, ex.getMessage());
        }
    }

    @Transactional
    public Integer deleteByName(String appName, String name, CacheClient cacheClient) {
        List<String> affectedUserKeys = authDao.queryUserKeyByRoleName(name, appName);
        userXRoleDao.deleteByRoleName(appName,name);
        roleXPermissionDao.deleteByRoleName(appName,name);
        Integer count = roleDao.deleteByName(name,appName);
        for (String userKey : affectedUserKeys) {
            String permissionKeys = StringUtils.join(authDao.queryPermKeysByUserKey(appName, userKey), ';');
            String fullUserKey = FullUserKeyGenerator.getFullUserKey(appName,userKey);
            userXPermissionBusiness.addOrUpdatePermissionsByFullUserKey(fullUserKey, permissionKeys);
            cacheClient.addOrUpdate(fullUserKey, permissionKeys);
        }
        return count;
    }
}
