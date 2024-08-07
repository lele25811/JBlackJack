package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.OptionalInt;
import java.util.Random;

/*
 * Classe che modella le regole di gioco, le mani, i punti e la logica
 */
public class TavoloDaGioco {
	// punteggio per mano, [player,...,mazziere]
	private int[] punti;
	private MazzoDaGioco mazzo;
	private ArrayList<Player> giocatori = new ArrayList<Player>();
	
	public TavoloDaGioco(BlackJackPlayer player) {
		addBot(player);
		addPunti(giocatori.size());
		mazzo = new MazzoDaGioco();
	}
	
	private void addPunti(int size) {
		punti = new int[size];
		for(int i = 0; i<size; i++) {
			punti[0] = 0;
		}
	}

	// Da modificare per dare la possibilità di scegliere quanti avversari
	public void addBot(BlackJackPlayer player) {
		BlackJackBot Franco = new BlackJackBot("Franco", "avatarFranco", false);
		BlackJackBot Baldassarre = new BlackJackBot("Baldassarre", "avatarBaldassare", false);
		BlackJackBot Banco = new BlackJackBot("Banco", "avatarBanco", true);
		giocatori.addAll(Arrays.asList(Franco, player, Baldassarre, Banco));
	}
	
	public void provaStampa() {
		for(Player p: giocatori) {
			System.out.println(p.toString());
			System.out.println(p.getMano());
			int[] puntiMano = p.getValoreMano();
			for(int i=0; i<puntiMano.length; i++) {
				System.out.println(puntiMano[i]);
			}
		}
	}
	
	// metodo di inizio gioco
	public void startGame() {
		distribuisciCarteIniziali();
		turnazione();
		
	}

	
	private void turnazione() {
		for(Player p: giocatori) {
			if(p instanceof BlackJackBot) {
				turnoBot(p);
				System.out.println("È un bot, "+p.getNickname());
			}
			if(p instanceof BlackJackPlayer) {
				System.out.println("È un Player, "+p.getNickname());
				//turnoPlayer();
			}
		}
	}

	private void turnoBot(Player p) {
		boolean isBanco = ((BlackJackBot) p).getIsBanco();
		int[] valori = p.getValoreMano();
		boolean isCarta = Arrays.stream(valori).max().orElse(0) <= 18;
		System.out.println("isCarta:"+isCarta);
		System.out.println("Valori mano:");
		for(int i=0; i<valori.length; i++) {
			System.out.println(valori[i]);
		}
		sceltaBot(isCarta, valori, isBanco);
	}
	
	private void sceltaBot(boolean isCarta, int[] valori, boolean isBanco) {
		Random random = new Random();
		if(valori.length > 1) { // && carte diverse
			if(!isCarta) {
				stai(valori[1]);
			}else {
				int sceltaCarta = random.nextInt(2); // scelta valore[0] o [1]
				int sceltaMossa = random.nextInt(2); // scelta mossa (carta o raddoppio)
				if(isBanco) {
					sceltaMossa = 0;
				}
				switch(sceltaMossa) {
					case 0:
						carta(valori[sceltaCarta]);
						break;
					case 1:
						raddoppio(valori[sceltaCarta]);
						break;
					default: carta(valori[0]);
				}
			}
		}
		if(valori.length == 1) {
			if(!isCarta) {
				stai(valori[0]);
			}else{
				int sceltaMossa = random.nextInt(2); // scelta mossa (carta o raddoppio)
				if(isBanco) {
					sceltaMossa = 0;
				}
				switch(sceltaMossa) {
					case 0:
						carta(valori[0]);
						break;
					case 1:
						raddoppio(valori[0]);
						break;
					default: carta(valori[0]);
				}
			}
		}
	}

	private void raddoppio(int valori) {
		System.out.println("Raddoppio con "+valori);
	}

	private void carta(int valori) {
		System.out.println("Richiedo carta con "+valori);
	}

	private int stai(int punti) {
		System.out.println("Sto con "+punti);
		return punti;
		
	}

	private void distribuisciCarteIniziali() {
		for(int i=0; i<2; i++) {
			for(Player p: giocatori) {
				p.addCarta(mazzo.prossimaCarta());
			}
		}
	}
	
	
}
