/**
 * Social Network (DataBase Project - Topic N°3)
 * SocialNetwork.java - main code
 * 
 * @author Fabian Devel, Valentin Perignon
 */

import javax.swing.JOptionPane;

public class SocialNetwork {

	public static void main(String[] args) {

		// Variables	
		final String NETWORKNAME = "TINGAMER";		/** Name of the social network */
		int connection;								/** Connection to the database */
		boolean isConnected = false;				/** State of the connection of the user */
		String currentUser;							/** Login of the user currently connected */
		JOptionPane mainMenu = new JOptionPane();	/** Box */
		boolean arret = false;						/** State of the app */

			// DEBUG
			currentUser = "Valentin";
			isConnected = true;

		// Database connection
		connection = BD.ouvrirConnexion("localhost", "reseau", "root", "");

		// Menu
		do{
			if(!isConnected) { // the user is not connected
				// Display variables
				String[] choices = {"S'inscrire", "Se connecter",  "Quitter"};
				int action = mainMenu.showOptionDialog(null, "Choississez votre action :", NETWORKNAME, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, choices, choices[2]);

				// Choices
				switch(action){
					case 0: // creation of an account
						//Library.inscription(connection, NETWORKNAME);
						break;
					
					case 1: // connection of the user
						//Library.connexion(connection);
						break;
					
					case 2: // stop the application
						//arret = Library.stopApp();
					break;
				}
			} else {
				// Display variables
				String[] choices = {"Rechercher un utilisateur", "Suivre un utilisateur", "Proposer un rendez-vous", "Répondre à un rendez-vous", "Quitter"};
				int action = mainMenu.showOptionDialog(null, "Choississez votre action :", NETWORKNAME, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, choices, choices[4]);

				// Choices
				switch(action){
					case 0: // search
						Library.searchUsers(connection, currentUser);
						break;
					
					case 1: // follow
						Library.followUser(connection, currentUser);
						break;

					case 2: // get appointment
						Library.getAppointment(connection, currentUser);
						break;

					case 3: // answer appointment
						Library.answerAppointment(connection, currentUser);
						break;
					
					case 4: // stop the application
						//arret = Library.stopApp();
						break;
				}
			}
		} while(!arret);

		// Closing of the connection
		BD.fermerConnexion(connection);
	}

}