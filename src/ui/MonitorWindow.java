package ui;

import model.User;

public class MonitorWindow {
	
	public MonitorWindow(User currentUser) {
		MonitorLayout window = new MonitorLayout(currentUser);
        window.setTitle("Create new request");
        window.setVisible(true);
        window.setBounds(100, 100, 500, 500);
        window.setResizable(false);
	}

}
