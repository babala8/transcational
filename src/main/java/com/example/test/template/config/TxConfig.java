package com.example.test.template.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;


@Configuration
//@MapperScan(basePackages = "com.zjft.zhyg.system.dao")// mybatis 配置
public class TxConfig {

    /**
     * 从配置文件获取主数据源的连接信息
     */
    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
//    @Value("${mybatis.mapper-locations-zjbims}")
//    private String mapperLocation;
//    @Value("${mybatis.type-aliases-package}")
//    private String typeAliasPackage;

    /**
     * 创建主数据源对象，注入到Spring
     */
    @Bean
    @Primary
//    @ConfigurationProperties(prefix = "spring.datasource.druid")// 读取数据库性能配置信息
    public DataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(this.driverClassName);
        dataSource.setUrl(this.url);
        dataSource.setUsername(this.username);
        dataSource.setPassword(this.password);
        return dataSource;
    }

    //注册JDBC
    @Bean
    public JdbcTemplate jdbcTemplate() throws Exception{
        //spring对@Configuration会特殊处理，给容器中加组件的方法多次调用都只是从容器中找组件
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource());
        return jdbcTemplate;
    }

    //注册事务管理器
    @Bean
    public PlatformTransactionManager platformTransactionManager() throws Exception{
        return new DataSourceTransactionManager(dataSource());
    }
}
