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

    public Result deleteByIdAndAppName(String appToken, RoleModel roleModel) {
        try {
            ApplicationModel applicationModel = applicationDao.queryByName(roleModel.getAppName());
            if (applicationModel == null) {
                return ResultUtil.getFailResult(Sentence.NONE_APP_FOUND);
            }
            if (tokenValidator.checkApplicationToken(appToken, applicationModel)) {
                CacheClient cacheClient = tenantSwitcher.switchByApplication(applicationModel);
                Integer count = proxySelf.deleteByIdAndAppName(roleModel.getId(), cacheClient);
                return ResultUtil.getSuccessResult(count);
            } else {
                return ResultUtil.getFailResult(Sentence.ILLEGAL_APP_TOKEN);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResultUtil.getFailResult(Sentence.UNKNOWN_ERROR, ex.getMessage());
        }
    }

    public Result updateByIdAndAppName(String appToken, RoleModel roleModel) {
        try {
            ApplicationModel applicationModel = applicationDao.queryByName(roleModel.getAppName());
            if (applicationModel == null) {
                return ResultUtil.getFailResult(Sentence.NONE_APP_FOUND);
            }

            if (tokenValidator.checkApplicationToken(appToken, applicationModel)) {
                tenantSwitcher.switchByApplication(applicationModel);
                return ResultUtil.getSuccessResult(roleDao.updateById(roleModel));
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

    public Result queryByIdAndAppName(String appToken, RoleModel roleModel) {
        try {
            ApplicationModel applicationModel = applicationDao.queryByName(roleModel.getAppName());
            if (tokenValidator.checkApplicationToken(appToken, applicationModel)) {
                tenantSwitcher.switchByApplication(applicationModel);
                RoleModel roleModelResult = roleDao.queryById(roleModel.getId());
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
    public Integer deleteByIdAndAppName(Integer roleId, CacheClient cacheClient) {
        List<String> affectedUsers = authDao.queryFullUserKeyByRoleId(roleId);
        userXRoleDao.deleteByRoleId(roleId);
        roleXPermissionDao.deleteByRoleId(roleId);
        Integer count = roleDao.deleteById(roleId);
        for (String fullUserKey : affectedUsers) {
            String permissions = StringUtils.join(authDao.queryPermissionCodesByFullUserKey(fullUserKey), ';');
            userXPermissionBusiness.addOrUpdatePermissionsByFullUserKey(fullUserKey, permissions);
            cacheClient.addOrUpdate(fullUserKey, permissions);
        }
        return count;
    }
}
