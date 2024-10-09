package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;

import controller.GameController;
import model.BlackJackPlayer;

/**
 * La classe MenuPage rappresenta il pannello di menu principale del gioco di BlackJack.
 * Questa pagina consente al giocatore di:
 * - Selezionare il numero di bot con cui giocare.
 * - Visualizzare le statistiche del giocatore come livello, numero di partite giocate, vinte e perse.
 * Estende JPanel e implementa ActionListener per gestire l'evento del bottone "Play".
 * Inoltre, carica un'immagine di sfondo per il layout del menu.
 */
public class MenuPage extends JPanel implements ActionListener{

	/**
	 *  Frame principale in cui viene visualizzata la pagina del menu. 
	 */
	private Frame frame;

	/**
	 *  Pannello che contiene il pulsante "Play" e le opzioni per il numero di bot.
	 */
	private JPanel leftMenuPanel;
	
	/** 
	 * Pannello che contiene le statistiche del giocatore. 
	 */
	private JPanel rightMenuPanel;
	
	/**
	 *  Immagine di sfondo per la pagina del menu. 
	 */
	private Image backgroundImage;

	/** 
	 * Bordo titolato per separare la sezione del menu. 
	 */
	private TitledBorder border;
	
	/**
	 *  Pulsante per avviare una nuova partita. 
	 */
	private MyButton playButton;
	
	/**
	 *  Etichetta che indica la selezione del numero di bot. 
	 */
	private JLabel botLabel;
	
	/**
	 *  Pulsante di opzione per selezionare un solo bot. 
	 */
	private JRadioButton oneBot;
	
	/**
	 *  Pulsante di opzione per selezionare due bot. 
	 */
	private JRadioButton twoBot;
	
	/**
	 *  Pulsante di opzione per selezionare tre bot. 
	 */
	private JRadioButton threeBot;
	
	/**
	 *  Gruppo di bottoni per la selezione del numero di bot. 
	 */
	private ButtonGroup group;	

	/**
	 *  Etichetta che mostra il nickname del giocatore. 
	 */
	private JLabel nicknameLabel;
	
	/**
	 *  Icona dell'avatar del giocatore. 
	 */
	private ImageIcon avatarIcon;
	
	/**
	 *  Etichetta per visualizzare l'avatar del giocatore. 
	 */
	private JLabel avatarLabel;
	
	/** 
	 * Etichetta che mostra il livello del giocatore. 
	 */
	private JLabel levelLabel;
	
	/**
	 *  Etichetta che mostra il numero di partite giocate dal giocatore. 
	 */
	private JLabel nGames;	
	
	/**
	 *  Etichetta che mostra il numero di partite vinte dal giocatore. 
	 */
	private JLabel nGamesWin;
	
	/**
	 *  Etichetta che mostra il numero di partite perse dal giocatore. 
	 */
	private JLabel nGamesLost;

	/**
	 *  Controller principale per gestire la logica di gioco. 
	 */
	private GameController controller;
	
	/**
	 *  Il giocatore umano le cui informazioni sono visualizzate nel pannello. 
	 */
	private BlackJackPlayer player;	

	/**
	 *  Gestore dell'audio per riprodurre suoni durante l'interazione con l'interfaccia utente. 
	 */
	private AudioManager audioManager;	

