package view;

import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;

import model.BlackJackPlayer;
import model.TavoloDaGioco;

public class MenuPage extends JPanel implements ActionListener{

	private Frame frame;
	
	private JPanel leftMenuPanel;
	private JPanel rightMenuPanel;
	private Image backgroundImage;
	
	private TitledBorder border;
	private MyButton playButton;
	private JLabel botLabel;
	private JRadioButton oneBot;
	private JRadioButton twoBot;
	private JRadioButton threeBot;
	private ButtonGroup group;
	
	private JLabel nicknameLabel;
	private ImageIcon avatarIcon;
	private JLabel avatarLabel;
	private JLabel levelLabel;
	private JLabel nGames;
	private JLabel nGamesWin;
	private JLabel nGamesDraw;
	private JLabel nGamesLost;
	
	private TavoloDaGioco tavoloDaGioco;
	private BlackJackPlayer player;
	
	public MenuPage(TavoloDaGioco tavoloDaGioco) {
		this.tavoloDaGioco = tavoloDaGioco;
		
		frame = new Frame();
		
		player = tavoloDaGioco.getPlayer();
		
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
        gbc.fill = gbc.CENTER;
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
        nGamesDraw = new JLabel("Draw Games: "+player.getNumeroPareggi());
        nGamesDraw.setFont(labelFont);
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
        rightMenuPanel.add(nGamesDraw, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 6;
        rightMenuPanel.add(nGamesLost, gbc);
        

        
        this.setLayout(new BorderLayout());
        this.add(leftMenuPanel, BorderLayout.WEST);
        this.add(rightMenuPanel, BorderLayout.EAST);
        
        frame.setContentPane(this);
		frame.setVisible(true);
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == playButton) {
			String nBot = group.getSelection().getActionCommand();
			frame.dispose();
			new GamePage(tavoloDaGioco, nBot);
		}
	}
	
	
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }

	
}
