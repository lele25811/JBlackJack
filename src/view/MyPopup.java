package view;

import javax.swing.JOptionPane;

/**
 * La classe MyPopup è responsabile della creazione e visualizzazione di una finestra popup
 * di dialogo con un messaggio informativo. Utilizza JOptionPanel per mostrare
 * una finestra di dialogo semplice con un titolo e un testo specificato.
 */
public class MyPopup {

	/**
	 * title è il titlo della finestra popup.
	 */
	private String title;

	/**
	 * text è il messaggio da visualizzare nella finestra popup.
	 */
	private String text;

	/**
	 * Costruttore della classe MyPopup.
	 * @param title il titolo della finestra di diagolo
	 * @param text il testo del messaggio da visualizzare
	 */
	public MyPopup(String title, String text) {
		this.title = title;
		this.text = text;
	}

	/**
	 * Mostra una finestra di dialogo con il titolo e il testo forniti durante l'istanza della classe.
	 * La finestra di dialogo utilizza un'icona di informazione.
	 */
	public void showMessage() {
		JOptionPane.showMessageDialog(
				null,
				text,
				title,
				JOptionPane.INFORMATION_MESSAGE
				);
	}
}