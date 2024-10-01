package model;

/**
 * Classe UpdateEvent per mandare comandi tramite il pattern Observer. 
 */
public class UpdateEvent {
	
	/**
	 * action definisce l'azione che si deve fare
	 */
	private String action;
	
	/**
	 * data definisce l'oggetto che deve fare l'azione
	 */
	private Object data;

	/**
	 * Costruttore della classe UpdateEvent
	 * @param action azione da fare
	 * @param data oggetto che deve compiere l'azione
	 */
	public UpdateEvent(String action, Object data) {
		this.action = action;
		this.data = data;
	}

	/**
	 * Metodo che ritorna l'azione da fare
	 * @return azione da fare
	 */
	public String getAction() {
		return action;
	}
	
	/**
	 * Metodo che ritorna l'oggetto che deve compire l'azione
	 * @return l'oggetto che deve compiere l'azione
	 */
	public Object getData() {
		return data;
	}
}
