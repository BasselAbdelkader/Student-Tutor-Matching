package ui;
import java.awt.Container;

import javax.swing.JFrame;

public abstract class WindowLayout extends JFrame {
	private static final long serialVersionUID = 1L;
	
	Container container = getContentPane();
	WindowLayout(){
		container.setLayout(null);
		initElements();
		setElementBounds();
		addToContainer();
		bindActionListeners();
		init();
	}
	
	protected abstract void initElements();
	
	protected abstract void setElementBounds();
	
	protected abstract void addToContainer();
	
	protected abstract void bindActionListeners();
	
	protected abstract void init();

}
