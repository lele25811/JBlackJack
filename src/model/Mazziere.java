package model;
import java.util.ArrayList;

public class Mazziere {
	
	/*
	 * Il mazziere controlla il mazzo e imposta le regole di gioco.
	 */
	
	private Mazzo m;
	
	public Mazziere() {
		m = new Mazzo();
	}
	
	
	public void mazzoStampa() {
		System.out.println(m.toString());
	}
	
	
	// da modificare
	public String prossima() {
		Carta c = m.next();
		return c.toString();
	}
	
	
	
}
