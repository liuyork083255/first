ALTER TABLE `wf_web_ncd`.`quotes`
	CHANGE `m1_volume` `m1_volume` VARCHAR(16) NULL COMMENT '期限为1M的发行数量',
	CHANGE `m3_volume` `m3_volume` VARCHAR(16) NULL COMMENT '期限为3M的发行数量',
	CHANGE `m6_volume` `m6_volume` VARCHAR(16) NULL COMMENT '期限为6M的发行数量',
	CHANGE `m9_volume` `m9_volume` VARCHAR(16) NULL COMMENT '期限为9M的发行数量',
	CHANGE `y1_volume` `y1_volume` VARCHAR(16) NULL COMMENT '期限为1Y的发行数量';

ALTER TABLE `wf_web_ncd`.`quote_details`
	CHANGE `planed_issue_amount` `planed_issue_amount` VARCHAR(16) NULL COMMENT '发行数量';