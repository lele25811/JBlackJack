package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import model.Player;
import model.TavoloDaGioco;

@SuppressWarnings("deprecation")
public class ActionPlayerPanel extends JPanel implements ActionListener, Observer{

	private MyButton cartaButton;
	private MyButton staiButton;
	private MyButton raddoppiaButton;
	private MyButton dividiButton;
	private TavoloDaGioco tavoloDaGioco;
	
	public ActionPlayerPanel(TavoloDaGioco tavoloDaGioco) {
		this.tavoloDaGioco = tavoloDaGioco;
		
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
			cartaButton.setEnabled(false);
			staiButton.setEnabled(false);
			raddoppiaButton.setEnabled(false);
			dividiButton.setEnabled(false);
			tavoloDaGioco.playerFinishedTurn();
			System.out.println("Sto bene cosi");
		}
		if(e.getSource() == raddoppiaButton) {
			System.out.println("Raddoppio");
		}
		if(e.getSource() == dividiButton) {
			System.out.println("Divido");
		}
		
	}

	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof String) {
			String action = (String) arg;
			if (action.equals("turnoPlayer")) {
				cartaButton.setEnabled(true);
				staiButton.setEnabled(true);
				raddoppiaButton.setEnabled(true);
				dividiButton.setEnabled(true);
			}
		}

	}
	
}
