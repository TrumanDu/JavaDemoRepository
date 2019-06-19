package com.aibibang.learn.thread;

/**
 * @author Truman.P.Du
 * @date Jun 12, 2019 3:34:43 PM
 * @version 1.0
 */
public class TestThread {

	public static void main(String[] args) {
//		Thread thread = new Thread(new Runnable() {
//			@Override
//			public void run() {
//				while (true) {
//					try {
//						Thread.sleep(1000);
//						System.out.println(Thread.currentThread().getName()+":正在工作！！");
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//				}
//
//			}
//		});
//		
//		thread.setName("线程1");
//		thread.start();
		
		
		Thread daemonThread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(1000);
						System.out.println(Thread.currentThread().getName()+":正在工作！！");
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

			}
		});
		
		daemonThread.setName("线程2");
		daemonThread.setDaemon(true);
		daemonThread.start();
		
		
		try {
			Thread.sleep(10000);
			System.out.println(Thread.currentThread().getName()+":停止工作！！");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		
	}
	
	
	
}
