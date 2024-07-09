package model;

/*
 * Classe che definisce il giocatore (superclasse)
 */
public class Player {
	
	/*
	 * nickname definisce il nome del giocatore
	 */
	protected String nickname;
	
	/*
	 * avatar definisce l'immagine di profilo del giocatore
	 */
	protected String avatar;
	
	/*
	 * Costruttore della classe Player
	 */
	public Player(String nickname, String avatar) {
		this.nickname = nickname;
		this.avatar = avatar;
	}
	
	/*
	 * Metodo che ritorna il nome del giocatore
	 */
	public String getNickname() {
		return nickname;
	}
	
	/*
	 * Metodo che ritorna, sottoforma di stringa, l'avatar del giocatore
	 */
	public String getAvatar() {
		return avatar;
	}

	/*
	 * Metodo che ritorna la stringa di Player
	 */
	@Override
	public String toString() {
		return "Player [ " + nickname + "," + avatar + "]";
	}
	
}
