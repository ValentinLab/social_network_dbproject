Gaming Social Network (Database Project - Topic N°3)
===================================================

## About
Development of an application that simulates a social network about video games.
Goals to reach:
* Creation of an account
* Login system
* Profile with user's favorite games
* Find users with same favorite games
* A user can follow another one
* Appointment proposal and answer

## Database
| Table       | Content                     |
| ----------- | --------------------------- |
| utilisateur | utLogin*, utPassword        |
| jeu         | jeTitre*, jeJoueur*         |
| suivi       | suSuivi*, suSuiveur*, suRDV |
