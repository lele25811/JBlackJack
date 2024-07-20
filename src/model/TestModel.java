package model;

import java.util.ArrayList;
import java.util.List;

public class TestModel {
	public static void main(String[] args) {
		BlackJackPlayer bp1 = new BlackJackPlayer("Emanuele", "avatar", false);
		Database bd = Database.getIstance();
		//bd.clearDatabase();
		//bd.addPlayer(bp1);
		//List<BlackJackPlayer> players = new ArrayList<BlackJackPlayer>();
		//players = bd.loadPlayer();
		//System.out.println(players);
		
		TavoloDaGioco tdg = new TavoloDaGioco(bp1);
		tdg.startGame();
		tdg.provaStampa();
	}
}