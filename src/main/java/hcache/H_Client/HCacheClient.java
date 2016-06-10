package hcache.H_Client;

/**
 * Created by liujingkun on 16/6/10.
 */



import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.HBaseAdmin;

import java.io.IOException;

public class HCacheClient {
    private boolean memecached = false;
    private boolean local_cache = false;
    private Memcache memcache;
    private cache local;
    private String tableName;
    public HCacheClient() {

    }
    public void put(String key, String value){
        if(local_cache)
        {
             memecached.delete(key)
        }
        if(memecached)
        {
            local.delete(key)
        }

    }

    public String get(){
        if(local_cache.cotain(key)) {
            return local_cache.get(key)
        }
        if(memecached.contain(key)) {
            return memecached.get(key)
        }

        return
    }
}
