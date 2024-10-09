package model;

/**
 *  Classe ENUM che definisce il nome del Valore delle carte con il rispettivo valore di gioco. 
 */
public enum Valore {
	
	/**
	 * Asso - "Asso" valore 1 (oppure 11)
	 */
	ASSO(1,"Asso"),
	
	/**
	 *  Due - "Due" valore 2
	 */
	DUE(2, "Due"),
	
	/**
	 * Tre - "Tre" valore 3
	 */
	TRE(3, "Tre"),
	
	/**
	 * Quattro - "Quattro" - valore 4
	 */
	QUATTRO(4, "Quattro"),
	
	/**
	 * Cinque - "Cinque" valore 5
	 */
	CINQUE(5, "Cinque"),
	
	/**
	 * Sei - "Sei" valore 6
	 */
	SEI(6, "Sei"),
	
	/**
	 * Sette - "Sette" valore 7
	 */
	SETTE(7, "Sette"),
	
	/**
	 * Otto - "Otto" valore 8
	 */
	OTTO(8,"Otto"),
	
	/**
	 * Nove - "Nove" valoe 9
	 */
	NOVE(9, "Nove"),
	
	/**
	 * Dieci - "Dieci" valore 10
	 */
	DIECI(10, "Dieci"),
	
	/**
	 * Jack - "Jack" valore 10
	 */
	JACK(10, "Jack"),
	
	/**
	 * Donna - "Donna" valore 10
	 */
	DONNA(10, "Donna"),
	
	/**
	 * Kappa - "Kappa" valore 10
	 */
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