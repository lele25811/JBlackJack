package view;

import javax.swing.JFrame;

/**
 * La classe Frame estende JFrame e rappresenta la finestra principale dell'applicazione
 * JBlackJack. Configura il titolo, le dimensioni e altre proprietà della finestra.
 */
public class Frame extends JFrame{

	/**
	 * Costuttore della classe Frame.
	 */
	public Frame() {
		this.setTitle("JBlackJack");

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setSize(1280, 720);

		this.setResizable(false);

		this.setLocationRelativeTo(null);
	}
}