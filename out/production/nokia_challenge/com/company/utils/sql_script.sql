-- MySQL Script generated by MySQL Workbench
-- Thu Nov  5 02:55:10 2020
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema nokia_challenge_db
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema nokia_challenge_db
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `nokia_challenge_db` DEFAULT CHARACTER SET utf8 ;
USE `nokia_challenge_db` ;

-- -----------------------------------------------------
-- Table `nokia_challenge_db`.`people`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `nokia_challenge_db`.`people` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `nationality` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `nokia_challenge_db`.`movies`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `nokia_challenge_db`.`movies` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(45) NOT NULL,
  `director_id` INT NOT NULL,
  `duration` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_movies_people_idx` (`director_id` ASC) INVISIBLE,
  UNIQUE INDEX `uniquek_title` (`title` ASC, `director_id` ASC) VISIBLE,
  CONSTRAINT `fk_movies_people`
    FOREIGN KEY (`director_id`)
    REFERENCES `nokia_challenge_db`.`people` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `nokia_challenge_db`.`starring`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `nokia_challenge_db`.`starring` (
  `movies_id` INT NOT NULL,
  `people_id` INT NOT NULL,
  PRIMARY KEY (`movies_id`, `people_id`),
  INDEX `fk_movies_has_people_people1_idx` (`people_id` ASC) VISIBLE,
  INDEX `fk_movies_has_people_movies1_idx` (`movies_id` ASC) VISIBLE,
  CONSTRAINT `fk_movies_has_people_movies1`
    FOREIGN KEY (`movies_id`)
    REFERENCES `nokia_challenge_db`.`movies` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_movies_has_people_people1`
    FOREIGN KEY (`people_id`)
    REFERENCES `nokia_challenge_db`.`people` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;