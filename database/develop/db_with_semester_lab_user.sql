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

 Date: 23/05/2023 20:04:01
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for administrator
-- ----------------------------
DROP TABLE IF EXISTS `administrator`;
CREATE TABLE `administrator`  (
  `adminID` int NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`adminID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of administrator
-- ----------------------------
INSERT INTO `administrator` VALUES (1, '林芳媛');
INSERT INTO `administrator` VALUES (2, '郭诗云');
INSERT INTO `administrator` VALUES (3, '史睿识');

-- ----------------------------
-- Table structure for current_semester
-- ----------------------------
DROP TABLE IF EXISTS `current_semester`;
CREATE TABLE `current_semester`  (
  `queryID` int NOT NULL,
  `currentSemesterID` int NULL DEFAULT NULL,
  PRIMARY KEY (`queryID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of current_semester
-- ----------------------------
INSERT INTO `current_semester` VALUES (1, 1);

-- ----------------------------
-- Table structure for instructor
-- ----------------------------
DROP TABLE IF EXISTS `instructor`;
CREATE TABLE `instructor`  (
  `instructorID` int NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`instructorID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of instructor
-- ----------------------------
INSERT INTO `instructor` VALUES (4, '傅莱', '教授');
INSERT INTO `instructor` VALUES (5, '束密', '教授');
INSERT INTO `instructor` VALUES (6, '谭德海', '教授');
INSERT INTO `instructor` VALUES (7, '万建中', '教授');
INSERT INTO `instructor` VALUES (8, '康小雯', '教授');
INSERT INTO `instructor` VALUES (9, '罗小楠', '教授');
INSERT INTO `instructor` VALUES (10, '黎晨希', '教授');
INSERT INTO `instructor` VALUES (11, '常强', '教授');
INSERT INTO `instructor` VALUES (12, '吕弩', '教授');
INSERT INTO `instructor` VALUES (13, '韩士', '教授');
INSERT INTO `instructor` VALUES (14, '晏浩', '教授');
INSERT INTO `instructor` VALUES (15, '车刚', '教授');
INSERT INTO `instructor` VALUES (16, '宿仑', '教授');
INSERT INTO `instructor` VALUES (17, '邓晸', '教授');
INSERT INTO `instructor` VALUES (18, '郑平', '教授');
INSERT INTO `instructor` VALUES (19, '弘灵', '教授');
INSERT INTO `instructor` VALUES (20, '韩夏波', '教授');
INSERT INTO `instructor` VALUES (21, '丁俊', '教授');
INSERT INTO `instructor` VALUES (22, '范乐怡', '教授');
INSERT INTO `instructor` VALUES (23, '王川', '教授');
INSERT INTO `instructor` VALUES (54, '徐鸣玉', '教授');

-- ----------------------------
-- Table structure for instructor_request
-- ----------------------------
DROP TABLE IF EXISTS `instructor_request`;
CREATE TABLE `instructor_request`  (
  `instructorRequestID` int NOT NULL AUTO_INCREMENT,
  `requestTime` datetime NULL DEFAULT NULL,
  `labType` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `startWeek` int NULL DEFAULT NULL,
  `endWeek` int NULL DEFAULT NULL,
  `weekday` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `slot` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `studentClass` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `studentCount` int NULL DEFAULT NULL,
  `course` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `adminProcessTime` datetime NULL DEFAULT NULL,
  `adminMessage` varchar(1022) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `instructorID` int NOT NULL,
  `semesterID` int NOT NULL,
  PRIMARY KEY (`instructorRequestID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of instructor_request
-- ----------------------------

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

-- ----------------------------
-- Table structure for long_arrangement
-- ----------------------------
DROP TABLE IF EXISTS `long_arrangement`;
CREATE TABLE `long_arrangement`  (
  `longArrangementID` int NOT NULL AUTO_INCREMENT,
  `startWeek` int NULL DEFAULT NULL,
  `endWeek` int NULL DEFAULT NULL,
  `weekday` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `slot` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `studentClass` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `studentCount` int NULL DEFAULT NULL,
  `instructorRequestID` int NOT NULL,
  `labID` int NOT NULL,
  PRIMARY KEY (`longArrangementID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of long_arrangement
-- ----------------------------

-- ----------------------------
-- Table structure for repair_request
-- ----------------------------
DROP TABLE IF EXISTS `repair_request`;
CREATE TABLE `repair_request`  (
  `repairRequestID` int NOT NULL AUTO_INCREMENT,
  `requestTime` datetime NULL DEFAULT NULL,
  `requestDescription` varchar(1022) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `repairStartTime` datetime NULL DEFAULT NULL,
  `repairEndTime` datetime NULL DEFAULT NULL,
  `technicianMessage` varchar(1022) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `instructorID` int NOT NULL,
  `labID` int NOT NULL,
  PRIMARY KEY (`repairRequestID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of repair_request
-- ----------------------------

-- ----------------------------
-- Table structure for responsible_for
-- ----------------------------
DROP TABLE IF EXISTS `responsible_for`;
CREATE TABLE `responsible_for`  (
  `responsibleForID` int NOT NULL AUTO_INCREMENT,
  `technicianID` int NOT NULL,
  `labID` int NOT NULL,
  PRIMARY KEY (`responsibleForID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of responsible_for
-- ----------------------------

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
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of semester
-- ----------------------------
INSERT INTO `semester` VALUES (1, 2022, 2023, 'FALL', '2023-05-15 18:06:03', 18);
INSERT INTO `semester` VALUES (2, 2022, 2023, 'SPRING', '2023-05-15 18:06:03', 20);

-- ----------------------------
-- Table structure for short_arrangement
-- ----------------------------
DROP TABLE IF EXISTS `short_arrangement`;
CREATE TABLE `short_arrangement`  (
  `shortArrangementID` int NOT NULL AUTO_INCREMENT,
  `week` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `weekday` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `slot` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `studentRequestID` int NOT NULL,
  `labID` int NOT NULL,
  PRIMARY KEY (`shortArrangementID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of short_arrangement
-- ----------------------------

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student`  (
  `studentID` int NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `major` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `clazz` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`studentID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES (34, '欧阳宇荫', '计算机科学与技术', '20计算机3');
INSERT INTO `student` VALUES (35, '戴玉萍', '计算机科学与技术', '20计算机3');
INSERT INTO `student` VALUES (36, '慕容星波', '计算机科学与技术', '20计算机3');
INSERT INTO `student` VALUES (37, '万建中', '计算机科学与技术', '20计算机3');
INSERT INTO `student` VALUES (38, '康小雯', '计算机科学与技术', '20计算机3');
INSERT INTO `student` VALUES (39, '公良雪松', '计算机科学与技术', '20计算机3');
INSERT INTO `student` VALUES (40, '黎晨希', '计算机科学与技术', '20计算机3');
INSERT INTO `student` VALUES (41, '冯棋', '计算机科学与技术', '20计算机3');
INSERT INTO `student` VALUES (42, '吕弩', '计算机科学与技术', '20计算机3');
INSERT INTO `student` VALUES (43, '韩士', '计算机科学与技术', '20计算机3');
INSERT INTO `student` VALUES (44, '晏浩', '计算机科学与技术', '20计算机3');
INSERT INTO `student` VALUES (45, '车刚', '计算机科学与技术', '20计算机3');
INSERT INTO `student` VALUES (46, '富才', '计算机科学与技术', '20计算机3');
INSERT INTO `student` VALUES (47, '邓晸', '计算机科学与技术', '20计算机3');
INSERT INTO `student` VALUES (48, '段紫杉', '计算机科学与技术', '20计算机3');
INSERT INTO `student` VALUES (49, '弘灵', '计算机科学与技术', '20计算机3');
INSERT INTO `student` VALUES (50, '韩夏波', '计算机科学与技术', '20计算机3');
INSERT INTO `student` VALUES (51, '丁俊', '计算机科学与技术', '20计算机3');
INSERT INTO `student` VALUES (52, '范乐怡', '计算机科学与技术', '20计算机3');
INSERT INTO `student` VALUES (53, '王川', '计算机科学与技术', '20计算机3');

-- ----------------------------
-- Table structure for student_request
-- ----------------------------
DROP TABLE IF EXISTS `student_request`;
CREATE TABLE `student_request`  (
  `studentRequestID` int NOT NULL AUTO_INCREMENT,
  `week` int NULL DEFAULT NULL,
  `weekday` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `slot` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `reason` varchar(1022) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `requestTime` datetime NULL DEFAULT NULL,
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `adminProcessTime` datetime NULL DEFAULT NULL,
  `adminMessage` varchar(1022) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `useCompleteTime` datetime NULL DEFAULT NULL,
  `labID` int NOT NULL,
  `semesterID` int NOT NULL,
  `studentID` int NOT NULL,
  PRIMARY KEY (`studentRequestID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of student_request
-- ----------------------------

-- ----------------------------
-- Table structure for technician
-- ----------------------------
DROP TABLE IF EXISTS `technician`;
CREATE TABLE `technician`  (
  `technicianID` int NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`technicianID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of technician
-- ----------------------------
INSERT INTO `technician` VALUES (24, '全晴波', '设备和安全负责人');
INSERT INTO `technician` VALUES (25, '艾蓓莉', '设备和安全负责人');
INSERT INTO `technician` VALUES (26, '刚承文', '设备和安全负责人');
INSERT INTO `technician` VALUES (27, '沙乐游', '设备和安全负责人');
INSERT INTO `technician` VALUES (28, '卓康', '设备和安全负责人');
INSERT INTO `technician` VALUES (29, '日正奇', '设备和安全负责人');
INSERT INTO `technician` VALUES (30, '宫汉', '设备和安全负责人');
INSERT INTO `technician` VALUES (31, '谢涵', '设备和安全负责人');
INSERT INTO `technician` VALUES (32, '罗侃', '设备和安全负责人');
INSERT INTO `technician` VALUES (33, '蔡鸿文', '设备和安全负责人');
INSERT INTO `technician` VALUES (54, '岑千青', '设备和安全负责人');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `userID` int NOT NULL AUTO_INCREMENT,
  `loginID` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`userID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 55 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '001', '$2a$10$JSbF7aXi0DscYfTdM.Y3HOWyDXz3VurF58ktIbUrdSEBq5eq1BfIa');
INSERT INTO `user` VALUES (2, '002', '$2a$10$kmfBppPyu5G3fWuLg5x.yOqlqsHuhWFMycaY1B6zjKiOAF90FaVqu');
INSERT INTO `user` VALUES (3, '003', '$2a$10$5gR9T2DaK/1DHPLT4Ip7mOfXef8WFsr2WGmOzpPdeGmkn0r6PmteG');
INSERT INTO `user` VALUES (4, '020', '$2a$10$6ogn/KUGVHkc9Ege/JmGSusQg6Aur3rnIPr5yMzQbjNFnk8ztny.y');
INSERT INTO `user` VALUES (5, '021', '$2a$10$B40lbn5W90cY/aKUKGxUTOeQZBJ.QLLmuv3tvo1OOiTNYj7GTwdxG');
INSERT INTO `user` VALUES (6, '022', '$2a$10$wrPbvsgZ90ggISGlML1XxuiMIiYez2f7eQsYOeb.5zMpegbQ1X.qG');
INSERT INTO `user` VALUES (7, '023', '$2a$10$7GJ72AxNRJDLIaX7P.10WOJ4cpS5zhc8dEhzm3jdF2OtYO6qSPjZG');
INSERT INTO `user` VALUES (8, '024', '$2a$10$RtLhD5/gyHMJU.AzcD/u..rzdddPlGlDRu5IdPUplQ6SG5xBz75e2');
INSERT INTO `user` VALUES (9, '025', '$2a$10$rLdf6ffe7TL2FQ.LRT4GN.aOhpP.qpGFF0ib9.Pu.YDb3d4HPVNIm');
INSERT INTO `user` VALUES (10, '026', '$2a$10$62impi6pwwLlVlvs2b9Nx.CKAqqWlsvhbpBK9UtXK9izUd8jOqQra');
INSERT INTO `user` VALUES (11, '027', '$2a$10$g.UlsCVXFOBvWdAY2hoEl.gQ5n.lNZ3PHJ2Apa6t5WMHyDfCKLxnO');
INSERT INTO `user` VALUES (12, '028', '$2a$10$B4nIrkOqAkpyl0eVGymNi.Ph4.ZIR9uRdQUL9y0tJS6ZLBvvgf8AC');
INSERT INTO `user` VALUES (13, '029', '$2a$10$krCg9kPPWsnmMXAWxcu.neShIwdyqbtz3QaxHDT91ZCaD8WCt8yRe');
INSERT INTO `user` VALUES (14, '030', '$2a$10$p/AAw7fxczPPJi53iAKJ6Ou0zua2euQGKoFoA3oy4N8u9LWE8twii');
INSERT INTO `user` VALUES (15, '031', '$2a$10$2fDiUCsvxu2MPRQe8wlOHeZaw2cUmOEXSpEjgCXZNc9lGmXQBbEFW');
INSERT INTO `user` VALUES (16, '032', '$2a$10$73JYJoVHIGXhZmIMwV/Ih.RLQ2oh5uLJysNnC1y2o7awCrXnTbqE.');
INSERT INTO `user` VALUES (17, '033', '$2a$10$gDwc6R4L1ZYiTp0ddNQyCu./jKvWm5.jzyyj/4O6jo1M5OJTJgqYe');
INSERT INTO `user` VALUES (18, '034', '$2a$10$iW4BKSEwEnyK9VyTC8WdqO1.EFmytDphiHcMWK1ZImDSTePb4pR/e');
INSERT INTO `user` VALUES (19, '035', '$2a$10$PEBBu07ohLG8vMW4cckXj.zyDIJClU9XaFC9WoMlSd/Yr8mBSSv7C');
INSERT INTO `user` VALUES (20, '036', '$2a$10$DPI7No0oiYhNKd.PgnL3Ku4nTws3R19VU8yXQ0ytGKq2GaNm3xSJC');
INSERT INTO `user` VALUES (21, '037', '$2a$10$.Ul7g6No1BUgRJ60BQyyJ.3h88FioS3XLjCT8T5UhKyJcxFdD0Qfq');
INSERT INTO `user` VALUES (22, '038', '$2a$10$Clkm76T2ru4nsSBgctELLuvTpfb8nbdoYBr94SaswnSnhI9xMyj9C');
INSERT INTO `user` VALUES (23, '039', '$2a$10$Rk74iIqnquNTDq9ugzrP5ekAYdT9uIamZCCnV8wZa/3R0zLKu6sCG');
INSERT INTO `user` VALUES (24, '060', '$2a$10$E7AqKkBeFLegjBl0To97mOVX7SBPth8d6FVf0ttDMGhkTkDnci4Me');
INSERT INTO `user` VALUES (25, '061', '$2a$10$HcIvXlzjUW0qqqwzDXvBT.LTAb1gh6JzmVc1/LgWLGmmfKv4OHoju');
INSERT INTO `user` VALUES (26, '062', '$2a$10$64Hj.5Uj2yiqlBJmeuLt8eWxl72tPX19xKzj6qeZ2P1AhBWNMhok2');
INSERT INTO `user` VALUES (27, '063', '$2a$10$FcWjdrTNzQVT5AszgJkYC.GqD22UXWj1qLbGXDLmLb/43O6dMrC2S');
INSERT INTO `user` VALUES (28, '064', '$2a$10$7f7KLq1DpGV7thloL2C84uuaVfk54B7d73gAMap8Maiw8U87hInuq');
INSERT INTO `user` VALUES (29, '065', '$2a$10$BCinovKrMPkhh4fA.KwWJ.2QYyW3/K1glwCa1TDHHUKpVSD4SvFxi');
INSERT INTO `user` VALUES (30, '066', '$2a$10$vGeChJeXjdpDaeWrBqDMp.BQWiIgQVjf8NtQb2hemWUwUUxusJPdG');
INSERT INTO `user` VALUES (31, '067', '$2a$10$HrWZdKoSa/dl8wrJgeyhoe4pslt1AYzvCH8mkeZkneTtBpcCxO1fi');
INSERT INTO `user` VALUES (32, '068', '$2a$10$CJR9CDXG7VBibgZ3Utv3NOfaLyWGM2MrrIGZhcL9hVEwxAmu8QC6e');
INSERT INTO `user` VALUES (33, '069', '$2a$10$645bv96aN5SGlsoZknQTqewiCBgubbJacE3kBlpGqalOMXXpulmRG');
INSERT INTO `user` VALUES (34, '100', '$2a$10$deGg5IjCsnZ.MgWJ2gu8Buiu4pXYHmvmn1i/2SMr.iFfdx7lO2H6W');
INSERT INTO `user` VALUES (35, '101', '$2a$10$49SL.c9oPLiiWz0VRI0x2eD8/SwLUZlEyotl6TsaWCrnx06Zgq3z6');
INSERT INTO `user` VALUES (36, '102', '$2a$10$V0JALuOpokXzpyXtmox0ROCRE4oKCvMuK0UvZNnq6P0zj3kexfLka');
INSERT INTO `user` VALUES (37, '103', '$2a$10$yasbNPJuQoEardDk2Qdzi.TRCVngZM64.ZvJliyTZKzlfhNXcbgBW');
INSERT INTO `user` VALUES (38, '104', '$2a$10$hzWMbrBLw9qTZlxJNXCCeO4PLdzMrMl1fgxpTmzWKYM06zFf.stD2');
INSERT INTO `user` VALUES (39, '105', '$2a$10$RhP9IY.LduUHyD3q.XSaa.HjpVcJj5tozrZjbINAp.z4lti/bjetu');
INSERT INTO `user` VALUES (40, '106', '$2a$10$Xk99MKagukvC6EqYXBnZFetD/gtXZ1AuTrPqw1lQL4eRMnwJlpcDK');
INSERT INTO `user` VALUES (41, '107', '$2a$10$Z2pZOENRsidFv5QJlb/creuybjIxp.GqYm5qLnle9mTA6HJg1Qi4q');
INSERT INTO `user` VALUES (42, '108', '$2a$10$wzb464IZQa3B7/1Ieznmnul.pGR9NddFXktkNiWNJO28jpCWZUiji');
INSERT INTO `user` VALUES (43, '109', '$2a$10$vWbGKO7P71UpqZi29A2VYulA3ahyhVO00s9fI4hgHziaRGyYLkhvW');
INSERT INTO `user` VALUES (44, '110', '$2a$10$DlM82aNcomM0.am2KgROce34HN1iFeI/iTxShv.dmabhy.4yZd1n.');
INSERT INTO `user` VALUES (45, '111', '$2a$10$mCd82MsL/9UdZ/D36exhhulsI.1V4pJ8mmudnLSvuRLwN4ICk9A1a');
INSERT INTO `user` VALUES (46, '112', '$2a$10$JnzlekhkTwLen1eZ175D1.V0hA4mClXenTumOzYs9dZ1pl0TfXwyu');
INSERT INTO `user` VALUES (47, '113', '$2a$10$6YQxh6zf7oIPRtQcI42xv.sRZ8nXTRGwdvvFvgjzF1WX3VIbSGqWO');
INSERT INTO `user` VALUES (48, '114', '$2a$10$Ss5zCSB28o5KyJjFIIfQOuQW0iTUG5G4jFYdtZv8ugrMzOPl941i6');
INSERT INTO `user` VALUES (49, '115', '$2a$10$h.5WXGJkm7EkZ3XElygFceGSpnspEbtZ94o5u3K7fTWVCFfTmSalu');
INSERT INTO `user` VALUES (50, '116', '$2a$10$YvJ2nqDOyQqTO8nHFkkO2u8e9LBSdtDgxLtAsLzOf2fRJbcxAMJTW');
INSERT INTO `user` VALUES (51, '117', '$2a$10$qMJ./SYdOZzwOW9AvR2WhuGVcEs9bl/3fy1Sv3mvADdn/9LvONEfa');
INSERT INTO `user` VALUES (52, '118', '$2a$10$wRQC0L0FL0tGK879kA0DLuGWmQXkkUWqc9J17R1sX1j4EcGKOk5CO');
INSERT INTO `user` VALUES (53, '119', '$2a$10$ZBsEORTCB7orvP6sJkfvju/llu/XdHS8PD4YWfCfJ2ehh8LFw9kra');
INSERT INTO `user` VALUES (54, '150', '$2a$10$89vNVImuySDBoYQSzeTgDONWy4Byeob3qor4w.va7yD6Zq1r/fPy.');

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `userRoleID` int NOT NULL AUTO_INCREMENT,
  `userID` int NOT NULL,
  `role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`userRoleID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 56 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES (1, 1, 'ROLE_ADMIN');
INSERT INTO `user_role` VALUES (2, 2, 'ROLE_ADMIN');
INSERT INTO `user_role` VALUES (3, 3, 'ROLE_ADMIN');
INSERT INTO `user_role` VALUES (4, 4, 'ROLE_INSTRUCTOR');
INSERT INTO `user_role` VALUES (5, 5, 'ROLE_INSTRUCTOR');
INSERT INTO `user_role` VALUES (6, 6, 'ROLE_INSTRUCTOR');
INSERT INTO `user_role` VALUES (7, 7, 'ROLE_INSTRUCTOR');
INSERT INTO `user_role` VALUES (8, 8, 'ROLE_INSTRUCTOR');
INSERT INTO `user_role` VALUES (9, 9, 'ROLE_INSTRUCTOR');
INSERT INTO `user_role` VALUES (10, 10, 'ROLE_INSTRUCTOR');
INSERT INTO `user_role` VALUES (11, 11, 'ROLE_INSTRUCTOR');
INSERT INTO `user_role` VALUES (12, 12, 'ROLE_INSTRUCTOR');
INSERT INTO `user_role` VALUES (13, 13, 'ROLE_INSTRUCTOR');
INSERT INTO `user_role` VALUES (14, 14, 'ROLE_INSTRUCTOR');
INSERT INTO `user_role` VALUES (15, 15, 'ROLE_INSTRUCTOR');
INSERT INTO `user_role` VALUES (16, 16, 'ROLE_INSTRUCTOR');
INSERT INTO `user_role` VALUES (17, 17, 'ROLE_INSTRUCTOR');
INSERT INTO `user_role` VALUES (18, 18, 'ROLE_INSTRUCTOR');
INSERT INTO `user_role` VALUES (19, 19, 'ROLE_INSTRUCTOR');
INSERT INTO `user_role` VALUES (20, 20, 'ROLE_INSTRUCTOR');
INSERT INTO `user_role` VALUES (21, 21, 'ROLE_INSTRUCTOR');
INSERT INTO `user_role` VALUES (22, 22, 'ROLE_INSTRUCTOR');
INSERT INTO `user_role` VALUES (23, 23, 'ROLE_INSTRUCTOR');
INSERT INTO `user_role` VALUES (24, 24, 'ROLE_TECHNICIAN');
INSERT INTO `user_role` VALUES (25, 25, 'ROLE_TECHNICIAN');
INSERT INTO `user_role` VALUES (26, 26, 'ROLE_TECHNICIAN');
INSERT INTO `user_role` VALUES (27, 27, 'ROLE_TECHNICIAN');
INSERT INTO `user_role` VALUES (28, 28, 'ROLE_TECHNICIAN');
INSERT INTO `user_role` VALUES (29, 29, 'ROLE_TECHNICIAN');
INSERT INTO `user_role` VALUES (30, 30, 'ROLE_TECHNICIAN');
INSERT INTO `user_role` VALUES (31, 31, 'ROLE_TECHNICIAN');
INSERT INTO `user_role` VALUES (32, 32, 'ROLE_TECHNICIAN');
INSERT INTO `user_role` VALUES (33, 33, 'ROLE_TECHNICIAN');
INSERT INTO `user_role` VALUES (34, 34, 'ROLE_STUDENT');
INSERT INTO `user_role` VALUES (35, 35, 'ROLE_STUDENT');
INSERT INTO `user_role` VALUES (36, 36, 'ROLE_STUDENT');
INSERT INTO `user_role` VALUES (37, 37, 'ROLE_STUDENT');
INSERT INTO `user_role` VALUES (38, 38, 'ROLE_STUDENT');
INSERT INTO `user_role` VALUES (39, 39, 'ROLE_STUDENT');
INSERT INTO `user_role` VALUES (40, 40, 'ROLE_STUDENT');
INSERT INTO `user_role` VALUES (41, 41, 'ROLE_STUDENT');
INSERT INTO `user_role` VALUES (42, 42, 'ROLE_STUDENT');
INSERT INTO `user_role` VALUES (43, 43, 'ROLE_STUDENT');
INSERT INTO `user_role` VALUES (44, 44, 'ROLE_STUDENT');
INSERT INTO `user_role` VALUES (45, 45, 'ROLE_STUDENT');
INSERT INTO `user_role` VALUES (46, 46, 'ROLE_STUDENT');
INSERT INTO `user_role` VALUES (47, 47, 'ROLE_STUDENT');
INSERT INTO `user_role` VALUES (48, 48, 'ROLE_STUDENT');
INSERT INTO `user_role` VALUES (49, 49, 'ROLE_STUDENT');
INSERT INTO `user_role` VALUES (50, 50, 'ROLE_STUDENT');
INSERT INTO `user_role` VALUES (51, 51, 'ROLE_STUDENT');
INSERT INTO `user_role` VALUES (52, 52, 'ROLE_STUDENT');
INSERT INTO `user_role` VALUES (53, 53, 'ROLE_STUDENT');
INSERT INTO `user_role` VALUES (54, 54, 'ROLE_TECHNICIAN');
INSERT INTO `user_role` VALUES (55, 54, 'ROLE_INSTRUCTOR');

SET FOREIGN_KEY_CHECKS = 1;
