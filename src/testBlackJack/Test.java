package testBlackJack;

import controller.GameController;
import model.BlackJackPlayer;
import model.TavoloDaGioco;
import view.Frame;

public class Test {
	
	public static void main(String[] args) {
		// va tolta la creazione del player da qui e gestita in altro modo
		BlackJackPlayer bp1 = new BlackJackPlayer("Emanuele", "avatar");
		
		TavoloDaGioco model = new TavoloDaGioco(bp1);
		Frame view = new Frame();
		new GameController(model, view);
		
		model.startGame();
		view.setVisible(true);
	}
}
