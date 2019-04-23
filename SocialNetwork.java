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

			// /!\ DEBUG
			currentUser = "Valentin";

		// Database connection
		connection = BD.ouvrirConnexion("localhost", "reseau", "root", "");

		// Following of a user
		Library.followUser(connection, currentUser);
	}

}