CREATE DATABASE universityDB
//Lager databasen
CREATE USER 'sensor1'@'localhost' IDENTIFIED BY 'passord';
GRANT ALL PRIVILEGES ON universityDB.* TO 'sensor1'@'localhost';
//Lager bruker til sensor
DROP TABLE IF EXISTS `staff`;
CREATE TABLE `staff` (
  `staff_id` int NOT NULL,
  `staff_name` varchar(100) DEFAULT NULL,
  `role` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`staff_id`)
//oppretter tabell til de ansatte
INSERT INTO `staff` VALUES (1,'marcus dahl','lærer'),(2,'lester lasrado','lærer'),(3,'bogdan marculescu','lærer'),(4,'trine meza','rektor');
//setter inn verdier til de ansatte
DROP TABLE IF EXISTS `students`;
CREATE TABLE `students` (
  `student_id` int NOT NULL,
  `student_name` varchar(100) DEFAULT NULL,
  `study_program` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`student_id`));
  INSERT INTO `students` VALUES (1,'staysman','Informasjonsteknologi'),(2,'else kåss','hr og ledelse'),(3,'jonis josef','anvendt psykologi'),(4,'adrian sellevoll','bedriftsøkonomi'),(5,'herman flesvig','hr og ledelse'),(6,'petter northug','anvendt psykologi'),(7,'jens stoltenberg','informasjonstekologi'),(8,'chirag patel','bedriftsøkonomi');
//oppretter tabell til studenter og befolker denne

  DROP TABLE IF EXISTS `study_programs`;
  CREATE TABLE `study_programs` (
    `program_id` int NOT NULL,
    `program_name` varchar(100) DEFAULT NULL,
    PRIMARY KEY (`program_id`)
    INSERT INTO `study_programs` VALUES (1,'Informasjonsteknologi'),(2,'hr og ledelse'),(3,'anvendt psykologi'),(4,'bedriftsøkonomi');
   //lager tabell til studielinjer