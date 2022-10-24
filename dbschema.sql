CREATE TABLE `vehicles` (
                            `ID` int NOT NULL AUTO_INCREMENT,
                            `MAKE` varchar(100) DEFAULT NULL,
                            `MODEL` varchar(100) DEFAULT NULL,
                            `REG_DT_TIME` datetime DEFAULT NULL,
                            `CASE_NUM` int DEFAULT NULL,
                            `MULKIA_NUM` int DEFAULT NULL,
                            `COLOR` varchar(100) DEFAULT NULL,
                            `PARKING_SLOT` varchar(5) DEFAULT NULL,
                            `IS_CASE_IN_COURT` bit(1) DEFAULT NULL,
                            `IS_CAR_AUCTION` bit(1) DEFAULT NULL,
                            `NUMBER_PLATE` varchar(25) DEFAULT NULL,
                            `OWNER_ID` int DEFAULT NULL,
                            `DEPT` varchar(100) DEFAULT NULL,
                            PRIMARY KEY (`ID`),
                            KEY `owner_fk` (`OWNER_ID`),
                            CONSTRAINT `owner_fk` FOREIGN KEY (`OWNER_ID`) REFERENCES `owner` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `owner` (
                         `ID` int NOT NULL AUTO_INCREMENT,
                         `ID_TYPE` varchar(30) DEFAULT NULL,
                         `FIRST_NAME` varchar(50) DEFAULT NULL,
                         `LAST_NAME` varchar(60) DEFAULT NULL,
                         `EMAIL_ADD` varchar(50) DEFAULT NULL,
                         `ID_NUM` varchar(30) DEFAULT NULL,
                         `CONTACT_NUM` varchar(15) DEFAULT NULL,
                         PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;