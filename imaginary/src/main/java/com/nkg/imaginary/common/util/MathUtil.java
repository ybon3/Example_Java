package com.nkg.imaginary.common.util;

public class MathUtil {

	//Testing
	public static void main(String[] args) {
		System.out.println(multiplySum(1,6));
		System.out.println(pow2By(30));
	}

	/**
	 * 計算從 s 到 e 的連乘積
	 */
	public static long multiplySum(int s, int e) {

		if (s > 0 && e >= s) {
			long sum = 1L;
			while (s <= e) {
				sum *= s;
				s++;
			}

			return sum;
		}
		else {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * 求 2^n
	 */
	public static long pow2By(int n) {
		return (1L << n);
	}
}
