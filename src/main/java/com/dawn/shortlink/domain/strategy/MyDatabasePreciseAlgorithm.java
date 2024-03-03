package com.dawn.shortlink.domain.strategy;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

public class MyDatabasePreciseAlgorithm extends MyBasePreciseAlgorithm implements PreciseShardingAlgorithm<String> {


    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<String> preciseShardingValue) {
//        System.out.println("测试开始"+preciseShardingValue.toString());
        if(!preciseShardingValue.getColumnName().equals(SHARDING_CLM)) return preciseShardingValue.getLogicTableName();
        long position = getHash(preciseShardingValue.getValue())%SUM_NUM;
        position = position/DB_NUM; // 得到数据库坐标
        for (String e : collection)
            if(e.endsWith(String.valueOf(position))) return e;
        return preciseShardingValue.getLogicTableName();
    }
}
