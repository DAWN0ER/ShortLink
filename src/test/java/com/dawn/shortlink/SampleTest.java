package com.dawn.shortlink;

import com.dawn.shortlink.domain.Base62CodeUtil;
import com.google.common.hash.Hashing;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Date;
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
    }


}
