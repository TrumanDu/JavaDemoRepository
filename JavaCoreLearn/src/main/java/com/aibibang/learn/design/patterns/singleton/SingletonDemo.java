package com.aibibang.learn.design.patterns.singleton;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 常见单例demo
 *
 * @author Truman
 * @create 2017-07-05 9:52
 * @description :
 * 这个就是所谓的饿汉模式，在类加载后，即会占用资源，但线程安全
 **/
public class SingletonDemo {
    private static final SingletonDemo singletonDemo = new SingletonDemo();

    private SingletonDemo() {
        System.out.println("初始化instance");
    }

    public static SingletonDemo getInstance() {
        return singletonDemo;
    }

    public static void main(String[] args) {
        System.out.println("---------------测试效果-----------------");
        System.out.println("instance = [" + SingletonDemo.getInstance() + "]");
        System.out.println("instance = [" + SingletonDemo.getInstance() + "]");
        System.out.println("instance = [" + SingletonDemo.getInstance() + "]");
        System.out.println("---------------多线程测试效果-----------------");
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println("instance = [" + SingletonDemo.getInstance() + "]");
                }
            });
        }
    }
}
