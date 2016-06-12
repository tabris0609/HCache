package com.ucas.hcache.TopCache;
import java.util.*;
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
    private int period = 0;

    private int countPeriod = 50;     //confirm by test
    private double alpha = 0.2;      //confirm by test
    private double Top_K_threshold;  //confirm by test

/***************** LRU replacement algorithm ********************/
    private LinkedList<Entry> LRUList = new LinkedList<Entry>();
/************************** The end *****************************/

    /**
     *configure cache size
     * Replacement scope of cache
     * @param confSize
     * @param threshold
     * @param strategy
     */
    public TopKCache(int confSize,double threshold,String strategy)
    {
        this.cachesize = confSize;
        this.currentSize = 0;
        this.Top_K_threshold = threshold;
        nodes = new Hashtable<String,Entry>(confSize);
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
/***************** LRU replacement algorithm ********************/
    private void lru_get(Entry cur)
    {

    }
/************************** The end *****************************/

    /**
     * hot=alpha*cnt/countPeriod+(1-alpha)*hot
     *
     */
    private void hot_update_grade()
    {
        //Set<String> keys = nodes.KeySet();
        Iterator<String> iterator   = nodes.keySet().iterator();
        while(iterator.hasNext()){
            String str =iterator.next();
            Entry update = nodes.get(str);
            update.hot = alpha*update.cnt/countPeriod+(1-alpha)*(update.hot);
            update.cnt = 0;
            nodes.put(str,update);
        }
        TopK_Replace_Cache();
    }

    /**
     * According Top_K_threshold update cache
     */
    private  void TopK_Replace_Cache()
    {
        /*for (String str : nodes.keySet())
        {
            System.out.println(str);
        }*/
        List<String> v = new ArrayList<String>(nodes.keySet());
        /**
         * From big to small sort
         */
        Collections.sort(v,new Comparator<String>()
        {
            public int compare(String arg0,String arg1)
            {
                Entry update0 = nodes.get(arg0);
                Entry update1 = nodes.get(arg1);
                double v1 = update1.hot - update0.hot;
                int ret =(int)v1;
                return ret;
            }
        });
        int Top_K = v.size();
        Top_K = (int)(Top_K*Top_K_threshold);
        Hashtable<String,Entry> replace = new Hashtable<String,Entry>();
        List<String> subList = v.subList(0,Top_K);
        Iterator it = subList.iterator();
        while(it.hasNext())
        {
            String str = (String) it.next();
            replace.put(str,nodes.get(str));
        }
        nodes.clear();
        nodes.putAll(replace);

        //System.out.println(str + " " + h.get(str));
    }

    /**
     * by get()
     * Compute global period compare to countPeriod
     * if period > countPeriod
     * calling Heat value replacement algorithm
     * @param cur
     */
    private void hot_update_cnt(Entry cur)
    {
        period++;
        cur.cnt++;
        nodes.put(cur.getKey(),cur);
        if(period >= countPeriod)
        {
            /**  Heat value replacement algorithm  **/
            period =0;
            hot_update_grade();
        }
    }
    /**
     *
     * @param key
     * @return
     */
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
                lru_get(cur);
            }
            return cur.getValue();
        }
        else
            return null;
    }

    /**
     *
     * @param key
     * @param value
     */
    public void set(String key,String value)
    {

        if(nodes.size()>= cachesize)
        {
            Entry entry = new Entry();
            TopK_Replace_Cache();
            entry.setKey(key);
            entry.setValue(value);
            nodes.put(key,entry);
        }
        else
        {
            Entry entry = new Entry();
            entry.setKey(key);
            entry.setValue(value);
            nodes.put(key,entry);
        }
    }

    /**
     *
     * @param key
     */
    public void remove(String key)
    {
        boolean contains = nodes.containsKey(key);
        if(contains)
        {
            nodes.remove(key);
        }
        else
        {
//            System.out.print("This KV isn't exist!");
        }
    }

    /**
     * clear cache
     */
    public void clear()
    {
        nodes.clear();
    }

}
