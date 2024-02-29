package com.dawn.shortlink.configuration;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.boot.autoconfigure.quartz.QuartzDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class DatasourceConfiguration {

    @Bean("dataSourceShortLink")
    @ConfigurationProperties("spring.datasource.short-link")
    @Primary
    public DruidDataSource dataSourceShortLink () {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean("dataSourceQuartz")
    @ConfigurationProperties("spring.datasource.quartz")
    @QuartzDataSource
    public DruidDataSource dataSourceQuartz(){
        return DruidDataSourceBuilder.create().build();
    }
}
