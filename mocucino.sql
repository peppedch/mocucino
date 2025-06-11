-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`Utenti`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Utenti` (
  `username` VARCHAR(45) NOT NULL,
  `nome` VARCHAR(45) NULL,
  `cognome` VARCHAR(45) NULL,
  `email` VARCHAR(45) NULL,
  `password` VARCHAR(45) NULL,
  `biografia` VARCHAR(45) NULL,
  `immagine` VARCHAR(255) NULL,
  PRIMARY KEY (`username`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Raccolte`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Raccolte` (
  `idRaccolta` INT NOT NULL,
  `titolo` VARCHAR(45) NULL,
  `descrizione` VARCHAR(800) NULL,
  `Utenti_username` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idRaccolta`),
  INDEX `fk_Raccolte_Utenti1_idx` (`Utenti_username` ASC) VISIBLE,
  CONSTRAINT `fk_Raccolte_Utenti1`
    FOREIGN KEY (`Utenti_username`)
    REFERENCES `mydb`.`Utenti` (`username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Ricette`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Ricette` (
  `idRicetta` INT NOT NULL,
  `titolo` VARCHAR(45) NULL,
  `procedimento` VARCHAR(45) NULL,
  `tempo` INT NULL,
  `visibilita` TINYINT NULL,
  `numLike` INT NULL,
  `numCommenti` INT NULL,
  `dataPubblicazione` DATETIME NULL,
  `Utenti_username` VARCHAR(45) NOT NULL,
  `Raccolte_idRaccolta` INT NOT NULL,
  PRIMARY KEY (`idRicetta`),
  INDEX `fk_Ricette_Utenti_idx` (`Utenti_username` ASC) VISIBLE,
  INDEX `fk_Ricette_Raccolte1_idx` (`Raccolte_idRaccolta` ASC) VISIBLE,
  CONSTRAINT `fk_Ricette_Utenti`
    FOREIGN KEY (`Utenti_username`)
    REFERENCES `mydb`.`Utenti` (`username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Ricette_Raccolte1`
    FOREIGN KEY (`Raccolte_idRaccolta`)
    REFERENCES `mydb`.`Raccolte` (`idRaccolta`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Likes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Likes` (
  `Utenti_username` VARCHAR(45) NOT NULL,
  `Ricette_idRicetta` INT NOT NULL,
  PRIMARY KEY (`Utenti_username`, `Ricette_idRicetta`),
  INDEX `fk_Likes_Utenti1_idx` (`Utenti_username` ASC) VISIBLE,
  INDEX `fk_Likes_Ricette1_idx` (`Ricette_idRicetta` ASC) VISIBLE,
  CONSTRAINT `fk_Likes_Utenti1`
    FOREIGN KEY (`Utenti_username`)
    REFERENCES `mydb`.`Utenti` (`username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Likes_Ricette1`
    FOREIGN KEY (`Ricette_idRicetta`)
    REFERENCES `mydb`.`Ricette` (`idRicetta`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Commenti`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Commenti` (
  `idCommento` INT NOT NULL,
  `autore` VARCHAR(45) NULL,
  `testoCommento` VARCHAR(45) NULL,
  `dataPubblicazione` DATETIME NULL,
  `Ricette_idRicetta` INT NOT NULL,
  `Utenti_username` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idCommento`),
  INDEX `fk_Commenti_Ricette1_idx` (`Ricette_idRicetta` ASC) VISIBLE,
  INDEX `fk_Commenti_Utenti1_idx` (`Utenti_username` ASC) VISIBLE,
  CONSTRAINT `fk_Commenti_Ricette1`
    FOREIGN KEY (`Ricette_idRicetta`)
    REFERENCES `mydb`.`Ricette` (`idRicetta`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Commenti_Utenti1`
    FOREIGN KEY (`Utenti_username`)
    REFERENCES `mydb`.`Utenti` (`username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Ingredienti`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Ingredienti` (
  `idIngrediente` INT NOT NULL,
  `nome` VARCHAR(45) NULL,
  PRIMARY KEY (`idIngrediente`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Ricette_has_Ingredienti`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Ricette_has_Ingredienti` (
  `Ricette_idRicetta` INT NOT NULL,
  `Ingredienti_idIngrediente` INT NOT NULL,
  `quantità` DECIMAL(10,2) NULL,
  `unità` VARCHAR(15) NULL,
  PRIMARY KEY (`Ricette_idRicetta`, `Ingredienti_idIngrediente`),
  INDEX `fk_Ricette_has_Ingredienti_Ingredienti1_idx` (`Ingredienti_idIngrediente` ASC) VISIBLE,
  INDEX `fk_Ricette_has_Ingredienti_Ricette1_idx` (`Ricette_idRicetta` ASC) VISIBLE,
  CONSTRAINT `fk_Ricette_has_Ingredienti_Ricette1`
    FOREIGN KEY (`Ricette_idRicetta`)
    REFERENCES `mydb`.`Ricette` (`idRicetta`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Ricette_has_Ingredienti_Ingredienti1`
    FOREIGN KEY (`Ingredienti_idIngrediente`)
    REFERENCES `mydb`.`Ingredienti` (`idIngrediente`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Tags`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Tags` (
  `idTag` INT NOT NULL,
  `nome` VARCHAR(45) NULL,
  PRIMARY KEY (`idTag`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Ricette_has_Tags`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Ricette_has_Tags` (
  `Ricette_idRicetta` INT NOT NULL,
  `Tags_idTag` INT NOT NULL,
  PRIMARY KEY (`Ricette_idRicetta`, `Tags_idTag`),
  INDEX `fk_Ricette_has_Tags_Tags1_idx` (`Tags_idTag` ASC) VISIBLE,
  INDEX `fk_Ricette_has_Tags_Ricette1_idx` (`Ricette_idRicetta` ASC) VISIBLE,
  CONSTRAINT `fk_Ricette_has_Tags_Ricette1`
    FOREIGN KEY (`Ricette_idRicetta`)
    REFERENCES `mydb`.`Ricette` (`idRicetta`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Ricette_has_Tags_Tags1`
    FOREIGN KEY (`Tags_idTag`)
    REFERENCES `mydb`.`Tags` (`idTag`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
