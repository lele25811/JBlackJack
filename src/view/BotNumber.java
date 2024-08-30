package view;

public enum BotNumber {
	ONE("1", "One"),
	TWO("2", "Two"),
	THREE("3", "Three");

	private final String VALUE;
	private final String TEXT;
	
	// Costruttore dell'enum
	BotNumber(String value, String text) {
		this.VALUE = value;
		this.TEXT = text;
	}

	// Metodo per ottenere il valore int associato
	public String getValue() {
		return VALUE;
	}
	
	public String getText() {
		return TEXT;
	}
}
