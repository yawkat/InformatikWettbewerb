package at.yawk.informatikwettbewerb.skyline;

import java.awt.Point;
import java.util.Scanner;

public class Rechner {
	/**
	 * 
	 * @param grundriss Alle Koordinaten des Grundrisses. Muss nicht zwingend ein Rechteck sein.
	 * @param entfernungRaster Entfernung, nach der ein Geb&auml;ude um <code>rasterErhoehung</code> h&ouml;her gebaut werden darf
	 * @param rasterErhoehung Zahl der Meter, um die ein Geb&auml;udepro <code>entfernungRaster</code> erh&ouml;t werden darf
	 * @param ausgangsHoehe H&ouml;he der "Needle"
	 * @return Maximalh&ouml;he
	 */
	public static int berechneHoeheFürGebaeude(final Point[] grundriss, final int entfernungRaster, final int rasterErhoehung, final int ausgangsHoehe) {
		// Koordinatenursprung
		final Point mitte = new Point();
		
		// kleinste Entfernung der Eckpunkte zu Koordinatenursprung
		double minimumEntfernung = Double.MAX_VALUE;
		for(final Point koordinate : grundriss)
			minimumEntfernung = Math.min(minimumEntfernung, mitte.distance(koordinate));
		
		// Entfernung in Rasterschritten
		final int minimumEntfernungRaster = (int)minimumEntfernung / entfernungRaster;
		
		// Gesamthöhe
		return minimumEntfernungRaster * rasterErhoehung + ausgangsHoehe;
	}
	
	public static void main(String[] args) {
		final Scanner s = new Scanner(System.in);
		try {
			System.out.println("Alle Koordinaten in der Form x,y.");
			final Point[] koordinaten = new Point[4];
			for(int i = 0; i < koordinaten.length; i++) {
				System.out.println("Koordinate " + (char)('A' + i) + ":");
				final String[] as = s.next().split(",");
				if(as.length == 2) {
					try {
						final int x = Integer.parseInt(as[0]);
						final int y = Integer.parseInt(as[1]);
						koordinaten[i] = new Point(x, y);
						continue;
					} catch(NumberFormatException e) {
						
					}
				}
				System.out.println("Eingabefehler.");
				i--;
			}
			System.out.println("Maximalhöhe: " + berechneHoeheFürGebaeude(koordinaten, 100, 1, 100));
		} finally {
			s.close();
		}
	}
}
