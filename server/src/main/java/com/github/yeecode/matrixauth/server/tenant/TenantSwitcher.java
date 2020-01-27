package com.github.yeecode.matrixauth.server.tenant;

import com.github.yeecode.dynamicdatasource.DynamicDataSource;
import com.github.yeecode.dynamicdatasource.model.DataSourceInfo;
import com.github.yeecode.matrixauth.server.cacheclient.CacheClient;
import com.github.yeecode.matrixauth.server.cacheclient.CacheClientManager;
import com.github.yeecode.matrixauth.server.cacheclient.NoneCacheClient;
import com.github.yeecode.matrixauth.server.dao.CacheDao;
import com.github.yeecode.matrixauth.server.dao.DataSourceDao;
import com.github.yeecode.matrixauth.server.model.ApplicationModel;
import com.github.yeecode.matrixauth.server.model.CacheModel;
import com.github.yeecode.matrixauth.server.model.DataSourceModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TenantSwitcher {
    @Autowired
    private DynamicDataSource dynamicDataSource;
    @Autowired
    private DataSourceDao dataSourceDao;
    @Autowired
    private CacheDao cacheDao;
    @Autowired
    private CacheClientManager cacheClientManager;

    public CacheClient switchByApplication(ApplicationModel applicationModel) {
        CacheClient cacheClient = new NoneCacheClient();
        if (applicationModel.getCacheName() != null) {
            CacheModel cacheModel = cacheDao.queryByName(applicationModel.getCacheName());
            if (cacheModel != null && cacheModel.getUrl() != null) {
                cacheClient = cacheClientManager.getRedisClient(cacheModel.getUrl(), cacheModel.getPassword());
            }
        }

        if (applicationModel.getDataSourceName() != null) {
            DataSourceModel dataSourceModel = dataSourceDao.queryByName(applicationModel.getDataSourceName());
            if (dataSourceModel != null && dataSourceModel.getUrl() != null) {
                DataSourceInfo dataSourceInfo = new DataSourceInfo(dataSourceModel.getName(),
                        dataSourceModel.getDriver(),
                        dataSourceModel.getUrl(),
                        dataSourceModel.getUserName(),
                        dataSourceModel.getPassword());
                dynamicDataSource.addAndSwitchDataSource(dataSourceInfo, true);
            }
        }
        return cacheClient;
    }

}
