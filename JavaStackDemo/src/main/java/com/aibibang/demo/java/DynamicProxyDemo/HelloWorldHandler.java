package com.aibibang.demo.java.DynamicProxyDemo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/** 
* @author: Truman.P.Du 
* @since: 2016年10月9日 上午10:34:26 
* @version: v1.0
* @description:
*/
public class HelloWorldHandler implements InvocationHandler {
    private Object obj;
    public HelloWorldHandler(Object obj) {
		super();
		this.obj=obj;
	}
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		// TODO Auto-generated method stub
		doBefore();
		Object result = method.invoke(obj, args);
		doAfter();
		return result;
	}
    
	private void doBefore(){
		System.out.println("before method invoke");
	}
    private void doAfter(){
    	System.out.println("after method invoke");
	}
}
