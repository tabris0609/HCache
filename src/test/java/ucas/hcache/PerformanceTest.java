package ucas.hcache;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.ucas.hcache.HController.HController;

/*
* @author: wjf
* @version: 2016年6月11日 下午2:04:09
*/

public class PerformanceTest {
	private List<Integer> data1=new ArrayList<Integer>();
	private List<Integer> data2=new ArrayList<Integer>();
	public PerformanceTest(){
		
	}

	@Test
	public List<Integer> prepare_data2(){
		for(int i=0;i<10000;i+=3){
			data2.add(data1.get(i));
			data2.add(data1.get(i));
			data2.add(data1.get(i));
		}
		return data2;
	}
	@Test
	public List<Integer> prepare_data1(){
		for(int i=0;i<10000;i++){
			data1.add((int)(Math.random()*10000));
		}
		return data1;
	}
	public void testLocalCache(List<Integer> data) throws IOException{
		HController hController =new HController();
        hController.createTable("abc", new String[]{"cf"});
        for(int i=0;i<data.size();i++){
        	hController.put("abc", "row", "cf",String.valueOf(data.get(i)), String.valueOf(data.get(i)));
        }
        long start=System.currentTimeMillis();
//        System.out.println("with memcached and local");
        for(int i=0;i<data.size();i++){
        	hController.get("abc", "row", "cf", String.valueOf(data.get(i)));
        }
        long end=System.currentTimeMillis();
        System.out.println(end-start+"ms");
	}

//	public void 
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		PerformanceTest test=new PerformanceTest();
		List<Integer> list;
	    list=test.prepare_data1();	
	    test.testLocalCache(list);
		
	    
//	    list=test.prepare_data2();
//	    test.testLocalCache(list);
		
		
	}

}
