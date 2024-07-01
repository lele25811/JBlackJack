package model;

public class Player {
	
	protected String nickname;
	protected String avatar;
	
	public Player(String nickname, String avatar) {
		this.nickname = nickname;
		this.avatar = avatar;
	}
	
	public String getNickname() {
		return nickname;
	}
	
	public String getAvatar() {
		return avatar;
	}

	@Override
	public String toString() {
		return "Player [ " + nickname + "," + avatar + "]";
	}
	
	

}
