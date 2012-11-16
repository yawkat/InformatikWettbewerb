package at.yawk.informatikwettbewerb.verben;

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
