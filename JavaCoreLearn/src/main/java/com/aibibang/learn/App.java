package com.aibibang.learn;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hello world!
 */
public class App {
    private static Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        /*logger.debug("debug");
    	logger.error("error");
    	logger.info("info");
        System.out.println( "Hello World!" );*/
        /*System.out.println("dae4dbb77268855c9a42a558d17e2dbd466c584ecb3fe92eb55c967601378a68|USA".hashCode());

        System.out.println("dae4dbb77268855c9a42a558d17e2dbd466c584ecb3fe92eb55c967601378a68|USA".getBytes().hashCode());*/
        //System.out.println(Math.abs("dae4dbb77268855c9a42a558d17e2dbd466c584ecb3fe92eb55c967601378a68|USA".hashCode()) % 100+1);
    	
    	Long l1 = 127l;
    	Long l2 = 127l;
    	System.out.println(l1==l2);
    }

    public void show() {
        System.out.println("class 2");
    }
}
