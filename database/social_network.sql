/*
SQLyog Community v13.1.2 (64 bit)
MySQL - 5.7.24 : Database - reseau
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`reseau` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `reseau`;

/*Table structure for table `utilisateur` */

DROP TABLE IF EXISTS `utilisateur`;

CREATE TABLE `utilisateur` (
  `utLogin` varchar(30) NOT NULL,
  `utPassword` varchar(10) NOT NULL,
  PRIMARY KEY (`utLogin`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `jeu` */

DROP TABLE IF EXISTS `jeu`;

CREATE TABLE `jeu` (
  `jeTitre` varchar(50) NOT NULL,
  `jeJoueur` varchar(30) NOT NULL,
  PRIMARY KEY (`jeTitre`,`jeJoueur`),
  KEY `fk_jeu_utilisateur` (`jeJoueur`),
  CONSTRAINT `fk_jeu_utilisateur` FOREIGN KEY (`jeJoueur`) REFERENCES `utilisateur` (`utLogin`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `suivi` */

DROP TABLE IF EXISTS `suivi`;

CREATE TABLE `suivi` (
  `suSuiveur` varchar(30) NOT NULL,
  `suSuivi` varchar(30) NOT NULL,
  `suRDV` int(2) DEFAULT NULL,
  PRIMARY KEY (`suSuiveur`,`suSuivi`),
  KEY `fk_suivi_util` (`suSuivi`),
  CONSTRAINT `fk_suiveur_util` FOREIGN KEY (`suSuiveur`) REFERENCES `utilisateur` (`utLogin`),
  CONSTRAINT `fk_suivi_util` FOREIGN KEY (`suSuivi`) REFERENCES `utilisateur` (`utLogin`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
