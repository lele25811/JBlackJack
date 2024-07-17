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

	List<BlackJackPlayer> bjPlayers = new ArrayList<BlackJackPlayer>();
	String file = "DatabasePlayer.txt";
	
	public Database() {
		loadPlayer();
		System.out.println(bjPlayers.toString());
	}
	
	public void addPlayer(BlackJackPlayer player) {
		bjPlayers.add(player);
		savePlayer();
	}
	
	// Salva l'oggetto Player serializzato nel file
	private void savePlayer() {
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
			out.writeObject(bjPlayers);
		}catch (IOException i) {
			i.printStackTrace();
		}	
	}
	
	// Carica l'oggetto player deserializzandolo dal file
	public List<BlackJackPlayer> loadPlayer() {
		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
			bjPlayers.add((BlackJackPlayer) in.readObject());
			return bjPlayers;
		}catch(IOException | ClassNotFoundException i) {
			i.printStackTrace();
			return null;
		}
	}
	
	// Stampa la lista dei giocatori salvati
	public void stampaPlayers() {
		if(bjPlayers.size() > 0) {
			for(BlackJackPlayer p : bjPlayers) {
				System.out.println(p.toString());
			}
		}else {
			System.out.println("Nessun Player Presente");
		}
	}
	
	
}
