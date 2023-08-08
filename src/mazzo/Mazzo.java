package mazzo;

import java.util.ArrayList;
import java.util.Collections;

public class Mazzo {
	private ArrayList<Carta> mazzo = new ArrayList<>(52);
	private int posizioneCarta = 0;
	
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
	
	
	
}
