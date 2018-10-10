package com.aibibang.demo.redis.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.aibibang.demo.redis.batch.RedisUtil;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

/**
 * @author Truman.P.Du
 * @since 2015年12月24日09:02:03
 * @version v1.0
 */
public class RedisTest {
	//private static final Logger logger = Logger.getLogger(RedisUtil.class);
	public static JedisCluster jedis = null;
	private static ResourceBundle bundle = ResourceBundle.getBundle("config");
    
	@Before
	public void init() {
			Set<HostAndPort> jedisClusterNode = new HashSet<HostAndPort>();
			HostAndPort hostAndPort = null;
			
			String[] connectArray = {"192.168.0.101:9000","192.168.0.102:9001"};
			for (String connect : connectArray) {
				String host = connect.split(":")[0].trim();
				String port = connect.split(":")[1].trim();
				hostAndPort = new HostAndPort(host, Integer.parseInt(port));
				jedisClusterNode.add(hostAndPort);
			}
			jedis = new JedisCluster(jedisClusterNode);
			
	}

	@Test
	public void testString() {
		String key = "hello";
		jedis.set(key, "hello redis");
		System.out.println(jedis.get(key));
		System.out.println(jedis.exists(key));
		System.out.println(jedis.del(key));
		System.out.println(jedis.exists(key));
		

	}

	@Test
	public void testMap() {

		jedis.del("user");
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", "truman");
		map.put("age", "25");
		jedis.hmset("user", map);
		List<String> rsmap = jedis.hmget("user", "name", "age");
		System.out.println(rsmap);
		jedis.hdel("user", "age");
		System.out.println(jedis.hmget("user", "age")); // null
		System.out.println(jedis.hlen("user")); //1
		System.out.println(jedis.exists("user"));// true
		System.out.println(jedis.hkeys("user"));// key
		System.out.println(jedis.hvals("user"));// value

		Iterator<String> iter = jedis.hkeys("user").iterator();
		while (iter.hasNext()) {
			String key = iter.next();
			System.out.println(key + ":" + jedis.hmget("user", key));
		}
	}

	/**
	 * jedis List
	 */
	@Test
	public void testList() {
		jedis.del("java framework");
		System.out.println(jedis.lrange("java framework", 0, -1));

		jedis.lpush("java framework", "spring");
		jedis.lpush("java framework", "struts");
		jedis.lpush("java framework", "hibernate");

		System.out.println(jedis.lrange("java framework", 0, -1));

		jedis.del("java framework");
		jedis.rpush("java framework", "spring");
		jedis.rpush("java framework", "struts");
		jedis.rpush("java framework", "hibernate");
		System.out.println(jedis.lrange("java framework", 0, -1));
	}

	/**
	 * jedis Set
	 */
	@Test
	public void testSet() {

		jedis.del("user");

		jedis.sadd("user", "liuling");
		jedis.sadd("user", "xinxin");
		jedis.sadd("user", "ling");
		jedis.sadd("user", "zhangxinxin");
		jedis.sadd("user", "who");

		jedis.srem("user", "who");
		System.out.println(jedis.smembers("user"));
		System.out.println(jedis.sismember("user", "who"));
															
		System.out.println(jedis.srandmember("user"));
		System.out.println(jedis.scard("user"));
	}

	@Test
	public void test() throws InterruptedException {

		jedis.del("a");
		jedis.lpush("a", "1");
		jedis.lpush("a", "6");
		jedis.lpush("a", "3");
		jedis.lpush("a", "9");
		System.out.println(jedis.lrange("a", 0, -1));// [9, 3, 6, 1]
		System.out.println(jedis.sort("a")); // [1, 3, 6, 9] //
		System.out.println(jedis.lrange("a", 0, -1));
	}
	@Test
	public void testSlot() {

		System.out.println(RedisUtil.getSlotByKey("detailaaaa:relationaaaa|hhhhhhh|9SIV00C45P2203"));
	}

/*	@Test
	public void testClusterString() {
		String key = "testCluster";
		RedisUtil redisUtil = new RedisUtil();
		redisUtil.set(key, "hello redis world", 10);
		System.out.println(redisUtil.get(key, 10));
		redisUtil.closeCluster();
	}*/

	@After
	public void destory() {
		try {
			jedis.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
