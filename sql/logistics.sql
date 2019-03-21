/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50723
Source Host           : 127.0.0.1:3306
Source Database       : logistics

Target Server Type    : MYSQL
Target Server Version : 50723
File Encoding         : 65001

Date: 2019-03-22 01:05:01
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for billinfo
-- ----------------------------
DROP TABLE IF EXISTS `billinfo`;
CREATE TABLE `billinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `accept_station` varchar(50) DEFAULT NULL,
  `bill_code` varchar(50) DEFAULT NULL,
  `bill_state` varchar(50) DEFAULT NULL,
  `bill_type` varchar(50) DEFAULT NULL,
  `write_date` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='票据查看 货运单使用(单据明细表)';

-- ----------------------------
-- Records of billinfo
-- ----------------------------
INSERT INTO `billinfo` VALUES ('1', null, 'HY669044', '已填', '货运单', '2019-03-10 16:37:52.520000');
INSERT INTO `billinfo` VALUES ('3', '杭州', 'HZ171436', '已填', '货运回执单', '2019-03-10 16:51:06.464000');
INSERT INTO `billinfo` VALUES ('4', null, 'HY282907', '已填', '货运单', '2019-03-10 17:50:36.065000');
INSERT INTO `billinfo` VALUES ('5', '杭州', 'HZ742133', '已填', '货运回执单', '2019-03-10 17:52:01.464000');
INSERT INTO `billinfo` VALUES ('6', null, 'HY602419', '已填', '货运单', '2019-03-10 18:35:47.971000');
INSERT INTO `billinfo` VALUES ('7', '上海', 'HZ831013', '已填', '货运回执单', '2019-03-10 18:37:12.411000');
INSERT INTO `billinfo` VALUES ('8', null, 'HY770674', '已填', '货运单', '2019-03-10 22:16:36.799000');
INSERT INTO `billinfo` VALUES ('10', null, 'HY842372', '已填', '货运单', '2019-03-17 11:48:01.254000');
INSERT INTO `billinfo` VALUES ('11', null, 'HY050568', '已填', '货运单', '2019-03-17 12:22:32.117000');
INSERT INTO `billinfo` VALUES ('12', '上海', 'HZ421544', '已填', '货运回执单', '2019-03-17 12:23:58.233000');
INSERT INTO `billinfo` VALUES ('15', null, 'HY812216', '已填', '货运单', '2019-03-17 19:21:50.206000');
INSERT INTO `billinfo` VALUES ('16', '杭州', 'HZ738738', '已填', '货运回执单', '2019-03-17 19:22:46.471000');
INSERT INTO `billinfo` VALUES ('17', null, 'HY910010', '已填', '货运单', '2019-03-21 22:45:43.459000');
INSERT INTO `billinfo` VALUES ('18', '上海', 'HZ563217', '已填', '货运回执单', '2019-03-21 22:47:54.810000');
INSERT INTO `billinfo` VALUES ('19', null, 'HY716876', '已填', '货运单', '2019-03-22 00:41:49.910000');
INSERT INTO `billinfo` VALUES ('20', '杭州', 'HZ333632', '作废', '货运回执单', '2019-03-22 00:45:02.307000');
INSERT INTO `billinfo` VALUES ('21', '杭州', 'HZ333632', '已填', '货运回执单', '2019-03-22 00:46:26.766000');

-- ----------------------------
-- Table structure for billrelease
-- ----------------------------
DROP TABLE IF EXISTS `billrelease`;
CREATE TABLE `billrelease` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `accept_station` varchar(50) DEFAULT NULL,
  `bill_code` varchar(50) DEFAULT NULL,
  `bill_type` varchar(50) DEFAULT NULL,
  `receive_bill_person` varchar(50) DEFAULT NULL,
  `receive_bill_time` date DEFAULT NULL,
  `release_person` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='将货运单与司机建立关系(单据分发表)';

-- ----------------------------
-- Records of billrelease
-- ----------------------------
INSERT INTO `billrelease` VALUES ('1', '杭州', 'HY669044', '货运单', 'SJ065481', '2019-03-10', 'GL846489');
INSERT INTO `billrelease` VALUES ('3', '杭州', 'HY282907', '货运单', 'SJ065481', '2019-03-10', 'GL846489');
INSERT INTO `billrelease` VALUES ('4', '上海', 'HY602419', '货运单', 'SJ159922', '2019-03-10', 'GL846489');
INSERT INTO `billrelease` VALUES ('5', '杭州', 'HY770674', '货运单', 'SJ065481', '2019-03-11', 'GL846489');
INSERT INTO `billrelease` VALUES ('6', '杭州', 'HY050568', '货运单', 'SJ065481', '2019-03-17', 'GL846489');
INSERT INTO `billrelease` VALUES ('7', '杭州', 'HY842372', '货运单', 'SJ159922', '2019-03-17', 'GL846489');
INSERT INTO `billrelease` VALUES ('10', '杭州', 'HY812216', '货运单', 'SJ065481', '2019-03-17', 'GL846489');
INSERT INTO `billrelease` VALUES ('11', '上海', 'HY910010', '货运单', 'SJ159922', '2019-03-21', 'GL846489');
INSERT INTO `billrelease` VALUES ('12', '杭州', 'HY716876', '货运单', 'SJ065481', '2019-03-22', 'GL846489');

-- ----------------------------
-- Table structure for callbackinfo
-- ----------------------------
DROP TABLE IF EXISTS `callbackinfo`;
CREATE TABLE `callbackinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bill_id` varchar(50) DEFAULT NULL,
  `bill_type` varchar(50) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `dial_no` varchar(50) DEFAULT NULL,
  `finally_dial_time` date DEFAULT NULL,
  `goods_bill_id` varchar(50) DEFAULT NULL,
  `locked` bit(1) NOT NULL,
  `success` bit(1) NOT NULL,
  `type` varchar(50) DEFAULT NULL,
  `write_time` date DEFAULT NULL,
  `writer` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='客服通知发货人，收货人(回告信息表)';

-- ----------------------------
-- Records of callbackinfo
-- ----------------------------
INSERT INTO `callbackinfo` VALUES ('1', null, null, '客服拨打电话提示收货人', '15893630802', null, 'HY669044', '\0', '\0', '已提回告', '2019-03-10', 'KF936854');
INSERT INTO `callbackinfo` VALUES ('2', null, null, '客服拨打电话提示发货人', '15893630801', null, 'HY669044', '\0', '\0', '到货回告', '2019-03-10', 'KF936854');
INSERT INTO `callbackinfo` VALUES ('3', null, null, '大家好，我现在已经到达中转地，一起正常', '15893630801', null, 'HY282907', '\0', '\0', '中转回告', '2019-03-10', 'SJ065481');
INSERT INTO `callbackinfo` VALUES ('4', null, null, '收件人快来取货了', '15893630802', null, 'HY282907', '\0', '\0', '提货回告', '2019-03-10', 'KF936854');
INSERT INTO `callbackinfo` VALUES ('5', null, null, '通知发件人 物品已经到达目的地', '15893630801', null, 'HY282907', '\0', '\0', '到货回告', '2019-03-10', 'KF936854');
INSERT INTO `callbackinfo` VALUES ('6', null, null, '你的快递已经到达', '15893630801', null, 'HY602419', '\0', '\0', '提货回告', '2019-03-10', 'KF936854');
INSERT INTO `callbackinfo` VALUES ('7', null, null, '你的快递已经到达目的地', '15893630802', null, 'HY602419', '\0', '\0', '到货回告', '2019-03-10', 'KF936854');
INSERT INTO `callbackinfo` VALUES ('8', null, null, '收件人快到取货', '15893630801', null, 'HY812216', '\0', '\0', '提货回告', '2019-03-19', 'KF743008');
INSERT INTO `callbackinfo` VALUES ('9', null, null, '发件人的货已经到达目的地', '15893630801', null, 'HY812216', '\0', '\0', '到货回告', '2019-03-19', 'KF743008');
INSERT INTO `callbackinfo` VALUES ('11', null, null, '测试调用方法', '15893630801', null, 'HY050568', '\0', '\0', '中转回告', '2019-03-19', 'KH385751');
INSERT INTO `callbackinfo` VALUES ('12', null, null, '交易完成', '15893630801', null, 'HY812216', '\0', '\0', '已提回告', '2019-03-19', 'KF743008');
INSERT INTO `callbackinfo` VALUES ('13', null, null, '交易完成', '15893630801', null, 'HY842372', '\0', '\0', '已提回告', '2019-03-19', 'KF743008');
INSERT INTO `callbackinfo` VALUES ('14', null, null, '发件人的货已经到达目的地', '15893630801', null, 'HY842372', '\0', '\0', '到货回告', '2019-03-19', 'KF743008');
INSERT INTO `callbackinfo` VALUES ('15', null, null, '收件人快到取货', '15893630801', null, 'HY842372', '\0', '\0', '提货回告', '2019-03-19', 'KF743008');
INSERT INTO `callbackinfo` VALUES ('17', null, null, '收件人快到取货', '15893630801', null, 'HY770674', '\0', '\0', '中转回告', '2019-03-19', 'KF743008');
INSERT INTO `callbackinfo` VALUES ('18', null, null, '发件人的货已经到达目的地', '15893630801', null, 'HY050568', '\0', '\0', '到货回告', '2019-03-21', 'KF743008');
INSERT INTO `callbackinfo` VALUES ('19', null, null, '发件人的货已经到达目的地', '15893630801', null, 'HY770674', '\0', '\0', '到货回告', '2019-03-21', 'KF743008');
INSERT INTO `callbackinfo` VALUES ('20', null, null, '收件人快到取货', '15893630801', null, 'HY050568', '\0', '\0', '提货回告', '2019-03-21', 'KF743008');
INSERT INTO `callbackinfo` VALUES ('21', null, null, '收件人快到取货', '15893630802', null, 'HY770674', '\0', '\0', '提货回告', '2019-03-21', 'KF743008');
INSERT INTO `callbackinfo` VALUES ('22', null, null, '收件人取货完成', '15893630801', null, 'HY050568', '\0', '\0', '已提回告', '2019-03-21', 'KF743008');
INSERT INTO `callbackinfo` VALUES ('23', null, null, '你的快件已到达，请收货', '15893630801', null, 'HY910010', '\0', '\0', '提货回告', '2019-03-22', 'KF743008');
INSERT INTO `callbackinfo` VALUES ('24', null, null, '你的快件已到达，请放心', '15893630801', null, 'HY910010', '\0', '\0', '到货回告', '2019-03-22', 'KF743008');
INSERT INTO `callbackinfo` VALUES ('25', null, null, '已经提走货物', '15893630801', null, 'HY910010', '\0', '\0', '已提回告', '2019-03-22', 'KH385751');
INSERT INTO `callbackinfo` VALUES ('26', null, null, '中转完成', '15893630801', null, 'HY716876', '\0', '\0', '中转回告', '2019-03-22', 'SJ065481');
INSERT INTO `callbackinfo` VALUES ('27', null, null, '中转完成', '15893630801', null, 'HY716876', '\0', '\0', '提货回告', '2019-03-22', 'KF743008');
INSERT INTO `callbackinfo` VALUES ('28', null, null, '收件人快到取货', '15893630801', null, 'HY716876', '\0', '\0', '到货回告', '2019-03-22', 'KF743008');
INSERT INTO `callbackinfo` VALUES ('29', null, null, '', '15893630801', null, 'HY716876', '\0', '\0', '已提回告', '2019-03-22', 'KH823675');

-- ----------------------------
-- Table structure for carcost
-- ----------------------------
DROP TABLE IF EXISTS `carcost`;
CREATE TABLE `carcost` (
  `driver_code` varchar(50) NOT NULL,
  `add_carriage_total` double NOT NULL,
  `allow_carry_weight` double DEFAULT NULL,
  `back_bill_code` varchar(255) DEFAULT NULL,
  `balance_time` datetime(6) DEFAULT NULL,
  `car_no` varchar(50) DEFAULT NULL,
  `car_type` varchar(50) DEFAULT NULL,
  `car_width` varchar(255) DEFAULT NULL,
  `carry_fee_total` double NOT NULL,
  `deal_goods_station` varchar(50) DEFAULT NULL,
  `fact_carriage_total` double NOT NULL,
  `goods_height` varchar(50) DEFAULT NULL,
  `load_station` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`driver_code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='统计报表-车辆运量(打印车辆成本)';

