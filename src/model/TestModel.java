package model;
public class TestModel {
	public static void main(String[] args) {
		MazzoDaGioco m = new MazzoDaGioco();
		m.mazzoStampa();
		System.out.println(m.getMazzoGiocoSize());
		System.out.println(m.prossimaCarta());
		System.out.println(m.prossimaCarta());
		System.out.println(m.prossimaCarta());
		System.out.println(m.prossimaCarta());
		System.out.println(m.prossimaCarta());
		m.stampaCarteInGioco();
		m.scartaCarte();
		System.out.println(m.getSizeScarti());
		System.out.println(m.getMazzoGiocoSize());
	}
}