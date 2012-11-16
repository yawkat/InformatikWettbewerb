package at.yawk.informatikwettbewerb.geldtransporter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Rechner {
	public static void main(String[] args) throws FileNotFoundException {
		final List<Integer> kofferWert;
		final List<Integer> kofferGewicht;
		{
			final Scanner s = new Scanner(new File("input.txt"));
			try {
				final int anzahl = s.nextInt();
				kofferWert = new ArrayList<Integer>(anzahl);
				kofferGewicht = new ArrayList<Integer>(anzahl);
				for(int i = 0; i < anzahl; i++) {
					kofferWert.add(s.nextInt());
					kofferGewicht.add(s.nextInt());
				}
			} finally {
				s.close();
			}
		}
		
		System.out.println("Überprüfe " + kofferWert.size() + " Koffer");
		System.out.println("Insgesamt " + longPow(2, kofferWert.size() - 1) + " Kombinationsmöglichkeiten");
		
		final Rechner r = new Rechner(kofferWert.size(), kofferWert, kofferGewicht, 10000);
		r.berechneUnterWert();
		System.out.println("Kombinationen unter (oder gleich) 10000 € Unterschied: " + r.moeglich.size());
		r.berechneBesteZusammensetzungUndGebeAus();
	}
	
	private static long longPow(final long basis, final long exponent) {
		if(exponent == 0) {
			if(basis == 0) {
				throw new NumberFormatException("Cannot calculate 0 ^ 0");
			} else {
				return 1;
			}
		} else if(exponent > 0) {
			if(basis == 0) {
				return 0;
			} else {
				return basis * longPow(basis, exponent - 1);
			}
		} else {
			throw new NumberFormatException("Cannot calculate negative exponent");
		}
	}
	
	private final static byte			bitmask		= (byte)0xff;
	
	private final int					kofferAnzahl;
	private final int					byteArraySize;
	private final List<Integer>			kofferWert;
	private final List<Integer>			kofferGewicht;
	
	private final int					maxWertUnterschied;
	
	private final Set<Zusammensetzung>	moeglich	= new HashSet<>();
	
	public Rechner(final int kofferAnzahl, final List<Integer> kofferWert, final List<Integer> kofferGewicht, final int maxWertUnterschied) {
		this.kofferAnzahl = kofferAnzahl;
		this.byteArraySize = this.kofferAnzahl / Byte.SIZE + 1;
		
		this.kofferWert = kofferWert;
		this.kofferGewicht = kofferGewicht;
		this.maxWertUnterschied = maxWertUnterschied;
	}
	
	public void berechneUnterWert() {
		final Zusammensetzung startZusammensetzung = new Zusammensetzung();
		final Executor e = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		final AtomicInteger runningThreads = new AtomicInteger();
		final int wertUnterschied = berechneWertUnterschied(startZusammensetzung);
		if(Math.abs(wertUnterschied) <= wertUnterschied) {
			moeglich.add(startZusammensetzung);
		}
		for(int i = 0; i < kofferAnzahl; i++) {
			final int finalIndex = i;
			e.execute(new Runnable() {
				@Override
				public void run() {
					runningThreads.incrementAndGet();
					try {
						final Zusammensetzung neueZusammensetzung = startZusammensetzung.clone();
						neueZusammensetzung.setBit(finalIndex, true);
						checkBits(neueZusammensetzung, finalIndex);
					} finally {
						runningThreads.decrementAndGet();
					}
				}
			});
		}
		while(runningThreads.get() > 0)
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch(InterruptedException e1) {
				e1.printStackTrace();
			}
	}
	
	public void berechneBesteZusammensetzungUndGebeAus() {
		final List<Zusammensetzung> sortingList = new ArrayList<>(moeglich);
		Collections.sort(sortingList, new Comparator<Zusammensetzung>() {
			@Override
			public int compare(Zusammensetzung o1, Zusammensetzung o2) {
				int gewichtUnterschied1 = 0, gewichtUnterschied2 = 0;
				for(int i = 0; i < kofferAnzahl; i++) {
					final int gewicht = kofferGewicht.get(i);
					if(o1.getBit(i)) {
						gewichtUnterschied1 += gewicht;
					} else {
						gewichtUnterschied1 -= gewicht;
					}
					if(o2.getBit(i)) {
						gewichtUnterschied2 += gewicht;
					} else {
						gewichtUnterschied2 -= gewicht;
					}
				}
				return Integer.compare(Math.abs(gewichtUnterschied1), Math.abs(gewichtUnterschied2));
			}
		});
		final Zusammensetzung beste = sortingList.get(0);
		int gewichtUnterschied = 0;
		int wertUnterschied = 0;
		for(int i = 0; i < kofferAnzahl; i++) {
			final int gewicht = kofferGewicht.get(i);
			final int wert = kofferWert.get(i);
			if(beste.getBit(i)) {
				gewichtUnterschied += gewicht;
				wertUnterschied += wert;
			} else {
				gewichtUnterschied -= gewicht;
				wertUnterschied -= wert;
			}
		}
		System.out.println(gewichtUnterschied + " " + wertUnterschied);
	}
	
	private void checkBits(final Zusammensetzung startZusammensetzung, final int maxBit) {
		final int wertUnterschied = berechneWertUnterschied(startZusammensetzung);
		if(wertUnterschied <= maxWertUnterschied) {
			if(Math.abs(wertUnterschied) <= wertUnterschied) {
				moeglich.add(startZusammensetzung);
			}
			for(int i = 0; i < maxBit; i++) {
				if(!startZusammensetzung.getBit(i)) {
					final Zusammensetzung neueZusammensetzung = startZusammensetzung.clone();
					neueZusammensetzung.setBit(i, true);
					checkBits(neueZusammensetzung, i);
				}
			}
		}
	}
	
	private int berechneWertUnterschied(final Zusammensetzung zusammensetzung) {
		int unterschied = 0;
		for(int i = 0; i < kofferAnzahl; i++) {
			if(zusammensetzung.getBit(i))
				unterschied += kofferWert.get(i);
			else
				unterschied -= kofferWert.get(i);
		}
		return unterschied;
	}
	
	private class Zusammensetzung implements Cloneable {
		private final byte[]	daten;
		
		public Zusammensetzung() {
			daten = new byte[byteArraySize];
		}
		
		@Override
		public Zusammensetzung clone() {
			final Zusammensetzung z = new Zusammensetzung();
			System.arraycopy(this.daten, 0, z.daten, 0, byteArraySize);
			return z;
		}
		
		public void setBit(final int index, final boolean value) {
			final int arrayIndex = index / Byte.SIZE;
			final int bitIndex = index % Byte.SIZE;
			if(value) {
				daten[arrayIndex] |= 1 << bitIndex;
			} else {
				daten[arrayIndex] &= bitmask ^ (1 << bitIndex);
			}
		}
		
		public boolean getBit(final int index) {
			return ((daten[index / Byte.SIZE] >> index % Byte.SIZE) & 1) == 1;
		}
		
		@Override
		public boolean equals(Object o) {
			return o instanceof Zusammensetzung && Arrays.equals(daten, ((Zusammensetzung)o).daten);
		}
		
		@Override
		public int hashCode() {
			return Arrays.hashCode(daten);
		}
	}
}
