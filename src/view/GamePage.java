package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import controller.GameController;
import model.BlackJackBot;
import model.BlackJackPlayer;
import model.TavoloDaGioco;

public class GamePage extends JPanel{
	
	private Frame frame;
	private Image backgroudImage;
		
	private Integer numeroGiocatori;
	private BotPanel bancoPanel;
	private BotPanel bot1Panel;
	private PlayerPanel playerPanel;
	private BotPanel bot2Panel;
	private JPanel gamePanel;
	//private ActionPlayerPanel actionPlayerMenu;
	private ActionPlayerPanel actionPlayerMenu;
	private GameController controller;
	
	@SuppressWarnings("deprecation")
	public GamePage() {
		controller = GameController.getIstance();
		numeroGiocatori = controller.getNumeroGiocatori();
		System.out.println("Ci sono "+numeroGiocatori+" giocatori");
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
	
	private void addPanelsPlayers() {
		if(numeroGiocatori == 2) {
			bancoPanel = new BotPanel("Banco", (BlackJackBot) controller.getBanco());
			playerPanel = new PlayerPanel("Player", (BlackJackPlayer) controller.getPlayer());

			gamePanel = new JPanel(new GridLayout(2, 1)); // 2 righe, 1 colonna
			gamePanel.setBackground(new Color(120, 0, 0, 0));
			gamePanel.setOpaque(false);

			gamePanel.add(bancoPanel); // Banco sopra
			gamePanel.add(playerPanel); // Player sotto
			
		}else if (numeroGiocatori == 3) {
			/*
			bancoPanel = new BotPanel("Banco", (BlackJackBot) controller.getBanco());
			bot1Panel = new BotPanel("Bot1", (BlackJackBot) controller.getBot1());
			playerPanel = new PlayerPanel("Player", (BlackJackPlayer) controller.getPlayer());

			gamePanel = new JPanel(new GridLayout(2, 2)); // 2 righe, 2 colonne
			gamePanel.setBackground(new Color(120, 0, 0, 0));
			gamePanel.setOpaque(false);

			gamePanel.add(bancoPanel);  // Banco sopra a sinistra
			gamePanel.add(bot1Panel);   // Bot1 sopra a destra
			gamePanel.add(playerPanel); // Player sotto
			*/
			
			bancoPanel = new BotPanel("Banco", (BlackJackBot) controller.getBanco());
			bot1Panel = new BotPanel("Bot1", (BlackJackBot) controller.getBot1());
			playerPanel = new PlayerPanel("Player", (BlackJackPlayer) controller.getPlayer());

			gamePanel = new JPanel(new GridBagLayout()); // Usa GridBagLayout per avere più controllo
			gamePanel.setBackground(new Color(120, 0, 0, 0));
			gamePanel.setOpaque(false);

			GridBagConstraints gbc = new GridBagConstraints();
			gbc.fill = GridBagConstraints.BOTH; // Riempie l'intera area della cella
			gbc.insets = new Insets(5, 5, 5, 5); // Imposta dei margini tra i componenti

			// Banco sopra a sinistra (prima riga, prima colonna)
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.weightx = 0.5; // Bilancia lo spazio orizzontale tra il banco e il bot
			gbc.weighty = 0.5; // Bilancia lo spazio verticale
			gamePanel.add(bancoPanel, gbc);

			// Bot1 sopra a destra (prima riga, seconda colonna)
			gbc.gridx = 1;
			gbc.gridy = 0;
			gamePanel.add(bot1Panel, gbc);

			// Player sotto che occupa tutta la larghezza (seconda riga, entrambe le colonne)
			gbc.gridx = 0;
			gbc.gridy = 1;
			gbc.gridwidth = 2; // Occupa due colonne
			gbc.weightx = 1.0; // Il playerPanel occupa l'intera larghezza
			gbc.weighty = 0.5; // Stessa altezza delle altre celle
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

	@SuppressWarnings("deprecation")
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
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(backgroudImage, 0, 0, 1280, 720, this);
	}
}
