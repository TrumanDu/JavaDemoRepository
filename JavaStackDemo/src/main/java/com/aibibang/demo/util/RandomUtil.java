package com.aibibang.demo.util;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

import org.apache.kafka.common.utils.Utils;


public class RandomUtil {
	
	private static Random rdm = new Random();

	/**
	 * 获取一个模拟人名（3到6位字母），首字母大写
	 * @return
	 */
	public static String getName(){
		
		int n = 3+rdm.nextInt(4);
		
		return upFirstChar(getRandomString(n));
	}
	
	/**
	 * 获取一个随机字符串，字符串长度为N
	 * @param n
	 * @return
	 */
	public static String getRandomString(int n){
		char[] s = new char[n];
		for(int i=0; i<n; i++){
			s[i] = ((char)(97+rdm.nextInt(26)));
		}
		return new String(s);
	}
	
	/**
	 * 将字符串首字母大写
	 * @param str
	 * @return
	 */
	public static String upFirstChar(String str){
		
		if(str == null || str.length()<1){
			return str;
		}
		
		char[] val = str.toCharArray();
		if(val[0]>96 && val[0]<123)
			val[0] = (char)(val[0]-32);
		return new String(val);
	}
	
	/**
	 * 获取一个随机的年龄值
	 * @return
	 */
	public static int getAge(){
		return getRandomInt(100);
	}
	
	/**
	 * 获取一个随机整型数
	 * @param n
	 * @return
	 */
	public static int getRandomInt(int n){
		return rdm.nextInt(n);
	}

	public static String getPhoneNum() {
		String phone = "15";
		for (int i = 0; i < 9; i++) {
			phone += getRandomInt(10);
		}
		
		return phone;
	}

	/**
	 * 获取从m-n的随机整数
	 * @param m
	 * @param n
	 * @return
	 */
	public static int getRandomInt(int m, int n) {
		return rdm.nextInt(n-m)+m;
	}
	
	/**
	 * 获取从m-n的随机整数
	 * @param m
	 * @param n
	 * @return
	 */
	public static long getRandomLong(long m, long n) {
		long r=0;
		while(r<m || r>n){
			r = rdm.nextLong();
		}
		return r;
	}
	
	/**
	 * 获取距今n年内的随机日期
	 * @param n
	 * @return
	 */
	public static Date getRandomDate(long n){
		long date = new Date().getTime();
		date = date - (long)(n*365*24*60*60*1000*rdm.nextDouble());
		return new Date(date);
	}
	
	/**
	 * 获取一个UUID
	 * @return
	 */
	public static String getUUID(){
		return UUID.randomUUID().toString();
	}
	
	/**
	 * 获取随机性别
	 * @return
	 */
	public static String getRandomSex() {
		return rdm.nextInt(10)>4?"M":"W";
	}
	
	
	/**
	 * 随机返回一个参数
	 * @return
	 */
	public static Object getRandomParam(Object... args) {
		return args[rdm.nextInt(args.length)];
	}
	

	public static String getRandomWords(int n) {
		StringBuffer sb = new StringBuffer();
		n = rdm.nextInt(n+1);
		while(n>0){
			n--;
			sb.append(getRandomString(rdm.nextInt(3)+3)).append(" ");
		}
		if(sb.length()>1)
			sb.deleteCharAt(sb.length()-1);
		return sb.toString();
	}
	
	public static void main(String[] args) {
		/*for (int i = 0; i < 10; i++) {
			System.out.println(getRandomWords(10));
		}*/
		String ss = "rpsjr";
		System.out.println(Utils.abs(Utils.murmur2(ss.getBytes())) %  5);
	}
	
	
}
