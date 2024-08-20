package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
/*
 * Classe che definisce la grafica del tavolo da gioco 
 */
public class LoginPanel extends JPanel{
	private Image backgroudImage;
	private JPanel newPlayer;
	private JButton buttonNewPlayer;
	private JPanel loadPlayer;
	private JButton buttonLoadPlayer;
	
	public LoginPanel() {
		//Carica l'immagine dal percorso specificato
		ImageIcon icon = new ImageIcon("./src/graphics/backgroundGame.png");
		backgroudImage = icon.getImage();
		
		newPlayer = new JPanel();
		newPlayer.setBackground(Color.RED);
		newPlayer.setPreferredSize(new Dimension(640, 360));
		buttonNewPlayer = new JButton("add Player");
		newPlayer.setLayout(new BorderLayout());
		newPlayer.add(buttonNewPlayer, BorderLayout.SOUTH);
		
		loadPlayer = new JPanel();
		loadPlayer.setBackground(Color.BLUE);
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
