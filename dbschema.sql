CREATE TABLE `VEHICLES` (
                            `ID` int NOT NULL AUTO_INCREMENT,
                            `MAKE` varchar(100) DEFAULT NULL,
                            `MODEL` varchar(100) DEFAULT NULL,
                            `REG_DT_TIME` datetime DEFAULT NULL,
                            `case_num` varchar(50) DEFAULT NULL,
                            `CHASSIS_NUM` varchar(50) DEFAULT NULL,
                            `COLOR` varchar(100) DEFAULT NULL,
                            `PARKING_SLOT` varchar(10) DEFAULT NULL,
                            `IS_CASE_IN_COURT` bit(1) DEFAULT NULL,
                            `IS_CAR_AUCTION` bit(1) DEFAULT NULL,
                            `NUMBER_PLATE` varchar(25) DEFAULT NULL,
                            `OWNER_ID` int DEFAULT NULL,
                            `DEPT` varchar(100) DEFAULT NULL,
                            `RELEASE_ID` int DEFAULT NULL,
                            `DEPARTMENT` varchar(100) DEFAULT NULL,
                            `RELEASE_DT` datetime DEFAULT NULL,
                            `IS_WANTED` bit(1) DEFAULT NULL,
                            `EMIRATE` varchar(50) DEFAULT NULL,
                            `CATEGORY` varchar(50) DEFAULT NULL,
                            `CODE` varchar(50) DEFAULT NULL,
                            `TYPE` varchar(50) DEFAULT NULL,
                            `STATUS` varchar(20) DEFAULT NULL,
                            `REMARKS` text,
                            PRIMARY KEY (`ID`),
                            KEY `owner_fk` (`OWNER_ID`),
                            KEY `RELEASE_ID_FK` (`RELEASE_ID`),
                            CONSTRAINT `owner_fk` FOREIGN KEY (`OWNER_ID`) REFERENCES `OWNER` (`ID`),
                            CONSTRAINT `RELEASE_ID_FK` FOREIGN KEY (`RELEASE_ID`) REFERENCES `RELEASE_IDENTITY` (`id`)
);

CREATE TABLE `OWNER` (
                         `ID` int NOT NULL AUTO_INCREMENT,
                         `ID_TYPE` varchar(30) DEFAULT NULL,
                         `FIRST_NAME` varchar(50) DEFAULT NULL,
                         `LAST_NAME` varchar(60) DEFAULT NULL,
                         `EMAIL_ADD` varchar(50) DEFAULT NULL,
                         `ID_NUM` varchar(30) DEFAULT NULL,
                         `CONTACT_NUM` varchar(15) DEFAULT NULL,
                         `NATIONALITY` varchar(50) DEFAULT NULL,
                         PRIMARY KEY (`ID`)
);

CREATE TABLE `PARKING_ZONE` (
                                `ZONE_LABEL` varchar(2) DEFAULT NULL,
                                `SLOT_NUM` int DEFAULT NULL,
                                `OC_STATUS` varchar(20) DEFAULT NULL,
                                `VEHICLE_ID` int DEFAULT NULL,
                                `ID` int NOT NULL AUTO_INCREMENT,
                                PRIMARY KEY (`ID`),
                                KEY `vehicle_id_fk` (`VEHICLE_ID`),
                                CONSTRAINT `vehicle_id_fk` FOREIGN KEY (`VEHICLE_ID`) REFERENCES `VEHICLES` (`ID`)
);

CREATE TABLE `RELEASE_IDENTITY` (
                                    `ID` int NOT NULL AUTO_INCREMENT,
                                    `ID_TYPE` varchar(30) DEFAULT NULL,
                                    `FIRST_NAME` varchar(50) DEFAULT NULL,
                                    `LAST_NAME` varchar(60) DEFAULT NULL,
                                    `EMAIL_ADD` varchar(50) DEFAULT NULL,
                                    `ID_NUM` varchar(30) DEFAULT NULL,
                                    `CONTACT_NUM` varchar(15) DEFAULT NULL,
                                    `NATIONALITY` varchar(50) DEFAULT NULL,
                                    `RELEASE_DT_TM` datetime DEFAULT NULL,
                                    `RELEASE_DOC_NUM` varchar(20) DEFAULT NULL,
                                    PRIMARY KEY (`ID`)
);

CREATE TABLE `IMAGES` (
    `ID` int NOT NULL AUTO_INCREMENT,
    `FILENAME` varchar(30) DEFAULT NULL,
    `FILETYPE` varchar(30) DEFAULT NULL,
    `BYTES` LONGBLOB,
    `VEHICLE_ID` int DEFAULT NULL,
    KEY `VEHICLE_ID_FK` (`VEHICLE_ID`),
    PRIMARY KEY(`ID`),
    CONSTRAINT `VEHICLE_ID_FKI` FOREIGN KEY(`VEHICLE_ID`) REFERENCES `VEHICLES`(`ID`)
);

CREATE TABLE `RELEASEDOCUMENTS` (
    `ID` int NOT NULL AUTO_INCREMENT,
    `FILENAME` varchar(30) DEFAULT NULL,
    `FILETYPE` varchar(30) DEFAULT NULL,
    `BYTES` LONGBLOB,
    `VEHICLE_ID` int DEFAULT NULL,
    KEY `VEHICLE_ID_FK` (`VEHICLE_ID`),
    PRIMARY KEY(`ID`),
    CONSTRAINT `VEHICLE_ID_FKR` FOREIGN KEY(`VEHICLE_ID`) REFERENCES `VEHICLES`(`ID`)
);

CREATE TABLE `USER_ROLES` (
                              `user_id` bigint NOT NULL,
                              `role_id` int NOT NULL,
                              PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `ROLES` (
                         `id` int NOT NULL AUTO_INCREMENT,
                         `name` varchar(20) DEFAULT NULL,
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `USERS` (
                         `id` bigint NOT NULL AUTO_INCREMENT,
                         `email` varchar(50) DEFAULT NULL,
                         `password` varchar(120) DEFAULT NULL,
                         `username` varchar(20) DEFAULT NULL,
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

+----+--------------------+
| id | name               |
+----+--------------------+
|  4 | ROLE_USER          |
|  5 | ROLE_ADMIN         |
|  6 | ROLE_EXIT_OPERATOR |
|  7 | ROLE_SUPERUSER     |
+----+--------------------+
