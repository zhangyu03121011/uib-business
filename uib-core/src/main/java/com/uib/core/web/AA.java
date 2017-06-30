package com.uib.core.web;

public class AA {
	public static void main(String[] args) {
		int i, j, k, total;
		System.out.println("1 cent" + "  2 cents" + "  5 cents");
		for (i = 0; i <= 10; i++)
			for (j = 0; j <= 5; j++)
				for (k = 0; k <= 2; k++) {
					total = i * 1 + j * 2 + k * 5;
					if (total > 10)
						break;
					if (10 == total)
						System.out.println("    " + i + ",    " + j + ",    " + k);
				}

	}

}
