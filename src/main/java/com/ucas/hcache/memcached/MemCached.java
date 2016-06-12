package com.ucas.hcache.memcached;
/*
* @author: wjf
* @version: 2016骞�6鏈�10鏃� 涓婂�?11:01:14
*/

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.exception.MemcachedException;

public class MemCached {
	private static final String ip="192.168.26.xxx";
	private static final int port=12000;
	private static MemcachedClientBuilder mcb=null;
	private static MemcachedClient mc=null;
	static{
		try {
			mcb=new XMemcachedClientBuilder();
			mc=mcb.build();
			mc.addServer(ip, port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public boolean set(String key,String value,int exp) {
		boolean flag=false;
		try {
			flag=mc.add(key, exp, value);
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (MemcachedException e) {
			e.printStackTrace();
		}
		return flag;
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
		return value;
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
		return obj != null;
	}
//	public static void main(String[] args) throws TimeoutException,  MemcachedException, InterruptedException{
//		mc.add("name", 100, "wangjianfei");
//		System.out.println(mc.get("name"));
//	}
	

}
