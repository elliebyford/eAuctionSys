package eAuctionSystem;

import java.util.Scanner;
import java.util.stream.Collectors;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class eSystem {

	private static final String PATH = "resources/";
	private final Scanner S = new Scanner(System.in);

	private boolean userAuthenticate;

	private User loggedIn;

	private int accountID;

	private Double upperBiddingIncrement;

	private Double lowerBiddingIncrement;

	private List<User> users = new LinkedList<User>();

	private List<Auction> auctions = Collections.synchronizedList(new LinkedList<Auction>());

	private static final DecimalFormat df = new DecimalFormat(".##");

	private Seller seller;

	private Buyer buyer;
	
	private Admin admin;

	public eSystem() {

		try {

			deSerialize();
			// Add Users

			users.add(new Admin("Ellie", "_Ellie"));
			users.add(new Admin("Macy", "_Macy"));
			users.add(new Admin("Becca", "_Becca"));
			users.add(new Seller("Sorren", "_Sorren"));
			users.add(new Seller("Glyn", "_Glyn"));
			users.add(new Buyer("Mark", "_Mark"));
			users.add(new Buyer("Kirsty", "_Kirsty"));
			users.add(new Buyer("Andy", "_Andy"));

			// Add Items
			/*
			 * Seller.class.cast(users.get(0)).getItems().add(new Item("Pineapple"));
			 * Seller.class.cast(users.get(0)).getItems().add(new Item("Watermelon"));
			 */

			// Active Auctions

			auctions.add(new Auction(1.20, 2.00, LocalDateTime.now().plusSeconds(120), Status.STATUS_ACTIVE,
					new Item("Orange"), Seller.class.cast(users.get(1))));

			auctions.add(new Auction(1.50, 3.00, LocalDateTime.now().plusSeconds(120), Status.STATUS_ACTIVE,
					new Item("Apple"), Seller.class.cast(users.get(1))));
			
			auctions.add(new Auction(1.70, 3.00, LocalDateTime.now().plusSeconds(120), Status.STATUS_ACTIVE,
					new Item("Pear"), Seller.class.cast(users.get(1))));

			// Add Pending Auctions

			auctions.add(new Auction(1.20, 2.00, LocalDateTime.now().plusSeconds(120), Status.STATUS_PENDING,
					new Item("Banana"), Seller.class.cast(users.get(1))));
			
			auctions.add(new Auction(1.40, 2.00, LocalDateTime.now().plusSeconds(120), Status.STATUS_PENDING,
					new Item("Grapes"), Seller.class.cast(users.get(1))));

			// Add bids on auctions

			auctions.get(0).placeBid(5.00, Buyer.class.cast(users.get(2)), LocalDateTime.now());

		} catch (Exception e) {
			System.out.print(e.getMessage() + "\n");
		}

	}

	/*
	 * Method to displays main menu
	 */

	public void Menu() {
		// sets auctions and users
		this.auctions = auctions;
		this.users = users;

		String choice = "";

		// as long as user does not quit, continue running the menu
		while (!(choice.equals("Q"))) {

			// checks user is logged into account
			if (loggedIn != null) {
				if (Seller.class.isInstance(loggedIn)) {
					// seller menu for seller
					sellerMenu();
				}
				else if(Admin.class.isInstance(loggedIn)) {
					// admin menu for admin
					adminMenu();
				} else {
					// buyer menu for buyer
					buyerMenu();
				}
			} else {
				

				// the main menu
				System.out.println("-- MAIN MENU --");

				System.out.println("1 - Browse Auctions");

				System.out.println("2 - Login");

				System.out.println("3 - Logout");

				System.out.println("4 - Create Account");

				System.out.println("Q - Quit");

				System.out.print("Pick : ");

				choice = S.next().toUpperCase();

				switch (choice) {

				case "1": { // Browse auctions in progress

					browseAuctions(auctions);

					break;

				}

				case "2": { // Login to account

					Login();

					break;

				}

				case "3": { // Logout of account

					Logout();

					break;

				}

				case "4": { // Set up an account on the system

					createAccount();

					break;

				}

				default: // Quits the system
					// Best practice to close scanners
					S.close();

					serialize();

					System.out.println("Goodbye!");
					System.exit(0);

				}
			}
		}

	}

	/*
	 * This method contains menu options for the seller
	 */
	private void sellerMenu() {

		System.out.println("--SELLER MENU--");
		System.out.println("1. [C]reate an Auction");
		System.out.println("2. [V]erify an Auction");
		System.out.println("3. [L]ogout");
		System.out.println("4. [Q]uit");
		System.out.print("Pick : ");

		String choice = S.next().toUpperCase();

		switch (choice) {
		case "1": // create an auction
		case "C": {
			createAuction();
			break;
		}

		case "2": // verify an auction
		case "V": {
			verifyAuction();
			break;
		}

		case "3": // Logout
		case "L": {
			Logout();
			break;
		}
		case "4": // Exit system
			System.out.println("Goodbye!");
			System.exit(0);
		}
	}

	/*
	 * This method contains menu options for buyer
	 */
	private void buyerMenu() {
		System.out.println("--BUYER MENU--");
		System.out.println("1. Place a bid on an auction");
		System.out.println("2. Browse Auctions in progress");
		System.out.println("3. Track Bids");
		System.out.println("4. Logout");
		System.out.println("5. Quit");
		System.out.print("Pick : ");

		String choice = S.nextLine();
		if (choice.equalsIgnoreCase("")) {
			choice = S.nextLine();

		}

		switch (choice) {
		case "1": // Make a bid on an active auction
			bidOnAuction();
			break;
		case "2": // Browse active auctions
			browseAuctions(auctions);
			break;
		case "3": // Track bids
			trackBid(auctions);
			break;
		case "4": // Logout of account
			Logout();
			break;
		case "5": // exit system
			serialize();
			System.out.println("Goodbye!");
			System.exit(0);

		}

	}
	
	 /*
	  * This menu is for admins of eAuction System
	  */
	private void adminMenu() {

		System.out.println("--ADMIN MENU--");
		System.out.println("1. Block Auctions");
		System.out.println("2. Block a Seller");
		System.out.println("3. View Blocked Sellers");
		System.out.println("4. Logout");
		System.out.println("5. Quit");
		System.out.print("Pick : ");

		String choice = S.next().toUpperCase();

		switch (choice) {
		case "1": {// Blocks Auctions
			blockAuction();
			break;
		}

		case "2": {// Blocks Sellers by username
			blockSellerByName();
			break;
		}

		case "3": {// Views all blocked sellers
			viewBlockedSellers(users);
			break;
		}
		
		case "4": {// Logout
			Logout();
			break;
		}
		
		case "5": // Exit system
			serialize();
			System.out.println("Goodbye!");
			System.exit(0);
		}
	}
		

// this method is to track users bids on auctions
	private void trackBid(List<Auction> auctions) {

		
		
//		Auction auction = new Auction();
		System.out.println("--BIDS PLACED ON AUCTIONS--");
		// retrieves active auctions
		List<Auction> activeAuctions = auctions.stream().filter(o -> o.getStatus().equals(Status.STATUS_ACTIVE))
				.collect(Collectors.toList());

		// call close method
		Auction auction = new Auction();

		while (auction != null) {

			auction.close();

		}

	}

	// this method is used to deserialize the data
	private void deSerialize() {
		ObjectInputStream ois;

		try {
			ois = new ObjectInputStream(new FileInputStream(PATH + "user.ser"));

			users = (LinkedList<User>) ois.readObject();

			ois.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	// this method is used to serialize the data

	private void serialize() {
		ObjectOutputStream oos;

		try {
			oos = new ObjectOutputStream(new FileOutputStream(PATH + "user.ser"));
			for (User user : users) {
				System.out.println(user.getUserName());
			}
			oos.writeObject(users);

			oos.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/*
	 * This method is used to create an account
	 * 
	 * A user can register as a Buyer, Seller or Admin
	 */

	private void createAccount() {
		String User;

		System.out.println("--CREATE ACCOUNT--");
		// pick buyer or seller
		System.out.print("Please Select Type of User: [B/S/A]");

		User = S.next().toUpperCase();

		while (!User.equals("B") & !User.equals("S")  & !User.equals("A"))
			;

		System.out.print("--CASE SENSITIVE--");

		// get user's username
		System.out.println("Please enter a username");

		String username = S.next();

		// get user's password
		System.out.println("Please enter your Password");

		String password = S.next();

		System.out.println("Please confirm your Password");
		// check for validity
		String confirmPassword = S.next();
		// user chooses B they will be a buyer
		if (User.equals("B")) {
			users.add(new Buyer(username, password));
			buyerMenu();
		}
		// user chooses S they will be a seller
		if (User.equals("S")) {
			users.add(new Seller(username, password));
			sellerMenu();
		}
		// user chooses A they are an Admin
		if (User.equals("A")) {
			users.add(new Admin(username, password));
			adminMenu();
		}

		System.out.println("Welcome! You have successfully created an account");
	}

	/*
	 * This method logs the user out of the eAuction system
	 */
	private void Logout() {

		if (loggedIn != null) {
			System.out.println("You are logged out");
			loggedIn = null;

		}
	}

	/*
	 * method is used to ask users for their details and log them into the system if
	 * their details are correct
	 */
	private void Login() {
		System.out.println("-- LOGIN (CASE SENSITIVE)--");
		System.out.println("Please enter your Username: ");
		String username = S.next();

		User users = getUserNameByName(username);
		// user has not registered

		if (users == null) {
			System.out.println("You need to create an account");
			return;
		}

		System.out.println("Please enter your Password");
		String password = S.next();

		// check if password is correct
		if (users.checkPassword(password)) {
			// login successful
			loggedIn = users;

		} else {
			// incorrect password
			System.out.println("Sorry you have entered incorrect details");
		}

	}

	/*
	 * method is used to get users by their username
	 */
	public User getUserNameByName(String username) {

		for (User user : users) {
			if (user.getUserName().equals(username)) {
				return user;
			}
		}
		// if returns null the user does not exist
		return null;
	}
	

	
	/*
	 * ADMIN FUNCTIONALITY
	 * 
	 * This method is to block sellers by their username
	 */
	private void blockSellerByName() {
		
		System.out.println("Please enter the Seller name you would like to block: ");
		String username = S.next();
		
		User user = getUserNameByName(username);
		
		// checks if seller exists
		do {
			
		if (Seller.class.isInstance(users)) {
			System.out.println(users.toString());
			
			
		} else {
			// Seller invalid
			System.out.println("--SELLER IS INVALID--");
			return;
		}
		
		if(username.equals(username)) {
			
		}
			// checks if seller username is correct
		} while(!username.equals(Seller.class.isInstance(users)));
		
		System.out.println("Would you like to block this Seller? [Y/N]");
		String option = S.next();
		if(option.equals("Y")) {
			// Blocks seller
			Seller.class.cast(users).setStatus(Status.STATUS_BLOCKED);
			System.out.println("--SELLER IS NOW BLOCKED--");
			return;
		
		} else {
			System.out.println("--SELLER IS NOT BLOCKED, PLEASE TRY AGAIN--");
		}
	}
	
	/*
	 * This method shows the Admin all blocked Sellers on the system
	 */	
	private void viewBlockedSellers( List<User> users) {
//		List<User> blockedUsers = users.stream().filter(o -> ((Seller) o).getStatus().equals(Status.STATUS_BLOCKED))
//				.collect(Collectors.toList());
		
		
		System.out.println("--VIEW ALL BLOCKED SELLERS--");
		// gets all sellers that are currently blocked
		if(Seller.class.cast(users).getStatus().equals(Status.STATUS_BLOCKED)) {
			System.out.println(users.toString());
		}
	}
	
	/*
	 * This method allows Admin to block an auction
	 */
	private void blockAuction() {
		
		
		System.out.println("Which auction would you like to block? (Enter the Auction ID)");
		browseAuctions(auctions);
		// gets AuctionID from admin
		int option = S.nextInt();
		
		if(Admin.class.cast(auctions).getStatus().equals(Status.STATUS_ACTIVE)) {
			Auction.class.cast(auctions).setStatus(Status.STATUS_BLOCKED);
			// Auction status is now set to blocked
			System.out.println("--AUCTION IS NOW BLOCKED--");
		} else {
			System.out.println("--AUCTION IS NOT BLOCKED, PLEASE TRY AGAIN--");
		}
		
	}
	
	
	/*
	 * Method to display welcome message before user begins
	 */

	public void message() {

		while (true) {
			// user authentication
			while (!userAuthenticate) {

				System.out.println("");

				System.out.println("Welcome!");

				verifyUser();

			}

		}

	}

	/*
	 * Method for if user chooses option 2 they will be authenticated
	 */

	private void verifyUser() {

		System.out.println("");

		System.out.println("Please enter your Username : ");
		String username = S.nextLine();
		System.out.println("");
		System.out.println("Please enter your Password : ");
		String password = S.nextLine();
	}

	// this method allows the user to browse active auctions
	private void browseAuctions(List<Auction> auctions) {

		List<Auction> activeAuctions = auctions.stream().filter(o -> o.getStatus().equals(Status.STATUS_ACTIVE))
				.collect(Collectors.toList());

		int i = 1;
		StringBuffer sb = new StringBuffer();

		System.out.println("--BROWSE ACTIVE AUCTIONS--");
		// loop through the active auctions and print out the details to the user
		for (Auction auction : activeAuctions) {

			// Prints out all details for active auctions
			sb.append("Auction ");
			sb.append(i);
			sb.append(": ");
			sb.append(auction.getItemDescription());
			sb.append(" \n - Close Date: ");
			sb.append(auction.getCloseDate().getDayOfWeek().toString().toLowerCase());
			sb.append(" ");
			sb.append(auction.getCloseDate().getDayOfMonth());
			sb.append(" ");
			sb.append(auction.getCloseDate().getMonth().toString().toLowerCase());
			sb.append(" ");
			sb.append(auction.getCloseDate().getYear());
			sb.append(" at ");
			sb.append(auction.getCloseDate().getHour());
			sb.append(":");
			sb.append(auction.getCloseDate().getMinute());
			sb.append(" Start Price: £");
			sb.append(auction.getStartPrice());
			sb.append(" Reserve Price: £");
			sb.append(auction.getReservePrice());
			sb.append("\n ");
			i++;
		}
		System.out.println(sb);

	}
	// this method allows the user to verify and auction

	private void verifyAuction() {

		List<Auction> pendingAuctions = auctions.stream().filter(o -> o.getStatus().equals(Status.STATUS_PENDING))
				.collect(Collectors.toList());
		// check if there are any pending auctions
		if (pendingAuctions.size() == 0) {
			System.out.println("There are no pending auctions that exist for your account");
		} else {
			// print out the pending auctions
			System.out.println("Which auction would you like to verify? (enter the auction number)");

			int i = 1;
			Map<Integer, Auction> options = new HashMap<>();

			Seller seller = (Seller) loggedIn;
			// retrieves all pending auctions and prints them out

			for (Auction auction : pendingAuctions) {
				// if auction is sellers auction
				if (auction.seller == seller) {
					options.put(i, auction);
					System.out.println("Auction " + i + ": " + auction.getItemDescription());
					i++;
				}
			}

			int option = S.nextInt();
			// retrieves auction selected by the user

			Auction select = options.get(option);

			// when user has selected auction, verify auction
			select.verifyAuction();
			System.out.println("Your Auction is now Verified and is in an Active Status");
		}

	}
	/*
	 * SELLER FUNCTIONALITY
	 * 
	 * This method allows Sellers to create auctions by entering auction details
	 */

	private void createAuction() {
		// name, description, start price, reserve price, start date, close date,

		// gets the item name for the auction
		System.out.println("Please enter the item name");
		String itemName = S.next();

		// gets the item description for the auction
		System.out.println("Please enter the item description");
		String itemDescription = S.next();

		// get the item start price for the auction
		System.out.println("Please enter the start price");
		double startPrice = S.nextDouble();

		// get the reserve price for the auction
		System.out.println("Please enter the reserve price");
		double reservePrice = S.nextDouble();

		S.nextLine();

		LocalDateTime closingDate = LocalDateTime.now();
		boolean validDate = false;

		// loops around until Seller enters a valid closing date
		while (!validDate) {
			// gets the auctions close date
			System.out.println("Please enter a closing date for the auction in yyyy-MM-dd HH:mm");
			String closeDate = S.nextLine();

			if (!(closeDate.contains("-") && closeDate.contains(":") & closeDate.length() <= 16)) {
				// invalid!
				System.out.println("Sorry that close date is not valid, please try again");
				continue;
			}
			// valid date format
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

			closingDate = LocalDateTime.parse(closeDate, formatter);

			// checks if closing date is valid
			if (closingDate.isBefore(LocalDateTime.now())) {
				// invalid closing date
				System.out.println("Sorry you have entered a date in the past, please try again");
				continue;
			}

			// the closing date is valid
			validDate = true;
		}

		// create the auction and add it to the list of auctions on the system
		Auction auction = new Auction(startPrice, reservePrice, closingDate, Status.STATUS_PENDING,
				new Item(itemDescription), seller);
		auctions.add(auction);

		System.out.println(
				"Auction is created, The auction will now be in a pending state and must be verified before it can be bid upon.");
	}

	/*
	 * BUYER FUNCTIONALITY
	 * 
	 * 
	 * This method will allow buyer to bid on a chosen auction
	 */
	private synchronized void bidOnAuction() {
		// retrieves all active auctions
		List<Auction> activeAuctions = auctions.stream().filter(o -> o.getStatus().equals(Status.STATUS_ACTIVE))
				.collect(Collectors.toList());

		if (activeAuctions.isEmpty()) {
			System.out.println("Sorry there ar currently no active auctions to bid on");
			return;
		}

		int option = 0;
		boolean isValid = false;
		// loop until they have chosen an active auction
		while (!isValid) {
			System.out.println("Please choose from the list of active auctions: (enter the Auction ID)");
			browseAuctions(auctions);

			option = S.nextInt();

			if (option > auctions.size() || option == 0) {
				System.out.println("Sorry the auction you are trying to place a bid on does not exist");
			} else {
				isValid = true;
			}
		}
		// get the auction the user has selected
		Auction auction = activeAuctions.get((option - 1));

		double minimumAmount = 0.0;

		// get the current bids from the auction
		if (auction.getBids() != null) {
			List<Bid> bids = auction.getBids();

			// get the maximum bid out of list of bids
			if (bids.size() > 0) {
				// get the maxmimum bid on the auction and set it as the minimum bid amount
				minimumAmount = bids.stream().collect(Collectors.maxBy(Comparator.comparingDouble(Bid::getAmount)))
						.get().getAmount();
			}
		}

		// sets bidding increments
		double maximumAmount = minimumAmount * 1.2;
		minimumAmount *= 1.1;
		boolean bidValid = false;

		if (maximumAmount == 0) {
			maximumAmount = auction.getReservePrice();
		}
		// loop until the bid is valid
		while (!bidValid) {
			System.out.println("Please enter the amount you would like to bid upon the auction between £"
					+ df.format(minimumAmount) + " and £" + df.format(maximumAmount));
			// get the bid
			double bid = S.nextDouble();

			if (bid <= maximumAmount && bid >= minimumAmount) {
				// valid bid
				bidValid = true;
				// create the bid
				Bid buyerBid = new Bid(bid, (Buyer) loggedIn, LocalDateTime.now());

				// place the bid onto the auction
				auction.placeBid(buyerBid);
				System.out.println(
						"Your bid of £" + df.format(bid) + " has successfully been placed on the chosen auction");

			} else {
				// invalid bid
				System.out.println("Sorry that bid was invalid, please try again");
			}
		}
	}

}
