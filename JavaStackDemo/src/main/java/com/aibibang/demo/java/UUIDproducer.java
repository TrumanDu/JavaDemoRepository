package com.aibibang.demo.java;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/** 
 * @author: Truman.P.Du 
 * @since: 2016年1月22日 下午3:55:06 
 * @version: v1.0
 * @description:
 */
public class UUIDproducer {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	long time = System.currentTimeMillis();
	int sum = 0;
	Map map = new HashMap<String, String>();
	while(System.currentTimeMillis()-time<1000){
		sum=sum+1;
		UUID uuid = UUID.randomUUID();
		map.put(uuid, "");
	 }
	System.out.println(map.size());
	System.out.println(sum);
	}

}
