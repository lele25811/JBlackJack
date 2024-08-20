package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;
import java.util.OptionalInt;
import java.util.Random;

/*
 * Classe che modella le regole di gioco, le mani, i punti e la logica
 */
@SuppressWarnings("deprecation")
public class TavoloDaGioco extends Observable{
	
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
		System.out.println("Mandato notify");
	}
	
	public void provaStampa() {
		for(Player p: giocatori) {
			System.out.println(p.toString());
			System.out.println(p.getMano());
			int[] puntiMano = p.getValoreManoIniziale();
			for(int i=0; i<puntiMano.length; i++) {
				System.out.println(puntiMano[i]);
			}
		}
	}
	
	// metodo di inizio gioco
	public void startGame() {
		setChanged();
		notifyObservers(getNumeroGiocatori());
		distribuisciCarteIniziali();
		turnazione();
		
	}

	
	private void turnazione() {
		for(Player p: giocatori) {
			System.out.println();
			if(p instanceof BlackJackBot) {
				//turnoBot(p);
				System.out.println("È un bot, "+p.getNickname());
				provaTurnoBot(p);
			}
			if(p instanceof BlackJackPlayer) {
				System.out.println("È un Player, "+p.getNickname());
				//turnoPlayer();
			}
		}
	}
	
	private int provaTurnoBot(Player p) {
		boolean isBanco = ((BlackJackBot) p).getIsBanco();
		boolean isRaddoppio = false;
		Random random = new Random();
		int valoreAggiornato = 0;
		int[] valori = p.getValoreManoIniziale();
		while(true) {
			
			for(int i=0; i<valori.length; i++) {
				System.out.println(valori[i]);
			}
			if(valoreAggiornato > 21) {
				System.out.println("CAIO###HO SBALLATO CON "+valoreAggiornato+"###");
				return 0;
			}
			// gestione uscita raddoppio
			if(isRaddoppio) {
				System.out.println("Il raddoppio mi ha lasciato:");
				System.out.println("Lung array "+ valori.length);
				System.out.println(valori[0]);
				return valori[0];
				
			}
			if(valori.length > 1 && valori[1] > 21) {
				valori = new int[]{valori[0]};
			}
			if(valori.length > 1) {
				if(valori[1] >= 18) {
					System.out.println("###STO CON "+valori[1] +"###");
					return valori[1];
				}else {
					int sceltaCarta = random.nextInt(2); // scelta valore[0] o [1]
					int sceltaMossa = random.nextInt(2); // scelta mossa (carta o raddoppio)
					if(isBanco) {
						sceltaMossa = 0;
					}
					switch(sceltaMossa) {
						case 0:
							valoreAggiornato = valori[sceltaCarta];
							System.out.println("###CARTA CON "+valori[sceltaCarta] +"###");
							carta(valori[sceltaCarta], p);
							break;
						case 1:
							valoreAggiornato = valori[sceltaCarta];
							System.out.println("###RADDOPPIO CON "+valori[sceltaCarta] +"###");
							isRaddoppio = true;
							raddoppio(valori[sceltaCarta], p);
							break;
						default: carta(valori[0], p);
					}
				}
			//Controllo valori singoli
			}else if(valori.length == 1) {
				if(valori[0] > 21) {
					System.out.println("###HO SBALLATO CON"+valori[0]+"###");
					return 0;
				}
				if(valori[0] >= 18) {
					System.out.println("###STO CON "+valori[0] +"###");
					return valori[0];
				}else {
					int sceltaMossa = random.nextInt(2);
					if(isBanco) {
						sceltaMossa = 0;
					}
					switch(sceltaMossa) {
					case 0:
						valoreAggiornato = valori[0];
						carta(valori[0], p);
						System.out.println("###CARTA CON "+valori[0] +"###");
						break;
					case 1:
						valoreAggiornato = valori[0];
						// TODO: definisci operazioni
						isRaddoppio = true;
						raddoppio(valori[0], p);
						System.out.println("###RADDOPPIO CON "+valori[0] +"###");
						break;
					default: carta(valori[0], p);
					}
				}
			}
			valori = p.getValoreMano(valoreAggiornato);
		}
	}

	private void raddoppio(int valori, Player p) {
		p.addCarta(mazzo.prossimaCarta());
		System.out.println("Mano attuale "+p.getMano());
	}

	private void carta(int valori, Player p) {
		p.addCarta(mazzo.prossimaCarta());
		System.out.println("Mano attuale "+p.getMano());
	}

	private void distribuisciCarteIniziali() {
		for(int i=0; i<2; i++) {
			for(Player p: giocatori) {
				p.addCarta(mazzo.prossimaCarta());
			}
		}
	}

	public int getNumeroGiocatori() {
		return giocatori.size();
	}
	
	
}
