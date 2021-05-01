package ui;
import model.User;

public class SeeContractWindow {
	
	SeeContractWindow(User currentUser){
		SeeContractsLayout window = new SeeContractsLayout(currentUser);
	     window.setTitle("Login");
	     window.setVisible(true);
	     window.setBounds(10, 10, 440, 350);
	     window.setResizable(false);
	}
}
