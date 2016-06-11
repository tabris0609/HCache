package com.ucas.hcache.HController;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import com.ucas.hcache.TopCache.TopKCache;
import com.ucas.hcache.memcached.MemCached;

/**
 * Created by liujingkun on 16/6/10.
 */

/*
   modify by hcs on 16/6/10
*/

public class HController {
    private boolean is_memcached = false;
    private boolean is_local_cache = false;
    private MemCached memcache;
    private TopKCache topkcache;

    public HController() throws IOException{
        FileInputStream in = new FileInputStream("conf/HCache.conf");
        Properties properties = new Properties();
        properties.load(in);

        this.is_memcached = "on".equals(properties.getProperty("memcached_cache"));
        this.is_local_cache = "on".equals(properties.getProperty("local_cache"));

        if (this.is_memcached) {
            this.memcache = new MemCached();
        }
        if (this.is_local_cache) {
//            this.topkcache = new TopKCache(2000,"hot");
        }

    }
    public  void  createTable(String tableName,String column_family[]) throws IOException {
        TableOperator.createTable(tableName,column_family);
    }
    public void put(String tableName,String row_key, String column_family,String column_key,String value){
        if(is_local_cache)
        {
             memcache.delete(tableName+row_key+column_family+column_key);
        }
        if(is_memcached)
        {
//            topkcache.remove(tableName+row_key+column_family+column_key);
        }
        try {
            TableOperator.putData(tableName,row_key,column_family,column_key,value);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String get(String tableName,String row_key, String column_family,String column_key){
        String key =tableName+row_key+column_family+column_key;
        if(is_local_cache) {
            String str = topkcache.get(key);
            if(str!=null)
                return  str;
        }
        if(is_memcached) {
            String str = memcache.get(key);
            if(str!=null)
                return str;
        }
        String value = null;
        try {
            value = TableOperator.getData(tableName,row_key,column_family,column_key);
            if(is_local_cache){
                topkcache.set(key,value);
            }
            if(is_memcached){
                memcache.set(key,value,1000);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  value;
    }
}
