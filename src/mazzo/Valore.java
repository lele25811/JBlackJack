package mazzo;

public enum Valore {
	ASSO(1,"Asso"),
	DUE(2, "Due"),
	TRE(3, "Tre"),
	QUATTRO(4, "Quattro"),
	CINQUE(5, "Cinque"),
	SEI(6, "Sei"),
	SETTE(7, "Sette"),
	OTTO(8,"Otto"),
	NOVE(9, "Nove"),
	JACK(10, "Jack"),
	DONNA(11, "Donna"),
	KAPPA(12, "Kappa");
	
	private final int nValore;
	private final String valore;
	
	// Costruttore
	private Valore(int nValore, String valore) {
		this.nValore = nValore;
		this.valore = valore;
	}
	
	// Stampa Valore
	public int getValore() {
		return nValore;
	}
	
	public String stampaValore() {
		return valore;
	}

}
