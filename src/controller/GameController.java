package controller;

import model.TavoloDaGioco;
import view.SplashScreenPage;

public class GameController {
	private TavoloDaGioco model;
	private SplashScreenPage view;
		
	@SuppressWarnings("deprecation")
	public GameController(TavoloDaGioco model, SplashScreenPage view) {
		this.model = model;
		this.view = view;
	}

	public void startGame() {
		model.startGame();
	}
	
	
	
}
