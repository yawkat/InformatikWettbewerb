package at.yawk.informatikwettbewerb.verben;

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
