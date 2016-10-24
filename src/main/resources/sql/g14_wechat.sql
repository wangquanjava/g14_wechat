/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50622
Source Host           : localhost:3306
Source Database       : g14_wechat

Target Server Type    : MYSQL
Target Server Version : 50622
File Encoding         : 65001

Date: 2016-10-24 18:36:45
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for item
-- ----------------------------
DROP TABLE IF EXISTS `item`;
CREATE TABLE `item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(100) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  `pic_url` varchar(100) DEFAULT NULL,
  `url` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of item
-- ----------------------------
INSERT INTO `item` VALUES ('1', '变形金刚1', '这是变形金刚1', 'http://ww4.sinaimg.cn/large/006x2JNajw1f93gzkx30dj3083083gmf.jpg', 'www.baidu.com');
INSERT INTO `item` VALUES ('2', '变形金刚2', '这是变形金刚2', 'http://ww4.sinaimg.cn/large/006x2JNajw1f93gzkx30dj3083083gmf.jpg', 'www.baidu.com');
INSERT INTO `item` VALUES ('3', '变形金刚3', '这是变形金刚3', 'http://ww4.sinaimg.cn/large/006x2JNajw1f93gzkx30dj3083083gmf.jpg', 'www.baidu.com');
INSERT INTO `item` VALUES ('4', '变形金刚4', '这是变形金刚4', 'http://ww4.sinaimg.cn/large/006x2JNajw1f93gzkx30dj3083083gmf.jpg', 'www.baidu.com');
