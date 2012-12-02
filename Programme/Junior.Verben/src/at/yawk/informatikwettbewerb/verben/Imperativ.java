package at.yawk.informatikwettbewerb.verben;

/**
 * Im Zweifel Imperativ
 */
public class Imperativ implements Regel {

	@Override
	public boolean kannVerwendetWerden(String verbForm) {
		return true;
	}

	@Override
	public String getStamm(String verbForm) {
		return verbForm;
	}
	
}
