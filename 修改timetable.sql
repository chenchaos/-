/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 80017
Source Host           : 127.0.0.1:3306
Source Database       : student

Target Server Type    : MYSQL
Target Server Version : 80017
File Encoding         : 65001

Date: 2023-05-04 16:51:55
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for timetable
-- ----------------------------
DROP TABLE IF EXISTS `timetable`;
CREATE TABLE `timetable` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `am8` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '上午8-9点',
  `am10` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '上午10-11点',
  `pm2` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '下午2-3点',
  `pm4` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '下午4-5点',
  `faculty` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '院系',
  `weekday` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '周工作日(星期一至星期五的任何一天)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='课程表';
