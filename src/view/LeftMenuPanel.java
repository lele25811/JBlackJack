package view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;

public class LeftMenuPanel extends JPanel{
	
	private Frame frame;
	
	private JButton playButton;
	private JLabel botLabel;
	private JRadioButton oneBot;
	private JRadioButton twoBot;
	private JRadioButton threeBot;
	
	// Parte sinistra del pannello del menu
	public LeftMenuPanel(Frame frame) {
		
		this.frame = frame;
		
		setPreferredSize(new Dimension(500, getHeight()));
		
		TitledBorder border = BorderFactory.createTitledBorder("Menu");
        border.setTitleJustification(TitledBorder.CENTER); // Centra il titolo sul bordo
        setBorder(border);
        
        playButton = new JButton("PLAY");
        
        botLabel = new JLabel("Choose how many bots:");
        oneBot = new JRadioButton("1");
        oneBot.setActionCommand("1");
        twoBot = new JRadioButton("2");
        twoBot.setActionCommand("2");
        threeBot = new JRadioButton("3");
        threeBot.setActionCommand("3");
        
        ButtonGroup group = new ButtonGroup();
        group.add(oneBot);
        group.add(twoBot);
        group.add(threeBot);
        threeBot.setSelected(true);
        
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = gbc.CENTER;
        gbc.insets = new Insets(10, 10, 10, 10);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(playButton, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(botLabel, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(oneBot, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(twoBot, gbc);
        
        gbc.gridx = 2;
        gbc.gridy = 2;
        add(threeBot, gbc);
        
        playButton.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String bot = group.getSelection().getActionCommand();
				System.out.println("HAI SCELTO DI GIOCARE CONTRO "+bot+" BOT");
				frame.showPanel("gamePanel");
				
			}
		});
        
	}

}
