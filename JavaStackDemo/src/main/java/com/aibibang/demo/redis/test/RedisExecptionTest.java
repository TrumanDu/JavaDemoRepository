package com.aibibang.demo.redis.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.exceptions.JedisConnectionException;

/** 
* @author: Truman.P.Du 
* @since: 2016年8月10日 上午9:03:18 
* @version: v1.0
* @description:测试redis连接池中连接数
*/
public class RedisExecptionTest {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(80);
		config.setMaxIdle(80);
		config.setMaxWaitMillis(1000);
		final JedisPool pool = new JedisPool(config, "192.168.0.101", 9000);
		
		ExecutorService executorService = Executors.newFixedThreadPool(10);
		for(int i =0 ;i<100000;i++){
			executorService.submit(new Runnable() {
				
				public void run() {
					Jedis jedis = null;
					try {
						jedis = pool.getResource();
						Pipeline p = jedis.pipelined();
						p.set("test1", "test1");
						p.set("test1", "test1");
						p.set("test1", "test1");
						p.set("test1", "test1");
						//System.out.println(jedis.hgetAll("reason:test_hb:138:38"));
					} catch (JedisConnectionException e) {
						e.printStackTrace();
					} finally {
						if (jedis != null)
							jedis.close();
					}	
					
				}
			});
		}
		Thread.sleep(1000*60);
	}

}
