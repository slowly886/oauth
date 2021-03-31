package com.faw.oauth.config.dataSource;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.datasource.oauth")
@Data
public class HikariCPOauthProperties {

    private String jdbcUrl;
    private String username;
    private String password;
    private String driverClassName;

    /**
     * 是否自定义配置，为true时下面两个参数才生效
     * 同一个SQL语句发生了两次预编译,这不是我们想要的效果,要想对同一SQL语句多次执行不是每次都预编译,就要使用
     */
    private Boolean cachePrepStmts;
    /**
     * 连接池大小默认25，官方推荐250-500
     */
    private Integer prepStmtCacheSize;
    /**
     * 单条语句最大长度默认256，官方推荐2048
     *准备SQL的最大长度.声明驱动程序将缓存.MySQL默认值为256.在我们经验
     *特别是像Hibernate这样的ORM框架默认值远远低于生成语句长度的阈值,官方推荐的设置是2048.
     */
    private Integer prepStmtCacheSqlLimit;


    /**
     * 新版本MySQL支持服务器端准备，开启能够得到显著性能提升
     * 使用预处理 只有为true才能开启Mysql的预编译
     */
    private Boolean useServerPrepStmts;

    private Boolean useLocalSessionState;
    private Boolean useLocalTransactionState;
    private Boolean rewriteBatchedStatements;
    private Boolean cacheResultSetMetadata;
    private Boolean cacheServerConfiguration;
    private Boolean elideSetAutoCommits;
    private Boolean maintainTimeStats;

    /**
     * 连接数据库测试sql
     */
    private String connectionTestQuery;
    /**
     * 连接池名字
     */
    private String poolName;

    /**
     * 最少空闲连接数
     */
    private Integer minIdle;
    /**
     * 最大连接数
     */
    private Integer maxActive;
    /**
     * 连接允许在池中闲置的最长时间，单位：毫秒，默认为10秒
     */
    private Integer idleTimeout;
    /**
     * 等待来自池的连接的最大毫秒数
     */
    private Integer connectionTimeout;

    /**
     * 一个连接的生命时长(毫秒),超时而且没被使用则被释放(retired),缺省:30分钟.
     * 建议设置比数据库超时时长少30秒,参考MySQL wait_timeout参数(show variables like '%timeout%';)
     * 单位：毫秒
     */
    private Integer maxLifetime;
    /**
     * 从池中获取的连接是否默认处于只读模式,默认为false
     */
    private Boolean readOnly;

    /**
     * removeAbandoned多久回收，单位秒
     */
    private Integer removeAbandonedTimeout;
    /**
     * 关闭abanded连接时输出错误日志
     */
    private Boolean logAbandoned;

}
