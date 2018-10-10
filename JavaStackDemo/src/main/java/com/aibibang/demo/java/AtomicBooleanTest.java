package com.aibibang.demo.java;

import java.util.concurrent.atomic.AtomicBoolean;

import com.aibibang.demo.util.RandomUtil;

/** 
* @author: Truman.P.Du 
* @since: 2016年6月3日 上午10:22:21 
* @version: v1.0
* @description:AtomicBoolean测试demo
*/
public class AtomicBooleanTest {
    private boolean closed = false;
    private AtomicBoolean aClosed = new AtomicBoolean(false);
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		Thread []threads = new Thread[10];  
		final AtomicBooleanTest t =new AtomicBooleanTest();
     for(int i=0;i<10;i++){
    	 final int num = i; 
    	 threads[i] = new Thread(){
    		 public void run() {
    			while(!t.aClosed.compareAndSet(false, true)){
    				//System.out.println("我是线程：" + num +",我在阻塞中。。。。。。");
    			}
                /*while(t.closed){
                	 System.out.println("我是线程：" + num );
    			 }
    			 t.closed=true; */
    			 try {
					Thread.sleep(RandomUtil.getRandomInt(500, 5000));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    			 //t.closed=false; 
    			 t.aClosed.set(false);
    			 System.out.println("我是线程：" + num + "，我完成任务！");   
    		 };
    	 } ;
    	 threads[i].start();
     }
     
     for(Thread tt:threads){
    	 tt.join();
     }
     //System.out.println(t.closed);
     System.out.println(t.aClosed.get());
     
	}

}
