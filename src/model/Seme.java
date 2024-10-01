package model;

/**
 * Classe ENUM che definisce il Seme della carta da gioco.
 */
public enum Seme {
	CUORI("Cuori"),
	QUADRI("Quadri"),
	FIORI("Fiori"),
	PICCHE("Picche");
	
	/**
	 * seme definisce il Seme della carta da gioco.
	 */
	private final String seme;
	
	/**
	 * Costruttore della classe Seme.
	 * @param seme il nome del seme della carta
	 */
	private Seme(String seme) {
		this.seme = seme;
	}
	
	/**
	 * Metodo che ritorna il seme della carta.
	 * @return il nome del seme della carta
	 */
	public String stampaSeme() {
		return seme;
	}
}