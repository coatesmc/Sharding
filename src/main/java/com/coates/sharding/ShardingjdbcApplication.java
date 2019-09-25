package com.coates.sharding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
//排除DataSourceConfiguratrion
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
//开启事物管理功能
@EnableTransactionManagement(proxyTargetClass = true)
public class ShardingjdbcApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShardingjdbcApplication.class, args);
    }

}
