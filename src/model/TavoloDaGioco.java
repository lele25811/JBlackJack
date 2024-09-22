package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import javax.swing.SwingUtilities;

/*
 * Classe che modella le regole di gioco, le mani, i punti e la logica
 */
@SuppressWarnings("deprecation")
public class TavoloDaGioco extends Observable{
	
	// punteggio per mano, [player,...,mazziere]
	private static TavoloDaGioco tavoloDaGiocoInstance;
	
	private int[] punti;
	private MazzoDaGioco mazzo;
	private ArrayList<Player> giocatori = new ArrayList<Player>();
	private BlackJackPlayer player;
	private int currentPlayerIndex;
	// Oggetto per la sincronizzazione dei turni
	private final Object lock = new Object();
	private Random random = new Random();
	private Thread provaT;
	private Database db;
	
	public static TavoloDaGioco getInstance() {
		if(tavoloDaGiocoInstance == null) tavoloDaGiocoInstance = new TavoloDaGioco();
		return tavoloDaGiocoInstance;
	}
	
	private TavoloDaGioco() {
		mazzo = new MazzoDaGioco();
		db = db.getIstance();
	}
		
	public Player getPlayer() {
		return player;
	}
	
	public Player getBanco() {
		return giocatori.get(giocatori.size()-1);
	}
	
	public Player getBot1() {
		return giocatori.get(0);
	}
	
	public Player getBot2() {
		return giocatori.get(2);
	}
	
	
	public void addPlayer(BlackJackPlayer player) {
		this.player = player;
		System.out.println("E' STATO AGGIUNTO IL PLAYER AL TAVOLO "+player.getNickname());
	}
	
	private void inizializzaPunti(int size) {
		punti = new int[size];
		for(int i = 0; i<size; i++) {
			punti[0] = 0;
		}
	}
	
	public void addBot(String nBot) {
		BlackJackBot banco = new BlackJackBot("Banco", "avatarBanco", true);
		BlackJackBot franco;
		BlackJackBot baldassarre;
		switch(nBot) {
			case "1":
				giocatori.addAll(Arrays.asList(player, banco));
				break;
			case "2":
				franco = new BlackJackBot("Franco", "avatarFranco", false);
				giocatori.addAll(Arrays.asList(franco, player, banco));
				break;
			case "3":
				baldassarre = new BlackJackBot("Baldassarre", "avatarBaldassare", false);
				franco = new BlackJackBot("Franco", "avatarFranco", false);
				giocatori.addAll(Arrays.asList(franco, player, baldassarre, banco));
				break;
			default: 
				baldassarre = new BlackJackBot("Baldassarre", "avatarBaldassare", false);
				franco = new BlackJackBot("Franco", "avatarFranco", false);
				giocatori.addAll(Arrays.asList(franco, player, baldassarre, banco));
				break;
		}
		currentPlayerIndex = 0;
		inizializzaPunti(giocatori.size());
	}
	
	public void provaStampa() {
		for(Player p: giocatori) {
			System.out.println(p.toString());
			System.out.println(p.getMano());
			int[] puntiMano = p.getValoreManoIniziale();
			for(int i=0; i<puntiMano.length; i++) {
				System.out.println(puntiMano[i]);
			}
		}
	}
	
	// metodo di inizio gioco
	public void startGame() {
		distribuisciCarteIniziali();
	}

	private void turnazione() {
		while(hasNext()) {
			System.out.println("Giocatore attuale "+currentPlayerIndex+" su "+giocatori.size()+"-1");
			if(currentPlayerIndex == giocatori.size()) {
				break;
			}
			Player p = getCurrentPlayer();
			if(p instanceof BlackJackBot) {
				System.out.println("MODEL.È il turno di bot, "+p.getNickname());
				turnoBot((BlackJackBot) p);
				setChanged();
				//notifyObservers("FineTurno");
				notifyObservers(new UpdateEvent("FineTurno", p));
			}
			if(p instanceof BlackJackPlayer) {
				((BlackJackPlayer) p).addPartita();
				System.out.println("MODEL.È il turno di Player, "+p.getNickname());
				turnoPlayer();
				waitForPlayerTurn();
			}
			nextPlayer();
		}
		System.out.println("Turnazione Terminata");
		finePartita();
	}
	
