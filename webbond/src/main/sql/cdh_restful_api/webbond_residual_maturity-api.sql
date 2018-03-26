DELETE FROM `t_api` WHERE `API_NAME` = 'webbond_residual_maturity';
INSERT INTO `t_api` VALUES ('100', '100', 'webbond_residual_maturity', null, null, 
'SELECT
	[columns], CONCAT(

		IF (
			(
				ISNULL(
					`sc`.`Now_to_OptionDate_days`
				)
				OR (
					`sc`.`Now_to_OptionDate_days` < 1
				)
			),
			CONCAT(
				`sc`.`Now_to_MaturityDate_days`,
				"D"
			),

		IF (
			(
				NOT ISNULL(`sc`.`Term_structure`)
			),
			CONCAT(
				CONCAT(
					`sc`.`Now_to_OptionDate_days`,
					"D + "
				),
				CONCAT(
					`sc`.`OptionDate_to_MaturityDate_days`,
					"D"
				)
			),
			CONCAT(
				`sc`.`Now_to_MaturityDate_days`,
				"D"
			)
		)
		),

	IF (
		(`sc`.`Option_Type` = "ETS"),
		" + N",
		""
	)
	) AS `residual_maturity`,
	`sc`.`Now_to_MaturityDate_days` AS `Now_to_MaturityDate_days`,
	-- IFNULL(`sc`.`Now_to_OptionDate_days`, `sc`.`Now_to_MaturityDate_days`) AS `Now_to_OptionDate_or_to_MaturityDate_days`
IF (
	(
		ISNULL(
			`sc`.`Now_to_OptionDate_days`
		)
		OR (
			`sc`.`Now_to_OptionDate_days` = 0
		)
	),
	`sc`.`Now_to_MaturityDate_days`,
	`sc`.`Now_to_OptionDate_days`
) AS `Now_to_OptionDate_or_to_MaturityDate_days`
FROM
	(
		SELECT
			`bond`.`Bond_Key`,
			`bond`.`Bond_ID`,
			`bond`.`Short_Name`,
			`bond`.`Term_structure`,
			`bond`.`Option_Type`,
			(
				TO_DAYS(`bond`.`Option_Date`) -
				IF (
					(
						TO_DAYS(NOW()) < TO_DAYS(
							`bond`.`Interest_Start_Date`
						)
					),
					TO_DAYS(
						`bond`.`Interest_Start_Date`
					),
					TO_DAYS(NOW())
				)
			) AS `Now_to_OptionDate_days`,
			(
				TO_DAYS(`bond`.`Maturity_Date`) - TO_DAYS(`bond`.`Option_Date`)
			) AS `OptionDate_to_MaturityDate_days`,
			(
				TO_DAYS(`bond`.`Maturity_Date`) -
				IF (
					(
						TO_DAYS(NOW()) < TO_DAYS(
							`bond`.`Interest_Start_Date`
						)
					),
					TO_DAYS(
						`bond`.`Interest_Start_Date`
					),
					TO_DAYS(NOW())
				)
			) AS `Now_to_MaturityDate_days`
		FROM
			`bond` AS `bond`
		WHERE
			(`bond`.`delflag` = 0)
	) AS sc',
'1', '1', null, '1', 'zhanzushun', Now(), 'zhanzushun', Now(), null, null, null, '1', null, null
);