package com.aibibang.learn.base.copy;

/**
 * @author Truman
 * @create 2017-03-08 15:15
 * @description : 深拷贝
 * 浅拷贝是指拷贝对象时仅仅拷贝对象本身（包括对象中的基本变量），而不拷贝对象包含的引用指向的对象（直接复制引用地址）。
 * 深拷贝不仅拷贝对象本身，而且拷贝对象包含的引用指向的所有对象（拷贝对象重新开辟新的存储空间）
 * 深拷贝则是对浅拷贝的递归
 * 深浅拷贝的本质区别在于浅拷贝只是复制引用地址，深拷贝重新开辟新的存储空间。
 **/
public class DeepClone implements Cloneable {
    private String name;
    private SimpleClone family;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SimpleClone getFamily() {
        return family;
    }

    public void setFamily(SimpleClone family) {
        this.family = family;
    }

    /**
     * 深拷贝
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        DeepClone o = null;
        try {
            o = (DeepClone) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        //此处注释掉即为浅拷贝
        //加上为深拷贝
        o.family = (SimpleClone) family.clone();
        return o;
    }

    public static void main(String[] args) {
        DeepClone d1 = new DeepClone();
        d1.setName("name1");
        SimpleClone s1 = new SimpleClone();
        s1.setFamilyName("aibibang");
        d1.setFamily(s1);

        try {
            DeepClone d2 = (DeepClone) d1.clone();
            System.out.println("first d2 FamilyName:" + d2.getFamily().getFamilyName());
            System.out.println("first d2 name:" + d2.getName());
            d2.getFamily().setFamilyName("baidu");

        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        System.out.println("d1 FamilyName:" + d1.getFamily().getFamilyName());
    }
}
