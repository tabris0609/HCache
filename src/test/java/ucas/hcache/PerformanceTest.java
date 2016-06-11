package ucas.hcache;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/*
* @author: wjf
* @version: 2016年6月11日 下午2:04:09
*/

public class PerformanceTest {
	private List<Integer> data1=new ArrayList<Integer>();
	private List<Integer> data2=new ArrayList<Integer>();
	public PerformanceTest(){
		for(int i=0;i<20001;i++){
			data1.add((int)(Math.random()*20000));
		}
	}
	@Test
	public void prepare_data2(){
		for(int i=0;i<4;i++){
			for(int j=0;j<2;j++){
				data2.add(data1.get(j));
			}
		}
	}
	@Test
	public void getData(){
		for(int i=0;i<data2.size();i++){
			System.out.println(data2.get(i));
		}
	}
//	public void 
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PerformanceTest test=new PerformanceTest();
		test.prepare_data2();
		test.getData();	
	}

}
