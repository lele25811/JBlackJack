package view;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;

import controller.GameController;
import model.BlackJackPlayer;
import model.Player;

/**
 * La classe LoginPage rappresenta la schermata di login del gioco 
 * BlackJack. Permette agli utenti di aggiungere un nuovo giocatore oppure di 
 * caricare un giocatore esistente. La schermata include un'interfaccia grafica 
 * con la possibilità di scegliere un nickname e un avatar.
 * 
 * Questa classe estende JPanel e implementa 
 * ActionListener per gestire le azioni degli utenti.
 */
public class LoginPage extends JPanel implements ActionListener{

	/** 
	 * Frame principale in cui viene visualizzata la pagina del menu. 
	 */
	private Frame frame;

	/** 
	 * Immagine di background. 
	 */
	private Image backgroundImage;	

	/** 
	 * Panel dove avviene la registrazione. 
	 */
	private JPanel newPlayer;

	/** 
	 * MyButton per effettuare la registrazione. 
	 */
	private MyButton buttonNewPlayer;

	/** 
	 * Etichetta nickname della registrazione 
	 */
	private JLabel nicknameLabel;

	/** 
	 * Campo di testo per inserire il nickname del giocatore.
	 */
	private JTextField nicknameTextField;

	/** 
	 *del avatar 1.
	 */
	private JLabel avatar1ImageLabel;	

	/**
	 *  Bottone scelta per l'avatar 1. 
	 */
	private JRadioButton avatar1Button;

	/** 
	 * Etichetta del avatar 2. 
	 */
	private JLabel avatar2ImageLabel;

	/**
	 *  Bottone scelta per l'avatar 2.
	 *   */
	private JRadioButton avatar2Button;

	/** 
	 * Etichetta del avatar 3. 
	 */
	private JLabel avatar3ImageLabel;

	/** 
	 * Bottone scelta per l'avatar 3.
	 */
	private JRadioButton avatar3Button;	

	/**
	 *  Etichetta del avatar 4.
	 */
	private JLabel avatar4ImageLabel;

	/** 
	 * Bottone scelta per l'avatar 4.
	 */
	private JRadioButton avatar4Button;	

	/** 
	 * Gruppo dei bottoni per la scelta del avatar. 
	 */
	private ButtonGroup group;

	/** 
	 * Panel del login. 
	 */
	private JPanel loadPlayer;

	/**
	 *  Bottone del login. 
	 */
	private MyButton buttonLoadPlayer;

	/**
	 *  Lista dei giocatori già registrati precedentemente.
	 */
	private JList<String> listaGiocatori;

	/** 
	 * Controller principale per gestire la logica di gioco.
	 */
	private GameController controller;

	/**
	 *  Gestore dell'audio per riprodurre suoni durante l'interazione con l'interfaccia utente. 
	 */
	private AudioManager audioManager;

