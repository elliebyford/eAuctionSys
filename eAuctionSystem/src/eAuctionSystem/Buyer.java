package eAuctionSystem;

import java.sql.Date;
import java.util.List;

public class Buyer extends User {

	private List<Auction> auctions;
	private List<Bid> bids;

	/*
	 * This constructor sets the buyers username and password
	 */
	public Buyer(String username, String password) {
		super(username, password);
	}

	/*
	 * This method will tell a buyer if they have won an auction
	 */
	public void victory(Auction auction) {
		String name = getUserName();
		String itemDesc = auction.getItemDescription();
		double bidValue = auction.getHighestBid().getAmount();
		System.out.println("Buyer: " + name + " ` won the auction: "  + " at a price of: £" + bidValue);
	}

	public void informBuyer() {

		System.out.println("You have lost the bid on this auction");
	}
	
	/*
	 * Method is used to retrieve all bids made on auction
	 */
	public List<Bid> getBids() {
		for(Bid bid: bids) {
		
			// if bids have been made on auction
		return bids;
		}
		// if no bids have been made on auction
		return null;
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
