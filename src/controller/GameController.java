package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observer;

import model.BlackJackBot;
import model.BlackJackPlayer;
import model.Database;
import model.TavoloDaGioco;
import view.GamePage;
import view.ActionPlayerPanel;
import view.AudioManager;
import view.PlayerPanel;

/**
 * La classe GameController gestisce l'interazione tra il model e la view del gioco
 * BlackJack. Implementa il pattern Singleton e controlla le azioni del giocatore,
 * gestisce i bot, e coordina gli aggiornamenti tra la vista e il modello.
 */
@SuppressWarnings("deprecation")
public class GameController {

	/**
	 * Istanza della classe TavoloDaGioco.
	 */
	private TavoloDaGioco model;

	/**
	 * Istanza della classe ActionPlayerPanel.
	 */
	private ActionPlayerPanel actionPlayerPanel;

	/**
	 * Istanza della classe PlayerPanel
	 */
	private PlayerPanel playerPanel;

	/**
	 * Istanza della classe gamePage.
	 */
	private GamePage gamePage;

	/**
	 * Unica istanza della classe Database.
	 */
	private Database db;

	/**
	 * Unica istanza della classe AudioManager.
	 */
	private AudioManager audioManager;

	/**
	 * Unica istanza della classe GameController.
	 */
	private static GameController newControllerIstance;

	/**
	 * Metodo che ottiene l'istanza Singleton della classe GameController. 
	 * @return l'istanza di GameController
	 */
	public static GameController getIstance() {
		if(newControllerIstance == null ) newControllerIstance = new GameController();
		return newControllerIstance;
	}

	/**
	 * Costruttore privato della classe
	 */
	private GameController() {
	}

	/**
	 * Aggiunge il TavoloDaGioco al controller, e prende le istanze di Database e AudioManager.
	 * @param tavoloDaGioco l'istanza della classe TavoloDaGioco
	 */
	public void addModel(TavoloDaGioco tavoloDaGioco) {
		this.model = tavoloDaGioco;
		audioManager = AudioManager.getInstance();
		db = Database.getIstance();
	}

	/**
	 * Aggiunge il pannello del giocatore umano (PlayerPanel) al controller.
	 * @param playerPanel il pannello del giocatore umano
	 */
	public void addPlayerPanel(PlayerPanel playerPanel) {
		this.playerPanel = playerPanel;
	}

	/**
	 * Aggiunge la pagina di gioco (GamePanel) al controller.
	 * @param gamePage il pannello che rappresenta la schermata di gioco
	 */
	public void addGamePage(GamePage gamePage) {
		this.gamePage = gamePage;
	}

	/**
	 * Restituisce il giocatore umano.
	 * @return il giocatore umano
	 */
	public BlackJackPlayer getPlayer() {
		return (BlackJackPlayer) model.getPlayer();
	}

	/**
	 * Restituisce il banco (bot).
	 * @return il bot che rappresenta il banco
	 */
	public BlackJackBot getBanco() {
		return (BlackJackBot) model.getBanco();
	}

	/**
	 * Restituisce il primo bot.
	 * @return il bot 1
	 */
	public BlackJackBot getBot1() {
		return (BlackJackBot) model.getBot1();
	}

	/**
	 * Restituisce il secondo bot.
	 * @return il bot 2
	 */
	public BlackJackBot getBot2() {
		return (BlackJackBot) model.getBot2();
	}

	/**
	 * Aggiunge il giocatore umano al tavolo da gioco.
	 * @param player il giocatore umano
	 */
	private void addPlayer(BlackJackPlayer player) {
		model.addPlayer(player);
	}

	/**
	 * Aggiunge un giocatore al database.
	 * @param nickname nome del giocatore
	 * @param avatar avatar del giocatore
	 */
	public void addDbPlayer(String nickname, String avatar) {
		BlackJackPlayer player = new BlackJackPlayer(nickname, avatar);
		db.addPlayer(player);
		addPlayer(player);
	}

	/**
	 * Recupera il giocatore dal database per indice e lo aggiunge al tavolo.
	 * @param indexPlayer l'indice del giocatore
	 */	
	public void getDbPlayerByIndex(int indexPlayer) {
		BlackJackPlayer player = db.getPlayerByIndex(indexPlayer);
		addPlayer(player);
	}

