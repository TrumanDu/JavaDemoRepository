package com.aibibang.demo.kafka;

import org.apache.kafka.common.utils.Utils;

/**
 * @author：Truman.P.Du
 * @createDate: 2018年3月24日 下午1:39:22
 * @version:1.0
 * @description:
 */
public class PartitionCalculate {

	public static void main(String[] args) {
		// System.out.println(partition("c001", 25));

//		int i = 0;
//		while (true) {
//			i++;
//			if (partition("c" + i, 25) == 1) {
//				System.out.println("c" + i);
//				break;
//			}
//		}
		
		System.out.println(partition(""+1,20));
		System.out.println(partition(""+2,20));
		System.out.println(partition(""+3,20));
		System.out.println(partition(""+4,20));
		System.out.println(partition(""+0,20));

	}

	public static int partition(String key, int numPartitions) {
		return Utils.toPositive(Utils.murmur2(key.getBytes())) % numPartitions;
	}

}
