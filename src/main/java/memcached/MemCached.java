package memcached;
/*
* @author: wjf
* @version: 2016年6月10日 上午11:01:14
*/

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.exception.MemcachedException;

public class MemCached {
	private static MemcachedClientBuilder mcb=null;
	private static MemcachedClient mc=null;
	static{
		try {
			mcb=new XMemcachedClientBuilder();
			mc=mcb.build();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void set(String key,String value,int exp) throws TimeoutException, InterruptedException, MemcachedException{
		
		mc.add(key, exp, value);
	}
	public String get(String key){
		String value=null;
		try {
			value= mc.get(key);
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MemcachedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public  boolean delete(String key){
		boolean flag=false;
		Object obj=null;
		try {
			obj=mc.get(key);
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MemcachedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(obj==null)return false;
		else return true;
	}
	

}
