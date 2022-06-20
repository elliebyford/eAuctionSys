package eAuctionSystem;

import java.io.Serializable;
import java.util.Objects;


/**
 * The representation of an item of the auction
 */
public class Item implements Serializable {
//	private final String itemName;
	private final String description;

	public Item(String description) {
//		this.itemName = itemName;
		this.description = description;
	}


	public String getDescription() {
		return description;
	}
	

	public String toString() {
		return String.format("{ id: %s, name: %s, description: %s }", description);
	}

}