	/**
	 * Costruttore della classe LoginPage. Inizializza i componenti 
	 * dell'interfaccia utente, inclusi i pannelli per la registrazione di un 
	 * nuovo giocatore e il caricamento di un giocatore esistente. Carica anche 
	 * l'immagine di sfondo per la schermata di accesso.
	 */
	public LoginPage() {
		frame = new Frame();
		audioManager = AudioManager.getInstance();
		controller = GameController.getIstance();
		setLayout(new BorderLayout());

		ImageIcon icon = new ImageIcon("./src/graphics/backgroundGame.png");
		backgroundImage = icon.getImage();

		newPlayer = new JPanel();
		newPlayer.setPreferredSize(new Dimension(640, 360));
		newPlayer.setBackground(new Color(0, 0, 0, 0));
		newPlayer.setOpaque(false);
		buttonNewPlayer = new MyButton("Add Player", this);
		nicknameLabel = new JLabel("Nickname:");
		nicknameLabel.setFont(nicknameLabel.getFont().deriveFont(18f));
		nicknameTextField = new JTextField(30);
		nicknameTextField.setFont(nicknameTextField.getFont().deriveFont(18f));

		avatar1Button = new JRadioButton("Male");
		avatar1Button.setActionCommand("Male");
		avatar2Button = new JRadioButton("Female");
		avatar2Button.setActionCommand("Female");
		avatar3Button = new JRadioButton("Alien");
		avatar3Button.setActionCommand("Alien");
		avatar4Button = new JRadioButton("Dog");
		avatar4Button.setActionCommand("Dog");

		group = new ButtonGroup();
		group.add(avatar1Button);
		group.add(avatar2Button);
		group.add(avatar3Button);
		group.add(avatar4Button);

		ImageIcon avatar1ImageIcon = new ImageIcon("./src/graphics/avatar/Male.png"); 
		Image image = avatar1ImageIcon.getImage();
		avatar1ImageIcon = new ImageIcon(image.getScaledInstance(100, 100, Image.SCALE_SMOOTH));
		avatar1ImageLabel = new JLabel(avatar1ImageIcon);

		ImageIcon avatar2ImageIcon = new ImageIcon("./src/graphics/avatar/Female.png"); 
		image = avatar2ImageIcon.getImage();
		avatar2ImageIcon = new ImageIcon(image.getScaledInstance(100, 100, Image.SCALE_SMOOTH));
		avatar2ImageLabel = new JLabel(avatar2ImageIcon);

		ImageIcon avatar3ImageIcon = new ImageIcon("./src/graphics/avatar/Alien.png"); 
		image = avatar3ImageIcon.getImage();
		avatar3ImageIcon = new ImageIcon(image.getScaledInstance(100, 100, Image.SCALE_SMOOTH));
		avatar3ImageLabel = new JLabel(avatar3ImageIcon);

		ImageIcon avatar4ImageIcon = new ImageIcon("./src/graphics/avatar/Dog.png"); 
		image = avatar4ImageIcon.getImage();
		avatar4ImageIcon = new ImageIcon(image.getScaledInstance(100, 100, Image.SCALE_SMOOTH));
		avatar4ImageLabel = new JLabel(avatar4ImageIcon);

		TitledBorder border = BorderFactory.createTitledBorder("Add Player");
		border.setTitleJustification(TitledBorder.CENTER);
		newPlayer.setBorder(border);

		newPlayer.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.NORTH;
		gbc.fill = GridBagConstraints.HORIZONTAL;

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 0.1;
		gbc.weighty = 0.1;
		newPlayer.add(nicknameLabel, gbc);

		gbc.gridx = 1; 
		gbc.gridy = 0;
		newPlayer.add(nicknameTextField, gbc);

		gbc.gridx = 0; 
		gbc.gridy = 1;
		newPlayer.add(avatar1Button, gbc);

		gbc.gridx = 1; 
		gbc.gridy = 1;
		newPlayer.add(avatar1ImageLabel, gbc);

		gbc.gridx = 0; 
		gbc.gridy = 2;
		newPlayer.add(avatar2Button, gbc);

		gbc.gridx = 1; 
		gbc.gridy = 2;
		newPlayer.add(avatar2ImageLabel, gbc);

		gbc.gridx = 0; 
		gbc.gridy = 3;
		newPlayer.add(avatar3Button, gbc);

		gbc.gridx = 1; 
		gbc.gridy = 3;
		newPlayer.add(avatar3ImageLabel, gbc);

		gbc.gridx = 0; 
		gbc.gridy = 4;
		newPlayer.add(avatar4Button, gbc);

		gbc.gridx = 1; 
		gbc.gridy = 4;
		newPlayer.add(avatar4ImageLabel, gbc);

		gbc.anchor = GridBagConstraints.SOUTH;
		gbc.gridx = 0; 
		gbc.gridy = 10;
		gbc.gridwidth = 2;
		newPlayer.add(buttonNewPlayer, gbc);

		loadPlayer = new JPanel();
		loadPlayer.setPreferredSize(new Dimension(640, 360));
		TitledBorder borderLoadPlayer = BorderFactory.createTitledBorder("Load Player");
		borderLoadPlayer.setTitleJustification(TitledBorder.CENTER);
		loadPlayer.setBorder(borderLoadPlayer);
		loadPlayer.setBackground(new Color(120, 0, 0, 0));
		loadPlayer.setOpaque(false);

		buttonLoadPlayer = new MyButton("load Player", this);
		loadPlayer.setLayout(new BorderLayout());
		loadPlayer.add(buttonLoadPlayer, BorderLayout.SOUTH);

		List<BlackJackPlayer> giocatori = controller.getDbPlayers();
		String[] playersName = giocatori.stream().map(Player::getNickname).toArray(String[]::new);
		String[] avatarName = giocatori.stream().map(Player::getAvatar).toArray(String[]::new);
		String[] playersNameAvatar = new String[playersName.length];
		for(int i=0; i<playersName.length; i++) {
			playersNameAvatar[i] = "Nickname: "+ playersName[i] + ", Avatar: " + avatarName[i];
		}

		listaGiocatori = new JList<String>(playersNameAvatar);
		listaGiocatori.setBackground(new Color(120, 0, 0, 0));
		listaGiocatori.setOpaque(false);
		Font font = listaGiocatori.getFont();
		Font newFont = font.deriveFont(font.getSize() + 10f);
		listaGiocatori.setFont(newFont);
		listaGiocatori.setFixedCellHeight(25); 

		listaGiocatori.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		loadPlayer.add(listaGiocatori, BorderLayout.CENTER);

		JScrollPane scrollPane = new JScrollPane(listaGiocatori);
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		loadPlayer.add(scrollPane, BorderLayout.CENTER);

		frame.setLayout(new BorderLayout());
		this.add(newPlayer, BorderLayout.WEST);
		this.add(loadPlayer, BorderLayout.EAST);

		frame.setContentPane(this);
		frame.setVisible(true);
	}

