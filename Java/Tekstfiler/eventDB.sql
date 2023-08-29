CREATE DATABASE eventDB
//Lager databasen

CREATE USER 'sensor'@'localhost' IDENTIFIED BY 'passord';
GRANT ALL PRIVILEGES ON eventDB.* TO 'sensor'@'localhost';
//Oppretter en bruker til sensor

DROP TABLE IF EXISTS `events`;
CREATE TABLE `events` (
  `event_id` int NOT NULL,
  `event_name` varchar(100) DEFAULT NULL,
  `event_date` date DEFAULT NULL,
  `event_program` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`event_id`)
//oppretter events med diverse verdier
  INSERT INTO `events` VALUES (1,' Graduation','2023-06-01',
  '\n------------------------------\nIntroduskjon - 30 minutter,\nTale - 1 minutt,\nsluttkommentarer - 15 minutter\n------------------------------\n');
//setter inn data for programbeskrivelsen

DROP TABLE IF EXISTS `participants`;
CREATE TABLE participants (
  event_id INT,
  guests INT,
  student_name VARCHAR(255) NOT NULL,
  study_program VARCHAR(255) NOT NULL,
  PRIMARY KEY (student_name),
  CONSTRAINT fk_event FOREIGN KEY (event_id) REFERENCES events (event_id)
);
//oppretter praticipants med tilhørende verdier