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

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import model.TavoloDaGioco;

public class GamePage extends JPanel{
	
	private Frame frame;
	private Image backgroudImage;
	
	private TavoloDaGioco tavoloDaGioco;
	String nBot;
	
	public GamePage(TavoloDaGioco tavoloDaGioco, String nBot) {
		frame = new Frame();
		this.tavoloDaGioco = tavoloDaGioco;
		this.nBot = nBot;
	
		tavoloDaGioco.addBot(nBot);
		System.out.println("Ci sono contro "+nBot+" Bot");
		
		ImageIcon icon = new ImageIcon("./src/graphics/backgroundGame.png");
		backgroudImage = icon.getImage();

		PlayersJPanel bancoPanel = new PlayersJPanel();
		PlayersJPanel bot1Panel = new PlayersJPanel();
		PlayersJPanel playerPanel = new PlayersJPanel();
		PlayersJPanel bot2Panel = new PlayersJPanel();
		
		this.setLayout(new GridLayout(3, 3));

		this.add(bancoPanel);     

		this.add(bot1Panel);

		this.add(playerPanel);
		
		this.add(bot2Panel);


		/*
		this.setLayout(new BorderLayout());
		
		PlayersJPanel bancoPanel = new PlayersJPanel();
		PlayersJPanel bot1Panel = new PlayersJPanel();
		PlayersJPanel playerPanel = new PlayersJPanel();
		PlayersJPanel bot2Panel = new PlayersJPanel();	
		
		
		this.add(bancoPanel, BorderLayout.NORTH);
	    this.add(bot1Panel, BorderLayout.SOUTH);
	    this.add(playerPanel, BorderLayout.EAST);
	    this.add(bot2Panel, BorderLayout.WEST);

		*/
		
		frame.setContentPane(this);
		frame.setVisible(true);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(backgroudImage, 0, 0, 1280, 720, this);
	}

}
