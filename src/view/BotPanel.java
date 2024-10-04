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
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import model.BlackJackBot;
import model.Carta;
import model.TavoloDaGioco;
import model.UpdateEvent;

/**
 * La classe BotPanel è una sottoclasse di JPanel che rappresenta il pannello
 * di un bot nel gioco di BlackJack. Implementa l'interfaccia Observer per aggiornare
 * il pannello quando ci sono cambiamenti di stato nel bot associato. Visualizza le carte
 * e il punteggio del bot e gestisce eventi come l'inizio della partita, l'assegnazione delle carte 
 * iniziali, la pescata di nuove carte e la fine del turno.
 */
@SuppressWarnings("deprecation")
public class BotPanel extends JPanel implements Observer{

	private TitledBorder titledBorder;	/** Il bordo titolato che contiene il nome del bot. */
	private BlackJackBot bot;	/** Riferimento al bot associato a questo pannello. */
	private ArrayList<Carta> mano;	/** La mano attualmente in possesso del bot */
	private ArrayList<Image> carteImages;	/** Una lista di immagini che rappresentano graficamente le carte attualmente in mano al bot. */
	private JLabel punti;	/** Un'etichetta che visualizza il punteggio attuale del bot. */
	private JLabel azione;
	private Integer puntiAttuali = 0;	/** Il punteggio attuale del bot. */
	private String cartaCopertaPath = "CartaRetro.png";	/** Il percorso dell'immagine utilizzata per rappresentare la carta coperta */
	private AudioManager audioManager;	/** Gestore dell'audio per riprodurre suoni durante l'interazione con l'interfaccia utente. */

