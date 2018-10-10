package com.aibibang.demo.java;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author：Truman.P.Du
 * @createDate: 2018年3月13日 下午2:05:49
 * @version:1.0
 * @description:
 */
public class ListSortDemo {

	public static void main(String[] args) {
		List<Student> stus = new ArrayList<Student>();
		stus.add(new ListSortDemo().new Student("ca", 3));
		stus.add(new ListSortDemo().new Student("cb", 3));
		stus.add(new ListSortDemo().new Student("as", 1));
		stus.add(new ListSortDemo().new Student("ds", 4));
		stus.add(new ListSortDemo().new Student("be", 2));

		Collections.sort(null);
		for (Student student : stus) {
			System.out.println(student.toString());
		}
	}

	class Student implements Comparable<Student> {
		private String name;
		private int age;

		private Student(String name, int age) {
			this.name = name;
			this.age = age;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}

		@Override
		public int compareTo(Student o) {
			return o.getName().compareTo(getName());
		}

		@Override
		public String toString() {
			return "Student [name=" + name + ", age=" + age + "]";
		}
		
		
	}
}
