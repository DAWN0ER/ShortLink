package com.dawn.shortlink.domain.strategy;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

public abstract class MyBasePreciseAlgorithm {

    protected static final int DB_NUM = 2;
    protected static final int TABLE_NUM = 2;
    protected static final int SUM_NUM = DB_NUM*TABLE_NUM;

    protected static final String SHARDING_CLM = "short_url";

    protected long getHash(String str){
        return Hashing.murmur3_32_fixed().hashString(str, StandardCharsets.UTF_8).padToLong();
    }


}
