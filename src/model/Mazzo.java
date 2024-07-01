package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class Mazzo implements Iterator<Carta>{
	private ArrayList<Carta> mazzo = new ArrayList<>(52);
	private int posizioneCarta = 0;
	
	private static Mazzo instance;
	
	public Mazzo() {
		mescola();
	}
	
	public Mazzo getInstance() {
		if (instance == null) instance = new Mazzo();
		return instance;
	}
	
	// Metodo Di Riempimento semplice del mazzo
	private ArrayList<Carta> riempimento() {
		for(Seme seme: Seme.values()) {
			for(Valore valore: Valore.values()) {
				Carta carta = new Carta(valore, seme);
				mazzo.add(carta);
			}
		}
		return mazzo;
	}
	
	// Metodo Di Mescolamento Mazzo
	public ArrayList<Carta> mescola() {
		riempimento();
		Collections.shuffle(mazzo);
		return mazzo;
	}
	
	public ArrayList<Carta> getMazzo() {
		return mazzo;
	}

	@Override
	public boolean hasNext() {
		return posizioneCarta < mazzo.size();
	}

	@Override
	public Carta next() {
		if(hasNext()) {
			return mazzo.get(posizioneCarta++);
		}
		return null;
	}
	
}
