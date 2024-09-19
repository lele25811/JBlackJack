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
	
	/*
	 * Ritorna l'array dei valori possibili della mano, in modo che i bot possono scegliere in autonomia e il player sceglie tramite menu popup
	 */
	public int[] getPuntiMano() {
		int valore = mano.stream().mapToInt(carta -> carta.getValore()).sum();
		boolean isAsso = mano.stream().anyMatch(carta -> "Asso".equals(carta.getStringValore()));
		if(isAsso) return new int[] {valore, valore+10};
		else return new int[] {valore};
	}
	
	public int[] getValoreManoIniziale() {
		int valore = mano.stream().mapToInt(carta -> carta.getValore()).sum();
		boolean isAsso = mano.stream().anyMatch(carta -> "Asso".equals(carta.getStringValore()));
		if(isAsso) return new int[] {valore, valore+10};
		else return new int[] {valore};
	}
	
	public int[] getValoreMano(int valoreAttuale) {
		boolean isAsso = mano.stream()
                .reduce((primo, secondo) -> secondo)
                .map(carta -> "Asso".equalsIgnoreCase(carta.getStringValore()))
                .orElse(false);
		int valoreUltimaCarta = mano.stream()
                  .map(Carta::getValore)
                  .reduce((primo, secondo) -> secondo)
                  .orElseThrow(() -> new IllegalStateException("La lista è vuota"));
		if (isAsso) return new int[] {valoreUltimaCarta+valoreAttuale, valoreUltimaCarta+valoreAttuale+10};
		else return new int[] {valoreUltimaCarta+valoreAttuale};
	}
	
	public Carta getLastCard() {
		if (mano.size() > 0) {
			return mano.get(mano.size()-1);
		}
		return null;
	}
	
	public boolean haveAsso() {
		if(getMano().size() > 1) {
			boolean isAsso = getMano().stream().anyMatch(carta -> "Asso".equals(carta.getStringValore()));
			return isAsso;
		}else {
			return false;
		}
	}
	
	public boolean lastIsAsso() {
		if(getMano().size() > 1) {
			boolean isAsso = getMano().stream().skip(getMano().size()-1).anyMatch(carta -> "Asso".equals(carta.getStringValore()));
			return isAsso;
		}else {
			return false;
		}
	}
	
}
