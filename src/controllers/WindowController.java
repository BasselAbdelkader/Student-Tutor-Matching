package controllers;

import ui.WindowLayout;

public abstract class WindowController {
	
	protected WindowLayout window;

	 WindowController(WindowLayout win, String title, int height, int width) {
		this.window = win;
		window.setTitle(title);
        window.setVisible(true);
        window.setBounds(10, 10, height, width);
        window.setResizable(false);
        
	}
	
	protected void closeWindow() {
		this.window.dispose();
	}
	
	protected abstract void bindActionListeners();
	protected abstract void init();

	

}
