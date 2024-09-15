package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import model.BlackJackPlayer;
import model.Carta;
import model.Player;
import model.TavoloDaGioco;

/*
 * Ambiente di gioco grafico dove vengono messe le carte per il player
 */
@SuppressWarnings("deprecation")
public class PlayersJPanel extends JPanel implements Observer{
	
	private TitledBorder titledBorder;
	private BlackJackPlayer player;
	private ArrayList<Carta> mano;
	private ArrayList<Image> carteImages;
	private JLabel punti;
	
	public PlayersJPanel(String name, BlackJackPlayer player) {
		this.player = player;
		carteImages = new ArrayList<>();
		punti = new JLabel("0", SwingConstants.CENTER);
		
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
		setBackground(new Color(120, 0, 0, 0));
		setOpaque(true);
		setLayout(new BorderLayout());
		
		add(punti, BorderLayout.NORTH);
		
	}
	
	public void setPanelTitle() {
		titledBorder.setTitle(player.getNickname());
		repaint();
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof TavoloDaGioco) {
			mostraCarte();
		}
	}
	
	public void mostraCarte() {
		System.out.println("Giocatore: "+player.getNickname());
		System.out.println("|_");
		System.out.println("Carte ricevute: "+player.getMano());
		carteImages.clear();
		mano = player.getMano();
		for (Carta carta : mano) {
			// Crea l'icona della carta usando il percorso dell'immagine ottenuto da carta.getNome()
		    String imagePath = carta.getPath(); // getNome() restituisce il path dell'immagine della carta
		    ImageIcon cartaIcon = new ImageIcon("./src/graphics/"+imagePath);
		    Image img = cartaIcon.getImage(); // Ottieni l'oggetto Image dall'ImageIcon
	        Image imgScaled = img.getScaledInstance(200, 200, Image.SCALE_SMOOTH); // Ridimensiona l'immagine
	       
	        carteImages.add(imgScaled);
	        
	        //ImageIcon scaledIcon = new ImageIcon(imgScaled);
		    //JLabel cartaLabel = new JLabel(scaledIcon);
	        //add(cartaLabel, BorderLayout.SOUTH);
	        punti.setText("Valore mano da definire");
		}
		revalidate();  // Aggiorna il layout del pannello
		repaint();     // Ridisegna il pannello
		
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

	    // Disegna ogni carta, sovrapponendole orizzontalmente
	    int xOffset = 10;  // Offset iniziale
	    int yOffset = getHeight() - 210;  // Posiziona le carte alla base del pannello

	    for (Image carta : carteImages) {
	    	g.drawImage(carta, xOffset, yOffset, this);
	        xOffset += 50;  // Sposta la prossima carta di 50px a destra
	    }
	}
}

