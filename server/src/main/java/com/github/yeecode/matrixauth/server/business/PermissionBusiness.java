package com.github.yeecode.matrixauth.server.business;

import com.github.yeecode.matrixauth.server.cacheclient.CacheClient;
import com.github.yeecode.matrixauth.server.constant.Sentence;
import com.github.yeecode.matrixauth.server.dao.ApplicationDao;
import com.github.yeecode.matrixauth.server.dao.AuthDao;
import com.github.yeecode.matrixauth.server.dao.PermissionDao;
import com.github.yeecode.matrixauth.server.dao.RoleXPermissionDao;
import com.github.yeecode.matrixauth.server.model.ApplicationModel;
import com.github.yeecode.matrixauth.server.model.PermissionModel;
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
public class PermissionBusiness {
    @Autowired
    private PermissionDao permissionDao;
    @Autowired
    private AuthDao authDao;
    @Autowired
    private RoleXPermissionDao roleXPermissionDao;
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
    private PermissionBusiness proxySelf;

    @PostConstruct
    public void setSelf() {
        proxySelf = context.getBean(PermissionBusiness.class);
    }

    public Result add(String appToken, PermissionModel permissionModel) {
        try {
            ApplicationModel applicationModel = applicationDao.queryByName(permissionModel.getAppName());
            if (applicationModel == null) {
                return ResultUtil.getFailResult(Sentence.NONE_APP_FOUND);
            }

            if (tokenValidator.checkApplicationToken(appToken, applicationModel)) {
                tenantSwitcher.switchByApplication(applicationModel);
                return ResultUtil.getSuccessResult(permissionDao.add(permissionModel));
            } else {
                return ResultUtil.getFailResult(Sentence.ILLEGAL_APP_TOKEN);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResultUtil.getFailResult(Sentence.UNKNOWN_ERROR, ex.getMessage());
        }
    }

    public Result deleteByKey(String appToken, PermissionModel permissionModel) {
        try {
            ApplicationModel applicationModel = applicationDao.queryByName(permissionModel.getAppName());
            if (applicationModel == null) {
                return ResultUtil.getFailResult(Sentence.NONE_APP_FOUND);
            }
            if (tokenValidator.checkApplicationToken(appToken, applicationModel)) {
                CacheClient cacheClient = tenantSwitcher.switchByApplication(applicationModel);
                Integer count = proxySelf.deleteByKey(permissionModel.getAppName(),permissionModel.getKey(), cacheClient);
                return ResultUtil.getSuccessResult(count);
            } else {
                return ResultUtil.getFailResult(Sentence.ILLEGAL_APP_TOKEN);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResultUtil.getFailResult(Sentence.UNKNOWN_ERROR, ex.getMessage());
        }
    }

    public Result updateByKey(String appToken, PermissionModel permissionModel) {
        try {
            ApplicationModel applicationModel = applicationDao.queryByName(permissionModel.getAppName());
            if (applicationModel == null) {
                return ResultUtil.getFailResult(Sentence.NONE_APP_FOUND);
            }
            if (tokenValidator.checkApplicationToken(appToken, applicationModel)) {
                CacheClient cacheClient = tenantSwitcher.switchByApplication(applicationModel);
                Integer count = proxySelf.updateById(permissionModel, cacheClient);
                return ResultUtil.getSuccessResult(count);
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
                List<PermissionModel> permissionModelList = permissionDao.queryByAppName(appName);
                return ResultUtil.getSuccessResult(permissionModelList);
            } else {
                return ResultUtil.getFailResult(Sentence.ILLEGAL_APP_TOKEN);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResultUtil.getFailResult(Sentence.UNKNOWN_ERROR, ex.getMessage());
        }
    }

    public Result queryByKey(String appToken, PermissionModel permissionModel) {
        try {
            ApplicationModel applicationModel = applicationDao.queryByName(permissionModel.getAppName());
            if (tokenValidator.checkApplicationToken(appToken, applicationModel)) {
                tenantSwitcher.switchByApplication(applicationModel);
                PermissionModel permissionModelResult = permissionDao.queryByKey(permissionModel.getKey(),permissionModel.getAppName());
                return ResultUtil.getSuccessResult(permissionModelResult);
            } else {
                return ResultUtil.getFailResult(Sentence.ILLEGAL_APP_TOKEN);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResultUtil.getFailResult(Sentence.UNKNOWN_ERROR, ex.getMessage());
        }
    }

    @Transactional
    public Integer deleteByKey(String appName, String permKey, CacheClient cacheClient) {
        List<String> affectedUserKeys = authDao.queryUserKeyByPermKey(appName,permKey);
        roleXPermissionDao.deleteByPermKey(appName, permKey);
        Integer count = permissionDao.deleteByKey(permKey, appName);
        for (String userKey : affectedUserKeys) {
            String permissions = StringUtils.join(authDao.queryPermKeysByUserKey(appName, userKey), ';');
            String fullUserKey = FullUserKeyGenerator.getFullUserKey(appName, userKey);
            userXPermissionBusiness.addOrUpdatePermissionsByFullUserKey(fullUserKey, permissions);
            cacheClient.addOrUpdate(fullUserKey, permissions);
        }
        return count;
    }

    @Transactional
    public Integer updateById(PermissionModel permissionModel, CacheClient cacheClient) {
        List<String> affectedUserKeys = authDao.queryUserKeyByPermKey(permissionModel.getAppName(),permissionModel.getKey());
        Integer count = permissionDao.updateByKey(permissionModel);
        for (String userKey : affectedUserKeys) {
            String permissionKeys = StringUtils.join(authDao.queryPermKeysByUserKey(permissionModel.getAppName(),permissionModel.getKey()), ';');
            String fullUserKey = FullUserKeyGenerator.getFullUserKey(permissionModel.getAppName(), userKey);
            userXPermissionBusiness.addOrUpdatePermissionsByFullUserKey(fullUserKey, permissionKeys);
            cacheClient.addOrUpdate(fullUserKey, permissionKeys);
        }
        return count;
    }
}
