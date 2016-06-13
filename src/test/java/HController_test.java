import com.ucas.hcache.HController.HController;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by hou on 2016/6/11.
 */
public class HController_test {
    public static void main(String args[]) throws IOException {
        HController hController =new HController();
        HashSet<String> set = new HashSet<String>();
        set.add("");
        //hController.createTable("abc", new String[]{"cf"});
        //hController.put("abc","row","cf","ck","value");
        //String res = hController.get("abc","row","cf","ck");
        HashMap<String, String> map = hController.get("usertable","test","f1", set);

        //System.out.println(res);
        //System.out.println("cache: "+data);

    }
}
