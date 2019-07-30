package com.mutongli.hash;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mtl
 * @Description: HashMap学习
 * @date 2019/7/30 17:26
 */
public class LrnHashMap {
    public void testPutReturn(){
        HashMap hb = new HashMap();
        Object rt1 = hb.put("123","345"); //HashMap的put方法返回的是之前的值,此时hb是第一次存入key="123",所以之前的值为空
        System.out.println(rt1);
        Object rt2 = hb.put("123","456"); //此时是第二次存入key="123",所以之前的值是345
        System.out.println(rt2);
    }

    public static void main(String[] args) {
        new LrnHashMap().testPutReturn();
    }
}
