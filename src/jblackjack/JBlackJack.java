package jblackjack;

import controller.GameController;
import model.TavoloDaGioco;
import view.SplashScreenPage;

/**
 * Classe principale del programma che avvia il gioco JBlackJack, creando le istanze necessarie 
 * per il modello, la vista e il controller.
 */
public class JBlackJack {
	
	/**
	 * Metodo main che inizializza il model, il controller e la view e fa iniziare il gioco.
	 * @param args Argomenti della riga di comando
	 */
	public static void main(String[] args) {
		TavoloDaGioco model = TavoloDaGioco.getInstance();
		SplashScreenPage view = new SplashScreenPage();
		GameController controller = GameController.getIstance();
		controller.addModel(model);
	}
}