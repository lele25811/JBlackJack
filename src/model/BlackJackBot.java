package model;

import java.util.Random;

public class BlackJackBot extends Player{

	private boolean isBanco;
	private Random random;
	private int punti;
	
	public BlackJackBot(String nickname, String avatar, boolean isBanco) {
		super(nickname, avatar);
		this.isBanco = isBanco;
		random = new Random();
	}
	
	public boolean getIsBanco() {
		return isBanco;
	}
	
	private void SceltaPunteggio(int[] puntiDisponibili) {
		if(puntiDisponibili.length < 2) {
			punti = puntiDisponibili[0];
		}else {
			int scelta;
			if(puntiDisponibili[1] >= 18) {
				scelta = 1;
			}else {
				scelta = random.nextInt(2); // Sceglie tra 0 o 1
			}
			switch(scelta) {
			case 0: punti = puntiDisponibili[0];
					break;
			case 1: punti = puntiDisponibili[1];
					break;
			default: punti = puntiDisponibili[1];
					break;
			}
		}
	}
	
	public int getSceltaPunti(int[] puntiDisponibili) {
		SceltaPunteggio(puntiDisponibili);
		return punti;
	}
	
	public int getPunti() {
		return punti;
	}
	
	public void setPunti(int punti) {
		this.punti = punti;
	}
	
	public int updatePunti() {
		System.out.println("bot: "+getNickname());
		System.out.println("Punti attuali: "+punti);
		System.out.println("Ultima carta "+getLastCard().toString()+" di valore "+getLastCard().getValore());
		return getLastCard().getValore();
	}
	
	
}
