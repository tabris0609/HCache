package com.ucas.hcache.HBaseOperator;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.conf.Configuration;


import java.io.IOException;

/**
 * Created by hou on 2016/6/10.
 */
public class TableOperator {
    private static Configuration conf = null;

    static {
        conf = HBaseConfiguration.create();
    }

    public static void createTable(String tableName, String[] columnFamilys) throws IOException {
        HBaseAdmin hAdmin = new HBaseAdmin(conf);
        if (hAdmin.tableExists(tableName)) {
            deleteTable(tableName);
        }
        HTableDescriptor htd = new HTableDescriptor(TableName.valueOf(tableName));
        for (String cf : columnFamilys){
            HColumnDescriptor hcf = new HColumnDescriptor(cf);
            htd.addFamily(hcf);
        }

        hAdmin.createTable(htd);
        hAdmin.close();
    }

    public static void deleteTable(String tableName) throws IOException {
        try{
            HBaseAdmin hAdmin = new HBaseAdmin(conf);
            hAdmin.disableTable(tableName);
            hAdmin.deleteTable(tableName);
            System.out.println("Delete table successfully!");
        }
        catch (MasterNotRunningException e) {
            e.printStackTrace();
        }
        catch (ZooKeeperConnectionException e) {
            e.printStackTrace();
        }
    }
}
