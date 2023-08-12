import java.util.ArrayList;

import mazzo.Carta;
import mazzo.Mazzo;

public class Mazziere {
	
	/*
	 * Il mazziere controlla il mazzo e imposta le regole di gioco.
	 */
	
	private Mazzo m;
	
	public Mazziere() {
		m = new Mazzo();
	}
	
	
	// da modificare
	public String prossima() {
		Carta c = m.next();
		return c.toString();
	}
	
	
	
}
