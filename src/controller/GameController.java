package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observer;

import javax.swing.JFrame;

import model.BlackJackBot;
import model.BlackJackPlayer;
import model.Database;
import model.TavoloDaGioco;
import view.GamePage;
import view.ActionPlayerPanel;
import view.AudioManager;
import view.PlayerPanel;
import view.SplashScreenPage;

public class GameController {
	private TavoloDaGioco model;
	private ActionPlayerPanel actionPlayerPanel;
	private PlayerPanel playerPanel;
	private GamePage gamePage;
	private Database db;
	private AudioManager audioManager;
	private static GameController newControllerIstance;


	public static GameController getIstance() {
		if(newControllerIstance == null ) newControllerIstance = new GameController();
		return newControllerIstance;
	}

	private GameController() {

	}

	public void addModel(TavoloDaGioco model) {
		this.model = model;
		audioManager = AudioManager.getInstance();
		db = Database.getIstance();
	}

	public void addPlayerPanel(PlayerPanel playerPanel) {
		this.playerPanel = playerPanel;
	}

	public void addGamePage(GamePage gamePage) {
		this.gamePage = gamePage;
	}

	public BlackJackPlayer getPlayer() {
		return (BlackJackPlayer) model.getPlayer();
	}

	public BlackJackBot getBanco() {
		return (BlackJackBot) model.getBanco();
	}

	public BlackJackBot getBot1() {
		return (BlackJackBot) model.getBot1();
	}

	public BlackJackBot getBot2() {
		return (BlackJackBot) model.getBot2();
	}

	// Aggiunge il player al tavolo
	public void addPlayer(BlackJackPlayer player) {
		model.addPlayer(player);
	}

	// Aggiunge il player al db
	public void addDbPlayer(BlackJackPlayer player) {
		db.addPlayer(player);
	}

	public BlackJackPlayer getDbPlayerByIndex(int indexPlayer) {
		return db.getPlayerByIndex(indexPlayer);
	}

	public ArrayList<BlackJackPlayer> getDbPlayers() {
		return (ArrayList<BlackJackPlayer>) db.getPlayers();
	}

	public void addBot(String nBot) {
		model.addBot(nBot);
	}	

	@SuppressWarnings("deprecation")
	public void addObserver(Observer o) {
		model.addObserver(o);
	}

	public void deleteObserver(Observer o) {
		model.deleteObserver(o);
	}

	public void addActionPlayerMenu(ActionPlayerPanel actionPlayerPanel) {
		this.actionPlayerPanel = actionPlayerPanel;
		initController();
	}

	// Gestione Tasti ActionPlayerMenu
	private void initController() {
		// Imposta gli ActionListener per i bottoni
		actionPlayerPanel.getCartaButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cartaAction();
			}
		});

		actionPlayerPanel.getStaiButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				staiAction();
			}
		});

		actionPlayerPanel.getDividiButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dividiAction();
			}
		});

		actionPlayerPanel.getRaddoppiaButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				raddoppiaAction();
			}
		});

		actionPlayerPanel.getPassaManoButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				passaManoAction();
			}
		});
	}

	private void cartaAction() {
		System.out.println("model "+model.getNumeroGiocatori());
		audioManager.play("./src/sounds/button.wav");
		model.getCard(model.getPlayer());
		System.out.println("Chiedo carta (controller)");
	}

	private void staiAction() {
		playerPanel.passaTurno(false);
		audioManager.play("./src/sounds/button.wav");
		System.out.println("Passo turno (controller)");
	}

	private void passaManoAction() {
		audioManager.play("./src/sounds/button.wav");
		updateIndexMano();
		playerPanel.updateIndexManoPlayerPanel(false);
		cambioTastiSplit();
		System.out.println("Passa mano (Controller)");
	}


	private void raddoppiaAction() {
		audioManager.play("./src/sounds/button.wav");
		model.getDouble(model.getPlayer());
		System.out.println("Raddoppia (controller)");
	}

	private void dividiAction() {
		audioManager.play("./src/sounds/button.wav");
		model.getSplit(model.getPlayer());
		System.out.println("Dividi (controller)");
	}

	public boolean isCarteUguali(BlackJackPlayer p) {
		return model.carteUguali(p);
	}

	public void passaTurno(int punteggio) {
		model.setPuntiTavolo(punteggio);
		model.playerFinishedTurn();
	}

	public void nuovaPartita() {
		model.resetPartita();
		playerPanel.resetPartita();
		gamePage.resetGame();
		model.getPlayer().resetPartita();
	}

	public void startGame() {
		model.startGame();
	}

	public void updateIndexMano() {
		model.updateIndexMano();
	}

	public void cambioTastiSplit() {
		actionPlayerPanel.cambioButtonSplit();
	}

	public void setPuntiPlayerSplit(Integer puntiMano1, Integer puntiMano2) {
		System.out.println("Set punti (Controller)");
		model.getPlayer().setPuntiPlayerSplit(puntiMano1, puntiMano2);
	}

	public Integer getNumeroGiocatori() {
		return model.getNumeroGiocatori(); 
	}

}