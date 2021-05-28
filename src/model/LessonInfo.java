package model;

public class LessonInfo {
	private String hoursPerSession;
	private String sessionsPerWeek;
	private String ratePerSession;
	private String contractDuration;
	
	

	public LessonInfo(String hoursPerSession, String sessionsPerWeek,String ratePerSession, String contractDuration) {
		this.hoursPerSession = hoursPerSession;
		this.sessionsPerWeek = sessionsPerWeek;
		this.ratePerSession = ratePerSession;
		this.contractDuration = contractDuration;
		
	}
	
	/**
	 * Get the agreed upon hours per session in the contract
	 * @return number of hours per session agreed in the contract
	 */
	public String getHoursPerSession() {
		return hoursPerSession;
	}
	
	/**
	 * Get the agreed upon sessions per week in the contract
	 * @return number of sessions per week agreed in the contract
	 */
	public String getSessionsPerWeek() {
		return sessionsPerWeek;
	}
	
	/**
	 * Get the agreed upon rate per session in the contract
	 * @return rate per session agreed in the contract
	 */
	public String getRatePerSession() {
		return ratePerSession;
	}
	
	/**
	 * Amend the number of hours per session in the UNSIGNED contract
	 * @return null
	 */
	public void setHoursPerSession(String hoursPerSession) {
		this.hoursPerSession = hoursPerSession;
	}
	
	/**
	 * Amend the number of sessions per session in the UNSIGNED contract
	 * @return null
	 */
	public void setSessionsPerWeek(String sessionsPerWeek) {
		this.sessionsPerWeek = sessionsPerWeek;
	}
	
	/**
	 * Amend the number rate per session in the UNSIGNED contract
	 * @return null
	 */
	public void setRatePerSession(String ratePerSession) {
		this.ratePerSession = ratePerSession;
	}
	
	public String getContractDuration() {
		return contractDuration;
	}

	public void setContractDuration(String contractDuration) {
		this.contractDuration = contractDuration;
	}
	
	@Override
	public String toString() {
		String out = "";
		out = out + "Contract duration: " + getContractDuration() + "\n";
		out = out + "Hours Per Session: " + getHoursPerSession() + "\n";
	    out = out + "Sessions Per Week: " + getSessionsPerWeek() + "\n";
	    out = out + "Rate Per Session: " + getRatePerSession() + "\n";
		return out;
	}
}
