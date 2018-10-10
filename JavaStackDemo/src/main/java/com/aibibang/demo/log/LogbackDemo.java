package com.aibibang.demo.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

/** 
 * @author Truman.P.Du 
 * @since 2015年12月24日09:02:03
 * @version v1.0
 * 主要是配置文件及jar包导入的正确与否
 */
public class LogbackDemo {
	private static Logger log = LoggerFactory.getLogger(LogbackDemo.class);
	static Marker marker = MarkerFactory.getMarker("DEV");

	public static void main(String[] args) {
		log.trace("======trace");
		log.debug("======debug");
		log.info("======info");
		log.warn("======warn");
		log.error("======error");
		log.error("======error2");
		log.error("======error3");
		/*log.debug(marker,"hello1");
		log.info(marker,"hello2");
		log.error(marker,"hello3");*/
	}
}