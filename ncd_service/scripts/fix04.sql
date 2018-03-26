ALTER TABLE quotes
	ADD COLUMN  outdated TINYINT(1) NOT NULL DEFAULT 0 COMMENT "1:过期;0:未过期",
	ADD COLUMN  m1_recommend TINYINT(1) DEFAULT NULL COMMENT "1:推荐;0:不推荐" AFTER m1_available,
	ADD COLUMN  m3_recommend TINYINT(1) DEFAULT NULL COMMENT "1:推荐;0:不推荐" AFTER m3_available,
	ADD COLUMN  m6_recommend TINYINT(1) DEFAULT NULL COMMENT "1:推荐;0:不推荐" AFTER m6_available,
	ADD COLUMN  m9_recommend TINYINT(1) DEFAULT NULL COMMENT "1:推荐;0:不推荐" AFTER m9_available,
	ADD COLUMN  y1_recommend TINYINT(1) DEFAULT NULL COMMENT "1:推荐;0:不推荐" AFTER y1_available;

update quotes set outdated = 1 where quotes.issuer_date != '2018-03-23';