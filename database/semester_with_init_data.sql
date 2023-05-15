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

 Date: 15/05/2023 18:35:46
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for semester
-- ----------------------------
DROP TABLE IF EXISTS `semester`;
CREATE TABLE `semester`  (
  `semesterID` int NOT NULL AUTO_INCREMENT,
  `firstHalfYear` int NULL DEFAULT NULL,
  `secondHalfYear` int NULL DEFAULT NULL,
  `season` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `startDate` datetime NULL DEFAULT NULL,
  `weekCount` int NULL DEFAULT NULL,
  PRIMARY KEY (`semesterID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of semester
-- ----------------------------
INSERT INTO `semester` VALUES (1, 2022, 2023, 'FALL', '2023-05-15 18:06:03', 18);
INSERT INTO `semester` VALUES (2, 2022, 2023, 'SPRING', '2023-05-15 18:06:03', 20);

SET FOREIGN_KEY_CHECKS = 1;
