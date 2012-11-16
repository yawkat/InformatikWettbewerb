package at.yawk.informatikwettbewerb.verben;

public interface Regel {
	public boolean kannVerwendetWerden(String verbForm);
	
	public String getStamm(String verbForm);
}
