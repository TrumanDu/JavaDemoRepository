package com.aibibang.learn.design.patterns.factory;

/**
 * 简单工厂模式
 *
 * @author Truman
 * @create 2017-07-06 14:24
 * @description :
 * 工厂模式在《Java与模式》中分为三类：
 * 1）简单工厂模式（Simple Factory）：不利于产生系列产品；
 * 2）工厂方法模式（Factory Method）：又称为多形性工厂；
 * 3）抽象工厂模式（Abstract Factory）：又称为工具箱，产生产品族，但不利于产生新的产品；
 * <p/>
 * 简单工厂模式又称静态工厂方法模式。重命名上就可以看出这个模式一定很简单。它存在的目的很简单：定义一个用于创建对象的接口。
 * 在简单工厂模式中,一个工厂类处于对产品类实例化调用的中心位置上,它决定那一个产品类应当被实例化, 如同一个交通警察站在来往
 * 的车辆流中,决定放行那一个方向的车辆向那一个方向流动一样。
 * 先来看看它的组成：
 * 1) 工厂类角色：这是本模式的核心，含有一定的商业逻辑和判断逻辑。在java中它往往由一个具体类实现。
 * 2) 抽象产品角色：它一般是具体产品继承的父类或者实现的接口。在java中由接口或者抽象类来实现。
 * 3) 具体产品角色：工厂类所创建的对象就是此角色的实例。在java中由一个具体类实现。
 * <p/>
 * 缺点：
 * 如果需要新添加条件，那么只能加 if-else 或者 switch 加 case。这就是硬编码，也违反了开闭原则。
 * 条件过多，那么就不利于维护。所以使用在条件不多的情况下。
 **/

interface Product {
    void run();
}

class Computer implements Product {
    @Override
    public void run() {
        System.out.println("computer running...");
    }
}

class Car implements Product {
    @Override
    public void run() {
        System.out.println("car running...");
    }
}

class Phone implements Product {
    @Override
    public void run() {
        System.out.println("phone running...");
    }
}

public class SimpleFactory {
    public static Product factory(String productName) {
        switch (productName) {
            case "Computer":
                return new Computer();
            case "Car":
                return new Car();
            case "Phone":
                return new Phone();
            default:
                return null;
        }

    }


    public static void main(String[] args) {
        Product p = SimpleFactory.factory("Computer");
        p.run();
        Product c = SimpleFactory.factory("Car");
        c.run();
        Product phone = SimpleFactory.factory("Phone");
        phone.run();
    }
}
