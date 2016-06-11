package com.ucas.hcache.TopCache;

public class Entry
{
    private String key;
    private String value;
    //private long long time_stamp;

    public int cnt;
    public double hot;

    public Entry(String k, String v)
    {
        this.key =k;
        this.value = v;
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
}