package com.aibibang.demo.redis;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/** 
* @author: Truman.P.Du 
* @since: 2016年5月13日 上午9:41:35 
* @version: v1.0
* @description:多线程测试standalone模式
*/
public class MulThread {
	private static Jedis jedis = null;	//jedis池  
    private static JedisPool pool;  
	static {
		
		JedisPoolConfig config = new JedisPoolConfig();  
		config.setMaxIdle(1024);      
		config.setMaxWaitMillis(10000L);
		config.setMinIdle(10);
		pool = new JedisPool(config, "192.168.0.101",6379);
	}
	/**
	 * set 方法
	 * @param key
	 * @param value
	 */
	public static void setString(String key,String value){
		Jedis jedis = pool.getResource();
		jedis.set(key, value);
		pool.returnResource(jedis);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int threadSize = 10;//控制线程数量
		ExecutorService executor = Executors.newFixedThreadPool(threadSize);
		int index = 50;//控制数据量大小50代表50*1000=50000条数据
		long start = System.currentTimeMillis();
		for (int i = 0; i < 1000 * index; i++) {
			Runnable runnable = new Runnable() {
				public void run() {
					MulThread.setString("hello", "hello redis");
				}

			};
			executor.execute(runnable);
		}
		while (((ThreadPoolExecutor) executor).getActiveCount() > 0) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		long end = System.currentTimeMillis();
		System.out.println(end - start);
	}

}
