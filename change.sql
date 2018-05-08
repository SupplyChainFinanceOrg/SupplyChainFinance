
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