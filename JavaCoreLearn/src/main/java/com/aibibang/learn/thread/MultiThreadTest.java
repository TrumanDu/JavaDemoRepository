package com.aibibang.learn.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author: Truman
 * @since: 2017年3月7日 下午3:18:18
 * @version: v1.0
 * @description:
 */
public class MultiThreadTest {
    private static Logger logger = LoggerFactory.getLogger(MultiThreadTest.class);
    public static ExecutorService pool;

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        pool = Executors.newFixedThreadPool(10);
        final Random r = new Random();
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            final int j = i;
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    int n2 = r.nextInt(10);
                    if (n2 != 0) {
                        try {
                            logger.info("exec ....." + j);
                            Thread.sleep(n2 * 1000);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
            });
        }

    }
}
