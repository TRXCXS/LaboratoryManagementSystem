/*
 Navicat Premium Data Transfer

 Source Server         : MySQL
 Source Server Type    : MySQL
 Source Server Version : 80033 (8.0.33)
 Source Host           : localhost:3306
 Source Schema         : demo

 Target Server Type    : MySQL
 Target Server Version : 80033 (8.0.33)
 File Encoding         : 65001

 Date: 22/05/2023 19:36:28
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for laboratory
-- ----------------------------
DROP TABLE IF EXISTS `laboratory`;
CREATE TABLE `laboratory`  (
  `labID` int NOT NULL AUTO_INCREMENT,
  `labNumber` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `labType` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `deviceCount` int NULL DEFAULT NULL,
  PRIMARY KEY (`labID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of laboratory
-- ----------------------------
INSERT INTO `laboratory` VALUES (1, '732', 'SOFTWARE', '软件实验室', 30);
INSERT INTO `laboratory` VALUES (2, '734', 'SOFTWARE', '软件实验室', 30);
INSERT INTO `laboratory` VALUES (3, '736', 'SOFTWARE', '软件实验室', 60);
INSERT INTO `laboratory` VALUES (4, '738', 'SOFTWARE', '软件实验室', 60);
INSERT INTO `laboratory` VALUES (5, '735', 'SOFTWARE', '软件实验室', 120);
INSERT INTO `laboratory` VALUES (6, '532', 'SOFTWARE', '软件实验室', 30);
INSERT INTO `laboratory` VALUES (7, '536', 'SOFTWARE', '软件实验室', 60);
INSERT INTO `laboratory` VALUES (8, '537', 'SOFTWARE', '软件实验室', 60);
INSERT INTO `laboratory` VALUES (9, '535', 'SOFTWARE', '软件实验室', 120);
INSERT INTO `laboratory` VALUES (10, '703', 'HARDWARE', '计算机硬件实验室', 30);
INSERT INTO `laboratory` VALUES (11, '704', 'HARDWARE', '计算机硬件实验室', 60);
INSERT INTO `laboratory` VALUES (12, '705', 'HARDWARE', '计算机硬件实验室', 120);
INSERT INTO `laboratory` VALUES (13, '706', 'SYSTEM', '计算机系统实验室', 30);
INSERT INTO `laboratory` VALUES (14, '707', 'SYSTEM', '计算机系统实验室', 60);
INSERT INTO `laboratory` VALUES (15, '708', 'SYSTEM', '计算机系统实验室', 120);
INSERT INTO `laboratory` VALUES (16, '801', 'SYSTEM', '计算机系统实验室', 60);
INSERT INTO `laboratory` VALUES (17, '803', 'SYSTEM', '计算机系统实验室', 120);
INSERT INTO `laboratory` VALUES (18, '806', 'IOT', '物联网实验室', 60);
INSERT INTO `laboratory` VALUES (19, '808', 'IOT', '物联网实验室', 120);
INSERT INTO `laboratory` VALUES (20, '832', 'NETWORK', '计算机网络实验室', 60);
INSERT INTO `laboratory` VALUES (21, '834', 'NETWORK', '计算机网络实验室', 120);

SET FOREIGN_KEY_CHECKS = 1;
