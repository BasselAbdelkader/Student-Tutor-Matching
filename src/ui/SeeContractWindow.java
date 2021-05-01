package ui;
import model.User;
/**
 * This is boot-strapper class for the See Contracts layout
 * it specifies the properties of the see contracts window such as dimensions, visibility and properties
 * @author Andrew Pang
 *
 */
public class SeeContractWindow {
	
	SeeContractWindow(User currentUser){
		SeeContractsLayout window = new SeeContractsLayout(currentUser);
	     window.setTitle("Login");
	     window.setVisible(true);
	     window.setBounds(10, 10, 440, 350);
	     window.setResizable(false);
	}
}
