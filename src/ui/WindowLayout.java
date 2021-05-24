package ui;
import java.awt.Container;

import javax.swing.JFrame;
/**
 * This is the parent class of all window layout classes 
 * This parent class enforces the methods that needs top be implemented by a layout class
 * It also streamlines the window creation process by separating the initial element setup  into 3 different phases depicted by the 3 methods below. 
 * It then calls the 3 methods below in a specific order.
 * @author Andrew Pang
 *
 */
public abstract class WindowLayout extends JFrame {
	private static final long serialVersionUID = 1L;
	
	public Container container = getContentPane();
	WindowLayout(){
		container.setLayout(null);
		initElements();
		setElementBounds();
		addToContainer();
	}
	
	/**
     * Phase 1: Instantiate the View Elements to be added to the Layout
     */
	protected abstract void initElements();

	/**
	 *Phase 2: Set the positions of the View elements to be added
	*/
	protected abstract void setElementBounds();
	
	/**
	 * Phase 3: Add the elements to the view container
	 */

	protected abstract void addToContainer();
	

}
