package mazzo;

public enum Seme {
	CUORI("Cuori"),
	QUADRI("Quadri"),
	FIORI("Fiori"),
	PICCHE("Picche");
	
	private final String seme;
	
	// Costruttore
	private Seme(String seme) {
		this.seme = seme;
	}
	
	// StampSeme
	public String stampaSeme() {
		return seme;
	}

}
