package model;

/**
 * Classe che definisce una Carta da gioco.
 */
public class Carta {
	/**
	 * Seme della carta.
	 */
	private Seme seme;
	
	/**
	 * Valore della carta.
	 */
	private Valore valore;
	
	/**
	 * Costruttore della classe Carta.
	 * @param valore il Valore della carta
	 * @param seme il Seme della carta
	 */
	public Carta(Valore valore, Seme seme) {
		this.valore = valore;
		this.seme = seme;
	}
	
	/**
	 * Metodo che ritorna il seme della carta.
	 * @return il nome del seme della carta
	 */
	public String getSeme() {
		return seme.stampaSeme();
	}
	
	/**
	 * Metodo che ritorna il valore della carta.
	 * @return il valore della carta
	 */
	public int getValore() {
		return valore.getValore();
	}
	
	/**
	 * Metodo che ritorna il nome del valore della carta.
	 * @return il nome del valore della carta
	 */
	public String getStringValore() {
		return valore.stampaValore();
	}
	
	/**
	 * Metodo che ritorna la carta sotto forma di Stringa.
	 * @return la stringa con la carta
	 */
	@Override
	public String toString() {
		return valore.stampaValore()+" di "+seme.stampaSeme();
	}
	
	/**
	 * Metodo che ritorna il percorso al immagine della carta.
	 * @return il percorso sottoforma di stringa dove si trova la carta
	 */
	public String getPath() {
		return valore.stampaValore()+seme.stampaSeme()+".png";
	}
}