DELETE FROM `t_api` WHERE `API_NAME` = 'webbond_recent_4th_workday_CIB';
DELETE FROM `t_api` WHERE `API_NAME` = 'webbond_recent_Nth_workday_CIB';

INSERT INTO `t_api` VALUES ('100', '100', 'webbond_recent_Nth_workday_CIB', '', null,
'
select DATE_FORMAT(recent_30_days.date, "%Y%m%d") as recent_Nth_workday from
(
select
	selected_date as date
from
	(select adddate("2017-01-01",t4*10000 + t3*1000 + t2*100 + t1*10 + t0) selected_date from
	 (select 0 t0 union select 1 union select 2 union select 3 union select 4 union select 5 union select 6 union select 7 union select 8 union select 9) t0,
	 (select 0 t1 union select 1 union select 2 union select 3 union select 4 union select 5 union select 6 union select 7 union select 8 union select 9) t1,
	 (select 0 t2 union select 1 union select 2 union select 3 union select 4 union select 5 union select 6 union select 7 union select 8 union select 9) t2,
	 (select 0 t3 union select 1 union select 2 union select 3 union select 4 union select 5 union select 6 union select 7 union select 8 union select 9) t3,
	 (select 0 t4 union select 1 union select 2 union select 3 union select 4 union select 5 union select 6 union select 7 union select 8 union select 9) t4) all_days
where all_days.selected_date between adddate(NOW(), -30) and NOW()
) recent_30_days

left JOIN holiday_info
on
	recent_30_days.date = holiday_info.holiday_date
	and holiday_info.market_type="CIB"
	and holiday_info.delflag="0"
',
'1', '1', null, '1', 'zhanzushun', Now(), 'zhanzushun', Now(), null, null, null, '1', 'holiday_info.ID is NULL order by date DESC', null
);



DELETE FROM `t_api` WHERE `API_NAME` = 'webbond_recent_4th_workday_SZE';
DELETE FROM `t_api` WHERE `API_NAME` = 'webbond_recent_Nth_workday_SZE';

INSERT INTO `t_api` VALUES ('100', '100', 'webbond_recent_Nth_workday_SZE', '', null,
'
select DATE_FORMAT(recent_30_days.date, "%Y%m%d") as recent_Nth_workday from
(
select
	selected_date as date
from
	(select adddate("2017-01-01",t4*10000 + t3*1000 + t2*100 + t1*10 + t0) selected_date from
	 (select 0 t0 union select 1 union select 2 union select 3 union select 4 union select 5 union select 6 union select 7 union select 8 union select 9) t0,
	 (select 0 t1 union select 1 union select 2 union select 3 union select 4 union select 5 union select 6 union select 7 union select 8 union select 9) t1,
	 (select 0 t2 union select 1 union select 2 union select 3 union select 4 union select 5 union select 6 union select 7 union select 8 union select 9) t2,
	 (select 0 t3 union select 1 union select 2 union select 3 union select 4 union select 5 union select 6 union select 7 union select 8 union select 9) t3,
	 (select 0 t4 union select 1 union select 2 union select 3 union select 4 union select 5 union select 6 union select 7 union select 8 union select 9) t4) all_days
where all_days.selected_date between adddate(NOW(), -30) and NOW()
) recent_30_days

left JOIN holiday_info
on
	recent_30_days.date = holiday_info.holiday_date
	and holiday_info.market_type="SZE"
	and holiday_info.delflag="0"
',
'1', '1', null, '1', 'zhanzushun', Now(), 'zhanzushun', Now(), null, null, null, '1', 'holiday_info.ID is NULL order by date DESC', null
);



DELETE FROM `t_api` WHERE `API_NAME` = 'webbond_recent_4th_workday_SSE';
DELETE FROM `t_api` WHERE `API_NAME` = 'webbond_recent_Nth_workday_SSE';

INSERT INTO `t_api` VALUES ('100', '100', 'webbond_recent_Nth_workday_SSE', '', null,
'
select DATE_FORMAT(recent_30_days.date, "%Y%m%d") as recent_Nth_workday from
(
select
	selected_date as date
from
	(select adddate("2017-01-01",t4*10000 + t3*1000 + t2*100 + t1*10 + t0) selected_date from
	 (select 0 t0 union select 1 union select 2 union select 3 union select 4 union select 5 union select 6 union select 7 union select 8 union select 9) t0,
	 (select 0 t1 union select 1 union select 2 union select 3 union select 4 union select 5 union select 6 union select 7 union select 8 union select 9) t1,
	 (select 0 t2 union select 1 union select 2 union select 3 union select 4 union select 5 union select 6 union select 7 union select 8 union select 9) t2,
	 (select 0 t3 union select 1 union select 2 union select 3 union select 4 union select 5 union select 6 union select 7 union select 8 union select 9) t3,
	 (select 0 t4 union select 1 union select 2 union select 3 union select 4 union select 5 union select 6 union select 7 union select 8 union select 9) t4) all_days
where all_days.selected_date between adddate(NOW(), -30) and NOW()
) recent_30_days

left JOIN holiday_info
on
	recent_30_days.date = holiday_info.holiday_date
	and holiday_info.market_type="SSE"
	and holiday_info.delflag="0"
',
'1', '1', null, '1', 'zhanzushun', Now(), 'zhanzushun', Now(), null, null, null, '1', 'holiday_info.ID is NULL order by date DESC', null
);
