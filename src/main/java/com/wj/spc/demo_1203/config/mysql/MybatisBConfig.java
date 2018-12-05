package com.wj.spc.demo_1203.config.mysql;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * Created by liyaoqiang on 2018/11/6.
 */
@Configuration
@EnableConfigurationProperties(BaseDataSourceConfig.class)
@MapperScan(basePackages = {"com.wj.spc.demo_1203.dao.bMapper"}, sqlSessionTemplateRef = "bSqlSessionTemplate")
public class MybatisBConfig {

    @Autowired
    private BaseDataSourceConfig baseDataSourceConfig;

    @Value("${spring.datasource.secondary.url}")
    private String jdbcUrl;

    @Value("${spring.datasource.secondary.password}")
    private String password;

    @Value("${spring.datasource.secondary.username}")
    private String username;

    @Bean(name = "bDataSource")
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();

        config.setJdbcUrl(jdbcUrl);
        config.setUsername(username);
        config.setPassword(password);
        config.setReadOnly(baseDataSourceConfig.isReadOnly());
        config.setConnectionTimeout(baseDataSourceConfig.getConnectionTimeout());
        config.setIdleTimeout(baseDataSourceConfig.getIdleTimeout());
        config.setMaxLifetime(baseDataSourceConfig.getMaxLifetime());
        config.setMaximumPoolSize(baseDataSourceConfig.getMaximumPoolSize());
        config.setMinimumIdle(baseDataSourceConfig.getMinimumIdle());
        config.setAutoCommit(baseDataSourceConfig.isAutoCommit());
        config.setConnectionTestQuery(baseDataSourceConfig.getConnectionTestQuery());

        return new HikariDataSource(config);

    }

    @Bean(name = "bSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("bDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setConfigLocation(new ClassPathResource("mybatis-config.xml"));
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/bMappers/*.xml"));

        return sessionFactory.getObject();
    }

    @Bean(name = "bSqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("bSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
