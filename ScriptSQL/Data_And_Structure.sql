-- MySQL dump 10.13  Distrib 8.0.20, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: garde_hurry
-- ------------------------------------------------------
-- Server version	8.0.19

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `childcare`
--

DROP TABLE IF EXISTS `childcare`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `childcare` (
  `id` int NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `time_start` time NOT NULL,
  `time_end` time NOT NULL,
  `number_of_children` int NOT NULL,
  `description` varchar(250) NOT NULL,
  `is_complete` tinyint(1) NOT NULL,
  `childcare_validated` tinyint(1) DEFAULT NULL,
  `is_accomplished` tinyint(1) NOT NULL,
  `user_in_charge_feedback` tinyint(1) NOT NULL,
  `user_in_need_feedback` tinyint(1) NOT NULL,
  `user_in_need_id` int NOT NULL,
  `user_in_charge_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `users_in_need_childcares_fk` (`user_in_need_id`),
  KEY `users_in_charge_childcares_fk` (`user_in_charge_id`),
  CONSTRAINT `users_in_charge_childcares_fk` FOREIGN KEY (`user_in_charge_id`) REFERENCES `user` (`id`) ON DELETE CASCADE,
  CONSTRAINT `users_in_need_childcares_fk` FOREIGN KEY (`user_in_need_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=473 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `childcare`
--

LOCK TABLES `childcare` WRITE;
/*!40000 ALTER TABLE `childcare` DISABLE KEYS */;
INSERT INTO `childcare` VALUES (141,'2021-08-20','08:30:00','10:00:00',1,'rdv mÃ©dical',1,1,1,1,0,100,3),(144,'2021-09-01','10:00:00','10:30:00',1,'Je dois aller rÃ©cupÃ©rer un colis',1,1,1,0,0,95,3),(148,'2021-08-29','08:00:00','14:00:00',1,'RENDEZ6VOUS M2DICAL',1,1,1,0,0,95,3),(161,'2021-08-28','09:00:00','10:00:00',2,'MÃ©lissa suit un traitement il faudrait juste lui mettre des gouttes dans les yeux! Merci',1,1,1,0,0,100,95),(162,'2021-09-04','15:30:00','16:45:00',1,'j\'ai une rÃ©union je ne peux pas les rÃ©cupÃ©rer Ã  l\'Ã©cole',1,1,1,1,0,95,3),(163,'2021-08-24','15:00:00','16:00:00',1,'j\'ai une rÃ©union, peux-tu la rÃ©cupÃ©rer du centre de loisir stp?',1,1,1,1,0,95,3),(229,'2021-10-01','09:00:00','11:00:00',2,'Doit aller sur paris',1,1,1,0,0,100,173),(236,'2021-10-02','14:00:00','16:00:00',1,'Rendez-vous mÃ©dical!',1,1,1,0,0,173,196),(464,'2021-11-03','09:10:00','11:00:00',1,'Rendez-vous pro',1,0,0,0,0,95,214),(465,'2021-11-06','09:00:00','11:00:00',2,'MÃ©decin',1,1,0,0,0,214,95),(472,'2021-11-06','09:00:00','10:20:00',2,'MÃ©decin',1,1,0,0,0,214,95);
/*!40000 ALTER TABLE `childcare` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `children`
--

DROP TABLE IF EXISTS `children`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `children` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `age` int NOT NULL,
  `school` enum('N','M','P','C') DEFAULT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `users_children_fk` (`user_id`),
  CONSTRAINT `users_children_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=472 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `children`
--

LOCK TABLES `children` WRITE;
/*!40000 ALTER TABLE `children` DISABLE KEYS */;
INSERT INTO `children` VALUES (96,'Millie',6,'P',95),(103,'FÃ©licien',2,'N',100),(121,'FÃ©licien',2,'N',119),(130,'MÃ©lissa',8,'P',100),(174,'Mathilda',3,'N',173),(175,'Brice',7,'P',173),(197,'SolÃ¨ne',9,'P',196),(198,'FÃ©licia',6,'P',196),(208,'Annie',6,'P',207),(209,'Liam',8,'P',207),(210,'Lea',0,'N',207),(215,'Marie',9,'P',214),(216,'Sofia',2,'N',214),(406,'CÃ©cilia',2,'N',405),(407,'Martin',7,'P',405),(470,'Emelyne',4,'M',469),(471,'Martine',10,'P',469);
/*!40000 ALTER TABLE `children` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `children_to_watch`
--

DROP TABLE IF EXISTS `children_to_watch`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `children_to_watch` (
  `children_id` int NOT NULL,
  `childcare_id` int NOT NULL,
  PRIMARY KEY (`children_id`,`childcare_id`),
  KEY `childcares_children_childcare_fk` (`childcare_id`),
  CONSTRAINT `childcares_children_childcare_fk` FOREIGN KEY (`childcare_id`) REFERENCES `childcare` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `childrens_children_childcare_fk` FOREIGN KEY (`children_id`) REFERENCES `children` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `children_to_watch`
--

LOCK TABLES `children_to_watch` WRITE;
/*!40000 ALTER TABLE `children_to_watch` DISABLE KEYS */;
INSERT INTO `children_to_watch` VALUES (103,141),(96,144),(96,148),(103,161),(130,161),(96,162),(96,163),(103,229),(130,229),(174,236),(96,464),(215,465),(216,465),(215,472),(216,472);
/*!40000 ALTER TABLE `children_to_watch` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment` (
  `id` int NOT NULL AUTO_INCREMENT,
  `time` timestamp NOT NULL,
  `content` varchar(300) NOT NULL,
  `user_commented_id` int NOT NULL,
  `user_comment_id` int NOT NULL,
  `childcare_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `users_comment_comments_fk` (`user_comment_id`),
  KEY `users_commented_comments_fk` (`user_commented_id`),
  KEY `childcares_comment_childcare_fk` (`childcare_id`),
  CONSTRAINT `childcares_comment_childcare_fk` FOREIGN KEY (`childcare_id`) REFERENCES `childcare` (`id`),
  CONSTRAINT `users_comment_comments_fk` FOREIGN KEY (`user_comment_id`) REFERENCES `user` (`id`),
  CONSTRAINT `users_commented_comments_fk` FOREIGN KEY (`user_commented_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=476 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
INSERT INTO `comment` VALUES (473,'2021-10-05 13:32:35','Petit adorable! ras pour moi',100,3,141),(474,'2021-10-05 13:33:49','petite super gentille! Ã©coute et aime jouer!',95,3,163),(475,'2021-10-05 13:34:12','Comme d\'habitude un plaisir!',95,3,162);
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `friend`
--

DROP TABLE IF EXISTS `friend`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `friend` (
  `id` int NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `is_accepted` tinyint(1) NOT NULL,
  `user_id` int NOT NULL,
  `friend_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `users_friends_fk` (`user_id`),
  KEY `users_befriended_friends_fk` (`friend_id`),
  CONSTRAINT `users_befriended_friends_fk` FOREIGN KEY (`friend_id`) REFERENCES `user` (`id`) ON DELETE CASCADE,
  CONSTRAINT `users_friends_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=463 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `friend`
--

LOCK TABLES `friend` WRITE;
/*!40000 ALTER TABLE `friend` DISABLE KEYS */;
INSERT INTO `friend` VALUES (111,'2021-08-02',1,3,95),(139,'2021-08-17',1,3,100),(190,'2021-08-27',1,3,119),(195,'2021-09-04',1,173,3),(201,'2021-09-06',1,3,196),(227,'2021-09-08',1,173,100),(228,'2021-09-08',1,214,100),(248,'2021-09-15',0,196,100),(249,'2021-09-15',0,196,214),(250,'2021-09-15',0,196,173),(274,'2021-09-21',0,214,241),(462,'2021-10-05',1,214,95);
/*!40000 ALTER TABLE `friend` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (476);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `uuid` binary(16) NOT NULL,
  `first_name` varchar(100) NOT NULL,
  `last_name` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `is_validated` tinyint(1) NOT NULL,
  `address` varchar(50) NOT NULL,
  `city` varchar(30) NOT NULL,
  `area` varchar(30) NOT NULL,
  `situation` tinyint(1) NOT NULL,
  `roles` varchar(10) NOT NULL,
  `is_active` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=470 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (3,_binary '/Zˆz\×I*±FSrJ\Ï','Laura','Fernand','l.fernand@gmail.com','$2a$10$I9vVL.6l4w7uTpk05fM0J.Pu5sPY7S/f3EMuxRkESGgOy3N9eWzve',1,'1 rue des roses','Lagny-le-Sec','Les Charmettes',1,'ADMIN',1),(95,_binary '\ÅþN\ç¬ÁHÊµ\Ý\Ø9ªf\Õ','Sandra','Monthy','s.Monthy@gmail.com','$2a$10$CSvZdH837COUmtofl9CMIuXCzbu2emaOZwb5vIN3Xf1v8XuFsM7gK',0,'6 rue des Roses','Lagny-le-Sec','Les Charmettes',1,'USER',1),(100,_binary '+/VMIv¼yºW…]\Æ','Marc','Bouquin','m.bouquin@gmail.com','$2a$10$tBZkoz1Pvi5RSEud6J3QkunavuJ6aovy2.B/iCnh9fLFXowTRuTVy',0,'6 rue des Maniola','Bordeaux','Bordeaux Centre',1,'USER',1),(119,_binary '‹]À:lIž-+\à\ç%…[','Diana','Roland','droland@gmail.com','$2a$10$C3lLdBTGZjYzi6Zs/VMX9.jRJsKIs1JMu95W9imxeoablhFbWCkUC',1,'1 rue des Bois','Lagny-le-Sec','Roseraie',1,'USER',1),(173,_binary 'Ml=]”Fˆ§l¥gõ÷\ê','Eliza','Monthy','e.monthy@gmail.com','$2a$10$Is7ZzIOfqkEhurLv/582n.p3IknB3fDW2dJF5t7xEEIpEVnmAhHp6',1,'20 rue des peupliers','Saint-Pathus','Les Arbustes',1,'USER',1),(196,_binary '›ý¤\åóKO’\"ñ ôŸ³”','Lizzy','Boulanger','l.boulanger@gmail.com','$2a$10$uOcoymTc4kf.TdRznL7mP.A3JyKdpH9dl50L1brFsjcyePgTqfAMC',0,'8 rue Central','Villiers-le-Bel','Cerisaie',1,'USER',1),(207,_binary '`!Ä“úNF¦–\å\î÷\Ó\"M','Sophie','Marie-Sainte','smariesainte@gmail.com','$2a$10$iSpZbnc7YY5lVSZ5NfJeTOW9GM1diGDTmwhGbe1BatQmNQfa4kQJq',0,'2 rue des Bouleaux','Saint-Pathus','Les Arbustes',1,'USER',1),(214,_binary '¨²÷)¯yK¥² œªs*³','Corinne','Fabre','cfabre@gmail.com','$2a$10$EJztvxegnn8IphZwCXSXWeFQta/shM.Zb7Ptgw0yt1MeGHmc.nmAu',0,'3 avenue des lilas','Lagny-le-Sec','Cerisaie',0,'USER',1),(241,_binary '‹X@>v¦EÕ¢ñ´õ\"U²','Renald','Baudouin','r.baudouin@gmail.com','$2a$10$Fvwrp.t2YJ6a0qg.iPU8XutzpXP61y8/7/jju5gKroyFnEG7GMpR6',1,'6 rue des Lilas','Angers','Centre-Ville',1,'USER',1),(242,_binary '‘yþ¢OU¡\à=ý\×ü-','Selena','Marcy','smarcy@gmail.com','$2a$10$mme4318qNi/vHov34271gOXCJsFepSfETYQGAhsMkbzUnblkcJ5d6',0,'16bis avenue des Lilas','Angers','Centre-Ville',0,'USER',1),(405,_binary '¶\è)…Ú‘O•Šq\Z’«uB\à','MarlÃ¨ne','Denizot','mdenizot@gmail.com','$2a$10$j/HsnOJnF.kao4qg8KCUIekLZXHWs7HfdraLHm/aGBaVgcR4M42k.',0,'6 avenue des Lilaris','Bordeaux','Bordeaux Centre',1,'USER',1),(469,_binary 'ö\ßÁÿ\ÒRE•.€:\Èù\Ç','FrÃ©dÃ©ric','Da Silva','fDaSilva@gmail.com','$2a$10$/WVmMOjtDYnu7RfL5LDfJ.NPdebDgxjReFaFSnMWzvOPho0ydFxbq',0,'12 rue des Platanes','Saint-Pathus','Les Arbustes',1,'USER',1);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-10-05 17:40:02
