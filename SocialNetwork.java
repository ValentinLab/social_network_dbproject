import javax.swing.JOptionPane;

/**
 * Social Network (DataBase Project - Topic N°3) SocialNetwork.java - main code
 * 
 * @author Fabian Devel, Valentin Perignon
 */

public class SocialNetwork {

	public static void main(String[] args) {

		// Variables
		final String NETWORKNAME = "TinGamer"; /** Name of the social network */
		int connection; /** Connection to the database */
		boolean isConnected = false; /** State of the connection of the user */
		String currentUser = ""; /** Login of the user currently connected */
		JOptionPane mainMenu = new JOptionPane(); /** Message Dialog */
		boolean isStopped = false; /** State of the app */

		// Database connection
		connection = BD.ouvrirConnexion("localhost", "reseau", "root", "");

		// Menu
		do {
			if (!isConnected) { // the user is not connected
				// Display variables
				String[] choicesMessage = { "Quitter", "Se connecter", "S'inscrire" };

				// Display
				int action = mainMenu.showOptionDialog(null, "Choississez votre action :", NETWORKNAME,
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, choicesMessage,
						choicesMessage[0]);

				// Choices
				switch (action) {
				case 2: // creation of an account
					Library.inscription(connection, NETWORKNAME);
					break;

				case 1: // connection of the user
					String[] connectionAnswer = Library.connexion(connection);
					if (connectionAnswer[0].equals("true")) {
						isConnected = true;
						currentUser = connectionAnswer[1];
					}
					break;

				case 0: // stop the application
					isStopped = Library.stopApp();
					break;
				}
			} else {
				// Display variables
				String[] choicesMessage = { "Se déconnecter", "Répondre à un rendez-vous", "Proposer un rendez-vous",
						"Suivre un utilisateur", "Rechercher un utilisateur", "Insérer un jeu favori" };
				String textMessage = "Bienvenue " + currentUser + ".\nQue souhaitez-vous faire ?";

				// Display
				int action = mainMenu.showOptionDialog(null, textMessage, NETWORKNAME, JOptionPane.YES_NO_CANCEL_OPTION,
						JOptionPane.PLAIN_MESSAGE, null, choicesMessage, choicesMessage[0]);

				// Choices
				switch (action) {
				case 5: // new favorite game
					Library.insertFavoriteVideoGames(connection, currentUser);
					break;

				case 4: // search
					Library.searchUsers(connection, currentUser);
					break;

				case 3: // follow
					Library.followUser(connection, currentUser);
					break;

				case 2: // get appointment
					Library.getAppointment(connection, currentUser);
					break;

				case 1: // answer appointment
					Library.answerAppointment(connection, currentUser);
					break;

				case 0: // stop the application
					isConnected = false;
					break;
				}
			}
		} while(!isStopped);

		// Closing of the connection
		BD.fermerConnexion(connection);
	}

}