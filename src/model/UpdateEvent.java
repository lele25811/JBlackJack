package model;

public class UpdateEvent {
	
	
	private String action;
	private Object data;

	public UpdateEvent(String action, Object data) {
		this.action = action;
		this.data = data;
	}

	public String getAction() {
		return action;
	}
	
	public Object getData() {
		return data;
	}
}
