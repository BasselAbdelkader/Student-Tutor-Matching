package model;

public interface Subscription {
	
	public void subscribe();
	
	public void unsubscribe();
	
	public String getNotification();
	
	public boolean isSubscribed();
	

}
