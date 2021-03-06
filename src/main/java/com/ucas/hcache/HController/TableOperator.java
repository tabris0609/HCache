package com.ucas.hcache.HController;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.ConcatenatedLists;
import org.mortbay.util.IO;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by liujingkun on 2016/6/10.
 */
public class TableOperator {
    private static Configuration conf = null;
    private static HashMap<String, HTable> hTables = null;
    static {
        conf = HBaseConfiguration.create();
        //conf.set("hbase.master", "172.17.100.16:60000")
        //conf.set(HConstants.ZOOKEEPER_QUORUM, "172.17.100.16:2181");
        conf.set("hbase.zookeeper.quorum", "hcache");
        conf.set("hbase.zookeeper.property.clientPort", "2181");
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
            return;
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
     * table name to delete
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
     * the name of table to insert data
     * @param columnFamily
     * hbase columnFamily
     * @param row_key
     * hbase row_key
     * @param col_key
     * hbase column key
     * @param value
     * value
     * @throws IOException
     */
    public static void putData(String tableName, String row_key,
                               String columnFamily, String col_key, String value) throws IOException {
        HTable hTable = null;
        hTable = get_hTable(tableName);

        Put put = new Put(row_key.getBytes());
        put.add(columnFamily.getBytes(), col_key.getBytes(), value.getBytes());
        hTable.put(put);
    }

    /**
     *
     * @param tableName
     * table name to operator
     * @param columnFamily
     * hbase columnFamily
     * @param row_key
     * hbase row_key
     * @param col_keys
     * hbase column key
     * @return String value
     * @throws IOException
     */
    public static HashMap<String, String> getData(String tableName, String row_key,
                                 String columnFamily) throws IOException {
        HTable hTable = null;
        HashMap<String, String> map = new HashMap<String, String>();
        hTable = get_hTable(tableName);
        Get get = new Get(row_key.getBytes());
        get.addFamily(columnFamily.getBytes());
        Result result = null;

        get.setCacheBlocks(false);
        result = hTable.get(get);

        for (Cell cell: result.rawCells()){
            map.put(new String(CellUtil.cloneQualifier(cell)),
                    new String(CellUtil.cloneValue(cell))
            );
        }
//        Iterator iter = map.entrySet().iterator();
//        while (iter.hasNext()){
//            Map.Entry entry = (Map.Entry) iter.next();
//            System.out.println(entry.getValue().toString());
//        }
        return map;
    }

    /**
     *
     * @param tableName
     * table name to get
     * @return htable
     * @throws IOException
     */
    private static HTable get_hTable(String tableName) throws IOException{
        if (hTables.containsKey(tableName)) {
            return hTables.get(tableName);
        }
        else {
            return new HTable(conf, tableName);
        }
    }
}
