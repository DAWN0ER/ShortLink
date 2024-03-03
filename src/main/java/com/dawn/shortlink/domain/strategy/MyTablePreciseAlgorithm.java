package com.dawn.shortlink.domain.strategy;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

public class MyTablePreciseAlgorithm extends MyBasePreciseAlgorithm implements PreciseShardingAlgorithm<String> {

    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<String> preciseShardingValue) {
        if(!preciseShardingValue.getColumnName().equals(SHARDING_CLM)) return preciseShardingValue.getLogicTableName();
        long position = getHash(preciseShardingValue.getValue())%TABLE_NUM; // 得到table坐标
        for (String e : collection)
            if(e.endsWith(String.valueOf(position))) return e;
        return preciseShardingValue.getLogicTableName();
    }
}