	/**
	 * Restituisce tutti i giocatori dal database.
	 * @return la lista dei giocatori nel database
	 */
	public ArrayList<BlackJackPlayer> getDbPlayers() {
		return (ArrayList<BlackJackPlayer>) db.getPlayers();
	}

	/**
	 * Aggiunge i bot al tavolo da gioco.
	 * @param nBot il nome del bot
	 */
	public void addBot(String nBot) {
		model.addBot(nBot);
	}	

	/**
	 * Aggiunge un observer al modello di gioco.
	 * @param o l'osservatore
	 */
	public void addObserver(Observer o) {
		model.addObserver(o);
	}

	/**
	 * Rimuove un observer dal modello di gioco.
	 * @param o l'osservatore
	 */
	public void deleteObserver(Observer o) {
		model.deleteObserver(o);
	}

	/**
	 * Aggiunge il pannello del menu azioni per il giocatore e inizializza 
	 * la gestione gli eventi dei bottoni.
	 * @param actionPlayerPanel il pannello delle azioni del giocatore
	 */
	public void addActionPlayerMenu(ActionPlayerPanel actionPlayerPanel) {
		this.actionPlayerPanel = actionPlayerPanel;
		initController();
	}

	/**
	 * Inizializza i listener per i bottoni nel pannello delle azioni del giocatore.
	 */
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

	/**
	 * Gestisce l'azione del bottone "Carta" (chiedi una carta).
	 */
	private void cartaAction() {
		audioManager.play("./src/sounds/button.wav");
		model.getCard(model.getPlayer());
	}

	/**
	 * Gestisce l'azione del bottone "Stai" (passa il turno).
	 */
	private void staiAction() {
		audioManager.play("./src/sounds/button.wav");
		playerPanel.passaTurno(false);
	}

	/**
	 * Gestisce l'azione del bottone "Passa Mano" durante uno split.
	 */
	private void passaManoAction() {
		audioManager.play("./src/sounds/button.wav");
		updateIndexMano();
		playerPanel.updateIndexManoPlayerPanel(false);
		cambioTastiSplit();
	}

	/**
	 * Gestisce l'azione del bottone "Raddoppia" (raddoppia la puntata).
	 */
	private void raddoppiaAction() {
		audioManager.play("./src/sounds/button.wav");
		model.getDouble(model.getPlayer());
	}

	/**
	 * Gestisce l'azione del bottone "Dividi" (split delle carte).
	 */
	private void dividiAction() {
		audioManager.play("./src/sounds/button.wav");
		model.getSplit(model.getPlayer());
	}

	/**
	 * Verifica se le carte iniziali del giocatore sono uguali, per permettere lo split.
	 * @param p il giocatore umano
	 * @return true se le carte sono uguali, false altrimenti
	 */
	public boolean isCarteUguali(BlackJackPlayer p) {
		return model.carteUguali(p);
	}

	/**
	 * Gestisce il passaggio del turno e aggiorna il punteggio.
	 * @param punteggio il punteggio del giocatore
	 */
	public void passaTurno(int punteggio) {
		model.setPuntiTavolo(punteggio);
		model.playerFinishedTurn();
	}

	/**
	 * Inizia una nuova partita, resettando i dati di gioco.
	 */
	public void nuovaPartita() {
		model.resetPartita();
		playerPanel.resetPartita();
		gamePage.resetGame();
		model.getPlayer().resetPartita();
	}

	/**
	 * Avvia il gioco chiamando il metodo startGame del modello.
	 */
	public void startGame() {
		model.startGame();
	}

	/**
	 * Aggiorna l'indice della mano attiva durante uno split.
	 */
	public void updateIndexMano() {
		model.updateIndexMano();
	}

	/**
	 * Aggiorna i bottoni nella GUI per gestire il gioco durante uno split.
	 */
	public void cambioTastiSplit() {
		actionPlayerPanel.cambioButtonSplit();
	}

	/**
	 * Metodo che setta i punti delle due mani
	 * @param puntiMano1 punti nella mano 1
	 * @param puntiMano2 punti nella mano 2
	 */
	public void setPuntiPlayerSplit(Integer puntiMano1, Integer puntiMano2) {
		model.getPlayer().setPuntiPlayerSplit(puntiMano1, puntiMano2);
	}

	/**
	 * Metodo che ritorna il numero di giocatori al tavolo.
	 * @return numero di giocatori al tavolo
	 */
	public Integer getNumeroGiocatori() {
		return model.getNumeroGiocatori(); 
	}
}