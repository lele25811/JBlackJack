package mazzo;

import java.util.ArrayList;

public class testMazzo {
	public static void main(String[] args) {
		Mazzo m = new Mazzo();
		ArrayList<Carta> mazzo = new ArrayList<>(52);
		mazzo = m.mescola();
		
		System.out.println(mazzo);
	}
}
