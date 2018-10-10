package com.aibibang.learn.base.classpath;

/**
 * @author Truman
 * @create 2017-03-08 17:29
 * @description :
 * java 获取classpath 地址方法
 **/
public class ClasspathTest {
    public static void main(String[] args) {
        System.out.println(System.getProperty("java.class.path"));
        //System.out.println(System.getProperty("path.separator"));
        /*
        以下两种都是获取编译根目录下的路径
        以下两种方法可以获取jar外面的资源
        */
        System.out.println(ClasspathTest.class.getResource("/").getPath());
        System.out.println(ClasspathTest.class.getClassLoader().getResource("").getPath());
        //获取当前类的编译目录
        System.out.println(ClasspathTest.class.getResource("").getPath());
        //以下方法可以获取jar内部的资源
        System.out.println(ClasspathTest.class.getClassLoader().getResourceAsStream(""));
    }
}
