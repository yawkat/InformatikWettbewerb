package at.yawk.informatikwettbewerb.geldtransporter;

public final class Geldkoffer {
	private final int gewicht;
	private final int wert;
	
	public Geldkoffer(final int gewicht, final int wert) {
		this.gewicht = gewicht;
		this.wert = wert;
	}
	
	public int getGewicht() {
		return gewicht;
	}
	
	public int getWert() {
		return wert;
	}
	
	@Override
	public String toString() {
		return getClass().getName() +  ": [Gewicht: " + getGewicht() + "; Wert: " + getWert() + "]";
	}
}
