package eAuctionSystem;

import java.util.LinkedList;
import java.util.List;

/*
 * <i> The Admin Class inherits Username and Password from the User abstract class </i>
 * @author Ellie Byford, Macy Esho, Becca Ellison
 * @see <I>User</i>
 * 
 * 
 */

public class Admin extends User implements Blockable {
	private List<Auction> auctions;
	private List<Bid> bids;

	private Status status;
	/*
	 * This constructor sets the Admins username and password
	 */
	public Admin(String username, String password) {
		super(username, password);
	}
	
	/*
	 * method used to get auction status
	 */
	public Status getStatus() {
		return status;
	}
	
	/*
	 * method used to set auction status
	 */
	public void setStatus(Status status) {
		this.status = status;
	}

	

	@Override
	public boolean isBlocked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setBlocked() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unBlock() {
		// TODO Auto-generated method stub
		
	}

	
}
