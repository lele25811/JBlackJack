package view;

import java.awt.event.ActionListener;

import javax.swing.JButton;

/**
 * La classe MyButton estende JButton ed è utilizzata per creare un pulsante personalizzato
 * con un testo specificato e un ActionListener associato. Il pulsante è configurato per
 * non essere focalizzabile e posiziona il testo al centro sia orizzontalmente che verticalmente.
 */
public class MyButton extends JButton{
	
	 /**
     * Costruttore della classe MyButton.
     * Crea un pulsante con il testo specificato e un listener per gestire l'azione di clic.
     * @param text Il testo da visualizzare sul pulsante.
     * @param actionListener L'ActionListener associato al pulsante, che gestisce gli eventi di clic.
     */
	public MyButton(String text, ActionListener actionListener) {

		this.setFocusable(false);

		this.setText(text);

		this.setHorizontalTextPosition(JButton.CENTER);

		this.setVerticalTextPosition(JButton.CENTER);

		this.addActionListener(actionListener);
	}
}
