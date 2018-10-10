package com.aibibang.demo.java;
/** 
* @author: Truman.P.Du 
* @since: 2016年5月7日 下午2:18:39 
* @version: v1.0
* @description:java 实现计算机加减乘除
*/
public class JavaArithmetric {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
        int i=999999999;
        System.out.println(i>>4);
        System.out.println(i%16384);
        System.out.println(i&(16384-1));
        
        int j=2;
        System.out.println((j<<16)-1);
	}

}