-- ----------------------------
-- Records of carcost
-- ----------------------------
INSERT INTO `carcost` VALUES ('SJ065481', '0', '30', null, null, '01', '普通货车', '3.8', '200', null, '0', '5.8', null);
INSERT INTO `carcost` VALUES ('SJ159922', '0', '3', null, null, '02', '货车', '3', '0', null, '0', '3', null);

-- ----------------------------
-- Table structure for cargoerror
-- ----------------------------
DROP TABLE IF EXISTS `cargoerror`;
CREATE TABLE `cargoerror` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `customer` varchar(50) DEFAULT NULL,
  `goods_bill_code` varchar(50) DEFAULT NULL,
  `goods_name` varchar(50) DEFAULT NULL,
  `goods_revert_bill_code` varchar(50) DEFAULT NULL,
  `goods_value` double DEFAULT NULL,
  `mistake_type` varchar(50) DEFAULT NULL,
  `piece_amount` int(11) DEFAULT NULL,
  `size` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='货运差错表';

-- ----------------------------
-- Records of cargoerror
-- ----------------------------

-- ----------------------------
-- Table structure for cargoreceipt
-- ----------------------------
DROP TABLE IF EXISTS `cargoreceipt`;
CREATE TABLE `cargoreceipt` (
  `goods_revert_bill_code` varchar(50) NOT NULL,
  `accept_station` varchar(50) DEFAULT NULL,
  `all_carriage` double NOT NULL,
  `arrive_time` date DEFAULT NULL,
  `back_bill_state` varchar(50) DEFAULT NULL,
  `carriage_banlance_mode` varchar(50) DEFAULT NULL,
  `carriage_mode` varchar(50) DEFAULT NULL,
  `carry_goods_bill_deposit` double NOT NULL,
  `carry_goods_insurance` double NOT NULL,
  `deal_goods_station` varchar(50) DEFAULT NULL,
  `dispatch_service_fee` double NOT NULL,
  `driver_id` varchar(50) DEFAULT NULL,
  `if_balance` varchar(50) DEFAULT NULL,
  `insurance` double NOT NULL,
  `linkman_phone` varchar(50) DEFAULT NULL,
  `load_station` varchar(50) DEFAULT NULL,
  `receive_goods_detail_addr` varchar(50) DEFAULT NULL,
  `receive_goods_linkman` varchar(50) DEFAULT NULL,
  `remark` varchar(50) DEFAULT NULL,
  `sign_time` date DEFAULT NULL,
  `start_advance` double NOT NULL,
  `start_carry_time` date DEFAULT NULL,
  PRIMARY KEY (`goods_revert_bill_code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='回执单合同，之前的基础上真正设置运输合同(货运回执单主表)';

-- ----------------------------
-- Records of cargoreceipt
-- ----------------------------
INSERT INTO `cargoreceipt` VALUES ('HZ128709', '上海', '1000', '2019-03-19', '已结合同', '现金', '0', '0', '0', '杭州', '0', 'SJ159922', null, '0', '15893630801', '上海', '杭州', '杭州陈1科技公司', '测试添加运输单失败', '2019-03-17', '200', '2019-03-17');
INSERT INTO `cargoreceipt` VALUES ('HZ171436', '杭州', '1000', '2019-03-10', '已结合同', '现金', '随便', '500', '200', '上海', '20', 'SJ065481', null, '200', '15893630802', '杭州', '上海', '上海陈2科技公司', '现金结算', '2019-03-10', '200', '2019-03-10');
INSERT INTO `cargoreceipt` VALUES ('HZ333632', '杭州', '11', '2019-03-22', '已结合同', '1', '1', '1', '1', '上海', '1', 'SJ065481', null, '1', '15893630801', '杭州', '上海', '上海第一公司', '1', '2019-03-22', '1', '2019-03-22');
INSERT INTO `cargoreceipt` VALUES ('HZ421544', '上海', '2000', '2019-03-21', '已结合同', '现金', '现金', '0', '0', '杭州', '0', 'SJ065481', null, '0', '15893630801', '上海', '上海', '上海第一公司', '0', '2019-03-17', '200', '2019-03-17');
INSERT INTO `cargoreceipt` VALUES ('HZ528643', '杭州', '5000', '2019-03-21', '已结合同', '现金', '批发', '0', '0', '上海', '0', 'SJ065481', null, '0', '15893630802', '杭州', '上海', '上海陈2科技公司', '测试回告问题', '2019-03-10', '800', '2019-03-11');
INSERT INTO `cargoreceipt` VALUES ('HZ563217', '上海', '2000', '2019-03-22', '已结合同', '现金', '批量', '200', '200', '杭州', '500', 'SJ159922', null, '200', '15893630801', '上海', '杭州', '杭州陈1科技公司', '测试整个流程', '2019-03-21', '500', '2019-03-21');
INSERT INTO `cargoreceipt` VALUES ('HZ738738', '杭州', '0', '2019-03-17', '已结合同', '现金', '0', '0', '0', '上海', '0', 'SJ065481', null, '0', '15893630801', '杭州', '上海', '上海第一公司', '无', '2019-03-17', '20', '2019-03-17');
INSERT INTO `cargoreceipt` VALUES ('HZ742133', '杭州', '2000', '2019-03-10', '已结合同', '现金', '无', '0', '0', '上海', '0', 'SJ065481', null, '0', '15893630802', '杭州', '上海', '上海陈2科技公司', '第二个运输单', '2019-03-10', '0', '2019-03-10');
INSERT INTO `cargoreceipt` VALUES ('HZ831013', '上海', '0', '2019-03-10', '已结合同', '现金', '0', '0', '0', '杭州', '0', 'SJ159922', null, '0', '15893630801', '上海', '杭州', '杭州陈1科技公司', '测试 第三次', '2019-03-10', '0', '2019-03-10');

-- ----------------------------
-- Table structure for cargoreceiptdetail
-- ----------------------------
DROP TABLE IF EXISTS `cargoreceiptdetail`;
CREATE TABLE `cargoreceiptdetail` (
  `goods_revert_bill_id` varchar(50) NOT NULL,
  `goods_bill_detail_id` varchar(255) DEFAULT NULL,
  `goods_value` double NOT NULL,
  `piece_amount` int(11) NOT NULL,
  `price_mode` varchar(50) NOT NULL,
  `price_standard` varchar(50) NOT NULL,
  `volume` double NOT NULL,
  `weight` double NOT NULL,
  PRIMARY KEY (`goods_revert_bill_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='回执单细节（运输合同）添加接货单后生成，为运输合同做准备(货运回执单详表)';

-- ----------------------------
-- Records of cargoreceiptdetail
-- ----------------------------
INSERT INTO `cargoreceiptdetail` VALUES ('HZ128709', 'HY842372', '20', '20', '20', '20', '20', '20');
INSERT INTO `cargoreceiptdetail` VALUES ('HZ171436', 'HY669044', '20000', '200', '重量', '随机', '3', '2.5');
INSERT INTO `cargoreceiptdetail` VALUES ('HZ333632', 'HY716876', '0', '20', '00', '0', '0', '0');
INSERT INTO `cargoreceiptdetail` VALUES ('HZ421544', 'HY050568', '2000', '20', '20', '2', '20', '20');
INSERT INTO `cargoreceiptdetail` VALUES ('HZ528643', 'HY770674', '5000', '50', '50', '50', '50', '50');
INSERT INTO `cargoreceiptdetail` VALUES ('HZ563217', 'HY910010', '20000', '500', '批量', '立方', '100', '100');
INSERT INTO `cargoreceiptdetail` VALUES ('HZ738738', 'HY812216', '52', '20', '20', '202', '20', '20');
INSERT INTO `cargoreceiptdetail` VALUES ('HZ742133', 'HY282907', '2000', '20', '20', '20', '20', '20');
INSERT INTO `cargoreceiptdetail` VALUES ('HZ831013', 'HY602419', '20', '20', '210', '20', '20', '20');

-- ----------------------------
-- Table structure for cityexpand
-- ----------------------------
DROP TABLE IF EXISTS `cityexpand`;
CREATE TABLE `cityexpand` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `city_id` int(11) NOT NULL,
  `range_city` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='起始城市和目的城市关系表(城市扩充表)';

-- ----------------------------
-- Records of cityexpand
-- ----------------------------
INSERT INTO `cityexpand` VALUES ('3', '6', '5,7,4,2');
INSERT INTO `cityexpand` VALUES ('5', '7', '4,6');

-- ----------------------------
-- Table structure for compensationinfo
-- ----------------------------
DROP TABLE IF EXISTS `compensationinfo`;
CREATE TABLE `compensationinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `amends` double NOT NULL,
  `amends_time` date DEFAULT NULL,
  `bad_destroy_goods` double NOT NULL,
  `customer` varchar(50) DEFAULT NULL,
  `receive_station_id` int(11) NOT NULL,
  `receive_station_name` varchar(50) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `write_date` date DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of compensationinfo
-- ----------------------------

-- ----------------------------
-- Table structure for complaintinfo
-- ----------------------------
DROP TABLE IF EXISTS `complaintinfo`;
CREATE TABLE `complaintinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `appeal_content` varchar(50) DEFAULT NULL,
  `appeal_date` varchar(50) DEFAULT NULL,
  `call_back_date` varchar(50) DEFAULT NULL,
  `customer` varchar(50) DEFAULT NULL,
  `deal_date` varchar(50) DEFAULT NULL,
  `deal_person` varchar(50) DEFAULT NULL,
  `deal_result` varchar(50) DEFAULT NULL,
  `goods_bill_code` varchar(50) DEFAULT NULL,
  `if_callback` bit(1) DEFAULT NULL,
  `if_handle` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of complaintinfo
-- ----------------------------

-- ----------------------------
-- Table structure for contactsservice
-- ----------------------------
DROP TABLE IF EXISTS `contactsservice`;
CREATE TABLE `contactsservice` (
  `send_goods_customer` varchar(50) NOT NULL,
  `balance` double NOT NULL,
  `bill_money` double NOT NULL,
  `carriage` double NOT NULL,
  `goods_bill_code` varchar(50) DEFAULT NULL,
  `insurance` double NOT NULL,
  `money_receivable` double NOT NULL,
  `receive_goods_addr` varchar(50) DEFAULT NULL,
  `received_money` double NOT NULL,
  `send_goods_addr` varchar(50) DEFAULT NULL,
  `send_goods_date` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`send_goods_customer`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='统计报表-往来业务表';

-- ----------------------------
-- Records of contactsservice
-- ----------------------------
INSERT INTO `contactsservice` VALUES ('上海第一公司', '0', '0', '2000', 'HY842372', '0', '0', '杭州', '0', '上海', '2019-03-17 00:00:00.000000');
INSERT INTO `contactsservice` VALUES ('上海陈2科技公司', '0', '0', '0', 'HY602419', '0', '0', '杭州', '0', '上海', '2019-03-10 00:00:00.000000');
INSERT INTO `contactsservice` VALUES ('杭州陈1科技公司', '0', '0', '2', 'HY812216', '2', '0', '上海', '0', '杭州', '2019-03-17 00:00:00.000000');

-- ----------------------------
-- Table structure for customeramount
-- ----------------------------
DROP TABLE IF EXISTS `customeramount`;
CREATE TABLE `customeramount` (
  `send_goods_customer` varchar(50) NOT NULL,
  `carriage_total` double NOT NULL,
  `insurance_total` double NOT NULL,
  `piece_amount_total` int(11) NOT NULL,
  PRIMARY KEY (`send_goods_customer`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='统计报表-客户运量';

-- ----------------------------
-- Records of customeramount
-- ----------------------------
INSERT INTO `customeramount` VALUES ('上海第一公司', '22000', '500', '2');
INSERT INTO `customeramount` VALUES ('上海陈2科技公司', '0', '0', '1');
INSERT INTO `customeramount` VALUES ('杭州陈1科技公司', '6502', '202', '5');

-- ----------------------------
-- Table structure for customerbillclear
-- ----------------------------
DROP TABLE IF EXISTS `customerbillclear`;
CREATE TABLE `customerbillclear` (
  `goods_bill_code` varchar(50) NOT NULL,
  `customer_code` varchar(255) DEFAULT NULL,
  `balance` double DEFAULT NULL,
  `balance_time` date DEFAULT NULL,
  `balance_type` varchar(50) DEFAULT NULL,
  `bill_money` double DEFAULT NULL,
  `carriage_reduce_fund` double DEFAULT NULL,
  `carry_goods_fee` double DEFAULT NULL,
  `insurance` double DEFAULT NULL,
  `money_receivable` double DEFAULT NULL,
  `pay_kickback` double DEFAULT NULL,
  `prepay_money` double DEFAULT NULL,
  `received_money` double DEFAULT NULL,
  PRIMARY KEY (`goods_bill_code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='货运单结算表';

-- ----------------------------
-- Records of customerbillclear
-- ----------------------------
INSERT INTO `customerbillclear` VALUES ('HY050568', 'KH385751', '0', '2019-03-21', '结入', '200', '20', '20', '0', '180', '0', '0', '180');
INSERT INTO `customerbillclear` VALUES ('HY282907', 'KH385751', '0', '2019-03-10', '结入', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `customerbillclear` VALUES ('HY602419', 'KH693381', '0', '2019-03-10', '结入', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `customerbillclear` VALUES ('HY669044', 'KH385751', '0', '2019-03-10', '结入', '2000', '200', '200', '200', '2000', '200', '0', '2000');
INSERT INTO `customerbillclear` VALUES ('HY716876', 'KH385751', '0', null, '结入', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `customerbillclear` VALUES ('HY770674', 'KH385751', '0', '2019-03-21', '结入', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `customerbillclear` VALUES ('HY812216', 'KH385751', '0', '2019-03-21', '结入', '0', '2', '2', '2', '0', '0', '0', '0');
INSERT INTO `customerbillclear` VALUES ('HY842372', 'KH823675', '0', '2019-03-19', '结入', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `customerbillclear` VALUES ('HY910010', 'KH823675', '0', '2019-03-22', '结入', '0', '200', '200', '500', '300', '0', '0', '300');

-- ----------------------------
-- Table structure for customerinfo
-- ----------------------------
DROP TABLE IF EXISTS `customerinfo`;
CREATE TABLE `customerinfo` (
  `customer_code` varchar(50) NOT NULL,
  `address` varchar(50) DEFAULT NULL,
  `customer` varchar(50) DEFAULT NULL,
  `customer_type` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `enterprise_property` varchar(50) DEFAULT NULL,
  `enterprise_size` varchar(50) DEFAULT NULL,
  `fax` varchar(50) DEFAULT NULL,
  `linkman` varchar(50) DEFAULT NULL,
  `linkman_mobile` varchar(50) DEFAULT NULL,
  `phone` varchar(50) DEFAULT NULL,
  `post_code` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`customer_code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of customerinfo
-- ----------------------------
INSERT INTO `customerinfo` VALUES ('KH385751', '杭州', '杭州陈1科技公司', '大客户', 'chen1@163.com', '私人', '200-999', '93630801', '陈1', '15893630801', '15893630801', '936308');
INSERT INTO `customerinfo` VALUES ('KH823675', '上海', '上海第一公司', '大型', '15893630801@163.com', '私人', '20-99', '93630801', '上海一哥', '15893630801', '15893630801', '630801');

-- ----------------------------
-- Table structure for customerreceiptinfo
-- ----------------------------
DROP TABLE IF EXISTS `customerreceiptinfo`;
CREATE TABLE `customerreceiptinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `carry_bill_event_id` int(11) NOT NULL,
  `check_goods_record` varchar(50) DEFAULT NULL,
  `customer` varchar(50) DEFAULT NULL,
  `goods_bill_code` varchar(50) DEFAULT NULL,
  `receive_goods_date` date DEFAULT NULL,
  `receive_goods_person` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='客户回执表';

-- ----------------------------
-- Records of customerreceiptinfo
-- ----------------------------
INSERT INTO `customerreceiptinfo` VALUES ('1', '0', '安全到货', '上海第一公司', 'HY812216', '2019-03-19', 'KH823675');
INSERT INTO `customerreceiptinfo` VALUES ('2', '0', '安全到货', '杭州陈1科技公司', 'HY842372', '2019-03-19', 'KH385751');
INSERT INTO `customerreceiptinfo` VALUES ('3', '0', '安全到货', '上海第一公司', 'HY050568', '2019-03-21', 'KH823675');
INSERT INTO `customerreceiptinfo` VALUES ('4', '0', '已确认收货', '杭州陈1科技公司', 'HY910010', '2019-03-22', 'KH385751');
INSERT INTO `customerreceiptinfo` VALUES ('5', '0', '', '上海第一公司', 'HY716876', '2019-03-22', 'KH823675');

-- ----------------------------
-- Table structure for driveramount
-- ----------------------------
DROP TABLE IF EXISTS `driveramount`;
CREATE TABLE `driveramount` (
  `driver_code` varchar(50) NOT NULL,
  `add_carriage_total` double NOT NULL,
  `carry_fee_total` double NOT NULL,
  `total` double NOT NULL,
  PRIMARY KEY (`driver_code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='统计报表-司机成本';

-- ----------------------------
-- Records of driveramount
-- ----------------------------
INSERT INTO `driveramount` VALUES ('SJ065481', '0', '200', '5');
INSERT INTO `driveramount` VALUES ('SJ159922', '0', '0', '2');

-- ----------------------------
-- Table structure for driverclear
-- ----------------------------
DROP TABLE IF EXISTS `driverclear`;
CREATE TABLE `driverclear` (
  `back_bill_code` varchar(50) NOT NULL,
  `add_carriage` double DEFAULT NULL,
  `balance` double DEFAULT NULL,
  `balance_time` date DEFAULT NULL,
  `balance_type` varchar(50) DEFAULT NULL,
  `bind_insurance` double DEFAULT NULL,
  `carry_fee` double DEFAULT NULL,
  `dispatch_service_fee` double DEFAULT NULL,
  `driver_code` varchar(50) DEFAULT NULL,
  `insurance` double DEFAULT NULL,
  `need_payment` double DEFAULT NULL,
  `payed_money` double DEFAULT NULL,
  `prepay_money` double DEFAULT NULL,
  PRIMARY KEY (`back_bill_code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='运输单结算表(司机结算主表)';

-- ----------------------------
-- Records of driverclear
-- ----------------------------
INSERT INTO `driverclear` VALUES ('HZ128709', '200', '-800', '2019-03-21', '结出', '0', '200', '0', 'SJ159922', '0', '200', '1200', '200');
INSERT INTO `driverclear` VALUES ('HZ171436', '0', '-800', '2019-03-10', '结出', '200', '200', '20', 'SJ065481', '200', '2000', '1200', '200');
INSERT INTO `driverclear` VALUES ('HZ333632', '0', '-11', '2019-03-22', '结出', '1', '0', '1', 'SJ065481', '1', '0', '12', '1');
INSERT INTO `driverclear` VALUES ('HZ421544', '200', '-1900', '2019-03-21', '结出', '0', '100', '0', 'SJ065481', '0', '0', '2200', '200');
INSERT INTO `driverclear` VALUES ('HZ528643', '0', '-5000', '2019-03-21', '结出', '0', '0', '0', 'SJ065481', '0', '5000', '5000', '800');
INSERT INTO `driverclear` VALUES ('HZ563217', '0', '-2000', '2019-03-22', '结出', '200', '0', '500', 'SJ159922', '200', '0', '2200', '500');
INSERT INTO `driverclear` VALUES ('HZ738738', '0', '0', '2019-03-21', '结出', '0', '0', '0', 'SJ065481', '0', '1200', '0', '20');
INSERT INTO `driverclear` VALUES ('HZ742133', '0', '-2000', '2019-03-10', '结出', '0', '0', '0', 'SJ065481', '0', '0', '2000', '0');
INSERT INTO `driverclear` VALUES ('HZ831013', '0', '0', '2019-03-10', '结出', '0', '0', '0', 'SJ159922', '0', '0', '0', '0');

-- ----------------------------
-- Table structure for driverinfo
-- ----------------------------
DROP TABLE IF EXISTS `driverinfo`;
CREATE TABLE `driverinfo` (
  `id` varchar(50) NOT NULL,
  `address` varchar(50) DEFAULT NULL,
  `allow_carry_volume` double DEFAULT NULL,
  `allow_carry_weight` double DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `biz_licence` varchar(50) DEFAULT NULL,
  `car_dept` varchar(50) DEFAULT NULL,
  `car_dept_tel` varchar(50) DEFAULT NULL,
  `car_frame_no` varchar(50) DEFAULT NULL,
  `car_length` varchar(50) DEFAULT NULL,
  `car_no` varchar(50) DEFAULT NULL,
  `car_type` varchar(50) DEFAULT NULL,
  `car_width` varchar(50) DEFAULT NULL,
  `company_car` bit(1) NOT NULL,
  `drive_licence` varchar(50) DEFAULT NULL,
  `driver_name` varchar(50) DEFAULT NULL,
  `engine_no` varchar(50) DEFAULT NULL,
  `gender` varchar(50) DEFAULT NULL,
  `goods_height` varchar(50) DEFAULT NULL,
  `id_card` varchar(50) DEFAULT NULL,
  `insurance_card` varchar(50) DEFAULT NULL,
  `phone` varchar(50) DEFAULT NULL,
  `remark` varchar(50) DEFAULT NULL,
  `run_licence` varchar(50) DEFAULT NULL,
  `state` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of driverinfo
-- ----------------------------
INSERT INTO `driverinfo` VALUES ('SJ065481', '杭州', '20', '30', '1983-10-10', 'C', '私人', '15893630801', '01', '5.5', '01', '普通货车', '3.8', '\0', 'A', '张三', '01', '男', '5.8', '412727199808022657', 'D', '15893630801', '张三', 'B', '空闲');
INSERT INTO `driverinfo` VALUES ('SJ159922', '上海', '5', '5', '2019-03-10', '3', '2', '15893630801', '3', '3', '02', '货车', '3', '\0', '3', '李四', '3', '男', '3', '412727199808022657', '3', '15893630801', '李四', '3', '空闲');

-- ----------------------------
-- Table structure for employee
-- ----------------------------
DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee` (
  `employee_code` varchar(50) NOT NULL,
  `birthday` date DEFAULT NULL,
  `department` varchar(50) DEFAULT NULL,
  `employee_name` varchar(50) DEFAULT NULL,
  `gender` varchar(50) DEFAULT NULL,
  `position` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`employee_code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='物流公司员工表';

-- ----------------------------
-- Records of employee
-- ----------------------------
INSERT INTO `employee` VALUES ('CW943338', '1989-03-10', '财务组', '财务1', '男', '助理');
INSERT INTO `employee` VALUES ('GL081838', '2019-03-14', '管理组', '测试随机数', '男', '经理');
INSERT INTO `employee` VALUES ('KF743008', '2019-03-19', '客服组', '客服1号', '男', '助理');
INSERT INTO `employee` VALUES ('PJ111879', '2019-03-10', '票据组', '票据组1姐', '女', '助理');
INSERT INTO `employee` VALUES ('PJ595668', '2019-03-14', '票据组', '测试随机id', '男', '经理');

-- ----------------------------
-- Table structure for employeeuser
-- ----------------------------
DROP TABLE IF EXISTS `employeeuser`;
CREATE TABLE `employeeuser` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `employee_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of employeeuser
-- ----------------------------

-- ----------------------------
-- Table structure for employeewage
-- ----------------------------
DROP TABLE IF EXISTS `employeewage`;
CREATE TABLE `employeewage` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `allowance` double NOT NULL,
  `basic_wage` double NOT NULL,
  `date` date DEFAULT NULL,
  `employee` varchar(50) DEFAULT NULL,
  `employee_code` varchar(50) NOT NULL,
  `station_wage` double NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='员工工资';

-- ----------------------------
-- Records of employeewage
-- ----------------------------
INSERT INTO `employeewage` VALUES ('1', '200', '2800', '2019-03-20', '财务1', 'CW943338', '300');
INSERT INTO `employeewage` VALUES ('2', '500', '2000', '2019-03-10', '客服01', 'KF936854', '500');
INSERT INTO `employeewage` VALUES ('5', '5000', '5000', '2019-03-20', '管理员', 'GL081838', '5000');
INSERT INTO `employeewage` VALUES ('6', '200', '2000', '2019-03-20', '客服1号', 'KF743008', '200');
INSERT INTO `employeewage` VALUES ('7', '300', '2500', '2019-03-20', '票据组1姐', 'PJ111879', '300');
INSERT INTO `employeewage` VALUES ('8', '3000', '2500', '2019-03-20', '测试随机id', 'PJ595668', '300');

-- ----------------------------
-- Table structure for extraclear
-- ----------------------------
DROP TABLE IF EXISTS `extraclear`;
CREATE TABLE `extraclear` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `balance_date` date DEFAULT NULL,
  `balance_money` double DEFAULT NULL,
  `balance_type` varchar(50) DEFAULT NULL,
  `remark` varchar(50) DEFAULT NULL,
  `subject_name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of extraclear
-- ----------------------------

-- ----------------------------
-- Table structure for extraincome
-- ----------------------------
DROP TABLE IF EXISTS `extraincome`;
CREATE TABLE `extraincome` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `income_month` varchar(255) DEFAULT NULL,
  `money` double NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `write_date` date DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='营业外收入';

-- ----------------------------
-- Records of extraincome
-- ----------------------------
INSERT INTO `extraincome` VALUES ('1', '2019-03', '500000', '政府补贴', '2019-03-10');

-- ----------------------------
-- Table structure for financefee
-- ----------------------------
DROP TABLE IF EXISTS `financefee`;
CREATE TABLE `financefee` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fee` double NOT NULL,
  `payout_month` varchar(255) DEFAULT NULL,
  `write_date` date DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of financefee
-- ----------------------------
INSERT INTO `financefee` VALUES ('1', '2000', '2019-03', '2019-03-20');

-- ----------------------------
-- Table structure for functionwithgroup
-- ----------------------------
DROP TABLE IF EXISTS `functionwithgroup`;
CREATE TABLE `functionwithgroup` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `function_id` int(11) NOT NULL,
  `group_id` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='权限功能和用户组关系表（多对多）';

-- ----------------------------
-- Records of functionwithgroup
-- ----------------------------
INSERT INTO `functionwithgroup` VALUES ('1', '1', '1');
INSERT INTO `functionwithgroup` VALUES ('2', '2', '1');
INSERT INTO `functionwithgroup` VALUES ('3', '3', '1');
INSERT INTO `functionwithgroup` VALUES ('4', '4', '1');
INSERT INTO `functionwithgroup` VALUES ('5', '5', '1');
INSERT INTO `functionwithgroup` VALUES ('6', '6', '1');
INSERT INTO `functionwithgroup` VALUES ('7', '7', '1');
INSERT INTO `functionwithgroup` VALUES ('8', '8', '1');
INSERT INTO `functionwithgroup` VALUES ('9', '9', '1');
INSERT INTO `functionwithgroup` VALUES ('10', '10', '1');
INSERT INTO `functionwithgroup` VALUES ('11', '11', '1');
INSERT INTO `functionwithgroup` VALUES ('12', '4', '7');
INSERT INTO `functionwithgroup` VALUES ('13', '5', '7');
INSERT INTO `functionwithgroup` VALUES ('14', '7', '4');
INSERT INTO `functionwithgroup` VALUES ('15', '7', '6');
INSERT INTO `functionwithgroup` VALUES ('16', '1', '2');
INSERT INTO `functionwithgroup` VALUES ('17', '2', '2');
INSERT INTO `functionwithgroup` VALUES ('18', '3', '2');
INSERT INTO `functionwithgroup` VALUES ('19', '7', '2');
INSERT INTO `functionwithgroup` VALUES ('20', '1', '7');
INSERT INTO `functionwithgroup` VALUES ('21', '2', '7');
INSERT INTO `functionwithgroup` VALUES ('22', '3', '7');
INSERT INTO `functionwithgroup` VALUES ('23', '7', '7');
INSERT INTO `functionwithgroup` VALUES ('24', '6', '3');
INSERT INTO `functionwithgroup` VALUES ('25', '9', '3');
INSERT INTO `functionwithgroup` VALUES ('26', '8', '5');
INSERT INTO `functionwithgroup` VALUES ('27', '9', '5');
INSERT INTO `functionwithgroup` VALUES ('28', '4', '6');

-- ----------------------------
-- Table structure for function_
-- ----------------------------
DROP TABLE IF EXISTS `function_`;
CREATE TABLE `function_` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `page_function` varchar(50) DEFAULT NULL,
  `page_name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='权限功能表';

-- ----------------------------
-- Records of function_
-- ----------------------------
INSERT INTO `function_` VALUES ('1', null, '票据管理');
INSERT INTO `function_` VALUES ('2', null, '接货管理');
INSERT INTO `function_` VALUES ('3', null, '配车管理');
INSERT INTO `function_` VALUES ('4', null, '到货管理');
INSERT INTO `function_` VALUES ('5', null, '中转管理');
INSERT INTO `function_` VALUES ('6', null, '结算管理');
INSERT INTO `function_` VALUES ('7', null, '客户服务');
INSERT INTO `function_` VALUES ('8', null, '监控分析');
INSERT INTO `function_` VALUES ('9', null, '成本核算');
INSERT INTO `function_` VALUES ('10', null, '应用管理');
INSERT INTO `function_` VALUES ('11', null, '系统管理');

-- ----------------------------
-- Table structure for goodsbill
-- ----------------------------
DROP TABLE IF EXISTS `goodsbill`;
CREATE TABLE `goodsbill` (
  `goods_bill_code` varchar(50) NOT NULL,
  `accept_procedure_rate` varchar(50) DEFAULT NULL,
  `accept_station` varchar(100) DEFAULT NULL,
  `carriage` double DEFAULT NULL,
  `carry_goods_fee` double DEFAULT NULL,
  `employee_code` varchar(50) DEFAULT NULL,
  `fact_deal_date` date DEFAULT NULL,
  `fetch_goods_mode` varchar(50) DEFAULT NULL,
  `help_accept_payment` double DEFAULT NULL,
  `if_audit` bit(1) DEFAULT NULL,
  `if_settle_accounts` bit(1) DEFAULT NULL,
  `insurance` double DEFAULT NULL,
  `money_of_change_pay` double DEFAULT NULL,
  `pay_kickback` double DEFAULT NULL,
  `pay_mode` varchar(50) DEFAULT NULL,
  `predelivery_date` date DEFAULT NULL,
  `receive_goods_addr` varchar(50) DEFAULT NULL,
  `receive_goods_customer` varchar(50) DEFAULT NULL,
  `receive_goods_customer_addr` varchar(50) DEFAULT NULL,
  `receive_goods_customer_code` varchar(50) DEFAULT NULL,
  `receive_goods_customer_tel` varchar(50) DEFAULT NULL,
  `reduce_fund` double DEFAULT NULL,
  `remark` varchar(50) DEFAULT NULL,
  `send_goods_addr` varchar(50) DEFAULT NULL,
  `send_goods_customer` varchar(50) DEFAULT NULL,
  `send_goods_customer_addr` varchar(50) DEFAULT NULL,
  `send_goods_customer_no` varchar(50) DEFAULT NULL,
  `send_goods_customer_tel` varchar(50) DEFAULT NULL,
  `send_goods_date` date DEFAULT NULL,
  `transfer_fee` double DEFAULT NULL,
  `transfer_station` varchar(50) DEFAULT NULL,
  `validity` bit(1) DEFAULT NULL,
  `write_bill_person` varchar(50) DEFAULT NULL,
  `write_date` date DEFAULT NULL,
  PRIMARY KEY (`goods_bill_code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='货运单主表';

-- ----------------------------
-- Records of goodsbill
-- ----------------------------
INSERT INTO `goodsbill` VALUES ('HY050568', '1.5', '杭州', '2000', '20', 'GL846489', '2019-03-21', '实地', '2000', '', '\0', '0', '0', '0', '现金', '2019-03-17', '上海', '上海第一公司', '上海', 'KH823675', '15893630801', '20', '00', '杭州', '杭州陈1科技公司', '杭州', 'KH385751', '15893630801', '2019-03-17', '0', '', '', 'GL846489', '2019-03-17');
INSERT INTO `goodsbill` VALUES ('HY282907', '1.2', '杭州', '2000', '0', 'GL846489', '2019-03-10', '实地', '2000', '\0', '\0', '0', '0', '0', '现金', '2019-03-10', '上海', '上海陈2科技公司', '上海', 'KH693381', '15893630802', '0', '0', '杭州', '杭州陈1科技公司', '杭州', 'KH385751', '15893630801', '2019-03-10', '1.3', '苏州', '\0', 'GL846489', '2019-03-10');
INSERT INTO `goodsbill` VALUES ('HY602419', '1.5', '上海', '0', '0', 'GL846489', '2019-03-10', '实地', '2000', '', '\0', '0', '0', '0', '现金', '2019-03-10', '杭州', '杭州陈1科技公司', '杭州', 'KH385751', '15893630801', '0', '第三次测试 司机验货之后，客户可以填写回告单', '上海', '上海陈2科技公司', '上海', 'KH693381', '15893630802', '2019-03-10', '1.3', '', '', 'GL846489', '2019-03-10');
INSERT INTO `goodsbill` VALUES ('HY669044', '1.5', '杭州', '2000', '200', 'GL846489', '2019-03-10', '实地', '2000', '', '\0', '200', '0', '200', '现金', '2019-03-13', '上海', '上海陈2科技公司', '上海', 'KH693381', '15893630802', '200', '第一个客户', '杭州', '杭州陈1科技公司', '杭州', 'KH385751', '15893630801', '2019-03-10', '1.3', '', '', 'GL846489', '2019-03-10');
INSERT INTO `goodsbill` VALUES ('HY716876', '0', '00', '0', '0', 'GL846489', '2019-03-22', '00', '2000', '\0', '\0', '0', '0', '0', '00', '2019-03-22', '上海', '上海第一公司', '上海', 'KH823675', '15893630801', '0', '0', '杭州', '杭州陈1科技公司', '杭州', 'KH385751', '15893630801', '2019-03-22', '0', '无锡', '\0', 'GL846489', '2019-03-22');
INSERT INTO `goodsbill` VALUES ('HY770674', '1.5', '杭州', '500', '0', 'PJ111879', '2019-03-21', '实地', '5000', '', '\0', '0', '0', '0', '现金', '2019-03-10', '上海', '上海陈2科技公司', '上海', 'KH693381', '15893630802', '0', '填写一个货运单', '杭州', '杭州陈1科技公司', '杭州', 'KH385751', '15893630801', '2019-03-10', '1.3', '', '', 'PJ111879', '2019-03-10');
INSERT INTO `goodsbill` VALUES ('HY812216', '20', '20', '2', '2', 'GL846489', '2019-03-17', '2', '20', '', '\0', '2', '0', '0', '20', '2019-03-17', '上海', '上海第一公司', '上海', 'KH823675', '15893630801', '2', '0', '杭州', '杭州陈1科技公司', '杭州', 'KH385751', '15893630801', '2019-03-17', '0', '', '', 'GL846489', '2019-03-17');
INSERT INTO `goodsbill` VALUES ('HY842372', '1.5', '上海', '2000', '0', 'GL846489', '2019-03-19', '实地', '200', '', '\0', '0', '0', '0', '现金', '2019-03-20', '杭州', '杭州陈1科技公司', '杭州', 'KH385751', '15893630801', '0', '0', '上海', '上海第一公司', '上海', 'KH823675', '15893630801', '2019-03-17', '1.3', '', '', 'GL846489', '2019-03-17');
INSERT INTO `goodsbill` VALUES ('HY910010', '1.5', '上海', '20000', '200', 'GL846489', '2019-03-22', '实地', '2000', '\0', '\0', '500', '0', '0', '现金', '2019-03-27', '杭州中南国际', '杭州陈1科技公司', '杭州', 'KH385751', '15893630801', '200', '测试功能流程', '上海徐家汇', '上海第一公司', '上海', 'KH823675', '15893630801', '2019-03-21', '0', '常州，镇江，无锡', '\0', 'GL846489', '2019-03-21');

-- ----------------------------
-- Table structure for goodsbillevent
-- ----------------------------
DROP TABLE IF EXISTS `goodsbillevent`;
CREATE TABLE `goodsbillevent` (
  `goods_bill_id` varchar(50) NOT NULL,
  `event_name` varchar(50) NOT NULL,
  `occur_time` datetime(6) DEFAULT NULL,
  `remark` varchar(50) NOT NULL,
  PRIMARY KEY (`goods_bill_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='查询接货单 使用（货运单事件表）';

-- ----------------------------
-- Records of goodsbillevent
-- ----------------------------
INSERT INTO `goodsbillevent` VALUES ('HY050568', '已结运单', '2019-03-21 00:22:34.695000', '单据已填');
INSERT INTO `goodsbillevent` VALUES ('HY282907', '已结运单', '2019-03-10 17:59:13.792000', '单据已填');
INSERT INTO `goodsbillevent` VALUES ('HY602419', '已结运单', '2019-03-10 18:39:34.782000', '单据已填');
INSERT INTO `goodsbillevent` VALUES ('HY669044', '已结运单', '2019-03-10 16:57:35.550000', '单据已填');
INSERT INTO `goodsbillevent` VALUES ('HY716876', '已结运单', '2019-03-22 00:49:35.159000', '单据已填');
INSERT INTO `goodsbillevent` VALUES ('HY770674', '已结运单', '2019-03-21 00:22:38.925000', '单据已填');
INSERT INTO `goodsbillevent` VALUES ('HY812216', '已结运单', '2019-03-17 19:26:40.638000', '单据已填');
INSERT INTO `goodsbillevent` VALUES ('HY842372', '已结运单', '2019-03-19 22:07:57.653000', '单据已填');
INSERT INTO `goodsbillevent` VALUES ('HY910010', '已结运单', '2019-03-22 00:14:45.470000', '单据已填');

-- ----------------------------
-- Table structure for goodsreceiptinfo
-- ----------------------------
DROP TABLE IF EXISTS `goodsreceiptinfo`;
CREATE TABLE `goodsreceiptinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `check_goods_record` varchar(50) DEFAULT NULL,
  `driver_name` varchar(50) DEFAULT NULL,
  `goods_revert_code` varchar(50) DEFAULT NULL,
  `rceive_goods_date` date DEFAULT NULL,
  `receive_goods_person` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='货物回执信息表';

-- ----------------------------
-- Records of goodsreceiptinfo
-- ----------------------------
INSERT INTO `goodsreceiptinfo` VALUES ('1', '司机验收成功', '张三', 'HZ171436', '2019-03-10', '上海陈2科技公司');
INSERT INTO `goodsreceiptinfo` VALUES ('2', '司机确认到达目的地-上海', '张三', 'HZ742133', '2019-03-10', '上海陈2科技公司');
INSERT INTO `goodsreceiptinfo` VALUES ('3', '货物已到', '李四', 'HZ831013', '2019-03-10', '杭州陈1科技公司');
INSERT INTO `goodsreceiptinfo` VALUES ('4', '安全到货', '张三', 'HZ738738', '2019-03-17', '上海第一公司');
INSERT INTO `goodsreceiptinfo` VALUES ('5', '安全到货', '李四', 'HZ128709', '2019-03-19', '杭州陈1科技公司');
INSERT INTO `goodsreceiptinfo` VALUES ('6', '安全到货', '张三', 'HZ421544', '2019-03-21', '上海第一公司');
INSERT INTO `goodsreceiptinfo` VALUES ('7', '安全到货', '张三', 'HZ528643', '2019-03-21', '上海陈2科技公司');
INSERT INTO `goodsreceiptinfo` VALUES ('8', '安全到货', '李四', 'HZ563217', '2019-03-22', '杭州陈1科技公司');
INSERT INTO `goodsreceiptinfo` VALUES ('9', '安全到货', '张三', 'HZ333632', '2019-03-22', '上海第一公司');

-- ----------------------------
-- Table structure for incomemonthlytemp
-- ----------------------------
DROP TABLE IF EXISTS `incomemonthlytemp`;
CREATE TABLE `incomemonthlytemp` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `biz_fee` double NOT NULL COMMENT '支出',
  `biz_income` double NOT NULL COMMENT '营业收入',
  `car_carriage` double NOT NULL COMMENT '车运费',
  `carriage_money` double NOT NULL COMMENT '运费金额',
  `convey_wage` double NOT NULL COMMENT '工资',
  `finance_fee` double NOT NULL COMMENT '财务费用',
  `house_rent` double NOT NULL COMMENT '房租费',
  `income` double NOT NULL COMMENT '收入',
  `insurance_money` double NOT NULL COMMENT '保险金',
  `manage_fee` double NOT NULL COMMENT '管理费用',
  `month` varchar(255) NOT NULL COMMENT '月份',
  `office_fee` double NOT NULL COMMENT '办公费',
  `other` double NOT NULL COMMENT '其他',
  `payout` double NOT NULL,
  `phone_fee` double NOT NULL COMMENT '电话费',
  `profit` double NOT NULL COMMENT '利润',
  `unbiz_income` double NOT NULL COMMENT '非营业收入',
  `wage` double NOT NULL COMMENT '工资',
  `water_elec_fee` double NOT NULL COMMENT '水电费',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='成本核算-成本报表（损益月报临时统计表）';

-- ----------------------------
-- Records of incomemonthlytemp
-- ----------------------------
INSERT INTO `incomemonthlytemp` VALUES ('1', '500', '13412', '500', '13011', '0', '2000', '10000', '513412', '401', '20000', '2019-03', '5000', '2000', '58100', '1000', '455312', '500000', '32600', '2000');

-- ----------------------------
-- Table structure for lineoverall
-- ----------------------------
DROP TABLE IF EXISTS `lineoverall`;
CREATE TABLE `lineoverall` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `all_carriage_total` double NOT NULL,
  `deal_goods_station` varchar(50) DEFAULT NULL,
  `insurance_total` double NOT NULL,
  `load_station` varchar(50) DEFAULT NULL,
  `times` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='统计报表-专线整体';

-- ----------------------------
-- Records of lineoverall
-- ----------------------------
INSERT INTO `lineoverall` VALUES ('1', '12000', '上海', '220', '杭州', '6');
INSERT INTO `lineoverall` VALUES ('2', '3000', '杭州', '0', '上海', '3');

-- ----------------------------
-- Table structure for managefee
-- ----------------------------
DROP TABLE IF EXISTS `managefee`;
CREATE TABLE `managefee` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `house_rent` double NOT NULL,
  `office_fee` double NOT NULL,
  `other_payout` double NOT NULL,
  `payout_month` varchar(255) DEFAULT NULL,
  `phone_fee` double NOT NULL,
  `water_elec_fee` double NOT NULL,
  `write_date` date DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of managefee
-- ----------------------------
INSERT INTO `managefee` VALUES ('1', '10000', '5000', '2000', '2019-03', '1000', '2000', '2019-03-21');

-- ----------------------------
-- Table structure for proxyfeeclear
-- ----------------------------
DROP TABLE IF EXISTS `proxyfeeclear`;
CREATE TABLE `proxyfeeclear` (
  `goods_bill_code` varchar(50) NOT NULL,
  `account_receivable` double DEFAULT NULL,
  `balance_date` date DEFAULT NULL,
  `commision_rate` float DEFAULT NULL,
  `commision_receivable` double DEFAULT NULL,
  `customer_code` varchar(50) DEFAULT NULL,
  `fact_receive_fund` double DEFAULT NULL,
  `goods_pay_change` double DEFAULT NULL,
  `received_commision` double DEFAULT NULL,
  PRIMARY KEY (`goods_bill_code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='代收费结算表';

-- ----------------------------
-- Records of proxyfeeclear
-- ----------------------------
INSERT INTO `proxyfeeclear` VALUES ('HY050568', '0', null, '1', '0', 'KH385751', '2000', '2000', '0');
INSERT INTO `proxyfeeclear` VALUES ('HY282907', '0', null, '0.02', '0', 'KH385751', '2000', '-2000', '0');
INSERT INTO `proxyfeeclear` VALUES ('HY602419', '0', '2019-03-10', '0.02', '39.99999910593033', 'KH693381', '2000', '-2000', '0');
INSERT INTO `proxyfeeclear` VALUES ('HY669044', '0', null, '0.02', '0', 'KH385751', '2000', '-2000', '0');
INSERT INTO `proxyfeeclear` VALUES ('HY716876', '0', '2019-03-22', '1', '0', 'KH385751', '2000', '2000', '2000');
INSERT INTO `proxyfeeclear` VALUES ('HY770674', '0', null, '0.02', '0', 'KH385751', '5000', '-5000', '0');
INSERT INTO `proxyfeeclear` VALUES ('HY812216', '0', null, '0.02', '0', 'KH385751', '20', '-20', '0');
INSERT INTO `proxyfeeclear` VALUES ('HY842372', '0', null, '0.02', '0', 'KH823675', '200', '-200', '0');
INSERT INTO `proxyfeeclear` VALUES ('HY910010', '0', '2019-03-22', '0.02', '39.99999910593033', 'KH823675', '2000', '-2000', '0');

-- ----------------------------
-- Table structure for region
-- ----------------------------
DROP TABLE IF EXISTS `region`;
CREATE TABLE `region` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `city` varchar(50) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='城市表';

-- ----------------------------
-- Records of region
-- ----------------------------
INSERT INTO `region` VALUES ('1', '南京');
INSERT INTO `region` VALUES ('2', '常州');
INSERT INTO `region` VALUES ('3', '镇江');
INSERT INTO `region` VALUES ('4', '无锡');
INSERT INTO `region` VALUES ('5', '苏州');
INSERT INTO `region` VALUES ('6', '上海');
INSERT INTO `region` VALUES ('7', '杭州');

-- ----------------------------
-- Table structure for routeinfo
-- ----------------------------
DROP TABLE IF EXISTS `routeinfo`;
CREATE TABLE `routeinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `distance` double NOT NULL,
  `end_station` int(11) DEFAULT NULL,
  `fetch_time` double NOT NULL,
  `pass_station` varchar(100) DEFAULT NULL,
  `start_station` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='线路信息表';

-- ----------------------------
-- Records of routeinfo
-- ----------------------------
INSERT INTO `routeinfo` VALUES ('1', '100', '5', '1', '', '6');
INSERT INTO `routeinfo` VALUES ('2', '200', '4', '2', '7', '6');
INSERT INTO `routeinfo` VALUES ('3', '100', '7', '1', '', '6');
INSERT INTO `routeinfo` VALUES ('4', '100', '4', '1', '', '6');
INSERT INTO `routeinfo` VALUES ('5', '100', '2', '1', '', '6');
INSERT INTO `routeinfo` VALUES ('6', '100', '4', '1', '', '7');
INSERT INTO `routeinfo` VALUES ('7', '200', '5', '2', '6', '7');
INSERT INTO `routeinfo` VALUES ('8', '200', '4', '2', '6', '7');
INSERT INTO `routeinfo` VALUES ('9', '200', '2', '2', '6', '7');
INSERT INTO `routeinfo` VALUES ('10', '100', '6', '1', '', '7');

-- ----------------------------
-- Table structure for sentlist
-- ----------------------------
DROP TABLE IF EXISTS `sentlist`;
CREATE TABLE `sentlist` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `back_cost` double NOT NULL,
  `car_card_no` varchar(50) DEFAULT NULL,
  `cash_pay` double NOT NULL,
  `driver_name` varchar(50) DEFAULT NULL,
  `goods_code` varchar(50) DEFAULT NULL,
  `goods_name` varchar(50) DEFAULT NULL,
  `goods_revert_bill_code` varchar(50) DEFAULT NULL,
  `help_accept_fund` varchar(50) DEFAULT NULL,
  `mobile` varchar(50) DEFAULT NULL,
  `pickup_pay` double NOT NULL,
  `piece_amount` int(11) NOT NULL,
  `remark` varchar(50) DEFAULT NULL,
  `send_goods_customer` varchar(50) DEFAULT NULL,
  `send_goods_customer_tel` varchar(50) DEFAULT NULL,
  `transfer_destination` varchar(50) DEFAULT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of sentlist
-- ----------------------------

-- ----------------------------
-- Table structure for transfercominfo
-- ----------------------------
DROP TABLE IF EXISTS `transfercominfo`;
CREATE TABLE `transfercominfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `city` varchar(50) DEFAULT NULL,
  `company_name` varchar(50) DEFAULT NULL,
  `detail_address` varchar(200) DEFAULT NULL,
  `link_phone` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='中转地公司信息';

-- ----------------------------
-- Records of transfercominfo
-- ----------------------------
INSERT INTO `transfercominfo` VALUES ('1', '苏州', '苏州第一公司', '苏州', '15893630801');
INSERT INTO `transfercominfo` VALUES ('2', '上海', '上海第二公司', '上海徐家汇104号', '15893630801');
INSERT INTO `transfercominfo` VALUES ('3', '无锡', '无锡第一公司', '无锡江干区', '15893630801');
INSERT INTO `transfercominfo` VALUES ('4', '常州', '常州第二公司', '松江区', '15893630801');

-- ----------------------------
-- Table structure for transferinfo
-- ----------------------------
DROP TABLE IF EXISTS `transferinfo`;
CREATE TABLE `transferinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `after_transfer_bill` varchar(50) DEFAULT NULL,
  `check_time` date DEFAULT NULL,
  `description` varchar(50) DEFAULT NULL,
  `goods_bill_code` varchar(50) DEFAULT NULL,
  `transfer_addr` varchar(50) DEFAULT NULL,
  `transfer_check` varchar(50) DEFAULT NULL,
  `transfer_company` varchar(50) DEFAULT NULL,
  `transfer_fee` double DEFAULT NULL,
  `transfer_station` varchar(50) DEFAULT NULL,
  `transfer_station_tel` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='中转信息表';

-- ----------------------------
-- Records of transferinfo
-- ----------------------------
INSERT INTO `transferinfo` VALUES ('1', 'HY282907', '2019-03-10', '现在到达中转目的地，苏州，请大家放心，一切正常', 'HY282907', '苏州', 'SJ065481', '苏州第一公司', '1.3', '苏州', '15893630801');
INSERT INTO `transferinfo` VALUES ('2', 'HY910010', '2019-03-22', '11', 'HY910010', '松江区', 'SJ159922', '常州第二公司', '1.3', '常州', '15893630801');
INSERT INTO `transferinfo` VALUES ('3', 'HY716876', '2019-03-22', '11', 'HY716876', '无锡江干区', 'SJ065481', '无锡第一公司', '1.3', '无锡', '15893630801');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `login_id` varchar(50) NOT NULL,
  `if_online` bit(1) DEFAULT b'0',
  `password` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`login_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='系统用户表';

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('CW943338', '', 'E10ADC3949BA59ABBE56E057F20F883E');
INSERT INTO `user` VALUES ('GL081838', '\0', 'E10ADC3949BA59ABBE56E057F20F883E');
INSERT INTO `user` VALUES ('GL846489', '', '81DC9BDB52D04DC20036DBD8313ED055');
INSERT INTO `user` VALUES ('KF743008', '', 'E10ADC3949BA59ABBE56E057F20F883E');
INSERT INTO `user` VALUES ('KH385751', '', 'E10ADC3949BA59ABBE56E057F20F883E');
INSERT INTO `user` VALUES ('KH823675', '', 'E10ADC3949BA59ABBE56E057F20F883E');
INSERT INTO `user` VALUES ('PJ111879', '', 'E10ADC3949BA59ABBE56E057F20F883E');
INSERT INTO `user` VALUES ('PJ595668', '\0', 'E10ADC3949BA59ABBE56E057F20F883E');
INSERT INTO `user` VALUES ('SJ065481', '', 'E10ADC3949BA59ABBE56E057F20F883E');
INSERT INTO `user` VALUES ('SJ159922', '', 'E10ADC3949BA59ABBE56E057F20F883E');

-- ----------------------------
-- Table structure for usergroup
-- ----------------------------
DROP TABLE IF EXISTS `usergroup`;
CREATE TABLE `usergroup` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` text,
  `group_name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户组类别表';

-- ----------------------------
-- Records of usergroup
-- ----------------------------
INSERT INTO `usergroup` VALUES ('1', '具有最高权限', '管理组');
INSERT INTO `usergroup` VALUES ('2', '接货管理：填写一份货运单合同\n配车管理：填写一份运输单\n票据管理：将某运单分配给某个司机', '票据组');
INSERT INTO `usergroup` VALUES ('3', '结算管理', '财务组');
INSERT INTO `usergroup` VALUES ('4', '对客户的投诉情况进行管理以及提货回告、到货回告、已提回告、中转回告、代收回告', '客服组');
INSERT INTO `usergroup` VALUES ('5', '对货运单监控和整体运营状况进行分析', '监控组');
INSERT INTO `usergroup` VALUES ('6', '客户服务，添加回告', '客户组');
INSERT INTO `usergroup` VALUES ('7', '到货管理，验收货物\n中转管理：修改中转状态\n客户服务，中转回告', '司机组');

-- ----------------------------
-- Table structure for userwithgroup
-- ----------------------------
DROP TABLE IF EXISTS `userwithgroup`;
CREATE TABLE `userwithgroup` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `group_id` int(11) NOT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户和用户组关系表（多对多）';

-- ----------------------------
-- Records of userwithgroup
-- ----------------------------
INSERT INTO `userwithgroup` VALUES ('1', '1', 'GL846489');
INSERT INTO `userwithgroup` VALUES ('2', '7', 'SJ065481');
INSERT INTO `userwithgroup` VALUES ('3', '7', 'SJ159922');
INSERT INTO `userwithgroup` VALUES ('4', '6', 'KH385751');
INSERT INTO `userwithgroup` VALUES ('7', '3', 'CW943338');
INSERT INTO `userwithgroup` VALUES ('9', '2', 'PJ111879');
INSERT INTO `userwithgroup` VALUES ('10', '1', 'GL081838');
INSERT INTO `userwithgroup` VALUES ('11', '2', 'PJ595668');
INSERT INTO `userwithgroup` VALUES ('12', '6', 'KH823675');
INSERT INTO `userwithgroup` VALUES ('13', '4', 'KF743008');
