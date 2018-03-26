DELETE FROM `t_api` WHERE `API_NAME` = 'webbond_broker1_auth';
INSERT INTO `t_api` VALUES ('107', '100', 'webbond_broker1_auth', 'idb_account_business.account_id:string', null,
'select [columns] from (
  select a.account_id, b.id as business_allow_id
  from idb_account_business a, idb_business_allow b
  where b.id=a.business_allow_id
  and b.business_code=\'BOND\' and b.company_id=\'1\' and a.status=1 and a.account_app_status=1 and a.broker_company_app_status=1
) as temp_broker1_account',
'1', '1', null, '1', 'zhanzushun', Now(), 'zhanzushun', Now(), null, null, null, '1', null, null
);

DELETE FROM `t_api` WHERE `API_NAME` = 'webbond_broker2_auth';
INSERT INTO `t_api` VALUES ('107', '100', 'webbond_broker2_auth', 'idb_account_business.account_id:string', null,
'select [columns] from (
  select a.account_id, b.id as business_allow_id
  from idb_account_business a, idb_business_allow b
  where b.id=a.business_allow_id
  and b.business_code=\'BOND\' and b.company_id=\'2\' and a.status=1 and a.account_app_status=1 and a.broker_company_app_status=1
) as temp_broker1_account',
'1', '1', null, '1', 'zhanzushun', Now(), 'zhanzushun', Now(), null, null, null, '1', null, null
);

DELETE FROM `t_api` WHERE `API_NAME` = 'webbond_broker3_auth';
INSERT INTO `t_api` VALUES ('107', '100', 'webbond_broker3_auth', 'idb_account_business.account_id:string', null,
'select [columns] from (
  select a.account_id, b.id as business_allow_id
  from idb_account_business a, idb_business_allow b
  where b.id=a.business_allow_id
  and b.business_code=\'BOND\' and b.company_id=\'3\' and a.status=1 and a.account_app_status=1 and a.broker_company_app_status=1
) as temp_broker1_account',
'1', '1', null, '1', 'zhanzushun', Now(), 'zhanzushun', Now(), null, null, null, '1', null, null
);

DELETE FROM `t_api` WHERE `API_NAME` = 'webbond_broker4_auth';
INSERT INTO `t_api` VALUES ('107', '100', 'webbond_broker4_auth', 'idb_account_business.account_id:string', null,
'select [columns] from (
  select a.account_id, b.id as business_allow_id
  from idb_account_business a, idb_business_allow b
  where b.id=a.business_allow_id
  and b.business_code=\'BOND\' and b.company_id=\'4\' and a.status=1 and a.account_app_status=1 and a.broker_company_app_status=1
) as temp_broker1_account',
'1', '1', null, '1', 'zhanzushun', Now(), 'zhanzushun', Now(), null, null, null, '1', null, null
);

DELETE FROM `t_api` WHERE `API_NAME` = 'webbond_broker5_auth';
INSERT INTO `t_api` VALUES ('107', '100', 'webbond_broker5_auth', 'idb_account_business.account_id:string', null,
'select [columns] from (
  select a.account_id, b.id as business_allow_id
  from idb_account_business a, idb_business_allow b
  where b.id=a.business_allow_id
  and b.business_code=\'BOND\' and b.company_id=\'5\' and a.status=1 and a.account_app_status=1 and a.broker_company_app_status=1
) as temp_broker1_account',
'1', '1', null, '1', 'zhanzushun', Now(), 'zhanzushun', Now(), null, null, null, '1', null, null
);

DELETE FROM `t_api` WHERE `API_NAME` = 'webbond_company_business_blacklist';
INSERT INTO `t_api` VALUES ('107', '100', 'webbond_company_business_blacklist', '', null,
'select [columns] from (
  select financial_company_id as company_id, business_allow_id
  from idb_company_business where status=1 and broker_company_app_status=-1
) as blacklist_table',
'1', '1', null, '1', 'zhanzushun', Now(), 'zhanzushun', Now(), null, null, null, '1', null, null
);
