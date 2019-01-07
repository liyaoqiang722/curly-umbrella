/*
package com.wj.spc.demo_1203.config.zookeeper.configurer;

import com.wj.spc.demo_1203.config.zookeeper.constants.ZookeeperConstants;
import com.wj.spc.demo_1203.config.zookeeper.helper.ZookeeperHelper;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.ConfigurablePropertyResolver;
import org.springframework.core.env.Environment;

import java.util.Properties;

*/
/**
 * Created by liyaoqiang on 2019/1/4.
 *//*

@Configuration
public class ZookeeperPropertySourcesPlaceholderConfigurer extends PropertySourcesPlaceholderConfigurer implements InitializingBean {

    private String connectionString;

    private int baseSleepTime;

    private int maxTries;

    @Override
    public void setEnvironment(Environment environment) {
        super.setEnvironment(environment);
        this.connectionString = environment.getProperty("zookeeper.connection");
        this.baseSleepTime = Integer.valueOf(environment.getProperty("zookeeper.baseSleepTime"));
        this.maxTries = Integer.valueOf(environment.getProperty("zookeeper.baseSleepTime"));
    }

    @Bean(name = "curatorFramework")
    public CuratorFramework curatorFramework() {
        if (null == this.connectionString) {
            throw new RuntimeException("Zookeeper connection string is not set");
        }

        RetryPolicy retryPolicy = new ExponentialBackoffRetry(this.baseSleepTime, this.maxTries);

        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder().connectString(this.connectionString).
                retryPolicy(retryPolicy).build();

        return curatorFramework;
    }

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, ConfigurablePropertyResolver propertyResolver) throws BeansException {
        super.processProperties(beanFactoryToProcess, propertyResolver);
    }

    @Override
    public void afterPropertiesSet() {
        Properties props = new Properties();
        CuratorFramework curatorFramework = curatorFramework();

        try {
            StringBuilder basePath = new StringBuilder();

            curatorFramework.start();

            buildRedisConfig(curatorFramework, props, basePath);

        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        } finally {
            CloseableUtils.closeQuietly(curatorFramework);
        }

        this.setProperties(props);
    }

    private void buildRedisConfig(CuratorFramework curatorFramework, Properties props, StringBuilder basePath) {
        basePath.delete(0, basePath.length());
        basePath.append("/").append(ZookeeperConstants.COUNSELOR_NAMESPACE).append("/").append(ZookeeperConstants.COUNSELOR_DAO_REDIS);

        StringBuilder sb = new StringBuilder();

        sb.delete(0, sb.length());
        sb.append(basePath).append("/").append(ZookeeperConstants.COUNSELOR_DAO_REDIS_REDIS_DB_DATABASE);
        props.put(ZookeeperConstants.COUNSELOR_DAO_REDIS_REDIS_DB_DATABASE, ZookeeperHelper.getData(curatorFramework, sb.toString()));

        sb.delete(0, sb.length());
        sb.append(basePath).append("/").append(ZookeeperConstants.COUNSELOR_DAO_REDIS_REDIS_DB_HOST);
        props.put(ZookeeperConstants.COUNSELOR_DAO_REDIS_REDIS_DB_HOST, ZookeeperHelper.getData(curatorFramework, sb.toString()));

        sb.delete(0, sb.length());
        sb.append(basePath).append("/").append(ZookeeperConstants.COUNSELOR_DAO_REDIS_REDIS_DB_PORT);
        props.put(ZookeeperConstants.COUNSELOR_DAO_REDIS_REDIS_DB_PORT, ZookeeperHelper.getData(curatorFramework, sb.toString()));

        sb.delete(0, sb.length());
        sb.append(basePath).append("/").append(ZookeeperConstants.COUNSELOR_DAO_REDIS_REDIS_DB_KEY);
        props.put(ZookeeperConstants.COUNSELOR_DAO_REDIS_REDIS_DB_KEY, ZookeeperHelper.getData(curatorFramework, sb.toString()));
    }

}
*/
