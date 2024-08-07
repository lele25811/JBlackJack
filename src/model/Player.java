package model;

import java.io.Serializable;
import java.util.ArrayList;

/*
 * Classe che definisce il giocatore (superclasse)
 */
public class Player implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private ArrayList<Carta> mano = new ArrayList<Carta>();

	/*
	 * nickname definisce il nome del giocatore
	 */
	private String nickname;
	
	/*
	 * avatar definisce l'immagine di profilo del giocatore
	 */
	private String avatar;
	
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
		return "Player [" + nickname + "," + avatar + "]";
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
}
