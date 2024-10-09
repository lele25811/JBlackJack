package model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * La classe Database si occupa di gestire la persistenza dei dati dei giocatori 
 * del gioco BlackJack, adottando il pattern Singleton. Questa classe permette 
 * di salvare, caricare, aggiornare e ottenere la lista dei giocatori dal file
 * di database.
 * I dati dei giocatori vengono salvati e caricati utilizzando la serializzazione 
 * Java, e il file utilizzato per salvare i dati è "DatabasePlayer.txt".
 */
public class Database {
	
	/**
	 * databaseInstance istanza unica della classe Database.
	 */
	private static Database databaseInstance;
	
	/**
	 * Lista dei giocatori di BlackJack salvati nel database.
	 */
	private List<BlackJackPlayer> bjPlayers;
	
	/**
	 * Nome del file usato come database per la persistenza dei dati dei giocatori.
	 */
	private final String FILENAME = "DatabasePlayer.txt";
	
	/**
	 * Metodo statico per ottenere l'unica istanza della classe Database (Singleton).
	 * Se non esiste un'istanza, ne viene creata una nuova.
	 * @return l'istanza unica di Database
	 */
	public static Database getIstance() {
		if (databaseInstance == null) databaseInstance = new Database();
		return databaseInstance;
	}
	
	/**
	 * Costruttore privato per la classe Database.
	 * Inizializza la lista dei giocatori come un'ArrayList vuota.
	 * Essendo privato, impedisce la creazione di istanze multiple della classe.
	 */
	private Database() {
		bjPlayers = new ArrayList<BlackJackPlayer>();
	}
	
	/**
	 * Aggiunge un nuovo giocatore alla lista dei giocatori e lo salva nel file di database.
	 * @param player il giocatore da aggiungere al database
	 */
	public void addPlayer(BlackJackPlayer player) {
		bjPlayers = loadPlayer();
		bjPlayers.add(player);
		savePlayer();
	}
	
	/**
	 * Aggiorna un giocatore esistente nella lista dei giocatori se il nickname e l'avatar 
	 * coincidono con quelli del giocatore fornito.
	 * @param updatePlayer il giocatore con i dati aggiornati
	 */

	public void updatePlayer(Player updatePlayer) {
		bjPlayers = loadPlayer();
		for(int i=0; i<bjPlayers.size(); i++) {
			BlackJackPlayer currentPlayer = bjPlayers.get(i);
			
			if (currentPlayer.getNickname().equals(updatePlayer.getNickname()) &&
		            currentPlayer.getAvatar().equals(updatePlayer.getAvatar())) {
				bjPlayers.set(i, (BlackJackPlayer) updatePlayer);
				break;
			}
		}
		savePlayer();
	}
	
	/**
	 * Restituisce il giocatore presente nella lista all'indice specificato.
	 * @param index l'indice del giocatore nella lista
	 * @return il giocatore all'indice specificato
	 */
	public BlackJackPlayer getPlayerByIndex(int index) {
		return bjPlayers.get(index);
	}
	
	/**
	 * Salva la lista dei giocatori nel file di database.
	 * Serializza l'oggetto List<BlackJackPlayer> nel file specificato da FILENAME.
	 * @throws DatabaseException se si verifica un errore durante il salvataggio dei dati
	 */
	private void savePlayer() {
		try {
			FileOutputStream file = new FileOutputStream(FILENAME);
			ObjectOutputStream out = new ObjectOutputStream(file);
			out.writeObject(bjPlayers);
			out.close();
			file.close();
		}catch (IOException i) {
			throw new DatabaseException("Errore durante il salvataggio dei giocatori nel file "+FILENAME, i);
		}	
	}
	

	/**
	 * Carica la lista dei giocatori dal file di database.
	 * Deserializza l'oggetto List di BlackJackPlayer dal file specificato da FILENAME.
	 * @return la lista dei giocatori caricata dal file
	 * @throws DatabaseException se si verifica un errore durante il caricamento dei dati
	 */
	@SuppressWarnings("unchecked")
	public List<BlackJackPlayer> loadPlayer() {
		try {
			FileInputStream file = new FileInputStream(FILENAME);
			ObjectInputStream in = new ObjectInputStream(file);
			bjPlayers.clear();
			bjPlayers.addAll((ArrayList<BlackJackPlayer>) in.readObject());
			in.close();
			file.close();
			return bjPlayers;
		}catch(IOException | ClassNotFoundException i) {
			throw new DatabaseException("Errore durante il caricamento dei giocatori nel file "+FILENAME, i);
		}
	 }
	
	/**
	 * Pulisce il database, cancellando tutti i dati presenti nel file.
	 * Inizializza nuovamente la lista dei giocatori come una lista vuota e la salva nel file.
	 */
	public void clearDatabase() {
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILENAME))) {
			bjPlayers.clear();
			out.writeObject(bjPlayers);
		} catch (IOException i) {
			i.printStackTrace();
		}
	}

	/**
	  * Restituisce la lista di tutti i giocatori salvati nel file di database.
	  * @return la lista dei giocatori
	  */
	 public List<BlackJackPlayer> getPlayers() {
		 return loadPlayer();
	 }
}