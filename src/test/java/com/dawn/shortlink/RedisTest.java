package com.dawn.shortlink;

import com.dawn.shortlink.domain.utils.BloomFilter;
import com.dawn.shortlink.domain.utils.BloomFilterSet;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.BitSet;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.apache.naming.SelectorContext.prefix;

public class RedisTest extends ShortLinkApplicationTests{

    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    BloomFilterSet BFset;


    @Test
    public void sampleTest(){
        ValueOperations ops = redisTemplate.opsForValue();
        BitSet set = new BitSet(10240);
        ops.set("tempkey",set);
        set = null;
        System.out.println(ops.size("tempkey"));
        ops.setBit("tempkey",1,true);
        System.out.println(ops.getBit("tempkey",1));
    }


    @Test
    public void bloomMutilTest(){

        HashSet<String> set = new HashSet<>();
        String name = "longUrlBloomFilter";
        BloomFilter filter = BFset.getBloomFilter(name);

        System.out.println("测试开始");
        int tmp =100;

        for(int i=0;i<tmp*100;i++){
            String str = UUID.randomUUID().toString();
            if(i%(tmp*10)==0) System.out.println(i/tmp+"%");
            if(i<tmp*10) set.add(str);
            filter.add(str,redisTemplate);
        }
        int count1 = 0;
        int count2 = 0;
        for(String s : set){
            if(filter.mightContains(s,redisTemplate)) count1++;
        }
        System.out.println("一轮结束");
        for(int i=0;i<tmp;i++){
            String s;
            do{
                s = UUID.randomUUID().toString();
            }while(set.contains(s));
            if(filter.mightContains(s,redisTemplate)) count2++;
        }

        System.out.println(
                "存在的判断率=" + count1 +"/" + set.size()
                +"\n不存在但误判" + count2 +"/" + tmp
        );


    }


    @Test
    public void simpleTest(){
        String name = "longUrlBloomFilter";
//        BFset.getBloomFilter(name).add();
    }



}
