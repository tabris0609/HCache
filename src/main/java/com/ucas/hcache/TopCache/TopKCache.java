package com.ucas.hcache.TopCache;
import java.util.Hashtable;
import java.util.Iterator;
import java.lang.Object;
import java.lang.String;
/**
 * Created by sonny on 2016/6/10.
 */
public class TopKCache {

    private int cachesize;
    private Hashtable<String,Entry> nodes;
    private int currentSize;
    private boolean lru ;
    public boolean hot ;
    private int countPeriod = 5;     //confirm by test
    private double alpha = 0.2;      //confirm by test
    private int period = 0;

    public TopKCache(int confSize, String strategy)
    {
        cachesize = confSize;
        currentSize = 0;
        nodes = new Hashtable<String,Entry>();
        if(strategy.equals("lru"))
        {
            lru =true;
            hot =false;
        }
        else
        {
            hot = true;
            lru =false;
        }

    }
    /**
     * hot=alpha*cnt/countPeriod+(1-alpha)*hot
     */
    private void hot_update_grade()
    {
        //Set<String> keys = nodes.KeySet();
        Iterator<String> iterator   = nodes.keySet().iterator();
        while(iterator.hasNext()){
            String str =iterator.next();
            Entry update = nodes.get(str);
            update.hot = alpha*update.cnt/countPeriod+(1-alpha)*update.hot;
            update.cnt = 0;
            nodes.put(str,update);
        }
    }

    /**
     *
     * @param cur
     */
    private void hot_update_cnt(Entry cur)
    {
        period++;
        cur.cnt++;
        nodes.put(cur.getKey(),cur);
        if(period >= countPeriod)
        {
            period =0;
            hot_update_grade();
        }
    }

    private void lru_update_grade()
    {

    }

    public String get(String key)
    {
        Entry cur = nodes.get(key);
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
            return cur.getValue();
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
            entry =new Entry(key,value);
            nodes.put(key,entry);
        }
    }

    public void remove()
    {}

    public void clear()
    {}



}
