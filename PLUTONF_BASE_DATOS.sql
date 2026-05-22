-- MySQL dump 10.13  Distrib 8.0.45, for Win64 (x86_64)
--
-- Host: localhost    Database: plutonf
-- ------------------------------------------------------
-- Server version	8.0.45

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
-- Table structure for table `astronautas`
--

DROP TABLE IF EXISTS `astronautas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `astronautas` (
  `idAstronauta` int NOT NULL AUTO_INCREMENT,
  `dni` varchar(20) NOT NULL,
  `nombre` varchar(100) DEFAULT NULL,
  `habilidadPrincipal` varchar(100) DEFAULT NULL,
  `rango` varchar(50) DEFAULT NULL,
  `edad` int DEFAULT NULL,
  `horasVuelo` int DEFAULT NULL,
  `nacionalidad` varchar(50) DEFAULT NULL,
  `idModulo` int DEFAULT NULL,
  PRIMARY KEY (`idAstronauta`),
  UNIQUE KEY `dni` (`dni`),
  KEY `fk_astronauta_modulo` (`idModulo`),
  CONSTRAINT `fk_astronauta_modulo` FOREIGN KEY (`idModulo`) REFERENCES `modulos` (`idModulo`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `astronautas`
--

LOCK TABLES `astronautas` WRITE;
/*!40000 ALTER TABLE `astronautas` DISABLE KEYS */;
INSERT INTO `astronautas` VALUES (1,'11111111A','Manuel','Piloto','Comandante',30,1200,'España',1),(2,'22222222B','Laura','Ingeniera','Teniente',28,850,'España',2),(3,'33333333C','Carlos','Médico','Capitán',35,1400,'México',3),(4,'44444444D','Sofía','Bióloga','Especialista',27,600,'Argentina',4),(5,'55555555E','David','Mecánico','Técnico',32,950,'Chile',5),(6,'1283728J','Fede','Agil','Comandantee',0,0,'',1);
/*!40000 ALTER TABLE `astronautas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `intervenciones_mantenimiento`
--

DROP TABLE IF EXISTS `intervenciones_mantenimiento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `intervenciones_mantenimiento` (
  `idIntervencion` int NOT NULL AUTO_INCREMENT,
  `idMantenimiento` int DEFAULT NULL,
  `idAstronauta` int DEFAULT NULL,
  `idModulo` int DEFAULT NULL,
  `fechaIntervencion` date DEFAULT NULL,
  `observaciones` text,
  PRIMARY KEY (`idIntervencion`),
  KEY `fk_int_mantenimiento` (`idMantenimiento`),
  KEY `fk_int_astronauta` (`idAstronauta`),
  KEY `fk_int_modulo` (`idModulo`),
  CONSTRAINT `fk_int_astronauta` FOREIGN KEY (`idAstronauta`) REFERENCES `astronautas` (`idAstronauta`) ON DELETE CASCADE,
  CONSTRAINT `fk_int_mantenimiento` FOREIGN KEY (`idMantenimiento`) REFERENCES `mantenimiento` (`idMantenimiento`) ON DELETE CASCADE,
  CONSTRAINT `fk_int_modulo` FOREIGN KEY (`idModulo`) REFERENCES `modulos` (`idModulo`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `intervenciones_mantenimiento`
--

LOCK TABLES `intervenciones_mantenimiento` WRITE;
/*!40000 ALTER TABLE `intervenciones_mantenimiento` DISABLE KEYS */;
INSERT INTO `intervenciones_mantenimiento` VALUES (1,1,1,1,'2026-06-01','Oxígeno revisado correctamente'),(2,2,2,2,'2026-06-02','Filtros sustituidos'),(3,3,5,5,'2026-06-03','Motor auxiliar reparadoo'),(4,4,4,4,'2026-06-04','Sistema eléctrico estable'),(5,5,3,4,'2026-06-05','Conductos limpios');
/*!40000 ALTER TABLE `intervenciones_mantenimiento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mantenimiento`
--

DROP TABLE IF EXISTS `mantenimiento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mantenimiento` (
  `idMantenimiento` int NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(255) DEFAULT NULL,
  `prioridad` enum('BAJA','ALTA','CRITICA') DEFAULT NULL,
  `costeRecursos` double DEFAULT NULL,
  `duracionEntidad` varchar(50) DEFAULT NULL,
  `herramientaNecesaria` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`idMantenimiento`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mantenimiento`
