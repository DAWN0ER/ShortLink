//package com.dawn.shortlink.configuration;
//
//import org.apache.shardingsphere.shardingjdbc.jdbc.adapter.AbstractDataSourceAdapter;
//import org.apache.shardingsphere.shardingjdbc.jdbc.core.datasource.ShardingDataSource;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.AutoConfigureAfter;
//import org.springframework.boot.autoconfigure.quartz.QuartzDataSource;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Lazy;
//
//import javax.annotation.Resource;
//import javax.sql.DataSource;
//
//@Configuration
//public class QuartzDataSourceConfiguration {
//
//    @Lazy
//    @Resource(name = "shardingDataSource")
//    AbstractDataSourceAdapter shardingDataSource;
//
//    @Bean
//    @QuartzDataSource
//    public DataSource quartzDatSource(){
//        return shardingDataSource.getDataSourceMap().get("quartz");
//    }
//
//}
