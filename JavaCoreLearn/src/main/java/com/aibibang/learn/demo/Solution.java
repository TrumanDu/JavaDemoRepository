package com.aibibang.learn.demo;

/**
 * @author：Truman.P.Du
 * @createDate: 2018年9月26日 下午3:49:03
 * @version:1.0
 * @description:
 */
public class Solution {
	public static int maxPoints(Point[] points) {
		if (points == null) {
			return 0;
		}
		if (points.length <= 2) {
			return points.length;
		}
		int max = 2;

		for (int i = 0; i < points.length; i++) {
			Point a = points[i];
			int temp = 1;
			int num = 0;
			for (int j = i + 1; j < points.length; j++) {
				Point b = points[j];
				if (b.x == a.x && b.y == a.y) {
					num++;
				} else {
					temp++;
					for (int k = j + 1; k < points.length; k++) {
						Point c = points[k];
						int t1 = (b.x - a.x) * (c.y - b.y);
						int t2 = (b.y - a.y) * (c.x - b.x);
						if (t1 == t2) {
							temp++;
						}
					}
				}

				if (max < (temp + num)) {
					max = temp + num;
				}
				temp = 1;
			}

		}

		return max;
	}

	public static void main(String[] args) {
		Point[] points = { new Point(0, 0), new Point(1, 1), new Point(1, -1) };
		System.out.println(maxPoints(points));
	}
}

// * Definition for a point.
class Point {
	int x;
	int y;

	Point() {
		x = 0;
		y = 0;
	}

	Point(int a, int b) {
		x = a;
		y = b;
	}
}
