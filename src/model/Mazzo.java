package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 * Classe Mazzo che definisce l'insieme di carte che fanno parte del mazzo da gioco
 * la classe implementa Iterator per poter iterare sulle carte.
 */
public class Mazzo implements Iterator<Carta>{
	
	/**
	 * Lista di (52) Carte che compone il mazzo da gioco .
	 */
	private ArrayList<Carta> mazzo = new ArrayList<>(52);
	
	/**
	 * numero che viene usato come iteratore per iterare sulle carte.
	 */
	private int posizioneCarta = 0;
	
	
	/**
	 * Costruttore della classe mazzo che mescola le carte appena viene creato il mazzo.
	 */
	public Mazzo() {
		mescola();
	}
	
	/**
	 * Metodo che riempie semplicemente il mazzo con tutte le carte,
	 * ogni carta avrà una coppia (seme, valore).
	 * @return il mazzo di carte ordinato
	 */
	private ArrayList<Carta> riempimento() {
		for(Seme seme: Seme.values()) {
			for(Valore valore: Valore.values()) {
				Carta carta = new Carta(valore, seme);
				mazzo.add(carta);
			}
		}
		return mazzo;
	}
	
	/**
	 * Metodo che mescola le carte al interno del mazzo da gioco.
	 * @return il mazzo di carte mescolato
	 */
	public ArrayList<Carta> mescola() {
		riempimento();
		Collections.shuffle(mazzo);
		return mazzo;
	}
	
	/**
	 * Metodo che ritorna la Lista delle Carte da gioco, quindi il mazzo.
	 * @return il mazzo di carte
	 */
	public ArrayList<Carta> getMazzo() {
		return mazzo;
	}

	/**
	 * Metodo del Iteratore che controlla se nella Lista del mazzo è presente una carta successiva.
	 * @return valore booleano vero se esiste una carta dopo, oppure falso
	 */
	@Override
	public boolean hasNext() {
		return posizioneCarta < mazzo.size();
	}

	/**
	 * Metodo che ritorna la carta successiva.
	 * @return una carta
	 */
	@Override
	public Carta next() {
		if(hasNext()) {
			return mazzo.get(posizioneCarta++);
		}
		return null;
	}
}