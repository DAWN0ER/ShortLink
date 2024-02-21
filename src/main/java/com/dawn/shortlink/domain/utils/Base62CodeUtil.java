package com.dawn.shortlink.domain.utils;

import com.google.common.hash.Hashing;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/*
Base62 编码解码器

 */
@Component
public final class Base62CodeUtil {

    private static final long BASE = 62;
    private static final byte[] KEY_LIST = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".getBytes();

    /*
    编码器
     */
    public static String encode(String originUrl){
        return encodeFormLong(murmurHashString(originUrl));
    }

    private static long murmurHashString(String str){
        return Hashing.murmur3_32_fixed().hashString(str, StandardCharsets.UTF_8).padToLong();
    }

    private static String encodeFormLong(Long num){
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
    public static long decode(String str){
        long res = 0L;
        long temp;
        int len = str.length();
        byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
        while(len-->0){
            if((temp = findValOfChar(bytes[len]))<0) return -1L;
            res = temp + res*BASE;
        }
        return res;
    }

    private static long findValOfChar(byte c){
        if(c<='9'){
            return c-'0';
        }else if(c<='Z'){
            return c-'A'+10L;
        }else if(c<='z') {
            return c - 'a' + 36L;
        }
        return -1L;
    }



}
