package com.aibibang.demo.redis.pubsub;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aibibang.demo.log.LogbackDemo;

import redis.clients.jedis.JedisPubSub;

/** 
* @author: Truman.P.Du 
* @since: 2016年2月25日 下午5:37:55 
* @version: v1.0
* @description:
*/
public class jedis_pub_sub_listener extends JedisPubSub {
	private static Logger log = LoggerFactory.getLogger(jedis_pub_sub_listener.class);
	int i=0;
	// 取得订阅的消息后的处理
	public void onMessage(String channel, String message) {
		long end = System.currentTimeMillis();
		i=i+1;
		System.out.println(i);
		//System.out.println(message);
		//System.out.println(channel + ":" + message);
	}

	// 初始化订阅时候的处理
	public void onSubscribe(String channel, int subscribedChannels) {
		System.out.println(channel + "=" + subscribedChannels);
	}

	// 取消订阅时候的处理
	public void onUnsubscribe(String channel, int subscribedChannels) {
		System.out.println(channel + "=" + subscribedChannels);
	}

	// 初始化按表达式的方式订阅时候的处理
	public void onPSubscribe(String pattern, int subscribedChannels) {
		System.out.println(pattern + "=" + subscribedChannels);
	}

	// 取消按表达式的方式订阅时候的处理
	public void onPUnsubscribe(String pattern, int subscribedChannels) {
		System.out.println(pattern + "=" + subscribedChannels);
	}

	// 取得按表达式的方式订阅的消息后的处理
	public void onPMessage(String pattern, String channel, String message) {
		System.out.println(pattern + "=" + channel + "=" + message);
	}
}
