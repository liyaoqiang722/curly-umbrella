package com.wj.spc.demo_1203.config.mysql;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by liyaoqiang on 2018/11/6.
 */
@Configuration
@ConfigurationProperties(prefix = "connection")
public class BaseDataSourceConfig {
    public boolean readOnly;               //连接只读数据库时配置为true， 保证安全
    private Long connectionTimeout;         //等待连接池分配连接的最大时长（毫秒），超过这个时长还没可用的连接则发生SQLException， 缺省:30秒
    private Long idleTimeout;               //一个连接idle状态的最大时长（毫秒），超时则被释放（retired），缺省:10分钟
    private Long maxLifetime;               //一个连接的生命时长（毫秒），超时而且没被使用则被释放（retired），缺省:30分钟，建议设置比数据库超时时长少30秒，参考MySQL wait_timeout参数（show variables like '%timeout%';）
    private Integer maximumPoolSize;        //连接池中允许的最大连接数。缺省值：10；推荐的公式：((core_count * 2) + effective_spindle_count)
    private Integer minimumIdle;            //连接池最小空闲数量
    private boolean autoCommit;
    private String connectionTestQuery;

    public boolean isReadOnly() {
        return readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    public Long getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(Long connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public Long getIdleTimeout() {
        return idleTimeout;
    }

    public void setIdleTimeout(Long idleTimeout) {
        this.idleTimeout = idleTimeout;
    }

    public Long getMaxLifetime() {
        return maxLifetime;
    }

    public void setMaxLifetime(Long maxLifetime) {
        this.maxLifetime = maxLifetime;
    }

    public Integer getMaximumPoolSize() {
        return maximumPoolSize;
    }

    public void setMaximumPoolSize(Integer maximumPoolSize) {
        this.maximumPoolSize = maximumPoolSize;
    }

    public Integer getMinimumIdle() {
        return minimumIdle;
    }

    public void setMinimumIdle(Integer minimumIdle) {
        this.minimumIdle = minimumIdle;
    }

    public boolean isAutoCommit() {
        return autoCommit;
    }

    public void setAutoCommit(boolean autoCommit) {
        this.autoCommit = autoCommit;
    }

    public String getConnectionTestQuery() {
        return connectionTestQuery;
    }

    public void setConnectionTestQuery(String connectionTestQuery) {
        this.connectionTestQuery = connectionTestQuery;
    }
}
