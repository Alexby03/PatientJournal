-- MySQL dump 10.13  Distrib 9.4.0, for Win64 (x86_64)
--
-- Host: localhost    Database: patientjournaldb
-- ------------------------------------------------------
-- Server version	9.4.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `conditions`
--

DROP TABLE IF EXISTS `conditions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `conditions` (
                              `condition_id` binary(16) NOT NULL,
                              `condition_name` varchar(255) NOT NULL,
                              `condition_type` enum('Autoimmune','Cancerous','Chronic','Genetic','Infectious','Neurological','Psychiatric') NOT NULL,
                              `diagnosed_date` date NOT NULL,
                              `severity_level` int NOT NULL,
                              `patient_id` binary(16) NOT NULL,
                              `practitioner_id` binary(16) NOT NULL,
                              PRIMARY KEY (`condition_id`),
                              KEY `FK5dnuk08yxv4e3ky0gnpbpo1t3` (`patient_id`),
                              KEY `FKxgpix71i5f8m5df7bia2g608` (`practitioner_id`),
                              CONSTRAINT `FK5dnuk08yxv4e3ky0gnpbpo1t3` FOREIGN KEY (`patient_id`) REFERENCES `users` (`user_id`),
                              CONSTRAINT `FKxgpix71i5f8m5df7bia2g608` FOREIGN KEY (`practitioner_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `conditions`
--

