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

		/*
		// Fin users with same favorite games
		Library.searchUsers(connection, currentUser);
		*/
		
		/*
		// Following of a user
		Library.followUser(connection, currentUser);
		*/

		/*
		// Get an appointment with a user
		Library.getAppointment(connection, currentUser);
		*/

		// Answer appointment
		Library.answerAppointment(connection, currentUser);
	}

}