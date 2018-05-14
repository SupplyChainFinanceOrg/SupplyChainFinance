
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
