package model;

import java.util.ArrayList;
import java.util.List;

public class TestModel {
	public static void main(String[] args) {
		MazzoDaGioco m = new MazzoDaGioco();
		m.mazzoStampa();
		System.out.println("MazzoGioco size " + m.getMazzoGiocoSize());
		
		BlackJackPlayer bp1 = new BlackJackPlayer("Emanuele", "avatar");
		BlackJackPlayer bp2 = new BlackJackPlayer("Mario", "avatar1");
		System.out.println(" ");
		
		Database bd = new Database();
		bd.addPlayer(bp1);
		
		List<BlackJackPlayer> players;
		players = bd.loadPlayer();
		bd.stampaPlayers();
		for(BlackJackPlayer p : players) {
			System.out.println("Giocatore: "+ p.toString());
		}
		
		//Controlla perchè non stampa emanuele (bp1)
		//Credo perchè quando faccio il save sovrascriva i precendenti mantenendo la cardinalità
		//Controlla se ogni volta che faccio write cosa succede
		
	}
}