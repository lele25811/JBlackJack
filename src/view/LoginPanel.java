package view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
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
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import model.BlackJackPlayer;
import model.Database;
/*
 * Classe che definisce la grafica del tavolo da gioco 
 */
public class LoginPanel extends JPanel{
	private Image backgroudImage;
	
	private JFrame frame;
	private JPanel newPlayer;
	private JButton buttonNewPlayer;
	private JLabel nicknameLabel;
	private JTextField nicknameTextField;
	private JPanel loadPlayer;
	private JButton buttonLoadPlayer;
	// Avatar 
	private JLabel avatar1ImageLabel;
	private JRadioButton avatar1Button;
	private JLabel avatar2ImageLabel;
	private JRadioButton avatar2Button;
	private JLabel avatar3ImageLabel;
	private JRadioButton avatar3Button;
	private JLabel avatar4ImageLabel;
	private JRadioButton avatar4Button;
	
	
	@SuppressWarnings("deprecation")
	public LoginPanel(Frame frame) {
		this.frame = frame;
		setLayout(new BorderLayout());
		//Carica l'immagine dal percorso specificato
		ImageIcon icon = new ImageIcon("./src/graphics/backgroundGame.png");
		backgroudImage = icon.getImage();
		
		newPlayer = new JPanel();
		newPlayer.setPreferredSize(new Dimension(640, 360));
		// set JPanel trasparent
		newPlayer.setBackground(new Color(0, 0, 0, 0));
		newPlayer.setOpaque(true);
		buttonNewPlayer = new JButton("Add Player");
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
		
		ButtonGroup group = new ButtonGroup();
		group.add(avatar1Button);
		group.add(avatar2Button);
		group.add(avatar3Button);
		group.add(avatar4Button);
		
		ImageIcon avatar1ImageIcon = new ImageIcon("./src/graphics/avatar/Avatar1.png"); 
		Image image = avatar1ImageIcon.getImage();
		avatar1ImageIcon = new ImageIcon(image.getScaledInstance(100, 100, image.SCALE_SMOOTH));
		avatar1ImageLabel = new JLabel(avatar1ImageIcon);
		
		ImageIcon avatar2ImageIcon = new ImageIcon("./src/graphics/avatar/Avatar2.png"); 
		image = avatar2ImageIcon.getImage();
		avatar2ImageIcon = new ImageIcon(image.getScaledInstance(100, 100, image.SCALE_SMOOTH));
		avatar2ImageLabel = new JLabel(avatar2ImageIcon);
		
		ImageIcon avatar3ImageIcon = new ImageIcon("./src/graphics/avatar/Avatar3.png"); 
		image = avatar3ImageIcon.getImage();
		avatar3ImageIcon = new ImageIcon(image.getScaledInstance(100, 100, image.SCALE_SMOOTH));
		avatar3ImageLabel = new JLabel(avatar3ImageIcon);
		
		ImageIcon avatar4ImageIcon = new ImageIcon("./src/graphics/avatar/Avatar4.png"); 
		image = avatar4ImageIcon.getImage();
		avatar4ImageIcon = new ImageIcon(image.getScaledInstance(100, 100, image.SCALE_SMOOTH));
		avatar4ImageLabel = new JLabel(avatar4ImageIcon);
        

		newPlayer.setLayout(new GridBagLayout());
		TitledBorder border = BorderFactory.createTitledBorder("Add Player");
        border.setTitleJustification(TitledBorder.CENTER); // Centra il titolo sul bordo
        newPlayer.setBorder(border);
        
        newPlayer.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = gbc.HORIZONTAL;
        
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
        
        buttonNewPlayer.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String nickname = nicknameTextField.getText();
				String avatar = group.getSelection().getActionCommand();
				//System.out.println("Nickname: "+nickname+" Avatar: "+avatar);
				
				Database bd = Database.getIstance();
				BlackJackPlayer player = new BlackJackPlayer(nickname, avatar);
				System.out.println(player.toString());
				
				/*
				 * TODO: gestisci il logic del giocatore, vai a comunicare al model 
				 * e quindi al bd per salvare il player.
				 */
				
				frame.showPanel("menuPanel");
			}
		});
		
		
		
		
		
		
		loadPlayer = new JPanel();
		//loadPlayer.setBackground(Color.BLUE);
		loadPlayer.setPreferredSize(new Dimension(640, 360));
		buttonLoadPlayer = new JButton("load Player");
		loadPlayer.setLayout(new BorderLayout());
		loadPlayer.add(buttonLoadPlayer, BorderLayout.SOUTH);
		
		
		setLayout(new BorderLayout());
		add(newPlayer, BorderLayout.WEST);
		add(loadPlayer, BorderLayout.EAST);
		
		
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(backgroudImage, 0, 0, 1280, 720, this);
	}
}
