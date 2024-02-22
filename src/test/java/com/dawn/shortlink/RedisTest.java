package com.dawn.shortlink;

import com.dawn.shortlink.service.domain.utils.BloomFilter;
import com.dawn.shortlink.service.domain.utils.BloomFilterSet;
import com.google.common.hash.Funnels;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.nio.charset.StandardCharsets;
import java.util.BitSet;
import java.util.HashSet;
import java.util.UUID;

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
    public void bloomFilterTest(){
        BloomFilter BL = new BloomFilter(1000,0.01,redisTemplate);
        BloomFilter BL2 = new BloomFilter(100,0.1,redisTemplate);
        System.out.println(
                "BL="+BL.hashCode()+"; "
                +"BL2="+BL2.hashCode()+"; "
        );
        BL.add("test",redisTemplate);
        BL.add("tttt",redisTemplate);
        System.out.println(
                BL.mightContains("test",redisTemplate)+"; "+
                BL2.mightContains("test",redisTemplate)+"; "
        );
    }


    @Test
    public void bloomMutilTest(){
//        BloomFilter filter = new BloomFilter(1000000,0.0,redisTemplate);
        HashSet<String> set = new HashSet<>();

        BloomFilter filter = BFset.getBloomFilter("longUrl");

        System.out.println("测试开始");
        int tmp =1000;

        for(int i=0;i<tmp*100;i++){
            String str = UUID.randomUUID().toString();
            if(i%tmp==0) System.out.print("\r"+i/tmp);
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
    public void guavaStudyTest(){
        com.google.common.hash.BloomFilter filter = com.google.common.hash.BloomFilter.create(Funnels.stringFunnel(StandardCharsets.UTF_8),100,0.01);
        filter.put("sd");
    }

}
