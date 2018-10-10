package com.aibibang.demo.MBean;
/** 
 * @author: Truman.P.Du 
 * @since: 2016年1月9日 上午10:56:40 
 * @version: v1.0
 * @description:列出所有的MBean
 */
import java.lang.management.ManagementFactory;
import java.util.Iterator;
import java.util.Set;

import javax.management.MBeanServer;
import javax.management.ObjectInstance;
 
public class ListAllJVMMBeans {
     
    public static void main(String[] args) throws Exception {
         
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
         
        Set instances = server.queryMBeans(null, null);
         
        Iterator iterator = instances.iterator();
         
        while (iterator.hasNext()) {
            ObjectInstance instance = (ObjectInstance) iterator.next();
            System.out.println("MBean Found:");
            System.out.println("Class Name:\t" + instance.getClassName());
            System.out.println("Object Name:\t" + instance.getObjectName());
            System.out.println("****************************************");
        }
         
    }
 
}
