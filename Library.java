import java.security.spec.ECParameterSpec;

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
		BD.fermerResultat(selectQuerry);
	}

	/**
	 * Put all string resultats from a query in a tab
	 * 
	 * @param tab The tab
	 * @param querry The querry
	 * @param attribut The attribut put in the tab
	 */
	private static void putStringQuerryInTab(String[] tab, int querry, String attribut) {
		// Treatment
		for(int i=0; i<tab.length; i++) {
			BD.suivant(querry);
			tab[i] = BD.attributString(querry, attribut);
		}
	}

	/**
	 * Get an appoitment with a folloed user
	 * 
	 * @param connection Connection to the DB
	 * @param currentUser User currently connected
	 */
	public static void getAppointment(int connection, String currentUser) {
		// Variables
		String[] otherUsers;
		int nbFollowedUsers;
		int nbUserToMeet = -1;
		int selectQuerry;
		int updateQuery;

		// Querry (get the number of users followed by the current user)
		selectQuerry = BD.executerSelect(connection, "SELECT COUNT(*) AS NbUsers FROM suivi WHERE suSuiveur = '" + currentUser + "' AND suRDV = 0");

		// Checking of the querry result
		BD.suivant(selectQuerry);
		nbFollowedUsers = BD.attributInt(selectQuerry, "NbUsers");
		BD.fermerResultat(selectQuerry);

		// Select the user and get an appoitment
		otherUsers = new String[nbFollowedUsers];
		switch(nbFollowedUsers) {
			case 0:
				Ecran.afficherln("Vous ne suivez aucun utilisateur ou vous avez déjà proposé des rendez-vous à chacun d'entre eux, il est donc impossible de proposer un rendez-vous.\n");
				break;
			case 1:
				selectQuerry = BD.executerSelect(connection, "SELECT suSuivi FROM suivi WHERE suSuiveur = '" + currentUser + "' AND suRDV = 0");
				putStringQuerryInTab(otherUsers, selectQuerry, "suSuivi");

				// choice of the user
				char answer;
				Ecran.afficherln("Vous ne pouvez proposer un rendez-vous qu'à l'utilisateur " + otherUsers[0] + ".");
				Ecran.afficher("Souhaitez-vous le faire ? [Y/N] ");
				answer = Clavier.saisirChar();
				if(answer == 'Y') {
					nbUserToMeet = 0;
				}
				Ecran.sautDeLigne();
				BD.fermerResultat(selectQuerry);
				break;
			default:
				selectQuerry = BD.executerSelect(connection, "SELECT suSuivi FROM suivi WHERE suSuiveur = '" + currentUser + "'");
				putStringQuerryInTab(otherUsers, selectQuerry, "suSuivi");

				// choice of the user
				Ecran.afficherln("Voici la liste des utilisateurs auxquels vous pouvez proposer un rendez-vous:");
				Ecran.afficherln(" 0 - Personne");
				for(int i=0; i<nbFollowedUsers; i++) {
					Ecran.afficherln(" " + (i+1) + " - " + otherUsers[i]);
				}
				do {
					if(nbUserToMeet > nbFollowedUsers) {
						Ecran.afficherln("/!\\ ATTENTION ! Ce numéro n'est pas valide.");
					}
					Ecran.afficher("Numéro de l'utilisateur que vous souhaitez rencontrer: ");
					nbUserToMeet = Clavier.saisirInt();
					Ecran.sautDeLigne();
				} while(nbUserToMeet > nbFollowedUsers);		
				nbUserToMeet = nbUserToMeet - 1;
				BD.fermerResultat(selectQuerry);
				break;
		}

		// Get an appointment
		if(nbUserToMeet < 0) {
			Ecran.afficherln("Vous n'avez proposé aucun rendez-vous.");
		} else {
			updateQuery = BD.executerUpdate(connection, "UPDATE suivi SET suRDV = 1 WHERE suSuiveur = '" + currentUser + "' AND suSuivi = '" + otherUsers[nbUserToMeet] + "'");
			BD.fermerResultat(updateQuery);
			Ecran.afficherln("Vous avez proposé un rendez-vous à " + otherUsers[nbUserToMeet]);
		}
	}

}