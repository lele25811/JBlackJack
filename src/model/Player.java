package model;

import java.io.Serializable;

/*
 * Classe che definisce il giocatore (superclasse)
 */
public class Player implements Serializable{
	
	private static final long serialVersionUID = 1L;

	/*
	 * nickname definisce il nome del giocatore
	 */
	private String nickname;
	
	/*
	 * avatar definisce l'immagine di profilo del giocatore
	 */
	private String avatar;
	
	/*
	 * Definisce se il giocatore è un bot o è il player
	 */
	private boolean isBot;
	
	/*
	 * Costruttore della classe Player
	 */
	public Player(String nickname, String avatar, boolean isBot) {
		this.nickname = nickname;
		this.avatar = avatar;
		this.isBot = isBot;
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
	
	public boolean isBot() {
		return isBot;
	}

	/*
	 * Metodo che ritorna la stringa di Player
	 */
	@Override
	public String toString() {
		return "Player [" + nickname + "," + avatar + "]";
	}
	
}
