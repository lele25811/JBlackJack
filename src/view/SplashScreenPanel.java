package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SplashScreenPanel extends JPanel {
    private Image img;
    private JButton playButton;

    public SplashScreenPanel() {
        // Carica e scala l'immagine
        ImageIcon icon = new ImageIcon("./src/graphics/BlackJack.png");
        img = icon.getImage().getScaledInstance(1280, 720, Image.SCALE_SMOOTH);

        // Imposta il layout nullo per posizionare il bottone manualmente
        setLayout(null);

        // Crea il bottone "Gioca"
        playButton = new JButton("Play");
        playButton.setBounds(590, 600, 100, 40); // Posiziona il bottone

        // Aggiungi il bottone al pannello
        add(playButton);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Disegna l'immagine di sfondo
        g.drawImage(img, 0, 0, this);
    }

    public void addPlayButtonActionListener(ActionListener listener) {
        // Permette di aggiungere un ActionListener esternamente
        playButton.addActionListener(listener);
        System.out.println("CLICK");
    }
}
