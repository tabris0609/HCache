package com.ucas.hcache.HController;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import com.google.protobuf.ByteString;
import com.ucas.hcache.TopCache.TopKCache;
import com.ucas.hcache.memcached.MemCached;

/**
 * Created by liujingkun on 16/6/10.
 */

/**
 * modify by hcs on 16/6/10
 */


public class HController {
    private boolean is_memcached = false;
    private boolean is_local_cache = false;
    private String strategy = "hot";
    private MemCached memcache;
    private TopKCache topkcache;

    /**
     *
     * @throws IOException
     */
    public HController() throws IOException{
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("HCache.conf");
        Properties properties = new Properties();
        properties.load(in);

        this.is_memcached = "on".equals(properties.getProperty("memcached_cache"));
        this.is_local_cache = "on".equals(properties.getProperty("local_cache"));
        if (properties.getProperty("strategy").toLowerCase().contentEquals("lru")) {
            this.strategy = "lru";
        }
        double threshold = Double.parseDouble(properties.getProperty("threshold"));
        int cache_size =Integer.parseInt(properties.getProperty("cacheSize"));
        /*System.out.println(this.is_local_cache);
        System.out.println(this.is_memcached);*/
        if (this.is_memcached) {
            this.memcache = new MemCached();
        }
        if (this.is_local_cache) {
            this.topkcache = new TopKCache(cache_size, threshold, strategy);
        }

    }

    /**
     *
     * @param tableName table name
     * @param column_family column family
     * @throws IOException table name error or column family error
     */
    public  void  createTable(String tableName,String column_family[]) throws IOException {
        TableOperator.createTable(tableName,column_family);
    }
    public void put(String tableName,String row_key, String column_family,String column_key,String value){
        if(is_local_cache)
        {
             topkcache.remove(tableName+row_key+column_family+column_key);
        }
        if(is_memcached)
        {
            memcache.delete(tableName+row_key+column_family+column_key);
        }
        try {
            TableOperator.putData(tableName,row_key,column_family,column_key,value);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param tableName table name
     * @param row_key row key
     * @param column_family column family
     * @param column_key column key
     * @return value
     */
    public String get(String tableName,String row_key, String column_family,String column_key){
        String key =tableName+row_key+column_family+column_key;
        if(is_local_cache) {
            String str = topkcache.get(key);
            if(str!=null)
//            	System.out.println(str);
                return  str;
        }
        if(is_memcached) {
            String str = memcache.get(key);
            if(str!=null)
                return str;
        }
        String value = null;
        try {
            HashSet<String> s = new HashSet<String>();
            s.add(column_key);
            HashMap<String,String> cur_map= TableOperator.getData(tableName,row_key,column_family,s);
            value = cur_map.get(column_key);
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
    public HashMap<String,String> get(String tableName, String row_key, String column_family, Set<String> column_key){
        String key =tableName+row_key+column_family;
        HashMap<String,String> ret =new HashMap<String, String>();
        Set<String> column_key_set=column_key;
        if(is_local_cache) {
            for (String column_key1:column_key_set) {
                String str = topkcache.get(key+column_key1);
                if(str!=null)
                {
                    ret.put(column_key1,str);
                    column_key_set.remove(column_key1);
                }
            }
            if(column_key_set.size()==0) {
                return ret;
            }
        }
        if(is_memcached) {
            for(String column_key1:column_key_set){
                String str = memcache.get(key+column_key1);
                if(str!=null){
                    ret.put(column_key1,str);
                    column_key_set.remove(column_key1);
                }
            }
            if(column_key_set.size()==0)
            {
                return ret;
            }
        }
        try {
            HashMap<String,String> tem = TableOperator.getData(tableName,row_key,column_family,column_key_set);
            Iterator<String> it =tem.keySet().iterator();
            while(it.hasNext()) {
                String value =it.next();
                if (is_local_cache) {
                    topkcache.set(key+value, tem.get(value));
                }
                if (is_memcached) {
                    memcache.set(key+value, tem.get(value), 1000);
                }
                ret.put(value,tem.get(value));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  ret;
    }

    /**
     *  use for test
     *  if could get from local cache return 1
     *                    memcached retrun 2
     *                    hbase return 3
     * @param tableName
     * @param row_key
     * @param column_family
     * @param column_key
     * @return
     */
    public int test_get(String tableName,String row_key, String column_family,String column_key){
        String key =tableName+row_key+column_family+column_key;
        if(is_local_cache) {
            String str = topkcache.get(key);
            if(str!=null)
                return  1;
        }
        if(is_memcached) {
            String str = memcache.get(key);
            if(str!=null)
                return 2;
        }
        return  3;
    }
}
