package com.aibibang.demo.redis.pubsub;

import java.util.HashSet;
import java.util.Set;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

/** 
* @author: Truman.P.Du 
* @since: 2016年2月25日 下午2:33:11 
* @version: v1.0
* @description:redis cluster pub/sub测试
*/
public class PUBSUBTest {
    static String channel = "ec2_channel";
	public static void main(String[] args) {
		// TODO Auto-generated method stub
     Set<HostAndPort>jedisClusterNode = new HashSet<HostAndPort>();
     jedisClusterNode.add(new HostAndPort("192.168.0.102",6379));
     //jedisClusterNode.add(new HostAndPort("192.168.0.103",6379));
     
     JedisCluster jc = new JedisCluster(jedisClusterNode);
     jedis_pub_sub_listener pub_sub_listener = new jedis_pub_sub_listener();
     jc.subscribe(pub_sub_listener,channel);
	}

}
