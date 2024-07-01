package model;

/*
 * Classe che definisce una Carta da gioco
 */
public class Carta {
	/*
	 * Seme della carta
	 */
	private Seme seme;
	
	/*
	 * Valore della carta
	 */
	private Valore valore;
	
	/*
	 * Costruttore della classe Carta
	 */
	public Carta(Valore valore, Seme seme) {
		this.valore = valore;
		this.seme = seme;
	}
	
	/*
	 * Metodo che ritorna il seme della carta
	 */
	public String getSeme() {
		return seme.stampaSeme();
	}
	
	/*
	 * Metodo che ritorna il valore della carta
	 */
	public int getValore() {
		return valore.getValore();
	}
	
	/*
	 * toStirng: stampa la coppia attuale di (valore,seme) 
	 * che compone la carta attuale
	 */
	@Override
	public String toString() {
		return valore.stampaValore()+" di "+seme.stampaSeme();
	}
	

}
