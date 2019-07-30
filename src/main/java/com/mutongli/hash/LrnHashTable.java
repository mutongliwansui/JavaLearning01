package com.mutongli.hash;

import java.util.Hashtable;

/**
 * @author mtl
 * @Description: HashTable学习
 * @date 2019/7/30 17:15
 */
public class LrnHashTable {

    public void testPutNull(){
        Hashtable<String,Object> hb = new Hashtable<>();
        String key = null;
//        hb.put(key,1);
        hb.put("123",null);
        System.out.println(hb);
    }


    public static void main(String[] args) {
        new LrnHashTable().testPutNull();
    }
}
