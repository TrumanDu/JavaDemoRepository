package com.aibibang.learn.design.patterns.factory;

/**
 * 抽象工厂模式
 *
 * @author Truman
 * @create 2017-07-06 15:13
 * @description :
 **/
interface Engine {
}

interface Tyre {
}

class EngineA implements Engine {
    public EngineA() {
        System.out.println("制造-----:EngineA");
    }
}

class EngineB implements Engine {
    public EngineB() {
        System.out.println("制造-----:EngineB");
    }
}

class TyreA implements Tyre {
    public TyreA() {
        System.out.println("制造-----:TyreA");
    }
}

class TyreB implements Tyre {
    public TyreB() {
        System.out.println("制造-----:TyreB");
    }
}

public interface AbstractFactory {
    public Engine createEngine();

    public Tyre createTyre();
}

class CarAFactory implements AbstractFactory {

    @Override
    public Engine createEngine() {
        return new EngineA();
    }

    @Override
    public Tyre createTyre() {
        return new TyreA();
    }
}

class CarBFactory implements AbstractFactory {

    @Override
    public Engine createEngine() {
        return new EngineB();
    }

    @Override
    public Tyre createTyre() {
        return new TyreB();
    }
}

class RunDemo {
    public static void main(String[] args) {
        //生产A车型
        AbstractFactory aFactory = new CarAFactory();
        aFactory.createEngine();
        aFactory.createTyre();
        //生产B车型
        AbstractFactory bFactory = new CarBFactory();
        bFactory.createEngine();
        bFactory.createTyre();
    }
}
