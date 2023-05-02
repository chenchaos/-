/*
 Navicat Premium Data Transfer

 Source Server         : localhsot
 Source Server Type    : MySQL
 Source Server Version : 50719
 Source Host           : localhost:3306
 Source Schema         : doctor

 Target Server Type    : MySQL
 Target Server Version : 50719
 File Encoding         : 65001

 Date: 16/04/2023 01:24:18
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for User
-- ----------------------------
DROP TABLE IF EXISTS `User`;
CREATE TABLE `User` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `phone` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '手机',
  `pass_work` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '密码',
  `img_id` int(11) DEFAULT NULL COMMENT '头像id',
  `amount` double DEFAULT '0' COMMENT '余额',
  `status` int(11) DEFAULT '0' COMMENT '0:正常 1:冻结',
  `name` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '昵称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for adminUser
-- ----------------------------
DROP TABLE IF EXISTS `adminUser`;
CREATE TABLE `adminUser` (
  `user_name` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '管理员账号',
  `passwork` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '管理员密码'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='管理员账号';

-- ----------------------------
-- Table structure for chat
-- ----------------------------
DROP TABLE IF EXISTS `chat`;
CREATE TABLE `chat` (
  `order_id` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '订单id',
  `text` text COLLATE utf8mb4_bin COMMENT '聊天内容',
  `name` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '聊天者姓名',
  `create_time` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '聊天时间',
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='聊天室表;';

-- ----------------------------
-- Table structure for classify
-- ----------------------------
DROP TABLE IF EXISTS `classify`;
CREATE TABLE `classify` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '分类名称',
  `img_id` int(11) DEFAULT NULL COMMENT '分类图片id',
  `order_num` int(11) DEFAULT NULL COMMENT '分类排序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='分类表';

-- ----------------------------
-- Table structure for doctor
-- ----------------------------
DROP TABLE IF EXISTS `doctor`;
CREATE TABLE `doctor` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '名称',
  `detail` text COLLATE utf8mb4_bin COMMENT '医生介绍',
  `img_id` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '医生详情图片id',
  `desc_img_id` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '医生头像id',
  `password` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='医生表;';

-- ----------------------------
-- Table structure for drug_msg
-- ----------------------------
DROP TABLE IF EXISTS `drug_msg`;
CREATE TABLE `drug_msg` (
  `name` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '药品名称',
  `price` double(10,2) DEFAULT NULL COMMENT '药品价格',
  `img` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='药品信息表';

-- ----------------------------
-- Table structure for drug_order
-- ----------------------------
DROP TABLE IF EXISTS `drug_order`;
CREATE TABLE `drug_order` (
  `order_id` int(255) DEFAULT NULL COMMENT '订单id',
  `name` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '药品名称',
  `price` varchar(55) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '药品价格',
  `is_pay` int(11) DEFAULT NULL COMMENT '是否支付;0:未支付 1:已支付'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='订单药品表;';

-- ----------------------------
-- Table structure for fall_order
-- ----------------------------
DROP TABLE IF EXISTS `fall_order`;
CREATE TABLE `fall_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fall_time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '生病时间;0:一个月内。1：三个月内  2三个月以上',
  `is_see` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '是否就诊过;0:否 1:是',
  `detail` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin COMMENT '具体问题',
  `doctor_id` int(255) DEFAULT NULL COMMENT '医生id',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='门诊挂号订单表;';

-- ----------------------------
-- Table structure for img
-- ----------------------------
DROP TABLE IF EXISTS `img`;
CREATE TABLE `img` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `img` longblob,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=231 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for merchandise
-- ----------------------------
DROP TABLE IF EXISTS `merchandise`;
CREATE TABLE `merchandise` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '商品名称',
  `type_id` int(11) DEFAULT NULL COMMENT '商品分类id',
  `group_num` int(11) DEFAULT NULL COMMENT '几人成团',
  `price` double DEFAULT NULL COMMENT '商品价格',
  `img_id` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '图片id，多个,分割',
  `recommend_num` int(11) DEFAULT NULL COMMENT '推荐星级',
  `create_time` datetime DEFAULT NULL,
  `details_img_id` int(11) DEFAULT NULL COMMENT '商品详情图',
  `details` text COLLATE utf8_bin COMMENT '商品详情',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='商品表';

-- ----------------------------
-- Table structure for pm_order
-- ----------------------------
DROP TABLE IF EXISTS `pm_order`;
CREATE TABLE `pm_order` (
  `user_phone` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '用户手机号',
  `mcd_id` int(11) DEFAULT NULL COMMENT '商品id',
  `status` int(10) DEFAULT NULL COMMENT '订单状态 0:拼团中 1:拼团成功',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `adress` text COLLATE utf8_bin COMMENT '收货地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

SET FOREIGN_KEY_CHECKS = 1;
