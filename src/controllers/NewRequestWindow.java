package controllers;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import adaptors.RequestAPI;
import adaptors.SubjectAPI;
import model.Request;
import model.Subject;
import model.User;
import ui.NewRequestLayout;
/**
 * This is boot-strapper class for the New Request Layout
 * it specifies the properties of the new request window such as dimensions, visibility and properties
 * @author Andrew Pang
 *
 */
public class NewRequestWindow extends WindowController {
	
	NewRequestLayout window;

	private User currentUser;
	private ArrayList<Subject> subjects;
	
	public NewRequestWindow(User currentUser) {
		super(new NewRequestLayout(),"Create new request",440,390);
		this.window = (NewRequestLayout) super.window;
		this.currentUser = currentUser;
    	bindActionListeners();
        init(); 
	}
	
	/**
	 * Bind elements that interacts with the user with their respective action listeners
	 */
	@Override
	protected void bindActionListeners() {
		
		window.getCreateRequestBtn().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Request b = new Request(currentUser,
						window.getRequestTypeInput().getSelectedItem().toString(),
						subjects.get(window.getSubjectInput().getSelectedIndex()).getId(),
						Integer.parseInt(window.getCompetencyInput().getSelectedItem().toString()),
						window.getHoursPerSessionInput().getSelectedItem().toString(),
						window.getSessionsPerWeekInput().getSelectedItem().toString(),
						window.getRatePerSessionInput().getText(),
						window.getContractDurationInput().getSelectedItem().toString()
						);
				try {
					String id = RequestAPI.getInstance().addRequest(b);
					new RequestWindow(currentUser, RequestAPI.getInstance().getRequest(id));
					closeWindow();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			
		});

	}
	
	/**
	 * Initialize the elements properties
	 */
	@Override
	protected void init() {
		
		try {
			subjects = SubjectAPI.getInstance().getAllSubjects();
			for (Subject s : subjects) {
				window.getSubjectInput().addItem(s.getName());
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(window, "Error getting subjects");
		}
	}
	
	
	
}
