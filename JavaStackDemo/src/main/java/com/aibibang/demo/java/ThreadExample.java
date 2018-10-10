package com.aibibang.demo.java;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import com.aibibang.demo.util.RandomUtil;

/** 
* @author: Truman.P.Du 
* @since: 2016年4月20日 下午5:05:38 
* @version: v1.0
* @description:线程学习demo
*/
public class ThreadExample {
	
	

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		/*ExecutorService s = Executors.newFixedThreadPool(3);
		Future<Map<String, Object>> result = s.submit(new MyCallable());
*/
		/*try {
			ThreadExample.test1();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		ThreadExample.test3();

	}

	public static void test1() {
		ExecutorService executorService = Executors.newFixedThreadPool(10);
		List<Future<String>> results = new ArrayList<Future<String>>();
		for (int i = 0; i < 50; i++) {
			Future<String> future = executorService.submit(new Callable<String>() {

				public String call() throws Exception {
					int sleep = RandomUtil.getRandomInt(500, 5000);
					System.out.println("Sleep time:" + sleep);

					try {
						Thread.sleep(sleep);
					} catch (Exception e) {
					}

					return RandomUtil.getRandomSex();

				}
			});

			results.add(future);
		}

		int threadCount = ((ThreadPoolExecutor) executorService).getActiveCount();
		System.out.println(threadCount);
		for (Future f : results) {
			try {
				System.out.println(f.get().toString());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		int threadCount2 = ((ThreadPoolExecutor) executorService).getActiveCount();
		System.out.println(threadCount2);
	}

	/**
	 * 有问题的写法
	 * 会执行两次
	 * @throws Exception 
	 */
	public  void test2() throws Exception {
		ExecutorService pool = Executors.newFixedThreadPool(10);
		List<Callable<String>> batchGets = new ArrayList<Callable<String>>();
		
		for (int i = 0; i < 50; i++) {
			batchGets.add(new Callable<String>() {
               
				public String call() throws Exception {
					int sleep = RandomUtil.getRandomInt(500, 5000);
					System.out.println("Sleep time:" + sleep);
					try {
						Thread.sleep(sleep);
					} catch (Exception e) {
					}
					return RandomUtil.getRandomSex();
				}

			});
		}
		//等待所有task执行结束后执行主线程
		pool.invokeAll(batchGets);

		for (int i = 0; i < batchGets.size(); i++) {
			System.out.println(batchGets.get(i).call());
		}
	}
	
	/**
	 * 第三种写法
	 * @throws Exception 
	 */
	public static void test3() throws Exception {
		ExecutorService pool = Executors.newFixedThreadPool(10);
		List<Callable<String>> batchGets = new ArrayList<Callable<String>>();
		for (int i = 0; i < 50; i++) {
			batchGets.add(new Callable<String>() {
				public String call() throws Exception {
					int sleep = RandomUtil.getRandomInt(500, 5000);
					System.out.println("Sleep time:" + sleep);
					try {
						Thread.sleep(sleep);
					} catch (Exception e) {
					}
					return RandomUtil.getRandomSex();
				}

			});
		}
		//等待所有task执行结束后执行主线程
		List<Future<String>> result=pool.invokeAll(batchGets);
        int threadCount = ((ThreadPoolExecutor)pool).getActiveCount();
        System.out.println("当前活跃线程数为:"+ threadCount);
		for (int i = 0; i < result.size(); i++) {
			System.out.println(result.get(i).get());
		}
	}
	
	
	

}

class MyCallable implements Callable<Map<String, Object>> {
	String name;

	public MyCallable() {
		System.out.println("0000");
	}

	public Map<String, Object> call() throws Exception {
		// TODO Auto-generated method stub
		System.out.println("11111111111");
		System.out.println(name);
		return null;
	}

}
