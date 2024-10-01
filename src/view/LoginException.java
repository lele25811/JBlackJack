package view;

/**
 * La classe LoginException rappresenta un'eccezione specifica 
 * per gli errori che possono verificarsi durante il processo di login.
 */
public class LoginException extends Exception{
	
	  /**
     * Costruttore della classe LoginException.
     * @param message Il messaggio che descrive l'errore di login.
     */
	public LoginException(String message) {
		super(message);
	}
}
