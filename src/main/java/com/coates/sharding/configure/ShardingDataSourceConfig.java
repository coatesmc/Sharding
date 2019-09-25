package com.coates.sharding.configure;

import com.dangdang.ddframe.rdb.sharding.api.ShardingDataSourceFactory;
import com.dangdang.ddframe.rdb.sharding.api.rule.BindingTableRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.DataSourceRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.ShardingRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.TableRule;
import com.dangdang.ddframe.rdb.sharding.api.strategy.database.DatabaseShardingStrategy;
import com.dangdang.ddframe.rdb.sharding.api.strategy.table.TableShardingStrategy;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;

/**
 * @ClassName ShardingDataSourceConfig
 * @Description TODO
 * @Author mc
 * @Date 2019/8/29 15:39
 * @Version 1.0
 **/
@Configuration
@MapperScan(basePackages ="com.coates.sharding.dao*")
@Log4j2
public class ShardingDataSourceConfig {


    /**
     * 配置数据源0，数据源的名称最好一定的规则，方便配置计算规则
     */
    @Bean(name = "dataSource0")
    @ConfigurationProperties(prefix = "sharding.jdbc.datasource.db0")
    public DataSource dataSource0() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "dataSource1")
    @ConfigurationProperties(prefix = "sharding.jdbc.datasource.db1")
    public DataSource dataSource1() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "dataSource2")
    @ConfigurationProperties(prefix = "sharding.jdbc.datasource.db2")
    public DataSource dataSource2() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "dataSource3")
    @ConfigurationProperties(prefix = "sharding.jdbc.datasource.db3")
    public DataSource dataSource3() {
        return DataSourceBuilder.create().build();
    }

    /**
     * 配置数据源规则，即将多个数据源交给sharding-jdbc管理，并且可以设置默认的数据源，
     * 当表没有配置分库规则时会使用默认的数据源
     * @param dataSource0
     * @param dataSource1
     * @return
     */
    @Bean
    public DataSourceRule dataSourceRule(@Qualifier("dataSource0") DataSource dataSource0,
                                         @Qualifier("dataSource1") DataSource dataSource1,
                                         @Qualifier("dataSource2") DataSource dataSource2,
                                         @Qualifier("dataSource3") DataSource dataSource3){
        Map<String, DataSource> dataSourceMap = new HashMap<>(); //设置分库映射
        dataSourceMap.put("dataSource0", dataSource0);
        dataSourceMap.put("dataSource1", dataSource1);
        dataSourceMap.put("dataSource2", dataSource2);
        dataSourceMap.put("dataSource3", dataSource3);
        return new DataSourceRule(dataSourceMap, "dataSource0"); //设置默认库，两个库以上时必须设置默认库。默认库的数据源名称必须是dataSourceMap的key之一
    }

    /**
     * 配置数据源策略和表策略，具体策略需要自己实现
     * @param dataSourceRule
     * @return
     */
    @Bean
    public ShardingRule shardingRule(DataSourceRule dataSourceRule){
        //具体分库分表策略
        TableRule orderTableRule = TableRule.builder("user")
                .actualTables(Arrays.asList("user"))
                .tableShardingStrategy(new TableShardingStrategy("id", new ModuloTableShardingAlgorithm()))
                .dataSourceRule(dataSourceRule)
                .build();

        //绑定表策略，在查询时会使用主表策略计算路由的数据源，因此需要约定绑定表策略的表的规则需要一致，可以一定程度提高效率
        List<BindingTableRule> bindingTableRules = new ArrayList<BindingTableRule>();
        bindingTableRules.add(new BindingTableRule(Arrays.asList(orderTableRule)));
        return ShardingRule.builder()
                .dataSourceRule(dataSourceRule)
                .tableRules(Arrays.asList(orderTableRule))
                .bindingTableRules(bindingTableRules)
                .databaseShardingStrategy(new DatabaseShardingStrategy("id", new ModuloDatabaseShardingAlgorithm()))
                .tableShardingStrategy(new TableShardingStrategy("id", new ModuloTableShardingAlgorithm()))
                .build();
    }

    /**
     * 创建sharding-jdbc的数据源DataSource，MybatisAutoConfiguration会使用此数据源
     * @param shardingRule
     * @return
     * @throws SQLException
     */
    @Bean(name="dataSource")
    public DataSource shardingDataSource(ShardingRule shardingRule) throws SQLException {
        return ShardingDataSourceFactory.createDataSource(shardingRule);
    }

    /**
     * 需要手动配置事务管理器
     * @param dataSource
     * @return
     */
    @Bean
    public DataSourceTransactionManager transactitonManager(@Qualifier("dataSource") DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "test1SqlSessionFactory")
    @Primary
    public SqlSessionFactory testSqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:com.coates/sharding/mapper/*/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "test1SqlSessionTemplate")
    @Primary
    public SqlSessionTemplate testSqlSessionTemplate(@Qualifier("test1SqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
