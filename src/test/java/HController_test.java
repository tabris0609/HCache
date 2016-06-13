import com.ucas.hcache.HController.HController;

import java.io.IOException;
import java.util.*;

/**
 * Created by hou on 2016/6/11.
 */
public class HController_test {
    public static void main(String args[]) throws IOException {
        HController hController =new HController();
        HashSet<String> set = null;
        hController.createTable("abc", new String[]{"cf"});
        hController.put("abc","row","cf","ck","value");
        HashMap<String, String> res = hController.get("abc","row","cf",set);
        //HashMap<String, String> map = hController.get("usertable","test","f1", set);

        Iterator iter = res.entrySet().iterator();
        while (iter.hasNext()){
            Map.Entry entry = (Map.Entry) iter.next();
            System.out.println(entry.getValue().toString());
        }
        //System.out.println(res);
        //System.out.println("cache: "+data);

    }
}
