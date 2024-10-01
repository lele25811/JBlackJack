package view;

/**
 * L'enumerazione BotNumber rappresenta i numeri dei bot
 * disponibili nel gioco di BlackJack. Ogni valore dell'enum 
 * è associato a una stringa rappresentativa del numero e a una 
 * descrizione testuale.
 */
public enum BotNumber {
	
	/**
	 * Rappresenta un bot
	 */
	ONE("1", "One"),
	
	/**
	 * Rappresenta due bot
	 */
	TWO("2", "Two"),
	
	/**
	 * Rappresenta tre bot
	 */
	THREE("3", "Three");

	/**
	 * il valore numerico sottoforma di stringa
	 */
	private final String VALUE;
	
	/**
	 * il valore testuale
	 */
	private final String TEXT;
	
	 /**
     * Costruttore dell'enumerazione BotNumber.
     * @param value La rappresentazione stringa del numero del bot.
     * @param text La descrizione testuale del numero del bot.
     */
	BotNumber(String value, String text) {
		this.VALUE = value;
		this.TEXT = text;
	}

	/**
     * Restituisce la rappresentazione stringa del numero del bot.
     * @return Il valore stringa del bot.
     */
	public String getValue() {
		return VALUE;
	}
	
	 /**
     * Restituisce la descrizione testuale del numero del bot.
     * @return Il testo descrittivo del bot.
     */
	public String getText() {
		return TEXT;
	}
}
