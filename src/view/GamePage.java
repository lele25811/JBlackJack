package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import controller.GameController;
import model.BlackJackBot;
import model.BlackJackPlayer;

/**
 * La classe GamePage rappresenta la pagina principale del gioco di BlackJack. 
 * È un pannello che contiene i pannelli per i giocatori e l'interfaccia di controllo 
 * per il giocatore umano. 
 * Organizza i giocatori e gestisce la visualizzazione del gioco.
 */
public class GamePage extends JPanel{

	private Frame frame; /** Il frame principale della finestra di gioco. */
	private Image backgroudImage;	/** L'immagine di sfondo del gioco. */

	private Integer numeroGiocatori;	/** Il numero di giocatori nel gioco. */
	private BotPanel bancoPanel;    /** Pannello che rappresenta il banco nel gioco. */
	private BotPanel bot1Panel;    /** Pannello che rappresenta il primo bot (se presente). */
	private PlayerPanel playerPanel;    /** Pannello che rappresenta il giocatore umano. */
	private BotPanel bot2Panel;    /** Pannello che rappresenta il secondo bot (se presente). */
	private JPanel gamePanel;    /** Il pannello principale del gioco che contiene i pannelli dei giocatori. */
	private ActionPlayerPanel actionPlayerMenu;    /** Il pannello contenente i controlli per il giocatore umano. */
	private GameController controller;    /** Il controller che gestisce la logica di gioco. */

	/**
	 * Costruttore della classe GamePage. Inizializza il frame, carica l'immagine di sfondo, 
	 * imposta il layout della pagina e aggiunge i pannelli dei giocatori.
	 */
	public GamePage() {
		controller = GameController.getIstance();
		numeroGiocatori = controller.getNumeroGiocatori();
		revalidate();
		repaint();

		frame = new Frame();

		ImageIcon icon = new ImageIcon("./src/graphics/backgroundGame.png");
		backgroudImage = icon.getImage();

		this.setLayout(new BorderLayout());
		addPanelsPlayers();

		controller.startGame();

		frame.setContentPane(this);
		frame.setVisible(true);
	}

	/**
	 * Metodo privato che aggiunge i pannelli dei giocatori in base al numero di giocatori. 
	 * Supporta le configurazioni per 2, 3 o 4 giocatori, posizionando il banco, i bot e 
	 * il giocatore umano in diversi layout.
	 */
	private void addPanelsPlayers() {
		if(numeroGiocatori == 2) {
			bancoPanel = new BotPanel("Banco", (BlackJackBot) controller.getBanco());
			playerPanel = new PlayerPanel("Player", (BlackJackPlayer) controller.getPlayer());

			gamePanel = new JPanel(new GridLayout(2, 1));
			gamePanel.setBackground(new Color(120, 0, 0, 0));
			gamePanel.setOpaque(false);

			gamePanel.add(bancoPanel);
			gamePanel.add(playerPanel); 

		}else if (numeroGiocatori == 3) {			
			bancoPanel = new BotPanel("Banco", (BlackJackBot) controller.getBanco());
			bot1Panel = new BotPanel("Bot1", (BlackJackBot) controller.getBot1());
			playerPanel = new PlayerPanel("Player", (BlackJackPlayer) controller.getPlayer());

			gamePanel = new JPanel(new GridBagLayout()); 
			gamePanel.setBackground(new Color(120, 0, 0, 0));
			gamePanel.setOpaque(false);

			GridBagConstraints gbc = new GridBagConstraints();
			gbc.fill = GridBagConstraints.BOTH; 
			gbc.insets = new Insets(5, 5, 5, 5); 

			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.weightx = 0.5; 
			gbc.weighty = 0.5; 
			gamePanel.add(bancoPanel, gbc);

			gbc.gridx = 1;
			gbc.gridy = 0;
			gamePanel.add(bot1Panel, gbc);

			gbc.gridx = 0;
			gbc.gridy = 1;
			gbc.gridwidth = 2; 
			gbc.weightx = 1.0; 
			gbc.weighty = 0.5; 
			gamePanel.add(playerPanel, gbc);

		}else if(numeroGiocatori == 4) {
			bancoPanel = new BotPanel("Banco", (BlackJackBot) controller.getBanco());
			bot1Panel = new BotPanel("Bot1", (BlackJackBot) controller.getBot1());
			playerPanel = new PlayerPanel("Player", (BlackJackPlayer) controller.getPlayer());
			bot2Panel = new BotPanel("Bot2", (BlackJackBot) controller.getBot2());

			gamePanel = new JPanel(new GridLayout(2, 2)); 
			gamePanel.setBackground(new Color(120, 0, 0, 0));
			gamePanel.setOpaque(false);

			gamePanel.add(bancoPanel);
			gamePanel.add(bot1Panel);
			gamePanel.add(playerPanel);
			gamePanel.add(bot2Panel);

		}
		actionPlayerMenu = new ActionPlayerPanel(frame);
		playerPanel.addActionPlayer(actionPlayerMenu);

		this.add(gamePanel, BorderLayout.CENTER);
		this.add(actionPlayerMenu, BorderLayout.PAGE_END);

		controller.addActionPlayerMenu(actionPlayerMenu);
		controller.addPlayerPanel(playerPanel);

		controller.addObserver(bancoPanel);
		if (bot1Panel != null) controller.addObserver(bot1Panel);
		controller.addObserver(playerPanel);
		if (bot2Panel != null) controller.addObserver(bot2Panel);
		controller.addObserver(actionPlayerMenu);
	}

	/**
	 * Metodo per resettare la partita. Rimuove gli osservatori collegati ai pannelli del 
	 * banco, dei bot, del giocatore e del pannello delle azioni.
	 */
	public void resetGame() {
		controller.deleteObserver(bancoPanel);
		if(bot1Panel != null) {
			controller.deleteObserver(bot1Panel);
		}
		controller.deleteObserver(playerPanel);
		if(bot2Panel != null) {
			controller.deleteObserver(bot2Panel);
		}
		controller.deleteObserver(actionPlayerMenu);  
	}

	/**
	 * Metodo per disegnare l'immagine di sfondo sul pannello.
	 * @param g Il contesto grafico usato per disegnare l'immagine.
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(backgroudImage, 0, 0, 1280, 720, this);
	}
}