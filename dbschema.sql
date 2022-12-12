CREATE TABLE `VEHICLES` (
                            `ID` int NOT NULL AUTO_INCREMENT,
                            `MAKE` varchar(100) DEFAULT NULL,
                            `MODEL` varchar(100) DEFAULT NULL,
                            `REG_DT_TIME` datetime DEFAULT NULL,
                            `case_num` varchar(50) DEFAULT NULL,
                            `CHASSIS_NUM` varchar(50) DEFAULT NULL,
                            `COLOR` varchar(100) DEFAULT NULL,
                            `parking_slot` varchar(10) DEFAULT NULL,
                            `IS_CASE_IN_COURT` bit(1) DEFAULT NULL,
                            `IS_CAR_AUCTION` bit(1) DEFAULT NULL,
                            `NUMBER_PLATE` varchar(25) DEFAULT NULL,
                            `OWNER_ID` int DEFAULT NULL,
                            `DEPT` varchar(100) DEFAULT NULL,
                            `RELEASE_ID` int DEFAULT NULL,
                            `IMAGE1` longblob,
                            `department` varchar(100) DEFAULT NULL,
                            `RELEASE_DT` datetime DEFAULT NULL,
                            `IMAGE2` longblob,
                            `IMAGE3` longblob,
                            `IMAGE4` longblob,
                            `IMAGE5` longblob,
                            `IS_WANTED` bit(1) DEFAULT NULL,
                            `EMIRATE` varchar(50) DEFAULT NULL,
                            `CATEGORY` varchar(50) DEFAULT NULL,
                            `CODE` varchar(50) DEFAULT NULL,
                            `TYPE` varchar(50) DEFAULT NULL,
                            `RELEASE_DOCUMENT` longblob,
                            `STATUS` varchar(20) DEFAULT NULL,
                            `IMAGE_TYPE_1` varchar(50) DEFAULT NULL,
                            `IMAGE_TYPE_2` varchar(50) DEFAULT NULL,
                            `IMAGE_TYPE_3` varchar(50) DEFAULT NULL,
                            `IMAGE_TYPE_4` varchar(50) DEFAULT NULL,
                            `IMAGE_TYPE_5` varchar(50) DEFAULT NULL,
                            `RELEASE_DOCUMENT_TYPE` varchar(50) DEFAULT NULL,
                            `REMARKS` text,
                            PRIMARY KEY (`ID`),
                            KEY `owner_fk` (`OWNER_ID`),
                            KEY `RELEASE_ID_FK` (`RELEASE_ID`),
                            CONSTRAINT `owner_fk` FOREIGN KEY (`OWNER_ID`) REFERENCES `OWNER` (`ID`),
                            CONSTRAINT `RELEASE_ID_FK` FOREIGN KEY (`RELEASE_ID`) REFERENCES `RELEASE_IDENTITY` (`id`)
)

CREATE TABLE `OWNER` (
                         `ID` int NOT NULL AUTO_INCREMENT,
                         `ID_TYPE` varchar(30) DEFAULT NULL,
                         `FIRST_NAME` varchar(50) DEFAULT NULL,
                         `LAST_NAME` varchar(60) DEFAULT NULL,
                         `EMAIL_ADD` varchar(50) DEFAULT NULL,
                         `ID_NUM` varchar(30) DEFAULT NULL,
                         `CONTACT_NUM` varchar(15) DEFAULT NULL,
                         `nationality` varchar(50) DEFAULT NULL,
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
)

CREATE TABLE `RELEASE_IDENTITY` (
                                    `id` int NOT NULL AUTO_INCREMENT,
                                    `id_type` varchar(30) DEFAULT NULL,
                                    `first_name` varchar(50) DEFAULT NULL,
                                    `last_name` varchar(60) DEFAULT NULL,
                                    `email_add` varchar(50) DEFAULT NULL,
                                    `id_num` varchar(30) DEFAULT NULL,
                                    `contact_num` varchar(15) DEFAULT NULL,
                                    `nationality` varchar(50) DEFAULT NULL,
                                    `RELEASE_DT_TM` datetime DEFAULT NULL,
                                    PRIMARY KEY (`id`)
)

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