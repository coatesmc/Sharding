package com.coates.sharding.configure;

import lombok.extern.log4j.Log4j2;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;

/**
 * @ClassName ShardingDataSourceConfig
 * @Description TODO
 * @Author mc
 * @Date 2019/8/29 15:39
 * @Version 1.0
 **/
@ComponentScan(basePackages = "com.coates.sharding")
@MapperScan("com.coates.sharding.dao*")
@Log4j2
public class ShardingDataSourceConfig {

}
