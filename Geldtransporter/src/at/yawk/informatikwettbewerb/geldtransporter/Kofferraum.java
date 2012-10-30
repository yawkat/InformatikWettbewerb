package at.yawk.informatikwettbewerb.geldtransporter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Kofferraum {
	private final Collection<Geldkoffer> enthalteneKoffer = new ArrayList<>();
	
	public int getGesamtWert() {
		int gesamtWert = 0;
		for(final Geldkoffer koffer : enthalteneKoffer) {
			gesamtWert += koffer.getWert();
		}
		return gesamtWert;
	}
	
	public int getGesamtGewicht() {
		int gesamtGewicht = 0;
		for(final Geldkoffer koffer : enthalteneKoffer) {
			gesamtGewicht += koffer.getGewicht();
		}
		return gesamtGewicht;
	}
	
	public void addKoffer(final Geldkoffer koffer) {
		this.enthalteneKoffer.add(koffer);
	}
	
	public Collection<Geldkoffer> getEnthalteneKoffer() {
		return Collections.unmodifiableCollection(enthalteneKoffer);
	}
	
	public void entferneKoffer(final Geldkoffer koffer) {
		enthalteneKoffer.remove(koffer);
	}
	
	public void leereKofferraum() {
		enthalteneKoffer.clear();
	}
}
