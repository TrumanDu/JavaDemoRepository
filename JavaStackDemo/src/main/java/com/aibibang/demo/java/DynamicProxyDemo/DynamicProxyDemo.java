package com.aibibang.demo.java.DynamicProxyDemo;

import java.lang.reflect.Proxy;

/** 
* @author: Truman.P.Du 
* @since: 2016年10月9日 上午10:27:13 
* @version: v1.0
* @description:动态代理demo
*/
public class DynamicProxyDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
		HelloWorldHandler handle = new HelloWorldHandler(new HelloServiceImpl());
		HelloService proxy = (HelloService) Proxy.newProxyInstance(DynamicProxyDemo.class.getClassLoader(),
				new Class[] { HelloService.class }, handle);
		proxy.sayHello("truman");
	}
}
