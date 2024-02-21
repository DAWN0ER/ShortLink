package com.dawn.shortlink;

import com.dawn.shortlink.domain.utils.BloomFilterUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.BitSet;
import java.util.HashSet;
import java.util.UUID;

public class RedisTest extends ShortLinkApplicationTests{

    @Autowired
    RedisTemplate redisTemplate;


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
        BloomFilterUtil BL = new BloomFilterUtil(1000,0.01,redisTemplate);
        BloomFilterUtil BL2 = new BloomFilterUtil(100,0.1,redisTemplate);
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
        BloomFilterUtil filter = new BloomFilterUtil(1000000,0.0,redisTemplate);
        HashSet<String> set = new HashSet<>();
        System.out.println("测试开始");
        for(int i=0;i<5e4;i++){
            String str = UUID.randomUUID().toString();
            if(i%10000==0)System.out.println("进度:"+i/500+"%测试; ");
            if(i<1000) set.add(str);
            filter.add(str,redisTemplate);
        }
        int count1 = 0;
        int count2 = 0;
        for(String s : set){
            if(filter.mightContains(s,redisTemplate)) count1++;
        }
        System.out.println("一轮结束");
        int tmp =100000;
        for(int i=0;i<tmp;i++){
            String s;
            do{
                s = UUID.randomUUID().toString();
            }while(set.contains(s));
            if(filter.mightContains(s,redisTemplate)) count2++;
        }

        System.out.println(
                "存在的判断率=" + count1 +"/" + set.size()
                +"不存在但误判" + count2 +"/" + tmp
        );

    }

}
