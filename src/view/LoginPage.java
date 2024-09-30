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
import javax.swing.JButton;
import javax.swing.JFrame;
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
import model.Database;
import model.Player;
import model.TavoloDaGioco;

public class LoginPage extends JPanel implements ActionListener{
	private Frame frame;
	
	private Image backgroudImage;
	
	// Register
	private JPanel newPlayer;
	private MyButton buttonNewPlayer;
	private JLabel nicknameLabel;
	private JTextField nicknameTextField;
	// Avatar 
	private JLabel avatar1ImageLabel;
	private JRadioButton avatar1Button;
	private JLabel avatar2ImageLabel;
	private JRadioButton avatar2Button;
	private JLabel avatar3ImageLabel;
	private JRadioButton avatar3Button;
	private JLabel avatar4ImageLabel;
	private JRadioButton avatar4Button;
	private ButtonGroup group;
	// Login
	private JPanel loadPlayer;
	private MyButton buttonLoadPlayer;
	private JList<String> listaGiocatori;
	
	private BlackJackPlayer player;
	private GameController controller;
	private AudioManager audioManager;
	
	
	public LoginPage() {
		frame = new Frame();
		audioManager = AudioManager.getInstance();
		controller = GameController.getIstance();
		setLayout(new BorderLayout());
			
		//Carica l'immagine dal percorso specificato
		ImageIcon icon = new ImageIcon("./src/graphics/backgroundGame.png");
		backgroudImage = icon.getImage();

		newPlayer = new JPanel();
		newPlayer.setPreferredSize(new Dimension(640, 360));
		// set JPanel trasparent
		newPlayer.setBackground(new Color(0, 0, 0, 0));
		newPlayer.setOpaque(false);
		buttonNewPlayer = new MyButton("Add Player", this);
		nicknameLabel = new JLabel("Nickname:");
		nicknameLabel.setFont(nicknameLabel.getFont().deriveFont(18f));
		nicknameTextField = new JTextField(30);
		nicknameTextField.setFont(nicknameTextField.getFont().deriveFont(18f));

		//Avatar
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
		avatar1ImageIcon = new ImageIcon(image.getScaledInstance(100, 100, image.SCALE_SMOOTH));
		avatar1ImageLabel = new JLabel(avatar1ImageIcon);

		ImageIcon avatar2ImageIcon = new ImageIcon("./src/graphics/avatar/Female.png"); 
		image = avatar2ImageIcon.getImage();
		avatar2ImageIcon = new ImageIcon(image.getScaledInstance(100, 100, image.SCALE_SMOOTH));
		avatar2ImageLabel = new JLabel(avatar2ImageIcon);

		ImageIcon avatar3ImageIcon = new ImageIcon("./src/graphics/avatar/Alien.png"); 
		image = avatar3ImageIcon.getImage();
		avatar3ImageIcon = new ImageIcon(image.getScaledInstance(100, 100, image.SCALE_SMOOTH));
		avatar3ImageLabel = new JLabel(avatar3ImageIcon);

		ImageIcon avatar4ImageIcon = new ImageIcon("./src/graphics/avatar/Dog.png"); 
		image = avatar4ImageIcon.getImage();
		avatar4ImageIcon = new ImageIcon(image.getScaledInstance(100, 100, image.SCALE_SMOOTH));
		avatar4ImageLabel = new JLabel(avatar4ImageIcon);

		TitledBorder border = BorderFactory.createTitledBorder("Add Player");
		border.setTitleJustification(TitledBorder.CENTER); // Centra il titolo sul bordo
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

		// da far scegliere gli avatar
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

		// Button
		gbc.anchor = GridBagConstraints.SOUTH;
		gbc.gridx = 0; 
		gbc.gridy = 10;
		gbc.gridwidth = 2;
		newPlayer.add(buttonNewPlayer, gbc);
		

		//Load Player
		loadPlayer = new JPanel();
		loadPlayer.setPreferredSize(new Dimension(640, 360));
		TitledBorder borderLoadPlayer = BorderFactory.createTitledBorder("Load Player");
        borderLoadPlayer.setTitleJustification(TitledBorder.CENTER); // Centra il titolo sul bordo
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
	    Font newFont = font.deriveFont(font.getSize() + 10f); // Aumenta la dimensione di 10 punti
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
		
		// TODO bg sistemalo
		frame.setContentPane(this);
		frame.setVisible(true);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(backgroudImage, 0, 0, 1280, 720, this);
	}
	

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
				player = new BlackJackPlayer(nickname, avatar);
				controller.addDbPlayer(player);
				controller.addPlayer(player);
				caricaMenu();
			}else if(e.getSource() == buttonLoadPlayer) {
				audioManager.play("./src/sounds/button.wav");
				int indexPlayer = listaGiocatori.getSelectedIndex();
				if(indexPlayer == -1) {
					throw new LoginException("Nessuno profilo selezionato");
				}else {
					player = controller.getDbPlayerByIndex(indexPlayer);
					controller.addPlayer(player);
					caricaMenu();
				}
			}
		}catch (LoginException ex) {
			MyPopup myPopup = new MyPopup("Errore", ex.getMessage());
			myPopup.showMessage();
		}
	}

	private void caricaMenu() {
		frame.dispose();
		new MenuPage();
	}
}
