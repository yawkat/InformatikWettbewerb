package at.yawk.informatikwettbewerb.verben;

/**
 * Präteritum dritte Person Singular (leite<i>te</i>)
 */
public class PraetertitumEr implements Regel {

	@Override
	public boolean kannVerwendetWerden(String verbForm) {
		return verbForm.endsWith("te");
	}

	@Override
	public String getStamm(String verbForm) {
		return verbForm.substring(0, verbForm.length() - 3);
	}
	
}
