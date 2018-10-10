package com.aibibang.learn.base.copy;

/**
 * @author Truman
 * @create 2017-03-08 15:25
 * @description :浅拷贝
 * 浅拷贝是指拷贝对象时仅仅拷贝对象本身（包括对象中的基本变量），而不拷贝对象包含的引用指向的对象。
 * 深拷贝不仅拷贝对象本身，而且拷贝对象包含的引用指向的所有对象
 * 总结：
 * 以下例子可以看出
 * 1.不复写clone方法，默认是浅拷贝
 * 2.浅拷贝不会拷贝对象包含的引用指向的对象，及新对象对值得操作不影响之前拷贝对象的
 **/
public class SimpleClone implements Cloneable {
    private String familyName;

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    /**
     * 浅拷贝
     * 可以删除
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public static void main(String[] args) {
        SimpleClone s1 = new SimpleClone();
        s1.setFamilyName("aibibang");

        try {
            SimpleClone s2 = (SimpleClone) s1.clone();
            System.out.println("first s2 FamilyName:" + s2.getFamilyName());
            s2.setFamilyName("truman");
            System.out.println("second s2 FamilyName:" + s2.getFamilyName());
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        System.out.println("s1 FamilyName:" + s1.getFamilyName());

    }
}
