package com.aibibang.learn.design.patterns.factory;

/**
 * 工厂方法模式
 *
 * @author Truman
 * @create 2017-07-06 14:49
 * @description :
 * * 工厂模式在《Java与模式》中分为三类：
 * 1）简单工厂模式（Simple Factory）：不利于产生系列产品；
 * 2）工厂方法模式（Factory Method）：又称为多形性工厂；
 * 3）抽象工厂模式（Abstract Factory）：又称为工具箱，产生产品族，但不利于产生新的产品；
 * <p/>
 * 简单工厂并不简单，它是整个模式的核心，一旦他出了问题，整个模式都将受影响而不能工作，为了降低风险和为日后的维护、扩展
 * 做准备，我们需要对它进行重构，引入工厂方法。
 * <p/>
 * 工厂方法和简单工厂的主要区别是，简单工厂是把创建产品的职能都放在一个类里面，而工厂方法则把不同的产品放在实现了工厂接
 * 口的不同工厂类里面，这样就算其中一个工厂类出了问题，其他工厂类也能正常工作，互相不受影响，以后增加新产品，也只需要新
 * 增一个实现工厂接口工厂类，就能达到，不用修改已有的代码。但工厂方法也有他局限的地方，那就是当面对的产品有复杂的等级结
 * 构的时候，例如，工厂除了生产家电外产品，还生产手机产品，这样一来家电是手机就是两大产品家族了，这两大家族下面包含了数
 * 量众多的产品，每个产品又有多个型号，这样就形成了一个复杂的产品树了。如果用工厂方法来设计这个产品家族系统，就必须为每
 * 个型号的产品创建一个对应的工厂类，当有数百种甚至上千种产品的时候，也必须要有对应的上百成千个工厂类，这就出现了传说的
 * 类爆炸，对于以后的维护来说，简直就是一场灾难....
 **/

public interface FactoryMethod {
    public Product create();
}

class ComputerFactory implements FactoryMethod {
    public Product create() {
        return new Computer();
    }
}

class CarFactory implements FactoryMethod {

    public Product create() {
        return new Car();
    }
}

class PhoneFactory implements FactoryMethod {
    public Product create() {
        return new Phone();
    }
}

class App {
    public static void main(String[] args) {
        //FactoryMethod factory = new ComputerFactory();
        FactoryMethod factory = new CarFactory();
        //FactoryMethod factory = new PhoneFactory();
        Product product = factory.create();
        product.run();
    }
}
