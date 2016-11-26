CREATE USER IF NOT EXISTS admin PASSWORD 'admin' ADMIN;

-- -----------------------------------------------------
-- CLEAR DATABASE
-- -----------------------------------------------------

DROP TABLE Version;
DROP TABLE Task;
DROP TABLE Message;
DROP TABLE Role;
DROP TABLE Project;
DROP TABLE User;

-- -----------------------------------------------------
-- Table User
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS User
(
userID INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
username VARCHAR(45) NOT NULL UNIQUE,
pwd VARCHAR(100) NOT NULL,
email VARCHAR(45) NOT NULL UNIQUE,
directory VARCHAR(255) UNIQUE,
);

-- -----------------------------------------------------
-- Table Project
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Project
(
projectID INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(80) NOT NULL,
compiler VARCHAR(45),
description CLOB,
wiki CLOB
);

-- -----------------------------------------------------
-- Table Role
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Role
(
roleID INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
userID INT NOT NULL,
projectID INT NOT NULL,
role VARCHAR(45) NOT NULL,
FOREIGN KEY (userID) REFERENCES User(userID)
 ON DELETE CASCADE
 ON UPDATE CASCADE,
FOREIGN KEY (projectID) REFERENCES Project(projectID)
 ON DELETE CASCADE
 ON UPDATE CASCADE,
);

-- -----------------------------------------------------
-- Table Message
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Message
(
messageID INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
userID INT NOT NULL,
projectID INT NOT NULL,
text CLOB NOT NULL,
date DATE NOT NULL DEFAULT CURRENT_DATE,
FOREIGN KEY (userID) REFERENCES User(userID)
 ON DELETE NO ACTION
 ON UPDATE CASCADE,
FOREIGN KEY (projectID) REFERENCES Project(projectID)
 ON DELETE NO ACTION
 ON UPDATE CASCADE
);

-- -----------------------------------------------------
-- Table Task
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Task
(
taskID INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
userID INT NOT NULL,
projectID INT NOT NULL,
type VARCHAR(45) NOT NULL,
state VARCHAR(45) NOT NULL,
text CLOB NOT NULL,
description CLOB,
referencedTask INT,
date DATE NOT NULL DEFAULT CURRENT_DATE,
FOREIGN KEY (userID) REFERENCES User(userID)
 ON DELETE NO ACTION
 ON UPDATE CASCADE,
FOREIGN KEY (projectID) REFERENCES Project(projectID)
 ON DELETE NO ACTION
 ON UPDATE CASCADE,
FOREIGN KEY (referencedTask) REFERENCES Task(taskID)
 ON DELETE NO ACTION
 ON UPDATE CASCADE
);

-- -----------------------------------------------------
-- Table Version
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Version
(
version VARCHAR(45) NOT NULL PRIMARY KEY,
userID INT NOT NULL,
projectID INT NOT NULL,
versionID INT NOT NULL DEFAULT 0,
FOREIGN KEY (userID) REFERENCES User(userID)
 ON DELETE NO ACTION
 ON UPDATE CASCADE,
 FOREIGN KEY (projectID) REFERENCES Project(projectID)
 ON DELETE NO ACTION
 ON UPDATE CASCADE,
);


-- -----------------------------------------------------
-- Data samples
-- -----------------------------------------------------

-- zoidberg password: admin
INSERT INTO USER Values(0001,'zoidberg', '$2a$10$WzSOqXRoTXA7QkIdhP3hl.2R85JSgG2nGYltG3HC0DftrefgFH5o6', 'zoidberg@ewide.com', 'zoidberg_directory');
INSERT INTO USER Values(0002,'fry', '$2a$10$WzSOqXRoTXA7QkIdhP3hl.2R85JSgG2nGYltG3HC0DftrefgFH5o6', 'fry@ewide.com', 'fry_directory');
INSERT INTO USER Values(0003,'bender', '$2a$10$WzSOqXRoTXA7QkIdhP3hl.2R85JSgG2nGYltG3HC0DftrefgFH5o6', 'bender@ewide.com', 'bender_directory');
