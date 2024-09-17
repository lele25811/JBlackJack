package model;

import java.util.Random;

public class BlackJackBot extends Player{

	private boolean isBanco;
	private Random random;
	
	public BlackJackBot(String nickname, String avatar, boolean isBanco) {
		super(nickname, avatar);
		this.isBanco = isBanco;
		random = new Random();
	}
	
	public boolean getIsBanco() {
		return isBanco;
	}
		
	// definisci le possibili mosse di un bot, scelta randomica ecc
	public int getSceltaPunteggio(int[] puntiDisponibili) {
		if(puntiDisponibili.length < 2) {
			return puntiDisponibili[0];
		}else {
			int scelta = random.nextInt(2); // Sceglie tra 0 o 1
			switch(scelta) {
			case 0: return puntiDisponibili[0];
			case 1: return puntiDisponibili[1];
			}
		}
		return 0;
	}
}
