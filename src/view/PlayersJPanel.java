package view;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

/*
 * Ambiente di gioco grafico dove vengono messe le carte per il player
 */
public class PlayersJPanel extends JPanel{
	
	private TitledBorder titledBorder;

	public PlayersJPanel() {
		setPreferredSize(new Dimension(200, 200));
		
		titledBorder = BorderFactory.createTitledBorder("Giocatore");
		titledBorder.setTitleJustification(TitledBorder.CENTER);
        setBorder(titledBorder);
		/*
		 * TODO titolo: nome giocatore, 
		 * vedi come organizzare il tutto forse è meglio creare un panel per il player e uno per i bot,
		 * vedi come gestire la parte in basso
		 */
		
		setBackground(new Color(120, 0, 0, 0));
		setOpaque(true);
		
	}
}
