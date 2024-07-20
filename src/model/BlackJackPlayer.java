package model;

import java.util.ArrayList;

/*
 * Classe che definisce un giocatore di BlackJack con le sue statistiche
 */
public class BlackJackPlayer extends Player{
	private ArrayList<Carta> mano = new ArrayList<Carta>();

	/*
	 * numeroPartite definisce il numero delle partite giocate da un giocatore
	 */
	private int numeroPartite;
	
	/*
	 * numeroVittorie definisce il numero di vittorie di un giocatore
	 */
	private int numeroVittorie;
	 
	 /*
	  * livello definisce il livello di un giocatore 
	  */
	private int livello;
	
	/*
	 * Costruttore della classe che richiama il construttore della sua Superclasse
	 */
	public BlackJackPlayer(String nickname, String avatar, boolean isBot) {
		super(nickname, avatar, isBot);
		numeroPartite = 0;
		numeroVittorie = 0;
		livello = 0;
	}
	
	public void addCarta(Carta carta) {
		mano.add(carta);
	}
	
	public ArrayList<Carta> getMano() {
		return mano;
	}
	
	public int[] getValoreMano() {
		int valore = mano.stream().mapToInt(carta -> carta.getValore()).sum();
		boolean asso = mano.stream().anyMatch(carta -> "Asso".equals(carta.getStringValore()));
		System.out.println("asso "+asso);
		if(asso) return new int[] {valore, valore+10};
		else return new int[] {valore};
	}
	
	/*
	 * Metodo che ritorna il numero delle partite giocate
	 */
	public int getNumeroPartite() {
		return numeroPartite;
	}
	
	/*
	 * Metodo che ritorna il numero di vittorie di un giocatore
	 */
	public int getNumeroVittorie() {
		return numeroVittorie;
	}
	
	/*
	 * Metodo che ritorna il numero delle sconfitte di un giocatore
	 */
	public int getNumeroSconfitte() {
		return numeroPartite - numeroVittorie;
	}
	
	/*
	 * Metodo che ritorna il livello di un giocatore
	 */
	public int getLivello() {
		return livello;
	}
	
	/*
	 * Metodo che aggiunge una partita giocata
	 */
	public void addPartita() {
		numeroPartite++;
	}
	
	/*
	 * Metodo che aggiunge una vittoria
	 */
	public void addVittoria() {
		numeroVittorie++;
		if (numeroVittorie % 5 == 0) levelUp();
	}
	
	/*
	 * Metodo che ogni 5 partite vinte fa salire di livello il giocatore
	 */
	private void levelUp() {
		livello++;
	}

	@Override
	public String toString() {
		return super.toString() + " BlackJackPlayer [numeroPartite=" + numeroPartite + ", numeroVittorie=" + numeroVittorie + ", livello="
				+ livello + "]";
	}
	
	
}
