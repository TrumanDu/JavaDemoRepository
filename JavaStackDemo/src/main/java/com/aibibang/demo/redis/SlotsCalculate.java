package com.aibibang.demo.redis;

import java.util.ArrayList;
import java.util.List;

import redis.clients.util.JedisClusterCRC16;

/** 
* @author: Truman.P.Du 
* @since: 2016年7月30日 上午2:04:51 
* @version: v1.0
* @description:
*/
public class SlotsCalculate {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<String>keys = new ArrayList<String>(); 
		keys.add("pcrl_n_EMCFGFK48");
		//keys.add("detail:relation|clearanceItem|00E-001J-019E1");
		//keys.add("detail:relation|clearanceItem|9SIA0VE0ST9974");
		//keys.add("detail:relation|vendorPortalArticle|12331");
		
		for(String key :keys){
			System.out.println(JedisClusterCRC16.getSlot(key));
		}
	}

}
