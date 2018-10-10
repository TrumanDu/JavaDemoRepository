package com.aibibang.demo.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/** 
* @author: Truman.P.Du 
* @since: 2016年3月2日 下午3:32:37 
* @version: v1.0
* @description:
*/
public class TrumanUtil {
	
	public static void PrintlnClassField(Object o) {
		Class c = o.getClass();
		Method[] methods = c.getMethods();
        for(Method m:methods){
        	if(m.getName().length()>3&&m.getName().contains("get")&&!m.getName().contains("getClass")&&!m.getName().contains("Schema")){
        		String name = m.getName();
        		try {
					System.out.println(name.substring(3, name.length())+":------------"+m.invoke(o));
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        	
        }
	}

	public static void main(String[] args) {
		
		//TrumanUtil.PrintlnClassField(md);
	}
}
