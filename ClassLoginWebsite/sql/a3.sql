-- MySQL dump 10.13  Distrib 5.7.21, for Win64 (x86_64)
--
-- Host: localhost    Database: a3
-- ------------------------------------------------------
-- Server version	5.7.21-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `accounts`
--

DROP TABLE IF EXISTS `accounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `accounts` (
  `utorid` varchar(255) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `account_type` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`utorid`),
  UNIQUE KEY `utorid` (`utorid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `accounts`
--

LOCK TABLES `accounts` WRITE;
/*!40000 ALTER TABLE `accounts` DISABLE KEYS */;
INSERT INTO `accounts` VALUES ('alices','Alice','Son','instructor','momson99'),('clairog','Clair','Ohno','student','ohnojoe'),('cornali','Corna','Li','student','jeeli'),('daveid','Dave','Smith','instructor','smither123'),('johno','John','Knee','instructor','johnee33'),('rexoc','Rex','Ocounty','student','countybounty'),('stdoba','Stdman','Board','student','stiffer'),('student','Student','Student','student','student'),('taps','Ta','Pseun','TA','pseun'),('tark','Tarse','Kob','TA','woah');
/*!40000 ALTER TABLE `accounts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `anon_feedback`
--

DROP TABLE IF EXISTS `anon_feedback`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `anon_feedback` (
  `num_id` int(11) NOT NULL AUTO_INCREMENT,
  `utorid` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `fq1` blob,
  `fq2` blob,
  PRIMARY KEY (`num_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `anon_feedback`
--

LOCK TABLES `anon_feedback` WRITE;
/*!40000 ALTER TABLE `anon_feedback` DISABLE KEYS */;
INSERT INTO `anon_feedback` VALUES (1,'daveid','smith','this is a test','also another test'),(2,'daveid','smith','it was great','the course is bad'),(3,'johno','knee','okay','alright'),(4,'alices','son','not too sure','maybe');
/*!40000 ALTER TABLE `anon_feedback` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `course_materials`
--

DROP TABLE IF EXISTS `course_materials`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `course_materials` (
  `mat_type` varchar(255) NOT NULL,
  `out_of` double DEFAULT NULL,
  `weight` double DEFAULT NULL,
  PRIMARY KEY (`mat_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course_materials`
--

LOCK TABLES `course_materials` WRITE;
/*!40000 ALTER TABLE `course_materials` DISABLE KEYS */;
INSERT INTO `course_materials` VALUES ('Assignment 1',50,15),('Assignment 2',40,20),('Final Exam',5,50),('Lab 1',3,3),('Lab 2',3,3),('Lab 3',3,3),('Term Test 1',20,5),('Term Test 2',90,30);
/*!40000 ALTER TABLE `course_materials` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `remark_req`
--

DROP TABLE IF EXISTS `remark_req`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `remark_req` (
  `req_num_id` int(11) NOT NULL AUTO_INCREMENT,
  `utorid` varchar(255) DEFAULT NULL,
  `mat_type` varchar(255) DEFAULT NULL,
  `why` blob NOT NULL,
  PRIMARY KEY (`req_num_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `remark_req`
--

LOCK TABLES `remark_req` WRITE;
/*!40000 ALTER TABLE `remark_req` DISABLE KEYS */;
INSERT INTO `remark_req` VALUES (1,'clairog','Assignment 1','The assignment was too easy. I want to curve the marks'),(2,'rexoc','Assignment 2','The assignment was too hard.'),(3,'cornali','Term Test 1','TA got me good.'),(4,'student','Lab 2','Because I am a student.'),(5,'student','Assignment 1','fdmfdkmfkdmkmkdmkmdkmvkdkdv');
/*!40000 ALTER TABLE `remark_req` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student_marks`
--

DROP TABLE IF EXISTS `student_marks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `student_marks` (
  `utorid` varchar(255) NOT NULL,
  `mat_type` varchar(255) NOT NULL,
  `mark` double DEFAULT NULL,
  PRIMARY KEY (`utorid`,`mat_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student_marks`
--

LOCK TABLES `student_marks` WRITE;
/*!40000 ALTER TABLE `student_marks` DISABLE KEYS */;
INSERT INTO `student_marks` VALUES ('clairog','Assignment 1',50),('clairog','Assignment 2',29),('clairog','Final Exam',2.5),('clairog','Term Test 1',16),('clairog','Term Test 2',77),('cornali','Assignment 1',44),('cornali','Assignment 2',23),('cornali','Final Exam',4),('cornali','Term Test 1',3),('cornali','Term Test 2',88),('rexoc','Assignment 1',14),('rexoc','Assignment 2',37),('rexoc','Final Exam',2),('rexoc','Lab 1',3),('rexoc','Term Test 1',14),('rexoc','Term Test 2',79),('stdoba','Assignment 1',40),('stdoba','Assignment 2',30),('stdoba','Final Exam',4),('stdoba','Term Test 1',17.5),('stdoba','Term Test 2',5),('student','Assignment 1',20),('student','Assignment 2',30),('student','Final Exam',3),('student','Term Test 1',20),('student','Term Test 2',40);
/*!40000 ALTER TABLE `student_marks` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-04-06 19:13:07
