# 데이터베이스 드랍
CREATE DATABASE IF NOT EXISTS DBTEST;

# 데이터베이스 생성
SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema DBTEST
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema DBTEST
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `DBTEST` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;

# 데이터베이스 사용
USE `DBTEST` ;

# 데이터베이스 테이블 생성
-- -----------------------------------------------------
-- Table `DBTEST`.`CampingCarCompany`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `DBTEST`.`CampingCarCompany` (
  `company_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `address` VARCHAR(255) NOT NULL,
  `phone` VARCHAR(255) NOT NULL,
  `manager_name` VARCHAR(255) NOT NULL,
  `manager_email` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`company_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `DBTEST`.`CampingCar`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `DBTEST`.`CampingCar` (
  `car_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `plate_number` VARCHAR(255) NOT NULL,
  `capacity` INT UNSIGNED NOT NULL,
  `image` LONGBLOB NOT NULL,
  `description` TEXT NOT NULL,
  `rental_price` INT UNSIGNED NOT NULL,
  `company_id` INT NOT NULL,
  `registered_date` DATE NOT NULL,
  PRIMARY KEY (`car_id`),
  UNIQUE INDEX `plate_number_UNIQUE` (`plate_number` ASC) VISIBLE,
  INDEX `company_id` (`company_id` ASC) VISIBLE,
  CONSTRAINT `campingcar_ibfk_1`
    FOREIGN KEY (`company_id`)
    REFERENCES `DBTEST`.`CampingCarCompany` (`company_id`) ON DELETE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `DBTEST`.`Customer`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `DBTEST`.`Customer` (
  `customer_id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(255) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `license_number` VARCHAR(255) NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  `address` VARCHAR(255) NOT NULL,
  `phone` VARCHAR(255) NOT NULL,
  `email` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`customer_id`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `DBTEST`.`Employee`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `DBTEST`.`Employee` (
  `employee_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `phone` VARCHAR(255) NOT NULL,
  `address` VARCHAR(255) NOT NULL,
  `salary` INT UNSIGNED NOT NULL,
  `dependents` INT UNSIGNED NOT NULL,
  `department` VARCHAR(255) NOT NULL,
  `role` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`employee_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `DBTEST`.`ExternalRepairShop`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `DBTEST`.`ExternalRepairShop` (
  `shop_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NULL DEFAULT NULL,
  `address` VARCHAR(255) NULL DEFAULT NULL,
  `phone` VARCHAR(255) NULL DEFAULT NULL,
  `manager_name` VARCHAR(255) NULL DEFAULT NULL,
  `manager_email` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`shop_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `DBTEST`.`ExternalRepairRecord`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `DBTEST`.`ExternalRepairRecord` (
  `external_repair_id` INT NOT NULL AUTO_INCREMENT,
  `car_id` INT NOT NULL,
  `shop_id` INT NOT NULL,
  `company_id` INT NOT NULL,
  `customer_id` INT NOT NULL,
  `content` TEXT NULL DEFAULT NULL,
  `repair_date` DATE NOT NULL,
  `cost` INT UNSIGNED NOT NULL,
  `due_date` DATE NOT NULL,
  `note` TEXT NULL DEFAULT NULL,
  PRIMARY KEY (`external_repair_id`),
  INDEX `car_id` (`car_id` ASC) VISIBLE,
  INDEX `shop_id` (`shop_id` ASC) VISIBLE,
  INDEX `company_id` (`company_id` ASC) VISIBLE,
  INDEX `customer_id` (`customer_id` ASC) VISIBLE,
  CONSTRAINT `externalrepairrecord_ibfk_1`
    FOREIGN KEY (`car_id`)
    REFERENCES `DBTEST`.`CampingCar` (`car_id`) ON DELETE CASCADE,
  CONSTRAINT `externalrepairrecord_ibfk_2`
    FOREIGN KEY (`shop_id`)
    REFERENCES `DBTEST`.`ExternalRepairShop` (`shop_id`) ON DELETE CASCADE,
  CONSTRAINT `externalrepairrecord_ibfk_3`
    FOREIGN KEY (`company_id`)
    REFERENCES `DBTEST`.`CampingCarCompany` (`company_id`) ON DELETE CASCADE,
  CONSTRAINT `externalrepairrecord_ibfk_4`
    FOREIGN KEY (`customer_id`)
    REFERENCES `DBTEST`.`Customer` (`customer_id`) ON DELETE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `DBTEST`.`Part`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `DBTEST`.`Part` (
  `part_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `unit_price` INT UNSIGNED NOT NULL,
  `stock_quantity` INT UNSIGNED NOT NULL,
  `stock_date` DATE NOT NULL,
  `supplier_name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`part_id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `DBTEST`.`InternalRepairRecord`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `DBTEST`.`InternalRepairRecord` (
  `internal_repair_id` INT NOT NULL AUTO_INCREMENT,
  `car_id` INT NOT NULL,
  `part_id` INT NULL DEFAULT NULL,
  `repair_date` DATE NOT NULL,
  `duration_minutes` INT UNSIGNED NOT NULL,
  `employee_id` INT NOT NULL,
  PRIMARY KEY (`internal_repair_id`),
  INDEX `car_id` (`car_id` ASC) VISIBLE,
  INDEX `part_id` (`part_id` ASC) VISIBLE,
  INDEX `employee_id` (`employee_id` ASC) VISIBLE,
  CONSTRAINT `internalrepairrecord_ibfk_1`
    FOREIGN KEY (`car_id`)
    REFERENCES `DBTEST`.`CampingCar` (`car_id`) ON DELETE CASCADE,
  CONSTRAINT `internalrepairrecord_ibfk_2`
    FOREIGN KEY (`part_id`)
    REFERENCES `DBTEST`.`Part` (`part_id`) ON DELETE CASCADE,
  CONSTRAINT `internalrepairrecord_ibfk_3`
    FOREIGN KEY (`employee_id`)
    REFERENCES `DBTEST`.`Employee` (`employee_id`) ON DELETE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `DBTEST`.`Rental`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `DBTEST`.`Rental` (
  `rental_id` INT NOT NULL AUTO_INCREMENT,
  `car_id` INT NOT NULL,
  `customer_id` INT NOT NULL,
  `company_id` INT NOT NULL,
  `start_date` DATE NOT NULL,
  `rental_period` INT UNSIGNED NOT NULL,
  `total_charge` INT UNSIGNED NOT NULL,
  `due_date` DATE NOT NULL,
  `extra_charges` TEXT NULL DEFAULT NULL,
  `extra_charge_amount` INT UNSIGNED NULL DEFAULT NULL,
  PRIMARY KEY (`rental_id`),
  UNIQUE INDEX `rental_id_UNIQUE` (`rental_id` ASC) VISIBLE,
  INDEX `car_id` (`car_id` ASC) VISIBLE,
  INDEX `customer_id` (`customer_id` ASC) VISIBLE,
  INDEX `company_id` (`company_id` ASC) VISIBLE,
  CONSTRAINT `rental_ibfk_1`
    FOREIGN KEY (`car_id`)
    REFERENCES `DBTEST`.`CampingCar` (`car_id`) ON DELETE CASCADE,
  CONSTRAINT `rental_ibfk_2`
    FOREIGN KEY (`customer_id`)
    REFERENCES `DBTEST`.`Customer` (`customer_id`) ON DELETE CASCADE,
  CONSTRAINT `rental_ibfk_3`
    FOREIGN KEY (`company_id`)
    REFERENCES `DBTEST`.`CampingCarCompany` (`company_id`) ON DELETE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- 일반 회원 계정
DROP USER IF EXISTS 'user1'@'localhost';

-- 2. user1 계정 생성
CREATE USER 'user1'@'localhost' IDENTIFIED BY 'user1';

-- 3. 권한을 제한하고 싶다면 예: SELECT, INSERT, UPDATE만
GRANT SELECT ON DBTEST.CampingCarCompany TO 'user1'@'localhost';
GRANT SELECT ON DBTEST.CampingCar TO 'user1'@'localhost';
GRANT SELECT ON DBTEST.Customer TO 'user1'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE ON DBTEST.Rental TO 'user1'@'localhost';
GRANT SELECT, INSERT ON DBTEST.ExternalRepairRecord TO 'user1'@'localhost';
GRANT SELECT ON DBTEST.ExternalRepairShop TO 'user1'@'localhost';

FLUSH PRIVILEGES;