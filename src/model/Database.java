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
	private String fileName = "DatabasePlayer.txt";
	
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
	
	//TODO
	public BlackJackPlayer getPlayerByName(String player) {
		
		return null;
	}
	
	// Salva l'oggetto Player serializzato nel file
	private void savePlayer() {
		try {
			FileOutputStream file = new FileOutputStream(fileName);
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
			FileInputStream file = new FileInputStream(fileName);
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
	        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
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
