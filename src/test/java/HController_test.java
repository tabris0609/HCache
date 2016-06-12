import com.ucas.hcache.HController.HController;

import java.io.IOException;

/**
 * Created by hou on 2016/6/11.
 */
public class HController_test {
    public static void main(String args[]) throws IOException {
        HController hController =new HController();
        hController.createTable("abc", new String[]{"cf"});
        hController.put("abc","row","cf","ck","value");
        String res = hController.get("abc","row","cf","ck");
        System.out.print(res);

    }
}
