CREATE TABLE documento (
  Id int NOT NULL AUTO_INCREMENT,
  Name varchar(100) DEFAULT NULL,
  PRIMARY KEY (Id)
);

CREATE TABLE funcionario (
  Id int NOT NULL AUTO_INCREMENT,
  Name varchar(100) NOT NULL,
  Email varchar(100) NOT NULL,
  BirthDate datetime NOT NULL,
  PRIMARY KEY (Id)
);

INSERT INTO documento (Name) VALUES 
  ('Certidão de nascimento'),
  ('Certidão de casamento'),
  ('Certidão de óbito'),
  ('Reconhecimento de firma');

INSERT INTO funcionario (Name, Email, BirthDate) VALUES 
  ('Bob Brown','bob@gmail.com','1998-04-21 00:00:00'),
  ('Maria Green','maria@gmail.com','1979-12-31 00:00:00'),
  ('Alex Grey','alex@gmail.com','1988-01-15 00:00:00'),
  ('Martha Red','martha@gmail.com','1993-11-30 00:00:00'),
  ('Donald Blue','donald@gmail.com','2000-01-09 00:00:00'),
  ('Alex Pink','bob@gmail.com','1997-03-04 00:00:00');