
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

/*[下午 4:57:22][5274 ms]*/ ALTER TABLE `jeesite`.`tb_contract_sign` ADD COLUMN `short_name` VARCHAR(50) NULL COMMENT '简称' AFTER `state`; 
ALTER TABLE `jeesite`.`tb_sgin_contract` ADD COLUMN `state` INT(11) NULL COMMENT '借款状态' AFTER `field_code`; 
ALTER TABLE `jeesite`.`tb_contract_sign` ADD COLUMN `state` INT(11) NULL COMMENT '状态' AFTER `operation_time`; 
ALTER TABLE `jeesite`.`tb_sgin_contract` ADD COLUMN `field_code` VARCHAR(100) NULL COMMENT '字段代码' AFTER `field_name`; 