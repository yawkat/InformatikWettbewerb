package at.yawk.informatikwettbewerb.verben;

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
