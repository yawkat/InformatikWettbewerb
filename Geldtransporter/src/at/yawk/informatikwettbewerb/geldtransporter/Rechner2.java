package at.yawk.informatikwettbewerb.geldtransporter;

import java.util.Arrays;
import java.util.BitSet;
import java.util.Comparator;

public class Rechner2 {
	private final static int[][]	koffer	= { { 42000, 103 }, { 31000, 100 }, { 20000, 95 }, { 20000, 119 }, { 20000, 148 }, { 20000, 165 }, { 180000, 721 }, { 9000, 89 }, { 9000, 89 }, { 18000, 156 }, { 18000, 181 }, { 28000, 93 }, { 28000, 239 }, { 17000, 173 }, { 121000, 87 }, { 14000, 113 }, { 579000, 1816 }, { 13000, 107 }, { 13000, 128 }, { 12000, 102 }, { 12000, 102 }, { 12000, 115 }, { 12000, 118 }, { 12000, 124 }, { 33000, 244 }, { 65000, 394 }, { 22000, 100 }, { 11000, 92 }, { 11000, 103 }, { 11000, 126 } };
	
	public static void main(String[] args) {
		Arrays.sort(koffer, new Comparator<int[]>() {
			@Override
			public int compare(int[] o1, int[] o2) {
				return Integer.compare(o1[0], o2[0]);
			}
		});
		System.out.println(Arrays.deepToString(koffer));
		System.out.println("Calculating...");
		final BitSet av = tryLessDifference(new BitSet(koffer.length));
		System.out.println("Wertunterschied: " + getUnterschied(av, 0));
		final BitSet best = tryOptimize(av);
		System.out.println(best);
		System.out.println("Wertunterschied: " + getUnterschied(best, 0));
		System.out.println("Gewichtsunterschied: " + getUnterschied(best, 1));
	}
	
	private static int getUnterschied(BitSet bits, int array) {
		int unterschied = 0;
		for(int i = 0; i < koffer.length; i++) {
			if(bits.get(i))
				unterschied += koffer[i][array];
			else
				unterschied -= koffer[i][array];
		}
		return Math.abs(unterschied);
	}
	
	private static BitSet tryLessDifference(final BitSet bits) {
		BitSet bestSet = bits;
		int minimumWertUnterschied = getUnterschied(bits, 0);
		for(int i = 0; i < koffer.length; i++) {
			if(!bits.get(i)) {
				final BitSet newBits = (BitSet)bits.clone();
				newBits.set(i);
				final int newWertUnterschied = getUnterschied(newBits, 0);
				if(newWertUnterschied < minimumWertUnterschied) {
					bestSet = newBits;
					minimumWertUnterschied = newWertUnterschied;
				}
			}
		}
		if(bestSet == bits)
			return bits;
		else
			return tryLessDifference(bestSet);
	}
	
	private static BitSet tryOptimize(final BitSet bits) {
		BitSet bestSet = bits;
		int minimumGewichtUnterschied = getUnterschied(bits, 1);
		for(int i = 0; i < koffer.length; i++) {
			if(!bits.get(i)) {
				final BitSet newBits = (BitSet)bits.clone();
				newBits.set(i);
				final int newWertUnterschied = getUnterschied(newBits, 0);
				if(newWertUnterschied <= 10000) {
					final int newGewichtUnterschied = getUnterschied(newBits, 1);
					if(newGewichtUnterschied < minimumGewichtUnterschied) {
						bestSet = newBits;
						minimumGewichtUnterschied = newGewichtUnterschied;
					}
				}
			}
		}
		if(bestSet == bits)
			return bits;
		else
			return tryOptimize(bestSet);
	}
}
