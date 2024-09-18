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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;

import model.BlackJackBot;
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
	private Integer puntiAttuali = 0;
	
	public PlayersJPanel(String name, BlackJackPlayer player) {
		this.player = player;
		carteImages = new ArrayList<>();
		punti = new JLabel("", SwingConstants.CENTER);
		
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
		if (o instanceof TavoloDaGioco && arg instanceof String) {
			String action = (String) arg;
			if(action.equals("DistribuisciCarteIniziali")) {
				drawCarteIniziali();
			}else if(action.equals("DistribuzioneTerminata")) {
				calcolaPunteggio();
			}else if(action.equals("NuovaCarta")) {
				drawCard();
				if(TavoloDaGioco.lastIsAsso(player)) {
					calcolaPunteggio();
				}else {
					puntiAttuali = puntiAttuali + player.getLastCard().getValore();
				}
				updatePunteggio();
			}
		}
	}
	
	private void updatePunteggio() {
		punti.setText("Punti attuali: " + puntiAttuali);
		punti.revalidate();
		punti.repaint();
	}
	
	private void drawCarteIniziali() {
		drawCard();
	}

	public void drawCard() {
		carteImages.clear();
		mano = player.getMano();
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
	
	public void calcolaPunteggio() {
		SwingUtilities.invokeLater(() -> {
			int[] punteggi = player.getPuntiMano();
			if (TavoloDaGioco.lastIsAsso(player) || puntiAttuali == 0) {
				puntiAttuali = scegliPunteggioAsso(punteggi);
			}else {
				puntiAttuali = punteggi[0];
			}
		});
	}
	
	public int scegliPunteggioAsso(int[] punteggi) {
	    if (punteggi.length == 2) { // Se ci sono due opzioni di punteggio (con Asso)
	        // Crea il messaggio e le opzioni per il popup
	        String message = "Vuoi contare l'Asso come 1 ("+punteggi[0]+") o 11 ("+punteggi[1]+")?";
	        String[] options = {"Asso = 1 ("+punteggi[0]+")", "Asso = 11 ("+punteggi[1]+")"};
	        
	        // Mostra il menu popup con le opzioni
	        int scelta = JOptionPane.showOptionDialog(
	            null, //componente padre della finestra 
	            message, // testo da visualizzare nella finestra
	            "Scegli il valore dell'Asso", // titolo finestra
	            JOptionPane.DEFAULT_OPTION, // tipo di operazioni che verranno mostrate
	            JOptionPane.QUESTION_MESSAGE, // tipo di messaggio che viene mostrato
	            null, // icona
	            options, // risposta 1
	            options[0] //risposta 2
	        );
	        
	        // Gestisci la scelta del giocatore
	        if (scelta == 0) { // Asso = 1
	            return punteggi[0];
	        } else if (scelta == 1) { // Asso = 11
	            return punteggi[1];
	        }
	    }
	    return punteggi[0];
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
