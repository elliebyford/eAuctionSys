package eAuctionSystem;

import java.io.Serializable;
import java.sql.Date;

/**
 * 
 * <i> User is the abstract class for all users allowing to log on successfully.
 * </i>
 * 
 * @author <i>Ellie Byford, Macy Esho, Becca Ellison</i>
 * @version <i>1.0</i>
 * @see <i> Buyer</i>
 * @see <i> Seller</i>
 * @see <i> Admin</i>
 * @since <i>1.0</i>
 *
 */

public abstract class User implements Blockable, Serializable {

	protected String username, password;


	/*
	 * This constructor sets values for username and password of each user
	 */
	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	@Override
	public String toString() {
		/*
		 * get username
		 */
		return getUserName();
	}

	public boolean checkPassword(String userPassword) {

		return password.equals(getPassword());

		// return false;
	}

	/*
	 * This method returns the user's username
	 */
	public String getUserName() {
		return username;
	}

	/*
	 * This method returns the user's password
	 */
	private String getPassword() {
		return password;
	}

	/*
	 * This method will set the user's username
	 */
	public String setUsername(String name) {
		return this.username = name;
	}

	/*
	 * This method will set the user's password
	 */
	private void setPassword(String password) {
		this.password = password;
	}



	public static boolean authenticateUser(String username2, String password2) {

		return false;
	}
}
