
-------------------------申请表与附件表的中间表-------------------------------------
CREATE TABLE `tb_att_apply` (
  `id` varchar(64) NOT NULL,
  `apply_id` varchar(64) DEFAULT NULL COMMENT '申请表',
  `attachent_id` varchar(64) DEFAULT NULL COMMENT '附件',
  `path` varchar(250) DEFAULT NULL COMMENT '附件位置',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

---------------------------------------------------------------------------

-----------------------字段-----------------------------------------------
ALTER TABLE `tb_comp`
CHANGE COLUMN `employees
_count` `employees_count`  int(20) NULL DEFAULT NULL COMMENT '员工数量' AFTER `comp_email`
---------------------------------------------------------------------
------------------------------------
1.录入企业类型、性质等
2.录入企业的状态
------------------------------------


DROP TABLE IF EXISTS `tb_state`;
CREATE TABLE `tb_state` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` int(2) DEFAULT NULL COMMENT '0注册 1流程',
  `product_id` int(11) DEFAULT '1' COMMENT '产品id  注册不写',
  `state_name` varchar(50) DEFAULT NULL COMMENT '对应状态',
  `operation_name` varchar(50) DEFAULT NULL COMMENT '操作名称',
  `nowstatus` varchar(10) DEFAULT NULL COMMENT '状态',
  `rolecode` varchar(20) DEFAULT NULL COMMENT '角色',
  `isstop` varchar(20) DEFAULT '0' COMMENT '0启用  1停用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_state
-- ----------------------------
INSERT INTO `tb_state` VALUES ('1', '0', '1', '待审核', '申请注册', '0', 'kf', '0');
INSERT INTO `tb_state` VALUES ('2', '0', '1', '通过', '审核', '1', 'kf', '0');
INSERT INTO `tb_state` VALUES ('3', '0', '1', '放弃', '', '2', '', '1');
INSERT INTO `tb_state` VALUES ('4', '0', '1', '补全资料', '补全', '3', 'kf', '0');
INSERT INTO `tb_state` VALUES ('5', '0', '1', '待审核', '审核', '4', 'kf', '0');
INSERT INTO `tb_state` VALUES ('6', '1', '1', '待审核', '申请借款', null, null, '0');
INSERT INTO `tb_state` VALUES ('7', '1', '1', '待确认授信金额', '审核', null, null, '0');
INSERT INTO `tb_state` VALUES ('8', '1', '1', '申请退回', '审核', null, null, '0');
INSERT INTO `tb_state` VALUES ('9', '1', '1', '放弃', '审核', null, null, '0');
INSERT INTO `tb_state` VALUES ('10', '1', '1', '确认授信金额', '确认', null, null, '0');
INSERT INTO `tb_state` VALUES ('11', '1', '1', '待调查', '复审', null, null, '0');
INSERT INTO `tb_state` VALUES ('12', '1', '1', '已调查', '调查', null, null, '0');
INSERT INTO `tb_state` VALUES ('13', '1', '1', '待总经理审批', '复审', null, null, '0');
INSERT INTO `tb_state` VALUES ('14', '1', '1', '总经理退回', '总经理审核', null, null, '0');
INSERT INTO `tb_state` VALUES ('15', '1', '1', '批准借款', '总经理审批', null, null, '0');
INSERT INTO `tb_state` VALUES ('16', '1', '1', '待金融机构审核', '复审', null, null, '0');
INSERT INTO `tb_state` VALUES ('17', '1', '1', '金融机构已审核', '金融机构审核', null, null, '0');
INSERT INTO `tb_state` VALUES ('18', '1', '1', '金融机构退回', '金融机构审核', null, null, '0');
INSERT INTO `tb_state` VALUES ('19', '1', '1', '待签署合同', '复审', null, null, '0');
INSERT INTO `tb_state` VALUES ('20', '1', '1', '合同已签署', '借款人签署', null, null, '0');
INSERT INTO `tb_state` VALUES ('21', '1', '1', '待核心企业确权', '复审', null, null, '0');
INSERT INTO `tb_state` VALUES ('22', '1', '1', '核心企业已确权', '核心企业确权', null, null, '0');
INSERT INTO `tb_state` VALUES ('23', '1', '1', '待放款', '复审', null, null, '0');
INSERT INTO `tb_state` VALUES ('24', '1', '1', '已放款', '申请机构放款', null, null, '0');
INSERT INTO `tb_state` VALUES ('25', '0', '1', '待审核', '申请注册', '0', 'jkqy', '0');
INSERT INTO `tb_state` VALUES ('26', '0', '1', '通过', '审核', '1', 'jkqy', '0');
INSERT INTO `tb_state` VALUES ('27', '0', '1', '放弃', '', '2', '', '1');
INSERT INTO `tb_state` VALUES ('28', '0', '1', '补全资料', '补全', '3', 'jkqy', '0');
INSERT INTO `tb_state` VALUES ('29', '0', '1', '待审核', '审核', '4', 'jkqy', '0');
-----------------------------------

CREATE TABLE `tb_state_role_button` (
  `id` varchar(64) NOT NULL,
  `type` varchar(64) DEFAULT NULL COMMENT '流程类型',
  `rolecode` varchar(64) DEFAULT NULL COMMENT '角色',
  `nowstatus` varchar(64) DEFAULT NULL COMMENT '当前状态',
  `isstop` varchar(1) DEFAULT '0' COMMENT '0可用 1不可用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

------------------------------------------------------

ALTER TABLE `jeesite`.`tb_contract_sign` ADD COLUMN `upload_pdfpath` VARCHAR(255) NULL COMMENT '合同模板pdf路径' AFTER `contract_content`; 
ALTER TABLE `jeesite`.`tb_contract_sign` ADD COLUMN `operation_time` DATETIME NULL COMMENT '操作时间' AFTER `upload_pdfpath`; 
ALTER TABLE `jeesite`.`tb_sgin_contract` ADD COLUMN `field_name` VARCHAR(200) NULL COMMENT '字段名称' AFTER `contract_id`;

ALTER TABLE `jeesite`.`tb_contract_sign` ADD COLUMN `short_name` VARCHAR(50) NULL COMMENT '简称' AFTER `state`; 
ALTER TABLE `jeesite`.`tb_sgin_contract` ADD COLUMN `state` INT(11) NULL COMMENT '借款状态' AFTER `field_code`; 
ALTER TABLE `jeesite`.`tb_contract_sign` ADD COLUMN `state` INT(11) NULL COMMENT '状态' AFTER `operation_time`; 
ALTER TABLE `jeesite`.`tb_sgin_contract` ADD COLUMN `field_code` VARCHAR(100) NULL COMMENT '字段代码' AFTER `field_name`; 
CREATE TABLE `jeesite`.`tb_counter`( `id` INT(11) NOT NULL AUTO_INCREMENT, `name` VARCHAR(20) COMMENT '名称', `value` VARCHAR(20) COMMENT '值', PRIMARY KEY (`id`) ); 
ALTER TABLE `jeesite`.`tb_counter` ADD COLUMN `code` VARCHAR(20) NULL COMMENT '编号' AFTER `value`; 
ALTER TABLE `jeesite`.`tb_loan_apply` ADD COLUMN `contarct_code` VARCHAR(50) NULL COMMENT '购销合同编号（应收账款）' AFTER `core_sys_pwd`, ADD COLUMN `contact_amount` VARCHAR(50) NULL COMMENT '账款金额' AFTER `contarct_code`; 
ALTER TABLE `jeesite`.`tb_loan_apply` ADD COLUMN `urge_date` VARCHAR(50) NULL COMMENT '催账日期' AFTER `contact_amount`; 
ALTER TABLE `jeesite`.`tb_loan_apply` CHANGE `urge_date` `urge_dateq` VARCHAR(50) CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '应收账款起', ADD COLUMN `urge_datez` VARCHAR(50) NULL COMMENT '应收账款止' AFTER `urge_dateq`; 
ALTER TABLE `jeesite`.`tb_loan_apply` CHANGE `urge_dateq` `urge_date_start` VARCHAR(50) CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '应收账款起', CHANGE `urge_datez` `urge_date_end` VARCHAR(50) CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '应收账款止'; 
ALTER TABLE `jeesite`.`tb_loan_apply` ADD COLUMN `money_collecting_account` VARCHAR(50) NULL COMMENT '资金回笼账号' AFTER `urge_date_end`, ADD COLUMN `money_collecting_bank` VARCHAR(100) NULL COMMENT '资金回笼开户行' AFTER `money_collecting_account`, ADD COLUMN `money_collecting_name` VARCHAR(20) NULL COMMENT '资金回笼用户名' AFTER `money_collecting_bank`; 

ALTER TABLE `jeesite`.`tb_sgin_contract` ADD COLUMN `field_code` VARCHAR(100) NULL COMMENT '字段代码' AFTER `field_name`; 

