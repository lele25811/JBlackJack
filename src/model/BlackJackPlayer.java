package model;

import java.util.ArrayList;

/*
 * Classe che definisce un giocatore di BlackJack con le sue statistiche
 */
public class BlackJackPlayer extends Player{

	/*
	 * numeroPartite definisce il numero delle partite giocate da un giocatore
	 */
	private int numeroPartite;
	
	/*
	 * numeroVittorie definisce il numero di vittorie di un giocatore
	 */
	private int numeroVittorie;
	
	/*
	 *  numeroSconfitte definisce il numero di sconfitte di un giocatore
	 */
	private int numeroSconfitte;
	
	
	 /*
	  * livello definisce il livello di un giocatore 
	  */
	private int livello;
	
	/*
	 * Costruttore della classe che richiama il construttore della sua Superclasse
	 */
	public BlackJackPlayer(String nickname, String avatar) {
		super(nickname, avatar);
		numeroPartite = 0;
		numeroVittorie = 0;
		livello = 0;
	}
	
	//°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°Metodi Statistiche°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°
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
		return numeroSconfitte;
	}
	
	/*
	 * Metodo che ritorna il numero dei pareggi di un giocatore
	 */
	public int getNumeroPareggi() {
		return numeroPartite - (numeroVittorie + numeroSconfitte);
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
		return super.toString() + " BlackJackPlayer [numeroPartite=" + numeroPartite + ", numeroVittorie=" + numeroVittorie + ", numeroPareggi=" +this.getNumeroPareggi() + ", livello="
				+ livello + "]";
	}
	
	
}
