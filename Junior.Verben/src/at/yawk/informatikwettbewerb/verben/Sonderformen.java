package at.yawk.informatikwettbewerb.verben;

/**
 * Spezielle Sonderformen die nicht in eigenen Regeln implementiert sind
 * */
public class Sonderformen implements Regel {
	private String[][]	sonderformen	= { { "mitgehalten", "mithalt" }, { "genehmigt", "genehmig" }, { "arbeite", "arbeit" }, { "gebet", "bet" } };
	
	@Override
	public boolean kannVerwendetWerden(String verbForm) {
		for(String[] form : sonderformen)
			if(form[0].equals(verbForm))
				return true;
		return false;
	}
	
	@Override
	public String getStamm(String verbForm) {
		for(String[] form : sonderformen)
			if(form[0].equals(verbForm))
				return form[1];
		return verbForm;
	}
}
