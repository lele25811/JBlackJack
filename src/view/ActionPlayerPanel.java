package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import controller.GameController;
import model.BlackJackPlayer;
import model.TavoloDaGioco;
import model.UpdateEvent;

@SuppressWarnings("deprecation")
public class ActionPlayerPanel extends JPanel implements Observer{

	private JButton cartaButton;
	private JButton staiButton;
	private JButton raddoppiaButton;
	private JButton dividiButton;
	private Frame frame;
	private GameController controller;
	
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
		raddoppiaButton = new JButton("Double");
		dividiButton = new JButton("Split");
		
		cartaButton.setEnabled(false);
		staiButton.setEnabled(false);
		raddoppiaButton.setEnabled(false);
		dividiButton.setEnabled(false);
		
		add(cartaButton);
		add(staiButton);
		add(raddoppiaButton);
		add(dividiButton);
		
	}
	
	public JButton getCartaButton() {
		return cartaButton;
	}

	public JButton getStaiButton() {
		return staiButton;
	}


	public JButton getRaddoppiaButton() {
		return raddoppiaButton;
	}


	public JButton getDividiButton() {
		return dividiButton;
	}

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
				//if(tavoloDaGioco.carteUguali((BlackJackPlayer)data)) {
				if(controller.isCarteUguali((BlackJackPlayer) data)) {
					dividiButton.setEnabled(true);
				}
			}
		}
	}		        
	
	public void passaTurno(int punteggio) {
		cartaButton.setEnabled(false);
		staiButton.setEnabled(false);
		raddoppiaButton.setEnabled(false);
		dividiButton.setEnabled(false);
		controller.passaTurno(punteggio);
	}

	public void chiediNuovaPartita() {
		int risposta = JOptionPane.showConfirmDialog(
				null, 
				"Vuoi giocare di nuovo?", 
				"Nuova partita", 
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE);

		if (risposta == JOptionPane.YES_OPTION) {
			// Reset del gioco e inizio nuova partita
			System.out.println("Nuova Partita");
			//tavoloDaGioco.resetPartita();
			//playerPanel.resetPartita();
			//gamePage.resetGame();
			controller.nuovaPartita();
			frame.dispose();
			new MenuPage();
		} else if (risposta == JOptionPane.NO_OPTION) {
			System.exit(0);
		}
	}

}

