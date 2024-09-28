package testBlackJack;

import controller.GameController;
import model.Database;
import model.TavoloDaGioco;
import view.SplashScreenPage;

public class Test {
	/*
	public static void main(String[] args) {
		// va tolta la creazione del player da qui e gestita in altro modo
		//BlackJackPlayer bp1 = new BlackJackPlayer("Emanuele", "Male");
		//BlackJackPlayer bp2 = new BlackJackPlayer("Jhonatan", "Alien");

		//Database db = Database.getIstance();
		//db.clearDatabase();
		//db.addPlayer(bp1);
		//db.addPlayer(bp2);

		TavoloDaGioco model = TavoloDaGioco.getInstance();
		SplashScreenPage view = new SplashScreenPage();
		GameController controller = new GameController(model, view);
		
		controller.startGame();
	}
	*/
	
	public static void main(String[] args) {
		//Database db = Database.getIstance();
		//db.clearDatabase();
		TavoloDaGioco model = TavoloDaGioco.getInstance();
		SplashScreenPage view = new SplashScreenPage();
		GameController controller = GameController.getIstance();
		controller.addModel(model);
	}
}
