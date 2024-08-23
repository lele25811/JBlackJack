package view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;

public class MenuPanel extends JPanel{
	
	private Frame frame;
	
	private LeftMenuPanel leftMenuPanel;
	
	public MenuPanel(Frame frame) {

		this.frame = frame;
		
		setBackground(Color.RED);
		
		leftMenuPanel = new LeftMenuPanel(frame);
		
		setLayout(new BorderLayout());
		
		add(leftMenuPanel, BorderLayout.WEST);
		
		
	}
	
}