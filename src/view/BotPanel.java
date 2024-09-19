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
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;

import model.BlackJackBot;
import model.Carta;
import model.TavoloDaGioco;

@SuppressWarnings("deprecation")
public class BotPanel extends JPanel implements Observer{
	
	private TitledBorder titledBorder;
	private BlackJackBot bot;
	private ArrayList<Carta> mano;
	private ArrayList<Image> carteImages;
	private JLabel punti;
	private Integer puntiAttuali = 0;
	
	public BotPanel(String name, BlackJackBot bot) {
		this.bot = bot;
		carteImages = new ArrayList<>();
		punti = new JLabel("", SwingConstants.CENTER);
		
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
		setOpaque(false);
		
		setLayout(new BorderLayout());
		add(punti, BorderLayout.NORTH);
		
	}
	
	public void setPanelTitle() {
		titledBorder.setTitle(bot.getNickname());
		revalidate();
		repaint();
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof TavoloDaGioco && arg instanceof String) {
			String action = (String) arg;
			
			if(action.equals("DistribuisciCarteIniziali")) {
				drawCarteIniziali();
				calcolaPunteggio();
				updatePunti();
			}else if(action.equals("DistribuzioneTerminata")) {
				calcolaPunteggio();
				updatePunti();
			}else if(action.equals("NuovaCartaBot")) {
				drawCards();
				updatePunti();
			}else if(action.equals("FineTurno")) {
				updatePunti();
			}
			revalidate();
			repaint();
		}
	}

	private void updatePunti() {
		puntiAttuali = bot.getPunti();
		punti.setText("Punti attuali: "+puntiAttuali);
		revalidate();
		repaint();
	}

	private void calcolaPunteggio() {
		int[] punteggiDisponibili = bot.getValoreManoIniziale();
		//puntiAttuali = bot.getSceltaPunteggio(punteggiDisponibili);
		puntiAttuali = bot.getSceltaPunti(punteggiDisponibili);
		updatePunti();
	}

	private void drawCarteIniziali() {
		drawCards();
	}
	
	public void drawCards() {
		carteImages.clear();
		mano = bot.getMano();
		for (Carta carta : mano) {
			// Crea l'icona della carta usando il percorso dell'immagine ottenuto da carta.getNome()
		    String imagePath = carta.getPath(); // getNome() restituisce il path dell'immagine della carta
		    ImageIcon cartaIcon = new ImageIcon("./src/graphics/"+imagePath);
		    Image img = cartaIcon.getImage(); // Ottieni l'oggetto Image dall'ImageIcon
	        Image imgScaled = img.getScaledInstance(200, 200, Image.SCALE_SMOOTH); // Ridimensiona l'immagine
	        
	        carteImages.add(imgScaled);
		}
		revalidate();
		repaint();
	}
	
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

	    // Disegna ogni carta, sovrapponendole orizzontalmente
	    int xOffset = 10;  // Offset iniziale
	    int yOffset = getHeight() - 210;  // Posiziona le carte alla base del pannello

	    for (Image carta : carteImages) {
	    	g.drawImage(carta, xOffset, yOffset, this);
	        xOffset += 40;  // Sposta la prossima carta di 50px a destra
	    }
	}
}
