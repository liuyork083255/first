-- 说明：目前线上 release 版本只要执行下面 sql 就可以保持最新
-- 修改发行量字段
ALTER TABLE `wf_web_ncd`.`quotes`
	CHANGE `m1_volume` `m1_volume` VARCHAR(16) NOT NULL DEFAULT '' COMMENT '期限为1M的发行数量',
	CHANGE `m3_volume` `m3_volume` VARCHAR(16) NOT NULL DEFAULT '' COMMENT '期限为3M的发行数量',
	CHANGE `m6_volume` `m6_volume` VARCHAR(16) NOT NULL DEFAULT '' COMMENT '期限为6M的发行数量',
	CHANGE `m9_volume` `m9_volume` VARCHAR(16) NOT NULL DEFAULT '' COMMENT '期限为9M的发行数量',
	CHANGE `y1_volume` `y1_volume` VARCHAR(16) NOT NULL DEFAULT '' COMMENT '期限为1Y的发行数量';

ALTER TABLE `wf_web_ncd`.`quote_details`
	CHANGE `planed_issue_amount` `planed_issue_amount` VARCHAR(16) NOT NULL DEFAULT '' COMMENT '发行数量';

-- 删除表 orders 字段
ALTER TABLE `wf_web_ncd`.`orders` DROP COLUMN `user_name`, DROP COLUMN `customer_org_name`;

-- 新建 user_contact 表
CREATE TABLE `user_contact` (
  `auto_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增序列',
  `user_id` varchar(32) NOT NULL DEFAULT '' COMMENT '用户user id',
  `qq` varchar(20) NOT NULL DEFAULT '' COMMENT '用户 QQ',
  `mobile` varchar(20) NOT NULL DEFAULT '' COMMENT '用户移动电话',
  `telephone` varchar(20) NOT NULL DEFAULT '' COMMENT '用户固定电话',
  `company_code` varchar(1) NOT NULL DEFAULT '' COMMENT '用户权限 brokerId',
  `user_name` varchar(15) NOT NULL DEFAULT '' COMMENT '用户名称',
  `customer_org_name` varchar(25) NOT NULL DEFAULT '' COMMENT '用户所属机构',
  PRIMARY KEY (`auto_id`),
  UNIQUE KEY `index_userid` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4