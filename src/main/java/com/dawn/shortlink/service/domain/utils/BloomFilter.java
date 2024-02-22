package com.dawn.shortlink.service.domain.utils;


import com.google.common.hash.Hashing;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;

/*
自己使用的 bloom filter 仅支持 String 类型, 数据固定
本身作为 redis 的 key 来操作 redis 里面储存的位图
最多支持 1e9/fpp=0.0001 数据的插入
 */

public class BloomFilter implements Serializable {

    private final int numBits;
    private final int numHashFunctions;


    public BloomFilter(int expectedInsertions, double fpp, RedisTemplate template) {
        this.numBits = optimalNumOfBits(expectedInsertions, fpp);
        this.numHashFunctions = optimalNumOfHashFunctions(expectedInsertions, numBits);
        template.opsForValue().set(this, new long[((numBits-1) >> 6) + 1]); // 这个是抄的 BitMap 的, 这个类本身不占用空间
    }

    /*
    计算通过 bloom 过滤器产生的 offset , 也是直接借鉴的 guava 的代码
     */
    private int[] murmurHashOffset(String str){
        long hash64 = Hashing.murmur3_128().hashString(str, StandardCharsets.UTF_8).padToLong();
        int hash32_1 = (int) hash64;
        int hash32_2 = (int) (hash64 >>> 32);
        int[] offsets = new int[numHashFunctions];
        for(int idx = 1;idx<=numHashFunctions;idx++){
            int combine = hash32_1 + hash32_2*idx;
            if(combine<0) combine=~combine;
            offsets[idx-1] = combine%numBits;
        }
        return offsets;
    }

    public boolean mightContains(String string,@NotNull RedisTemplate redis){
        ValueOperations ops = redis.opsForValue();
        int[] offsets = this.murmurHashOffset(string);
        for(int offset:offsets){
            if(Boolean.FALSE.equals(ops.getBit(this, offset))) return false;
        }
        return true;
    }

    public void add(String str,@NotNull RedisTemplate redis ){
        ValueOperations ops = redis.opsForValue();
        int[] offsets = this.murmurHashOffset(str);
        for(int offset:offsets){
            ops.setBit(this,offset,true);
        }
    }

    /*
        下面的全是借鉴的 guava 里面的 bloom filter 的方法
        计算最佳 hash 函数个数和计算最佳位图的大小
        */
    private static int optimalNumOfHashFunctions(int n, int m) {
        // (m / n) * log(2), but avoid truncation due to division!
        return Math.max(1, (int) Math.round((double) m / n * Math.log(2)));
    }

    private static int optimalNumOfBits(int n, double p) {
        if (p == 0) {
            p = 0.0001; // int 类型最小精确到万分之一能保证数据不溢出
        }
        return (int) (-n * Math.log(p) / (Math.log(2) * Math.log(2)));
    }



}
