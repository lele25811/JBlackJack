package model;

import java.util.ArrayList;

/**
 * La classe BlackJackBot rappresenta un bot che gioca a BlackJack, estendendo la classe Player.
 * Il bot può essere designato come "banco" e possiede logiche per la gestione dei punteggi e del valore dell'asso.
 */
public class BlackJackBot extends Player{
	
	/**
	 * isBanco definisce se il BlackJackBot è il banco oppure no.
	 */
    private boolean isBanco;
    
    /**
     * punti definisce i punti attuali del bot.
     */
    private int punti;
    
    /**
     * punteggiDisponibili definisce i punteggi possibili per la mano corrente
     * che il bot può scegliere.
     */
    private ArrayList<Integer> punteggiDisponibili;
    
    /**
     * assoScegliValore definisce se il bot ha scelto il valore 11 per l'asso oppure no.
     */
    private boolean assoScegliValore;

    /**
     * Costruttore della classe BlackJackBot.
     * @param nickname nome del bot
     * @param avatar avatar del bot 
     * @param isBanco se il bot è il banco
     */
    public BlackJackBot(String nickname, String avatar, boolean isBanco) {
        super(nickname, avatar);
        this.isBanco = isBanco;
        this.punteggiDisponibili = new ArrayList<>();
        this.assoScegliValore = false;
    }

    /**
     * Restituisce se il bot è il banco.
     * @return true se il bot è il banco, false altrimenti
     */
    public boolean getIsBanco() {
        return isBanco;
    }

    /**
     * Metodo privato che sceglie il punteggio migliore possibile per il bot,
     * considerando se utilizzare il valore 1 o 11 per l'asso.
     */
    private void sceltaPunteggio() {
        int punteggioMinimo = punteggiDisponibili.get(0);
        int punteggioMassimo = punteggiDisponibili.get(punteggiDisponibili.size() - 1);

        if (punteggiDisponibili.size() < 2) {
            punti = punteggioMinimo;
        } else {
            if (punteggioMassimo <= 21) {
                punti = punteggioMassimo;
                assoScegliValore = true; 
            } else {
                punti = punteggioMinimo;
                assoScegliValore = false;
            }
        }
    }

    /**
     * Restituisce il punteggio scelto in base alla mano corrente del bot.
     * @param valoriMano array contenente i possibili valori della mano del bot
     * @return il punteggio scelto dal bot
     */
    public int getSceltaPunti(int[] valoriMano) {
        punteggiDisponibili.clear();
        for (int valore : valoriMano) {
            punteggiDisponibili.add(valore);
        }
        sceltaPunteggio();
        return punti;
    }

    /**
     * Restituisce il punteggio attuale del bot.
     * @return il punteggio del bot
     */
    public int getPunti() {
        return punti;
    }

    /**
    * Imposta il punteggio del bot.
    * @param punti il punteggio da assegnare al bot
    */
    public void setPunti(int punti) {
        this.punti = punti;
    }

    /**
     * Aggiorna il punteggio del bot, considerando l'ultima carta pescata.
     * Se il bot ha scelto di considerare l'asso come 11 e il nuovo punteggio supera 21,
     * il valore dell'asso viene ridotto a 1.
     */
    public void aggiornaPunti() {
        int ultimaCartaValore = getLastCard().getValore();

        if (assoScegliValore && (punti + ultimaCartaValore) > 21) {
            punti = punti - 10; 
            assoScegliValore = false; 
        }
        punti += ultimaCartaValore; 
    }
    
    /**
     * Metodo per ottenere il valore dell'ultima carta pescata dal bot.
     * @return il valore dell'ultima carta pescata
     */
    public int updatePunti() {
		return getLastCard().getValore();
	}
}