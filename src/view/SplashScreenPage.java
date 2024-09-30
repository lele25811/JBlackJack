package view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class SplashScreenPage extends JPanel implements ActionListener{
	private Frame frame;
	private Image img;
	private MyButton playButton;
	private AudioManager audioManager;
	
	public SplashScreenPage() {
		frame = new Frame();
		audioManager = AudioManager.getInstance();
		// Carica e scala l'immagine
        ImageIcon icon = new ImageIcon("./src/graphics/BlackJack.png"); 
        //img = new ImageIcon("./src/graphics/BlackJack.png").getImage();
        img = icon.getImage().getScaledInstance(1280, 720, Image.SCALE_SMOOTH);

        // Imposta il layout nullo per posizionare il bottone manualmente
        this.setLayout(null);

        // Crea il bottone "Gioca"
        playButton = new MyButton("Play", this);
        playButton.setBounds(590, 600, 100, 40); // Posiziona il bottone

        // Aggiungi il bottone al pannello
        this.add(playButton);
        frame.setContentPane(this);
        frame.setVisible(true);
	}
	
	 @Override
	 protected void paintComponent(Graphics g) {
		 super.paintComponent(g);
	        
	     // Converte l'oggetto Graphics in un oggetto Graphics2D per utilizzare funzionalità avanzate di disegno
	     Graphics2D g2D = (Graphics2D) g;
	        
	     // Disegna l'immagine di sfondo alle coordinate (0, 0) del pannello
	     g2D.drawImage(img, 0, 0, this);
    }
	 
	@Override
	public void actionPerformed(ActionEvent e) {
	    // Verifica se l'evento è stato generato dal pulsante di invio
		if(e.getSource()== playButton) {
			audioManager.play("./src/sounds/button.wav");
			// Chiude il frame corrente
			frame.dispose();
		    // Avvia l'interfaccia grafica del gioco con il nuovo giocatore
			new LoginPage();	
		}
	}	 
}
