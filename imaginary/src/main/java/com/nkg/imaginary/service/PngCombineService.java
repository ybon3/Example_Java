package com.nkg.imaginary.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.nkg.imaginary.common.util.MathUtil;
import com.nkg.imaginary.pojo.PngComposition;
import com.nkg.imaginary.pojo.PngSet;

@Service
public class PngCombineService extends BaseService {

	public List<PngComposition> combine(PngSet pngSet, int limit) {

		long maxCombineAmount = pngSet.main.size() * pngSet.bg.size() * MathUtil.pow2By(pngSet.accessories.size());
		if (maxCombineAmount < limit) {
			throw new IllegalArgumentException("Max combine amount less than limit. " + maxCombineAmount);
		}

		//建立各組合表
		byte[] mainElmt = buildElement(pngSet.main.size());
		byte[] bgndElmt = buildElement(pngSet.bg.size());
		byte[] acsrElmt = buildElement(pngSet.accessories.size());

		//取得 main + bg 的各種組合
		List<byte[]> baseCombination = pairedCombination(mainElmt, bgndElmt);

		//取得 accessories 所有組合
		List<byte[]> allCombination = findAllCombination(acsrElmt); //TODO 改為boot-up 預載

		//紀錄被挑選的隨機組合
		List<PngComposition> result = new ArrayList<>();

		//隨機組合
		Random ran = new Random(System.currentTimeMillis());
		for (int i = 0; i < limit; i++) {

			byte[] background = baseCombination.get(ran.nextInt(baseCombination.size()));

			byte[] accessories = allCombination.remove(ran.nextInt(allCombination.size()));

			//將組合映對到檔案
			File main =  pngSet.main.get(background[0]);
			File bgnd =  pngSet.bg.get(background[1]);

			File[] multiLayer = new File[accessories.length];
			for (int j = 0; j < accessories.length; j++) {
				multiLayer[j] = pngSet.accessories.get(accessories[j]);
			}

			result.add(new PngComposition(main, bgnd, multiLayer));
		}

		return result;
	}

	/**
	 * 建立循序數集合，不重複
	 */
	public static byte[] buildElement(int amount) {
		byte[] elements = new byte[amount];

		for (byte i = 0; i < amount; i++) {
			elements[i] = i;
		}

		return elements;
	}

	/**
	 * 取得指定集合各取一個值的所有組合
	 */
	public static List<byte[]> pairedCombination(byte[] set1, byte[] set2) {
		List<byte[]> res = new ArrayList<>();

		for (byte i = 0; i < set1.length; i++) {
			for (byte j = 0; j < set2.length; j++) {
				res.add(new byte[]{set1[i], set2[j]});
			}
		}

		return res;
	}

	public static List<byte[]> findAllCombination(byte[] set) {
		List<byte[]> res = new ArrayList<>();

		//利用二進位的依序走訪來求集合的所有子集
		int n = set.length;
		long count = MathUtil.pow2By(n); //迴圈2^n次

		for (int i = 1; i < count; i++) { //略過空集合
			String setStr = Integer.toBinaryString(i); //將int值轉換成二進位制值的字串
			int unChoose = n - setStr.length();
			int len = 0;
			byte[] comb = new byte[n];
			for (int j = 0; j < setStr.length(); j++){
				if(setStr.charAt(j) == '1') {
					comb[len++] = set[unChoose+j];
				}
			}

			//cut array
			res.add(Arrays.copyOfRange(comb, 0, len));
		}

		return res;
	}
}
