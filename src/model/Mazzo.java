package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/*
 * Classe Mazzo che definisce l'insieme di carte che fanno parte del mazzo da gioco
 * la classe implementa Iterator per poter iterare sulle carte
 */
public class Mazzo implements Iterator<Carta>{
	
	/*
	 * Lista di (52) Carte che compone il mazzo da gioco 
	 */
	private ArrayList<Carta> mazzo = new ArrayList<>(52);
	
	/*
	 * Variabile iteratrice per quando si sfutta iterator per iterare sulle carte
	 */
	private int posizioneCarta = 0;
	
	/*
	 * Istanza mazzo
	 */
	private static Mazzo instance;
	
	/*
	 * Costruttore della classe mazzo che mescola le carte appena viene creato il mazzo
	 */
	public Mazzo() {
		mescola();
	}
	
	/*
	 * Metodo che permette di non creare altri mazzi (singleton pattern)
	 */
	public Mazzo getInstance() {
		if (instance == null) instance = new Mazzo();
		return instance;
	}
	
	/*
	 * Metodo che riempie semplicemente il mazzo con tutte le carte,
	 * ogni carta avrà una coppia (seme, valore),
	 * ritorna il mazzo
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
	
	/*
	 * Metodo che mescola le carte al interno del mazzo da gioco
	 */
	public ArrayList<Carta> mescola() {
		riempimento();
		Collections.shuffle(mazzo);
		return mazzo;
	}
	
	/*
	 * Metodo che ritorna la Lista delle Carte da gioco, quindi il mazzo
	 */
	public ArrayList<Carta> getMazzo() {
		return mazzo;
	}

	/*
	 * Metodo del Iteratore che controlla se nella Lista del mazzo è presente una carta successiva
	 */
	@Override
	public boolean hasNext() {
		return posizioneCarta < mazzo.size();
	}

	/*
	 * Metodo che ritorna la carta successiva
	 */
	@Override
	public Carta next() {
		if(hasNext()) {
			return mazzo.get(posizioneCarta++);
		}
		return null;
	}
	
}
