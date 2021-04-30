
public class ViewContractWindow {
	
	ViewContractWindow(User currentUser, Contract contract){
		ViewContractLayout window = new ViewContractLayout(currentUser, contract);
        window.setTitle("View contract  " + contract.getId());
        window.setVisible(true);
        window.setBounds(100, 100, 500, 840);
        window.setResizable(false);
	}
	
}
