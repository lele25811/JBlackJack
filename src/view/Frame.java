package view;

import javax.swing.JFrame;

public class Frame extends JFrame{
	
	public Frame() {
		this.setTitle("JTrash");
        
        // Chiude l'applicazione quando il frame viene chiuso
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Imposta le dimensioni del frame
        this.setSize(1280, 720);
        
        // Impedisce il ridimensionamento del frame
        this.setResizable(false);
        
        // Posiziona il frame al centro dello schermo
        this.setLocationRelativeTo(null);
        
        // Rende il frame visibile
        //this.setVisible(true);
	}
}
