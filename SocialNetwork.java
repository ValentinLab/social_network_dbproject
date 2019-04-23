/**
 * Social Network (DataBase Project - Topic N°3)
 * SocialNetwork.java - main code
 * 
 * @author Fabian Devel, Valentin Perignon
 */

public class SocialNetwork {

	public static void main(String[] args) {

		// Variables
		/** Connection to the database */
		int connection;
		/** Login of the user currently connected */
		String currentUser;

		// Database connection
		connection = BD.ouvrirConnexion("localhost", "reseau", "root", "");

			// DEBUG
			currentUser = "Valentin";

		// Following of a user
		Library.followUser(connection, currentUser);
	}

}