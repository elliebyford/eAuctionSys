package eAuctionSystem;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class Auction implements Blockable, Serializable {

	private int auctionID;

	private String auction;

	private Double startPrice;

	private Double reservePrice;

	private LocalDateTime startDate;

	private LocalDateTime closeDate;

	private Status status;

	private Buyer buyer;

	Seller seller;
	
	Admin admin;

	private Item item;

	private Boolean verification;

	private List<Bid> bids;

	private String auctions;

	/*
	 * 
	 * Constructor to set the details of each auction
	 * 
	 * 
	 * 
	 */

	public Auction() {
	}

	public Auction(double startPrice, double reservePrice, LocalDateTime closeDate, Status status, Item item,
			Seller seller) {

		this.startPrice = startPrice;

		this.reservePrice = reservePrice;

		this.closeDate = closeDate;

		this.status = status;

		this.item = item;

		this.seller = seller;

		this.bids = new ArrayList<>();

	}

	@Override
	public String toString() {
		return auction;
	}

	/*
	 * 
	 * Method to verify auction and change status so that buyers can bid on Auction
	 * 
	 */

	public Auction(String auctionID2, String name, String description, double startPrice2, double reservePrice2,
			String startDate2, String closeDate2) {
		// TODO Auto-generated constructor stub
	}

	/*
	 * This method will verify the auction by changing the status to active ready
	 * for bids
	 */
	public void verifyAuction() {

		this.status = status.STATUS_ACTIVE;

	}

	/*
	 * method used to add bids that users have made
	 */
	public void placeBid(double amount, Buyer buyer, LocalDateTime when) {
		new Bid(amount, buyer, when);
	}

	/*
	 * method for before auction is verified.
	 */
	public Status pending() {

		return status = Status.STATUS_PENDING;
	}

	/*
	 * Method is used to close the auction, if reserve price has been met it will
	 * notify buyer of the highest bid that they have won the auction
	 */
	public void close() {

		List<Bid> bids = getBids();

		// sets auction status to closed when auction has finished
		this.status = status.STATUS_CLOSED;

		// checks if any bids have been made on the auction
		if (bids.size() == 0) {
			// auction has expired, no bids made therefore, noone has won bid
			System.out.println(getItemDescription() + " Auction has expried and there is no winner ");
			return;
		}
		// retrieve highest bid made on auction
		Bid highestBid = bids.stream().collect(Collectors.maxBy(Comparator.comparingDouble(Bid::getAmount))).get();
		// get buyer that made highest bid on auction
		buyer = highestBid.getWho();

		// checks if highest bid is less than the reserve price of the auction
		if (highestBid.getAmount() < getReservePrice()) {
			// reserve price is less than highest bid, therefore there is no winner for the
			// auction
			System.out.println(getItemDescription()
					+ " Auction has expired and reserve price has not been met therefore, there is no winner for this auction. ");
			return;
		}

		// if reserve price has been met there is a winner for the auction
		System.out.println(getItemDescription() + " Auction has ended and there is a winner. ");
		// inform buyer of the highest bid that they have won the auction
		buyer.victory(this);

	}

	/*
	 * This method retrieves all bids made on an auction
	 */
	public List getBids() {

		return bids;

	}

	/*
	 * This method will return the start price of the auction
	 */
	public double getStartPrice() {
		return startPrice;
	}

	/*
	 * This method will set the start price opf the auction
	 */
	public void setStartPrice(double startPrice) {
		this.startPrice = startPrice;
	}

	/*
	 * This method will return the reserve price of the auction
	 */
	public double getReservePrice() {
		return reservePrice;
	}

	/*
	 * This method will set the reserve price of the auction
	 */
	public void setReservePrice(double reservePrice) {
		this.reservePrice = reservePrice;
	}

	/*
	 * This method will return the start date of the auction
	 */
	public LocalDateTime getStartDate() {
		return startDate;
	}

	/*
	 * This method will set the start date of an auction
	 */
	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	/*
	 * This method will return the close date of the auction
	 */
	public LocalDateTime getCloseDate() {
		return closeDate;
	}

	/*
	 * This method will set the close date of the auction
	 */
	public void setCloseDate(LocalDateTime closeDate) {
		this.closeDate = closeDate;
	}

	/*
	 * This method will retrieve the status of the auction
	 */
	public Status getStatus() {
		return status;
	}

	/*
	 * This method will set the status of the auction
	 */
	public void setStatus(Status status) {
		this.status = status;
	}

	/*
	 * This method will return the description of the item on the auction
	 */
	public String getItemDescription() {
		return item.getDescription();
	}

	/*
	 * This method will return item name on the auction
	 */
//	public String getItemName() {
//		return item.getItemName();
//	}

	@Override
	/*
	 * Method isBlocked will check if the auction has been blocked
	 */
	public boolean isBlocked() {

		return this.status == status.STATUS_BLOCKED;

	}

	@Override
	/*
	 * this will set the auction to blocked
	 */
	public void setBlocked() {
		// TODO Auto-generated method stub
		this.status = status.STATUS_BLOCKED;
	}

	@Override
	public void unBlock() {
		this.status = status.STATUS_ACTIVE;

	}

	public Item getItem() {
		return item;
	}

	/*
	 * This method will return the highest bid from an auction when an auction is
	 * closed
	 */
	public Bid getHighestBid() {

		Bid highBid = new Bid(auctionID, buyer, closeDate);
		for (Bid bids : this.bids) {
			if (bids.getAmount() > highBid.getAmount()) {
				highBid = bids;

			}
		}
		// returns winner of the auction with the highest bid
		return highBid;
	}

	/*
	 * This method allows a buyer to place a bid on an auction
	 * 
	 * @param buyerBid is the buyer who makes the bid
	 */
	public void placeBid(Bid buyerBid) {
		this.bids.add(buyerBid);

	}

}
