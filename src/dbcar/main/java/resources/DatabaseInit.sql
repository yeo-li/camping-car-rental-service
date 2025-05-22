# 데이터베이스 드랍
DROP DATABASE IF EXISTS DBTEST;

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
  `image` BLOB NOT NULL,
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


# 데이터베이스 샘플 데이터 생성
INSERT INTO CampingCarCompany (name, address, phone, manager_name, manager_email) VALUES ('별빛렌탈 7404', '서울시 강남구 테헤란로 10', '010-9425-2169', '장준서', 'manager1@gmail.com');
INSERT INTO CampingCarCompany (name, address, phone, manager_name, manager_email) VALUES ('자연모터 1834', '서울시 강남구 테헤란로 20', '010-9979-9120', '이하은', 'manager2@gmail.com');
INSERT INTO CampingCarCompany (name, address, phone, manager_name, manager_email) VALUES ('하늘투어 9519', '서울시 강남구 테헤란로 30', '010-5193-6954', '김준서', 'manager3@naver.com');
INSERT INTO CampingCarCompany (name, address, phone, manager_name, manager_email) VALUES ('코리아캠핑 27', '서울시 강남구 테헤란로 40', '010-1039-1405', '이지우', 'manager4@naver.com');
INSERT INTO CampingCarCompany (name, address, phone, manager_name, manager_email) VALUES ('코리아캠핑 6970', '서울시 강남구 테헤란로 50', '010-2873-7313', '조서연', 'manager5@naver.com');
INSERT INTO CampingCarCompany (name, address, phone, manager_name, manager_email) VALUES ('코리아캠핑 5169', '서울시 강남구 테헤란로 60', '010-7914-1710', '최예린', 'manager6@gmail.com');
INSERT INTO CampingCarCompany (name, address, phone, manager_name, manager_email) VALUES ('자연모터 1691', '서울시 강남구 테헤란로 70', '010-2586-8402', '강수빈', 'manager7@daum.net');
INSERT INTO CampingCarCompany (name, address, phone, manager_name, manager_email) VALUES ('별빛렌탈 6422', '서울시 강남구 테헤란로 80', '010-7807-1403', '최지우', 'manager8@gmail.com');
INSERT INTO CampingCarCompany (name, address, phone, manager_name, manager_email) VALUES ('별빛렌탈 3837', '서울시 강남구 테헤란로 90', '010-9490-4160', '김민준', 'manager9@gmail.com');
INSERT INTO CampingCarCompany (name, address, phone, manager_name, manager_email) VALUES ('하늘투어 8786', '서울시 강남구 테헤란로 100', '010-5848-2289', '장지후', 'manager10@gmail.com');
INSERT INTO CampingCarCompany (name, address, phone, manager_name, manager_email) VALUES ('코리아캠핑 1222', '서울시 강남구 테헤란로 110', '010-3560-5487', '윤도윤', 'manager11@gmail.com');
INSERT INTO CampingCarCompany (name, address, phone, manager_name, manager_email) VALUES ('별빛렌탈 7055', '서울시 강남구 테헤란로 120', '010-5871-7353', '장민준', 'manager12@daum.net');
INSERT INTO CampingCar (name, plate_number, capacity, image, description, rental_price, company_id, registered_date) VALUES ('썬무버-MPO', '서울12가0001', 2, x'FFD8FFE0', '넓고 편안한 캠핑카입니다.', 168253, 1, '2024-10-10');
INSERT INTO CampingCar (name, plate_number, capacity, image, description, rental_price, company_id, registered_date) VALUES ('네이처하우스-WNI', '서울12가0002', 2, x'FFD8FFE0', '넓고 편안한 캠핑카입니다.', 92527, 2, '2024-09-24');
INSERT INTO CampingCar (name, plate_number, capacity, image, description, rental_price, company_id, registered_date) VALUES ('에코스트림-J0V', '서울12가0003', 6, x'FFD8FFE0', '넓고 편안한 캠핑카입니다.', 87995, 3, '2025-05-06');
INSERT INTO CampingCar (name, plate_number, capacity, image, description, rental_price, company_id, registered_date) VALUES ('다이나믹캠프-TKZ', '서울12가0004', 3, x'FFD8FFE0', '넓고 편안한 캠핑카입니다.', 118536, 4, '2025-04-27');
INSERT INTO CampingCar (name, plate_number, capacity, image, description, rental_price, company_id, registered_date) VALUES ('트래블라인-5YV', '서울12가0005', 3, x'FFD8FFE0', '넓고 편안한 캠핑카입니다.', 77507, 5, '2025-02-02');
INSERT INTO CampingCar (name, plate_number, capacity, image, description, rental_price, company_id, registered_date) VALUES ('트래블라인-IMY', '서울12가0006', 4, x'FFD8FFE0', '넓고 편안한 캠핑카입니다.', 97849, 6, '2024-06-23');
INSERT INTO CampingCar (name, plate_number, capacity, image, description, rental_price, company_id, registered_date) VALUES ('다이나믹캠프-UT9', '서울12가0007', 3, x'FFD8FFE0', '넓고 편안한 캠핑카입니다.', 159077, 7, '2024-10-08');
INSERT INTO CampingCar (name, plate_number, capacity, image, description, rental_price, company_id, registered_date) VALUES ('에코스트림-DLB', '서울12가0008', 3, x'FFD8FFE0', '넓고 편안한 캠핑카입니다.', 93109, 8, '2024-12-07');
INSERT INTO CampingCar (name, plate_number, capacity, image, description, rental_price, company_id, registered_date) VALUES ('오로라밴-QO7', '서울12가0009', 2, x'FFD8FFE0', '넓고 편안한 캠핑카입니다.', 165051, 9, '2025-02-08');
INSERT INTO CampingCar (name, plate_number, capacity, image, description, rental_price, company_id, registered_date) VALUES ('네이처하우스-LY3', '서울12가0010', 5, x'FFD8FFE0', '넓고 편안한 캠핑카입니다.', 195868, 10, '2024-12-06');
INSERT INTO CampingCar (name, plate_number, capacity, image, description, rental_price, company_id, registered_date) VALUES ('엘크라이저-P4Z', '서울12가0011', 4, x'FFD8FFE0', '넓고 편안한 캠핑카입니다.', 77410, 11, '2024-10-31');
INSERT INTO CampingCar (name, plate_number, capacity, image, description, rental_price, company_id, registered_date) VALUES ('다이나믹캠프-HL6', '서울12가0012', 5, x'FFD8FFE0', '넓고 편안한 캠핑카입니다.', 153036, 12, '2024-09-07');
INSERT INTO Customer (username, password, license_number, name, address, phone, email) VALUES ('user1', 'pw1', 'L955810', '임현우', '경기도 성남시 수정구 1번길', '010-7088-8057', '임현우@gmail.com');
INSERT INTO Customer (username, password, license_number, name, address, phone, email) VALUES ('user2', 'pw2', 'L684453', '임준서', '경기도 성남시 수정구 2번길', '010-6073-8590', '임준서@naver.com');
INSERT INTO Customer (username, password, license_number, name, address, phone, email) VALUES ('user3', 'pw3', 'L562939', '이서연', '경기도 성남시 수정구 3번길', '010-3547-8011', '이서연@gmail.com');
INSERT INTO Customer (username, password, license_number, name, address, phone, email) VALUES ('user4', 'pw4', 'L256284', '박준서', '경기도 성남시 수정구 4번길', '010-4963-7968', '박준서@gmail.com');
INSERT INTO Customer (username, password, license_number, name, address, phone, email) VALUES ('user5', 'pw5', 'L774459', '강지후', '경기도 성남시 수정구 5번길', '010-9217-9424', '강지후@gmail.com');
INSERT INTO Customer (username, password, license_number, name, address, phone, email) VALUES ('user6', 'pw6', 'L466685', '윤민준', '경기도 성남시 수정구 6번길', '010-4465-1569', '윤민준@daum.net');
INSERT INTO Customer (username, password, license_number, name, address, phone, email) VALUES ('user7', 'pw7', 'L890636', '정민준', '경기도 성남시 수정구 7번길', '010-2751-8069', '정민준@gmail.com');
INSERT INTO Customer (username, password, license_number, name, address, phone, email) VALUES ('user8', 'pw8', 'L160410', '임민준', '경기도 성남시 수정구 8번길', '010-3251-3086', '임민준@gmail.com');
INSERT INTO Customer (username, password, license_number, name, address, phone, email) VALUES ('user9', 'pw9', 'L123613', '최수빈', '경기도 성남시 수정구 9번길', '010-4798-5651', '최수빈@gmail.com');
INSERT INTO Customer (username, password, license_number, name, address, phone, email) VALUES ('user10', 'pw10', 'L800007', '정도윤', '경기도 성남시 수정구 10번길', '010-4471-7485', '정도윤@daum.net');
INSERT INTO Customer (username, password, license_number, name, address, phone, email) VALUES ('user11', 'pw11', 'L906867', '임민준', '경기도 성남시 수정구 11번길', '010-3955-5025', '임민준@gmail.com');
INSERT INTO Customer (username, password, license_number, name, address, phone, email) VALUES ('user12', 'pw12', 'L798502', '조서연', '경기도 성남시 수정구 12번길', '010-5986-3246', '조서연@gmail.com');
INSERT INTO Employee (name, phone, address, salary, dependents, department, role) VALUES ('조현우', '010-2853-7324', '인천시 부평구 산곡로 3', 3109000, 1, '총무팀', '정비');
INSERT INTO Employee (name, phone, address, salary, dependents, department, role) VALUES ('강서연', '010-2903-2408', '인천시 부평구 산곡로 6', 5213000, 0, '정비팀', '사무');
INSERT INTO Employee (name, phone, address, salary, dependents, department, role) VALUES ('강수빈', '010-4135-1968', '인천시 부평구 산곡로 9', 4848000, 1, '총무팀', '정비');
INSERT INTO Employee (name, phone, address, salary, dependents, department, role) VALUES ('이지후', '010-1390-1294', '인천시 부평구 산곡로 12', 5605000, 0, '정비팀', '사무');
INSERT INTO Employee (name, phone, address, salary, dependents, department, role) VALUES ('정서연', '010-3600-3263', '인천시 부평구 산곡로 15', 3094000, 2, '인사팀', '정비');
INSERT INTO Employee (name, phone, address, salary, dependents, department, role) VALUES ('정민준', '010-2205-5764', '인천시 부평구 산곡로 18', 3978000, 0, '정비팀', '관리');
INSERT INTO Employee (name, phone, address, salary, dependents, department, role) VALUES ('정도윤', '010-8013-8701', '인천시 부평구 산곡로 21', 4546000, 0, '인사팀', '관리');
INSERT INTO Employee (name, phone, address, salary, dependents, department, role) VALUES ('정지우', '010-8383-8551', '인천시 부평구 산곡로 24', 5262000, 1, '총무팀', '사무');
INSERT INTO Employee (name, phone, address, salary, dependents, department, role) VALUES ('정민준', '010-9311-5753', '인천시 부평구 산곡로 27', 4962000, 0, '영업팀', '사무');
INSERT INTO Employee (name, phone, address, salary, dependents, department, role) VALUES ('최예린', '010-4869-8261', '인천시 부평구 산곡로 30', 5892000, 0, '정비팀', '정비');
INSERT INTO Employee (name, phone, address, salary, dependents, department, role) VALUES ('임수빈', '010-2232-7639', '인천시 부평구 산곡로 33', 4265000, 0, '인사팀', '관리');
INSERT INTO Employee (name, phone, address, salary, dependents, department, role) VALUES ('장예린', '010-7809-9823', '인천시 부평구 산곡로 36', 5699000, 2, '영업팀', '관리');
INSERT INTO ExternalRepairShop (name, address, phone, manager_name, manager_email) VALUES ('정비소1', '부산시 해운대구 해운대로 7', '010-1234-5678', '김현우', 'shop1@daum.net');
INSERT INTO ExternalRepairShop (name, address, phone, manager_name, manager_email) VALUES ('정비소2', '부산시 해운대구 해운대로 14', '010-1684-9665', '김예린', 'shop2@gmail.com');
INSERT INTO ExternalRepairShop (name, address, phone, manager_name, manager_email) VALUES ('정비소3', '부산시 해운대구 해운대로 21', '010-8027-6985', '조도윤', 'shop3@gmail.com');
INSERT INTO ExternalRepairShop (name, address, phone, manager_name, manager_email) VALUES ('정비소4', '부산시 해운대구 해운대로 28', '010-3235-6786', '조하은', 'shop4@naver.com');
INSERT INTO ExternalRepairShop (name, address, phone, manager_name, manager_email) VALUES ('정비소5', '부산시 해운대구 해운대로 35', '010-4513-6164', '최민준', 'shop5@daum.net');
INSERT INTO ExternalRepairShop (name, address, phone, manager_name, manager_email) VALUES ('정비소6', '부산시 해운대구 해운대로 42', '010-8479-3939', '정하은', 'shop6@daum.net');
INSERT INTO ExternalRepairShop (name, address, phone, manager_name, manager_email) VALUES ('정비소7', '부산시 해운대구 해운대로 49', '010-5863-0129', '강예린', 'shop7@naver.com');
INSERT INTO ExternalRepairShop (name, address, phone, manager_name, manager_email) VALUES ('정비소8', '부산시 해운대구 해운대로 56', '010-1471-8679', '박도윤', 'shop8@gmail.com');
INSERT INTO ExternalRepairShop (name, address, phone, manager_name, manager_email) VALUES ('정비소9', '부산시 해운대구 해운대로 63', '010-2442-8529', '박서연', 'shop9@gmail.com');
INSERT INTO ExternalRepairShop (name, address, phone, manager_name, manager_email) VALUES ('정비소10', '부산시 해운대구 해운대로 70', '010-1472-7199', '박수빈', 'shop10@daum.net');
INSERT INTO ExternalRepairShop (name, address, phone, manager_name, manager_email) VALUES ('정비소11', '부산시 해운대구 해운대로 77', '010-2043-9733', '최지우', 'shop11@daum.net');
INSERT INTO ExternalRepairShop (name, address, phone, manager_name, manager_email) VALUES ('정비소12', '부산시 해운대구 해운대로 84', '010-9896-8983', '조현우', 'shop12@naver.com');
INSERT INTO Part (name, unit_price, stock_quantity, stock_date, supplier_name) VALUES ('에어컨 필터 587', 173109, 14, '2025-03-05', '부품상사1');
INSERT INTO Part (name, unit_price, stock_quantity, stock_date, supplier_name) VALUES ('에어컨 필터 353', 132406, 86, '2025-03-09', '부품상사2');
INSERT INTO Part (name, unit_price, stock_quantity, stock_date, supplier_name) VALUES ('오일 필터 530', 178944, 99, '2025-01-03', '부품상사3');
INSERT INTO Part (name, unit_price, stock_quantity, stock_date, supplier_name) VALUES ('타이어 558', 19910, 27, '2024-11-29', '부품상사4');
INSERT INTO Part (name, unit_price, stock_quantity, stock_date, supplier_name) VALUES ('배터리 503', 177208, 73, '2024-11-16', '부품상사5');
INSERT INTO Part (name, unit_price, stock_quantity, stock_date, supplier_name) VALUES ('배터리 972', 81792, 98, '2024-12-30', '부품상사6');
INSERT INTO Part (name, unit_price, stock_quantity, stock_date, supplier_name) VALUES ('헤드라이트 439', 134965, 96, '2025-06-01', '부품상사7');
INSERT INTO Part (name, unit_price, stock_quantity, stock_date, supplier_name) VALUES ('배터리 183', 32151, 84, '2024-12-10', '부품상사8');
INSERT INTO Part (name, unit_price, stock_quantity, stock_date, supplier_name) VALUES ('휠 허브 705', 86507, 34, '2025-02-18', '부품상사9');
INSERT INTO Part (name, unit_price, stock_quantity, stock_date, supplier_name) VALUES ('타이어 121', 185788, 20, '2024-11-18', '부품상사10');
INSERT INTO Part (name, unit_price, stock_quantity, stock_date, supplier_name) VALUES ('오일 필터 246', 194676, 64, '2025-05-02', '부품상사11');
INSERT INTO Part (name, unit_price, stock_quantity, stock_date, supplier_name) VALUES ('타이어 876', 15452, 58, '2025-01-11', '부품상사12');
INSERT INTO ExternalRepairRecord (car_id, shop_id, company_id, customer_id, content, repair_date, cost, due_date, note) VALUES (1, 1, 1, 1, '외부 수리 내용 1', '2025-02-09', 77042, '2025-05-18', NULL);
INSERT INTO ExternalRepairRecord (car_id, shop_id, company_id, customer_id, content, repair_date, cost, due_date, note) VALUES (2, 2, 2, 2, '외부 수리 내용 2', '2025-05-02', 251284, '2025-06-03', NULL);
INSERT INTO ExternalRepairRecord (car_id, shop_id, company_id, customer_id, content, repair_date, cost, due_date, note) VALUES (3, 3, 3, 3, '외부 수리 내용 3', '2025-04-04', 52207, '2025-06-05', NULL);
INSERT INTO ExternalRepairRecord (car_id, shop_id, company_id, customer_id, content, repair_date, cost, due_date, note) VALUES (4, 4, 4, 4, '외부 수리 내용 4', '2025-03-15', 325742, '2025-06-17', NULL);
INSERT INTO ExternalRepairRecord (car_id, shop_id, company_id, customer_id, content, repair_date, cost, due_date, note) VALUES (5, 5, 5, 5, '외부 수리 내용 5', '2025-01-14', 115077, '2025-07-01', NULL);
INSERT INTO ExternalRepairRecord (car_id, shop_id, company_id, customer_id, content, repair_date, cost, due_date, note) VALUES (6, 6, 6, 6, '외부 수리 내용 6', '2025-03-29', 127928, '2025-07-03', NULL);
INSERT INTO ExternalRepairRecord (car_id, shop_id, company_id, customer_id, content, repair_date, cost, due_date, note) VALUES (7, 7, 7, 7, '외부 수리 내용 7', '2025-03-24', 212661, '2025-06-11', NULL);
INSERT INTO ExternalRepairRecord (car_id, shop_id, company_id, customer_id, content, repair_date, cost, due_date, note) VALUES (8, 8, 8, 8, '외부 수리 내용 8', '2025-06-07', 22640, '2025-05-22', NULL);
INSERT INTO ExternalRepairRecord (car_id, shop_id, company_id, customer_id, content, repair_date, cost, due_date, note) VALUES (9, 9, 9, 9, '외부 수리 내용 9', '2025-04-24', 418849, '2025-05-26', NULL);
INSERT INTO ExternalRepairRecord (car_id, shop_id, company_id, customer_id, content, repair_date, cost, due_date, note) VALUES (10, 10, 10, 10, '외부 수리 내용 10', '2024-12-05', 133444, '2025-07-07', NULL);
INSERT INTO ExternalRepairRecord (car_id, shop_id, company_id, customer_id, content, repair_date, cost, due_date, note) VALUES (11, 11, 11, 11, '외부 수리 내용 11', '2025-01-30', 218328, '2025-07-02', NULL);
INSERT INTO ExternalRepairRecord (car_id, shop_id, company_id, customer_id, content, repair_date, cost, due_date, note) VALUES (12, 12, 12, 12, '외부 수리 내용 12', '2024-12-24', 338847, '2025-06-11', NULL);
INSERT INTO InternalRepairRecord (car_id, part_id, repair_date, duration_minutes, employee_id) VALUES (1, 1, '2025-02-05', 94, 1);
INSERT INTO InternalRepairRecord (car_id, part_id, repair_date, duration_minutes, employee_id) VALUES (2, 2, '2024-12-06', 74, 2);
INSERT INTO InternalRepairRecord (car_id, part_id, repair_date, duration_minutes, employee_id) VALUES (3, 3, '2025-05-06', 171, 3);
INSERT INTO InternalRepairRecord (car_id, part_id, repair_date, duration_minutes, employee_id) VALUES (4, 4, '2025-02-10', 167, 4);
INSERT INTO InternalRepairRecord (car_id, part_id, repair_date, duration_minutes, employee_id) VALUES (5, 5, '2024-12-30', 33, 5);
INSERT INTO InternalRepairRecord (car_id, part_id, repair_date, duration_minutes, employee_id) VALUES (6, 6, '2025-01-19', 109, 6);
INSERT INTO InternalRepairRecord (car_id, part_id, repair_date, duration_minutes, employee_id) VALUES (7, 7, '2025-06-11', 33, 7);
INSERT INTO InternalRepairRecord (car_id, part_id, repair_date, duration_minutes, employee_id) VALUES (8, 8, '2025-02-26', 144, 8);
INSERT INTO InternalRepairRecord (car_id, part_id, repair_date, duration_minutes, employee_id) VALUES (9, 9, '2024-12-31', 59, 9);
INSERT INTO InternalRepairRecord (car_id, part_id, repair_date, duration_minutes, employee_id) VALUES (10, 10, '2025-01-06', 91, 10);
INSERT INTO InternalRepairRecord (car_id, part_id, repair_date, duration_minutes, employee_id) VALUES (11, 11, '2024-12-08', 135, 11);
INSERT INTO InternalRepairRecord (car_id, part_id, repair_date, duration_minutes, employee_id) VALUES (12, 12, '2025-05-16', 144, 12);
INSERT INTO Rental (car_id, customer_id, company_id, start_date, rental_period, total_charge, due_date, extra_charges, extra_charge_amount) VALUES (1, 1, 1, '2025-04-12', 4, 320000, '2025-04-16', '세차비', 37901);
INSERT INTO Rental (car_id, customer_id, company_id, start_date, rental_period, total_charge, due_date, extra_charges, extra_charge_amount) VALUES (2, 2, 2, '2025-04-25', 12, 960000, '2025-05-07', '세차비', 32729);
INSERT INTO Rental (car_id, customer_id, company_id, start_date, rental_period, total_charge, due_date, extra_charges, extra_charge_amount) VALUES (3, 3, 3, '2025-04-05', 7, 560000, '2025-04-12', '세차비', 31401);
INSERT INTO Rental (car_id, customer_id, company_id, start_date, rental_period, total_charge, due_date, extra_charges, extra_charge_amount) VALUES (4, 4, 4, '2025-05-02', 10, 800000, '2025-05-12', '세차비', 19766);
INSERT INTO Rental (car_id, customer_id, company_id, start_date, rental_period, total_charge, due_date, extra_charges, extra_charge_amount) VALUES (5, 5, 5, '2025-04-29', 8, 640000, '2025-05-07', '세차비', 14794);
INSERT INTO Rental (car_id, customer_id, company_id, start_date, rental_period, total_charge, due_date, extra_charges, extra_charge_amount) VALUES (6, 6, 6, '2025-04-22', 1, 80000, '2025-04-23', '세차비', 31972);
INSERT INTO Rental (car_id, customer_id, company_id, start_date, rental_period, total_charge, due_date, extra_charges, extra_charge_amount) VALUES (7, 7, 7, '2025-03-17', 10, 800000, '2025-03-27', '세차비', 17158);
INSERT INTO Rental (car_id, customer_id, company_id, start_date, rental_period, total_charge, due_date, extra_charges, extra_charge_amount) VALUES (8, 8, 8, '2025-02-21', 13, 1040000, '2025-03-06', '세차비', 12298);
INSERT INTO Rental (car_id, customer_id, company_id, start_date, rental_period, total_charge, due_date, extra_charges, extra_charge_amount) VALUES (9, 9, 9, '2025-05-18', 7, 560000, '2025-05-25', '세차비', 15294);
INSERT INTO Rental (car_id, customer_id, company_id, start_date, rental_period, total_charge, due_date, extra_charges, extra_charge_amount) VALUES (10, 10, 10, '2025-06-08', 8, 640000, '2025-06-16', '세차비', 39072);
INSERT INTO Rental (car_id, customer_id, company_id, start_date, rental_period, total_charge, due_date, extra_charges, extra_charge_amount) VALUES (11, 11, 11, '2025-03-31', 9, 720000, '2025-04-09', '세차비', 17724);
INSERT INTO Rental (car_id, customer_id, company_id, start_date, rental_period, total_charge, due_date, extra_charges, extra_charge_amount) VALUES (12, 12, 12, '2025-05-16', 5, 400000, '2025-05-21', '세차비', 23166);
# 사용자 권한 설정

-- 관리자 계정
# CREATE USER 'root'@'%' IDENTIFIED BY '1234';
# ALTER USER 'root'@'%' IDENTIFIED BY '1234';

-- 일반 회원 계정
# DROP USER 'user1'@'%';

# CREATE USER 'user1'@'%' IDENTIFIED BY 'user1';
-- user1 계정은 반드시 캠핑카 대역 예약에 필요한 테이블에 대해서만 읽기/쓰기 원한을 부여한다. (root 계정은 모든 권한 소유)

-- root: 모든 권한 부여
# GRANT ALL PRIVILEGES ON DBTEST.* TO 'root'@'%';

-- 일반 사용자: 특정 테이블에 대해 CRUD만 허용
-- 일반 회원은 Rental, externalRepairRecord 테이블에 대해서만 삭제/변경을 수행한다.
# GRANT SELECT, INSERT, UPDATE, DELETE
# ON DBTEST TO 'user1'@'%';

# GRANT SELECT, INSERT, UPDATE, DELETE
# ON DBTEST TO 'user1'@'%';
-- 필요한 다른 테이블도 동일하게 반복

FLUSH PRIVILEGES;