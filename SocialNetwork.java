/**
 * Social Network (Database Project - Topic NÂ°3)
 * SocialNetwork.java
 * 
 * @author Fabian Devel, Valentin Perignon
 */
 
 //bibliothèque pour les boites de dialogues
import javax.swing.JOptionPane;

public class SocialNetwork {
	
	public static void inscription (int connexion, String nomReseauSocial) {
		String motDePasse, login, sql, jvPref;
		JOptionPane menuInscription = new JOptionPane();
		login = saisieCorrecte("login", 30, menuInscription);
		if(login == null){
			return;
		}
		motDePasse = saisieCorrecte("mot de passe", 10, menuInscription);
		if(motDePasse == null){
			return;
		}
		
		/*do{
			login = menuInscription.showInputDialog(null, "Veuillez saisir votre identifiant : ", "Inscription", JOptionPane.QUESTION_MESSAGE);
			if(login == null){
				return;
			}
			if(login.length() == 0){
				menuInscription.showMessageDialog(null, "Ce champ est obligatoire, veuillez recommencer la saisie : " , "ERREUR - Login obligatoire-", JOptionPane.ERROR_MESSAGE);
			}
			if(login.length() > 30){
				menuInscription.showMessageDialog(null, "Votre login ne peut pas excéder 30 caractères, veuillez recommencer la saisie : " , "ERREUR - Login obligatoire-", JOptionPane.ERROR_MESSAGE);
			}
		} while(login.length() == 0 || login.length() > 30);
		do{
			motDePasse = menuInscription.showInputDialog(null, "Veuillez saisir votre mot de passe (10 caractères maximum)", "Inscription", JOptionPane.QUESTION_MESSAGE);
			if(motDePasse == null){
				return;
			}
			if(motDePasse.length() > 10){
				menuInscription.showMessageDialog(null, "Votre mot de passe ne doit pas faire plus de 10 caractères. Saississez un autre mot de passe :" , "ERREUR - Mot de passe trop long", JOptionPane.ERROR_MESSAGE);
			}
			if(motDePasse.length() == 0){
				menuInscription.showMessageDialog(null, "Ce champ est obligatoire, veuillez recommencer la saisie : " , "ERREUR - Mot de passe obligatoire ", JOptionPane.ERROR_MESSAGE);
			}
		}while(motDePasse.length() == 0 || motDePasse.length() > 10);*/
		
		sql = "INSERT INTO utilisateur (utLogin, utPassword) VALUES (" + login + ", " + motDePasse + ")";	
		// insertion des données dans la table utilisateur
		/*int inscription = BD.executerUpdate(connexion, sql);
		while (inscription == -1){
			menuInscription.showMessageDialog(null, "Votre identifiant doit être déjà pris, veuillez saisir en un autre : ", "ERREUR -  Identifiant déjà utilisé", JOptionPane.ERROR_MESSAGE);
			login = verificationsSaisie("login", 30, menuInscription);
		}*/
		
		menuInscription.showMessageDialog(null, "Félicitations pour votre inscription sur " + nomReseauSocial + ". Nous allons maintenant vous demander des informations pour compléter votre profil ! " , "Renseignement complémentaire", JOptionPane.INFORMATION_MESSAGE);
		jvPref = saisieCorrecte("jeu vidéo préféré", 50, menuInscription);
		if(jvPref == null){
			return;
		}
		/*do{
			jvPref = menuInscription.showInputDialog(null, "Quel est votre jeu vidéo préféré ? ", "Informations complémentaires", JOptionPane.QUESTION_MESSAGE);
			if(jvPref == null){
				return;
			}
			if(jvPref.length() == 0){
				menuInscription.showMessageDialog(null, "Ce champ est obligatoire, veuillez recommencer la saisie : " , "ERREUR - Renseignement obligatoire ", JOptionPane.ERROR_MESSAGE);
			}
		}while(jvPref.length() == 0);*/
		sql = "INSERT INTO jeu (jeTitre, jeJoueur) VALUES (" + jvPref + ", " + login + ")";
		// insertion des données dans la table jeu	
		//inscription = BD.executerUpdate(connexion, sql);
		menuInscription.showMessageDialog(null, "Et voilà, votre profil est finalisé ! Nous vous souhaitons une agréable utilisation de " + nomReseauSocial + " ! ", "Inscription finalisée", JOptionPane.INFORMATION_MESSAGE);
	}

	public static String saisieCorrecte(String typeSaisie,  int nbChar, JOptionPane menu){
		String valeur;
		do{
			valeur = menu.showInputDialog(null, "Veuillez saisir votre " + typeSaisie + " : ", "Inscription - " + typeSaisie, JOptionPane.QUESTION_MESSAGE);
			if(valeur == null){
				return(null);
			}
			if(valeur.length() == 0){
				menu.showMessageDialog(null, "Ce champ est obligatoire, veuillez recommencer la saisie : " , "ERREUR - " + typeSaisie + " obligatoire", JOptionPane.ERROR_MESSAGE);
			}
			if(valeur.length() > nbChar){
				menu.showMessageDialog(null, "Votre " + typeSaisie + " ne peut pas excéder " + nbChar+ " caractères, veuillez recommencer la saisie : " , "ERREUR - " + typeSaisie + " trop long !", JOptionPane.ERROR_MESSAGE);
			}
		} while(valeur.length() == 0 || valeur.length() > nbChar);
		return(valeur);
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
		/**********  Variables pour la connexion à la base de donnée **************/
		
		String loginAdmin = "devel";
		String nomBD = loginAdmin + "_BD";
		String adresseReseau = "172.20.128.64";
		int connexion = 0;
		
		/***********Variables programme***********/
		final String nomReseauSocial = "TINGAMER";
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
					inscription(connexion, nomReseauSocial);
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
		//BD.fermerConnextion(connextion);
	}

}