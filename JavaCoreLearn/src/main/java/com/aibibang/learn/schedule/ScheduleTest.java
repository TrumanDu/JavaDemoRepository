package com.aibibang.learn.schedule;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 调度器使用demo
 *
 * @author Truman
 * @create 2017-07-05 16:54
 * @description :
 * scheduleAtFixedRate与scheduleWithFixedDelay区别是在于完成上一个任务后，是否延迟。
 * 从测试结果看scheduleWithFixedDelay会在上一次运行完成后，再延迟1s执行。
 * 两者都是在上一个任务完成后才会被调用，不会出现并发
 **/
public class ScheduleTest {

    public static void main(String[] args) {
        ScheduledThreadPoolExecutor schedule = new ScheduledThreadPoolExecutor(10);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        schedule.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("execute scheduleAtFixedRate.....:" + sdf.format(new Date()));
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, 0, 1 * 1000, TimeUnit.MILLISECONDS);

        schedule.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                System.out.println("execute scheduleWithFixedDelay.....:" + sdf.format(new Date()));
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, 0, 1 * 1000, TimeUnit.MILLISECONDS);
    }
}
