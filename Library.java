import javax.swing.JOptionPane;

/**
 * Social Network (DataBase Project - Topic N°3)
 * Library.java - all functions
 * 
 * @author Fabian Devel, Valentin Perignon
 */

public class Library {

	// ------------------------------
	// Private methods
	// ------------------------------

	public static String saisieCorrecte( String typeMenu, String typeSaisie,  int nbChar, JOptionPane menu){
		String valeur;
		do{
			valeur = menu.showInputDialog(null, "Veuillez saisir votre " + typeSaisie + " : ", typeMenu + " - " + typeSaisie, JOptionPane.QUESTION_MESSAGE);
			if(valeur == null){
				return(null);
			}
			if(valeur.length() == 0){
				menu.showMessageDialog(null, "Ce champ est obligatoire, veuillez recommencer la saisie : " , "ERREUR - " + typeSaisie + " obligatoire", JOptionPane.ERROR_MESSAGE);
			}
			if(valeur.length() > nbChar){
				menu.showMessageDialog(null, "Votre " + typeSaisie + " ne peut pas excéder " + nbChar+ " caractéres, veuillez recommencer la saisie : " , "ERREUR - " + typeSaisie + " trop long !", JOptionPane.ERROR_MESSAGE);
			}
		} while(valeur.length() == 0 || valeur.length() > nbChar);
		return(valeur);
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

	// ------------------------------
	// Public methods
	// ------------------------------

	/**
	 * Stop the app
	 * 
	 * @return True if the app has to stop
	 */
	public static boolean stopApp() {
		// Variables
		boolean isStopped = false;
		int boxMessage = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment quitter l'application ?", "A bientôt ! ", JOptionPane.YES_NO_OPTION);

		// Treatment
		if(boxMessage == 0){
			isStopped = true;
		}

		return(isStopped);
	}

	public static void inscription (int connexion, String nomReseauSocial) {
		String motDePasse, login, sql, jvPref;
		JOptionPane menuInscription = new JOptionPane();
		login = saisieCorrecte("Inscription", "login", 30, menuInscription);
		if(login == null){
			return;
		}
		motDePasse = saisieCorrecte("Inscription","mot de passe", 10, menuInscription);
		if(motDePasse == null){
			return;
		}
		
		
		sql = "INSERT INTO utilisateur (utLogin, utPassword) VALUES ('" + login + "', '" + motDePasse + "')";	
		// insertion des données dans la table utilisateur
		int inscription = BD.executerUpdate(connexion, sql);
		while (inscription == -1){
			menuInscription.showMessageDialog(null, "Votre identifiant doit étre déjé pris, veuillez saisir en un autre : ", "ERREUR -  Identifiant déjé utilisé", JOptionPane.ERROR_MESSAGE);
			login = saisieCorrecte("inscription", "login", 30, menuInscription);
			if(login == null){
				return;
			}
		}
		
		menuInscription.showMessageDialog(null, "Félicitations pour votre inscription sur " + nomReseauSocial + ". Nous allons maintenant vous demander des informations pour compléter votre profil ! " , "Renseignement complémentaire", JOptionPane.INFORMATION_MESSAGE);
		jvPref = saisieCorrecte("Info complémentaire", "jeu vidéo préféré", 50, menuInscription);
		if(jvPref == null){
			return;
		}
		
		sql = "INSERT INTO jeu (jeTitre, jeJoueur) VALUES (" + jvPref + ", " + login + ")";
		// insertion des données dans la table jeu	
		inscription = BD.executerUpdate(connexion, sql);
		menuInscription.showMessageDialog(null, "Et voilé, votre profil est finalisé ! Nous vous souhaitons une agréable utilisation de " + nomReseauSocial + " ! ", "Inscription finalisée", JOptionPane.INFORMATION_MESSAGE);
	}

	public static String connexion(int connexion) {
		String login, motDePasse;
		JOptionPane menuConnexion = new JOptionPane();
		login = saisieCorrecte("Connexion", "login", 30, menuConnexion);
		if(login == null){
			return "";
		}
		motDePasse = saisieCorrecte("Connexion", "mot de passe", 10, menuConnexion);
		if(motDePasse == null){
			return "";
		}
		//  tests des données avec celles présentes dans la base de donnée
		int res = BD.executerSelect(connexion, "SELECT* FROM utilisateur");
		while (BD.suivant(res)) {
			if( BD.attributString(res,"utLogin").equals(login)){
				if(BD.attributString(res,"utPassword").equals(motDePasse)){
					menuConnexion.showMessageDialog(null, "Bonjour " + login + " ! ");
					BD.suivant(res);
					BD.fermerResultat(res);
				}
			}
		}
		BD.fermerResultat(res);

		return login;
	}

	/**
	 * Find users with same favorite games
	 * @param connection Connection to the DB
	 * @param currentUser User currently connected
	 */
	public static void searchUsers(int connection, String currentUser) {
		// Variables
		String[] favoriteGames;
		int nbFavoriteGames;
		int selectQuerry;
		int choice = 1;

		// Display variables
		JOptionPane boxMessage = new JOptionPane();
		String boxTitle = "Recherche d'utilisateurs";
		String showMessage;
		String answerMessString;

		// Querry (get the number of users followed by the current user with no appointment)
		selectQuerry = BD.executerSelect(connection, "SELECT COUNT(*) AS nbGames FROM jeu WHERE jeJoueur = '" + currentUser + "'");

		// Checking of the querry result
		BD.suivant(selectQuerry);
		nbFavoriteGames = BD.attributInt(selectQuerry, "nbGames");
		BD.fermerResultat(selectQuerry);
		
		// Display of favorite games
		favoriteGames = new String[nbFavoriteGames];
		switch(nbFavoriteGames) {
			case 0: // no favorite game
				boxMessage.showMessageDialog(null, "Vous n'aimez aucun jeu pour le moment.", boxTitle, JOptionPane.INFORMATION_MESSAGE);
				choice = 0;
				break;
				 
			case 1: // only one favorite game
				// Querry (get the list of favorite games)
				selectQuerry = BD.executerSelect(connection, "SELECT jeTitre FROM jeu WHERE jeJoueur = '" + currentUser + "'");
				putStringQuerryInTab(favoriteGames, selectQuerry, "jeTitre");
				BD.fermerResultat(selectQuerry);

				// Choose to search other users
				int answer = boxMessage.showConfirmDialog(null, "Votre jeu favoris est " + favoriteGames[0] + ". Voulez-vous trouver des utilisateurs qui aiment ce jeu ?", boxTitle, JOptionPane.YES_NO_OPTION);
				if(answer == 1) {
					choice = 0;
				}
				break;
			
			default: // many favorite games
				// Querry (get the list of favorite games)
				selectQuerry = BD.executerSelect(connection, "SELECT jeTitre FROM jeu WHERE jeJoueur = '" + currentUser + "'");
				putStringQuerryInTab(favoriteGames, selectQuerry, "jeTitre");
				BD.fermerResultat(selectQuerry);

				// Choice of the game
				showMessage = "";
				answerMessString = "";
				showMessage = "Voici la liste des jeux que vous aimez:\n";
				for(int i=0; i<nbFavoriteGames; i++) {
					showMessage += " " + (i+1) + " - " + favoriteGames[i] + "\n";
				}
				showMessage += "\nNuméro du jeu pour lequel vous souhaitez trouver des joueurs aimant le même jeu:";
				answerMessString = boxMessage.showInputDialog(null, showMessage, boxTitle, JOptionPane.QUESTION_MESSAGE);
				if(answerMessString == null || answerMessString.equals(""))
					return;
				choice = Integer.parseInt(answerMessString);
				break;
		}

		// Search of users
		if(choice > 0) {
			// Querry (get the other users)
			selectQuerry = BD.executerSelect(connection, "SELECT jeJoueur FROM jeu WHERE jeTitre = '" + favoriteGames[choice-1] + "' AND jeJoueur != '" + currentUser + "'");

			// Display of users
			if(BD.suivant(selectQuerry)) {
				showMessage = "Liste des joueurs appréciant " + favoriteGames[choice-1] + " :\n";
				do {
					showMessage += " - " + BD.attributString(selectQuerry, "jeJoueur") + "\n";
				} while(BD.suivant(selectQuerry));
				BD.fermerResultat(selectQuerry);
			} else {
				showMessage = "Il n'y a aucun autre joueur...";
			}
		} else {
			showMessage = "Vous n'avez effectué aucune recherche.";
		}
		boxMessage.showMessageDialog(null, showMessage, boxTitle, JOptionPane.INFORMATION_MESSAGE);
	}

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

		// Display variables
		JOptionPane boxMessage = new JOptionPane();
		String boxTitle = "Suivi d'utilisateurs";
		String showMessage;

		// Login of user to follow entry
		do {
			showMessage = "";
			if(otherUser.equals(currentUser)) {
				showMessage += "/!\\ ATTENTION ! Vous devez entrer un login différent du votre...\n";
			}
			showMessage += "Veuillez saisir le login de l'utilisateur à suivre: ";
			otherUser = boxMessage.showInputDialog(null, showMessage, boxTitle, JOptionPane.QUESTION_MESSAGE);
			if(otherUser == null || otherUser.equals(""))
				return;
		} while(otherUser.equals(currentUser));

		// Querry (check if the user exits)
		selectQuerry = BD.executerSelect(connection, "SELECT * FROM utilisateur WHERE utLogin = '" + otherUser + "'");

		// Checking of the querry result
		if(BD.suivant(selectQuerry)) { // the user exists
			// Querry (check if the current user already follow the other)
			selectQuerry = BD.executerSelect(connection, "SELECT * FROM suivi WHERE suSuiveur = '" + currentUser + "' AND suSuivi = '" + otherUser + "'");
			if(BD.suivant(selectQuerry)) { // already follow
				showMessage = "Vous suivez déjà l'utilisateur " + otherUser + ".";
			} else { // not followed
				// Querry (update of the table suivi)
				updateQuery = BD.executerUpdate(connection, "INSERT INTO suivi (suSuiveur, suSuivi) VALUES ('" + currentUser + "', '" + otherUser + "')");
				showMessage = "Et voilà ! Vous suivez maintenant l'utilisateur " + otherUser + ".";
			}
		} else { // the user does not exists
			showMessage = "/!\\ ATTENTION ! L'utilisateur " + otherUser + " n'existe pas...";
			followUser(connection, currentUser);
		}
		BD.fermerResultat(selectQuerry);
		boxMessage.showMessageDialog(null, showMessage, boxTitle, JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Get an appoitment with a followed user
	 * 
	 * @param connection Connection to the DB
	 * @param currentUser User currently connected
	 */
	public static void getAppointment(int connection, String currentUser) {
		// Variables
		String[] otherUsers;
		int nbFollowedUsers;
		int indexUserToMeet = -1;
		int selectQuerry;
		int updateQuery;

		// Display variables
		JOptionPane boxMessage = new JOptionPane();
		String boxTitle = "Proposition de rendez-vous";
		String showMessage;

		// Querry (get the number of users followed by the current user with no appointment)
		selectQuerry = BD.executerSelect(connection, "SELECT COUNT(*) AS NbUsers FROM suivi WHERE suSuiveur = '" + currentUser + "' AND suRDV IS NULL");

		// Checking of the querry result
		BD.suivant(selectQuerry);
		nbFollowedUsers = BD.attributInt(selectQuerry, "NbUsers");
		BD.fermerResultat(selectQuerry);

		// Select a user and offer an appoitment
		otherUsers = new String[nbFollowedUsers];
		switch(nbFollowedUsers) {
			case 0: // can't offer any appointment
				showMessage = "Vous ne suivez aucun utilisateur ou vous avez déjà proposé des rendez-vous à chacun d'entre eux, il est donc impossible de proposer un rendez-vous.\n";
				boxMessage.showMessageDialog(null, showMessage, boxTitle, JOptionPane.INFORMATION_MESSAGE);
				break;

			case 1: // can offer only one appointment
				// Querry (get the user)
				selectQuerry = BD.executerSelect(connection, "SELECT suSuivi FROM suivi WHERE suSuiveur = '" + currentUser + "' AND suRDV IS NULL");
				putStringQuerryInTab(otherUsers, selectQuerry, "suSuivi");

				// Choice of the user
				int answer;
				showMessage = "Vous ne pouvez proposer un rendez-vous qu'à l'utilisateur " + otherUsers[0] + ".\n";
				showMessage += "Souhaitez-vous le faire ?";
				answer = boxMessage.showConfirmDialog(null, showMessage, boxTitle, JOptionPane.YES_NO_OPTION);
				if(answer == JOptionPane.OK_OPTION)
					indexUserToMeet = 0;
				BD.fermerResultat(selectQuerry);
				break;

			default: // can offer many appointments
				// Querry (get the list of users)
				selectQuerry = BD.executerSelect(connection, "SELECT suSuivi FROM suivi WHERE suSuiveur = '" + currentUser + "' AND suRDV IS NULL");
				putStringQuerryInTab(otherUsers, selectQuerry, "suSuivi");

				// Choice of the user
				showMessage = "Voici la liste des utilisateurs auxquels vous pouvez proposer un rendez-vous:\n";
				showMessage += " 0 - Personne\n";
				for(int i=0; i<nbFollowedUsers; i++) {
					showMessage += " " + (i+1) + " - " + otherUsers[i] + "\n";
				}
				do {
					if(indexUserToMeet > nbFollowedUsers) {
						showMessage += "/!\\ ATTENTION ! Ce numéro n'est pas valide.";
					}
					showMessage += "Numéro de l'utilisateur que vous souhaitez rencontrer:";
					indexUserToMeet = Integer.parseInt(boxMessage.showInputDialog(null, showMessage, boxTitle, JOptionPane.QUESTION_MESSAGE));
				} while(indexUserToMeet > nbFollowedUsers);		
				indexUserToMeet = indexUserToMeet - 1;
				BD.fermerResultat(selectQuerry);
				break;
		}

		// Get an appointment
		if(indexUserToMeet < 0) {
			showMessage = "Vous n'avez proposé aucun rendez-vous.";
		} else {
			updateQuery = BD.executerUpdate(connection, "UPDATE suivi SET suRDV = 0 WHERE suSuiveur = '" + currentUser + "' AND suSuivi = '" + otherUsers[indexUserToMeet] + "'");
			BD.fermerResultat(updateQuery);
			showMessage = "Vous avez proposé un rendez-vous à " + otherUsers[indexUserToMeet];
		}
		boxMessage.showMessageDialog(null, showMessage, boxTitle, JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Be able to answer appointments
	 * 
	 * @param connection Connection to the DB
	 * @param currentUser User currently connected
	 */
	public static void answerAppointment(int connection, String currentUser) {
		// Variables
		String[] appointmentUsers;
		int nbAppointmentUsers;
		int indexUser = 0;
		int selectQuerry;
		int updateQuery;

		// Display variables
		JOptionPane boxMessage = new JOptionPane();
		String boxTitle = "Répondre à une proposition de rendez-vous";
		String showMessage;

		// Querry (get the number of users with appointment)
		selectQuerry = BD.executerSelect(connection, "SELECT COUNT(*) AS NbAppointment FROM suivi WHERE suSuivi = '" + currentUser + "' AND suRDV = 0");

		// Checking of the result of the query
		BD.suivant(selectQuerry);
		nbAppointmentUsers = BD.attributInt(selectQuerry, "NbAppointment");
		BD.fermerResultat(selectQuerry);
		appointmentUsers = new String[nbAppointmentUsers];

		// Answer to other users
		switch(nbAppointmentUsers) {
			case 0: // no appointment
				showMessage = "Aucun utilisateur ne vous a proposé de rendez-vous pour le moment.";
				boxMessage.showMessageDialog(null, showMessage, boxTitle, JOptionPane.INFORMATION_MESSAGE);
				break;

			case 1: // one appointment
				// Querry (get other users)
				selectQuerry = BD.executerSelect(connection, "SELECT suSuiveur FROM suivi WHERE suSuivi = '" + currentUser + "' AND suRDV = 0");
				putStringQuerryInTab(appointmentUsers, selectQuerry, "suSuiveur");
				BD.fermerResultat(selectQuerry);

				// Select a user to answer
				int answer;
				showMessage = "Vous avez une proposition de rendez-vous de la part de " + appointmentUsers[0] + ".\nSouhaitez-vous lui répondre ?";
				answer = boxMessage.showConfirmDialog(null, showMessage, boxTitle, JOptionPane.YES_NO_OPTION);
				if(answer == JOptionPane.OK_OPTION)
					indexUser = 1;
				break;

			default: // many appointments
				// Querry (get other users)
				selectQuerry = BD.executerSelect(connection, "SELECT suSuiveur FROM suivi WHERE suSuivi = '" + currentUser + "' AND suRDV = 0");
				putStringQuerryInTab(appointmentUsers, selectQuerry, "suSuiveur");
				BD.fermerResultat(selectQuerry);

				// Select a user to answer
				showMessage = "Voici la liste des utilisateurs qui vous ont proposé un rendez-vous:\n";
				for(int i=0; i<nbAppointmentUsers; i++) {
					showMessage += " " + (i+1) + " - " + appointmentUsers[i] + "\n";
				}
				do {
					if(indexUser > nbAppointmentUsers) {
						showMessage += "/!\\ ATTENTION Le numéro n'est pas valide.";
					}
					showMessage += "Numéro de l'utilisateur auquel vous souhaitez répondre: ";
					indexUser = Integer.parseInt(boxMessage.showInputDialog(null, showMessage, boxTitle, JOptionPane.QUESTION_MESSAGE));
				} while(indexUser > nbAppointmentUsers);
				break;
		}

		// Answer appointment
		if(indexUser > 0) {
			// Accept or not the answer
			int answer;
			showMessage = "Souhaitez-vous accepter le rendez-vous de " + appointmentUsers[indexUser-1] + " ?";
			answer = boxMessage.showConfirmDialog(null, showMessage, boxTitle, JOptionPane.YES_NO_OPTION);

			// Send the answer
			if(answer == JOptionPane.OK_OPTION) {
				updateQuery = BD.executerUpdate(connection, "UPDATE suivi SET suRDV = 1 WHERE suSuiveur =  '" + appointmentUsers[indexUser-1] + "' AND suSuivi = '" + currentUser + "'");
				showMessage = "Vous avez accepté le rendez-vous avec " + appointmentUsers[indexUser-1] + ".";
			} else {
				updateQuery = BD.executerUpdate(connection, "UPDATE suivi SET suRDV = -1 WHERE suSuiveur =  '" + appointmentUsers[indexUser-1] + "' AND suSuivi = '" + currentUser + "'");
				showMessage = "Vous avez refusé le rendez-vous avec " + appointmentUsers[indexUser-1] + ".";
			}
			boxMessage.showMessageDialog(null, showMessage, boxTitle, JOptionPane.INFORMATION_MESSAGE);
			BD.fermerResultat(updateQuery);
		}
	}

}