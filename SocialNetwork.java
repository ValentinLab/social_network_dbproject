/**
 * Social Network (Database Project - Topic NÂ°3)
 * SocialNetwork.java
 * 
 * @author Fabian Devel, Valentin Perignon
 */
 
 //bibliothèque pour les boites de dialogues
import javax.swing.JOptionPane;

public class SocialNetwork {
	
	public static void inscription (String nomReseauSocial) {
		String motDePasse, login, sql;
		JOptionPane menuInscription = new JOptionPane();
		do{
			login = menuInscription.showInputDialog(null, "Veuillez saisir votre identifiant : ", "Inscription", JOptionPane.QUESTION_MESSAGE);
			while(login == null){
				return;
			}
		} while(login.length() == 0);
		motDePasse = menuInscription.showInputDialog(null, "Veuillez saisir votre mot de passe (10 caractères maximum)", "Inscription", JOptionPane.QUESTION_MESSAGE);
		if(motDePasse == null){
			return;
		}
		while(motDePasse.length() > 10){
			menuInscription.showMessageDialog(null, "Votre mot de passe doit faire moins de 10 caractères. Saississez un autre mot de passe :" , "ERREUR - Mot de passe trop long", JOptionPane.ERROR_MESSAGE);
			motDePasse = menuInscription.showInputDialog(null, "Veuillez saisir votre mot de passe (10 caractères maximum)", "Inscription", JOptionPane.QUESTION_MESSAGE);
		}
		
		sql = "INSERT INTO utilisateur (utLogin, utPassword) VALUES (" + login + ", " + motDePasse + ")";	
		// insertion des données dans la table utilisateur
		/*int inscription = BD.executerUpdate(connexion, sql);
		while (inscription == -1){
			Ecran.afficherln("Votre identifiant doit être déjà pris, veuillez saisir en un autre : ");
			login = menuInscription.showInputDialog(null, "Veuillez saisir votre identifiant : ", "Inscription", JOptionPane.QUESTION_MESSAGE);
		}*/
		
		menuInscription.showMessageDialog(null, "Félicitations pour votre inscription sur " + nomReseauSocial + ". Nous allons maintenant vous demandez des informations pour compléter votre profil ! " , "Renseignement complémentaire", JOptionPane.INFORMATION_MESSAGE);
		String jvPref = menuInscription.showInputDialog(null, "Pour commencer, quelle est votre jeu vidéo préféré ? ", "Informations complémentaires", JOptionPane.QUESTION_MESSAGE);
		sql = "INSERT INTO jeu (jeTitre, jeJoueur) VALUES (" + jvPref + login + ")";
		// insertion des données dans la table jeu	
		//inscription = BD.executerUpdate(connexion, sql);
		menuInscription.showMessageDialog(null, "Et voilà, votre profil est finalisé ! Nous vous souhaitons une agréable utilisation de " + nomReseauSocial + " ! ", "Inscription finalisée", JOptionPane.INFORMATION_MESSAGE);
	}

	public static boolean arretAppli (){
		boolean arret = false;
		int n = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment quitter l'application ?", "A bientôt ! ", JOptionPane.YES_NO_OPTION);
		if(n == 0){
			arret = true;
		}
		return(arret);
	}
	
	public static void main(String[] args) {
		// Variables 
		final String nomReseauSocial = "TINGAMER";
		String loginAdmin = "devel";
		String nomBD = loginAdmin + "_BD";
		String adresseReseau = "172.20.128.64";
		int action;
		JOptionPane menuPrincipal = new JOptionPane(), menuConnexion = new JOptionPane();
		boolean arret = false;
		
		//Connexion à la base de donnée
		//int connexion = BD.ouvrirConnexion(adresseReseau, nomBD, loginAdmin, loginAdmin);
		
		do{
			String[] choix = {"Inscription", "Connexion",  "Quitter"};
			action = menuPrincipal.showOptionDialog(null, "Choississez votre action ", "Menu Principal", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, choix, choix[2]);
			
			switch(action){
				//Inscription au réseau social
				case 0 : {
					inscription(nomReseauSocial);
				}
				break;	
				
				//Connexion au réseau social
				case 1 : {
					
				}
				break;
				
				// Arrêt de l'application
				case 2 : {
					arret = arretAppli();
				}
				break;
			}
		} while(!arret);
		
	}

}