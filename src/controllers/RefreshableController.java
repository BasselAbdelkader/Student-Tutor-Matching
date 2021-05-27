package controllers;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import ui.WindowLayout;

public abstract class RefreshableController extends WindowController implements Refreshable{
	
	private Timer timer = new Timer();
	
	/**
	 * A task to perform, in this case is the refresh function.
	 */
	private TimerTask refresherTask = new TimerTask() {  
	    @Override  
	    public void run() {  
	    	refresh(); 
	    }
	};
	
	RefreshableController(WindowLayout win, String title, int height, int width) {
		super(win, title, height, width);
		timer.schedule(refresherTask, new Date(System.currentTimeMillis() + Refreshable.DEFAULT_REFRESH_INTERVAL), Refreshable.DEFAULT_REFRESH_INTERVAL);
		// TODO Auto-generated constructor stub
	}

	public abstract void refresh();
	
	/**
	 * Make sure to cancel the refresh task before exisiting. 
	 */
	@Override
	protected void closeWindow() {
		refresherTask.cancel();
		super.closeWindow();
	}

}
