package eAuctionSystem;

public interface Blockable {
	
	/*
	 * This method checks the auction status is blocked
	 */
	
	public boolean isBlocked();
	
	/*
	 * This method changes the auction status from active to blocked
	 */
	
	public void setBlocked();
	
	
	/*
	 * This method changes the auction status from blocked to active
	 */
	
	public void unBlock();
	
	
	
	
	

}
