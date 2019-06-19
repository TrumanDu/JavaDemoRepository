package com.aibibang.learn.arithmetic.sort;

/**
 * @author：Truman.P.Du
 * @createDate: 2019年1月10日 下午6:22:07
 * @version:1.0
 * @description: 插入排序
 */
public class SortByInset {

	public static void main(String[] args) {
		// 1000以内随机数
		int random = (int) (Math.random() * 1000);
		int[] randomArray = new int[random];
		for (int i = 0; i < random; i++) {
			randomArray[i] = (int) (Math.random() * 1000);
		}

		SortByInset sort = new SortByInset();

		randomArray = sort.sortByInsertASC(randomArray);
		for (int i = 0; i < random; i++) {
			System.out.println(randomArray[i]);
		}
	}

	/**
	 * 递增排序
	 * 
	 * @param array
	 * @return
	 */
	public int[] sortByInsertASC(int[] array) {
		if (array.length <= 1) {
			return array;
		}

		// TODO
		for (int i = 1; i < array.length; i++) {
			int value = array[i];
			for (int j = i - 1; j >= 0; j--) {
				if (array[j] > value) {
					array[j + 1] = array[j];// 移动数据
				} else {
					// 找到插入问题，j为待插入位置
					array[j] = value;
					break;
				}
			}
		}
		return array;
	}

}
