package at.yawk.informatikwettbewerb.verben;

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
