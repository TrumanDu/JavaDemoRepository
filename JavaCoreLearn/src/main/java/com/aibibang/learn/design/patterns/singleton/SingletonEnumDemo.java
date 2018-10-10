package com.aibibang.learn.design.patterns.singleton;

/**
 * 利用枚举实现单例模式
 *
 * @author Truman
 * @create 2017-07-05 10:21
 * @description :
 **/
public enum SingletonEnumDemo {
    INSTANCE;

    private SingletonEnumDemo() {
        System.out.println("初始化instance");
    }

    private void invokeMethod() {
        System.out.println("调用invokeMethod");
    }

    public static void main(String[] args) {
        System.out.println("---------------测试效果-----------------");
        SingletonEnumDemo.INSTANCE.invokeMethod();
        System.out.println("instance = [" + SingletonEnumDemo.INSTANCE.hashCode() + "]");
        System.out.println("instance = [" + SingletonEnumDemo.INSTANCE.hashCode() + "]");
        System.out.println("instance = [" + SingletonEnumDemo.INSTANCE.hashCode() + "]");
    }

}
