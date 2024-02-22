package com.dawn.shortlink;

import com.dawn.shortlink.domain.utils.Base62CodeUtil;
import com.google.common.hash.Hashing;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class SampleTest {

    @Test
    public void Base62enCodeTest(){
//        Base62Code demo = new Base62Code();
        int turn = 900;

        String res;
        while (turn-->0){
            res = Base62CodeUtil.encode(UUID.randomUUID().toString());
            System.out.println(res);
        }

    }

    @Test
    public void Base62deCodeTest(){
//        Base62Code demo = new Base62Code();

        String testlist = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz000";

        for(int idx=0;idx<62;idx++){
            System.out.println(
                    testlist.charAt(idx)
                    + " = "
                    + Base62CodeUtil.decode(testlist.substring(idx,idx+1))
            );
        }


    }

    @Test
    public void Base62CompareTest(){
        int turn = 10000;
        String tmp = null;
        String org = null;
        long orgHash = 0L;
        long res = 0L;

        while(turn-->0){
            org = UUID.randomUUID().toString();
            orgHash = Hashing.murmur3_32_fixed().hashString(org, StandardCharsets.UTF_8).padToLong();
            tmp = Base62CodeUtil.encode(org);
            res = Base62CodeUtil.decode(tmp);
            System.out.println(
                    "org="+org
                    + "; shortURL="+tmp
                    + "; compare=" + (res==orgHash)
            );
            if(orgHash!=res) break;
        }

        System.out.println(
                "org="+org
                + "; shortURL="+tmp
                +"; Hash1=" + orgHash
                +"; Hash2=" + res
        );

    }

    @Test
    public void Ts(){
        int n = 100000000;
        double p  =0.0001;
        int res = (int) (-n * Math.log(p) / (Math.log(2) * Math.log(2)));
        System.out.println(res);
        System.out.println(Long.MAX_VALUE);
        System.out.println(Double.MIN_VALUE);
    }

    @Test
    public void Tsts(){
        for(int i=0;i<1e8;i++) {
            String str = UUID.randomUUID().toString();
            System.out.println("进度:" + i + " str:" + str );
        }
    }


}
