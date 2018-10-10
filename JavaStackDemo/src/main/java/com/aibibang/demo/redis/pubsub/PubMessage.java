package com.aibibang.demo.redis.pubsub;

import com.aibibang.demo.util.RandomUtil;

import redis.clients.jedis.Jedis;

/** 
* @author: Truman.P.Du 
* @since: 2016年2月26日 下午2:33:16 
* @version: v1.0
* @description:
*/
public class PubMessage {

	public static void main(String[] args) {
		 jedis_pub_sub_listener pub_sub_listener = new jedis_pub_sub_listener();
		 Jedis jj = new Jedis("192.168.0.102",6379);
		 String[]mess = new String[10];
		 String message = RandomUtil.getRandomString(10*1024);
		 /*for(int i=10;i<101;i=i+10){
			 mess[i%10]= RandomUtil.getRandomString(i*1024);
		 }*/
		 long start = System.currentTimeMillis();
		 System.out.println(start);
		 for(int j=0;j<10000;j++){
		     jj.publish(PUBSUBTest.channel, message);
		     
		 }
		 long end = System.currentTimeMillis(); 
		 System.out.println("K耗时:"+(end-start));
		 
	}

}
