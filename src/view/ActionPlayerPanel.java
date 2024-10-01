package view;

import java.awt.Color;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import controller.GameController;
import model.BlackJackPlayer;
import model.Player;
import model.TavoloDaGioco;
import model.UpdateEvent;

@SuppressWarnings("deprecation")
public class ActionPlayerPanel extends JPanel implements Observer{

	private JButton cartaButton;
	private JButton staiButton;
	private JButton raddoppiaButton;
	private JButton dividiButton;
	private JButton passaManoButton;
	private Frame frame;
	private GameController controller;
	
	/**
	 * La classe ActionPlayerPanel rappresenta il pannello di controllo per il giocatore umano,
	 * dove può selezionare le azioni da intraprendere durante il gioco di BlackJack, come
	 * chiedere una carta, stare, raddoppiare o dividere le carte.
	 * Implementa Observer per reagire agli aggiornamenti del modello (TavoloDaGioco).
	 */
	public ActionPlayerPanel(Frame frame) {
		this.frame = frame;
		controller = GameController.getIstance();
		
		TitledBorder border = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.RED, 2), "Azioni di gioco");
	    border.setTitleColor(Color.RED);
	    setBorder(border);
	    
	    setBackground(new Color(120, 0, 0, 0));
		setOpaque(false);
		
		cartaButton = new JButton("Card");
		staiButton = new JButton("Stay");
		passaManoButton = new JButton("Next Hand");
		raddoppiaButton = new JButton("Double");
		dividiButton = new JButton("Split");
		
		cartaButton.setEnabled(false);
		staiButton.setEnabled(false);
		passaManoButton.setEnabled(false);
		raddoppiaButton.setEnabled(false);
		dividiButton.setEnabled(false);
		
		add(cartaButton);
		add(staiButton);
		add(passaManoButton);
		add(raddoppiaButton);
		add(dividiButton);
		
		passaManoButton.setVisible(false);
	}
	
	/**
	 * Restituisce il bottone per l'azione "Chiedi carta".
	 * @return il bottone "Card"
	 */
	public JButton getCartaButton() {
		return cartaButton;
	}

	/**
	 * Restituisce il bottone per l'azione "Stai".
	 * @return il bottone "Stay"
	 */
	public JButton getStaiButton() {
		return staiButton;
	}

	/**
	 * Restituisce il bottone per l'azione "Raddoppia".
	 * @return il bottone "Double"
	 */
	public JButton getRaddoppiaButton() {
		return raddoppiaButton;
	}

	/**
	 * Restituisce il bottone per l'azione "Dividi".
	 * @return il bottone "Split"
	 */
	public JButton getDividiButton() {
		return dividiButton;
	}
	
	/**
	 * Restituisce il bottone per l'azione "Passa Mano" (durante uno split).
	 * @return il bottone "Next Hand"
	 */
	public JButton getPassaManoButton() {
		return passaManoButton;
	}

	/**
	 * Aggiorna lo stato del pannello in base agli eventi provenienti dal modello
	 * osservato. Abilita o disabilita i bottoni in base all'azione corrente.
	 * @param o l'oggetto osservato (TavoloDaGioco)
	 * @param arg l'evento di aggiornamento (UpdateEvent)
	 */
	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof TavoloDaGioco && arg instanceof UpdateEvent) {
			UpdateEvent event = (UpdateEvent) arg;

			String action = event.getAction();
			Object data = event.getData();

			if (action.equals("TurnoPlayer")) {
				cartaButton.setEnabled(true);
				staiButton.setEnabled(true);
				raddoppiaButton.setEnabled(true);
				if(controller.isCarteUguali((BlackJackPlayer) data)) {
					dividiButton.setEnabled(true);
				}
			}else if (action.equals("Dividi")) {
				Player player = (Player) data;
				if (player.isSplit() && player.getIndexMano() == 0) {
					staiButton.setVisible(false);
					passaManoButton.setVisible(true);
					passaManoButton.setEnabled(true);
				} else {
					staiButton.setVisible(true);
					staiButton.setEnabled(true);
					passaManoButton.setVisible(false);
				}
			}
		}
	}
	
	/**
	 * Disabilita tutti i bottoni e passa il turno al giocatore successivo.
	 * @param punteggio il punteggio del giocatore al termine del turno
	 */
	public void passaTurno(int punteggio) {
		cartaButton.setEnabled(false);
		staiButton.setEnabled(false);
		raddoppiaButton.setEnabled(false);
		dividiButton.setEnabled(false);
		controller.passaTurno(punteggio);
	}
	
	/**
	 * Aggiorna i bottoni per gestire la transizione tra le mani durante uno split.
	 */
	public void cambioButtonSplit() {
		staiButton.setVisible(true); 
		staiButton.setEnabled(true);
		passaManoButton.setVisible(false);
	}

	/**
	 * Chiede al giocatore se vuole iniziare una nuova partita attraverso un
	 * pop-up di conferma. Se il giocatore sceglie "Yes", il gioco viene resettato.
	 * Se sceglie "No", il gioco termina.
	 */
	public void chiediNuovaPartita() {
		int risposta = JOptionPane.showConfirmDialog(
				null, 
				"Vuoi giocare di nuovo?", 
				"Nuova partita", 
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE);

		if (risposta == JOptionPane.YES_OPTION) {
			controller.nuovaPartita();
			frame.dispose();
			new MenuPage();
		} else if (risposta == JOptionPane.NO_OPTION) {
			System.exit(0);
		}
	}
}