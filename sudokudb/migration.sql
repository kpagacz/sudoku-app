-- MySQL Script generated by MySQL Workbench
-- Fri May 20 20:44:36 2022
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0;
SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0;
SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE =
        'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema sudoku_club
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema sudoku_club
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `sudoku_club` DEFAULT CHARACTER SET utf8;
USE `sudoku_club`;

-- -----------------------------------------------------
-- Table `sudoku_club`.`Users`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `sudoku_club`.`Users`;

CREATE TABLE IF NOT EXISTS `sudoku_club`.`Users`
(
    `login`    VARCHAR(45) NOT NULL,
    `password` VARCHAR(80) NOT NULL,
    PRIMARY KEY (`login`),
    UNIQUE INDEX `login_UNIQUE` (`login` ASC) VISIBLE
)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sudoku_club`.`Sudokus`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `sudoku_club`.`Sudokus`;

CREATE TABLE IF NOT EXISTS `sudoku_club`.`Sudokus`
(
    `idSudokus` INT         NOT NULL AUTO_INCREMENT,
    `title`     VARCHAR(45) NOT NULL,
    `cells`     JSON        NOT NULL,
    `userLogin` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`idSudokus`),
    INDEX `fk_Sudokus_Users1_idx` (`userLogin` ASC) VISIBLE,
    CONSTRAINT `fk_Sudokus_Users1`
        FOREIGN KEY (`userLogin`)
            REFERENCES `sudoku_club`.`Users` (`login`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sudoku_club`.`SolvedSudokus`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `sudoku_club`.`SolvedSudokus`;

CREATE TABLE IF NOT EXISTS `sudoku_club`.`SolvedSudokus`
(
    `idSolvedSudokus` INT         NOT NULL AUTO_INCREMENT,
    `sudokuId`        INT         NOT NULL,
    `userLogin`       VARCHAR(45) NOT NULL,
    PRIMARY KEY (`idSolvedSudokus`, `sudokuId`, `userLogin`),
    INDEX `fk_SolvedSudokus_Sudokus1_idx` (`sudokuId` ASC) VISIBLE,
    INDEX `fk_SolvedSudokus_Users1_idx` (`userLogin` ASC) VISIBLE,
    CONSTRAINT `fk_SolvedSudokus_Sudokus1`
        FOREIGN KEY (`sudokuId`)
            REFERENCES `sudoku_club`.`Sudokus` (`idSudokus`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `fk_SolvedSudokus_Users1`
        FOREIGN KEY (`userLogin`)
            REFERENCES `sudoku_club`.`Users` (`login`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB;


SET SQL_MODE = @OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS;