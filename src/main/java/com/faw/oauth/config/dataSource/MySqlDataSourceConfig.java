package com.faw.oauth.config.dataSource;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableConfigurationProperties(HikariCPUserProperties.class)
@MapperScan(basePackages = {"com.faw.oauth.dao.user"}, sqlSessionFactoryRef = "mysqlSqlSessionFactory", sqlSessionTemplateRef = "mysqlSqlSessionTemplate")
@ConditionalOnClass(HikariCPUserProperties.class)
//@AutoConfigureBefore(DataSourceAutoConfiguration.class)
public class MySqlDataSourceConfig {

    @Autowired
    private HikariCPUserProperties properties;

    @Value("${mybatis-plus.mapper-locations}")
    private String mapperLocations;

    @Bean(name = "mysqlDataSource", destroyMethod = "close")
    @Qualifier("mysqlDs")
    @Primary
    public HikariDataSource mysqlDataSource() {
        System.out.println("------------1------------");
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(properties.getJdbcUrl());
        hikariConfig.setUsername(properties.getUsername());
        hikariConfig.setPassword(properties.getPassword());
        hikariConfig.setDriverClassName(properties.getDriverClassName());

        hikariConfig.addDataSourceProperty("dataSource.cachePrepStmts", properties.getCachePrepStmts());
        hikariConfig.addDataSourceProperty("dataSource.prepStmtCacheSize", properties.getPrepStmtCacheSize());
        hikariConfig.addDataSourceProperty("dataSource.prepStmtCacheSqlLimit", properties.getPrepStmtCacheSqlLimit());

        hikariConfig.addDataSourceProperty("dataSource.useServerPrepStmts", "true");

        hikariConfig.addDataSourceProperty("useLocalSessionState", properties.getUseLocalSessionState());
        hikariConfig.addDataSourceProperty("useLocalTransactionState", properties.getUseLocalTransactionState());
        hikariConfig.addDataSourceProperty("rewriteBatchedStatements", properties.getRewriteBatchedStatements());
        hikariConfig.addDataSourceProperty("cacheResultSetMetadata", properties.getCacheResultSetMetadata());
        hikariConfig.addDataSourceProperty("cacheServerConfiguration", properties.getCacheServerConfiguration());
        hikariConfig.addDataSourceProperty("elideSetAutoCommits", properties.getElideSetAutoCommits());
        hikariConfig.addDataSourceProperty("maintainTimeStats", properties.getMaintainTimeStats());
        hikariConfig.addDataSourceProperty("logAbandoned", properties.getLogAbandoned());
        hikariConfig.addDataSourceProperty("removeAbandonedTimeout", properties.getRemoveAbandonedTimeout());

        hikariConfig.setConnectionTestQuery(properties.getConnectionTestQuery());
        hikariConfig.setPoolName(properties.getPoolName());

        hikariConfig.setMaximumPoolSize(properties.getMaxActive());
        hikariConfig.setMaxLifetime(properties.getMaxLifetime());
        hikariConfig.setIdleTimeout(properties.getIdleTimeout());
        hikariConfig.setConnectionTimeout(properties.getConnectionTimeout());
        hikariConfig.setReadOnly(properties.getReadOnly());

        return new HikariDataSource(hikariConfig);

    }

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    /**
     * SQL执行效率插件
     */
    @Bean
    @Primary
//    @Profile({"dev", "sit"}) // 设置 dev test 环境开启
    public PerformanceInterceptor performanceInterceptor() {
        return new PerformanceInterceptor();
    }

    @Autowired
    private PerformanceInterceptor performanceInterceptor;

    @Autowired
    PaginationInterceptor paginationInterceptor;

    @Bean(name = "mysqlSqlSessionFactory")
    @Primary
    public SqlSessionFactory mysqlSqlSessionFactory(@Qualifier("mysqlDataSource") DataSource dataSource) throws Exception {

        MybatisSqlSessionFactoryBean sqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        MybatisConfiguration configuration = new MybatisConfiguration();
        // ISqlInjector injector= new LogicSqlInjector();
        // injector.injectSqlRunner(configuration);
        sqlSessionFactoryBean.setConfiguration(configuration);
        // sqlSessionFactoryBean.setPlugins(new Interceptor[] {paginationInterceptor, performanceInterceptor});
        if (performanceInterceptor != null) {
            sqlSessionFactoryBean.setPlugins(new Interceptor[]{paginationInterceptor, performanceInterceptor});
        } else {
            sqlSessionFactoryBean.setPlugins(new Interceptor[]{paginationInterceptor});
        }
        sqlSessionFactoryBean
                .setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mapperLocations));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name = "mysqlTransactionManager")
    @Primary
    public DataSourceTransactionManager mysqlTransactionManager(@Qualifier("mysqlDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "mysqlSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate mysqlSqlSessionTemplate(@Qualifier("mysqlSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
