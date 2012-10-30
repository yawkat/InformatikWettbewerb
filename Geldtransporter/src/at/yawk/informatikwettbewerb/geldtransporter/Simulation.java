package at.yawk.informatikwettbewerb.geldtransporter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Simulation implements Runnable {
	public static void main(String[] arguments) {
		new Simulation().run();
	}
	
	@Override
	public void run() {
		List<Geldkoffer> alleKoffer = null;
		{
			final File inputKofferFile = new File("input.txt");
			try {
				final Scanner scanner = new Scanner(inputKofferFile);
				try {
					alleKoffer = new ArrayList<Geldkoffer>(scanner.nextInt());
					while(scanner.hasNext()) {
						scanner.nextLine();
						final int nextWert = scanner.nextInt();
						final int nextGewicht = scanner.nextInt();
						alleKoffer.add(new Geldkoffer(nextGewicht, nextWert));
					}
				} finally {
					scanner.close();
				}
			} catch(FileNotFoundException e) {
				System.err.println("Eingabedatei für Geldkoffer (" + inputKofferFile.getAbsolutePath() + ") nicht gefunden, bricht ab!");
				return;
			}
		}
		
		Collections.sort(alleKoffer, new Comparator<Geldkoffer>() {
			@Override
			public int compare(Geldkoffer o1, Geldkoffer o2) {
				return Integer.compare(o1.getGewicht(), o2.getGewicht());
			}
		});
		
		final Kofferraum kofferraum1 = new Kofferraum();
		final Kofferraum kofferraum2 = new Kofferraum();
		
		for(int i = 0; i < alleKoffer.size(); i++) {
			((i & 1) == 0 ? kofferraum1 : kofferraum2).addKoffer(alleKoffer.get(i));
		}
		
		while(true) {
			boolean hatGeaendert = false;
			for(final Geldkoffer koffer : alleKoffer) {
				final Kofferraum quellKofferraum = kofferraum1.getEnthalteneKoffer().contains(koffer) ? kofferraum1 : kofferraum2;
				final Kofferraum zielKofferraum = quellKofferraum == kofferraum1 ? kofferraum2 : kofferraum1;
				final int wertAbstand = quellKofferraum.getGesamtWert() - zielKofferraum.getGesamtWert();
				final int gewichtAbstand = quellKofferraum.getGesamtGewicht() - zielKofferraum.getGesamtGewicht();
				
				if(gewichtAbstand > 0 || wertAbstand > 10_000) {
					quellKofferraum.entferneKoffer(koffer);
					zielKofferraum.addKoffer(koffer);
					
					if(wertAbstand > quellKofferraum.getGesamtWert() - zielKofferraum.getGesamtWert() && gewichtAbstand > quellKofferraum.getGesamtGewicht() - zielKofferraum.getGesamtGewicht()) {
						hatGeaendert = true;
						break;
					} else {
						quellKofferraum.addKoffer(koffer);
						zielKofferraum.entferneKoffer(koffer);
					}
				}
			}
			
			System.out.println("1: " + kofferraum1.getGesamtWert() + " " + kofferraum1.getGesamtGewicht());
			System.out.println("2: " + kofferraum2.getGesamtWert() + " " + kofferraum2.getGesamtGewicht());
		}
	}
}
