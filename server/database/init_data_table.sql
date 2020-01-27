/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 80017
Source Host           : 127.0.0.1:3306
Source Database       : matrixauth

Target Server Type    : MYSQL
Target Server Version : 80017
File Encoding         : 65001

Date: 2020-01-19 13:47:48
*/

SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for application
-- ----------------------------
DROP TABLE IF EXISTS `application`;
CREATE TABLE `application`
(
  `name`           varchar(255) NOT NULL,
  `token`          varchar(255)                                                  DEFAULT NULL,
  `dataSourceName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `cacheName`      varchar(255)                                                  DEFAULT NULL,
  PRIMARY KEY (`name`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for cacheclient
-- ----------------------------
DROP TABLE IF EXISTS `cache`;
CREATE TABLE `cache`
(
  `name`     varchar(255) NOT NULL,
  `url`      varchar(255) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`name`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for datasource
-- ----------------------------
DROP TABLE IF EXISTS `datasource`;
CREATE TABLE `datasource`
(
  `name`     varchar(255) NOT NULL,
  `url`      text         NOT NULL,
  `driver`   varchar(255)                                                  DEFAULT NULL,
  `userName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `password` varchar(255)                                                  DEFAULT NULL,
  PRIMARY KEY (`name`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission`
(
  `id`          int(11) NOT NULL AUTO_INCREMENT,
  `appName`     varchar(255) DEFAULT NULL,
  `name`        varchar(255) DEFAULT NULL,
  `code`        varchar(255) DEFAULT NULL,
  `description` text,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`
(
  `id`          int(11)      NOT NULL AUTO_INCREMENT,
  `appName`     varchar(255) NOT NULL,
  `name`        varchar(255) NOT NULL,
  `parentId`    int(11) DEFAULT NULL,
  `description` text,
  `type`        varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for role_x_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_x_permission`;
CREATE TABLE `role_x_permission`
(
  `roleId`       int(11) NOT NULL,
  `permissionId` int(11) NOT NULL,
  PRIMARY KEY (`roleId`, `permissionId`),
  KEY `roleId` (`roleId`) USING BTREE,
  KEY `permissionId` (`permissionId`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
(
  `appName` varchar(255)                                                  NOT NULL,
  `key`     varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `name`    varchar(255) DEFAULT NULL,
  PRIMARY KEY (`appName`, `key`),
  UNIQUE KEY `keyAndAppName` (`appName`, `key`) USING BTREE,
  KEY `appName` (`appName`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for user_x_permission
-- ----------------------------
DROP TABLE IF EXISTS `user_x_permission`;
CREATE TABLE `user_x_permission`
(
  `fullUserKey` varchar(255) NOT NULL,
  `permissions` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  PRIMARY KEY (`fullUserKey`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for user_x_role
-- ----------------------------
DROP TABLE IF EXISTS `user_x_role`;
CREATE TABLE `user_x_role`
(
  `fullUserKey` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `roleId`      int(11)                                                       NOT NULL,
  PRIMARY KEY (`fullUserKey`, `roleId`),
  KEY `userId` (`fullUserKey`) USING BTREE,
  KEY `roleId` (`roleId`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
