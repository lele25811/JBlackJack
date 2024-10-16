package model;

/**
 * Classe che definisce un giocatore di BlackJack con le sue statistiche.
 */
public class BlackJackPlayer extends Player{

	/**
	 * numeroPartite definisce il numero delle partite giocate da un giocatore.
	 */
	private int numeroPartite;
	
	/**
	 * numeroVittorie definisce il numero di vittorie di un giocatore.
	 */
	private int numeroVittorie;
	
	 /**
	  * livello definisce il livello di un giocatore .
	  */
	private int livello;
	
	/**
	 * Costruttore della classe che richiama il construttore della sua Superclasse.
	 * @param nickname il nome del giocatore
	 * @param avatar l'avatar del giocatore
	 */
	public BlackJackPlayer(String nickname, String avatar) {
		super(nickname, avatar);
		numeroPartite = 0;
		numeroVittorie = 0;
		livello = 0;
	}
	
	/**
	 * Metodo che ritorna il numero delle partite giocate.
	 * @return il numero di partite giocate
	 */
	public int getNumeroPartite() {
		return numeroPartite;
	}
	
	/**
	 * Metodo che ritorna il numero di vittorie di un giocatore.
	 * @return il numero di partite vinte
	 */
	public int getNumeroVittorie() {
		return numeroVittorie;
	}
	
	/**
	 * Metodo che ritorna il numero delle sconfitte di un giocatore.
	 * @return il numero di partite perse
	 */
	public int getNumeroSconfitte() {
		return numeroPartite - numeroVittorie;
	}
	
	/**
	 * Metodo che ritorna il livello di un giocatore.
	 * @return il livello del giocatore
	 */
	public int getLivello() {
		return livello;
	}
	
	/**
	 * Metodo che aggiunge una partita giocata.
	 */
	public void addPartita() {
		numeroPartite++;
	}
	
	/**
	 * Metodo che aggiunge una vittoria, e fa salire di livello ogni 5 vittorie.
	 */
	public void addVittoria() {
		numeroVittorie++;
		if (numeroVittorie % 5 == 0) levelUp();
	}
	
	/**
	 * Metodo che fa salire di livello il giocatore.
	 */
	private void levelUp() {
		livello++;
	}

	/**
	 * Metodo che ritorna il BLackJackPlayer sottoforma di stringa.
	 * @return il BlackJackPlayer sottoforma di stringa
	 */
	@Override
	public String toString() {
		return super.toString() + " BlackJackPlayer [numeroPartite=" + numeroPartite + ", numeroVittorie=" + numeroVittorie + ", numeroSconfitte=" + this.getNumeroSconfitte() + ", livello="
				+ livello + "]";
	}
}