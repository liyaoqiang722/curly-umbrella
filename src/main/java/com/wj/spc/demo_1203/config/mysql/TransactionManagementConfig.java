package com.wj.spc.demo_1203.config.mysql;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * Created by liyaoqiang on 2018/11/6.
 */
@Configuration
public class TransactionManagementConfig implements TransactionManagementConfigurer {

    @Resource(name = "myTransactionManager")
    private PlatformTransactionManager annotationTransactionManager;

    @Bean(name = "myTransactionManager")
    @Primary
    public DataSourceTransactionManager transactionManager(@Qualifier("dataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return annotationTransactionManager;
    }
}
