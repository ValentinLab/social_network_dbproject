/**
 * Social Network (DataBase Project - Topic N°3)
 * Library.java - all functions
 * 
 * @author Fabian Devel, Valentin Perignon
 */

public class Library {

	/**
	 * Follow another user
	 * 
	 * @param connection Connection to the DB
	 * @param currentUser User currently connected
	 */
	public static void followUser(int connection, String currentUser) {
		// Variables
		String otherUser = "";
		int selectQuerry;
		int updateQuery;

		// Login of user to follow entry
		do {
			if(otherUser.equals(currentUser)) {
				Ecran.afficherln("/!\\ ATTENTION ! Vous devez entrer un login différent du votre...");
			}
			Ecran.afficher("Veuillez saisir le login de l'utilisateur à suivre: ");
			otherUser = Clavier.saisirString();
			Ecran.sautDeLigne();
		} while(otherUser.equals(currentUser));

		// Querry (check if the user exits)
		selectQuerry = BD.executerSelect(connection, "SELECT * FROM utilisateur WHERE utLogin = '" + otherUser + "'");

		// Checking of the querry result
		if(BD.suivant(selectQuerry)) { // the user exists
			// Querry (check if the current user already follow the other)
			selectQuerry = BD.executerSelect(connection, "SELECT * FROM suivi WHERE suSuiveur = '" + currentUser + "' AND suSuivi = '" + otherUser + "'");
			if(BD.suivant(selectQuerry)) { // already follow
				Ecran.afficherln("Vous suivez déjà l'utilisateur " + otherUser + ".");
			} else { // not followed
				// Querry (update of the table suivi)
				updateQuery = BD.executerUpdate(connection, "INSERT INTO suivi (suSuiveur, suSuivi, suRDV) VALUES ('" + currentUser + "', '" + otherUser + "', 0)");
				Ecran.afficherln("Et voilà ! Vous suivez maintenant l'utilisateur " + otherUser + ".");
			}
		} else { // the user does not exists
			Ecran.afficherln("/!\\ ATTENTION ! L'utilisateur " + otherUser + " n'existe pas...");
			followUser(connection, currentUser);
		}
	}

}