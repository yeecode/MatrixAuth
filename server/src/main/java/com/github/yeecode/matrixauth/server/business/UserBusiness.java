package com.github.yeecode.matrixauth.server.business;

import com.github.yeecode.matrixauth.server.cacheclient.CacheClient;
import com.github.yeecode.matrixauth.server.constant.Sentence;
import com.github.yeecode.matrixauth.server.dao.ApplicationDao;
import com.github.yeecode.matrixauth.server.dao.UserDao;
import com.github.yeecode.matrixauth.server.dao.UserXPermissionDao;
import com.github.yeecode.matrixauth.server.dao.UserXRoleDao;
import com.github.yeecode.matrixauth.server.model.ApplicationModel;
import com.github.yeecode.matrixauth.server.model.UserModel;
import com.github.yeecode.matrixauth.server.tenant.TenantSwitcher;
import com.github.yeecode.matrixauth.server.util.FullUserKeyGenerator;
import com.github.yeecode.matrixauth.server.util.Result;
import com.github.yeecode.matrixauth.server.util.ResultUtil;
import com.github.yeecode.matrixauth.server.util.TokenValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;

@EnableTransactionManagement
@Service
public class UserBusiness {
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserXPermissionDao userXPermissionDao;
    @Autowired
    private UserXRoleDao userXRoleDao;
    @Autowired
    private TokenValidator tokenValidator;
    @Autowired
    private ApplicationDao applicationDao;
    @Autowired
    private TenantSwitcher tenantSwitcher;
    @Autowired
    private ApplicationContext context;
    private UserBusiness proxySelf;

    @PostConstruct
    public void setSelf() {
        proxySelf = context.getBean(UserBusiness.class);
    }

    public Result add(String appToken, UserModel userModel) {
        try {
            ApplicationModel applicationModel = applicationDao.queryByName(userModel.getAppName());
            if (applicationModel == null) {
                return ResultUtil.getFailResult(Sentence.NONE_APP_FOUND);
            }

            if (tokenValidator.checkApplicationToken(appToken, applicationModel)) {
                tenantSwitcher.switchByApplication(applicationModel);
                return ResultUtil.getSuccessResult(userDao.add(userModel));
            } else {
                return ResultUtil.getFailResult(Sentence.ILLEGAL_APP_TOKEN);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResultUtil.getFailResult(Sentence.UNKNOWN_ERROR, ex.getMessage());
        }
    }

    public Result deleteByKeyAndAppName(String appToken, UserModel userModel) {
        try {
            ApplicationModel applicationModel = applicationDao.queryByName(userModel.getAppName());
            if (applicationModel == null) {
                return ResultUtil.getFailResult(Sentence.NONE_APP_FOUND);
            }
            if (tokenValidator.checkApplicationToken(appToken, applicationModel)) {
                CacheClient cacheClient = tenantSwitcher.switchByApplication(applicationModel);
                Integer count = proxySelf.deleteByKeyAndAppName(userModel.getAppName(), userModel.getKey(), cacheClient);
                return ResultUtil.getSuccessResult(count);
            } else {
                return ResultUtil.getFailResult(Sentence.ILLEGAL_APP_TOKEN);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResultUtil.getFailResult(Sentence.UNKNOWN_ERROR, ex.getMessage());
        }
    }

    public Result updateByKeyAndAppName(String appToken, UserModel userModel) {
        try {
            ApplicationModel applicationModel = applicationDao.queryByName(userModel.getAppName());
            if (applicationModel == null) {
                return ResultUtil.getFailResult(Sentence.NONE_APP_FOUND);
            }

            if (tokenValidator.checkApplicationToken(appToken, applicationModel)) {
                tenantSwitcher.switchByApplication(applicationModel);
                return ResultUtil.getSuccessResult(userDao.updateByKeyAndAppName(userModel));
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
                List<UserModel> userModelList = userDao.queryByAppName(appName);
                return ResultUtil.getSuccessResult(userModelList);
            } else {
                return ResultUtil.getFailResult(Sentence.ILLEGAL_APP_TOKEN);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResultUtil.getFailResult(Sentence.UNKNOWN_ERROR, ex.getMessage());
        }
    }

    public Result queryByKeyAndAppName(String appToken, UserModel userModel) {
        try {
            ApplicationModel applicationModel = applicationDao.queryByName(userModel.getAppName());
            if (tokenValidator.checkApplicationToken(appToken, applicationModel)) {
                tenantSwitcher.switchByApplication(applicationModel);
                UserModel userModelResult = userDao.queryByKeyAndAppName(userModel.getKey(), userModel.getAppName());
                return ResultUtil.getSuccessResult(userModelResult);
            } else {
                return ResultUtil.getFailResult(Sentence.ILLEGAL_APP_TOKEN);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResultUtil.getFailResult(Sentence.UNKNOWN_ERROR, ex.getMessage());
        }
    }

    @Transactional
    Integer deleteByKeyAndAppName(String appName, String userKey, CacheClient cacheClient) {
        String fullUserKey = FullUserKeyGenerator.getFullUserKey(appName, userKey);
        userXRoleDao.deleteByFullUserKey(fullUserKey);
        userXPermissionDao.deleteByFullUserKey(fullUserKey);
        cacheClient.delete(fullUserKey);
        return userDao.deleteByKeyAndAppName(userKey, appName);
    }
}
