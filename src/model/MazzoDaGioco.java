package model;
import java.util.ArrayList;
import java.util.Iterator;

public class MazzoDaGioco {
	
	/*
	 * Il mazziere controlla il mazzo e imposta le regole di gioco.
	 */
	ArrayList<Carta> mazzoGioco = new ArrayList<Carta>();
	ArrayList<Carta> carteInGioco = new ArrayList<Carta>();
	ArrayList<Carta> mazzoScarti = new ArrayList<Carta>();
	
	public MazzoDaGioco() {
		Mazzo m1 = new Mazzo();
		Mazzo m2 = new Mazzo();
		Mazzo m3 = new Mazzo();
		Mazzo m4 = new Mazzo();
		
		ArrayList<ArrayList<Carta>> listaMazzi = new ArrayList<>();
        listaMazzi.add(m1.getMazzo());
        listaMazzi.add(m2.getMazzo());
        listaMazzi.add(m3.getMazzo());
        listaMazzi.add(m4.getMazzo());
        unisciMazzi(listaMazzi);
	}
	
	private void unisciMazzi(ArrayList<ArrayList<Carta>> listaMazzi) {
		for(ArrayList<Carta> mazzo : listaMazzi) {
			mazzoGioco.addAll(mazzo);
		}
	}
	
	public void mazzoStampa() {
		System.out.println(mazzoGioco.toString());
	}
	
	public Integer getMazzoGiocoSize() {
		return mazzoGioco.size();
	}
	
	public Carta prossimaCarta() {
		Iterator<Carta> s = mazzoGioco.iterator();
		Carta carta = s.next();
		prossimaRemove();
		carteInGioco.add(carta);
		return carta;
	}
	
	private void prossimaRemove() {
		mazzoGioco.remove(0);
	}
	
	public void scartaCarte() {
		mazzoScarti.addAll(carteInGioco);
		carteInGioco.removeAll(carteInGioco);
	}
	
	public void stampaCarteInGioco() {
		System.out.println(carteInGioco);
	}
	
	// private, per controllare quante carte ci sono negli scarti
	public Integer getSizeScarti() {
		return mazzoScarti.size();
	}
	
}
