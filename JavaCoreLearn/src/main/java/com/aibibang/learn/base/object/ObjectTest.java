package com.aibibang.learn.base.object;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Truman
 * @create 2017-03-23 14:25
 * @description :
 **/
public class ObjectTest {

    public static void main(String[] args) {
        String str = "string";
        Object os = str;
        Map<String, String> map = new HashMap<String, String>() {/**
			 * 
			 */
			private static final long serialVersionUID = -6437555142384718862L;

		{
            put("1", "1");
            put("2", "2");

        }};

        Object om = map;
        if (os instanceof String) {
            System.out.println("os = [String]");
        }

        if (om instanceof Map) {
            System.out.println("os = [Map]");
        }

    }
}
