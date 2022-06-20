package eAuctionSystem;

import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

/*
 * <i> The Seller Class is inherits Username and Password from the User abstract class </i>
 * @author Ellie Byford, Macy Esho, Becca Ellison
 * @see <I>User</i>
 * 
 * 
 */
public final class Seller extends User implements Blockable {
	private List<Auction> auctions;
	private List<Item> items = new LinkedList<Item>();
	private Status status;

	/*
	 * This constructor will set the sellers username and password
	 */
	public Seller(String username, String password) {
		super(username, password);
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

	}
	
	/*
	 * method used to retrieve items
	 */
	public List<Item> getItems() {
		return items;
		
	}
	
	/*
	 * method used to retrieve item description
	 */
	public Item getItemByDescription(String description) {
		for(Item items: items) {
		return items;
			}
		return null;
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
}
