package model;

import java.util.ArrayList;
import java.util.Random;

public class BlackJackBot extends Player{
	
    private boolean isBanco;
    private Random random;
    private int punti;
    private ArrayList<Integer> punteggiDisponibili;
    private boolean assoScegliValore;

    public BlackJackBot(String nickname, String avatar, boolean isBanco) {
        super(nickname, avatar);
        this.isBanco = isBanco;
        this.random = new Random();
        this.punteggiDisponibili = new ArrayList<>();
        this.assoScegliValore = false; // Indica se è stato scelto un valore per l'asso
    }

    public boolean getIsBanco() {
        return isBanco;
    }

    // Metodo per scegliere il punteggio considerando l'asso
    private void sceltaPunteggio() {
        int punteggioMinimo = punteggiDisponibili.get(0);
        int punteggioMassimo = punteggiDisponibili.get(punteggiDisponibili.size() - 1);

        if (punteggiDisponibili.size() < 2) {
            punti = punteggioMinimo;
        } else {
            if (punteggioMassimo <= 21) {
                punti = punteggioMassimo;
                assoScegliValore = true; // Se scegli 11, memorizzi la scelta
            } else {
                punti = punteggioMinimo;
                assoScegliValore = false; // Se scegli 1, memorizzi la scelta
            }
        }
    }

    public int getSceltaPunti(int[] valoriMano) {
        punteggiDisponibili.clear();
        for (int valore : valoriMano) {
            punteggiDisponibili.add(valore);
        }
        sceltaPunteggio();
        return punti;
    }

    public int getPunti() {
        return punti;
    }

    public void setPunti(int punti) {
        this.punti = punti;
    }

    public void aggiornaPunti() {
        int ultimaCartaValore = getLastCard().getValore();

        // Se è stato scelto 11 come valore dell'asso, riduci il punteggio se sfori 21
        if (assoScegliValore && (punti + ultimaCartaValore) > 21) {
            punti = punti - 10; // Trasforma l'asso da 11 a 1
            assoScegliValore = false; // L'asso ora vale 1
        }

        punti += ultimaCartaValore; // Aggiungi il valore dell'ultima carta pescata
        System.out.println("Bot: " + getNickname() + " Punti attuali: " + punti);
    }
    
    public int updatePunti() {
		return getLastCard().getValore();
	}
}