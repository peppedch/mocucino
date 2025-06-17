CREATE DATABASE  IF NOT EXISTS `mydb` /*!40100 DEFAULT CHARACTER SET utf8mb3 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `mydb`;
-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: localhost    Database: mydb
-- ------------------------------------------------------
-- Server version	8.0.33

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
-- Table structure for table `amministratori`
--

DROP TABLE IF EXISTS `amministratori`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `amministratori` (
  `username` varchar(45) NOT NULL,
  `password` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`username`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `amministratori`
--

LOCK TABLES `amministratori` WRITE;
/*!40000 ALTER TABLE `amministratori` DISABLE KEYS */;
INSERT INTO `amministratori` VALUES ('admin_fabio','password'),('admin_giuseppe','password'),('admin_marco','password');
/*!40000 ALTER TABLE `amministratori` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `commenti`
--

DROP TABLE IF EXISTS `commenti`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `commenti` (
  `idCommento` int NOT NULL AUTO_INCREMENT,
  `autore` varchar(45) DEFAULT NULL,
  `testoCommento` varchar(45) NOT NULL,
  `dataPubblicazione` datetime DEFAULT NULL,
  `Ricette_idRicetta` int NOT NULL,
  `Utenti_username` varchar(45) NOT NULL,
  PRIMARY KEY (`idCommento`),
  UNIQUE KEY `idCommento_UNIQUE` (`idCommento`),
  KEY `fk_Commenti_Ricette1_idx` (`Ricette_idRicetta`),
  KEY `fk_Commenti_Utenti1_idx` (`Utenti_username`),
  CONSTRAINT `fk_Commenti_Ricette1` FOREIGN KEY (`Ricette_idRicetta`) REFERENCES `ricette` (`idRicetta`),
  CONSTRAINT `fk_Commenti_Utenti1` FOREIGN KEY (`Utenti_username`) REFERENCES `utenti` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `commenti`
--

LOCK TABLES `commenti` WRITE;
/*!40000 ALTER TABLE `commenti` DISABLE KEYS */;
INSERT INTO `commenti` VALUES (1,'Elena Esposito','La pizza è venuta perfetta!','2023-05-16 09:15:00',1,'elena_esposito'),(2,'Francesco Roman','Ho aggiunto olive ed è ottima','2023-05-17 19:30:00',1,'francesco_roman'),(3,'Chiara Colomb','Troppo dolce per i miei gusti','2023-06-11 18:40:00',2,'chiara_colomb'),(4,'Paolo Conti','Perfetto per una cena speciale','2023-06-12 12:20:00',2,'paolo_conti'),(5,'Martina Galli','Perfetto per una cena veloce','2023-07-23 20:30:00',3,'martina_galli'),(6,'Andrea Ferrari','Ho usato il parmigiano al posto del burro','2023-08-06 12:50:00',4,'andrea_ferrari'),(7,'Sofia Bianchi','I brownies sono morbidissimi','2023-09-19 15:10:00',5,'sofia_bianchi'),(8,'TestPeppe','DAICAZZO','2025-06-11 04:00:33',4,'TestPeppe'),(9,'TestPeppe','buonissima','2025-06-11 15:14:12',1,'TestPeppe'),(10,'TestPeppe','forza napoli','2025-06-11 17:08:42',4,'TestPeppe'),(11,'UndiciGiugno00','vediamo se funziona','2025-06-11 17:13:51',17,'UndiciGiugno00'),(12,'parente11','commentiamo','2025-06-11 17:24:06',15,'parente11'),(13,'TestPeppe','ottima','2025-06-11 17:53:47',2,'TestPeppe'),(14,'TestPeppe','undicigiugno','2025-06-11 17:54:10',3,'TestPeppe'),(15,'giulia_russo','test','2025-06-11 22:37:24',19,'giulia_russo'),(16,'giulia_russo','test','2025-06-11 22:37:33',17,'giulia_russo'),(17,'giulia_russo','aaa','2025-06-11 22:37:47',15,'giulia_russo'),(18,'giulia_russo','aaa','2025-06-11 22:37:54',16,'giulia_russo'),(19,'giulia_russo','aaa','2025-06-11 22:38:00',17,'giulia_russo'),(20,'TestPeppe','Uagliooo si fort','2025-06-11 23:09:46',23,'TestPeppe'),(21,'Vediamo','yoo','2025-06-11 23:16:23',23,'Vediamo'),(22,'TestPeppe','song nu mostr','2025-06-12 12:25:05',18,'TestPeppe'),(23,'TestPeppe','sono un gay','2025-06-13 17:24:55',24,'TestPeppe'),(24,'giulia_russo','sei proprio un cuoco','2025-06-13 17:25:45',24,'giulia_russo'),(25,'TestPeppe','olè','2025-06-13 17:47:57',26,'TestPeppe'),(26,'TestPeppe','magnatill','2025-06-13 17:56:46',27,'TestPeppe'),(27,'TestPeppe','ciao','2025-06-13 18:19:06',28,'TestPeppe'),(28,'TestPeppe','43304','2025-06-13 18:30:25',29,'TestPeppe'),(29,'TestPeppe','cccc','2025-06-16 15:57:51',34,'TestPeppe'),(30,'TestPeppe','eee','2025-06-16 16:07:25',36,'TestPeppe');
/*!40000 ALTER TABLE `commenti` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ingredienti`
--

DROP TABLE IF EXISTS `ingredienti`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ingredienti` (
  `idIngrediente` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idIngrediente`),
  UNIQUE KEY `idIngrediente_UNIQUE` (`idIngrediente`)
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ingredienti`
--

LOCK TABLES `ingredienti` WRITE;
/*!40000 ALTER TABLE `ingredienti` DISABLE KEYS */;
INSERT INTO `ingredienti` VALUES (1,'Farina 00'),(2,'Zucchero'),(3,'Uova'),(4,'Lievito'),(5,'Latte'),(6,'Burro'),(7,'Pomodoro'),(8,'Basilico'),(9,'Mozzarella'),(10,'Spaghetti'),(11,'Riso Arborio'),(12,'Zafferano'),(13,'Caffè'),(14,'Cacao'),(15,'Mascarpone'),(16,'Savoiardi'),(17,'Cioccolato Fondente'),(18,'pasta'),(19,'riso'),(20,'cioccolata'),(21,'banana'),(22,'patate'),(23,'eee'),(24,'pesce'),(25,'fiori di zucca'),(26,'patate fritte'),(27,'carne'),(28,'limone'),(29,'carne umana'),(30,'panna'),(31,'ds'),(32,'ciao2'),(33,'242'),(34,'ewew'),(35,'3444334'),(36,'sdsds'),(37,'fdfds2'),(38,'aggiuntoora'),(39,'Ceci cotti'),(40,'Tonno sott\'olio'),(41,'Pomodorini'),(42,'Cipolla rossa'),(43,'Olio extravergine d\'oliva'),(44,'sale'),(45,'Pasta corta'),(46,'Pistacchi sgusciati'),(47,'Parmigiano grattugiato'),(48,'Olio extravergine d’oliva'),(49,'Lenticchie cotte'),(50,'Pane grattugiato'),(51,'Carota'),(52,'Cipolla'),(53,'Olio d’oliva'),(54,'Pane integrale'),(55,'Avocado'),(56,'Uovo'),(57,'Pepe'),(58,'Zucchine'),(59,'Ricotta'),(60,'Parmigiano'),(61,'Pangrattato'),(62,'Panna fresca'),(63,'Gelatina in fogli'),(64,'Vaniglia'),(65,'Cozze'),(66,'Pepe nero'),(67,'Acqua'),(68,'Lievito di birra'),(69,'Salame napoletano'),(70,'Provola');
/*!40000 ALTER TABLE `ingredienti` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `likes`
--

DROP TABLE IF EXISTS `likes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `likes` (
  `Utenti_username` varchar(45) NOT NULL,
  `Ricette_idRicetta` int NOT NULL,
  PRIMARY KEY (`Utenti_username`,`Ricette_idRicetta`),
  KEY `fk_Likes_Utenti1_idx` (`Utenti_username`),
  KEY `fk_Likes_Ricette1_idx` (`Ricette_idRicetta`),
  CONSTRAINT `fk_Likes_Ricette1` FOREIGN KEY (`Ricette_idRicetta`) REFERENCES `ricette` (`idRicetta`),
  CONSTRAINT `fk_Likes_Utenti1` FOREIGN KEY (`Utenti_username`) REFERENCES `utenti` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `likes`
--

LOCK TABLES `likes` WRITE;
/*!40000 ALTER TABLE `likes` DISABLE KEYS */;
INSERT INTO `likes` VALUES ('giulia_russo',1),('marco_verdi',1),('sofia_bianchi',1),('TestPeppe',1),('andrea_ferrari',2),('elena_esposito',2),('TestPeppe',2),('francesco_roman',3),('TestPeppe',3),('chiara_colomb',4),('paolo_conti',4),('TestPeppe',4),('luca_rossi',5),('martina_galli',5),('giulia_russo',15),('giulia_russo',16),('giulia_russo',17),('UndiciGiugno00',17),('TestPeppe',18),('giulia_russo',19),('TestPeppe',21),('TestPeppe',23),('Vediamo',23),('giulia_russo',24),('TestPeppe',26),('TestPeppe',27),('TestPeppe',28),('TestPeppe',29),('TestPeppe',34),('TestPeppe',35),('TestPeppe',36);
/*!40000 ALTER TABLE `likes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `raccolte`
--

DROP TABLE IF EXISTS `raccolte`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `raccolte` (
  `idRaccolta` int NOT NULL AUTO_INCREMENT,
  `titolo` varchar(45) NOT NULL,
  `descrizione` varchar(800) DEFAULT NULL,
  `Utenti_username` varchar(45) NOT NULL,
  PRIMARY KEY (`idRaccolta`),
  UNIQUE KEY `unique_user_title` (`Utenti_username`,`titolo`),
  UNIQUE KEY `idRaccolta_UNIQUE` (`idRaccolta`),
  KEY `fk_Raccolte_Utenti1_idx` (`Utenti_username`),
  CONSTRAINT `fk_Raccolte_Utenti1` FOREIGN KEY (`Utenti_username`) REFERENCES `utenti` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `raccolte`
--

LOCK TABLES `raccolte` WRITE;
/*!40000 ALTER TABLE `raccolte` DISABLE KEYS */;
INSERT INTO `raccolte` VALUES (1,'Ricette Italiane','Le migliori ricette della tradizione italiana','luca_rossi'),(2,'Dolci Facili','Dolci veloci da preparare','sofia_bianchi'),(3,'Primi Piatti','Antipasti e primi piatti per ogni occasione','marco_verdi'),(4,'Vegano','Piatti completamente vegani','giulia_russo'),(5,'Comfort Food','Cibi che scaldano il cuore','andrea_ferrari'),(6,'default','Raccolta predefinita dell\'utente','testuser01'),(7,'default','Raccolta predefinita dell\'utente','andrea_ferrari'),(8,'default','Raccolta predefinita dell\'utente','chiara_colomb'),(9,'default','Raccolta predefinita dell\'utente','elena_esposito'),(10,'default','Raccolta predefinita dell\'utente','francesco_roman'),(11,'default','Raccolta predefinita dell\'utente','giulia_russo'),(12,'default','Raccolta predefinita dell\'utente','luca_rossi'),(13,'default','Raccolta predefinita dell\'utente','marco_verdi'),(14,'default','Raccolta predefinita dell\'utente','martina_galli'),(15,'default','Raccolta predefinita dell\'utente','paolo_conti'),(16,'default','Raccolta predefinita dell\'utente','sofia_bianchi'),(17,'default','Raccolta predefinita dell\'utente','TestPeppe'),(19,'proviamo','Creata dall\'utente','TestPeppe'),(20,'eee','Creata dall\'utente','TestPeppe'),(21,'srsr','Creata dall\'utente','TestPeppe'),(22,'frutta','Creata dall\'utente','TestPeppe'),(23,'default','Raccolta predefinita dell\'utente','UndiciGiugno00'),(24,'default','Raccolta predefinita dell\'utente','parente11'),(25,'default','Raccolta predefinita dell\'utente','Vediamo'),(26,'cipiace','Creata dall\'utente','Vediamo'),(27,'13giugno','Creata dall\'utente','TestPeppe'),(28,'debiase','Creata dall\'utente','TestPeppe'),(29,'patata','Creata dall\'utente','TestPeppe'),(30,'magnatill','Creata dall\'utente','TestPeppe'),(31,'mouse','Creata dall\'utente','TestPeppe'),(32,'jammjamm','Creata dall\'utente','TestPeppe'),(33,'default','Raccolta predefinita dell\'utente','vediamo_now'),(34,'eere','Creata dall\'utente','TestPeppe'),(35,'vvvv','Creata dall\'utente','TestPeppe'),(36,'CHENESO','Creata dall\'utente','TestPeppe'),(37,'default','Raccolta predefinita dell\'utente','marcodamore'),(38,'default','Raccolta predefinita dell\'utente','erererer');
/*!40000 ALTER TABLE `raccolte` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ricette`
--

DROP TABLE IF EXISTS `ricette`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ricette` (
  `idRicetta` int NOT NULL AUTO_INCREMENT,
  `titolo` varchar(45) NOT NULL,
  `procedimento` varchar(255) DEFAULT NULL,
  `tempo` int DEFAULT NULL,
  `visibilita` tinyint DEFAULT NULL,
  `numLike` int DEFAULT '0',
  `numCommenti` int DEFAULT '0',
  `dataPubblicazione` datetime DEFAULT NULL,
  `Utenti_username` varchar(45) NOT NULL,
  `Raccolte_idRaccolta` int NOT NULL,
  PRIMARY KEY (`idRicetta`),
  UNIQUE KEY `idRicetta_UNIQUE` (`idRicetta`),
  KEY `fk_Ricette_Utenti_idx` (`Utenti_username`),
  KEY `fk_Ricette_Raccolte1_idx` (`Raccolte_idRaccolta`),
  CONSTRAINT `fk_Ricette_Raccolte1` FOREIGN KEY (`Raccolte_idRaccolta`) REFERENCES `raccolte` (`idRaccolta`),
  CONSTRAINT `fk_Ricette_Utenti` FOREIGN KEY (`Utenti_username`) REFERENCES `utenti` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ricette`
--

LOCK TABLES `ricette` WRITE;
/*!40000 ALTER TABLE `ricette` DISABLE KEYS */;
INSERT INTO `ricette` VALUES (1,'Pizza Margherita','Stendere l\'impasto e aggiungere gli ingredienti',90,1,4,2,'2023-05-15 18:30:00','luca_rossi',1),(2,'Tiramisù','Stratificare savoiardi e crema al mascarpone',60,1,3,2,'2023-06-10 14:15:00','sofia_bianchi',2),(3,'Pasta al Pomodoro','Cuocere la pasta e preparare la salsa',30,1,2,1,'2023-07-22 12:00:00','marco_verdi',3),(4,'Risotto Zafferano','Tostare il riso e aggiungere brodo e zafferano',45,1,3,1,'2023-08-05 19:45:00','giulia_russo',4),(5,'Brownies','Sciogliere cioccolato e burro, mescolare',50,0,2,1,'2023-09-18 10:20:00','andrea_ferrari',5),(8,'Testiamo','testiamo dai cazzo',2,1,NULL,NULL,'2025-06-09 21:56:58','TestPeppe',19),(9,'Testiamo','testiamo dai cazzo',2,1,NULL,NULL,'2025-06-09 21:57:04','TestPeppe',17),(10,'Testiamo','testiamo dai cazzo',2,1,NULL,NULL,'2025-06-09 21:57:14','TestPeppe',20),(11,'test','etoer',2,1,NULL,NULL,'2025-06-09 22:05:29','TestPeppe',17),(12,'testiamo','ciaooo',2,1,NULL,NULL,'2025-06-09 22:08:49','TestPeppe',17),(13,'Othertesting','ciaoaoa',23,1,NULL,NULL,'2025-06-09 22:13:25','TestPeppe',21),(14,'testiamodai','vediamooo',34,1,NULL,NULL,'2025-06-10 00:43:01','TestPeppe',17),(15,'lolaoa','3ererer',23,1,1,2,'2025-06-10 01:07:01','TestPeppe',19),(16,'rerer','ererer',23,1,1,1,'2025-06-10 01:07:48','TestPeppe',20),(17,'Dajeeeee','eririre',4,1,1,3,'2025-06-11 01:23:57','TestPeppe',17),(18,'undicigiugno','magnt a banan',21,1,1,1,'2025-06-11 03:12:54','TestPeppe',22),(19,'Pasta e patate','metti la pasta e poi le patate',5,1,1,1,'2025-06-11 15:12:57','TestPeppe',17),(21,'eee','eee',2,0,1,NULL,'2025-06-11 18:18:19','TestPeppe',17),(22,'test','ciao',20,1,0,0,'2025-06-11 22:59:32','TestPeppe',17),(23,'ventitre','mannagggg',21,1,2,2,'2025-06-11 23:09:16','Vediamo',26),(24,'RicettaCasuale','vediamo se è buona',30,1,1,2,'2025-06-13 17:23:32','TestPeppe',27),(25,'marcolino','ciao',24,1,0,0,'2025-06-13 17:40:52','TestPeppe',28),(26,'46','cavolfiore',34,1,1,1,'2025-06-13 17:47:29','TestPeppe',29),(27,'56','magnt o limon',34,1,1,1,'2025-06-13 17:56:33','TestPeppe',30),(28,'zucca','dijjer',32,1,1,1,'2025-06-13 18:18:32','TestPeppe',31),(29,'ingegnere','ereijr32',32,1,1,1,'2025-06-13 18:30:07','TestPeppe',32),(30,'panna','rijeerreij',32,1,0,0,'2025-06-15 20:35:45','giulia_russo',11),(31,'ssdds','3ewe',323,0,0,0,'2025-06-15 23:57:30','TestPeppe',17),(32,'re3r','e3ewwe',2,1,0,0,'2025-06-16 03:43:56','TestPeppe',17),(33,'feerer','frerr',21,1,0,0,'2025-06-16 03:44:28','TestPeppe',34),(34,'16giugno','jamm a vre',34,1,1,1,'2025-06-16 15:57:41','TestPeppe',35),(35,'vdfe','ererer',32,1,1,0,'2025-06-16 15:59:14','TestPeppe',17),(36,'SecondoDAI','fdererr',32,1,1,1,'2025-06-16 16:07:14','TestPeppe',36),(38,'Insalata di ceci e tonno','Un piatto freddo, veloce e nutriente, perfetto per l\'estate. Unisce la freschezza dei pomodorini al gusto deciso del tonno.',10,1,0,0,'2025-06-17 22:52:30','giulia_russo',11),(39,'Pasta con pesto di pistacchi','Un primo piatto dal gusto cremoso e aromatico, arricchito dalla dolcezza dei pistacchi.',15,1,0,0,'2025-06-17 22:53:58','giulia_russo',11),(40,'Polpette di lenticchie','Perfette per un secondo piatto vegetariano, croccanti fuori e morbide dentro.',25,1,0,0,'2025-06-17 22:55:26','andrea_ferrari',7),(41,'Toast avocado e uovo','Colazione salata o brunch sfizioso, semplice da preparare e pieno di nutrienti.',10,1,0,0,'2025-06-17 22:56:28','andrea_ferrari',7),(42,'Zucchine ripiene vegetariane','Variante leggera delle zucchine ripiene, ideali come secondo piatto o piatto unico.',35,1,0,0,'2025-06-17 22:57:55','sofia_bianchi',16),(43,'Panna cotta alla vaniglia','Dolce al cucchiaio classico, facile da preparare, perfetto a fine pasto con frutti di bosco.',20,1,0,0,'2025-06-17 22:59:04','martina_galli',14),(44,'Impepata di cozze','Antipasto napoletano semplice e saporito. Cozze fresche, tanto pepe e una spruzzata di limone: tutto qui.',10,1,0,0,'2025-06-17 23:01:10','marcodamore',37),(45,'Pizza fritta napoletana','La “cugina” della pizza al forno, ma fritta e ripiena. Un\'esplosione di gusto: ricotta, salame e provola racchiusi in un guscio croccante.',40,1,0,0,'2025-06-17 23:03:36','marco_verdi',13);
/*!40000 ALTER TABLE `ricette` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ricette_has_ingredienti`
--

DROP TABLE IF EXISTS `ricette_has_ingredienti`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ricette_has_ingredienti` (
  `Ricette_idRicetta` int NOT NULL,
  `Ingredienti_idIngrediente` int NOT NULL,
  `quantità` decimal(10,2) DEFAULT NULL,
  `unità` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`Ricette_idRicetta`,`Ingredienti_idIngrediente`),
  KEY `fk_Ricette_has_Ingredienti_Ingredienti1_idx` (`Ingredienti_idIngrediente`),
  KEY `fk_Ricette_has_Ingredienti_Ricette1_idx` (`Ricette_idRicetta`),
  CONSTRAINT `fk_Ricette_has_Ingredienti_Ingredienti1` FOREIGN KEY (`Ingredienti_idIngrediente`) REFERENCES `ingredienti` (`idIngrediente`),
  CONSTRAINT `fk_Ricette_has_Ingredienti_Ricette1` FOREIGN KEY (`Ricette_idRicetta`) REFERENCES `ricette` (`idRicetta`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ricette_has_ingredienti`
--

LOCK TABLES `ricette_has_ingredienti` WRITE;
/*!40000 ALTER TABLE `ricette_has_ingredienti` DISABLE KEYS */;
INSERT INTO `ricette_has_ingredienti` VALUES (1,1,500.00,'g'),(1,7,300.00,'g'),(1,8,10.00,'g'),(1,9,200.00,'g'),(2,2,150.00,'g'),(2,3,3.00,'unità '),(2,13,100.00,'ml'),(2,14,50.00,'g'),(2,15,500.00,'g'),(2,16,200.00,'g'),(3,7,400.00,'g'),(3,8,5.00,'g'),(3,10,400.00,'g'),(4,6,50.00,'g'),(4,11,350.00,'g'),(4,12,0.50,'g'),(5,1,100.00,'g'),(5,2,200.00,'g'),(5,3,3.00,'unità'),(5,6,150.00,'g'),(5,17,200.00,'g'),(8,18,200.00,'g'),(9,18,200.00,'g'),(10,18,200.00,'g'),(11,18,200.00,'g'),(12,18,200.00,'g'),(13,18,100.00,'g'),(14,19,500.00,'g'),(15,18,200.00,'g'),(16,19,300.00,'g'),(17,20,200.00,'g'),(18,21,200.00,'g'),(19,18,200.00,'g'),(19,22,100.00,'g'),(21,23,3.00,'g'),(22,18,200.00,'g'),(23,24,500.00,'pz'),(24,25,50.00,'g'),(24,26,10.00,'g'),(25,27,200.00,'g'),(26,18,200.00,'g'),(27,28,200.00,'g'),(28,27,200.00,'g'),(29,29,200.00,'g'),(30,30,200.00,'g'),(31,31,23.00,'g'),(32,32,20.00,'g'),(33,33,23.00,'g'),(34,14,200.00,'g'),(34,19,2.00,'tazze'),(34,30,50.00,'g'),(35,34,24.00,'g'),(35,35,223.00,'g'),(36,14,200.00,'g'),(36,36,3232.00,'g'),(36,37,3232.00,'g'),(36,38,22.00,'g'),(38,39,240.00,'g'),(38,40,160.00,'g'),(38,41,100.00,'g'),(38,42,50.00,'g'),(38,43,2.00,'cucchiai'),(38,44,1.00,'g'),(39,8,5.00,'pz'),(39,44,1.00,'g'),(39,45,160.00,'g'),(39,46,50.00,'g'),(39,47,30.00,'g'),(39,48,3.00,'cucchiai'),(40,44,1.00,'g'),(40,49,200.00,'g'),(40,50,40.00,'g'),(40,51,1.00,'pz'),(40,52,1.00,'pz'),(40,53,2.00,'cucchiai'),(41,44,1.00,'pz'),(41,54,2.00,'pz'),(41,55,1.00,'pz'),(41,56,1.00,'pz'),(41,57,1.00,'g'),(42,44,1.00,'g'),(42,57,1.00,'g'),(42,58,4.00,'pz'),(42,59,200.00,'g'),(42,60,40.00,'g'),(42,61,30.00,'g'),(43,2,100.00,'g'),(43,62,500.00,'ml'),(43,63,10.00,'g'),(43,64,1.00,'pz'),(44,65,1000.00,'g'),(44,66,2.00,'cucchiai'),(45,1,500.00,'g'),(45,44,10.00,'g'),(45,57,1.00,'g'),(45,59,200.00,'g'),(45,67,300.00,'ml'),(45,68,12.00,'g'),(45,69,100.00,'g'),(45,70,100.00,'g');
/*!40000 ALTER TABLE `ricette_has_ingredienti` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ricette_has_tags`
--

DROP TABLE IF EXISTS `ricette_has_tags`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ricette_has_tags` (
  `Ricette_idRicetta` int NOT NULL,
  `Tags_idTag` int NOT NULL,
  PRIMARY KEY (`Ricette_idRicetta`,`Tags_idTag`),
  KEY `fk_Ricette_has_Tags_Tags1_idx` (`Tags_idTag`),
  KEY `fk_Ricette_has_Tags_Ricette1_idx` (`Ricette_idRicetta`),
  CONSTRAINT `fk_Ricette_has_Tags_Ricette1` FOREIGN KEY (`Ricette_idRicetta`) REFERENCES `ricette` (`idRicetta`),
  CONSTRAINT `fk_Ricette_has_Tags_Tags1` FOREIGN KEY (`Tags_idTag`) REFERENCES `tags` (`idTag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ricette_has_tags`
--

LOCK TABLES `ricette_has_tags` WRITE;
/*!40000 ALTER TABLE `ricette_has_tags` DISABLE KEYS */;
INSERT INTO `ricette_has_tags` VALUES (5,1),(8,1),(9,1),(12,1),(13,1),(14,1),(17,1),(18,1),(29,1),(34,1),(36,1),(40,1),(1,2),(8,2),(9,2),(11,2),(12,2),(13,2),(14,2),(17,2),(18,2),(34,2),(36,2),(40,2),(42,2),(12,3),(13,3),(14,3),(15,3),(16,3),(17,3),(18,3),(22,3),(23,3),(24,3),(25,3),(28,3),(30,3),(31,3),(34,3),(35,3),(36,3),(40,3),(2,4),(3,4),(8,4),(9,4),(12,4),(13,4),(14,4),(17,4),(18,4),(24,4),(34,4),(36,4),(38,4),(39,4),(41,4),(44,4),(1,5),(3,5),(4,5),(8,5),(9,5),(12,5),(13,5),(14,5),(17,5),(18,5),(28,5),(32,5),(34,5),(36,5),(38,5),(39,5),(43,5),(44,5),(45,5),(8,6),(9,6),(11,6),(12,6),(13,6),(14,6),(16,6),(17,6),(18,6),(26,6),(27,6),(34,6),(35,6),(36,6),(38,6),(39,6),(44,6),(45,6),(8,7),(9,7),(11,7),(12,7),(13,7),(14,7),(17,7),(18,7),(28,7),(34,7),(36,7),(41,7),(2,8),(5,8),(8,8),(9,8),(10,8),(11,8),(12,8),(13,8),(14,8),(17,8),(18,8),(34,8),(36,8),(43,8),(4,9),(8,9),(9,9),(12,9),(13,9),(14,9),(15,9),(16,9),(17,9),(18,9),(23,9),(25,9),(29,9),(33,9),(34,9),(36,9),(38,9),(40,9),(41,9),(42,9),(12,10),(13,10),(14,10),(17,10),(18,10),(19,10),(21,10),(24,10),(25,10),(34,10),(36,10),(39,10),(42,10);
/*!40000 ALTER TABLE `ricette_has_tags` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tags`
--

DROP TABLE IF EXISTS `tags`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tags` (
  `idTag` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idTag`),
  UNIQUE KEY `idTag_UNIQUE` (`idTag`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tags`
--

LOCK TABLES `tags` WRITE;
/*!40000 ALTER TABLE `tags` DISABLE KEYS */;
INSERT INTO `tags` VALUES (1,'Vegano'),(2,'Vegetariano'),(3,'Senza glutine'),(4,'Veloce'),(5,'Italiano'),(6,'Salato'),(7,'Spuntino'),(8,'Dolce'),(9,'Salutare'),(10,'Primo piatto');
/*!40000 ALTER TABLE `tags` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `utenti`
--

DROP TABLE IF EXISTS `utenti`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `utenti` (
  `username` varchar(45) NOT NULL,
  `nome` varchar(45) NOT NULL,
  `cognome` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `biografia` varchar(45) DEFAULT NULL,
  `immagine` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`username`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `utenti`
--

LOCK TABLES `utenti` WRITE;
/*!40000 ALTER TABLE `utenti` DISABLE KEYS */;
INSERT INTO `utenti` VALUES ('andrea_ferrari','Andrea','Ferrari','andrea_ferrari@mail.ex','Ferrari48','Appassionato di auto sportive','uploads/andrea_ferrari.jpg'),('chiara_colomb','Chiara','Colomb','chiara_colomb@mail.ex','Colombo2','Studentessa di architettura','uploads/chiara_colomb.png'),('elena_esposito','Elena','Esposito','elena_esposito@mail.ex','Elena2024','Insegnante di scuola media','uploads/elena_esposito.png'),('erererer','D\'arezzo','sddsds','cdf@mail.ex','Caccaa131',NULL,NULL),('francesco_roman','Francesco','Roman','francesco_roman@mail.ex','Francesco03','Calciatore dilettante','uploads/francesco_roman.jpg'),('giulia_russo','Giulia','Russo','giulia_russo@mail.ex','Giulia456','Viaggiatrice instancabile','uploads/giulia_russo.png'),('luca_rossi','Luca','Rossi','luca_rossi@mail.ex','Password1','Studente di ingegneria','uploads/luca_rossi.jpg'),('marcodamore','Marco','D\'amore','marcodamore@mail.ex','Marcolino1',NULL,NULL),('marco_verdi','Marco','Verdi','marco_verdi@mail.ex','MarcoVerd5','Cuoco amatoriale','uploads/marco_verdi.jpg'),('martina_galli','Martina','Galli','martina_galli@mail.ex','Martin42','Amante della lettura','uploads/martina_galli.jpg'),('paolo_conti','Paolo','Conti','paolo_conti@mail.ex','Napoli00','Giocatore di basket','uploads/paolo_conti.jpg'),('parente11','Parente','Undici','parente11@mail.ex','Parente00*',NULL,NULL),('sofia_bianchi','Sofia','Bianchi','sofia_bianchi@mail.ex','Securepass9','Appassionata di fotografia','uploads/sofia_bianchi.png'),('TestPeppe','TestOggi','PeppeAScuppett','testpeppe@mail.ex','Peppino00','forza napoli','C:\\Users\\gdech\\OneDrive\\Desktop\\IS\\fotogruppo.jpg'),('testuser01','Test','User','testuser01@mail.com','Password123','Biografia di test','uploads/default.jpg'),('UndiciGiugno00','Undici','Giugno','undicigiugno@mail.ex','Undici11*',NULL,NULL),('Vediamo','Vediamo','Ora','vediamo@mail.ex','Vediamo00',NULL,NULL),('vediamo_now','vediamo','now','vediamonow@mail.ex','Vediamo1',NULL,NULL);
/*!40000 ALTER TABLE `utenti` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `crea_raccolta_default` AFTER INSERT ON `utenti` FOR EACH ROW BEGIN
  INSERT INTO mydb.raccolte (titolo, descrizione, utenti_username)
  VALUES (
    'default',
    'Raccolta predefinita dell\'utente',
    NEW.username
  );
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Dumping events for database 'mydb'
--

--
-- Dumping routines for database 'mydb'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-06-18  1:13:30
