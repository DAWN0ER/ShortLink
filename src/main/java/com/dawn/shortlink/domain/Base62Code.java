package com.dawn.shortlink.domain;

import com.google.common.hash.Hashing;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/*
Base62 编码解码器

 */
@Component
public class Base62Code {

    private static final long BASE = 62;
    private static final byte[] KEY_LIST = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".getBytes();


    /*
    编码器
     */
    public String encode(String originUrl){
        return encodeFormLong(murmurHashString(originUrl));
    }

    private static long murmurHashString(String str){
        return Hashing.murmur3_32_fixed().hashString(str, StandardCharsets.UTF_8).padToLong();
    }

    private String encodeFormLong(Long num){
        byte[] str = new byte[6];
        int idx =0;
        while(num!=0){
            str[idx++] = KEY_LIST[(int) (num%BASE)];
            num = num/BASE;
        }
        return new String(str).substring(0,idx);
    }

    /*
    解码器
     */
    public long decode(String str){
        long res = 0L;
        int len = str.length();
        byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
        while(len-->0){
            res = findValOfChar(bytes[len]) + res*BASE;
        }
        return res;
    }

    private long findValOfChar(byte c){
        if(c<='9'){
            return c-'0' +0L;
        }else if(c<='Z'){
            return c-'A'+10L;
        }else if(c<='z') {
            return c - 'a' + 36L;
        }
        return -1L;
    }



}
