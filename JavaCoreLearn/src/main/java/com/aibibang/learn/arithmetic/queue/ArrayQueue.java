package com.aibibang.learn.arithmetic.queue;

/**
 * @author：Truman.P.Du
 * @createDate: 2019年1月8日 下午8:36:06
 * @version:1.0
 * @description:数组队列
 */
public class ArrayQueue<T> {

	private Object[] array;

	private int head = 0;
	private int tail = 0;

	public ArrayQueue(int capacity) {
		array = new Object[capacity];
	}

	public boolean put(T obj) {
		if (tail == array.length) {
			if (head == 0) {
				return false;
			}

			// 需要移动元素
			for (int i = head; i < tail; i++) {
				array[i - head] = array[i];
			}
			tail = tail - head;
			head = 0;

		}

		array[tail] = obj;
		tail++;

		return true;
	}

	@SuppressWarnings("unchecked")
	public T take() {
		if (head == tail) {
			return null;
		}
		T rep = (T) array[head];
		head++;
		return rep;
	}

	public static void main(String[] args) {

	}

}
