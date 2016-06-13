package com.ucas.hcache.memcached;

/**
 * Created by hou on 2016/6/13.
 */

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.exception.MemcachedException;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;


public class MemCachedHash {
    private static final String ip="172.17.100.16";
    private static final int port=12000;
    private static MemcachedClientBuilder mcb=null;
    private static MemcachedClient mc=null;
    static{
        try {
            mcb=new XMemcachedClientBuilder();
            mc=mcb.build();
            mc.addServer(ip, port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public boolean set(String key, HashMap<String,String> value, int exp) {
        boolean flag=false;
        try {
            flag=mc.add(key, exp, value);
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (MemcachedException e) {
            e.printStackTrace();
        }
        return flag;
    }
    public HashMap<String,String> get(String key){
        HashMap<String,String> value=null;
        try {
            value= mc.get(key);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (MemcachedException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return value;
    }
    public  boolean delete(String key) {
        boolean flag = false;
        Object obj = null;
        try {
            obj = mc.delete(key);
            return obj != null;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (MemcachedException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return flag;
    }
}
