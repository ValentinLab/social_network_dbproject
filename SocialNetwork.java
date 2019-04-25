/**
 * Social Network (Database Project - Topic N°3)
 * SocialNetwork.java
 * 
 * @author Fabian Devel, Valentin Perignon
 */
 
 //biblioth�que pour les boites de dialogues
import javax.swing.JOptionPane;

public class SocialNetwork {
	
	

	public static void main(String[] args) {
		// Variables 
		final String nomReseauSocial = "TINGAMER";
		String loginAdmin = "devel";
		String nomBD = loginAdmin + "_BD";
		String adresseReseau = "172.20.128.64";
		String motDePasse, login, sql;
		int action;
		JOptionPane menuPrincipal = new JOptionPane(), menuInscription = new JOptionPane(), menuConnexion = new JOptionPane();
		
		//Connexion � la base de donn�e
		//int connexion = BD.ouvrirConnexion(adresseReseau, nomBD, loginAdmin, loginAdmin);
		
		String[] choix = {"Inscription", "Connexion", "Recherche"};
		int rang = menuPrincipal.showOptionDialog(null, "Choississez votre action ", "Menu Principal", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, choix, choix[2]);
		action = rang;
		
		switch(action){
			//Inscription au r�seau social
			case 0 : {
				login = menuInscription.showInputDialog(null, "Veuillez saisir votre identifiant : ", "Inscription", JOptionPane.QUESTION_MESSAGE);
				motDePasse = menuInscription.showInputDialog(null, "Veuillez saisir votre mot de passe (10 caract�res maximum)", "Inscription", JOptionPane.QUESTION_MESSAGE);
				while(motDePasse.length() > 10){
					menuInscription.showMessageDialog(null, "Votre mot de passe doit faire moins de 10 caract�res. Saississez un autre mot de passe :" , "ERREUR - Mot de passe trop long", JOptionPane.ERROR_MESSAGE);
					motDePasse = menuInscription.showInputDialog(null, "Veuillez saisir votre mot de passe (10 caract�res maximum)", "Inscription", JOptionPane.QUESTION_MESSAGE);
				}
				sql = "INSERT INTO utilisateur (utLogin, utPassword) VALUES (" + login + ", " + motDePasse + ")";	
				// insertion des donn�es dans la table utilisateur
				/*int inscription = BD.executerUpdate(connexion, sql);
				while (inscription == -1){
					Ecran.afficherln("Votre identifiant doit �tre d�j� pris, veuillez saisir en un autre : ");
					login = Clavier.saisirString();
				}*/
				
				menuInscription.showMessageDialog(null, "F�licitations pour votre inscription sur " + nomReseauSocial + ". Nous allons maintenant vous demandez des informations pour compl�ter votre profil ! " , "Renseignement compl�mentaire", JOptionPane.INFORMATION_MESSAGE);
				String jvPref = menuInscription.showInputDialog(null, "Pour commencer, quelle est votre jeu vid�o pr�f�r� ? ", "Informations compl�mentaires", JOptionPane.QUESTION_MESSAGE);
				sql = "INSERT INTO jeu (jeTitre, jeJoueur) VALUES (" + jvPref + login + ")";
				// insertion des donn�es dans la table jeu	
				//inscription = BD.executerUpdate(connexion, sql);
				menuInscription.showMessageDialog(null, "Et voil�, votre profil est finalis� ! Nous vous souhaitons une agr�able utilisation de " + nomReseauSocial + " ! ", "Inscription finalis�e", JOptionPane.INFORMATION_MESSAGE);
			}
			break;	
			
			//Connexion au r�seau social
			case 1 : {
				
			}
			break;
			
			//Recherche d'autres utilisateurs
			case 2 : {
				
			}
			break;
		}
		
	}

}