DELETE FROM `t_api` WHERE `API_NAME` = 'webbond_cdc_auth';
INSERT INTO `t_api` VALUES ('107', '100', 'webbond_cdc_auth', 'idb_cdc_account.account_id:string', null,
'
select [columns] from
(select idb_cdc_account.account_id, idb_cdc_account.enable
from idb_cdc_account as idb_cdc_account
where idb_cdc_account.status = 1
and idb_cdc_account.enable = 1) as temp_cdc_account
',
'1', '1', null, '1', 'zhanzushun', Now(), 'zhanzushun', Now(), null, null, null, '1', null, null
);