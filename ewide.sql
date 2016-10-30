CREATE USER IF NOT EXISTS ‘admin’ PASSWORD admin ADMIN;

-- -----------------------------------------------------
-- CLEAR DATABASE
-- -----------------------------------------------------

DROP TABLE Version;
DROP TABLE File;
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
pwd VARCHAR(45) NOT NULL,
email VARCHAR(45) NOT NULL UNIQUE,
directory VARCHAR(255) UNIQUE,
);

-- -----------------------------------------------------
-- Table Project
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Project
(
projectID INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(45) NOT NULL,
compiler VARCHAR(45),
linkRepo VARCHAR(255) NOT NULL UNIQUE,
description CLOB,
fileTree VARCHAR(255) NOT NULL UNIQUE,
linkMakefile VARCHAR(255) NOT NULL UNIQUE,
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
-- Table File
-- -----------------------------------------------------



CREATE TABLE IF NOT EXISTS File
(
fileID INT NOT NULL PRIMARY KEY,
projectID INT NOT NULL,
name VARCHAR(45) NOT NULL,
path  VARCHAR(255) NOT NULL UNIQUE,
type VARCHAR(45) NOT NULL,
FOREIGN KEY (projectID) REFERENCES Project(projectID)
 ON DELETE NO ACTION
 ON UPDATE CASCADE,
);

-- -----------------------------------------------------
-- Table Version
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Version
(
versionID INT NOT NULL PRIMARY KEY,
userID INT NOT NULL,
fileID INT NOT NULL,
version INT NOT NULL DEFAULT 0,
date DATE NOT NULL,
text CLOB,
FOREIGN KEY (userID) REFERENCES User(userID)
 ON DELETE NO ACTION
 ON UPDATE CASCADE,
FOREIGN KEY (fileID) REFERENCES File(fileID)
 ON DELETE NO ACTION
 ON UPDATE CASCADE
);


-- -----------------------------------------------------
-- Data samples
-- -----------------------------------------------------


INSERT INTO USER Values(0001,'zoidberg', 'admin', 'zoidberg@ewide.com', 'zoidberg_directory');
INSERT INTO USER Values(0002,'fry', 'admin', 'fry@ewide.com', 'fry_directory');

INSERT INTO PROJECT Values(1, 'Projet MULTIMIF', 'compiler', 'multimif_linkrepo', 'unknow','multimif_filetree','multimif_linkmake');
INSERT INTO PROJECT Values(2, 'Projet IA', 'compiler', 'projet_ia_linkrepo', 'unknow','projet_ia_filetree','projet_ia_linkmake');

INSERT INTO ROLE Values(1, 1, 1, 'MANAGER');
INSERT INTO ROLE Values(2, 2, 2, 'MANAGER');

INSERT INTO TASK (taskID, userID, projectID, type, state, text) Values(1,1,1, 'TODO', 'Pending', 'Task 1');
INSERT INTO TASK (taskID, userID, projectID, type, state, text) Values(2,1,1, 'TODO', 'Pending', 'Task 2');
INSERT INTO TASK (taskID, userID, projectID, type, state, text) Values(3,1,1, 'TODO', 'Pending', 'Task 3');

