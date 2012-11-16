package at.yawk.informatikwettbewerb.verben;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Regeln {
	private static List<Regel>	regeln	= Arrays.asList(new Sonderformen(), new PraesensDu(), new PraetertitumEr(), new PerfektEr(), new PartizipPraesensAktiv(), new Grundform(), new Imperativ());
	
	private static String getVerb(String form) {
		for(final Regel r : regeln) {
			if(r.kannVerwendetWerden(form)) {
				return r.getStamm(form) + "en";
			}
		}
		return form;
	}
	
	public static void main(String[] args) {
		final Scanner s = new Scanner(System.in);
		try {
			System.out.println("Form:");
			System.out.println("Grundform: " + getVerb(s.next()));
		} finally {
			s.close();
		}
	}
}
