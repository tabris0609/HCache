package com.ucas.hcache.HController;

import com.ucas.hcache.memcached.MemCached;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

// import package write or read .
/**
 * Created by liujingkun on 16/6/10.
 */

/*
   modify by hcs on 16/6/10
*/

public class HController {
    private boolean memecached = false;
    private boolean local_cache = false;
    private MemCached memcache;
    private cache local;
    private String tableName;
    public HController() throws IOException{
        FileInputStream in = new FileInputStream("conf/HCache.conf");
        Properties properties = new Properties();
        properties.load(in);

        this.memecached = "on".equals(properties.getProperty("redis_cache"));
        this.local_cache = "on".equals(properties.getProperty("local_cache"));

    }

    public void put(String key, String value){
        if(local_cache)
        {
             memcache.delete(key);
        }
        if(memecached)
        {
            local.delete(key);
        }
        write()
    }

    public String get(String key){
        if(local_cache&&local.cotain(key)) {
            return local_cache.get(tableName,key);
        }
        if(memecached&&memcache.contain(key)) {
            return memcache.get(key);
        }

        String value = read(tableName,key);
        if(local_cache)
            local.set(key,value);

        if(memecached)
            memcache.set(key,value,1000);

        return value;
    }
}
