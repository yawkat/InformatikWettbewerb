package at.yawk.informatikwettbewerb.verben;

/**
 * Grundform als übergebenes Verb
 * */
public class Grundform implements Regel {

	@Override
	public boolean kannVerwendetWerden(String verbForm) {
		return verbForm.endsWith("en");
	}

	@Override
	public String getStamm(String verbForm) {
		return verbForm.substring(0, verbForm.length() - 2);
	}
	
}
