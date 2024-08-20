package controller;

import model.TavoloDaGioco;
import view.Frame;

public class GameController {
	private TavoloDaGioco model;
	private Frame view;
	
	@SuppressWarnings("deprecation")
	public GameController(TavoloDaGioco model, Frame view) {
		this.model = model;
		this.view = view;
		
		model.addObserver(view);
	}
}
