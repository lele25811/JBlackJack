package testBlackJack;

import controller.GameController;
import model.BlackJackPlayer;
import model.Database;
import model.TavoloDaGioco;
import view.Frame;

public class Test {
	
	public static void main(String[] args) {
		// va tolta la creazione del player da qui e gestita in altro modo
		BlackJackPlayer bp1 = new BlackJackPlayer("Emanuele", "Male");
		BlackJackPlayer bp2 = new BlackJackPlayer("Jhonatan", "Alien");

		//Database db = Database.getIstance();
		//db.clearDatabase();
		//db.addPlayer(bp1);
		//db.addPlayer(bp2);
		
		
		/* Modifica il tavolo da gioco (il giocatore deve essere aggiunto dopo con un metodo) 
		 * -> login (carica nel db)
		 * -> menuView
		 * -> quando clicca "play" carica il tavolo da gioco().addPlayer();
		 * DA MODIFICARE L'ENTRY POINT DEL MODEL???
		 * 
		 */
		
		TavoloDaGioco model = new TavoloDaGioco(bp1);
		Frame view = new Frame();
		new GameController(model, view);
		
		model.startGame();
		view.setVisible(true);
	}
}