LOCK TABLES `conditions` WRITE;
/*!40000 ALTER TABLE `conditions` DISABLE KEYS */;
INSERT INTO `conditions` VALUES (0x79657DAD942B4F7F90F3E763B7192B97,'Common Cold','Infectious','2025-03-15',2,0x586DE8E07FF649F994CDC32F00BA9E33,0x92E06867C3C511F081277E98026CDA3C),(0x9E5DF2B193E542E3A468BE7B6F859FDF,'Ear infection','Infectious','2025-08-14',2,0x586DE8E07FF649F994CDC32F00BA9E33,0x92E2D347C3C511F081277E98026CDA3C),(0xEACF0ADBBA154F45AC155DFCAC570DFD,'Random Test 789','Infectious','2025-11-01',1,0x586DE8E07FF649F994CDC32F00BA9E33,0x92E06867C3C511F081277E98026CDA3C),(0xFF6B2D3EC2D143F6BFFFCDA652851A40,'Stronger Infection','Infectious','2025-11-17',4,0x586DE8E07FF649F994CDC32F00BA9E33,0x92E06867C3C511F081277E98026CDA3C);
/*!40000 ALTER TABLE `conditions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `encounters`
--

DROP TABLE IF EXISTS `encounters`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `encounters` (
                              `encounter_id` binary(16) NOT NULL,
                              `description` varchar(255) DEFAULT NULL,
                              `encounter_date` datetime(6) NOT NULL,
                              `patient_id` binary(16) NOT NULL,
                              `practitioner_id` binary(16) NOT NULL,
                              PRIMARY KEY (`encounter_id`),
                              KEY `FK1pkkrmb5o0yuvx29srqmgmiuo` (`patient_id`),
                              KEY `FKf773yf10kos7phrxt6n5ftvd7` (`practitioner_id`),
                              CONSTRAINT `FK1pkkrmb5o0yuvx29srqmgmiuo` FOREIGN KEY (`patient_id`) REFERENCES `users` (`user_id`),
                              CONSTRAINT `FKf773yf10kos7phrxt6n5ftvd7` FOREIGN KEY (`practitioner_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `encounters`
--

LOCK TABLES `encounters` WRITE;
/*!40000 ALTER TABLE `encounters` DISABLE KEYS */;
INSERT INTO `encounters` VALUES (0x01B98A6D04A2473D9753EF84639D1A38,'Patient came to the clinic with a high fever, suspecting the flu. After some tests the conclusion was that it was the common cold with some stronger symptoms','2025-03-15 12:03:00.000000',0x586DE8E07FF649F994CDC32F00BA9E33,0x92E06867C3C511F081277E98026CDA3C),(0x0C230598FC1C4E198FB3094926EA7BA1,'Random Test 123','2025-11-18 04:05:00.000000',0x586DE8E07FF649F994CDC32F00BA9E33,0x92E06867C3C511F081277E98026CDA3C);
/*!40000 ALTER TABLE `encounters` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `locations`
--

DROP TABLE IF EXISTS `locations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `locations` (
                             `location_id` binary(16) NOT NULL,
                             `location_type` enum('Danderyd','Huddinge','Kungsholmen','Sodermalm','Solna') NOT NULL,
                             PRIMARY KEY (`location_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `locations`
--

LOCK TABLES `locations` WRITE;
/*!40000 ALTER TABLE `locations` DISABLE KEYS */;
INSERT INTO `locations` VALUES (0x92DC1AA4C3C511F081277E98026CDA3C,'Kungsholmen'),(0x92DC1DE5C3C511F081277E98026CDA3C,'Danderyd'),(0x92DC1F3BC3C511F081277E98026CDA3C,'Sodermalm'),(0x92DC1FADC3C511F081277E98026CDA3C,'Solna');
/*!40000 ALTER TABLE `locations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `messages`
--

DROP TABLE IF EXISTS `messages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `messages` (
                            `message_id` binary(16) NOT NULL,
                            `date_time` datetime(6) NOT NULL,
                            `message` text NOT NULL,
                            `sender_id` binary(16) NOT NULL,
                            `session_id` binary(16) NOT NULL,
                            PRIMARY KEY (`message_id`),
                            KEY `FK4ui4nnwntodh6wjvck53dbk9m` (`sender_id`),
                            KEY `FKtkbtam456hs6b6y3d81c08rpx` (`session_id`),
                            CONSTRAINT `FK4ui4nnwntodh6wjvck53dbk9m` FOREIGN KEY (`sender_id`) REFERENCES `users` (`user_id`),
                            CONSTRAINT `FKtkbtam456hs6b6y3d81c08rpx` FOREIGN KEY (`session_id`) REFERENCES `sessions` (`session_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `messages`
--

LOCK TABLES `messages` WRITE;
/*!40000 ALTER TABLE `messages` DISABLE KEYS */;
INSERT INTO `messages` VALUES (0x1407F0E65C1F489993BBE5E000FAF959,'2025-11-17 16:47:53.636934','Please don\'t use messages to spam',0x92E06867C3C511F081277E98026CDA3C,0x068C07AB7C09464287B19A4EF1782C6A),(0x24E424B75F3F4844A37E248324441A16,'2025-11-18 16:11:18.030446','test 3',0x3EE429C059E54AB292AD47515C15B530,0x068C07AB7C09464287B19A4EF1782C6A),(0x490B5BE4574447E2862D69FB8013D8F8,'2025-11-18 16:12:42.446105','Okej.',0x92E06867C3C511F081277E98026CDA3C,0x2AFA393B1E494C12BC4F93E5684DA570),(0x54C7E9AAF7FD4BF8A9AA9425C5EFC109,'2025-11-17 16:47:35.172857','Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry\'s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.',0x3EE429C059E54AB292AD47515C15B530,0x068C07AB7C09464287B19A4EF1782C6A),(0x5975AC7B07974893ADAD0BB826C66966,'2025-11-18 15:57:04.099469','Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus a lacinia augue, sit amet fermentum urna.',0x3EE429C059E54AB292AD47515C15B530,0x2AFA393B1E494C12BC4F93E5684DA570),(0x73F2180249B741FE9446D25326C18D9F,'2025-11-18 16:11:16.252925','MessageController test 2',0x3EE429C059E54AB292AD47515C15B530,0x068C07AB7C09464287B19A4EF1782C6A);
/*!40000 ALTER TABLE `messages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `observations`
--

DROP TABLE IF EXISTS `observations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `observations` (
                                `observation_id` binary(16) NOT NULL,
                                `description` varchar(255) NOT NULL,
                                `observation_date` datetime(6) NOT NULL,
                                `patient_id` binary(16) NOT NULL,
                                `practitioner_id` binary(16) NOT NULL,
                                PRIMARY KEY (`observation_id`),
                                KEY `FKsf34347604wlc7amo427uy7y3` (`patient_id`),
                                KEY `FKclauoqcstc94jx4lce4e4vety` (`practitioner_id`),
                                CONSTRAINT `FKclauoqcstc94jx4lce4e4vety` FOREIGN KEY (`practitioner_id`) REFERENCES `users` (`user_id`),
                                CONSTRAINT `FKsf34347604wlc7amo427uy7y3` FOREIGN KEY (`patient_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `observations`
--

LOCK TABLES `observations` WRITE;
/*!40000 ALTER TABLE `observations` DISABLE KEYS */;
INSERT INTO `observations` VALUES (0x1B334E70B85542C1AEC6EEC91AA7E7FA,'Random Test 456','2025-10-27 03:42:00.000000',0x586DE8E07FF649F994CDC32F00BA9E33,0x92E06867C3C511F081277E98026CDA3C),(0x25AD3AD7891B4D7389AC85930F16E835,'High fever 39 degrees\nSweaty\nSneezes a lot\nPersistent cough','2025-03-15 11:34:00.000000',0x586DE8E07FF649F994CDC32F00BA9E33,0x92E06867C3C511F081277E98026CDA3C);
/*!40000 ALTER TABLE `observations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `organizations`
--

DROP TABLE IF EXISTS `organizations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `organizations` (
                                 `organization_id` binary(16) NOT NULL,
                                 `organization_type` enum('DanderydsSjukhus','KarolinskaUniversitetssjukhuset','LillaErstagarden','Sodersjukhuset','StGoransSjukhus') NOT NULL,
                                 `location_id` binary(16) NOT NULL,
                                 PRIMARY KEY (`organization_id`),
                                 UNIQUE KEY `UKg0kfbrg9bob3le5fso86b7ebj` (`location_id`),
                                 CONSTRAINT `FKkvfsnlewse3pfw7qexip9mdx4` FOREIGN KEY (`location_id`) REFERENCES `locations` (`location_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `organizations`
--

LOCK TABLES `organizations` WRITE;
/*!40000 ALTER TABLE `organizations` DISABLE KEYS */;
INSERT INTO `organizations` VALUES (0x92DE46EDC3C511F081277E98026CDA3C,'DanderydsSjukhus',0x92DC1DE5C3C511F081277E98026CDA3C),(0x92DE4A1AC3C511F081277E98026CDA3C,'LillaErstagarden',0x92DC1AA4C3C511F081277E98026CDA3C),(0x92DE4C96C3C511F081277E98026CDA3C,'Sodersjukhuset',0x92DC1F3BC3C511F081277E98026CDA3C),(0x92DE4DC2C3C511F081277E98026CDA3C,'StGoransSjukhus',0x92DC1FADC3C511F081277E98026CDA3C);
/*!40000 ALTER TABLE `organizations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sessions`
--

DROP TABLE IF EXISTS `sessions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sessions` (
                            `session_id` binary(16) NOT NULL,
                            `creation_date` datetime(6) NOT NULL,
                            `receiver_id` binary(16) NOT NULL,
                            `sender_id` binary(16) NOT NULL,
                            `subject` varchar(255) NOT NULL,
                            PRIMARY KEY (`session_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sessions`
--

LOCK TABLES `sessions` WRITE;
/*!40000 ALTER TABLE `sessions` DISABLE KEYS */;
INSERT INTO `sessions` VALUES (0x068C07AB7C09464287B19A4EF1782C6A,'2025-11-17 16:47:20.756100',0x92E06867C3C511F081277E98026CDA3C,0x3EE429C059E54AB292AD47515C15B530,'Hälsobesök #1 2025'),(0x2AFA393B1E494C12BC4F93E5684DA570,'2025-11-18 15:56:42.738676',0x92E06867C3C511F081277E98026CDA3C,0x3EE429C059E54AB292AD47515C15B530,'Test angående förenklade DTO:er');
/*!40000 ALTER TABLE `sessions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
                         `user_role` varchar(31) NOT NULL,
                         `user_id` binary(16) NOT NULL,
                         `email` varchar(255) NOT NULL,
                         `full_name` varchar(255) NOT NULL,
                         `password` varchar(255) NOT NULL,
                         `user_type` enum('Doctor','OtherStaff','Patient') NOT NULL,
                         `organization_id` binary(16) DEFAULT NULL,
                         PRIMARY KEY (`user_id`),
                         UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`),
                         KEY `FKqpugllwvyv37klq7ft9m8aqxk` (`organization_id`),
                         CONSTRAINT `FKqpugllwvyv37klq7ft9m8aqxk` FOREIGN KEY (`organization_id`) REFERENCES `organizations` (`organization_id`),
                         CONSTRAINT `users_chk_1` CHECK ((`user_role` in (_utf8mb4'PATIENT',_utf8mb4'PRACTITIONER')))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('PATIENT',0x3EE429C059E54AB292AD47515C15B530,'alexanderby03@gmail.com','Alexander Bykadorov','asdasd','Patient',NULL),('PATIENT',0x47C98B2829F544C6BAAE72577E8D5115,'annahak@gmail.com','Anna Håkansson','patient123','Patient',NULL),('PATIENT',0x586DE8E07FF649F994CDC32F00BA9E33,'kalle89@live.se','Karl Karlsson','patient123','Patient',NULL),('PATIENT',0x6DED173F62474957BF88758F9C6C08B6,'louisesj93@hotmail.com','Louise Smith Jones','patient123','Patient',NULL),('PRACTITIONER',0x92E06867C3C511F081277E98026CDA3C,'anna.lund@ds.se','Dr. Anna Lund','doctor123','Doctor',0x92DE46EDC3C511F081277E98026CDA3C),('PRACTITIONER',0x92E06EF4C3C511F081277E98026CDA3C,'erik.svensson@sodersjukhuset.se','Dr. Erik Svensson','doctor123','Doctor',0x92DE4C96C3C511F081277E98026CDA3C),('PRACTITIONER',0x92E070EDC3C511F081277E98026CDA3C,'sofia.nilsson@stgorans.se','Dr. Sofia Nilsson','doctor123','Doctor',0x92DE4DC2C3C511F081277E98026CDA3C),('PRACTITIONER',0x92E2D347C3C511F081277E98026CDA3C,'karl.andersson@ds.se','Karl Andersson','staff123','OtherStaff',0x92DE46EDC3C511F081277E98026CDA3C),('PRACTITIONER',0x92E2D936C3C511F081277E98026CDA3C,'lisa.berg@sodersjukhuset.se','Lisa Berg','staff123','OtherStaff',0x92DE4C96C3C511F081277E98026CDA3C),('PRACTITIONER',0x92E2DB1FC3C511F081277E98026CDA3C,'johan.ek@stgorans.se','Johan Ek','staff123','OtherStaff',0x92DE4DC2C3C511F081277E98026CDA3C),('PATIENT',0x92E4F726C3C511F081277E98026CDA3C,'emil.karlsson@gmail.com','Emil Karlsson','patient123','Patient',NULL),('PATIENT',0x92E4FA73C3C511F081277E98026CDA3C,'sara.nilsson@gmail.com','Sara Nilsson','patient123','Patient',NULL),('PATIENT',0x92E4FB29C3C511F081277E98026CDA3C,'maria.lind@outlook.com','Maria Lind','patient123','Patient',NULL);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-11-24 18:23:36
