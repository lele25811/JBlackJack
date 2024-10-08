package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;
import java.util.Random;

/**
 * La classe TavoloDaGioco modella le regole di gioco, la gestione dei turni, delle mani e dei punti 
 * per una partita di BlackJack. Implementa la logica per distribuire carte, gestire i turni dei giocatori, 
 * inclusi bot e mazziere, e gestire lo stato finale della partita.
 */
@SuppressWarnings("deprecation")
public class TavoloDaGioco extends Observable{
	
	/**
	 * tavoloDaGiocoInstance unica istanza della classe TavoloDaGioco.
	 */
	private static TavoloDaGioco tavoloDaGiocoInstance;
	
	/**
	 * Punti accumulati per ogni giocatore in una partita.
	 */
	private int[] punti;
	
	/**
	 * Mazzo di carte utilizzato nel gioco.
	 */
	private MazzoDaGioco mazzo;
	
	/**
	 * Lista dei giocatori (inclusi player, bot e mazziere).
	 */
	private ArrayList<Player> giocatori = new ArrayList<Player>();
	
	/**
	 * Istanza del giocatore umano.
	 */
	private BlackJackPlayer player;
	
	/**
	 * L'indice del giocatore corrente nel turno.
	 */
	private int currentPlayerIndex;
	
	/**
	 * Oggetto per la sincronizzazione dei turni tra i giocatori.
	 */
	private final Object lock = new Object();
	
	/**
	 * Oggetto Random per le decisioni dei bot.
	 */
	private Random random = new Random();
	
	/**
	 * Thread per la gestione della distribuzione delle carte e dei turni.
	 */
	private Thread threadGioco;
	
	/**
	 * Database per la gestione dei dati del giocatore.
	 */
	private Database db;
	
	/**
	 * Flag per gestire lo stato di split delle carte.
	 */
	private boolean isSplit = false;
	
	/**
	 * Restituisce l'istanza singleton di TavoloDaGioco.
	 * @return l'unica istanza di TavoloDaGioco
	 */
	public static TavoloDaGioco getInstance() {
		if(tavoloDaGiocoInstance == null) tavoloDaGiocoInstance = new TavoloDaGioco();
		return tavoloDaGiocoInstance;
	}
	
	/**
	 * Costruttore privato che inizializza il mazzo e il database.
	 */
	private TavoloDaGioco() {
		mazzo = new MazzoDaGioco();
		db = Database.getIstance();
	}
		
	/**
	 * Metodo che ritorna il giocatore umano.
	 * @return il giocatore umano
	 */
	public Player getPlayer() {
		return player;
	}
	
	/**
	 * Metodo che ritorna il banco.
	 * @return il bot che fa da banco
	 */
	public Player getBanco() {
		return giocatori.get(giocatori.size()-1);
	}
	
	/**
	 * Metodo che ritorna il bot numero 1.
	 * @return il bot numero 1
	 */
	public Player getBot1() {
		return giocatori.get(0);
	}
	
	/**
	 * Metodo che ritorna il bot numero 2.
	 * @return il bot numero 2
	 */
	public Player getBot2() {
		return giocatori.get(2);
	}
	
	/**
	 * Metodo che aggiunge il giocatore umano alla classe TavoloDaGioco.
	 * @param player il giocatore umano da aggiungere
	 */
	public void addPlayer(BlackJackPlayer player) {
		this.player = player;
	}
	
	/**
	 * Metodo che inizializza i punteggi dei giocatori a 0.
	 * @param size numero di giocatori
	 */
	private void inizializzaPunti(int size) {
		punti = new int[size];
		for(int i = 0; i<size; i++) {
			punti[0] = 0;
		}
	}
	
	/**
	 * Metodo che aggiunge i bot al tavolo in base al numero di giocatori scelto.
	 * @param nBot numero di bot da aggiungere (1,2 o 3)
	 */
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
	
	/**
	 * Metodo di inizio gioco
	 */
	public void startGame() {
		distribuisciCarteIniziali();
	}

