package com.aibibang.demo.java.DynamicProxyDemo;
/** 
* @author: Truman.P.Du 
* @since: 2016年10月9日 上午10:32:02 
* @version: v1.0
* @description:
*/
public class HelloServiceImpl implements HelloService {

	@Override
	public void sayHello(String name) {
		// TODO Auto-generated method stub
		System.out.println("hello:"+name);
	}

}
