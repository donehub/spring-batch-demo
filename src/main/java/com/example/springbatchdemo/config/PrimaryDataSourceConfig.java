package com.example.springbatchdemo.config;

import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author zourongsheng
 * @version 1.0
 * @date 2022/4/23 18:54
 */
@Configuration
@MapperScan(basePackages = "com.example.springbatchdemo.mapper", sqlSessionTemplateRef = "batchDemoSqlSessionTemplate")
public class PrimaryDataSourceConfig {

    private static final Logger log = LoggerFactory.getLogger(PrimaryDataSourceConfig.class);

    @Value("${mybatis.batch-demo.mapper-locations}")
    private String batchDemoMapperPath;

    @Primary
    @Bean(name = "batchDemoSqlSessionFactory")
    public SqlSessionFactory batchDemoSqlSessionFactory(@Qualifier("batchDemoDB") DataSource dataSource) {
        SqlSessionFactory sqlSessionFactory = null;
        try {
            SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();

            sessionFactory.setDataSource(dataSource);
            // 映射文件地址
            sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(batchDemoMapperPath));
            // 如果使用了xml文件
            org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
            // 使用jdbc的getGeneratedKeys获取数据库自增主键值
            configuration.setUseGeneratedKeys(true);
            // 使用列别名替换列名 select user as User
            configuration.setUseColumnLabel(true);
            // 自动使用驼峰命名属性映射字段   userId    user_id
            configuration.setMapUnderscoreToCamelCase(true);
            sessionFactory.setConfiguration(configuration);

            // 分页插件
            Interceptor interceptor = new PageInterceptor();
            Properties properties = new Properties();
            // 数据库
            properties.setProperty("helperDialect", "mysql");
            // 是否分页合理化
            properties.setProperty("reasonable", "false");
            interceptor.setProperties(properties);
            sessionFactory.setPlugins(new Interceptor[]{interceptor});

            sqlSessionFactory = sessionFactory.getObject();
        } catch (Exception e) {
            log.error("fail to init MyBatis sqlSessionFactory!", e);
        }
        return sqlSessionFactory;
    }

    @Primary
    @Bean("batchDemoTransactionManager")
    public PlatformTransactionManager batchDemoTransactionManager(@Qualifier("batchDemoDB") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Primary
    @Bean(name = "batchDemoSqlSessionTemplate")
    public SqlSessionTemplate batchDemoSqlSessionTemplate(@Qualifier("batchDemoSqlSessionFactory") SqlSessionFactory batchDemoSqlSessionFactory) {
        return new SqlSessionTemplate(batchDemoSqlSessionFactory);
    }
}
