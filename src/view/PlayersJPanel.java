package view;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import model.BlackJackPlayer;
import model.Player;

/*
 * Ambiente di gioco grafico dove vengono messe le carte per il player
 */
public class PlayersJPanel extends JPanel{
	
	private TitledBorder titledBorder;
	private BlackJackPlayer player;
	
	public PlayersJPanel(String name, BlackJackPlayer player) {
		this.player = player;
		
		setPreferredSize(new Dimension(200, 200));
		
		titledBorder = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.RED),  // Bordo rosso
                player.getNickname(),                      // Titolo con il nome del giocatore
                TitledBorder.CENTER,                       // Posiziona il titolo al centro
                TitledBorder.TOP,                          // Posiziona il titolo in alto
                getFont(),                                 // Font del titolo
                Color.WHITE                               // Colore del titolo (bianco)
        );

        
        setBorder(titledBorder);
		/*
		 * TODO titolo: nome giocatore, 
		 * vedi come organizzare il tutto forse è meglio creare un panel per il player e uno per i bot,
		 * vedi come gestire la parte in basso
		 */
		
		setBackground(new Color(120, 0, 0, 0));
		setOpaque(true);
		
	}
	
	public void setPanelTitle() {
		titledBorder.setTitle(player.getNickname());
		repaint();
	}
}
