package com.aibibang.demo.redis.test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import redis.clients.jedis.Jedis;

/**
 * @author Truman.P.Du
 * @since 2016年02月05日09:02:03
 * @version v1.0
 */
public class TwemproxyRedisTest {

	private static Jedis jedis = null;

	@Before
	public void init() {
		jedis = new Jedis("192.168.0.101", 22125);
	}

	@Test
	public void testString() {
		String key = "hello";
		jedis.set(key, "hello redis");
		Assert.assertEquals("hello redis",jedis.get(key));
		Assert.assertEquals(true,jedis.exists(key));
		jedis.del(key);
		Assert.assertEquals(false,jedis.exists(key));
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
		Assert.assertEquals("1", jedis.hlen("user").toString());
		Assert.assertEquals(true, jedis.exists("user"));
		System.out.println(jedis.hmget("user", "age")); // null
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
		
		Assert.assertEquals(false,jedis.sismember("user", "who"));
		
		System.out.println(jedis.srandmember("user"));
		
		Assert.assertEquals("4",jedis.scard("user").toString());
	}
    /**
     * 测试排序
     * @throws InterruptedException
     */
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

	@After
	public void destory() {
		jedis.close();
	}
}