	/**
	 * Metodo che gestisce il ciclo dei turni tra giocatori fino alla fine della partita.
	 */
	private void turnazione() {
		while(hasNext()) {
			if(currentPlayerIndex == giocatori.size()) {
				break;
			}
			Player p = getCurrentPlayer();
			if(p instanceof BlackJackBot) {
				try {
					Thread.sleep(1500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if(((BlackJackBot)p).getIsBanco()) {
					setChanged();
					notifyObservers(new UpdateEvent("TurnoBanco", p));
				}
				turnoBot((BlackJackBot) p);
				if(punti[currentPlayerIndex] > 21) {
					setChanged();
					notifyObservers(new UpdateEvent("Sballato", p));
				}else {
					setChanged();
					notifyObservers(new UpdateEvent("Passa Turno", p));
				}
			}
			if(p instanceof BlackJackPlayer) {
				((BlackJackPlayer) p).addPartita();
				setChanged();
				notifyObservers(new UpdateEvent("TurnoPlayer", player));
				waitForPlayerTurn();
			}
			nextPlayer();
		}
		finePartita();
	}
	
	/**
	 * Metodo che blocca il thread di gioco finchè il giocatore umano non termina il turno.
	 */
	private void waitForPlayerTurn() {
		synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
	}
	
	/**
	 * Metodo che notifica la fine del turno del giocatore.
	 */
	public void playerFinishedTurn() {
        synchronized (lock) {
            lock.notify();
        }
    }

	/**
	 * Metodo che assegna i punti finali ai giocatori.
	 * @param punteggioFinale il punteggio del giocatore
	 */
	public void setPuntiTavolo(int punteggioFinale) {
		punti[currentPlayerIndex] = punteggioFinale;
	}
	
	/**
	 * Metodo che restituisce il punteggio di un determinato giocatore.
	 * @param p il giocatore
	 * @return punteggio del giocatore
	 */
	public int getPunti(Player p) {
		return punti[giocatori.indexOf(p)];
	}

	/**
	 * Metodo che gestisce il turno di un bot.
	 * @param bot il bot che deve giocare
	 * @return il punteggio finale del bot
	 */
	private int turnoBot(BlackJackBot bot) {
		boolean raddoppio = false;
		int punti = bot.getPunti();
		while(true) {
			if(isSballato(punti)) {
				bot.setPunti(punti);
				setPuntiTavolo(punti);
				return punti;
			}else if(punti >= 18 || raddoppio) {
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
						getRaddoppioBot(bot);
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

	/**
	 * Metodo che distribuisce le carte iniziali a tutti i giocatori.
	 */
	private void distribuisciCarteIniziali() {
		threadGioco = new Thread(() -> {
			try {
				for (int i = 0; i < 2; i++) {
					for (Player p : giocatori) {
						Thread.sleep(1500);
						p.addCarta(mazzo.prossimaCarta());
						setChanged();
						notifyObservers(new UpdateEvent("DistribuisciCarteIniziali", p));
					}
				}
				setChanged();
				notifyObservers(new UpdateEvent("DistribuzioneTerminata", player));
				turnazione();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				e.printStackTrace();
			}
		});
		threadGioco.start();
	}
	
	/**
	 * Metodo che restituisce il numero di giocatori attualmente al tavolo.
	 * @return il numero di giocatori
	 */
	public int getNumeroGiocatori() {
		return giocatori.size();
	}
	
	/**
	 * Metodo che ritorna il giocatore corrente.
	 * @return il giocatore corrente
	 */
	public Player getCurrentPlayer() {
		return giocatori.get(currentPlayerIndex);
	}
	
	/**
	 * Metodo che passa il turno al giocatore successivo.
	 */
	public void nextPlayer() {
		if (currentPlayerIndex <= giocatori.size()) {
            currentPlayerIndex++;
        } else {
            currentPlayerIndex = 0;
        }
	}
	
	/**
	 * Metodo che verifica se ci sono ancora giocatori che devono giocare.
	 * @return true se ci sono giocatori che devono giocare, altrimenti false
	 */
	public boolean hasNext() {
		return currentPlayerIndex < giocatori.size();
	}
	
	/**
	 * Metodo che richiede una carta per il giocatore.
	 * @param p giocatore che richiede una carta
	 */
	public void getCard(Player p) {
		p.addCarta(mazzo.prossimaCarta());
		setChanged();
		notifyObservers(new UpdateEvent("NuovaCarta", p));
	}
	
	/**
	 * Metodo che richiede una carta per il bot.
	 * @param p il bot che richiede una carta
	 */
	private void getCardBot(Player p) {
		p.addCarta(mazzo.prossimaCarta());
		setChanged();
        notifyObservers(new UpdateEvent("Carta", p));
	}
	
	/**
	 * Metodo che richiede il raddoppio per il bot.
	 * @param p il bot che richiede una carta
	 */
	private void getRaddoppioBot(Player p) {
		p.addCarta(mazzo.prossimaCarta());
		setChanged();
        notifyObservers(new UpdateEvent("Raddoppio", p));
	}
	
	/**
	 * Metodo che passa al prossimo player.
	 */
	public void stay() {
		nextPlayer();
	}
	
	/**
	 * Metodo che richiede il raddoppio.
	 * @param p il giocatore che richiede il raddoppio
	 */
	public void getDouble(Player p) {
		p.addCarta(mazzo.prossimaCarta());
		setChanged();
		notifyObservers(new UpdateEvent("Raddoppio", p));
	}
	
	/**
	 * Metodo che richiede lo Split.
	 * @param p il giocatore che richiede lo Split
	 */
	public void getSplit(Player p) {
		isSplit = true;
		p.maniSplit();
		setChanged();
		notifyObservers(new UpdateEvent("Dividi", p));
	}
	
	/**
	 * Metodo che controlla se il punteggio è maggiore di 21.
	 * @param punto punteggio da controllare
	 * @return true se il punteggio è maggiore di 21, altrimenti false
	 */
	public boolean isSballato(int punto) {
		return punto > 21;
	}
	
	/**
	 *  Gestione fine della partita, calcolando i vincitori tra il banco e gli altri giocatori.
	 */
	public void finePartita() {
		boolean vittoriaPlayer = false;
		int puntiBanco = 0;
		ArrayList<Player> giocatoriVincitori = new ArrayList<>();
		if(punti == null) {
			inizializzaPunti(giocatori.size());
		}
		puntiBanco = punti[giocatori.size()-1];		
		if(puntiBanco > 21) {
			for(int i=0; i<giocatori.size()-1; i++) {
				if(punti[i] <= 21) {
					giocatoriVincitori.add(giocatori.get(i));
				}
			}
		}else {
			for(int i=0; i<giocatori.size()-1; i++) {
				if(giocatori.get(i) instanceof BlackJackPlayer && isSplit) {
					if(player.getRisultatoSplit(puntiBanco)) {
						giocatoriVincitori.add(player);
					}
				}else if(!isSplit){
					if(punti[i] <= 21 && punti[i] > puntiBanco) {
						giocatoriVincitori.add(giocatori.get(i));
					}
				}
			}
		}
		int indexP = 0;
		if (!giocatoriVincitori.isEmpty()) {
			for(Player p: giocatoriVincitori) {
				if(p instanceof BlackJackPlayer) {
					((BlackJackPlayer) p).addVittoria();
					vittoriaPlayer = true;
				}
			}
		}
		for(Player p: giocatori) {
			if(p instanceof BlackJackBot && !((BlackJackBot)p).getIsBanco()) {
				String risultato = risultatoBot(punti[indexP], puntiBanco);
				setChanged();
				notifyObservers(new UpdateEvent(risultato, p));
			}
			indexP++;
		}
		if(vittoriaPlayer) {
			setChanged();
			notifyObservers(new UpdateEvent("Vittoria", player));
		}else if(!vittoriaPlayer) {
			setChanged();
			notifyObservers(new UpdateEvent("Sconfitta", player));
		}
		db.updatePlayer(player);
	}

	/**
	 * Metodo che confronta il punteggio di un giocatore (bot) con il punteggio del banco.
	 * @param puntiBot punteggio del bot
	 * @param puntiBanco punteggio del banco
	 * @return la stringa con il risultato del confronto
	 */
	private String risultatoBot(int puntiBot, int puntiBanco) {
		if(puntiBot <= 21 && puntiBanco > 21) {
			return "Vittoria";
		}else if(puntiBot > puntiBanco && puntiBot <= 21) {
			return "Vittoria";
		} else if(puntiBot > 21 || puntiBot < puntiBanco) {
			return "Sconfitta";
		}
		return "";
	}
	
	/**
	 * Metodo che resetta i parametri della classe TavoloDaGioco per la prossima partita
	 */
	public void resetPartita() {
		for(Player player: giocatori) {
			player.resetMano();
		}
		currentPlayerIndex = 0;
		giocatori.clear();
	}

	/**
	 * Metodo che controlla se il valore di due carte è uguale oppure no.
	 * @param p il giocatore che deve controllare le carte
	 * @return true se il valore delle carte è uguale, altrimenti false
	 */
	public boolean carteUguali(Player p) {
		if(p.getMano().size() == 2) {
			return p.getMano().get(0).getValore() == p.getMano().get(1).getValore();
		}
		return false;
	}

	/**
	 * Metodo che aggiorna il conteggio della mano nello Split
	 */
	public void updateIndexMano() {
		getPlayer().updateIndexMano();
	}
}