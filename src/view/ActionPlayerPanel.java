package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class ActionPlayerPanel extends JPanel implements ActionListener{

	private MyButton cartaButton;
	private MyButton staiButton;
	private MyButton raddoppiaButton;
	private MyButton dividiButton;
	
	public ActionPlayerPanel() {
		
		TitledBorder border = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.RED, 2), "Azioni di gioco");
	    border.setTitleColor(Color.RED);
	    setBorder(border);
	    
	    setBackground(new Color(120, 0, 0, 0));
		setOpaque(false);
		
		cartaButton = new MyButton("Card", this);
		staiButton = new MyButton("Stay", this);
		raddoppiaButton = new MyButton("Double", this);
		dividiButton = new MyButton("Split", this);
		
		add(cartaButton);
		add(staiButton);
		add(raddoppiaButton);
		add(dividiButton);
		
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == cartaButton) {
			System.out.println("Chiedo carta");
		}
		if(e.getSource() == staiButton) {
			System.out.println("Sto bene cosi");
		}
		if(e.getSource() == raddoppiaButton) {
			System.out.println("Raddoppio");
		}
		if(e.getSource() == dividiButton) {
			System.out.println("Divido");
		}
		
	}
	
}
