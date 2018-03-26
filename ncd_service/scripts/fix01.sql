alter table user_filters drop primary key;
alter table user_filters add auto_id BIGINT(20) PRIMARY Key AUTO_INCREMENT FIRST ;
alter table user_filters add constraint index_userId unique (`user_id`);