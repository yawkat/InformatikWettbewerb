package at.yawk.informatikwettbewerb.verben;

public class PerfektEr implements Regel {
	
	@Override
	public boolean kannVerwendetWerden(String verbForm) {
		return verbForm.matches("ge.+t");
	}
	
	@Override
	public String getStamm(String verbForm) {
		return verbForm.substring(2, verbForm.length() - 1);
	}
	
}
