package com.ucas.hcache.HBaseOperator;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.HBaseAdmin;

import java.io.IOException;
import org.apache.hadoop.conf

/**
 * Created by hou on 2016/6/10.
 */
public class TableOperator {
    private String tableName;
    private  Configuration configuration;

    boolean create(String name) throws IOException {
        tableName =name;
        // create table descriptor
        HTableDescriptor htd = new HTableDescriptor(TableName.valueOf(tableName));
        // create column descriptor
        HColumnDescriptor cf = new HColumnDescriptor("res");
        htd.addFamily(cf);
        // configure HBase
        configuration = HBaseConfiguration.create();
        HBaseAdmin hAdmin = new HBaseAdmin(configuration);
        if(hAdmin.tableExists(tableName)){
            hAdmin.disableTable(tableName);
            hAdmin.deleteTable(tableName);
        }
        hAdmin.createTable(htd);
        hAdmin.close();
        return  true;
    }
}
