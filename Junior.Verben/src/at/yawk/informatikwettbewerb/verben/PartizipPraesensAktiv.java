package at.yawk.informatikwettbewerb.verben;

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