	/**
	 * Costruisce un BotPanel che rappresenta il bot specificato.
	 * Inizializza il layout, il bordo, e le componenti di visualizzazione del punteggio.
	 * @param name Il nome del pannello (non utilizzato attualmente).
	 * @param bot  Il bot associato a questo pannello.
	 */
	public BotPanel(String name, BlackJackBot bot) {
		this.bot = bot;
		audioManager = AudioManager.getInstance();
		carteImages = new ArrayList<>();
		punti = new JLabel("", SwingConstants.CENTER);
		azione = new JLabel("", SwingConstants.CENTER);

		setPreferredSize(new Dimension(200, 200));

		titledBorder = BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.WHITE),  
				bot.getNickname(),                      
				TitledBorder.CENTER,                    
				TitledBorder.TOP,                       
				getFont(),                              
				Color.WHITE                             
				);
		setBorder(titledBorder);

		setBackground(new Color(120, 0, 0, 0));
		setOpaque(false);

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		punti.setAlignmentX(CENTER_ALIGNMENT);
		azione.setAlignmentX(CENTER_ALIGNMENT);

		add(punti);
		add(azione);
	}

	/**
	 * Imposta il titolo del bordo con il nome corrente del bot.
	 */
	public void setPanelTitle() {
		titledBorder.setTitle(bot.getNickname());
		revalidate();
		repaint();
	}

	/**
	 * Metodo dell'interfaccia Observer. Viene chiamato ogni volta che l'oggetto osservato
	 * (in questo caso TavoloDaGioco) notifica un cambiamento. Il metodo aggiorna
	 * il pannello del bot in base all'evento ricevuto.
	 * @param o   L'oggetto osservato (in questo caso il TavoloDaGioco).
	 * @param arg L'evento di aggiornamento passato dall'oggetto osservato.
	 */
	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof TavoloDaGioco && arg instanceof UpdateEvent) {
			UpdateEvent event = (UpdateEvent) arg;
			String action = event.getAction();
			Object data = event.getData();
			if (data instanceof BlackJackBot && bot.equals((BlackJackBot) data)) {
				if(action.equals("DistribuisciCarteIniziali")) {
					if(bot.getIsBanco()) {
						drawCardsInizialiBanco();
					}else {
						drawCards();
						calcolaPunteggio();
						updatePunti();
					}
				}else if(action.equals("TurnoBanco")) {
					drawCards();
					calcolaPunteggio();
					updatePunti();
				}else if(action.equals("Carta") || action.equals("Raddoppio")) {
					updateAzione(action, false);
					try {
						Thread.sleep(2000);
					}catch (Exception e) {
						e.printStackTrace();
					}
					drawCards();
					calcolaPunteggio();
					updatePunti();
					updateAzione(action, false);
				}else if(action.equals("Passa Turno") || action.equals("Sballato")) {
					updatePunti();
					updateAzione(action, true);
				}
				revalidate();
				repaint();
			}
		}
	}

	/**
	 * Aggiorna il punteggio attuale visualizzato nel pannello.
	 * Ottiene il punteggio corrente del bot e aggiorna il JLabel dei punti.
	 */
	private void updatePunti() {
		puntiAttuali = bot.getPunti();
		punti.setText("Punti Attuali: "+puntiAttuali);
		punti.revalidate();
		punti.repaint();
	}
	
	/**
	 * Aggiorna l'azione che viene fatta dal bot e viene visualizzata nel pannello.
	 * @param action l'azione che viene fatta dal bot
	 * @param fine true se il turno del bot termina dopo questa azione, altrimenti false
	 */
	private void updateAzione(String action, boolean fine) {
		if (!fine) {
			azione.setText("Ha chiesto: " + action);
		} else {
			azione.setText(action);
		}
		azione.revalidate();
		azione.repaint();
	}

	/**
	 * Calcola il punteggio attuale della mano del bot.
	 * Utilizza i punteggi disponibili della mano del bot per scegliere il punteggio migliore.
	 */
	private void calcolaPunteggio() {
		int[] punteggiDisponibili = bot.getValoreManoIniziale();
		puntiAttuali = bot.getSceltaPunti(punteggiDisponibili);
	}

	/**
	 * Disegna le carte attualmente in mano al bot. Aggiunge le immagini delle carte
	 * alla lista di carte da visualizzare e ridimensiona ogni carta a 200x200 pixel.
	 * Riproduce un effetto sonoro durante l'aggiornamento.
	 */
	public void drawCards() {
		carteImages.clear();
		mano = bot.getMano();
		for (Carta carta : mano) {
			String imagePath = carta.getPath(); 
			ImageIcon cartaIcon = new ImageIcon("./src/graphics/"+imagePath);
			Image img = cartaIcon.getImage(); 
			Image imgScaled = img.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
			carteImages.add(imgScaled);
		}
		audioManager.play("./src/sounds/card.wav");
		revalidate();
		repaint();
	}

	/**
	 * Disegna le carte iniziali del banco, mostrando solo la prima carta scoperta
	 * e la seconda coperta. Viene utilizzato durante il turno del banco.
	 */
	public void drawCardsInizialiBanco() {
		carteImages.clear();
		mano = bot.getMano();
		if (mano.size() > 0) {
			Carta primaCarta = mano.get(0);
			String primaCartaPath = primaCarta.getPath();
			ImageIcon primaCartaIcon = new ImageIcon("./src/graphics/" + primaCartaPath);
			Image primaCartaImage = primaCartaIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
			carteImages.add(primaCartaImage);
		}
		if (mano.size() > 1) {
			ImageIcon cartaCopertaIcon = new ImageIcon("./src/graphics/" + cartaCopertaPath);
			Image cartaCopertaImage = cartaCopertaIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
			carteImages.add(cartaCopertaImage);
		}
		audioManager.play("./src/sounds/card.wav");
		revalidate();
		repaint();
	}

	/**
	 * Override del metodo paintComponent per disegnare graficamente le carte sul pannello.
	 * Le carte vengono disegnate in modo sovrapposto, con un offset orizzontale tra ciascuna.
	 * @param g Il contesto grafico in cui disegnare le carte.
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		int xOffset = 10;  
		int yOffset = getHeight() - 210;  
		for (Image carta : carteImages) {
			g.drawImage(carta, xOffset, yOffset, this);
			xOffset += 40; 
		}
	}
}
