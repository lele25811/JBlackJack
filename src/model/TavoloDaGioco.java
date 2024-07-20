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
	private ArrayList<BlackJackPlayer> giocatori = new ArrayList<BlackJackPlayer>();
	
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
		BlackJackPlayer Franco = new BlackJackPlayer("Franco", "avatarFranco", true);
		BlackJackPlayer Baldassarre = new BlackJackPlayer("Baldassarre", "avatarBaldassare", true);
		BlackJackPlayer Banco = new BlackJackPlayer("Banco", "avatarBanco", true);
		giocatori.addAll(Arrays.asList(Franco, player, Baldassarre, Banco));
	}
	
	public void provaStampa() {
		for(BlackJackPlayer p: giocatori) {
			System.out.println(p.toString());
			System.out.println(p.getMano());
			int[] puntiMano = p.getValoreMano();
			for(int i=0; i<puntiMano.length; i++) {
				System.out.println(puntiMano[i]);
			}
		}
	}
	
	public void startGame() {
		distribuisciCarteIniziali();
		turnazione();
		
	}

	
	private void turnazione() {
		for(BlackJackPlayer p: giocatori) {
			if(p.isBot()) {
				turnoBot(p);
			}
			turnoPlayer();
		}
	}
	
	private void turnoPlayer() {
		// TODO Auto-generated method stub
		
	}

	private void turnoBot(BlackJackPlayer p) {
		int[] valori = p.getValoreMano();
		boolean isCarta = Arrays.stream(valori).max().orElse(0) <= 18;
		for(int i=0; i<valori.length; i++) {
			System.out.println(valori[i]);
		}
		sceltaBot(isCarta, valori);
	}
	
	private int sceltaBot(boolean isCarta, int[] valori) {
		Random random = new Random();
		
		//TODO: controlla Diagramma
		return 0;
	}

	private void distribuisciCarteIniziali() {
		for(int i=0; i<2; i++) {
			for(BlackJackPlayer p: giocatori) {
				p.addCarta(mazzo.prossimaCarta());
			}
		}
	}
	
	
}
