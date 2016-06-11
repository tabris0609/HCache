package com.ucas.hcache;

import com.ucas.hcache.HController.HController;
import junit.framework.TestCase;

/**
 * Created by liujingkun on 16/6/10.
 */
public class clien_test extends TestCase{

    HController client = null;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        client = new HController();

    }

    public void test(){
        System.out.println("abc");
    }
}