--

LOCK TABLES `mantenimiento` WRITE;
/*!40000 ALTER TABLE `mantenimiento` DISABLE KEYS */;
INSERT INTO `mantenimiento` VALUES (1,'Revisión de oxígeno','ALTA',150,'2 horas','Medidor de oxígeno'),(2,'Cambio de filtros','BAJA',80,'1 hora','Filtro espacial'),(3,'Reparación del motor auxiliar','CRITICA',500,'5 horas','Llave especial'),(4,'Comprobación eléctrica','ALTA',200,'3 horas','Multímetroo'),(5,'Limpieza de conductos','BAJA',60,'1 hora','Kit de limpieza');
/*!40000 ALTER TABLE `mantenimiento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `misiones`
--

DROP TABLE IF EXISTS `misiones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `misiones` (
  `idMision` int NOT NULL AUTO_INCREMENT,
  `nombrePlaneta` varchar(100) DEFAULT NULL,
  `objetivo` text,
  `idNave` int DEFAULT NULL,
  PRIMARY KEY (`idMision`),
  KEY `fk_mision_nave` (`idNave`),
  CONSTRAINT `fk_mision_nave` FOREIGN KEY (`idNave`) REFERENCES `nave` (`idNave`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `misiones`
--

LOCK TABLES `misiones` WRITE;
/*!40000 ALTER TABLE `misiones` DISABLE KEYS */;
INSERT INTO `misiones` VALUES (1,'Marte','Explorar superficie marciana',1),(2,'Júpiter','Analizar tormentas atmosféricas',2),(3,'Saturno','Estudiar sus anillos',3),(5,'Plutón','Recoger muestras heladas',5),(6,'Urano','Buscar aguaa',2),(7,'Neptuno','Limpiar',2),(10,'Marte','Marcianos',2);
/*!40000 ALTER TABLE `misiones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `modulos`
--

DROP TABLE IF EXISTS `modulos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `modulos` (
  `idModulo` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  `sector` varchar(50) DEFAULT NULL,
  `capacidadMaxima` int DEFAULT NULL,
  `nivelOxigeno` double DEFAULT NULL,
  `temperaturaInterior` double DEFAULT NULL,
  `idNave` int DEFAULT NULL,
  `nivelSeguridad` int DEFAULT NULL,
  `numExperimentos` int DEFAULT NULL,
  `numCamas` int DEFAULT NULL,
  PRIMARY KEY (`idModulo`),
  KEY `fk_modulo_nave` (`idNave`),
  CONSTRAINT `fk_modulo_nave` FOREIGN KEY (`idNave`) REFERENCES `nave` (`idNave`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `modulos`
--

LOCK TABLES `modulos` WRITE;
/*!40000 ALTER TABLE `modulos` DISABLE KEYS */;
INSERT INTO `modulos` VALUES (1,'Control Central','Sector A',10,95,22,1,3,NULL,NULL),(2,'Laboratorio Químico','Sector B',6,90,21,NULL,NULL,12,NULL),(3,'Zona Vivienda','Sector C',20,98,23,3,NULL,NULL,10),(4,'Laboratorio Biológico','Sector D',8,23,20,4,NULL,5,NULL),(5,'Control Secundario','Sector E',12,96,22,5,2,NULL,NULL),(6,'habitacon','C',10,23,22,1,7,NULL,NULL);
/*!40000 ALTER TABLE `modulos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nave`
--

DROP TABLE IF EXISTS `nave`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nave` (
  `idNave` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  `alcance` double DEFAULT NULL,
  `combustibleActual` double DEFAULT NULL,
  `fechaLanzamiento` date DEFAULT NULL,
  `estadoNave` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`idNave`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nave`
--

LOCK TABLES `nave` WRITE;
/*!40000 ALTER TABLE `nave` DISABLE KEYS */;
INSERT INTO `nave` VALUES (1,'Apolo XI',50000,85,'2026-01-10','Operativa'),(2,'Orion Alpha',75000,70,'2026-02-15','En misión'),(3,'Galaxia I',60000,95,'2026-03-20','Operativa'),(4,'Nebula X',90000,60,'2026-04-05','Revisión'),(5,'Pluton Explorer',120000,88,'2026-05-12','Operativo'),(7,'Sanchez-12',30000,40,'2026-01-25','Rota');
/*!40000 ALTER TABLE `nave` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-05-22 12:38:11
