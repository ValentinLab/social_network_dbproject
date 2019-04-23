/**
 * Social Network (DataBase Project - Topic N°3)
 * Library.java - all functions
 * 
 * @author Fabian Devel, Valentin Perignon
 */

public class Library {

	/**
	 * Follow another use
	 * 
	 * @param connection Connection to the DB
	 * @param currentUser User currently connected
	 */
	public static void followUser(int connection, String currentUser) {
		// Variables
		String otherUser;
		int resultQuerry;

		// Title
		Ecran.afficherln("### SUIVI D'UN UTILISATEUR ###\n");

		// Other login entry
		Ecran.afficher("Veuillez-saisir le login de l'utilisateur à suivre: ");
		otherUser = Clavier.saisirString();
		while(otherUser.equals(currentUser)) {
			Ecran.afficher("Veuillez-saisir le login de l'utilisateur à suivre (différent de vous): ");
			otherUser = Clavier.saisirString();
		}
		Ecran.sautDeLigne();

		// Querry
		resultQuerry = BD.executerSelect(connection, "SELECT * FROM utilisateur WHERE utLogin = '" + otherUser + "'");

		// Checking of the querry result
		if(BD.suivant(resultQuerry)) {
			Ecran.afficherln("Il existe !");
		} else {
			Ecran.afficherln("L'utilisateur " + otherUser + " n'existe pas...");
		}
	}

}