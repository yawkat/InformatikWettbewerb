package at.yawk.informatikwettbewerb.verben;

/**
 * Präsens zweite Person Singular (sag<i>st</i>)
 */
public class PraesensDu implements Regel {

	@Override
	public boolean kannVerwendetWerden(String verbForm) {
		return verbForm.endsWith("st");
	}

	@Override
	public String getStamm(String verbForm) {
		return verbForm.substring(0, verbForm.length() - 2);
	}
	
}
