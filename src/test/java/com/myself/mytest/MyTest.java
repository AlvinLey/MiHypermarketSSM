package com.myself.mytest;

import com.myself.utils.MD5Util;
import org.junit.Test;

/**
 * @Author：摘到星星了吗
 * @Date:2022/2/14 -02-14-19:50
 * @Description: com.myself.mytest
 * @version:1.0
 */
public class MyTest {
    @Test
    public void testMD5(){
        String mi = MD5Util.getMD5("000000");
        System.out.println(mi);

    }
}
