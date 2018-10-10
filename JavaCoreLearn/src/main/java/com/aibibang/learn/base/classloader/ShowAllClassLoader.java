package com.aibibang.learn.base.classloader;

/**
 * @author Truman
 * @create 2017-06-27 17:28
 * @description :
 * 显示所有的类加载器
 **/
public class ShowAllClassLoader {

    public static void main(String[] args) {
        ClassLoader loader = ShowAllClassLoader.class.getClassLoader();
        while (loader != null) {
            System.out.println(loader.toString());
            loader = loader.getParent();
        }
    }
}
