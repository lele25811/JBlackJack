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
		
	private BotPanel bancoPanel;
	private BotPanel bot1Panel;
	private PlayerPanel playerPanel;
	private BotPanel bot2Panel;
	//private ActionPlayerPanel actionPlayerMenu;
	private ActionPlayerPanel actionPlayerMenu;
	private GameController controller;
	
	@SuppressWarnings("deprecation")
	public GamePage() {
		controller = GameController.getIstance();
		
		revalidate();
		repaint();
		
		frame = new Frame();
				
		ImageIcon icon = new ImageIcon("./src/graphics/backgroundGame.png");
		backgroudImage = icon.getImage();
		
		bancoPanel = new BotPanel("Banco", (BlackJackBot) controller.getBanco());
		bot1Panel = new BotPanel("Bot1", (BlackJackBot) controller.getBot1());
		playerPanel = new PlayerPanel("Player", (BlackJackPlayer) controller.getPlayer());
		bot2Panel = new BotPanel("Bot2", (BlackJackBot) controller.getBot2());
		
		//actionPlayerMenu = new ActionPlayerPanel(tavoloDaGioco, playerPanel, frame, this);
		actionPlayerMenu = new ActionPlayerPanel(frame);
		
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
        
        // ###
        controller = GameController.getIstance();
        controller.addActionPlayerMenu(actionPlayerMenu);
        controller.addPlayerPanel(playerPanel);

        controller.addObserver(bancoPanel);
        controller.addObserver(bot1Panel);
        controller.addObserver(playerPanel);
        controller.addObserver(bot2Panel);
        controller.addObserver(actionPlayerMenu);
        
        controller.startGame();
        
		frame.setContentPane(this);
		frame.setVisible(true);
	}
	
	@SuppressWarnings("deprecation")
	public void resetGame() {
	    controller.deleteObserver(bancoPanel);
	    controller.deleteObserver(bot1Panel);
	    controller.deleteObserver(playerPanel);
	    controller.deleteObserver(bot2Panel);
	    controller.deleteObserver(actionPlayerMenu);  
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(backgroudImage, 0, 0, 1280, 720, this);
	}
}
