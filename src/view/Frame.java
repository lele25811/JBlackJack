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
	private LoginPanel loginPanel;
	private SplashScreenPanel splashPanel;
	private MenuPanel tablePanel;
	
	// todo:
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
		// Creaiamo i pannelli
		splashPanel = new SplashScreenPanel();
        loginPanel = new LoginPanel(this);
        tablePanel = new MenuPanel();
        
        // Aggiungiamo i pannelli al CardLayout
        mainPanel.add(splashPanel, "splash");
        mainPanel.add(loginPanel, "playerSelect");
        mainPanel.add(tablePanel, "menuPanel");

        splashPanel.addPlayButtonActionListener(e -> cardLayout.show(mainPanel, "playerSelect"));
        
        // Aggiungiamo il mainPanel al JFrame
        add(mainPanel);

        // Mostriamo lo splashPanel all'inizio
        cardLayout.show(mainPanel, "splash");
        
    }

	public void showPanel(String panelName) {
		cardLayout.show(mainPanel, panelName);
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof TavoloDaGioco) {
			nGiocatori = (int) arg;
			System.out.println("Giocatori: "+arg);
		}
	}

}
