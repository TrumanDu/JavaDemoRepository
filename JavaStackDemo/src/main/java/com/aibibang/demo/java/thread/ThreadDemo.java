package com.aibibang.demo.java.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/** 
* @author: Truman.P.Du 
* @since: 2016年8月4日 下午6:45:21 
* @version: v1.0
* @description:
*/
public class ThreadDemo {

	public static void main(String[] args) throws Exception, ExecutionException {
		// TODO Auto-generated method stub
		/*new Thread() {
			public void run() {
             System.out.println("I`am a thread!");               
			};
		}.start();*/
		
		/*new Thread(new Runnable() {
			public void run() {
				// TODO Auto-generated method stub
				System.out.println("I`am a second thread!");     
			}
		}).start();*/
		
		/*ExecutorService executorService = Executors.newSingleThreadExecutor();
		executorService.submit(new Runnable() {
			public void run() {
				System.out.println("I`am a thrid thread!");
			}
		});*/
		
		/*ExecutorService executorService = Executors.newFixedThreadPool(10);
		List<Future<String>> results = new ArrayList<Future<String>>();
		for( int i=0;i<10000;i++){
			final int j = i;
			Future<String> result = executorService.submit(new Callable<String>() {
				public String call() throws Exception {
					// TODO Auto-generated method stub
					return "value"+j;
				}
			});
			results.add(result);
		}
		for(Future<String>result :results){
			System.out.println(result.get());//当线程没有执行完毕会阻塞
		}
		executorService.shutdown();*/
		
		/*ExecutorService executorService = Executors.newFixedThreadPool(10);
		List<Callable<String>> tasks = new ArrayList<Callable<String>>();
		for (int i = 0; i < 10; i++) {
			final int j = i;
			tasks.add(new Callable<String>() {
				public String call() throws Exception {
					// TODO Auto-generated method stub
					return "value" + j;
				}
			});
		}
		//此时还未执行
		List<Future<String>> results = executorService.invokeAll(tasks);//未执行完成阻塞
		for (Future<String> result : results) {
			System.out.println(result.get());//当线程没有执行完毕会阻塞
		}
		executorService.shutdown();*/
		
		Thread t =new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Thread.sleep(30000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		t.setDaemon(true);
		System.out.println(t.isDaemon());
		t.start();
		System.out.println("end.....");
		
	}

}
