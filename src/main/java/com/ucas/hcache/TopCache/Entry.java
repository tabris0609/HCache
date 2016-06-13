package com.ucas.hcache.TopCache;

import java.util.HashMap;

public class Entry
{
    private String key;
    private HashMap<String,String> value = new HashMap<String,String>();
    //private String value;
    //private long long time_stamp;
    public double hot;

    public int cnt;


    public Entry()
    {
        this.cnt =0;
        this.hot =0;
    }

    /**
     *
     * @return
     */
    public  String getKey()
    {
        return key;
    }

    /**
     *
     * @return
     */
    public HashMap<String, String> getValue()
    {
        return value;
    }

    /**
     *
     * @param val
     */
    public void setValue(HashMap val)
    {
        this.value = val;
    }

    /**
     *
     * @param key
     */
    public void setKey(String key)
    {
        this.key = key;
    }
}