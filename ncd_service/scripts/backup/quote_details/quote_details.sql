CREATE TABLE `quote_details_2018` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增序列',
  `flow_id` bigint(20) DEFAULT NULL,
  `offer_id` varchar(50) NOT NULL COMMENT '报价ID',
  `issue_dept_id` varchar(32) DEFAULT NULL COMMENT '发行机构ID',
  `goods_level` varchar(4) DEFAULT NULL COMMENT '报价评级',
  `issue_day` varchar(4) DEFAULT NULL COMMENT '发行日<星期>',
  `terminal` varchar(2) DEFAULT NULL COMMENT '期限\n1->1M\n2->3M\n3->6M\n4->9M\n5->1Y',
  `issue_price` decimal(16,4) DEFAULT NULL COMMENT '发行价格',
  `issue_price_flag` varchar(1) DEFAULT NULL COMMENT '0:正常,1:推荐',
  `planed_issue_amount` decimal(16,4) DEFAULT NULL COMMENT '发行数量',
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
  KEY `index_issuer_date_terminal` (`terminal`,`offer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4