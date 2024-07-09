package model;
public class TestModel {
	public static void main(String[] args) {
		MazzoDaGioco m = new MazzoDaGioco();
		m.mazzoStampa();
		System.out.println("MazzoGioco size " + m.getMazzoGiocoSize());
		
		BlackJackPlayer bp = new BlackJackPlayer("Emanuele", "avatar");
		System.out.println(bp.getNickname()+" - "+bp.getAvatar());
	}
}