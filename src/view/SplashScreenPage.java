package view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * La classe SplashScreenPage rappresenta la schermata iniziale del gioco Blackjack.
 * Questa schermata mostra un'immagine di sfondo e un pulsante per iniziare il gioco.
 */
public class SplashScreenPage extends JPanel implements ActionListener{

	/**
	 * frame è la finestra di gioco principale.
	 */
	private Frame frame;

	/**
	 * img è l'immagine di sfondo.
	 */
	private Image img;

	/**
	 * playButton è il bottone per avviare il gioco
	 */
	private MyButton playButton;

	/**
	 * audioManager è l'istanza della classe AudioManager che si occupa di gestire gli effetti sonori
	 */
	private AudioManager audioManager;

	/**
	 * Costruttore della classe SplashScreenPage
	 * Inizializza la schermata di splash, carica l'immagine di sfondo e crea il pulsante "Play".
	 */
	public SplashScreenPage() {
		frame = new Frame();
		audioManager = AudioManager.getInstance();
		ImageIcon icon = new ImageIcon("./src/graphics/BlackJack.png"); 
		img = icon.getImage().getScaledInstance(1280, 720, Image.SCALE_SMOOTH);

		this.setLayout(null);

		playButton = new MyButton("Play", this);
		playButton.setBounds(590, 600, 100, 40);

		this.add(playButton);
		frame.setContentPane(this);
		frame.setVisible(true);
	}

	/**
	 * Disegna il contenuto del pannello, inclusa l'immagine di sfondo.
	 * @param g L'oggetto Graphics utilizzato per il disegno.
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D) g;
		g2D.drawImage(img, 0, 0, this);
	}

	/**
	 * Gestisce gli eventi di azione, in particolare il clic sul pulsante "Play".
	 * Quando il pulsante viene premuto, viene riprodotto un suono e viene avviata
	 * la schermata di login, chiudendo la schermata di splash.
	 * @param e L'evento di azione generato dal pulsante.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()== playButton) {
			audioManager.play("./src/sounds/button.wav");
			frame.dispose();
			new LoginPage();	
		}
	}	 
}
