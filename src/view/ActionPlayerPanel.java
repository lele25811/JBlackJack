package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import model.Player;
import model.TavoloDaGioco;
import model.UpdateEvent;

@SuppressWarnings("deprecation")
public class ActionPlayerPanel extends JPanel implements ActionListener, Observer{

	private MyButton cartaButton;
	private MyButton staiButton;
	private MyButton raddoppiaButton;
	private MyButton dividiButton;
	private TavoloDaGioco tavoloDaGioco;
	private PlayerPanel playerPanel;
	private Frame frame;
	private GamePage gamePage;
	
	public ActionPlayerPanel(TavoloDaGioco tavoloDaGioco, PlayerPanel playerPanel, Frame frame, GamePage gamePage) {
		this.frame = frame;
		this.tavoloDaGioco = tavoloDaGioco;
		this.playerPanel = playerPanel;
		this.gamePage = gamePage;
		
		TitledBorder border = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.RED, 2), "Azioni di gioco");
	    border.setTitleColor(Color.RED);
	    setBorder(border);
	    
	    setBackground(new Color(120, 0, 0, 0));
		setOpaque(false);
		
		cartaButton = new MyButton("Card", this);
		staiButton = new MyButton("Stay", this);
		raddoppiaButton = new MyButton("Double", this);
		dividiButton = new MyButton("Split", this);
		
		cartaButton.setEnabled(false);
		staiButton.setEnabled(false);
		raddoppiaButton.setEnabled(false);
		dividiButton.setEnabled(false);
		
		add(cartaButton);
		add(staiButton);
		add(raddoppiaButton);
		add(dividiButton);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == cartaButton) {
			tavoloDaGioco.getCard(tavoloDaGioco.getPlayer());
			System.out.println("Chiedo carta");
		}
		if(e.getSource() == staiButton) {
			playerPanel.passaTurno(false);
			System.out.println("Passo turno");
		}
		if(e.getSource() == raddoppiaButton) {
			tavoloDaGioco.getDouble(tavoloDaGioco.getPlayer());
			System.out.println("Raddoppio");
			
		}
		if(e.getSource() == dividiButton) {
			System.out.println("Divido");
		}
		
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
				dividiButton.setEnabled(true);
			}
		}		        
	}
	
	public void passaTurno(int punteggio) {
		cartaButton.setEnabled(false);
		staiButton.setEnabled(false);
		raddoppiaButton.setEnabled(false);
		dividiButton.setEnabled(false);
		tavoloDaGioco.setPuntiTavolo(punteggio);
		tavoloDaGioco.playerFinishedTurn();
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
			tavoloDaGioco.resetPartita();
			playerPanel.resetPartita();
			gamePage.resetGame();
			frame.dispose();
			new MenuPage(tavoloDaGioco);
		} else if (risposta == JOptionPane.NO_OPTION) {
			System.exit(0);
		}
	}

}
