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

import model.BlackJackBot;
import model.BlackJackPlayer;
import model.TavoloDaGioco;

public class GamePage extends JPanel{
	
	private Frame frame;
	private Image backgroudImage;
	
	private TavoloDaGioco tavoloDaGioco;
	
	private BotPanel bancoPanel;
	private BotPanel bot1Panel;
	private PlayerPanel playerPanel;
	private BotPanel bot2Panel;
	
	private ActionPlayerPanel actionPlayerMenu;
	
	
	@SuppressWarnings("deprecation")
	public GamePage(TavoloDaGioco tDG) {
		frame = new Frame();
		this.tavoloDaGioco = tDG;
	
		System.out.println("Ci sono contro "+ tavoloDaGioco.getNumeroGiocatori() +" Bot");
		
		ImageIcon icon = new ImageIcon("./src/graphics/backgroundGame.png");
		backgroudImage = icon.getImage();
		
		bancoPanel = new BotPanel("Banco", (BlackJackBot) tavoloDaGioco.getBanco());
		bot1Panel = new BotPanel("Bot1", (BlackJackBot) tavoloDaGioco.getBot1());
		playerPanel = new PlayerPanel("Player", (BlackJackPlayer) tavoloDaGioco.getPlayer());
		bot2Panel = new BotPanel("Bot2", (BlackJackBot) tavoloDaGioco.getBot2());
		
		actionPlayerMenu = new ActionPlayerPanel(tavoloDaGioco, playerPanel);
		
		playerPanel.addActionPlayer(actionPlayerMenu);

		
		this.setLayout(new BorderLayout());

        JPanel gamePanel = new JPanel(new GridLayout(2, 2)); 
        gamePanel.setBackground(new Color(120, 0, 0, 0));
        gamePanel.setOpaque(false);
        
        gamePanel.add(bancoPanel);
        gamePanel.add(bot1Panel);
        gamePanel.add(playerPanel);
        gamePanel.add(bot2Panel);

        this.add(gamePanel, BorderLayout.CENTER);
        this.add(actionPlayerMenu, BorderLayout.PAGE_END);
        
        tavoloDaGioco.addObserver(bancoPanel);
        tavoloDaGioco.addObserver(bot1Panel);
        tavoloDaGioco.addObserver(playerPanel);
        tavoloDaGioco.addObserver(bot2Panel);
        tavoloDaGioco.addObserver(actionPlayerMenu);
        tavoloDaGioco.startGame();
        
		frame.setContentPane(this);
		frame.setVisible(true);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(backgroudImage, 0, 0, 1280, 720, this);
	}

}
