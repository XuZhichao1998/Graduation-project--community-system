use elife_db;

DROP PROCEDURE IF EXISTS pro_enroll_regist;
DROP PROCEDURE IF EXISTS pro_show_pass_regist; -- 存储过程，审核报名者，按照报名日期和限定人数先来先得

DROP TABLE IF EXISTS activity_participation;
DROP TABLE IF EXISTS activity_registration;
DROP TABLE IF EXISTS service_activity;
DROP TABLE IF EXISTS party_member_info;
DROP TABLE IF EXISTS cooperation_unit;

CREATE TABLE cooperation_unit (
	unit_id int NOT NULL AUTO_INCREMENT,
	unit_name varchar(100) NOT NULL,
	PRIMARY KEY(unit_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO cooperation_unit(unit_name) VALUES 
	('华中师范大学党委办公室'), -- 1
	('湖北省电力局党委办公室'), -- 2
	('华中科技大学党委办公室'), -- 3
	('武汉理工大学党委办公室'), -- 4
	('湖北省电力局党委办公室'), -- 5
	('湖北省教育局党委办公室'), -- 6
	('武汉大学党委办公室');     -- 7

	
CREATE TABLE party_member_info (
	id varchar(20) NOT NULL, -- 党员身份证号
	full_name varchar(20) NOT NULL, -- 党员姓名 
	unit_id int NOT NULL, -- 外键 
	sex char(2) NOT NULL CHECK(sex='男' or sex='女'), -- 男or女
	PRIMARY KEY(id),
	CONSTRAINT foreign_party_member_unit FOREIGN KEY(unit_id) REFERENCES cooperation_unit(unit_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
													
INSERT INTO party_member_info(id, full_name, unit_id, sex) VALUES
	('420100197203149514', '王立新', 1, '男'),
	('420100198302092589', '张小丽', 1, '女'),
	('420100198911212574', '赵梓华', 1, '男'),
	('350507199308011341', '周茜婉', 1, '女'),
	('140106199812212558', '许智超', 1, '男'),
	('630401196705110105', '喻凡'  , 2, '女'),
	('430305199012189552', '卫杰'  , 2, '男'),
	('520106198704134874', '刘汝佳', 3, '男'),
	('420301198812208422', '钱薇秋', 3, '女'),
	('450202197810034745', '章雨'  , 4, '女'),
	('440102198501278643', '严瑶'  , 4, '女'),
	('210501199511113349', '张亮莹', 5, '女'),
	('530403196711127455', '李建国', 5, '男'),
	('150407198103115339', '秦诚清', 6, '男'),
	('500204199608281267', '韩嘉茜', 6, '女'),
	('410204198212115895', '华辰宇', 7, '男'),
	('330507197205075146', '孙丽丽', 7, '女');	
	
	
CREATE TABLE service_activity (
	pid int NOT NULL AUTO_INCREMENT, -- 活动编号
	project_name varchar(100) NOT NULL, -- 活动名称
	content text NOT NULL, -- 活动内容
	begin_time datetime NOT NULL, -- 活动开始时间
	registration_deadline datetime NOT NULL, -- 报名截止日期
	recruitment_numbers smallint NOT NULL, -- 招募人数
	requirement varchar(100), -- 志愿者要求
	project_status varchar(20) DEFAULT '招募中', -- 活动状态：'招募中','进行中','已结束'
	PRIMARY KEY (pid)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;	

INSERT INTO service_activity(project_name, content, begin_time, registration_deadline, recruitment_numbers, requirement, project_status) VALUES
("老人防诈骗科普进社区",     "(1)活动开始前，联系社区相关工作人员确定活动形式、场地、时间等\n(2)提前准备好宣讲素材\n(3)进行场地布置，引导参加活动的人们入座\n(4)进行宣讲\n(5)引导参加人员有序退场\n(6)可制作小视频，手册等防诈骗科普内容传播于各大社交平台，如社区群等\n",
	"2020-12-26 16:00", "2020-12-25 21:00", 30, "热心善良，18岁以上", default),
("居家战役志愿服务活动", "作为社会志愿者开展防疫检测、家庭慰问、防疫物资捐赠等活动", "2021-01-02 10:00", "2021-01-01 21:00", 20, "热心公益，有一定社区服务经验", "已结束"),
("战“疫”物资搬运活动", "帮助社区搬运战“疫”物资（口罩，战疫纪念品等）到指定地点", "2021-01-06 10:00", "2021-01-05 21:00", 2, "热心善良", default),
("探望孤寡老人服务活动", "(1)活动开始前，联系社区相关工作人员确定探望老人的名单和地址\n(2)陪老人聊天\n(3)大家一起为老人做午餐\n", "2021-01-13 10:00", "2021-01-12 21:00", 10, "30岁以上", default),
("社区办公室坐班活动", "在社区办公室坐班，解决群众问题", "2021-01-13 9:00", "2021-01-12 21:00", 8, "有党支部工作经验者优先", default),
("社区路面清扫活动", "清扫社区内的若干条街道", "2021-01-20 9:00", "2021-01-19 23:00", 4, "党员优先", default);


CREATE TABLE activity_registration ( -- 服务活动报名表
	pid int NOT NULL, 
	volunteer_id varchar(20) NOT NULL, -- 报名者身份证号
	volunteer_name varchar(20) NOT NULL, -- 报名者姓名
	approval varchar(20) DEFAULT '待审核', -- '已通过'， '待审核'，'人数已满'
	volunteer_regist_time datetime NOT NULL DEFAULT Now(),
	PRIMARY KEY (pid,volunteer_id),
	CONSTRAINT forign_activity_reg_pid FOREIGN KEY(pid) REFERENCES service_activity(pid),
	CONSTRAINT foreign_activity_reg_vid FOREIGN KEY(volunteer_id) REFERENCES party_member_info(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO activity_registration(pid, volunteer_id, volunteer_name, volunteer_regist_time) VALUES
	(1, '420100197203149514', '王立新', "2020-12-21 15:40"),
	(2, '420100197203149514', '王立新', "2020-12-28 09:03"),
	(3, '420100197203149514', '王立新', "2021-01-04 19:37"),
	(4, '420100197203149514', '王立新', "2021-01-09 11:33"),
	(5, '420100197203149514', '王立新', "2021-01-05 20:41"),
	(6, '420100197203149514', '王立新', "2021-01-16 21:11"),
	(1, '420100198911212574', '赵梓华', "2020-12-21 16:01"),
	(3, '420100198911212574', '赵梓华', "2021-01-03 21:09"),
	(4, '420100198911212574', '赵梓华', "2021-01-10 08:30"),
	(5, '420100198911212574', '赵梓华', "2021-01-06 12:34"),
	(6, '420100198911212574', '赵梓华', "2021-01-15 13:38"),
	(3, '140106199812212558', '许智超', "2021-01-04 22:18"),
	(4, '140106199812212558', '许智超', "2021-01-12 09:36"),
	(5, '140106199812212558', '许智超', "2021-01-10 21:08"),
	(6, '140106199812212558', '许智超', "2021-01-15 07:28"),
	(4, '150407198103115339', '秦诚清', "2021-01-12 19:07"),
	(6, '150407198103115339', '秦诚清', "2021-01-18 14:05"),
	(5, '210501199511113349', '张亮莹', "2021-01-10 16:22"),
	(5, '330507197205075146', '孙丽丽', "2021-01-10 16:24"),
	(6, '350507199308011341', '周茜婉', "2021-01-18 08:38"),
	(6, '410204198212115895', '华辰宇', "2021-01-19 16:03"),
	(3, '420100198302092589', '张小丽', "2021-01-04 17:38"),
	(4, '420301198812208422', '钱薇秋', "2021-01-11 08:31");
	
	
/*
SELECT a.pid, project_name, begin_time, recruitment_numbers AS 'number', volunteer_id AS 'vid', volunteer_name AS 'vname', approval, volunteer_regist_time 
FROM service_activity a INNER JOIN activity_registation reg ON (a.pid = reg.pid)
ORDER BY a.pid, volunteer_regist_time;
*/

DROP PROCEDURE IF EXISTS pro_show_pass_regist;
delimiter $$
CREATE PROCEDURE pro_show_pass_regist(IN m_pid INT) 
	BEGIN
		DECLARE ask_cnt INT DEFAULT 0;
		SET ask_cnt = (SELECT recruitment_numbers FROM service_activity tba WHERE m_pid = tba.pid);
		SELECT * FROM activity_registation a
		WHERE a.pid = m_pid
		ORDER BY volunteer_regist_time
		LIMIT ask_cnt;
	END 
	$$
delimiter ;	

/*
CALL pro_show_pass_regist(6);
SELECT * FROM activity_registration WHERE pid = 6 ORDER BY volunteer_regist_time;
*/

DROP PROCEDURE IF EXISTS pro_enroll_regist;
delimiter $$
CREATE PROCEDURE pro_enroll_regist(IN m_pid INT) 
	BEGIN
		DECLARE ask_cnt INT DEFAULT 0;
		DECLARE has_passed_cnt INT DEFAULT 0;
		SET ask_cnt = (SELECT recruitment_numbers FROM service_activity tba WHERE m_pid = tba.pid);
		SET has_passed_cnt = (SELECT count(*) FROM activity_registration tba WHERE m_pid = tba.pid AND approval = '已通过');
		SET ask_cnt = IF(has_passed_cnt < ask_cnt, ask_cnt - has_passed_cnt, 0);
		UPDATE activity_registration tba 
		SET tba.approval =  
			(CASE WHEN tba.volunteer_id in (
				SELECT tmp1.volunteer_id FROM (
					SELECT a.volunteer_id
					FROM activity_registration a
					WHERE a.pid = m_pid AND a.approval = '待审核'
					ORDER BY volunteer_regist_time
					LIMIT ask_cnt
					) AS tmp1
				)	
				THEN '已通过' ELSE '人数已满' END
			)
		WHERE tba.pid = m_pid AND tba.approval = '待审核';
	END 
	$$
delimiter ;	

-- SELECT * FROM activity_registration ORDER BY pid, volunteer_regist_time;
CALL pro_enroll_regist(6);
CALL pro_enroll_regist(5);
CALL pro_enroll_regist(4);
CALL pro_enroll_regist(3);
CALL pro_enroll_regist(2);
CALL pro_enroll_regist(1);


CREATE TABLE activity_participation ( -- 活动参与情况表
	pid int NOT NULL, -- 活动编号（外键）
	vid varchar(20) NOT NULL, -- 志愿者(党员)身份证号（外键）
	attendance varchar(20) NOT NULL DEFAULT 'Yes', -- 是否出勤 '已参与'， '未参与'
	actual_duration double NOT NULL, -- 实际参与活动时长
	PRIMARY KEY (pid, vid),
	CONSTRAINT foreign_partic_pid FOREIGN KEY(pid) REFERENCES service_activity(pid),
	CONSTRAINT foreign_activity_part_vid FOREIGN KEY(vid) REFERENCES party_member_info(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO activity_participation(pid, vid, attendance, actual_duration) VALUES
	(1, '420100197203149514', default, 2),
	(2, '420100197203149514', default, 1.5),
	(4, '420100197203149514', default, 2),
	(5, '420100197203149514', default, 4),
	(6, '420100197203149514', default, 1),
	(1, '420100198911212574', default, 2),
	(3, '420100198911212574', default, 2),
	(4, '420100198911212574', default, 2),
	(5, '420100198911212574', default, 4),
	(6, '420100198911212574', default, 1),
	(4, '140106199812212558', default, 2),
	(5, '140106199812212558', default, 3),
	(6, '140106199812212558', default, 1),
	(4, '150407198103115339', default, 2),
	(5, '210501199511113349', default, 3),
	(5, '330507197205075146', default, 4),
	(6, '350507199308011341', default, 1),
	(3, '420100198302092589', 'No', 0),
	(4, '420301198812208422', default, 2);
	
/*	
	
-- 查询参与表中服务时长排行前三的党员
	
SELECT vid, full_name, sum(actual_duration) AS tot_hour FROM activity_participation p INNER JOIN party_member_info m ON (p.vid = m.id) WHERE attendance = 'Yes' GROUP BY vid ORDER BY tot_hour DESC LIMIT 3; 

-- 查询所有党员服务总时长，按照时长第一关键字降序，id第二关键字升序排

SELECT m.id, m.full_name, IFNULL(sum(actual_duration),0) AS tot_hour 
FROM party_member_info m LEFT OUTER JOIN activity_participation p ON (p.vid = m.id)WHERE p.attendance = 'Yes'
GROUP BY (m.id) 
ORDER BY tot_hour DESC, m.idLimit 3;


-- 查询所有与社区合作的党支部中所有党员服务总时长的排名

SELECT u.unit_id, u.unit_name, IFNULL(sum(tot_hour), 0) AS unit_tot_hour
FROM cooperation_unit u LEFT OUTER JOIN (
	SELECT m.id, m.unit_id, IFNULL(sum(actual_duration),0) AS tot_hour 
	FROM party_member_info m LEFT OUTER JOIN activity_participation p ON (p.vid = m.id)
	WHERE p.attendance = 'Yes'
	GROUP BY (m.id)
) vhour 
ON (u.unit_id = vhour.unit_id)
GROUP BY (u.unit_id)
ORDER BY unit_tot_hour DESC;

*/

DROP TABLE IF EXISTS temporary_access_application;

CREATE TABLE temporary_access_application(
	application_id int NOT NULL AUTO_INCREMENT COMMENT '主键自增',
	person_id varchar(20) NOT NULL COMMENT '申请人身份证号',
	person_name varchar(20) NOT NULL COMMENT '申请人姓名',
	phone_number varchar(20) NOT NULL COMMENT '申请人联系方式',
	reason varchar(100) NOT NULL COMMENT '申请出入社区的理由',
	car_number varchar(20) COMMENT '车牌号，没有则为null', 
	estimated_enter_time datetime NOT NULL COMMENT '预计进入社区时间',
	estimated_leave_time datetime NOT NULL COMMENT '预计离开社区时间',
	apply_commit_time datetime NOT NULL DEFAULT Now() COMMENT '申请提交时间', 
	approval varchar(100) NOT NULL DEFAULT '待审核' COMMENT '审核状态：待审核or通过or不通过[理由]',
	approval_manager_name varchar(20) COMMENT '审核人姓名',
	actual_enter_time datetime COMMENT '实际进入社区时间',
	actual_leave_time datetime COMMENT '实际离开社区时间',
	PRIMARY KEY(application_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

DELETE FROM temporary_access_application;
INSERT INTO temporary_access_application(person_id, person_name, phone_number, reason, car_number, estimated_enter_time, estimated_leave_time, apply_commit_time) VALUES
	('140105200108315472', '赵云', '18306547821', '探望亲人,3号楼401', null, '2021-03-10 10:00:00', '2021-03-10 20:30:00', '2021-03-07 11:39:00'),
	('	', '吕方', '17262998886', '维修社区水管', '鄂A72102', '2021-03-20 11:00:00', '2021-03-20 16:00:00', '2021-03-07 13:52:00');
	
	
	/*
SELECT pid, project_name, content, begin_time, registration_deadline,recruitment_numbers, requirement, project_status, 
CASE WHEN (SELECT COUNT(*)FROM activity_registration ar WHERE  volunteer_id = '140106199812212558' AND sa.pid = ar.pid)>0 THEN '已报名' ELSE '未报名' END AS 'hasRegist'
FROM service_activity sa;



SELECT pid, project_name, content, begin_time, registration_deadline,recruitment_numbers, requirement, project_status, 
CASE WHEN (SELECT COUNT(*)FROM activity_registration ar WHERE  volunteer_id = '140106199812212558' AND sa.pid = ar.pid)>0 THEN '已报名' ELSE '未报名' END AS 'hasRegist'
FROM service_activity sa
ORDER BY pid DESC
LIMIT 0, 10;
*/
	
	





	