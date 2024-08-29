package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class MyButton extends JButton{
	
	public MyButton(String text, ActionListener actionListener) {
		// Imposta il pulsante in modo che non possa essere selezionato tramite il tab
		this.setFocusable(false);

		// Imposta il testo del pulsante
		this.setText(text);

		// Imposta la posizione orizzontale del testo al centro del pulsante
		this.setHorizontalTextPosition(JButton.CENTER);

		// Imposta la posizione verticale del testo al centro del pulsante
		this.setVerticalTextPosition(JButton.CENTER);

		// Aggiunge un ActionListener per gestire i clic sul pulsante
		this.addActionListener(actionListener);

	}

}
