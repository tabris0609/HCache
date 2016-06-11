package com.ucas.hcache.TopCache;

public class Entry
{
    private String key;
    private String value;
    //private long long time_stamp;
    public double hot;

    public int cnt;


    public Entry()
    {
        this.cnt =0;
        this.hot =0;
    }
    public  String getKey()
    {
        return key;
    }
    public String getValue()
    {
        return value;
    }

    public void setValue(String val)
    {
        this.value = val;
    }
    public void setKey(String key)
    {
        this.key = key;
    }
}