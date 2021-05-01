package ui;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This is the parent class for a refreshable layout, which is essentially a normal windowLayout but with an auto refresh function
 * This method has a default timer that will automatically call the refresh function in its child classes to enforce the auto refresh feature. 
 * The child classes decides which components it needs to refresh individually which is why it is made abstract.
 * @author Andrew Pang
 *
 */
public abstract class RefreshableLayout extends WindowLayout {
	
	private static final long serialVersionUID = 1L;
	private static final int DEFAULT_REFRESH_INTERVAL = 120000;
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
	
	RefreshableLayout(){
		super();
		timer.schedule(refresherTask, new Date(System.currentTimeMillis() + DEFAULT_REFRESH_INTERVAL), DEFAULT_REFRESH_INTERVAL);
	}

	protected abstract void refresh();
	
	/**
	 * Make sure to cancel the refresh task before exisiting. 
	 */
	public void dispose() {
		refresherTask.cancel();
		super.dispose();
	}

}
