package ui;
import model.Contract;
import model.User;
/**
 * This is boot-strapper class for the View Contract Window
 * it specifies the properties of the view contract window such as dimensions, visibility and properties
 * @author Andrew Pang
 *
 */
public class ViewContractWindow {
	
	ViewContractWindow(User currentUser, Contract contract){
		ViewContractLayout window = new ViewContractLayout(currentUser, contract);
        window.setTitle("View contract  " + contract.getId());
        window.setVisible(true);
        window.setBounds(100, 100, 500, 880);
        window.setResizable(false);
	}
	
}
