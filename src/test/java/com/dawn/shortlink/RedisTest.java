package com.dawn.shortlink;

import net.bytebuddy.implementation.bytecode.ShiftRight;
import org.junit.Test;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.HashSet;
import java.util.UUID;

public class RedisTest extends ShortLinkApplicationTests{

    @Autowired
    RedisTemplate<String,String> redisTemplate;
    @Autowired
    @Qualifier("BloomFilterRedisson")
    RedissonClient client;
    @Autowired
    @Qualifier("UrlCacheRedisson")
    RedissonClient cacheClient;


    @Test
    public void redissonTest(){
        String name = "longUrlBloomFilter";
        RBloomFilter<Object> bloomFilter = client.getBloomFilter(name);
        if(bloomFilter.contains("www.repeat.com")) System.out.println("yes");
        else System.out.println(bloomFilter.getExpectedInsertions());
    }

    @Test
    public void simpleTest(){
        System.out.println(cacheClient.getBucket("sss").get());
        System.out.println(cacheClient.getBucket("sss").delete()+"\n");
        cacheClient.getKeys().getKeys().forEach(System.out::println);

    }

    @Test
    public void bfTest(){
        String u = "http://localhost:8787/api/shortlink/test";
        RBloomFilter<Object> filter = client.getBloomFilter("longUrlBloomFilter");
        System.out.println(
                filter.contains(u)
        );
    }

    @Test
    public void bloomMutilTest(){

        HashSet<String> set = new HashSet<>();
        String name = "testBF";
        RBloomFilter<Object> filter = client.getBloomFilter(name);
        filter.tryInit(10000000,0.01);

        System.out.println("测试开始");
        int tmp =100;

        for(int i=0;i<tmp*100;i++){
            String str = UUID.randomUUID().toString();
            if(i%(tmp*10)==0) System.out.println(i/tmp+"%");
            if(i<tmp*10) set.add(str);
            filter.add(str);
        }
        int count1 = 0;
        int count2 = 0;
        for(String s : set){
            if(filter.contains(s)) count1++;
        }
        System.out.println("一轮结束");
        for(int i=0;i<tmp;i++){
            String s;
            do{
                s = UUID.randomUUID().toString();
            }while(set.contains(s));
            if(filter.contains(s)) count2++;
        }

        System.out.println(
                "存在的判断率=" + count1 +"/" + set.size()
                +"\n不存在但误判" + count2 +"/" + tmp
        );

        filter.delete();


    }

    @Test
    public void cacheTest(){
        String shortUrl = "asdewrzds";
//        String url = "http://localhost:8787/api/shortlink/test";
//        cacheClient.getBucket(shortUrl).set(url);
        System.out.println(cacheClient.getBucket(shortUrl));
        System.out.println(cacheClient.getBucket(shortUrl).get());
//        System.out.println(cacheClient.getBucket(shortUrl).get().equals("ds"));
        cacheClient.getKeys().getKeys().forEach(System.out::println);
        cacheClient.getBucket(shortUrl).delete();
    }

}
