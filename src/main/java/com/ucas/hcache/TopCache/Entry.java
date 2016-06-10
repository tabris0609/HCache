package com.ucas.hcache.TopCache;

pubilc class Entry
{
    private String key;
    private String value;
    private int cnt;
    private double hot;
    private long long time_stamp;
    public Entry(String k, String v)
    {
        this.key =k;
        this.value = v;
        this.cnt =0;
        this.hot =0;
    }
}