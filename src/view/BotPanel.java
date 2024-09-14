package view;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import model.BlackJackBot;

public class BotPanel extends JPanel{
	
	private TitledBorder titledBorder;
	private BlackJackBot bot;
	
	public BotPanel(String name, BlackJackBot bot) {
		this.bot = bot;
		
		setPreferredSize(new Dimension(200, 200));
		
		titledBorder = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.WHITE),  // Bordo rosso
                bot.getNickname(),                      // Titolo con il nome del giocatore
                TitledBorder.CENTER,                       // Posiziona il titolo al centro
                TitledBorder.TOP,                          // Posiziona il titolo in alto
                getFont(),                                 // Font del titolo
                Color.WHITE                               // Colore del titolo (bianco)
        );
		
        setBorder(titledBorder);
        
		setBackground(new Color(120, 0, 0, 0));
		setOpaque(true);
		
	}
	
	public void setPanelTitle() {
		titledBorder.setTitle(bot.getNickname());
		repaint();
	}
}