	/**
	 * Costruttore che crea la pagina del menu principale. Inizializza il frame, i pannelli, 
	 * le etichette e i bottoni. Aggiunge gli elementi nel layout e configura le interazioni.
	 */
	public MenuPage() {
		frame = new Frame();
		audioManager = AudioManager.getInstance();
		controller = GameController.getIstance();

		player = GameController.getIstance().getPlayer();

		backgroundImage = new ImageIcon("./src/graphics/backgroundGame.png").getImage();

		leftMenuPanel = new JPanel();
		leftMenuPanel.setPreferredSize(new Dimension(500, getHeight()));

		border = BorderFactory.createTitledBorder("Menu");
		border.setTitleJustification(TitledBorder.CENTER);
		leftMenuPanel.setBorder(border);

		playButton = new MyButton("PLAY", this);

		Font labelFont = getFont().deriveFont(Font.BOLD, getFont().getSize() + 10);

		botLabel = new JLabel("Choose how many bots:");
		botLabel.setFont(labelFont);
		oneBot = new JRadioButton(BotNumber.ONE.getText());
		oneBot.setActionCommand(BotNumber.ONE.getValue());
		twoBot = new JRadioButton(BotNumber.TWO.getText());
		twoBot.setActionCommand(BotNumber.TWO.getValue());
		threeBot = new JRadioButton(BotNumber.THREE.getText());
		threeBot.setActionCommand(BotNumber.THREE.getValue());

		group = new ButtonGroup();
		group.add(oneBot);
		group.add(twoBot);
		group.add(threeBot);
		threeBot.setSelected(true);

		leftMenuPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.fill = GridBagConstraints.CENTER;
		gbc.insets = new Insets(10, 10, 10, 10);

		gbc.gridx = 1;
		gbc.gridy = 0;
		leftMenuPanel.add(playButton, gbc);

		gbc.gridx = 1;
		gbc.gridy = 1;
		leftMenuPanel.add(botLabel, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		leftMenuPanel.add(oneBot, gbc);

		gbc.gridx = 1;
		gbc.gridy = 2;
		leftMenuPanel.add(twoBot, gbc);

		gbc.gridx = 2;
		gbc.gridy = 2;
		leftMenuPanel.add(threeBot, gbc);


		rightMenuPanel = new JPanel();
		rightMenuPanel.setPreferredSize(new Dimension(780 ,getHeight()));
		rightMenuPanel.setOpaque(false);

		border = BorderFactory.createTitledBorder("Statistiche");
		border.setTitleJustification(TitledBorder.CENTER); // Centra il titolo sul bordo
		rightMenuPanel.setBorder(border);

		nicknameLabel = new JLabel(player.getNickname());
		nicknameLabel.setFont(labelFont);

		String avatarPath = "./src/graphics/avatar/"+player.getAvatar()+".png"; 
		avatarIcon = new ImageIcon(avatarPath);
		Image avatarImg = avatarIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH); 
		avatarLabel = new JLabel(new ImageIcon(avatarImg));

		levelLabel = new JLabel("Level: "+player.getLivello());
		levelLabel.setFont(labelFont);
		nGames = new JLabel("Player Games: "+player.getNumeroPartite());
		nGames.setFont(labelFont);
		nGamesWin = new JLabel("Win Games: "+player.getNumeroVittorie());
		nGamesWin.setFont(labelFont);
		nGamesLost = new JLabel("Lost Games: "+player.getNumeroSconfitte());
		nGamesLost.setFont(labelFont);

		rightMenuPanel.setLayout(new GridBagLayout());

		gbc.gridx = 0;
		gbc.gridy = 0;
		rightMenuPanel.add(avatarLabel, gbc);

		gbc.gridx = 1;
		gbc.gridy = 0;
		rightMenuPanel.add(nicknameLabel, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 2;
		rightMenuPanel.add(levelLabel, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		rightMenuPanel.add(nGames, gbc);

		gbc.gridx = 0;
		gbc.gridy = 4;
		rightMenuPanel.add(nGamesWin, gbc);

		gbc.gridx = 0;
		gbc.gridy = 5;
		rightMenuPanel.add(nGamesLost, gbc);



		this.setLayout(new BorderLayout());
		this.add(leftMenuPanel, BorderLayout.WEST);
		this.add(rightMenuPanel, BorderLayout.EAST);

		frame.setContentPane(this);
		frame.setVisible(true);
	}

	/**
	 * Metodo chiamato quando viene premuto il pulsante "Play".
	 * Riproduce un suono e avvia una nuova partita aggiungendo il numero di bot selezionati.
	 * @param e L'evento di azione generato dal pulsante "Play".
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == playButton) {
			audioManager.play("./src/sounds/button.wav");
			String nBot = group.getSelection().getActionCommand();
			controller.addBot(nBot);
			frame.dispose();
			GamePage gamePage = new GamePage();
			GameController.getIstance().addGamePage(gamePage);
		}
	}

	/**
	 * Metodo per disegnare l'immagine di sfondo sul pannello.
	 * @param g Il contesto grafico usato per disegnare l'immagine.
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
	}
}