package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/*
 * Classe che definisce il giocatore (superclasse)
 */
public class Player implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private ArrayList<Carta> mano = new ArrayList<Carta>();
	private Map<Integer, ArrayList<Carta>> manoSplit;
	private boolean isSplit = false;
	private Integer indexMano;
	private int puntiMano1;
	private int puntiMano2;

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
		if(!isSplit) {
			mano.add(carta);
		}else {
			ArrayList<Carta> manoAttuale = new ArrayList<>();
			manoAttuale.addAll(manoSplit.get(indexMano));
			manoAttuale.add(carta);
			manoSplit.put(indexMano, manoAttuale);
		}
	}
	
	public void StampaManiSplit() {
		for (Map.Entry<Integer, ArrayList<Carta>> entry : manoSplit.entrySet()) {
		    Integer key = entry.getKey();
		    ArrayList<Carta> value = entry.getValue();

		    // Stampa la chiave
		    System.out.println("Mano numero: " + key);

		    // Stampa ogni carta nella lista
		    System.out.println("Carte nella mano:");
		    for (Carta carta : value) {
		        System.out.println(carta);  // Assumendo che la classe 'Carta' abbia un toString() definito
		    }
		}
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
	
	public void resetMano() {
		mano.clear();
	}
	
	public void maniSplit() {
		indexMano = 0;
		isSplit = true;
		manoSplit = new HashMap<>();
		for(int i = 0; i<mano.size(); i++) {
			ArrayList<Carta> cartaMano = new ArrayList<>();
			cartaMano.add(mano.get(i));
			manoSplit.put(i, cartaMano);
		}
	}
	
	public Map<Integer, ArrayList<Carta>> getManiSplit() {
		return manoSplit;
	}
	
	public ArrayList<Carta> getMano1() {
		return manoSplit.get(0);
	}
	
	public ArrayList<Carta> getMano2() {
		return manoSplit.get(1);
	}
	
	public int getLastCartaValoreSplit(Integer indexMano) {
		return manoSplit.getOrDefault(indexMano, new ArrayList<>()).stream()
			    .reduce((prima, seconda) -> seconda)  // Ottiene l'ultima carta
			    .map(Carta::getValore)               // Ottiene il valore della carta
			    .orElseThrow(() -> new IllegalStateException("La lista è vuota o la chiave non esiste"));
	}
	
	public String getLastCartaSplit(Integer indexMano) {
        return manoSplit.getOrDefault(indexMano, new ArrayList<>()).stream()
	    .reduce((prima, seconda) -> seconda)  // Ottiene l'ultima carta
	    .map(Carta::getStringValore)
	    .orElseThrow(() -> new IllegalStateException("La lista è vuota o la chiave non esiste"));
	}

	public void updateIndexMano() {
		indexMano +=1;
	}
	
	public boolean isSplit() {
		return isSplit;
	}
	
	public Integer getIndexMano() {
		return indexMano;
	}
	
	public void setPuntiPlayerSplit(Integer puntiMano1, Integer puntiMano2) {
		this.puntiMano1 = puntiMano1;
		this.puntiMano2 = puntiMano2;
		System.out.println("Aggiornamento punti...");
		System.out.println("PuntiMano1 "+puntiMano1+" PuntiMano2 "+puntiMano2);
	}
	
	public boolean getRisultatoSplit(int puntiBanco) {
		System.out.println("Controllo punteggio dal player...con puntiBanco "+puntiBanco);
		return (puntiBanco < puntiMano1 && puntiMano1 < 22) || (puntiBanco < puntiMano2 && puntiMano2 < 22);
	}
	
	public void resetPartita() {
		mano.clear();
		manoSplit.clear();
		indexMano = null;
		isSplit = false;
	}
}
