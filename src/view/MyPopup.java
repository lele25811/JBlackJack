package view;

import javax.swing.JOptionPane;

public class MyPopup {

	private String title;
	private String text;
		
	public MyPopup(String title, String text) {
		this.title = title;
		this.text = text;
	}
	
	public void showMessage() {
		JOptionPane.showMessageDialog(
			null,
			text,
			title,
			JOptionPane.INFORMATION_MESSAGE
			);
	}
}
