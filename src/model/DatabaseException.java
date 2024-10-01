package model;

/**
 * La classe DatabaseException è una classe di eccezione personalizzata che estende RuntimeException.
 * Viene utilizzata per gestire gli errori specifici che possono verificarsi durante
 * le operazioni di salvataggio e caricamento dei dati nel database.
 */
public class DatabaseException extends RuntimeException{


	/**
	 * Costruttore che crea una DatabaseException con un messaggio specifico.
	 * @param message il messaggio che descrive l'errore
	 */
	public DatabaseException(String message) {
		super(message);
	}

	/**
	 * Costruttore che crea una DatabaseException con un messaggio specifico e una causa.
	 * @param message il messaggio che descrive l'errore
	 * @param cause l'eccezione che ha causato questo errore
	 */
	public DatabaseException(String message, Throwable cause) {
		super(message, cause);
	}
}