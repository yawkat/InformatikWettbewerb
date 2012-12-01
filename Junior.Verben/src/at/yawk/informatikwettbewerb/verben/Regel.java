package at.yawk.informatikwettbewerb.verben;


/**
 * Grundinterface Regel
 */
public interface Regel {
	public boolean kannVerwendetWerden(String verbForm);
	
	public String getStamm(String verbForm);
}
