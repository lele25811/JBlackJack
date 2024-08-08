package model;

public class BlackJackBot extends Player{

	private boolean isBanco;
	
	public BlackJackBot(String nickname, String avatar, boolean isBanco) {
		super(nickname, avatar);
		this.isBanco = isBanco;
	}
	
	public boolean getIsBanco() {
		return isBanco;
	}
		
}
