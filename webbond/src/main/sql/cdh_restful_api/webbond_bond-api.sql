DELETE FROM `t_api` WHERE `API_NAME` = 'webbond_bond';
INSERT INTO `t_api` VALUES ('100', '100', 'webbond_bond', 'bond.bond_key:string', null, 'SELECT\r\n	`bond`.auction_date_start,\r\n	`bond`.bond_id,\r\n	`bond`.bond_key,\r\n	`bond`.bond_subtype,\r\n	`bond`.bond_type,\r\n	`bond`.compound_frequency,\r\n	`bond`.coupon_frequency,\r\n	`bond`.coupon_rate_current,\r\n	`bond`.coupon_rate_spread,\r\n	`bond`.coupon_type,\r\n	`bond`.delisted_date,\r\n	`bond`.ent_cor,\r\n	`bond`.fixing_ma_days,\r\n	`bond`.frn_index_id,\r\n	`bond`.full_name,\r\n	`bond`.interest_start_date,\r\n	`bond`.is_municipal,\r\n	`bond`.issue_amount,\r\n	`bond`.issue_price,\r\n	`bond`.issue_rate,\r\n	`bond`.issue_start_date,\r\n	`bond`.issue_year,\r\n	`bond`.issuer_code,\r\n	`bond`.issuer_outlook_current,\r\n	`bond`.issuer_rating_current,\r\n	`bond`.issuer_rating_current_npy,\r\n	`bond`.issuer_rating_date,\r\n	`bond`.issuer_rating_institution_code,\r\n	`bond`.listed_date,\r\n	`bond`.maturity_date,\r\n	`bond`.maturity_term,\r\n	`bond`.next_coupon_date,\r\n	`bond`.option_date,\r\n	`bond`.option_exercise_date,\r\n	`bond`.option_style,\r\n	`bond`.option_type,\r\n	`bond`.p_issuer_outlook_current,\r\n	`bond`.p_issuer_rating_current,\r\n	`bond`.p_rating_current,\r\n	`bond`.payment_date,\r\n	`bond`.planned_issue_amount,\r\n	`bond`.rating_augment,\r\n	`bond`.rating_current,\r\n	-- bondrating\r\n	`bond`.rating_current_npy,\r\n	`bond`.rating_date,\r\n	`bond`.rating_institution_code,\r\n	`bond`.redemption_str,\r\n	`bond`.term_structure,\r\n	`bond`.term_unit,\r\n	`p_bond_list_info`.listed_market,\r\n	`p_bond_list_info`.pinyin,\r\n	`p_bond_list_info`.pinyin_full,\r\n	`p_bond_list_info`.short_name,\r\n	`cash_flow`.`cashflow_paymentdate`,\r\n\r\nIF (\r\n	(\r\n		NOT ISNULL(`bond`.`Option_Date`)\r\n	)\r\n	AND (\r\n		NOT ISNULL(`bond`.`Term_structure`)\r\n	)\r\n	AND (\r\n		TO_DAYS(`bond`.`Option_Date`) -\r\n		IF (\r\n			(\r\n				TO_DAYS(\r\n					`bond`.`Interest_Start_Date`\r\n				) > TO_DAYS(NOW())\r\n			),\r\n			TO_DAYS(\r\n				`bond`.`Interest_Start_Date`\r\n			),\r\n			TO_DAYS(NOW())\r\n		) >= 1\r\n	),\r\n	\"01\",\r\n	\"02\"\r\n) AS optionType,\r\n CASE\r\nWHEN (\r\n	`bond`.`Ent_Cor` = \"COP\"\r\n	AND `bond`.`External_Type` = \"EPB\"\r\n	AND `bond`.`Bond_Subtype` IN (\"CEB\", \"LEB\")\r\n) THEN\r\n	\"01\"\r\nWHEN (\r\n	`bond`.`Ent_Cor` = \"COP\"\r\n	AND `bond`.`External_Type` = \"PPN\"\r\n	AND `bond`.`Bond_Subtype` IN (\"PPN\")\r\n) THEN\r\n	\"02\"\r\nEND AS `corporateBondType`,\r\n CASE\r\nWHEN (\r\n	`bond`.`bond_subtype` IN (\"BGB\")\r\n) THEN\r\n	\"01\"\r\nWHEN (\r\n	`bond`.`bond_subtype` IN (\"SCB\")\r\n) THEN\r\n	\"02\"\r\nWHEN (\r\n	`bond`.`bond_subtype` IN (\"PBB\", \"PSB\")\r\n) THEN\r\n	\"03\"\r\nWHEN (\r\n	`bond`.`bond_subtype` IN (\"TLB\", \"LLB\")\r\n) THEN\r\n	\"04\"\r\nWHEN (\r\n	`bond`.`bond_subtype` IN (\"CCP\", \"LCP\", \"SSB\")\r\n) THEN\r\n	\"0506\"\r\nWHEN (\r\n	`bond`.`bond_subtype` IN (\"CSP\", \"LSP\")\r\n) THEN\r\n	\"0506\"\r\nWHEN (\r\n	`bond`.`bond_subtype` IN (\"CMN\", \"LMN\")\r\n) THEN\r\n	\"07\"\r\nWHEN (\r\n	(\r\n		`bond`.`bond_subtype` IN (\r\n			\"CCB\",\r\n			\"CEB\",\r\n			\"LEB\",\r\n			\"RAB\",\r\n			\"CVB\",\r\n			\"SCV\"\r\n		)\r\n	)\r\n	AND (`bond`.`Ent_Cor` = \"ENT\")\r\n) THEN\r\n	\"08\"\r\nWHEN (\r\n	(\r\n		`bond`.`bond_subtype` IN (\r\n			\"CCB\",\r\n			\"CEB\",\r\n			\"LEB\",\r\n			\"RAB\",\r\n			\"CVB\",\r\n			\"SCV\"\r\n		)\r\n	)\r\n	AND (`bond`.`Ent_Cor` = \"COP\")\r\n) THEN\r\n	\"0910\"\r\nWHEN (\r\n	(\r\n		`bond`.`bond_subtype` IN (\"PPN\")\r\n	)\r\n	AND (`bond`.`Ent_Cor` = \"COP\")\r\n) THEN\r\n	\"0910\"\r\nWHEN (\r\n	(\r\n		`bond`.`bond_subtype` IN (\"PPN\")\r\n	)\r\n	AND (`bond`.`Ent_Cor` IS NULL)\r\n) THEN\r\n	\"11\"\r\nWHEN (\r\n	`bond`.`bond_subtype` IN (\r\n		\"SPD\",\r\n		\"MCD\",\r\n		\"SHD\",\r\n		\"CCD\",\r\n		\"RTD\",\r\n		\"RRD\",\r\n		\"FRD\",\r\n		\"OTD\"\r\n	)\r\n) THEN\r\n	\"12\"\r\nWHEN (\r\n	`bond`.`bond_subtype` IN (\"AMP\", \"CBS\", \"LBS\", \"MBS\")\r\n) THEN\r\n	\"13\"\r\nELSE\r\n	\"14\"\r\nEND AS `qbBondType`,\r\n CASE\r\nWHEN (\r\n	ISNULL(\r\n		`bond`.`Issuer_Rating_Current`\r\n	)\r\n	AND ISNULL(`bond`.`Rating_Current`)\r\n) THEN\r\n	\"--\"\r\nWHEN (\r\n	ISNULL(\r\n		`bond`.`Issuer_Rating_Current`\r\n	)\r\n	AND (\r\n		NOT ISNULL(`bond`.`Rating_Current`)\r\n	)\r\n) THEN\r\n	CONCAT(\r\n		\"--/\",\r\n		`bond`.`Rating_Current`\r\n	)\r\nWHEN (\r\n	(\r\n		NOT ISNULL(\r\n			`bond`.`Issuer_Rating_Current`\r\n		)\r\n	)\r\n	AND ISNULL(`bond`.`Rating_Current`)\r\n) THEN\r\n	CONCAT(\r\n		`bond`.`Issuer_Rating_Current`,\r\n		\"/--\"\r\n	)\r\nWHEN (\r\n	(\r\n		NOT ISNULL(\r\n			`bond`.`Issuer_Rating_Current`\r\n		)\r\n	)\r\n	AND (\r\n		NOT ISNULL(`bond`.`Rating_Current`)\r\n	)\r\n	AND (\r\n		`bond`.`Issuer_Rating_Current` <> `bond`.`Rating_Current`\r\n	)\r\n) THEN\r\n	CONCAT(\r\n		`bond`.`Issuer_Rating_Current`,\r\n		\"/\",\r\n		`bond`.`Rating_Current`\r\n	)\r\nWHEN (\r\n	(\r\n		NOT ISNULL(\r\n			`bond`.`Issuer_Rating_Current`\r\n		)\r\n	)\r\n	AND (\r\n		NOT ISNULL(`bond`.`Rating_Current`)\r\n	)\r\n	AND (\r\n		`bond`.`Issuer_Rating_Current` = `bond`.`Rating_Current`\r\n	)\r\n) THEN\r\n	`bond`.`Issuer_Rating_Current`\r\nEND AS `corpBondRating`,\r\n CASE\r\nWHEN (\r\n	`bond`.`coupon_type` IN (\"ZRO\", \"DSC\", \"FRB\", \"PAM\")\r\n	OR (\r\n		`bond`.`Coupon_Type` = \"FRN\"\r\n		AND `bond`.`Next_Coupon_Date` = `bond`.`maturity_date`\r\n	)\r\n) THEN\r\n	\"03\"\r\nWHEN (\r\n	`bond`.`coupon_type` = \"FRN\"\r\n	AND `bond`.`FRN_Index_ID` LIKE \"%SHIBOR%\"\r\n	AND `bond`.`Next_Coupon_Date` != `bond`.`maturity_date`\r\n) THEN\r\n	\"01\"\r\nWHEN (\r\n	`bond`.`coupon_type` = \"FRN\"\r\n	AND `bond`.`FRN_Index_ID` LIKE \"%DEPO%\"\r\n	AND `bond`.`Next_Coupon_Date` != `bond`.`maturity_date`\r\n) THEN\r\n	\"02\"\r\nEND AS `couponType`,\r\n\r\nIF (\r\n	(\r\n		TO_DAYS(\r\n			(\r\n				SELECT\r\n					DATE_FORMAT(NOW(), \"%Y%m%d\") AS `DATE_FORMAT(NOW(), \"%Y%m%d\")`\r\n			)\r\n		) - TO_DAYS(`bond`.`Listed_Date`)\r\n	) < 6,\r\n	1,\r\n	0\r\n) AS `newListed`,\r\n CASE\r\nWHEN (`bond`.`Option_Type` = \"ETS\") THEN\r\n	\"01\"\r\nELSE\r\n	\"02\"\r\nEND AS `perpetualType`,\r\n CASE `bond`.`Rating_Current`\r\nWHEN \"AAA\" THEN\r\n	\"01\"\r\nWHEN \"A-1\" THEN\r\n	\"02\"\r\nWHEN \"AA+\" THEN\r\n	\"03\"\r\nWHEN \"AA\" THEN\r\n	\"04\"\r\nWHEN \"AA-\" THEN\r\n	\"05\"\r\nWHEN \"A+\" THEN\r\n	\"06\"\r\nELSE\r\n	\"07\"\r\nEND AS `bondRating`,\r\n CASE\r\nWHEN (\r\n	(\r\n		`bond`.`bond_subtype` = \"PBB\"\r\n	)\r\n	AND (\r\n		`bond`.`Issuer_Code` = \"G000124\"\r\n	)\r\n) THEN\r\n	\"01\"\r\nWHEN (\r\n	(\r\n		`bond`.`bond_subtype` = \"PBB\"\r\n	)\r\n	AND (\r\n		`bond`.`Issuer_Code` <> \"G000124\"\r\n	)\r\n) THEN\r\n	\"02\"\r\nEND AS `financialBondType`,\r\n CASE `p_bond_list_info`.`Listed_Market`\r\nWHEN \"CIB\" THEN\r\n	CONCAT(\r\n		`p_bond_list_info`.`Bond_ID`,\r\n		\".IB\"\r\n	)\r\nWHEN \"SSE\" THEN\r\n	`p_bond_list_info`.`Bond_ID`\r\nWHEN \"SZE\" THEN\r\n	`p_bond_list_info`.`Bond_ID`\r\nEND AS `bondCode`,\r\n\r\nIF (\r\n	(\r\n		`p_bond_list_info`.`Listed_Market` = \"CIB\"\r\n	),\r\n	1,\r\n	0\r\n) AS `interBank`,\r\n\r\nIF (\r\n	(\r\n		`p_bond_list_info`.`Listed_Market` = \"SSE\"\r\n	)\r\n	OR (\r\n		`p_bond_list_info`.`Listed_Market` = \"SZE\"\r\n	),\r\n	1,\r\n	0\r\n) AS `exchange`,\r\n\r\nIF (\r\n	(\r\n		(\r\n			`p_bond_list_info`.`Is_Mortgage` = \"Y\"\r\n		)\r\n		AND (\r\n			`bond`.`Issuer_Rating_Current` IN (\"AAA+\", \"AAA\", \"AA+\", \"AA\")\r\n		)\r\n		AND (\r\n			`bond`.`Rating_Current` = \"AAA\"\r\n		)\r\n		AND (\r\n			`bond`.`Issuer_Outlook_Current` IN (\"STB\", \"POS\")\r\n		)\r\n	),\r\n	1,\r\n	0\r\n) AS `pledge`\r\nFROM\r\n	`bond` AS `bond`\r\nLEFT JOIN `p_bond_list_info` AS `p_bond_list_info` ON (\r\n	`bond`.`Bond_Key` = `p_bond_list_info`.`Bond_Key`\r\n	AND (`bond`.`delflag` = 0)\r\n	AND (\r\n		`p_bond_list_info`.`delflag` = 0\r\n	)\r\n)\r\nLEFT JOIN (\r\n	SELECT\r\n		Bond_Key,\r\n		GROUP_CONCAT(\r\n			Payment_Date\r\n			ORDER BY\r\n				Payment_Date DESC SEPARATOR \'/\'\r\n		) AS cashflow_paymentdate\r\n	FROM\r\n		p_cash_flow\r\n	GROUP BY\r\n		Bond_Key\r\n) AS `cash_flow` ON (\r\n	`bond`.`Bond_Key` = `cash_flow`.`Bond_Key`\r\n)', '1', '1', null, '1', 'zhanzushun', '2017-04-01 20:13:42', 'zhanzushun', '2017-04-01 20:13:42', null, null, null, '1', null, null);