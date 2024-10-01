package model;

/**
 *  Classe ENUM che definisce il nome del Valore delle carte con il rispettivo valore di gioco. 
 */
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
	DIECI(10, "Dieci"),
	JACK(10, "Jack"),
	DONNA(10, "Donna"),
	KAPPA(10, "Kappa");
	
	/**
	 * nValore definisce il valore (numerico) di gioco della carta.
	 */
	private final int nValore;
	
	/**
	 * valore definisce il nome del Valore della carta.
	 */
	private final String valore;
	
	/**
	 * Costruttore della classe Valore.
	 * @param nValore il valore della carta
	 * @param valore il nome della carta
	 */
	private Valore(int nValore, String valore) {
		this.nValore = nValore;
		this.valore = valore;
	}
	
	/**
	 *  Metodo che ritorna il valore di gioco.
	 *  @return il valore numerico della carta
	 */
	public int getValore() {
		return nValore;
	}
	
	/**
	 *  Metodo che il nome del Valore.
	 *  @return il nome della carta
	 */
	public String stampaValore() {
		return valore;
	}
}