	private void waitForPlayerTurn() {
		synchronized (lock) {
            try {
                lock.wait();  // Attende che il turno del giocatore sia completato
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
	}
	
	public void playerFinishedTurn() {
        synchronized (lock) {
        	System.out.println("Turno finito");
            lock.notify();  // Notifica che il turno del giocatore è finito
        }
    }

	// Da modificare per l assegnazione punti
	public void setPuntiTavolo(int punteggioFinale) {
		punti[currentPlayerIndex] = punteggioFinale;
	}
	
	public int getPunti(Player p) {
		return punti[giocatori.indexOf(p)];
	}

	// Entry point per il turno del bot
	private int turnoBot(BlackJackBot bot) {
		boolean raddoppio = false;
		int punti = bot.getPunti();
		while(true) {
			//System.out.println("punti Attuali: "+punti);
			if(isSballato(punti)) {
				System.out.println("Ho sballato con "+punti);
				bot.setPunti(punti);
				setPuntiTavolo(punti);
				return punti;
			}else if(punti >= 18 || raddoppio) {
				System.out.println("Sto con "+punti);
				bot.setPunti(punti);
				setPuntiTavolo(punti);
				return punti;
			}else if(punti < 18) {
				int scelta = 0;
				if(!bot.getIsBanco()) {
					scelta = random.nextInt(2);
				}
				switch(scelta) {
					case 0: getCardBot(bot);
							break;
					case 1: {
						getCardBot(bot);
						System.out.println("Raddoppio");
						raddoppio = true;
						break;
					}
				}
			}
			punti = punti + bot.updatePunti();
			bot.setPunti(punti);
			punti = bot.getPunti();
		}
	}

	private void distribuisciCarteIniziali() { 
		 provaT = new Thread(() -> {
			try {
				for (int i = 0; i < 2; i++) {  // Due carte per ciascun giocatore
					for (Player p : giocatori) {
						Thread.sleep(1000);  // Attesa di 1 secondi tra una carta e l'altra
						p.addCarta(mazzo.prossimaCarta());
						setChanged();
						//notifyObservers("DistribuisciCarteIniziali");  // Notifica la distribuzione della carta
						notifyObservers(new UpdateEvent("DistribuisciCarteIniziali", p));
					}
				}
				System.out.println("Distribuzione della carte completata, La partita può iniziare");
				setChanged();
				//notifyObservers("DistribuzioneTerminata");
				notifyObservers(new UpdateEvent("DistribuzioneTerminata", player));
				turnazione();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		provaT.start();
	}
	
	public int getNumeroGiocatori() {
		return giocatori.size();
	}
	
	public void getCarte() {
		mazzo.mazzoStampa();
	}
	
	public void turnoPlayer() {
		setChanged();
		//notifyObservers("TurnoPlayer");
		notifyObservers(new UpdateEvent("TurnoPlayer", player));
	}
	
	/*
	 * Gestione della turnazione
	 */

	// Ritorna il giocatore corrente
	public Player getCurrentPlayer() {
		return giocatori.get(currentPlayerIndex);
	}
	
	public void nextPlayer() {
		if (currentPlayerIndex <= giocatori.size()) {
            currentPlayerIndex++;
        } else {
            // Tutti i giocatori hanno giocato
            currentPlayerIndex = 0; // Reset currentPlayer 
        }
		System.out.println("Prossimo player: "+currentPlayerIndex);
	}
	
	public boolean hasNext() {
		return currentPlayerIndex < giocatori.size();
	}
	
	/*
	 * TODO: GESTIONE DELLE MOSSE DEL PLAYER E DI CONSEGUENZA DEI BOT
	 */
	
	public void getCard(Player p) {
		System.out.println("Chiedo carta");
		p.addCarta(mazzo.prossimaCarta());
		System.out.println(p.getMano());
		setChanged();
		//notifyObservers("NuovaCarta");
		notifyObservers(new UpdateEvent("NuovaCarta", p));
	}
	
	public void getCardBot(Player p) {
		System.out.println("Chiedo carta");
		p.addCarta(mazzo.prossimaCarta());
		System.out.println(p.getMano());
		try {
			Thread.sleep(2000);
		}catch (Exception e) {
			e.printStackTrace();
		}
		setChanged();
        //notifyObservers("NuovaCartaBot");
        notifyObservers(new UpdateEvent("NuovaCartaBot", p));
	}
	
	public void stay() {
		nextPlayer();
	}
	
	public boolean isSballato(int punto) {
		return punto > 21;
	}
	
	/*
	 *  Gestione fine della partita
	 *  punti tiene i punteggi (manca player) in sequenza fai confronti vari
	 */
	public void finePartita() {
		boolean vittoriaPlayer = false;
		int puntiBanco = 0;
		ArrayList<Player> giocatoriVincitori = new ArrayList<>();
		if(punti == null) {
			inizializzaPunti(giocatori.size());
		}
		puntiBanco = punti[giocatori.size()-1];
		System.out.println("Il banco ha totalizzato: "+puntiBanco);
		
		if(puntiBanco > 21) {
			System.out.println("Il banco ha sballato");
			for(int i=0; i<giocatori.size()-1; i++) {
				if(punti[i] <= 21) {
					giocatoriVincitori.add(giocatori.get(i));
				}
			}
		}else {
			System.out.println("Il banco non ha sballato");
			for(int i=0; i<giocatori.size()-1; i++) {
				if(punti[i] <= 21 && punti[i] > puntiBanco) {
					giocatoriVincitori.add(giocatori.get(i));
				}
			}
		}
		if (!giocatoriVincitori.isEmpty()) {
	        System.out.println("Giocatori che hanno vinto: ");
	        for(Player p: giocatoriVincitori) {
	        	System.out.println(p.getNickname());
	        	if(p instanceof BlackJackPlayer) {
	        		((BlackJackPlayer) p).addVittoria();
	        		vittoriaPlayer = true;
	        	}
	        }
	    }else {
	        System.out.println("Il banco ha vinto.");
	    }
		if(vittoriaPlayer) {
			System.out.println("Partito Vittoria messaggio poppUp");
			setChanged();
			//notifyObservers("Vittoria");
			notifyObservers(new UpdateEvent("Vittoria", player));
			
		}else if(!vittoriaPlayer){
			System.out.println("Partito Sconfitta messaggio poppUp");
			setChanged();
			//notifyObservers("Sconfitta");
			notifyObservers(new UpdateEvent("Sconfitta", player));
		}
		System.out.println("Aggiornamento db...");
		db.updatePlayer(player);
	}

	public void resetPartita() {
		for(Player player: giocatori) {
			player.resetMano();
		}
		currentPlayerIndex = 0;
		giocatori.clear();
		System.out.println("Giocatori dopo il reset "+giocatori);
	}

	
}
