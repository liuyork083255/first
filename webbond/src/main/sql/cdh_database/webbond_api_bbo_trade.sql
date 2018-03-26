
DELETE FROM t_api
WHERE API_NAME = 'webbond_bbo';
INSERT INTO t_api (DATA_SOURCE_ID, DATA_SOURCE_DB_ID, API_NAME, INPUT_ARGS, OUTPUT_ARGS, LOGIC_SQL, API_GROUPS, VERSION, COMMENTS, AVAILABLE_FLAG, CREATOR, CREATE_TIME, LAST_MODIFY_PERSON, LAST_MODIFY_TIME, EXTRA1, EXTRA2, EXTRA3, IS_WHERE_AVAILABLE, FORCE_CONDITION, DATE_COLUMN) VALUES ('106', '100', 'webbond_bbo', 'bondCode:string', null, 'select [columns] from (SELECT webbond_bbo.*
                      FROM (SELECT
                              bondKey,
                              ListedMarket,
                              brokerId,
                              max(updateDateTime) AS updateDateTime
                            FROM webbond_bbo
                            WHERE TO_DAYS(updateDateTime) = TO_DAYS(NOW())
                            GROUP BY bondKey, ListedMarket, brokerId) as t LEFT JOIN webbond_bbo
                          ON t.bondKey = webbond_bbo.bondKey AND t.ListedMarket = webbond_bbo.ListedMarket AND
                             t.brokerId = webbond_bbo.brokerId AND t.updateDateTime = webbond_bbo.updateDateTime) as temp_webbond_bbo', '1,3', '1', '最优报价表', '1', 'wangchengzhang', '2016-02-15 16:10:20', 'wangchengzhang', '2016-02-15 16:10:20', null, null, null, 1, null, null);

DELETE FROM t_api
WHERE API_NAME = 'webbond_trade';
INSERT INTO t_api (DATA_SOURCE_ID, DATA_SOURCE_DB_ID, API_NAME, INPUT_ARGS, OUTPUT_ARGS, LOGIC_SQL, API_GROUPS, VERSION, COMMENTS, AVAILABLE_FLAG, CREATOR, CREATE_TIME, LAST_MODIFY_PERSON, LAST_MODIFY_TIME, EXTRA1, EXTRA2, EXTRA3, IS_WHERE_AVAILABLE, FORCE_CONDITION, DATE_COLUMN)
VALUES ('106', '100', 'webbond_trade', NULL, NULL, 'select [columns] from webbond_trade', '1', '1', '今日成交表', '1',
               'wangchengzhang', '2016-02-15 16:10:20', 'wangchengzhang', '2016-02-15 16:10:20', NULL, NULL, NULL, 1,
        NULL, NULL);

