package model;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/*
 * La classe MazzoDaGioco definisce le regole di funzionamento del mazzo da gioco
 */
public class MazzoDaGioco {
	
	/*
	 * L'ArrayList mazzoGioco definisce le carte (208, 4 mazzi normali), le carte che utilizza il mazziere
	 */
	ArrayList<Carta> mazzoGioco = new ArrayList<Carta>();
	/*
	 * l'ArrayList carteInGioco definisce le carte che sono sul tavolo al momento, quindi in gioco
	 */
	ArrayList<Carta> carteInGioco = new ArrayList<Carta>();
	/*
	 * l'ArrayList mazzoScarti definisce le carte che sono scartate, quindi già usate nella mani precedenti
	 */
	ArrayList<Carta> mazzoScarti = new ArrayList<Carta>();
	
	/*
	 * Il Costruttore di MazzoDaGioco crea 4 mazzi distinti e li uscite nel ArrayList MazzoGioco, e li mischia
	 */
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
        Collections.shuffle(mazzoGioco);
	}
	
	/*
	 * Il Meotodo unisciMazzi serve al costruttore per costruire la mazzoGioco
	 */
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
	
	/*
	 * Il metodo prossimaCarta ritorna la prima carta del mazzo grazie al iteratore di nella classe Carta
	 */
	public Carta prossimaCarta() {
		Iterator<Carta> s = mazzoGioco.iterator();
		Carta carta = s.next();
		prossimaRemove();
		carteInGioco.add(carta);
		return carta;
	}
	
	/*
	 * Il metodo prossimaRemove rimuove la carta dal mazzoGioco
	 */
	private void prossimaRemove() {
		mazzoGioco.remove(0);
	}
	
	/*
	 * Il Metodo scartaCarte mette nel mazzoScarti le carte che erano in gioco e pulisce l'ArrayList di carteInGioco per la prossima mano
	 */
	public void scartaCarte() {
		mazzoScarti.addAll(carteInGioco);
		carteInGioco.removeAll(carteInGioco);
	}
	
	public void stampaCarteInGioco() {
		System.out.println(carteInGioco);
	}
	
	// private, per controllare quante carte ci sono negli scarti
	private Integer getSizeScarti() {
		return mazzoScarti.size();
	}
	
	/*
	 * Il Metodo ricaricaMazzo controlla quante carte ci sono nel mazzoScarti e se sono la metà dei mazzi di gioco 
	 * allora mischia le carte degli scarti e ricarica MazzoGioco, svuotando poi l'ArrayList mazzoScarti 
	 */
	public void ricaricaMazzo() {
		if (getSizeScarti() > 103) {
			Collections.shuffle(mazzoScarti);
			mazzoGioco.addAll(mazzoScarti);
			mazzoScarti.removeAll(mazzoScarti);
		}
	}
}
