package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;
import java.util.Random;

/*
 * Classe che modella le regole di gioco, le mani, i punti e la logica
 */
@SuppressWarnings("deprecation")
public class TavoloDaGioco extends Observable{
	
	// punteggio per mano, [player,...,mazziere]
	private static TavoloDaGioco tavoloDaGiocoInstance;
	
	// non sicuro se veramente usata.
	private int[] punti;
	private MazzoDaGioco mazzo;
	private ArrayList<Player> giocatori = new ArrayList<Player>();
	private BlackJackPlayer player;
	private int currentPlayerIndex;
	// Oggetto per la sincronizzazione dei turni
	private final Object lock = new Object();
	
	public static TavoloDaGioco getInstance() {
		if(tavoloDaGiocoInstance == null) tavoloDaGiocoInstance = new TavoloDaGioco();
		return tavoloDaGiocoInstance;
	}
	
	private TavoloDaGioco() {
		mazzo = new MazzoDaGioco();
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
	
	private void addPunti(int size) {
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
		addPunti(giocatori.size());
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
		/*
		new Thread(() -> {
	        turnazione();  // Esegue il ciclo di gioco su un thread separato
	    }).start();
		*/
	}

	private void turnazione() {
		while(hasNext()) {
			System.out.println("Giocatore attuale "+currentPlayerIndex+" su "+giocatori.size()+"-1");
			if(currentPlayerIndex == giocatori.size()-1) {
				break;
			}
			Player p = getCurrentPlayer();
			if(p instanceof BlackJackBot) {
				//turnoBot(p);
				System.out.println("MODEL.È il turno di bot, "+p.getNickname());
				int punti = provaTurnoBot(p);
				setPunti(currentPlayerIndex, punti);
				nextPlayer();
			}
			if(p instanceof BlackJackPlayer) {
				System.out.println("MODEL.È il turno di Player, "+p.getNickname());
				turnoPlayer();
				waitForPlayerTurn();
				nextPlayer();
			}
		}
	}
	
	private void waitForPlayerTurn() {
		synchronized (lock) {
            try {
            	System.out.println("Sono in attesa");
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

	private void setPunti(int currentPlayerIndex, int p) {
		System.out.println(currentPlayerIndex+ "ha totalizzato: "+ p);
		punti[currentPlayerIndex] = p;
	}
	
	public int getPunti(Player p) {
		return punti[giocatori.indexOf(p)];
	}

	private int provaTurnoBot(Player p) {
		boolean isBanco = ((BlackJackBot) p).getIsBanco();
		boolean isRaddoppio = false;
		Random random = new Random();
		int valoreAggiornato = 0;
		int[] valori = p.getValoreManoIniziale();
		while(true) {
			
			for(int i=0; i<valori.length; i++) {
				System.out.println(valori[i]);
			}
			if(valoreAggiornato > 21) {
				System.out.println("###HO SBALLATO CON "+valoreAggiornato+"###");
				return 0;
			}
			// gestione uscita raddoppio
			if(isRaddoppio) {
				System.out.println("Il raddoppio mi ha lasciato:");
				System.out.println(valori[0]);
				return valori[0];
			}
			if(valori.length > 1 && valori[1] > 21) {
				valori = new int[]{valori[0]};
			}
			if(valori.length > 1) {
				if(valori[1] >= 18) {
					System.out.println("###STO CON "+valori[1] +"###");
					return valori[1];
				}else {
					int sceltaCarta = random.nextInt(2); // scelta valore[0] o [1]
					int sceltaMossa = random.nextInt(2); // scelta mossa (carta o raddoppio)
					if(isBanco) {
						sceltaMossa = 0;
					}
					switch(sceltaMossa) {
						case 0:
							valoreAggiornato = valori[sceltaCarta];
							System.out.println("###CARTA CON "+valori[sceltaCarta] +"###");
							carta(valori[sceltaCarta], p);
							break;
						case 1:
							valoreAggiornato = valori[sceltaCarta];
							System.out.println("###RADDOPPIO CON "+valori[sceltaCarta] +"###");
							isRaddoppio = true;
							raddoppio(valori[sceltaCarta], p);
							break;
						default: carta(valori[0], p);
					}
				}
			//Controllo valori singoli
			}else if(valori.length == 1) {
				if(valori[0] > 21) {
					System.out.println("###HO SBALLATO CON"+valori[0]+"###");
					return 0;
				}
				if(valori[0] >= 18) {
					System.out.println("###STO CON "+valori[0] +"###");
					return valori[0];
				}else {
					int sceltaMossa = random.nextInt(2);
					if(isBanco) {
						sceltaMossa = 0;
					}
					switch(sceltaMossa) {
					case 0:
						valoreAggiornato = valori[0];
						carta(valori[0], p);
						System.out.println("###CARTA CON "+valori[0] +"###");
						break;
					case 1:
						valoreAggiornato = valori[0];
						// TODO: definisci operazioni
						isRaddoppio = true;
						raddoppio(valori[0], p);
						System.out.println("###RADDOPPIO CON "+valori[0] +"###");
						break;
					default: carta(valori[0], p);
					}
				}
			}
			valori = p.getValoreMano(valoreAggiornato);
		}
	}

	private void raddoppio(int valori, Player p) {
		p.addCarta(mazzo.prossimaCarta());
		System.out.println("Mano attuale "+p.getMano());
		setChanged();
		notifyObservers("ChiediCarta");
	}

	private void carta(int valori, Player p) {
		p.addCarta(mazzo.prossimaCarta());
		System.out.println("Mano attuale "+p.getMano());
		setChanged();
		notifyObservers("ChiediCarta");
	}

	private void distribuisciCarteIniziali() { 
		new Thread(() -> {
			try {
				for (int i = 0; i < 2; i++) {  // Due carte per ciascun giocatore
					for (Player p : giocatori) {
						Thread.sleep(1000);  // Attesa di 1 secondi tra una carta e l'altra
						p.addCarta(mazzo.prossimaCarta());
						setChanged();
						notifyObservers("DistribuisciCarteIniziali");  // Notifica la distribuzione della carta
					}
				}
				System.out.println("Distribuzione della carte completata, La partita può iniziare");
				setChanged();
				notifyObservers("DistribuzioneTerminata");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();
	}
	
	public int getNumeroGiocatori() {
		return giocatori.size();
	}
	
	public static boolean lastIsAsso(Player p) {
		boolean isAsso = p.getMano().stream().skip(p.getMano().size() -1).anyMatch(carta -> "Asso".equals(carta.getStringValore()));
		return isAsso;
	}
	
	public void getCarte() {
		mazzo.mazzoStampa();
	}
	
	public void turnoPlayer() {
		setChanged();
		notifyObservers("turnoPlayer");
	}
	
	/*
	 * Gestione della turnazione
	 */

	// Ritorna il giocatore corrente
	public Player getCurrentPlayer() {
		return giocatori.get(currentPlayerIndex);
	}
	
	public void nextPlayer() {
		if (currentPlayerIndex < giocatori.size() - 1) {
            currentPlayerIndex++;
        } else {
            // Tutti i giocatori hanno giocato
            currentPlayerIndex = 0; // Reset currentPlayer 
        }
	}
	
	public boolean hasNext() {
		return currentPlayerIndex >=0;
	}
	
	/*
	 * TODO: GESTIONE DELLE MOSSE DEL PLAYER E DI CONSEGUENZA DEI BOT
	 */
	
	public void getCard(Player p) {
		p.addCarta(mazzo.prossimaCarta());
		setChanged();
		notifyObservers("NuovaCarta");
	}
	
	public void stay() {
		nextPlayer();
		// corretto? credo di no
		//turnazione();
	}
	
}
