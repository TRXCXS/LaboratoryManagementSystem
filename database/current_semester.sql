/*
 Navicat Premium Data Transfer

 Source Server         : mysqlconn1
 Source Server Type    : MySQL
 Source Server Version : 80031 (8.0.31)
 Source Host           : localhost:3306
 Source Schema         : demo

 Target Server Type    : MySQL
 Target Server Version : 80031 (8.0.31)
 File Encoding         : 65001

 Date: 12/05/2023 20:26:33
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for current_semester
-- ----------------------------
DROP TABLE IF EXISTS `current_semester`;
CREATE TABLE `current_semester`  (
  `queryID` int NOT NULL,
  `currentSemesterID` int NULL DEFAULT NULL,
  PRIMARY KEY (`queryID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