	/**
	 * Disegna l'immagine di sfondo della schermata di accesso.
	 * @param g L'oggetto Graphics utilizzato per il disegno.
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(backgroundImage, 0, 0, 1280, 720, this);
	}

	/**
	 * Gestisce gli eventi generati dai pulsanti dell'interfaccia. 
	 * A seconda del pulsante premuto, registra un nuovo giocatore o 
	 * carica un giocatore esistente. 
	 * Eccezione gestita all'interno del blocco try-catch:
	 * throws LoginException se la registrazione non è completa o nessun profilo è selezionato.
	 * @param e L'evento dell'azione generato.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			if(e.getSource() == buttonNewPlayer) {
				audioManager.play("./src/sounds/button.wav");
				String nickname = nicknameTextField.getText();
				ButtonModel selectedModel = group.getSelection();
				if(nickname.isEmpty() || selectedModel == null) {
					throw new LoginException("Registrazione non completa");
				}
				String avatar = selectedModel.getActionCommand();
				controller.addDbPlayer(nickname, avatar);
				caricaMenu();
			}else if(e.getSource() == buttonLoadPlayer) {
				audioManager.play("./src/sounds/button.wav");
				int indexPlayer = listaGiocatori.getSelectedIndex();
				if(indexPlayer == -1) {
					throw new LoginException("Nessuno profilo selezionato");
				}else {
					controller.getDbPlayerByIndex(indexPlayer);
					caricaMenu();
				}
			}
		}catch (LoginException ex) {
			MyPopup myPopup = new MyPopup("Errore", ex.getMessage());
			myPopup.showMessage();
		}
	}

	/**
	 * Carica il menu principale del gioco dopo la registrazione o il caricamento 
	 * di un giocatore. Chiude la schermata di accesso attuale.
	 */
	private void caricaMenu() {
		frame.dispose();
		new MenuPage();
	}
}