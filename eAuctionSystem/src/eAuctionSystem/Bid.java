package eAuctionSystem;

import java.io.Serializable;
import java.time.LocalDateTime;

import java.util.Objects;

/**
 * The representation of an auction bid from a user
 */
public class Bid implements Serializable {
	private Buyer who;
	private double amount;
	private LocalDateTime when;

	/*
	 * This constructor sets bid
	 */
	public Bid(double amount, Buyer who, LocalDateTime when) {
		this.who = who;
		this.amount = amount;
		this.when = when;
	}

	/*
	 * This method gets user for bid
	 */
	public Buyer getWho() {
		return who;
	}

	/*
	 * This method sets user for bid
	 */
	public Buyer setWho(Buyer who) {
		return who;

	}

	/*
	 * This method returns the value of the bid
	 */
	public double getAmount() {
		return amount;
	}

	/*
	 * This method returns the date the bid was made
	 */
	public LocalDateTime getWhen() {
		return when;
	}

	/*
	 * This method sets the value of the bid
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}

	// retrieves all bids on an auction
	public void getAllBids() {

	}

	/*
	 * This method sets the date of the bid on an auction
	 */
	public void setWhen(LocalDateTime when) {
		this.when = when;
	}

	public String toString() {
		return String.format("{ user: %s, value: %s bid date: %s}", who, amount, when);
	}


}
