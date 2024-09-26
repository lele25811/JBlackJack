package model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/*
 * Classe che si occupa di salvare/caricare i dati su/da file
 */
public class Database {
	
	private static Database databaseInstance;
	private List<BlackJackPlayer> bjPlayers;
	private final String FILENAME = "DatabasePlayer.txt";
	
	public static Database getIstance() {
		if (databaseInstance == null) databaseInstance = new Database();
		return databaseInstance;
	}
	
	private Database() {
		bjPlayers = new ArrayList<BlackJackPlayer>();
	}
	
	public void addPlayer(BlackJackPlayer player) {
		bjPlayers = loadPlayer();
		bjPlayers.add(player);
		savePlayer();
	}
	
	public void updatePlayer(Player updatePlayer) {
		bjPlayers = loadPlayer();
		for(int i=0; i<bjPlayers.size(); i++) {
			BlackJackPlayer currentPlayer = bjPlayers.get(i);
			
			if (currentPlayer.getNickname().equals(updatePlayer.getNickname()) &&
		            currentPlayer.getAvatar().equals(updatePlayer.getAvatar())) {
				System.out.println("Aggiornato "+updatePlayer.getNickname()+" avatar: "+updatePlayer.getAvatar());
				bjPlayers.set(i, (BlackJackPlayer) updatePlayer);
				break;
			}
		}
		savePlayer();
	}
	
	public BlackJackPlayer getPlayerByIndex(int index) {
		return bjPlayers.get(index);
	}
	
	// Salva l'oggetto Player serializzato nel file
	private void savePlayer() {
		try {
			FileOutputStream file = new FileOutputStream(FILENAME);
			ObjectOutputStream out = new ObjectOutputStream(file);
			out.writeObject(bjPlayers);
			out.close();
			file.close();
		}catch (IOException i) {
			i.printStackTrace();
		}	
	}
	
	// Carica l'oggetto player deserializzandolo dal file
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
			i.printStackTrace();
			return null;
		}
	 }
	
	 // Metodo per pulire il file
	 public void clearDatabase() {
	        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILENAME))) {
	            bjPlayers.clear();
	            out.writeObject(bjPlayers);
	        } catch (IOException i) {
	            i.printStackTrace();
	        }
	    }
	
	// Stampa la lista dei giocatori salvati
	public void stampaPlayers() {
		System.out.println("Grandezza ArrayList<BJPLAYER> "+bjPlayers.size());
		if(bjPlayers.size() > 0) {
			for(BlackJackPlayer p : bjPlayers) {
				System.out.println(p.toString());
			}
		}else {
			System.out.println("Nessun Player Presente");
		}
	}

	public List<BlackJackPlayer> getPlayers() {
		return loadPlayer();
	}
	
}
