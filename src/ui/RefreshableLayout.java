package ui;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public abstract class RefreshableLayout extends WindowLayout {
	
	private static final long serialVersionUID = 1L;
	private static final int DEFAULT_REFRESH_INTERVAL = 120000;
	private Timer timer = new Timer();
	
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
	
	public void dispose() {
		refresherTask.cancel();
		super.dispose();
	}

}
