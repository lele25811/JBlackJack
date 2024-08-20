package view;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import model.TavoloDaGioco;

@SuppressWarnings("deprecation")
public class Frame extends JFrame implements Observer{
	
	private CardLayout cardLayout;
	private JPanel mainPanel;
	
	private int nGiocatori = 0;
	
	public Frame() {
		setTitle("JBlackJack");
		setSize(1280, 720);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		
		// Creiamo un CardLayout per passare da un pannello all'altro
		cardLayout = new CardLayout();
		mainPanel = new JPanel(cardLayout);
		// Creaiamo due pannelli
		SplashScreenPanel splashPanel = new SplashScreenPanel();
        LoginPanel loginPanel = new LoginPanel();

        // Aggiungiamo i pannelli al CardLayout
        mainPanel.add(splashPanel, "splash");
        mainPanel.add(loginPanel, "boardGame");

        splashPanel.addPlayButtonActionListener(e -> cardLayout.show(mainPanel, "boardGame"));
        
        // Aggiungiamo il mainPanel al JFrame
        add(mainPanel);

        // Mostriamo lo splashPanel all'inizio
        cardLayout.show(mainPanel, "splash");
    }


	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof TavoloDaGioco) {
			nGiocatori = (int) arg;
			System.out.println("Giocatori: "+arg);
		}
	}

}
