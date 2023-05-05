/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 80017
Source Host           : 127.0.0.1:3306
Source Database       : student

Target Server Type    : MYSQL
Target Server Version : 80017
File Encoding         : 65001

Date: 2023-05-05 11:59:58
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for askleave
-- ----------------------------
DROP TABLE IF EXISTS `askleave`;
CREATE TABLE `askleave` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sid` int(11) DEFAULT NULL COMMENT '学生id',
  `leave_reason` varchar(255) DEFAULT NULL COMMENT '请假原因',
  `start_time` datetime DEFAULT NULL COMMENT '请假开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '请假结束时间',
  `create_time` datetime DEFAULT NULL COMMENT '请假单创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of askleave
-- ----------------------------
INSERT INTO `askleave` VALUES ('1', '1', '事假123', '2023-05-05 10:40:02', '2023-05-13 10:40:12', '2023-05-05 10:40:27');
INSERT INTO `askleave` VALUES ('2', '2', '病假234', '2023-05-05 10:40:05', '2023-05-19 10:40:18', '2023-05-05 10:40:30');
INSERT INTO `askleave` VALUES ('3', '3', '家里有事', '2023-05-05 10:40:08', '2023-05-08 10:40:22', '2023-05-05 10:40:34');
