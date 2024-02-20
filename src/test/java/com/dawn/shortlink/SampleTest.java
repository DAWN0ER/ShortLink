package com.dawn.shortlink;

import com.dawn.shortlink.domain.Base62Code;
import com.google.common.hash.Hashing;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.UUID;

public class SampleTest {

    @Test
    public void Base62enCodeTest(){
        Base62Code demo = new Base62Code();
        int turn = 900;

        String res;
        while (turn-->0){
            res = demo.encode(UUID.randomUUID().toString());
            System.out.println(res);
        }

    }

    @Test
    public void Base62deCodeTest(){
        Base62Code demo = new Base62Code();

        String testlist = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz000";

        for(int idx=0;idx<62;idx++){
            System.out.println(
                    testlist.substring(idx,idx+1)
                    + " = "
                    + demo.decode(testlist.substring(idx,idx+1))
            );
        }


    }

    @Test
    public void Base62CompareTest(){
        Base62Code coder = new Base62Code();
        int turn = 10000;
        String tmp = null;
        String org = null;
        long orgHash = 0L;
        long res = 0L;

        while(turn-->0){
            org = UUID.randomUUID().toString();
            orgHash = Hashing.murmur3_32_fixed().hashString(org, StandardCharsets.UTF_8).padToLong();
            tmp = coder.encode(org);
            res = coder.decode(tmp);
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
        Timestamp timestamp = new Timestamp(32341234543541L);
        System.out.println(timestamp);
        java.sql.Date date = new Date(timestamp.getTime());
        System.out.println(date.getTime());
    }


}
