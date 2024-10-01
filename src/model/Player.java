package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe che rappresenta un giocatore nel gioco di BlackJack.
 * Include funzionalità per gestire le carte del giocatore, la divisione delle mani (split) 
 * e il calcolo del punteggio.
 * Questa classe è serializzabile per consentire la persistenza dei dati del giocatore.
 */
public class Player implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Mano principale del giocatore.
	 */
	private ArrayList<Carta> mano = new ArrayList<Carta>();
	
	/**
	 * Map che rappresenta le mani separate dopo uno split. 
	 * La chiave rappresenta il numero della mano e il valore è la lista di carte associate.
	 */
	private Map<Integer, ArrayList<Carta>> manoSplit;
	
	/**
	 * Indica se il giocatore ha eseguito uno split.
	 */
	private boolean isSplit = false;

	/**
	 * Indice della mano attualmente in gioco durante lo split.
	 */
	private Integer indexMano;

	/**
	 * Punteggio della prima mano nel caso di uno split.
	 */
	private int puntiMano1;

	/**
	 * Punteggio della seconda mano nel caso di uno split.
	 */
	private int puntiMano2;

	/**
	 * nickname definisce il nome del giocatore.
	 */
	private String nickname;
	
	/**
	 * avatar definisce l'immagine di profilo del giocatore
	 */
	private String avatar;


	/**
	 * Costruttore della classe Player.
	 * @param nickname il nome del giocatore
	 * @param avatar l'avatar del giocatore
	 */
	public Player(String nickname, String avatar) {
		this.nickname = nickname;
		this.avatar = avatar;
	}
	
	/**
	 * Metodo che ritorna il nome del giocatore.
	 * @return il nickname del giocatore
	 */
	public String getNickname() {
		return nickname;
	}
	
	/**
	 * Metodo che ritorna, sottoforma di stringa, l'avatar del giocatore.
	 * @return l'avatar del giocatore
	 */
	public String getAvatar() {
		return avatar;
	}

	/**
	 * Metodo che ritorna la stringa di Player.
	 * @return una stringa che rappresenta il giocatore
	 */
	@Override
	public String toString() {
		return "Player [" + nickname + "," + avatar + "]";
	}
	
	/**
	 * Aggiunge una carta alla mano del giocatore.
	 * Se è attivo lo split, la carta viene aggiunta alla mano corrente dello split.
	 * @param carta la carta da aggiungere alla mano
	 */
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
	
	/**
	 * Metodo che ritorna la mano attuale del giocatore.
	 * @return le carte che ha in mano il giocatore
	 */
	public ArrayList<Carta> getMano() {
		return mano;
	}
	
	/**
	 * Restituisce i possibili punteggi della mano principale.
	 * Se la mano contiene un Asso, vengono considerati due valori (con Asso a 1 o 11).
	 * @return un array di possibili punteggi della mano
	 */
	public int[] getPuntiMano() {
		int valore = mano.stream().mapToInt(carta -> carta.getValore()).sum();
		boolean isAsso = mano.stream().anyMatch(carta -> "Asso".equals(carta.getStringValore()));
		if(isAsso) return new int[] {valore, valore+10};
		else return new int[] {valore};
	}
	
	/**
	 * Restituisce i possibili valori della mano iniziale.
	 * @return un array di possibili valori della mano iniziale
	 */
	public int[] getValoreManoIniziale() {
		int valore = mano.stream().mapToInt(carta -> carta.getValore()).sum();
		boolean isAsso = mano.stream().anyMatch(carta -> "Asso".equals(carta.getStringValore()));
		if(isAsso) return new int[] {valore, valore+10};
		else return new int[] {valore};
	}
	
	/**
	 * Calcola i possibili valori della mano corrente con l'ultima carta aggiunta.
	 * @param valoreAttuale il valore attuale della mano
	 * @return un array di possibili valori della mano
	 */
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
	
	/**
	 * Restituisce l'ultima carta aggiunta alla mano del giocatore.
	 * @return l'ultima carta o null se non ci sono carte
	 */
	public Carta getLastCard() {
		if (mano.size() > 0) {
			return mano.get(mano.size()-1);
		}
		return null;
	}
	
	/**
	 * Verifica se nella mano del giocatore è presente un Asso.
	 * @return true se la mano contiene un Asso, false altrimenti
	 */
	public boolean haveAsso() {
		if(getMano().size() > 1) {
			boolean isAsso = getMano().stream().anyMatch(carta -> "Asso".equals(carta.getStringValore()));
			return isAsso;
		}else {
			return false;
		}
	}
	
	/**
	 * Verifica se l'ultima carta aggiunta è un Asso.
	 * @return true se l'ultima carta è un Asso, false altrimenti
	 */
	public boolean lastIsAsso() {
		if(getMano().size() > 1) {
			boolean isAsso = getMano().stream().skip(getMano().size()-1).anyMatch(carta -> "Asso".equals(carta.getStringValore()));
			return isAsso;
		}else {
			return false;
		}
	}
	
	/**
	 * Metodo che resetta la mano del giocatore
	 */
	public void resetMano() {
		mano.clear();
	}
	
	/**
	 * Inizializza lo split delle mani. 
	 * Ogni carta nella mano originale viene assegnata a una mano separata.
	 */
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
	
	/**
	 * Restituisce la mappa delle mani dopo uno split.
	 * @return la mappa delle mani splittate
	 */
	public Map<Integer, ArrayList<Carta>> getManiSplit() {
		return manoSplit;
	}

	/**
	 * Restituisce la prima mano dopo uno split.
	 * @return la prima mano splittata
	 */
	public ArrayList<Carta> getMano1() {
		return manoSplit.get(0);
	}
	

	/**
	 * Restituisce la seconda mano dopo uno split.
	 * @return la seconda mano splittata
	 */
	public ArrayList<Carta> getMano2() {
		return manoSplit.get(1);
	}
	
	/**
	 * Restituisce il valore dell'ultima carta nella mano splittata specificata.
	 * @param indexMano l'indice della mano
	 * @return il valore dell'ultima carta
	 */
	public int getLastCartaValoreSplit(Integer indexMano) {
		return manoSplit.getOrDefault(indexMano, new ArrayList<>()).stream()
			    .reduce((prima, seconda) -> seconda)  // Ottiene l'ultima carta
			    .map(Carta::getValore)               // Ottiene il valore della carta
			    .orElseThrow(() -> new IllegalStateException("La lista è vuota o la chiave non esiste"));
	}
	
	/**
	 * Restituisce il valore della stringa dell'ultima carta nella mano splittata specificata.
	 * @param indexMano l'indice della mano
	 * @return il valore della stringa dell'ultima carta
	 */
	public String getLastCartaSplit(Integer indexMano) {
        return manoSplit.getOrDefault(indexMano, new ArrayList<>()).stream()
	    .reduce((prima, seconda) -> seconda)  // Ottiene l'ultima carta
	    .map(Carta::getStringValore)
	    .orElseThrow(() -> new IllegalStateException("La lista è vuota o la chiave non esiste"));
	}

	/**
	 * Metodo che aggiorna il valore del indexMano, passa alla mano successiva.
	 */
	public void updateIndexMano() {
		indexMano +=1;
	}
	
	 /**
	  * Metodo che ritorna il valore boolean del isSplit.
	  * @return ritorna true se lo split è attivo, altrimenti falso
	  */
	public boolean isSplit() {
		return isSplit;
	}
	
	/**
	 * Metodo che ritorna il numero della mano attuale.
	 * @return il numero di mano attuale
	 */
	public Integer getIndexMano() {
		return indexMano;
	}
	
	/**
	 * Metodo che inizializza il valore delle due mani che vengono create con lo split.
	 * @param puntiMano1 punteggio mano uno
	 * @param puntiMano2 punteggio mano due
	 */
	public void setPuntiPlayerSplit(Integer puntiMano1, Integer puntiMano2) {
		this.puntiMano1 = puntiMano1;
		this.puntiMano2 = puntiMano2;
	}
	
	/**
	 * Metodo che controlla se il punteggio delle due mani vince sul punteggio del banco, e 
	 * se non è un valore maggiore di 21.
	 * @param puntiBanco punteggio del banco
	 * @return true se il giocatore vince con una delle due mani, altrimenti false
	 */
	public boolean getRisultatoSplit(int puntiBanco) {
		return (puntiBanco < puntiMano1 && puntiMano1 < 22) || (puntiBanco < puntiMano2 && puntiMano2 < 22);
	}
	
	/**
	 * Metodo che resetta i parametri del player, per iniziare una nuova partita.
	 */
	public void resetPartita() {
		mano.clear();
		manoSplit.clear();
		indexMano = null;
		isSplit = false;
	}
}
