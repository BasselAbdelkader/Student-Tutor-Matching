package controllers;

public interface Refreshable {
	
	public static final int DEFAULT_REFRESH_INTERVAL = 120000;
	
	public void refresh();
}
