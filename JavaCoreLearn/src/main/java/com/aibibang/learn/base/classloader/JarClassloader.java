package com.aibibang.learn.base.classloader;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

/**
 * 自定义jar类加载器
 *
 * @author Truman
 * @create 2017-06-27 17:40
 * @description :
 * 一、java提供了三种ClassLoader对Class进行加载：
 * 1.BootStrap ClassLoader：称为启动类加载器，是Java类加载层次中最顶层的类加载器，负责加载JDK中的核心类库，如：rt.jar、resources.jar、charsets.jar等，可通过如下程序获得该类加载器从哪些地方加载了相关的jar或class文件：
 * /Java/jdk1.6.0_22/jre/lib/resources.jar
 * /Java/jdk1.6.0_22/jre/lib/rt.jar
 * /Java/jdk1.6.0_22/jre/lib/sunrsasign.jar
 * /Java/jdk1.6.0_22/jre/lib/jsse.jar
 * /Java/jdk1.6.0_22/jre/lib/jce.jar
 * /Java/jdk1.6.0_22/jre/lib/charsets.jar
 * /Java/jdk1.6.0_22/jre/classes/
 * 2.Extension ClassLoader：称为扩展类加载器，负责加载Java的扩展类库，默认加载JAVA_HOME/jre/lib/ext/目下的所有jar。
 * 3.App ClassLoader：称为系统类加载器，负责加载应用程序classpath目录下的所有jar和class文件。
 * 二、ClassLoader的加载原理
 * 1、原理介绍
 * <p/>
 * ClassLoader使用的是双亲委托模型来搜索类的，每个ClassLoader实例都有一个父类加载器的引用（不是继承的关系，是一个包含的关系），
 * 虚拟机内置的类加载器（Bootstrap ClassLoader）本身没有父类加载器，但可以用作其它ClassLoader实例的的父类加载器。
 * 当一个ClassLoader实例需要加载某个类时，它会试图亲自搜索某个类之前，先把这个任务委托给它的父类加载器，这个过程是由上至下依次检查的，
 * 首先由最顶层的类加载器Bootstrap ClassLoader试图加载，如果没加载到，则把任务转交给Extension ClassLoader试图加载，如果也没加载到，
 * 则转交给App ClassLoader 进行加载，如果它也没有加载得到的话，则返回给委托的发起者，由它到指定的文件系统或网络等URL中加载该类。
 * 如果它们都没有加载到这个类时，则抛出ClassNotFoundException异常。否则将这个找到的类生成一个类的定义，并将它加载到内存当中，
 * 最后返回这个类在内存中的Class实例对象。
 * 2、为什么要使用双亲委托这种模型呢？
 * <p/>
 * 因为这样可以避免重复加载，当父亲已经加载了该类的时候，就没有必要子ClassLoader再加载一次。
 * 考虑到安全因素，我们试想一下，如果不使用这种委托模式，那我们就可以随时使用自定义的String来动态替代java核心api中定义的类型，
 * 这样会存在非常大的安全隐患，而双亲委托的方式，就可以避免这种情况，因为String已经在启动时就被引导类加载器（Bootstrcp ClassLoader）
 * 加载，所以用户自定义的ClassLoader永远也无法加载一个自己写的String，除非你改变JDK中ClassLoader搜索类的默认算法。
 * 3、 但是JVM在搜索类的时候，又是如何判定两个class是相同的呢？
 * <p/>
 * JVM在判定两个class是否相同时，不仅要判断两个类名是否相同，而且要判断是否由同一个类加载器实例加载的。
 * 只有两者同时满足的情况下，JVM才认为这两个class是相同的。就算两个class是同一份class字节码，如果被两个不同的ClassLoader实例所加载，
 * JVM也会认为它们是两个不同class。
 **/
public class JarClassloader extends URLClassLoader {
    private static Map<String, JarClassloader> classloaderMap = new HashMap<String, JarClassloader>();

    public JarClassloader() {
        super(new URL[]{}, JarClassloader.class.getClassLoader());
    }

    public static Class<?> loadJar(URL url, String name) throws MalformedURLException, ClassNotFoundException {
        JarClassloader jarloader = new JarClassloader();
        jarloader.addURL(url);
        classloaderMap.put(name, jarloader);
        Class<?> cl = jarloader.loadClass(name);
        return cl;
    }

    public static void unLoadJar(String name) {
        if (classloaderMap.containsKey(name)) {
            JarClassloader jarloader = classloaderMap.get(name);
            classloaderMap.remove(name);
            try {
                jarloader.close();
            } catch (IOException e) {
            }
            System.gc();
        }
    }

    public static void main(String[] args) throws MalformedURLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        String path = System.getProperty("user.dir") + "/jarResource/JavaCoreLearn-1-SNAPSHOT.jar";
        String path2 = System.getProperty("user.dir") + "/jarResource/JavaCoreLearn-2-SNAPSHOT.jar";

        String name = "com.aibibang.learn.base.classloader.IServiceImpl";
        Class<?> cl = JarClassloader.loadJar(new File(path).toURI().toURL(), name);
        IService app = (IService) cl.newInstance();
        app.show();
        System.out.println("IService  classloader = [" + IService.class.getClassLoader() + "]");
        System.out.println("c1  classloader = [" + app.getClass().getClassLoader() + "]");
        System.out.println("c1 parent classloader = [" + app.getClass().getClassLoader().getParent() + "]");
        JarClassloader.unLoadJar(name);

        System.out.println("---------------------------");
        Class<?> c2 = JarClassloader.loadJar(new File(path2).toURI().toURL(), name);
        IService app2 = (IService) c2.newInstance();
        app2.show();
        System.out.println("c2  classloader = [" + app2.getClass().getClassLoader() + "]");
        System.out.println("c2 parent classloader = [" + app2.getClass().getClassLoader().getParent() + "]");
        JarClassloader.unLoadJar(name);

        System.out.println("-----------加载classpath 下class----------------");
        String name2 = "com.aibibang.learn.base.classloader.ShowAllClassLoader";
        Class<?> c3 = JarClassloader.loadJar(new File(path2).toURI().toURL(), name2);
        Object app3 = c3.newInstance();
        System.out.println("c3  classloader = [" + app3.getClass().getClassLoader() + "]");

        System.out.println("----------加载classpath 下jar中class-----------------");
        String name4 = "ch.qos.logback.core.encoder.ByteArrayUtil";
        Class<?> c4 = JarClassloader.loadJar(new File(path2).toURI().toURL(), name4);
        Object app4 = c4.newInstance();
        System.out.println("c4  classloader = [" + app4.getClass().getClassLoader() + "]");

    }
}
