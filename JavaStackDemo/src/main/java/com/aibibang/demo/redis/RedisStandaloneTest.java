package com.aibibang.demo.redis;

import redis.clients.jedis.Jedis;

/**
 * @author：Truman.P.Du
 * @createDate: 2019年3月8日 下午3:28:17
 * @version:1.0
 * @description:
 */
public class RedisStandaloneTest {

	public static void main(String[] args) {
		Jedis jedis = new Jedis("192.168.0.101", 9100);
		jedis.auth("bigdata");
		// 新增100000 string
		for (int i = 0; i < 100000; i++) {
			jedis.set("ec:dev:" + i,
					"AnnotationConfigServletWebServerApplicationContext: Closing org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext@53a60f67: startup date [Fri Mar 08 15:30:04 CST 2019]; parent: org.springframework.context.annotation.AnnotationConfigApplicationContext@4b49d497");
		}
		// 新增10000 hash
		for (int i = 0; i < 10000; i++) {
			jedis.hset("ec:hash:" + i, "key" + i,
					"AnnotationConfigServletWebServerApplicationContext: Closing org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext@53a60f67: startup date [Fri Mar 08 15:30:04 CST 2019]; parent: org.springframework.context.annotation.AnnotationConfigApplicationContext@4b49d497");
		}

		jedis.bgsave();

		jedis.close();

	}

}
