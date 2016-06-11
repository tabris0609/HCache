package com.ucas.hcache.HController;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.mortbay.util.IO;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by hou on 2016/6/10.
 */
public class TableOperator {
    private static Configuration conf = null;
    private static HashMap<String, HTable> hTables = null;

    static {
        conf = HBaseConfiguration.create();
        hTables = new HashMap<String, HTable>();
    }

    /**
     *
     * @param tableName
     * table name to create
     * @param columnFamilys
     * hbase columnFamilys of "tablename"
     * @throws IOException
     */
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

    /**
     *
     * @param tableName
     * @throws IOException
     */
    public static void deleteTable(String tableName) throws IOException {
        try{
            HBaseAdmin hAdmin = new HBaseAdmin(conf);
            hAdmin.disableTable(tableName);
            hAdmin.deleteTable(tableName);

            hTables.remove(tableName); //delete hTable for the tableName in hTables
            System.out.println("Delete table successfully!");
        }
        catch (MasterNotRunningException e) {
            e.printStackTrace();
        }
        catch (ZooKeeperConnectionException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param tableName
     * @param columnFamily
     * @param row_key
     * @param col_key
     * @param value
     * @throws IOException
     */
    public static void putData(String tableName, String columnFamily,
                               String row_key, String col_key, String value) throws IOException {
        HTable hTable = null;
        hTable = gethTable(tableName);

        Put put = new Put(row_key.getBytes());
        put.add(columnFamily.getBytes(), col_key.getBytes(), value.getBytes());
        hTable.put(put);
    }

    /**
     *
     * @param tableName
     * @param columnFamily
     * @param row_key
     * @param col_key
     * @param value
     * @throws IOException
     */
    public static void getData(String tableName, String columnFamily,
                               String row_key, String col_key, String value) throws IOException {
        HTable hTable = null;
        hTable = gethTable(tableName);


    }

    /**
     *
     * @param tableName
     * @return
     * @throws IOException
     */
    private static HTable gethTable(String tableName) throws IOException{
        if (hTables.containsKey(tableName)) {
            return hTables.get(tableName);
        }
        else {
            return new HTable(conf, tableName);
        }
    }
}
