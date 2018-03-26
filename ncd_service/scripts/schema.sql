CREATE DATABASE /*!32312 IF NOT EXISTS*/`wf_web_ncd_test` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
USE wf_web_ncd_test;

/*Table structure for table `broker_admin` */

DROP TABLE IF EXISTS `broker_admin`;

CREATE TABLE `broker_admin` (
  `auto_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增序列',
  `user_id` varchar(32) DEFAULT '' COMMENT '用户id',
  `broker_id` varchar(32) DEFAULT '' COMMENT '经济商id',
  PRIMARY KEY (`auto_id`),
  KEY `index_userId` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Table structure for table `favourites` */

DROP TABLE IF EXISTS `favourites`;

CREATE TABLE `favourites` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增序列',
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户ID',
  `issuer_id` varchar(7) DEFAULT NULL COMMENT '机构ID',
  PRIMARY KEY (`id`),
  KEY `index_userId` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Table structure for table `issuers` */

DROP TABLE IF EXISTS `issuers`;

CREATE TABLE `issuers` (
  `id` varchar(32) NOT NULL DEFAULT '' COMMENT '机构ID',
  `full_name` varchar(60) DEFAULT NULL COMMENT '机构全称',
  `short_name` varchar(60) DEFAULT NULL COMMENT '机构简称',
  `first_level_order` varchar(4) DEFAULT NULL COMMENT '评级排序',
  `second_level_order` varchar(7) DEFAULT NULL COMMENT '机构code排序',
  `total_asset` decimal(16,4) DEFAULT NULL COMMENT '总资产',
  `net_asset` decimal(16,4) DEFAULT NULL COMMENT '净资产',
  `revenue` decimal(16,4) DEFAULT NULL COMMENT '营收',
  `net_profit` decimal(16,4) DEFAULT NULL COMMENT '净利润',
  `ldp` decimal(16,4) DEFAULT NULL COMMENT '存贷比',
  `ccar` decimal(16,4) DEFAULT NULL COMMENT '核心资本充足率',
  `bad_ratio` decimal(16,4) DEFAULT NULL COMMENT '不良率',
  `rate` varchar(10) DEFAULT NULL,
  `index_date` date DEFAULT NULL COMMENT '报表截止日期',
  `code` varchar(32) DEFAULT NULL COMMENT '机构编码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Table structure for table `orders` */

DROP TABLE IF EXISTS `orders`;

CREATE TABLE `orders` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增序列',
  `quote_detail_id` bigint(20) DEFAULT NULL COMMENT '报价明细id',
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户id',
  `broker_id` varchar(32) DEFAULT NULL COMMENT '经纪人ID',
  `broker_name` varchar(32) DEFAULT NULL COMMENT '经纪人名称',
  `issuer_name` varchar(60) DEFAULT NULL COMMENT '发行银行',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `issuer_id` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `index_userId` (`user_id`),
  KEY `index_quoteDetailId` (`quote_detail_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Table structure for table `quote_details` */

DROP TABLE IF EXISTS `quote_details`;

CREATE TABLE `quote_details` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增序列',
  `flow_id` bigint(20) DEFAULT NULL,
  `offer_id` varchar(50) NOT NULL COMMENT '报价ID',
  `issue_dept_id` varchar(32) DEFAULT NULL COMMENT '发行机构ID',
  `goods_level` varchar(4) DEFAULT NULL COMMENT '报价评级',
  `issue_day` varchar(4) DEFAULT NULL COMMENT '发行日<星期>',
  `terminal` varchar(2) DEFAULT NULL COMMENT '期限\n1->1M\n2->3M\n3->6M\n4->9M\n5->1Y',
  `issue_price` decimal(16,4) DEFAULT NULL COMMENT '发行价格',
  `issue_price_flag` varchar(1) DEFAULT NULL COMMENT '0:正常,1:推荐',
  `planed_issue_amount` varchar(16) DEFAULT '' COMMENT '发行数量',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  `good_status` varchar(1) DEFAULT NULL COMMENT '0正常,1 Reffer,2删除',
  `status` varchar(1) DEFAULT NULL COMMENT '1有效，2无效',
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `enterprise_type` varchar(32) DEFAULT NULL COMMENT '机构类型',
  `issue_date` date DEFAULT NULL COMMENT '发行日期',
  `description` varchar(255) DEFAULT NULL COMMENT '询满',
  `price_change` varchar(16) DEFAULT NULL COMMENT '价格变动',
  `actual_issue_amount` varchar(16) DEFAULT NULL COMMENT '实际发行量',
  `broker_id` varchar(32) DEFAULT NULL COMMENT '经纪人ID',
  `broker_name` varchar(32) DEFAULT NULL COMMENT '经纪人名称',
  `flag_vip` varchar(1) DEFAULT '0' COMMENT '0普通机构,1付费机构',
  `price_type` varchar(4) DEFAULT NULL,
  `non_bank` tinyint(1) DEFAULT '0' COMMENT 'true 限非银',
  `available` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `flow_id` (`flow_id`),
  KEY `index_issuer_date_terminal` (`terminal`,`offer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Table structure for table `quotes` */

DROP TABLE IF EXISTS `quotes`;

CREATE TABLE `quotes` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增序列',
  `rate` varchar(10) DEFAULT NULL COMMENT '报价评级',
  `issuer_id` varchar(32) NOT NULL DEFAULT '' COMMENT '机构ID',
  `first_level_order` varchar(7) DEFAULT NULL COMMENT '评级排序',
  `second_level_order` varchar(7) DEFAULT NULL COMMENT '机构code排序',
  `issuer_date` date NOT NULL COMMENT '发行日期',
  `issuer_name` varchar(60) DEFAULT NULL COMMENT '机构名称',
  `broker_id` varchar(32) DEFAULT NULL COMMENT '经纪人ID',
  `broker_name` varchar(32) DEFAULT NULL COMMENT '经纪人名称',
  `full_name` varchar(60) DEFAULT NULL COMMENT '机构全称',
  `m1_detail_id` varchar(32) DEFAULT NULL COMMENT '期限为1M的quote_detail的offer_id',
  `m1_price` decimal(14,4) DEFAULT NULL COMMENT '期限为1M的价格',
  `m1_volume` varchar(16) DEFAULT '' COMMENT '期限为1M的发行数量',
  `m1_change` decimal(14,4) DEFAULT NULL COMMENT '期限为1M的价格变动',
  `m1_mark` varchar(256) DEFAULT NULL COMMENT '期限为1M的备注',
  `m1_available` tinyint(1) DEFAULT NULL COMMENT '期限为1M是否询满,0:询满',
  `m1_quote_time` time DEFAULT NULL COMMENT '期限为1M报价时间',
  `m3_detail_id` varchar(32) DEFAULT NULL COMMENT '期限为3M的quote_detail的offer_id',
  `m3_price` decimal(14,4) DEFAULT NULL COMMENT '期限为3M的价格',
  `m3_volume` varchar(16) DEFAULT '' COMMENT '期限为3M的发行数量',
  `m3_change` decimal(14,4) DEFAULT NULL COMMENT '期限为3M的价格变动',
  `m3_mark` varchar(256) DEFAULT NULL COMMENT '期限为3M的备注',
  `m3_available` tinyint(1) DEFAULT NULL COMMENT '期限为3M是否询满',
  `m3_quote_time` time DEFAULT NULL COMMENT '期限为3M报价时间',
  `m6_detail_id` varchar(32) DEFAULT NULL COMMENT '期限为6M的quote_detail的offer_id',
  `m6_price` decimal(14,4) DEFAULT NULL COMMENT '期限为6M的价格',
  `m6_volume` varchar(16) DEFAULT '' COMMENT '期限为6M的发行数量',
  `m6_change` decimal(14,4) DEFAULT NULL COMMENT '期限为6M的价格变动',
  `m6_mark` varchar(256) DEFAULT NULL COMMENT '期限为6M的备注',
  `m6_available` tinyint(1) DEFAULT NULL COMMENT '期限为6M是否询满',
  `m6_quote_time` time DEFAULT NULL COMMENT '期限为6M报价时间',
  `m9_detail_id` varchar(32) DEFAULT NULL COMMENT '期限为9M的quote_detail的offer_id',
  `m9_price` decimal(14,4) DEFAULT NULL COMMENT '期限为9M的价格',
  `m9_volume` varchar(16) DEFAULT '' COMMENT '期限为9M的发行数量',
  `m9_change` decimal(14,4) DEFAULT NULL COMMENT '期限为9M的价格变动',
  `m9_mark` varchar(256) DEFAULT NULL COMMENT '期限为9M的备注',
  `m9_available` tinyint(1) DEFAULT NULL COMMENT '期限为9M是否询满',
  `m9_quote_time` time DEFAULT NULL COMMENT '期限为9M报价时间',
  `y1_detail_id` varchar(32) DEFAULT NULL COMMENT '期限为1Y的quote_detail的offer_id',
  `y1_price` decimal(14,4) DEFAULT NULL COMMENT '期限为1Y的价格',
  `y1_volume` varchar(16) DEFAULT '' COMMENT '期限为1Y的发行数量',
  `y1_change` decimal(14,4) DEFAULT NULL COMMENT '期限为1Y的价格变动',
  `y1_mark` varchar(256) DEFAULT NULL COMMENT '期限为1Y的备注',
  `y1_available` tinyint(1) DEFAULT NULL COMMENT '期限为1Y是否询满',
  `y1_quote_time` time DEFAULT NULL COMMENT '期限为1Y报价时间',
  `recommend` tinyint(1) DEFAULT NULL COMMENT '推荐',
  `issuer_code` varchar(32) DEFAULT NULL,
  `outdated` tinyint(1) NOT NULL DEFAULT '0' COMMENT '1:过期;0:未过期',
  `m1_recommend` tinyint(1) DEFAULT NULL COMMENT '1:推荐;0:不推荐',
  `m3_recommend` tinyint(1) DEFAULT NULL COMMENT '1:推荐;0:不推荐',
  `m6_recommend` tinyint(1) DEFAULT NULL COMMENT '1:推荐;0:不推荐',
  `m9_recommend` tinyint(1) DEFAULT NULL COMMENT '1:推荐;0:不推荐',
  `y1_recommend` tinyint(1) DEFAULT NULL COMMENT '1:推荐;0:不推荐',
  PRIMARY KEY (`id`),
  KEY `index_issuer_date` (`issuer_date`,`broker_id`,`first_level_order`,`second_level_order`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Table structure for table `state_own_bank` */

DROP TABLE IF EXISTS `state_own_bank`;

CREATE TABLE `state_own_bank` (
  `auto_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增序列',
  `code` varchar(20) DEFAULT '' COMMENT '国有银行代码',
  `name` varchar(20) DEFAULT '' COMMENT '国有银行名称',
  `sort_value` varchar(20) DEFAULT '' COMMENT '国有银行排序',
  PRIMARY KEY (`auto_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `user_filters`;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `user_filters` (
  `user_id` varchar(32) NOT NULL DEFAULT '' COMMENT '用户ID',
  `broker_id` varchar(32) DEFAULT '' COMMENT 'brokerID',
  `rating` varchar(50) DEFAULT NULL COMMENT '评级',
  `institution_type` varchar(50) DEFAULT NULL COMMENT '银行类型',
  `total_asset` varchar(20) DEFAULT NULL COMMENT '总资产',
  `net_asset` varchar(20) DEFAULT NULL COMMENT '净资产',
  `revenue` varchar(20) DEFAULT NULL COMMENT '营收',
  `net_profit` varchar(20) DEFAULT NULL COMMENT '净利润',
  `ldp` varchar(20) DEFAULT NULL COMMENT '存贷比',
  `ccar` varchar(20) DEFAULT NULL COMMENT '核心资本充足率',
  `bad_ratio` varchar(20) DEFAULT NULL COMMENT '不良率',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;