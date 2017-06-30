package com.uib.common.utils;

import java.util.Random;


public class RandomUtil {
	public static String getRandom(int n){
		String randNum = "";
		Random random = new Random();
		for (int i=0; i<n; i++){   
			randNum += String.valueOf(random.nextInt(10));   
		}
		return randNum;
	}
	
	
	public static void main(String[] args) {
		System.out.println(RandomUtil.getRandom(6));
	}
}
