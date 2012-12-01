package at.yawk.informatikwettbewerb.verben;

/**
 * Partizip Präsens, Aktiv (schweig<i>end</i>)
 * @author Jonalu
 */
public class PartizipPraesensAktiv implements Regel {
	
	@Override
	public boolean kannVerwendetWerden(String verbForm) {
		return verbForm.endsWith("end");
	}
	
	@Override
	public String getStamm(String verbForm) {
		return verbForm.substring(0, verbForm.length() - 3);
	}
	
}
