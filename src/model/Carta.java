package model;

public class Carta {
	private Seme seme;
	private Valore valore;
	
	// Costruttore
	public Carta(Valore valore, Seme seme) {
		this.valore = valore;
		this.seme = seme;
	}
	
	public String getSeme() {
		return seme.stampaSeme();
	}
	
	public int getValore() {
		return valore.getValore();
	}
	
	@Override
	public String toString() {
		return valore.stampaValore()+" di "+seme.stampaSeme();
	}
	

}
