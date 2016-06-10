package com.ucas.hcache.TopCache;
import java.util.Hashtable;
import java.util.Objects;
import java.util.set;
/**
 * Created by sonny on 2016/6/10.
 */
public class TopKCache {

    private int cachesize;
    private Hashtable<String,Entry> nodes;
    private int currentSize;
    private boolean lru ;
    private boolean hot ;
    private int countPeriod = 5;
    private double alpha = 0.2;
    private int period = 0;

    private void hot_update_grade()
    {
        Set<String> keys = nodes.KeySet();
        Set<String>::iterator it = key.iterator();
        while(it.hasNext()){
            String str =it.next();
            Entry update = nodes.get(str);
            update.hot = alpha*update.cnt/countPeriod+(1-alpha)*update.hot;
            update.cnt = 0;
            nodes.put(str,update);
        }
    }
    private void hot_update_cnt(Entry cur)
    {
        period++;
        cur.cnt++;
        nodes.put(cur.key,cur);
        if(period >= countPeriod)
        {
            period =0;
            hot_update_grade();
        }
    }

    private void lru_update_grade();
    public TopKCache(int confSize, String strategy)
    {
        cachesize = confSize;
        currentSize = 0;
        nodes = new Hashtable<Object,Entry>(confSize);
        if(strategy.equals("lru"))
            lru =true,hot =false;
        else
            hot = true,lru =false;
    }
    public String get(String key)
    {
        Entry cur = nodes.get(key).value;
        if(nodes.contains(key))
        {
            if(hot)
            {
                hot_update_cnt(cur);
            }
            else
            {
                lru_update_grade();
            }
            return cur;
        }
        else
            return null;
    }
    public void set(String key,String value)
    {
        Entry entry;
        if(nodes.size()>= cachesize)
        {

            //remove()
        }
        else
        {
            Entry entry =new entry(key,value);
            nodes.put(key,entry);
        }
    }

    public void remove()
    {}

    public void clear()
    {}



}
