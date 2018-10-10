package com.aibibang.demo.log;

import org.apache.log4j.Logger;



/** 
 * @author: Truman.P.Du 
 * @since: 2016年1月22日 上午11:00:51 
 * @version: v1.0
 * @description:
 */
public class Log4jDemo {

	private static Logger log = Logger.getLogger(Log4jDemo.class);  
    public static void main(String[] args) {  
        log.trace("===Log4j===trace");  
        log.debug("===Log4j===debug");  
        log.info("===Log4j===info");  
        log.warn("===Log4j===warn");  
        log.error("===Log4j===error");  
    }  
}
