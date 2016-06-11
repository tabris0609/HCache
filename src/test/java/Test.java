import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by sonny on 2016/6/11.
 */

public class Test {
    public static void main(String args[])
    {
        final Hashtable<String, Integer> h = new Hashtable<String, Integer>();
        h.put("a", 3);
        h.put("b", 1);
        h.put("c", 2);
        for (String str : h.keySet())
        {
            System.out.println(str);
        }
        List<String> v = new ArrayList<String>(h.keySet());
        Collections.sort(v,new Comparator<Object>()
        {
            public int compare(Object arg0,Object arg1)
            {
                return h.get(arg1) - (h.get(arg0));
            }
        });
        for (String str : v)
        {
            System.out.println(str + " " + h.get(str));
        }
    }
}
