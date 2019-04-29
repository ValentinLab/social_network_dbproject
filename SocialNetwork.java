import javax.swing.JOptionPane;

/**
 * Social Network (DataBase Project - Topic N°3)
 * SocialNetwork.java - main code
 * 
 * @author Fabian Devel, Valentin Perignon
 */

public class SocialNetwork {

	public static void main(String[] args) {

		// Variables	
		final String NETWORKNAME = "TINGAMER";		/** Name of the social network */
		int connection;								/** Connection to the database */
		boolean isConnected = false;				/** State of the connection of the user */
		String currentUser;							/** Login of the user currently connected */
		JOptionPane mainMenu = new JOptionPane();	/** Message Dialog */
		boolean isStopped = false;					/** State of the app */

			// DEBUG
			currentUser = "Valentin";
			isConnected = false;

		// Database connection
		connection = BD.ouvrirConnexion("localhost", "reseau", "root", "");

		// Menu
		do{
			if(!isConnected) { // the user is not connected
				// Display variables
				String[] choicesMessage = {"S'inscrire", "Se connecter",  "Quitter"};

				// Display
				int action = mainMenu.showOptionDialog(null, "Choississez votre action :", NETWORKNAME, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, choicesMessage, choicesMessage[2]);

				// Choices
				switch(action){
					case 0: // creation of an account
						Library.inscription(connection, NETWORKNAME);
						break;
					
					case 1: // connection of the user
						currentUser = Library.connexion(connection);
						isConnected = true;
						break;
					
					case 2: // stop the application
						isStopped = Library.stopApp();
					break;
				}
			} else {
				// Display variables
				String[] choicesMessage = {"Répondre à un rendez-vous", "Proposer un rendez-vous", "Suivre un utilisateur", "Rechercher un utilisateur", "Quitter"};
				String textMessage = "Bienvenue " + currentUser + ".\nQue souhaitez-vous faire ?";

				// Display
				int action = mainMenu.showOptionDialog(null, textMessage, NETWORKNAME, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, choicesMessage, choicesMessage[4]);

				// Choices
				switch(action){
					case 3: // search
						Library.searchUsers(connection, currentUser);
						break;
					
					case 2: // follow
						Library.followUser(connection, currentUser);
						break;

					case 1: // get appointment
						Library.getAppointment(connection, currentUser);
						break;

					case 0: // answer appointment
						Library.answerAppointment(connection, currentUser);
						break;
					
					case 4: // stop the application
						isStopped = Library.stopApp();
						break;
				}
			}
		} while(!isStopped);

		// Closing of the connection
		BD.fermerConnexion(connection);
	}

}