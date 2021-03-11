use elife_db;

drop trigger if exists trig_update_order_after_insert_detail;
drop trigger if exists trig_update_logistic_orderid_after_insert_itself;
drop trigger if exists trig_update_logistic_last_state_after_insert_logistic_state;
-- drop trigger if exists trig_insert_logistic_state_after_insert_logistic;
drop trigger if exists trig_update_household_before_delete_user;
drop trigger if exists trig_update_household_after_insert_user;

drop table if exists logistic_state; -- 物流状态信息表
drop table if exists logistic; -- 物流状态表
drop table if exists express_address; -- 物流地址表
drop table if exists express_item_category; -- 物流寄件类型
drop table if exists courier; -- 快递员信息表
drop table if exists shopping_order_detail; -- 团购订单明细表
drop table if exists shopping_order; -- 团购订单表
drop table if exists product; -- 商品表
drop table if exists product_unit; -- 商品计量单位表
drop table if exists product_category; -- 商品类别表
drop table if exists parking; -- 车位信息表
drop table if exists payment; -- 缴费单表
drop table if exists remain; -- 余额表
drop table if exists complaint; -- 投诉单表
drop table if exists report_repair; -- 报修表
drop table if exists announcement; -- 公告表
drop table if exists notice; -- 通知表
drop table if exists user_info; -- 用户信息表
drop table if exists admin_info; -- 管理员信息表
drop table if exists household; -- 住户表


-- 住户表
;

create table household
(
	household_id int primary key auto_increment,
	building_number smallint not null, -- 楼栋号
	unit_number smallint not null, -- 单元号
	room_number smallint not null, -- 房间号
	parmanent_resident smallint not null, -- 常住人口数量
	user_id varchar(50) -- 这家的唯一注册用户的id
);

-- 管理员表
;

create table admin_info
(
	admin_name varchar(50) primary key,  -- 管理员登录名
	admin_password varchar(50) not null  -- 管理员登录密码
)

-- 用户表
;

create table user_info
(
	user_id varchar(50) primary key, -- 用户编号(登录)
	user_name varchar(20) not null,  -- 用户姓名 
	user_password varchar(50) not null,  -- 用户登录密码
	user_identity_number varchar(20) not null, -- 用户身份证号
	user_sex varchar(10) not null, -- 用户性别
	user_phone_number varchar(20) not null,  -- 用户手机号
	user_email varchar(50),  -- 用户电子邮箱(可以为空)
	household_id int not null,
	constraint forign_user_household foreign key(household_id) references household(household_id)
);

-- 通知表
;

create table notice
(
	notice_id int primary key auto_increment, -- 通知编号
	notice_type varchar(20) not null, -- 通知类型
	notice_scope int not null, -- 通知范围 0代表通知单个用户，1代表公告
	notice_user_id varchar(50) not null, -- 接收通知的用户
	notice_content text not null, -- 通知内容
	notice_date datetime not null -- 发出通知的日期时间
	#constraint forign_notice_uid foreign key(notice_user_id) references user_info(user_id)
);


-- 公告表
;

drop table if exists announcement;
create table announcement
(
	announcement_id int primary key auto_increment, -- 公告编号
	announcement_content text not null, -- 公告内容
	announcement_date datetime not null -- 公告发布的日期
);
insert into announcement values(null,'春节前夕，大家防疫工作仍然不能懈怠。请每户派一个代表来物业中心领取各家的免费口罩',now());
insert into announcement values(null,'元宵节请每户派一个代表来社区中心领汤圆',now());
insert into announcement values(null,'今天放假！请大家注意交通安全。',now());




-- 报修表
;

create table report_repair
(
	report_repair_id int primary key auto_increment, -- 报修流水号
	report_user_id varchar(50) not null, -- 报修业主编号
	report_problem varchar(100) not null, -- 报修的问题
	report_datetime datetime not null, -- 报修时间和日期
	report_state varchar(100) not null default('待处理'), -- 报修状态 -1 自己的原因，拒绝上门 0 收到报修请耐心等待处理  1已处理待上门维修 2已经上门维修完毕
	repair_finish_datetime datetime, -- 上门维修日期(非空表示已经维修成功)
	constraint forign_report_user foreign key(report_user_id) references user_info(user_id)
)

-- 投诉信息表
;

create table complaint
(
	complaint_id int primary key auto_increment, -- 投诉流水号
	complaint_user_id varchar(50) not null, -- 投诉的用户名
	complaint_type varchar(20) not null, -- 投诉的类型 '服务投诉', '价格投诉', '诚信投诉', '意外事故投诉'
	complaint_message text not null, -- 投诉内容
	phone_number varchar(20) not null, -- 联系方式
	complaint_date datetime not null, -- 投诉的日期
	complaint_state varchar(100) not null, -- 投诉的状态
	constraint forign_complaint_user foreign key(complaint_user_id) references user_info(user_id)
);

				
-- 余额表
;

create table remain
(
	id int primary key auto_increment, -- 余额id
	household_id int not null, -- 住户编号
	remain_type varchar(20) not null, -- 待缴费类型(水，电，煤气，物业，暖气)
	remain_amount numeric(10,2) not null, -- 余额
	constraint forign_remain_household foreign key(household_id) references household(household_id)
);


-- 缴费管理表
;

create table payment
(
	payment_id int primary key auto_increment, -- 缴费流水号
	payment_send_date datetime not null, -- 缴费通知的日期
	payment_deadline datetime not null, -- 缴费的截止日期
	payment_amount decimal(10,2) not null, -- 应当缴费的金额
	household_id int not null, -- 住户编号
	payment_type varchar(20) not null, -- 缴费类型 (水费，电费，煤气费，物业费)
	payment_state int not null default(0), -- 缴费状态: 0表示还没有缴费成功，1表示已经缴费成功
	payment_finish_time datetime, -- 用户完成缴费的日期
	constraint forign_payment_household foreign key(household_id) references household(household_id)
);




-- 车位表
;

create table parking 
(
	parking_id int primary key auto_increment, -- 车位号	
	parking_place_description varchar(50) not null, -- 车位描述 具体位置(几排几号)
	parking_car_number varchar(20) default('none') -- 停车的车牌号，如果没有车则为none
);

-- select parking_id,parking_place_description,parking_car_number from parking where 1=1 and parking_car_number like concat(concat('%','none'),'%')

-- select count(parking_id) from parking;


-- select * from parking where parking_id = 34;

-- update parking set parking_car_number = '晋A8765' where parking_id = 1;



-- 商品类别表
;

create table product_category  
(
	category_id int primary key auto_increment, -- 商品分类编号
	category_name varchar(20) not null -- 商品分类名称
);
-- '时令蔬菜'  1
-- '新鲜水果'  2
-- '酒水乳品'  3
-- '日用百货'  4
-- '优惠活动'  5
-- '其它类型'  6  


-- 商品计量单位表
;

create table product_unit
(
	id int primary key auto_increment, -- 商品计量单位编号
	unit varchar(20) not null -- 商品计量单位
)

-- 商品信息表
;

create table product
(
	product_id varchar(50) primary key, -- 商品编号(商品条码)
	product_name varchar(50) not null, -- 商品名称
	unit_price numeric(10,2) not null, -- 商品单价
	unit varchar(20) not null, -- 计量单位(没有设置外键，插入的时候判断)
	category_id int not null, -- (商品分类)外键
	constraint forign_product_categroy foreign key(category_id) references product_category(category_id)
)
#select count(product_id) from product;
#select product_id,product_name,unit_price,unit,tb1.category_id,category_name from product as tb1 left join product_category as tb2 on tb1.category_id=tb2.category_id;  
#select * from product as tb1 left join product_category as tb2 on tb1.category_id=tb2.category_id;
-- 团购订单表
;

create table shopping_order
(  
	shopping_order_id int primary key auto_increment, -- 团购订单表
	user_id varchar(50) not null,
	total_money numeric(10,2) not null default(0), -- 订单总价钱
	order_date datetime not null, -- 下单时间
	constraint forign_shopping_uid foreign key(user_id) references user_info(user_id)
)

-- 团购订单明细表
;

create table shopping_order_detail
(
  order_detail_id int primary key auto_increment, -- 团购订单明细流水号
  order_id int not null, -- 流水号（外键）
	product_id varchar(50) not null, -- 商品编号
	product_name varchar(50) not null, -- 商品名称
	unit_price numeric(10,2) not null, -- 商品单价 
	quantity int not null, -- 商品数量	
	product_unit varchar(10) not null, -- 商品计量单位
  subtotal numeric(10,2), -- 小计金额
	constraint forign_order_id foreign key(order_id) references shopping_order(shopping_order_id),
	constraint forign_order_product_id foreign key(product_id) references product(product_id)
)


-- 快递员表
;

create table courier
(
	courier_id int primary key auto_increment, -- 快递员编号
	courier_name varchar(20),  -- 快递员姓名
	courier_phone_number varchar(20) -- 快递员手机号
); 


-- 物流寄件类型表
;

create table express_item_category
(
	id int primary key auto_increment,
	item_category varchar(20) not null  -- 日用品 衣服 文件 生鲜 数码产品 其它
);

-- 物流地址表
;

create table express_address
(
	express_address_id int primary key auto_increment, -- 物流地址编号
	express_user_id varchar(50) not null, -- 用户编号(是哪个用户的地址信息)
	express_address_type int not null, -- 地址类型，0代表寄件地址，1代表收件地址
	express_name varchar(20) not null, -- 寄件人或收件人姓名
	express_phone_number varchar(20) not null, -- 寄件人或收件人手机号码
	express_province varchar(20) not null, -- 寄件人或收件人所在省份
	express_city varchar(20) not null, -- 寄件人或收件人所在市
	express_district varchar(20) not null, -- 寄件人或收件人所在区/县/乡
	express_postal_code varchar(20) not null, -- 寄件人或收件人所在地区的邮政编码
	express_detailed_address text not null, -- 寄件人或收件人的详细地址
	constraint forign_express_userid foreign key(express_user_id) references user_info(user_id)
);


-- 物流信息表
;

create table logistic
(
	logistic_id int primary key auto_increment, -- 物流订单号
	logistic_order_id varchar(100) not null default('loaded'), -- 物流订单号，由触发器设置，格式为下单日期+时间+编号2021-01-23_19:41:02_0001
	logistic_submit_date datetime not null, -- 用户下单时间  select date_format(now(),'%Y-%m-%d_%H:%i:%S');
	user_id varchar(50) not null, -- 下单的用户
	sender_name varchar(20) not null, -- 寄件人姓名
	logistic_type int not null, -- 寄件类型
	sender_phone_number varchar(20) not null, -- 寄件人手机号码
	sender_province varchar(20) not null, -- 寄件人所在省份
	sender_city varchar(20) not null, -- 寄件人所在市
	sender_district varchar(20) not null, -- 寄件人所在区/县/乡
	sender_postal_code varchar(20) not null, -- 寄件人邮政编码
	sender_detailed_address text not null, -- 寄件人详细地址	
	receiver_name varchar(20) not null, -- 收件人姓名
	receiver_phone_number varchar(20) not null, -- 收件人电话号码
	receiver_province varchar(20) not null,  -- 收件人所在省
	receiver_city varchar(20) not null, -- 收件人所在市
	receiver_district varchar(20) not null, -- 收件人所在区/县/乡
	receiver_postal_code varchar(20) not null, -- 收件人邮政编码
	receiver_detailed_address text not null, -- 收件人详细地址
	logistic_money numeric(10,2) not null, -- 寄件收费
	courier_id int, -- 处理订单的快递员(快递站工作人员)编号
	logistic_last_state varchar(100) default('待处理'), -- 物流状态信息 如'待处理', '已到达xxx中转站' '已成功签收'
	logistic_last_updatetime datetime, -- 物流状态信息最后更新的时间
	logistic_finish smallint not null default 0, -- 收件人是否已经成功签收，0 表示还未签收  1表示已经成功签收
	constraint forign_logistic_courierid foreign key(courier_id) references courier(courier_id),
	constraint forign_logistic_type foreign key(logistic_type) references express_item_category(id),
	constraint forign_logistic_uid foreign key(user_id) references user_info(user_id)
);



-- 物流状态信息表
;

create table logistic_state
(
	logistic_state_id int primary key auto_increment, -- 物流状态信息编号
	logistic_id int not null, -- 物流信息单号 外键
	logistic_state_time datetime not null, -- 状态的日期和时间
	logistic_state_description varchar(100) not null, -- '待处理'，'到达xxx中转站', '从xxx中转站中运出...'
	constraint forign_logistic_state_number foreign key(logistic_id) references logistic(logistic_id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- 触发器：在创建用户的之后，修改住户表记录对应的user_id
;

delimiter $$
create trigger trig_update_household_after_insert_user after insert on user_info for each row
begin
	declare uid varchar(50);
	declare hid int;
	set uid = NEW.user_id; 
	set hid = NEW.household_id;
	update household set user_id=uid where household_id=hid;
end $$
delimiter;

-- -- 触发器：在删除用户的之后，住户表将对应的user_id设置为null
;

delimiter $$
create trigger trig_update_household_before_delete_user after delete on user_info for each row
begin
	declare hid int;
	set hid = OLD.household_id;
	update household set user_id=null where household_id=hid;
end  $$
delimiter;



-- 触发器： 在插入物流状态信息后，更新物流订单表中的最新状态
;

delimiter $$
create trigger trig_update_logistic_last_state_after_insert_logistic_state after insert on logistic_state for each row
begin
	declare last_state varchar(100);
	declare cur_time datetime;
	declare lid int;
	set last_state = NEW.logistic_state_description;
	set cur_time = NEW.logistic_state_time;
	set lid = NEW.logistic_id;
	update logistic set logistic_last_state=last_state,logistic_last_updatetime=cur_time where logistic_id=lid;
	if last_state like "%已签收%" then
		update logistic set logistic_finish = 1 where logistic_id=lid;
	end if;
end  $$
delimiter ;

-- 触发器：在插入物流订单信息后，更新logistic_order_id为日期+时间+物流订单流水号
;

delimiter $$
create trigger trig_update_logistic_orderid_after_insert_itself before insert on logistic for each row
begin 
	declare lid int;
	declare addstr varchar(100);
	declare datee varchar(100);
	declare lidstr varchar(100);
	set lid = NEW.logistic_id;
	set lidstr = concat(lid,'');
	set addstr = concat((select date_format(now(),'%Y-%m-%d_%H:%i:%S')),lidstr);
	set NEW.logistic_order_id = addstr;
end $$
delimiter;

-- 触发器:初始插入的物流订单总价为0.每插入一条物流订单明细，就将小计金额累加道物流订单的总价上
;

delimiter $$
create trigger trig_update_order_after_insert_detail after insert on shopping_order_detail for each row
begin
	declare orderid int;
	declare submoney numeric(10,2);
	declare preTot numeric(10,2);
	set orderid = NEW.order_id;
	set submoney = NEW.subtotal;  
	set preTot = (select total_money from shopping_order where shopping_order_id=orderid);
	update shopping_order set total_money = preTot+submoney where shopping_order_id=orderid;

end $$
delimiter;



-- 假设有7栋楼，每栋楼有三个单元，每个单元有5层，每层有三个房间，设楼栋号为B，单元号为U,房间为D,楼层号F=R/100,层内序号为H,则
-- household_id = (B-1)*45 + (U-1)*15 + (F-1)*3 + H 
-- B = (household-1)/45+1
-- U = ((household-1)-B*45)/15+1
-- ...
;

insert into household(building_number,unit_number,room_number,parmanent_resident) values(1,1,101,4);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(1,1,102,4);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(1,1,103,3);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(1,1,201,1);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(1,1,202,4);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(1,1,203,5);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(1,1,301,3);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(1,1,302,4);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(1,1,303,4);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(1,1,401,3);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(1,1,402,5);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(1,1,403,2);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(1,1,501,4);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(1,1,502,2);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(1,1,503,5);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(1,2,101,3);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(1,2,102,3);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(1,2,103,3);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(1,2,201,2);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(1,2,202,4);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(1,2,203,3);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(1,2,301,1);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(1,2,302,5);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(1,2,303,4);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(1,2,401,3);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(1,2,402,2);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(1,2,403,1);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(1,2,501,1);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(1,2,502,1);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(1,2,503,2);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(1,3,101,5);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(1,3,102,5);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(1,3,103,1);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(1,3,201,2);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(1,3,202,3);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(1,3,203,5);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(1,3,301,2);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(1,3,302,1);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(1,3,303,5);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(1,3,401,3);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(1,3,402,2);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(1,3,403,2);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(1,3,501,2);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(1,3,502,4);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(1,3,503,4);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(2,1,101,5);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(2,1,102,1);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(2,1,103,3);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(2,1,201,1);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(2,1,202,5);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(2,1,203,3);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(2,1,301,2);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(2,1,302,5);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(2,1,303,4);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(2,1,401,5);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(2,1,402,3);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(2,1,403,1);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(2,1,501,2);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(2,1,502,1);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(2,1,503,3);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(2,2,101,4);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(2,2,102,3);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(2,2,103,2);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(2,2,201,2);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(2,2,202,5);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(2,2,203,4);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(2,2,301,1);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(2,2,302,5);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(2,2,303,5);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(2,2,401,3);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(2,2,402,5);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(2,2,403,3);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(2,2,501,5);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(2,2,502,3);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(2,2,503,3);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(2,3,101,3);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(2,3,102,5);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(2,3,103,1);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(2,3,201,2);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(2,3,202,4);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(2,3,203,3);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(2,3,301,4);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(2,3,302,4);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(2,3,303,2);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(2,3,401,4);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(2,3,402,1);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(2,3,403,3);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(2,3,501,5);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(2,3,502,3);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(2,3,503,1);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(3,1,101,1);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(3,1,102,3);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(3,1,103,3);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(3,1,201,4);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(3,1,202,5);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(3,1,203,5);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(3,1,301,1);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(3,1,302,2);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(3,1,303,1);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(3,1,401,4);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(3,1,402,1);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(3,1,403,3);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(3,1,501,4);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(3,1,502,3);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(3,1,503,2);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(3,2,101,3);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(3,2,102,2);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(3,2,103,4);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(3,2,201,4);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(3,2,202,1);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(3,2,203,3);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(3,2,301,2);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(3,2,302,5);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(3,2,303,4);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(3,2,401,1);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(3,2,402,3);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(3,2,403,4);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(3,2,501,1);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(3,2,502,4);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(3,2,503,5);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(3,3,101,2);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(3,3,102,4);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(3,3,103,1);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(3,3,201,3);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(3,3,202,5);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(3,3,203,4);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(3,3,301,4);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(3,3,302,4);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(3,3,303,4);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(3,3,401,1);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(3,3,402,5);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(3,3,403,5);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(3,3,501,2);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(3,3,502,3);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(3,3,503,3);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(4,1,101,2);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(4,1,102,5);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(4,1,103,1);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(4,1,201,4);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(4,1,202,4);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(4,1,203,4);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(4,1,301,1);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(4,1,302,3);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(4,1,303,1);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(4,1,401,1);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(4,1,402,1);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(4,1,403,1);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(4,1,501,1);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(4,1,502,3);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(4,1,503,2);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(4,2,101,5);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(4,2,102,3);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(4,2,103,3);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(4,2,201,5);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(4,2,202,3);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(4,2,203,2);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(4,2,301,4);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(4,2,302,3);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(4,2,303,2);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(4,2,401,4);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(4,2,402,1);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(4,2,403,4);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(4,2,501,2);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(4,2,502,3);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(4,2,503,2);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(4,3,101,3);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(4,3,102,2);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(4,3,103,1);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(4,3,201,3);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(4,3,202,3);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(4,3,203,5);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(4,3,301,3);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(4,3,302,3);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(4,3,303,1);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(4,3,401,2);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(4,3,402,1);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(4,3,403,4);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(4,3,501,2);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(4,3,502,3);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(4,3,503,4);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(5,1,101,5);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(5,1,102,2);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(5,1,103,3);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(5,1,201,4);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(5,1,202,1);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(5,1,203,2);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(5,1,301,1);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(5,1,302,2);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(5,1,303,5);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(5,1,401,1);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(5,1,402,1);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(5,1,403,1);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(5,1,501,5);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(5,1,502,2);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(5,1,503,4);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(5,2,101,2);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(5,2,102,3);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(5,2,103,2);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(5,2,201,5);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(5,2,202,2);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(5,2,203,2);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(5,2,301,1);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(5,2,302,3);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(5,2,303,1);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(5,2,401,2);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(5,2,402,1);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(5,2,403,5);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(5,2,501,1);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(5,2,502,5);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(5,2,503,1);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(5,3,101,5);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(5,3,102,5);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(5,3,103,1);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(5,3,201,3);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(5,3,202,1);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(5,3,203,2);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(5,3,301,4);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(5,3,302,4);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(5,3,303,1);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(5,3,401,2);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(5,3,402,1);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(5,3,403,5);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(5,3,501,3);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(5,3,502,5);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(5,3,503,4);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(6,1,101,1);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(6,1,102,2);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(6,1,103,1);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(6,1,201,2);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(6,1,202,5);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(6,1,203,1);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(6,1,301,3);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(6,1,302,1);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(6,1,303,2);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(6,1,401,3);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(6,1,402,3);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(6,1,403,1);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(6,1,501,2);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(6,1,502,5);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(6,1,503,5);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(6,2,101,1);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(6,2,102,1);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(6,2,103,5);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(6,2,201,5);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(6,2,202,3);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(6,2,203,3);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(6,2,301,4);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(6,2,302,1);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(6,2,303,3);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(6,2,401,2);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(6,2,402,5);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(6,2,403,1);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(6,2,501,4);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(6,2,502,4);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(6,2,503,1);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(6,3,101,1);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(6,3,102,3);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(6,3,103,4);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(6,3,201,2);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(6,3,202,5);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(6,3,203,5);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(6,3,301,2);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(6,3,302,4);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(6,3,303,1);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(6,3,401,2);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(6,3,402,2);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(6,3,403,4);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(6,3,501,5);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(6,3,502,1);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(6,3,503,5);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(7,1,101,4);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(7,1,102,1);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(7,1,103,5);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(7,1,201,5);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(7,1,202,1);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(7,1,203,4);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(7,1,301,3);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(7,1,302,3);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(7,1,303,5);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(7,1,401,1);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(7,1,402,2);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(7,1,403,5);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(7,1,501,1);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(7,1,502,5);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(7,1,503,3);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(7,2,101,5);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(7,2,102,3);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(7,2,103,2);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(7,2,201,5);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(7,2,202,3);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(7,2,203,4);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(7,2,301,4);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(7,2,302,5);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(7,2,303,5);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(7,2,401,2);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(7,2,402,2);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(7,2,403,4);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(7,2,501,2);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(7,2,502,5);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(7,2,503,1);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(7,3,101,5);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(7,3,102,3);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(7,3,103,5);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(7,3,201,3);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(7,3,202,2);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(7,3,203,4);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(7,3,301,5);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(7,3,302,4);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(7,3,303,2);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(7,3,401,3);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(7,3,402,3);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(7,3,403,1);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(7,3,501,4);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(7,3,502,4);
insert into household(building_number,unit_number,room_number,parmanent_resident) values(7,3,503,2);

insert into admin_info(admin_name,admin_password) values('曾超勇','zcy');
insert into admin_info(admin_name,admin_password) values('李千千','lqq');
insert into admin_info(admin_name,admin_password) values('许智超','xzc');
insert into admin_info(admin_name,admin_password) values('刘荣','lr');
insert into admin_info(admin_name,admin_password) values('刘雨琴','lyq');


insert into user_info values('Apare','许智超','123456','140106199812212558','男','18235151277','Apare_xzc@163.com',1);
insert into user_info values('Gsg','何山','a*&O{?uR[$08F','440203199503230715','男','16731916737','Gsg@163.com',53);
insert into user_info values('Pnvdfuu','韩梦','x~4DAw5a]P/C','350507199308011341','女','14729944570','Pnvdfuu@163.com',54);
insert into user_info values('Kctxfvd','姜娅婉','E<>%J}@2yz2bE#a','140106198907217066','女','18856794925','9196173250@qq.com',55);
insert into user_info values('Noujb','窦全','t4}BCybu+','150104197903060615','男','15596809070','Noujb@126.com',56);
insert into user_info values('Zfksej','戚旭元','$q(a8!OCEzq','54010119681120371X','男','15302476095','Zfksej@163.com',57);
insert into user_info values('Mcheox','喻凡','XHVaa|hjinn>,I','630401196705110105','女','19913651074','Mcheox@126.com',58);
insert into user_info values('Nalr','秦贵生','.Kua3gNWB>s+t','13040219700621883X','男','17894232699','Nalr@126.com',59);
insert into user_info values('Czy','华珍','kp&y!1w','370201196604160924','女','19706321836','Czy@126.com',60);
insert into user_info values('Ipkwzo','何诚','d6ACao}.XsH&T','540104197609255072','男','13678152689','Ipkwzo@126.com',61);
insert into user_info values('Hob','孔艳','F@>+-u5xVZkKP[-RE9v$','210104199206160362','女','14457743089','Hob@126.com',62);
insert into user_info values('Rfeqon','施珊','DVP-nAT','520301197209031422','女','13756011637','Rfeqon@126.com',63);
insert into user_info values('Bpzany','水梦','ja:Ja*q','450304198807240201','女','18907035710','Bpzany@163.com',64);
insert into user_info values('Bbsz','邹幂秋','0J;2%ai,<K)bKumSB','310405198005221168','女','18157368371','2069495147@qq.com',65);
insert into user_info values('Vvvxg','云发诚','X5y_Gax?}0vaIao]M-','630506199303312135','男','15046910635','Vvvxg@126.com',66);
insert into user_info values('Ltl','卫杰','{n(wtA','430305199012189552','男','15544240968','Ltl@126.com',67);
insert into user_info values('Mbr','郎珊','5vDmI]a:tX!J','620407196504020923','女','17411194071','Mbr@163.com',68);
insert into user_info values('Uqb','戚杰新','doIC{aHy/','140302197111222395','男','15083170755','7888391817@qq.com',69);
insert into user_info values('Ujk','范馨娆','k6@7N~pe,a_','440202197706260382','女','19788862218','Ujk@163.com',70);
insert into user_info values('Hasxwi','周山','H8@;fnX5F!nN','820207198301201555','男','15870337209','426360975@qq.com',71);
insert into user_info values('Bxb','谢茜婉','hD#|f[*4*;J','710407196812063549','女','16633622791','794443984@qq.com',72);
insert into user_info values('Tkbill','施梦','$s|LZZisMLN1e^sX','510502197306267801','女','18518066914','Tkbill@126.com',73);
insert into user_info values('Rhspml','柏江祥','4DXY9n?[]<06%h=^>#aE','350401197610137370','男','16612750599','8387995521@qq.com',74);
insert into user_info values('Gnzl','钱薇秋','1h2^Lv::L5','420301198812208422','女','14976839220','Gnzl@163.com',75);
insert into user_info values('Qtlds','何叔浩','2cxgS0Wd+]a','64040719960418751X','男','17324948712','Qtlds@163.com',76);
insert into user_info values('Yurzmd','孟秋','^/|qgs<d)o>,euI/]=n','540401198904014584','女','13918094150','Yurzmd@163.com',77);
insert into user_info values('Kebz','刘昆','qWtW&:p5a@aM%??eO','520106198704134874','男','15733901228','Kebz@163.com',78);
insert into user_info values('Mjqf','杨才江','UBh/@TnO.5E.[p.)Yin','370107197502158195','男','15954510272','Mjqf@163.com',79);
insert into user_info values('Jpff','许珍','fsa_|d(Z7)Y&Vp&S','310207197907244266','女','13661696878','Jpff@163.com',80);
insert into user_info values('Gtjb','章雨','h-]2a|','450202197810034745','女','17826047139','Gtjb@126.com',81);
insert into user_info values('Dihqhb','严瑶','Mu8PRaDJ/er80','440102198501278643','女','17019467473','Dihqhb@126.com',82);
insert into user_info values('Ealgxd','郎武清','67CMBI%R18aPa%Ja9C','320507196508245937','男','16647843854','3345949109@qq.com',83);
insert into user_info values('Ijoqyc','姜江全','|8;]/!j>kZX^6egw','46010219750910501X','男','14764529604','Ijoqyc@163.com',84);
insert into user_info values('Rln','严雨婷','%YT}-2-ItLX(','120207197110194747','女','17466873327','5666448318@qq.com',85);
insert into user_info values('Lilvb','刘姝艺','ei<NipL<6i^*','710407199809041382','女','17518841048','Lilvb@126.com',86);
insert into user_info values('Okob','曾琼梦','_HanX2<]@','23050419790118456X','女','17307986504','Okob@163.com',87);
insert into user_info values('Uxhvevy','钱茜','47?*aC','64010419740522992X','女','13204202815','Uxhvevy@126.com',88);
insert into user_info values('Jyy','章元','orbNsO;(;8E1xG=','420303199101098713','男','18671746406','005372325@qq.com',89);
insert into user_info values('Gye','李瑞','PaBHd|','340202199702283348','女','17690662477','Gye@163.com',90);
insert into user_info values('Krulc','杨旭龙','=Rffk/XZR>/E%mr','45020119970912801X','男','17524426500','Krulc@126.com',91);
insert into user_info values('Ilqoowi','严学','9[,^S*D@^v}_]-L%&7-','220507197103267219','男','15781782193','7155850327@qq.com',92);
insert into user_info values('Khfa','钱艺','axSJuB&a7@a','120401196605030882','女','13850175957','422426145@qq.com',93);
insert into user_info values('Mjsih','许幂瑞','qZ*fnau%OYu+q@D@e~C','640202197807159604','女','15236830998','Mjsih@163.com',94);
insert into user_info values('Yurr','魏仲','T%;gz&Rna)q:','220402198103189911','男','13929659455','Yurr@163.com',95);
insert into user_info values('Rpb','卫瑶婷','!T3h:!EuqYsCQs(34!92','630201199706223801','女','15667150229','Rpb@126.com',96);
insert into user_info values('Yjhkcrl','戚婷琼','hal&;ga1.DNT(e|aMB','42050419840217018X','女','17146595626','Yjhkcrl@126.com',97);
insert into user_info values('Jyviv','郑瑶','PL[=:I','210501199511113349','女','16699437052','Jyviv@163.com',98);
insert into user_info values('Psi','柏飞生','KNRJNiM|$XW','45030419790904051X','男','19058729627','Psi@126.com',99);
insert into user_info values('Bgghj','吕婉丹','4enY=V%','520306198710257109','女','16614712198','Bgghj@163.com',100);
insert into user_info values('Eeleql','吴季','pP^fb[a[c;ig;h}m;','530403196711127455','男','17732244739','Eeleql@163.com',101);
insert into user_info values('Lyraar','尤艺瑾','&(*,$%uS*Pda','410205197212139400','女','18132949520','Lyraar@126.com',102);
insert into user_info values('Ewp','金凡叶','/;Qm%?4}G#WUaX.lj%','130302196903302541','女','13558886981','186635962@qq.com',103);
insert into user_info values('Gbwp','刘嘉','i+p)]otCKPQ{?','220107197112189606','女','19076086364','Gbwp@163.com',104);
insert into user_info values('Kmquvdw','孟利子','i4jfo8{:','370503199005270896','男','14755590766','863976871@qq.com',105);
insert into user_info values('Plcjyzp','水凡','!B6KLI','370501198712147044','女','18413926732','137116927@qq.com',106);
insert into user_info values('Xzcnt','戚江','?oxJ#=2aWt)(0m4yq','450402198412285313','男','19122700898','6488757860@qq.com',107);
insert into user_info values('Xheke','王祖','3~K@a~PYwnrxnh*VuL+','360503197107023652','男','14948323508','Xheke@126.com',108);
insert into user_info values('Cjaa','施利旭','aami!fQk[Q.','210505199409159353','男','19009687783','Cjaa@163.com',109);
insert into user_info values('Bbp','朱海','u%<jinPH*VA$Dj','23050119700815975X','男','19389019520','Bbp@163.com',110);
insert into user_info values('Kfcdvsd','郎楠','t^V{4)Xwz','440304199303217340','女','19921082853','740935624@qq.com',111);
insert into user_info values('Hahrkre','奚昌','(*(>?fkRsC}l','650306197510167976','男','17036599795','8865579826@qq.com',112);
insert into user_info values('Zhdaqpt','潘琼婷','ieGWIo9v[','510201198908082764','女','15526760711','300815599@qq.com',113);
insert into user_info values('Tzaocb','谢祖昌','t=!yQ;{^36+Ly#/','110107196804046618','男','15251564655','Tzaocb@126.com',114);
insert into user_info values('Mypy','孙瑾叶','qK&aj%M+o}z8]e','430207198211294148','女','18183460770','654624153@qq.com',115);
insert into user_info values('Ospqrds','窦江','b4).aLZg<?)1uab:','150507198303051099','男','16716426926','Ospqrds@163.com',116);
insert into user_info values('Oem','尤海旭','Xv/ZaV/:paU7^i/','44020319720802891X','男','16694356803','1869242663@qq.com',117);
insert into user_info values('Uatuuib','周娆嫣',']~,Hi,3+x^A9V:L&~7','650504197610033304','女','18115029233','0167168406@qq.com',118);
insert into user_info values('Ppnufj','秦诚清','GqV<=eRA!D~!=Q14','150407198103115339','男','18126106371','Ppnufj@163.com',119);
insert into user_info values('Imzwvaz','韩嘉茜','/ht7?Ka%T#eH','500204199608281267','女','17328438488','Imzwvaz@163.com',120);
insert into user_info values('Agfk','尤莉','hsLm>7','220203199703209067','女','15575655851','Agfk@126.com',121);
insert into user_info values('Igqvcp','陈娆','-(NH!rc','460401199808068267','女','15789375991','211880350@qq.com',122);
insert into user_info values('Wkvbr','华昌','|+]}~q{4V}','410204198212115895','男','16701453619','430950005@qq.com',123);
insert into user_info values('Wrpzg','金元',';/b&v.T=','330107198502205733','男','18849690343','Wrpzg@163.com',124);
insert into user_info values('Qipvt','韩婉','f~eU)Ig^l;a','510202198908183402','女','17775069013','465578791@qq.com',125);
insert into user_info values('Xfhfztk','柏生','HfIBfSac','630306199203047714','男','19724071227','Xfhfztk@163.com',126);
insert into user_info values('Dkmilyf','吴杰新','k|]w]R7AoK|#N!','23010619870902289X','男','13328298541','Dkmilyf@126.com',127);
insert into user_info values('Ahw','施瑞','^=4ErhCObl,a3e','330507197205075146','女','17725319317','Ahw@163.com',128);
insert into user_info values('Ipupx','姜梁','}]A=5fJR6&839dG=','220507199301244335','男','14739280688','6206979529@qq.com',129);

insert into notice values(null,'物业通知',0,'Gsg','公共逃生通道不能放私人物品，请速清理',now());
insert into notice values(null,'物业通知',0,'Apare','公共逃生通道不能放私人物品，请速清理',now());
insert into notice values(null,'安全通知',1,'EveryOne','春节前夕，大家防疫工作仍然不能懈怠。请每户派一个代表来物业中心领取各家的免费口罩',now());

insert into announcement values(null,'春节前夕，大家防疫工作仍然不能懈怠。请每户派一个代表来物业中心领取各家的免费口罩',now());
insert into announcement values(null,'元宵节请每户派一个代表来社区中心领汤圆',now());
insert into announcement values(null,'今天放假！请大家注意交通安全。',now());
select * from announcement;

insert into report_repair values(null,'Czy','水管漏水',now(),default,null);

insert into complaint values(null,'Noujb','价格投诉','物业费涨价不合理','19706321836',now(),'待处理');
insert into complaint values(null,'Apare','诚信投诉','我家窗前的花被人搬走了','17362992495',now(),'待处理');
insert into complaint values(null,'Apare','价格投诉','煤气费太贵','17362992495',now(),'待处理');
insert into complaint values(null,'Apare','价格投诉','水费太贵','17362992495',now(),'待处理');
insert into complaint values(null,'Apare','价格投诉','天然气费太贵','17362992495',now(),'待处理');
insert into complaint values(null,'Apare','价格投诉','电费太贵','17362992495',now(),'待处理');
insert into complaint values(null,'Apare','价格投诉','物业费太贵','17362992495',now(),'待处理');
insert into complaint values(null,'Apare','物业投诉','楼道不整洁太贵','17362992495',now(),'待处理');


insert into payment values(null,now(),'2021-02-15',88.9,53,'电费',default,null);
insert into payment values(null,now(),'2021-02-15',100.5,1,'电费',default,null);
insert into payment values(null,now(),'2021-02-15',30.0, 1,'水费',default,null);
insert into payment values(null,now(),'2021-02-15',77,1,'天然气费',default,null);
insert into payment values(null,now(),'2021-02-14',62.3,1,'物业费',default,null);
insert into payment values(null,now(),'2021-02-25 18:00:00',38,1,'垃圾处理费',0,null);

insert into parking values(null,'1排1号',default);
insert into parking values(null,'1排2号',default);
insert into parking values(null,'1排3号','鄂R44918');
insert into parking values(null,'1排4号',default);
insert into parking values(null,'1排5号',default);
insert into parking values(null,'1排6号',default);
insert into parking values(null,'1排7号','鄂F77185');
insert into parking values(null,'1排8号',default);
insert into parking values(null,'1排9号','鄂A72102');
insert into parking values(null,'1排10号',default);
insert into parking values(null,'1排11号',default);
insert into parking values(null,'1排12号','鄂J82920');
insert into parking values(null,'1排13号',default);
insert into parking values(null,'1排14号','鄂M88277');
insert into parking values(null,'1排15号',default);
insert into parking values(null,'2排1号','鄂F67969');
insert into parking values(null,'2排2号',default);
insert into parking values(null,'2排3号',default);
insert into parking values(null,'2排4号','鄂A70584');
insert into parking values(null,'2排5号',default);
insert into parking values(null,'2排6号',default);
insert into parking values(null,'2排7号',default);
insert into parking values(null,'2排8号',default);
insert into parking values(null,'2排9号',default);
insert into parking values(null,'2排10号',default);
insert into parking values(null,'2排11号',default);
insert into parking values(null,'2排12号',default);
insert into parking values(null,'2排13号',default);
insert into parking values(null,'2排14号',default);
insert into parking values(null,'2排15号',default);
insert into parking values(null,'3排1号','鄂N87856');
insert into parking values(null,'3排2号','鄂E15145');
insert into parking values(null,'3排3号',default);
insert into parking values(null,'3排4号',default);
insert into parking values(null,'3排5号','鄂S15995');
insert into parking values(null,'3排6号',default);
insert into parking values(null,'3排7号',default);
insert into parking values(null,'3排8号',default);
insert into parking values(null,'3排9号',default);
insert into parking values(null,'3排10号',default);
insert into parking values(null,'3排11号',default);
insert into parking values(null,'3排12号',default);
insert into parking values(null,'3排13号',default);
insert into parking values(null,'3排14号','鄂A63375');
insert into parking values(null,'3排15号','鄂J53980');
insert into parking values(null,'4排1号',default);
insert into parking values(null,'4排2号',default);
insert into parking values(null,'4排3号','鄂A33853');
insert into parking values(null,'4排4号',default);
insert into parking values(null,'4排5号',default);
insert into parking values(null,'4排6号',default);
insert into parking values(null,'4排7号','鄂B36176');
insert into parking values(null,'4排8号','鄂E85769');
insert into parking values(null,'4排9号',default);
insert into parking values(null,'4排10号',default);
insert into parking values(null,'4排11号','鄂A90565');
insert into parking values(null,'4排12号',default);
insert into parking values(null,'4排13号',default);
insert into parking values(null,'4排14号',default);
insert into parking values(null,'4排15号',default);
insert into parking values(null,'5排1号','鄂K12030');
insert into parking values(null,'5排2号',default);
insert into parking values(null,'5排3号','鄂A93181');
insert into parking values(null,'5排4号','鄂K58639');
insert into parking values(null,'5排5号',default);
insert into parking values(null,'5排6号',default);
insert into parking values(null,'5排7号',default);
insert into parking values(null,'5排8号',default);
insert into parking values(null,'5排9号',default);
insert into parking values(null,'5排10号',default);
insert into parking values(null,'5排11号',default);
insert into parking values(null,'5排12号','鄂E64678');
insert into parking values(null,'5排13号','鄂H99414');
insert into parking values(null,'5排14号',default);
insert into parking values(null,'5排15号',default);
insert into parking values(null,'6排1号',default);
insert into parking values(null,'6排2号',default);
insert into parking values(null,'6排3号',default);
insert into parking values(null,'6排4号',default);
insert into parking values(null,'6排5号',default);
insert into parking values(null,'6排6号',default);
insert into parking values(null,'6排7号','鄂B67379');
insert into parking values(null,'6排8号',default);
insert into parking values(null,'6排9号','鄂S30382');
insert into parking values(null,'6排10号',default);
insert into parking values(null,'6排11号',default);
insert into parking values(null,'6排12号','鄂A38426');
insert into parking values(null,'6排13号',default);
insert into parking values(null,'6排14号',default);
insert into parking values(null,'6排15号',default);
insert into parking values(null,'7排1号','鄂G25908');
insert into parking values(null,'7排2号',default);
insert into parking values(null,'7排3号','鄂A45577');
insert into parking values(null,'7排4号','鄂A89268');
insert into parking values(null,'7排5号',default);
insert into parking values(null,'7排6号','鄂M67841');
insert into parking values(null,'7排7号',default);
insert into parking values(null,'7排8号',default);
insert into parking values(null,'7排9号',default);
insert into parking values(null,'7排10号',default);
insert into parking values(null,'7排11号','鄂D73864');
insert into parking values(null,'7排12号',default);
insert into parking values(null,'7排13号',default);
insert into parking values(null,'7排14号',default);
insert into parking values(null,'7排15号',default);
insert into parking values(null,'8排1号',default);
insert into parking values(null,'8排2号','鄂A81283');
insert into parking values(null,'8排3号','鄂B14504');
insert into parking values(null,'8排4号',default);
insert into parking values(null,'8排5号',default);
insert into parking values(null,'8排6号',default);
insert into parking values(null,'8排7号',default);
insert into parking values(null,'8排8号',default);
insert into parking values(null,'8排9号','鄂H37542');
insert into parking values(null,'8排10号',default);
insert into parking values(null,'8排11号',default);
insert into parking values(null,'8排12号',default);
insert into parking values(null,'8排13号','鄂N57989');
insert into parking values(null,'8排14号',default);
insert into parking values(null,'8排15号',default);
insert into parking values(null,'9排1号',default);
insert into parking values(null,'9排2号',default);
insert into parking values(null,'9排3号',default);
insert into parking values(null,'9排4号',default);
insert into parking values(null,'9排5号',default);
insert into parking values(null,'9排6号','鄂A25220');
insert into parking values(null,'9排7号','鄂L82003');
insert into parking values(null,'9排8号',default);
insert into parking values(null,'9排9号',default);
insert into parking values(null,'9排10号',default);
insert into parking values(null,'9排11号',default);
insert into parking values(null,'9排12号',default);
insert into parking values(null,'9排13号',default);
insert into parking values(null,'9排14号',default);
insert into parking values(null,'9排15号','鄂J61485');
insert into parking values(null,'10排1号','鄂S59291');
insert into parking values(null,'10排2号',default);
insert into parking values(null,'10排3号',default);
insert into parking values(null,'10排4号',default);
insert into parking values(null,'10排5号',default);
insert into parking values(null,'10排6号',default);
insert into parking values(null,'10排7号',default);
insert into parking values(null,'10排8号',default);
insert into parking values(null,'10排9号',default);
insert into parking values(null,'10排10号','鄂G31617');
insert into parking values(null,'10排11号',default);
insert into parking values(null,'10排12号',default);
insert into parking values(null,'10排13号',default);
insert into parking values(null,'10排14号','鄂A12525');
insert into parking values(null,'10排15号',default);
insert into parking values(null,'11排1号',default);
insert into parking values(null,'11排2号','鄂A64503');
insert into parking values(null,'11排3号','鄂A37004');
insert into parking values(null,'11排4号',default);
insert into parking values(null,'11排5号',default);
insert into parking values(null,'11排6号','鄂J71399');
insert into parking values(null,'11排7号',default);
insert into parking values(null,'11排8号',default);
insert into parking values(null,'11排9号','鄂E71990');
insert into parking values(null,'11排10号',default);
insert into parking values(null,'11排11号',default);
insert into parking values(null,'11排12号','鄂A67679');
insert into parking values(null,'11排13号','鄂L71396');
insert into parking values(null,'11排14号',default);
insert into parking values(null,'11排15号',default);
insert into parking values(null,'12排1号',default);
insert into parking values(null,'12排2号','鄂A31416');
insert into parking values(null,'12排3号',default);
insert into parking values(null,'12排4号',default);
insert into parking values(null,'12排5号',default);
insert into parking values(null,'12排6号','鄂A35415');
insert into parking values(null,'12排7号',default);
insert into parking values(null,'12排8号',default);
insert into parking values(null,'12排9号',default);
insert into parking values(null,'12排10号',default);
insert into parking values(null,'12排11号',default);
insert into parking values(null,'12排12号',default);
insert into parking values(null,'12排13号',default);
insert into parking values(null,'12排14号','鄂L76272');
insert into parking values(null,'12排15号',default);
insert into parking values(null,'13排1号',default);
insert into parking values(null,'13排2号',default);
insert into parking values(null,'13排3号','鄂A63577');
insert into parking values(null,'13排4号',default);
insert into parking values(null,'13排5号',default);
insert into parking values(null,'13排6号',default);
insert into parking values(null,'13排7号',default);
insert into parking values(null,'13排8号',default);
insert into parking values(null,'13排9号',default);
insert into parking values(null,'13排10号',default);
insert into parking values(null,'13排11号',default);
insert into parking values(null,'13排12号','鄂K77267');
insert into parking values(null,'13排13号',default);
insert into parking values(null,'13排14号','鄂A46925');
insert into parking values(null,'13排15号',default);
insert into parking values(null,'14排1号',default);
insert into parking values(null,'14排2号','鄂K86809');
insert into parking values(null,'14排3号',default);
insert into parking values(null,'14排4号',default);
insert into parking values(null,'14排5号',default);
insert into parking values(null,'14排6号','鄂E09793');
insert into parking values(null,'14排7号',default);
insert into parking values(null,'14排8号','鄂A37454');
insert into parking values(null,'14排9号','鄂M63367');
insert into parking values(null,'14排10号',default);
insert into parking values(null,'14排11号',default);
insert into parking values(null,'14排12号',default);
insert into parking values(null,'14排13号',default);
insert into parking values(null,'14排14号','鄂A06912');
insert into parking values(null,'14排15号','鄂R51614');
insert into parking values(null,'15排1号',default);
insert into parking values(null,'15排2号',default);
insert into parking values(null,'15排3号',default);
insert into parking values(null,'15排4号','鄂A54051');
insert into parking values(null,'15排5号','鄂A12130');
insert into parking values(null,'15排6号','鄂N47311');
insert into parking values(null,'15排7号',default);
insert into parking values(null,'15排8号',default);
insert into parking values(null,'15排9号','鄂S01909');
insert into parking values(null,'15排10号',default);
insert into parking values(null,'15排11号',default);
insert into parking values(null,'15排12号',default);
insert into parking values(null,'15排13号',default);
insert into parking values(null,'15排14号','鄂A22891');
insert into parking values(null,'15排15号',default);
insert into parking values(null,'16排1号',default);
insert into parking values(null,'16排2号','鄂H12241');
insert into parking values(null,'16排3号',default);
insert into parking values(null,'16排4号',default);
insert into parking values(null,'16排5号',default);
insert into parking values(null,'16排6号','鄂A80687');
insert into parking values(null,'16排7号','鄂A00110');
insert into parking values(null,'16排8号','鄂A35460');
insert into parking values(null,'16排9号',default);
insert into parking values(null,'16排10号',default);
insert into parking values(null,'16排11号','鄂A75688');
insert into parking values(null,'16排12号',default);
insert into parking values(null,'16排13号',default);
insert into parking values(null,'16排14号',default);
insert into parking values(null,'16排15号','鄂A30930');
insert into parking values(null,'17排1号','鄂N68200');
insert into parking values(null,'17排2号',default);
insert into parking values(null,'17排3号',default);
insert into parking values(null,'17排4号',default);
insert into parking values(null,'17排5号','鄂R45350');
insert into parking values(null,'17排6号',default);
insert into parking values(null,'17排7号','鄂C59769');
insert into parking values(null,'17排8号',default);
insert into parking values(null,'17排9号','鄂A98828');
insert into parking values(null,'17排10号',default);
insert into parking values(null,'17排11号',default);
insert into parking values(null,'17排12号','鄂E68459');
insert into parking values(null,'17排13号',default);
insert into parking values(null,'17排14号','鄂A82540');
insert into parking values(null,'17排15号',default);
insert into parking values(null,'18排1号',default);
insert into parking values(null,'18排2号',default);
insert into parking values(null,'18排3号',default);
insert into parking values(null,'18排4号','鄂K30468');
insert into parking values(null,'18排5号',default);
insert into parking values(null,'18排6号',default);
insert into parking values(null,'18排7号',default);
insert into parking values(null,'18排8号',default);
insert into parking values(null,'18排9号',default);
insert into parking values(null,'18排10号',default);
insert into parking values(null,'18排11号',default);
insert into parking values(null,'18排12号','鄂J32944');
insert into parking values(null,'18排13号',default);
insert into parking values(null,'18排14号',default);
insert into parking values(null,'18排15号',default);
insert into parking values(null,'19排1号',default);
insert into parking values(null,'19排2号','鄂H45780');
insert into parking values(null,'19排3号',default);
insert into parking values(null,'19排4号',default);
insert into parking values(null,'19排5号','鄂C13463');
insert into parking values(null,'19排6号',default);
insert into parking values(null,'19排7号',default);
insert into parking values(null,'19排8号',default);
insert into parking values(null,'19排9号',default);
insert into parking values(null,'19排10号',default);
insert into parking values(null,'19排11号','鄂A12035');
insert into parking values(null,'19排12号','鄂M66902');
insert into parking values(null,'19排13号',default);
insert into parking values(null,'19排14号',default);
insert into parking values(null,'19排15号','鄂L12133');
insert into parking values(null,'20排1号',default);
insert into parking values(null,'20排2号',default);
insert into parking values(null,'20排3号',default);
insert into parking values(null,'20排4号',default);
insert into parking values(null,'20排5号',default);
insert into parking values(null,'20排6号',default);
insert into parking values(null,'20排7号',default);
insert into parking values(null,'20排8号',default);
insert into parking values(null,'20排9号',default);
insert into parking values(null,'20排10号',default);
insert into parking values(null,'20排11号','鄂A27301');
insert into parking values(null,'20排12号','鄂A35967');
insert into parking values(null,'20排13号',default);
insert into parking values(null,'20排14号',default);
insert into parking values(null,'20排15号',default);


insert into remain values(null,1,'水费',0);
insert into remain values(null,1,'电费',0);
insert into remain values(null,1,'天然气费',0);
insert into remain values(null,1,'物业费',0);
insert into remain values(null,2,'水费',0);
insert into remain values(null,2,'电费',0);
insert into remain values(null,2,'天然气费',0);
insert into remain values(null,2,'物业费',0);
insert into remain values(null,3,'水费',0);
insert into remain values(null,3,'电费',0);
insert into remain values(null,3,'天然气费',0);
insert into remain values(null,3,'物业费',0);
insert into remain values(null,4,'水费',0);
insert into remain values(null,4,'电费',0);
insert into remain values(null,4,'天然气费',0);
insert into remain values(null,4,'物业费',0);
insert into remain values(null,5,'水费',0);
insert into remain values(null,5,'电费',0);
insert into remain values(null,5,'天然气费',0);
insert into remain values(null,5,'物业费',0);
insert into remain values(null,6,'水费',0);
insert into remain values(null,6,'电费',0);
insert into remain values(null,6,'天然气费',0);
insert into remain values(null,6,'物业费',0);
insert into remain values(null,7,'水费',0);
insert into remain values(null,7,'电费',0);
insert into remain values(null,7,'天然气费',0);
insert into remain values(null,7,'物业费',0);
insert into remain values(null,8,'水费',0);
insert into remain values(null,8,'电费',0);
insert into remain values(null,8,'天然气费',0);
insert into remain values(null,8,'物业费',0);
insert into remain values(null,9,'水费',0);
insert into remain values(null,9,'电费',0);
insert into remain values(null,9,'天然气费',0);
insert into remain values(null,9,'物业费',0);
insert into remain values(null,10,'水费',0);
insert into remain values(null,10,'电费',0);
insert into remain values(null,10,'天然气费',0);
insert into remain values(null,10,'物业费',0);
insert into remain values(null,11,'水费',0);
insert into remain values(null,11,'电费',0);
insert into remain values(null,11,'天然气费',0);
insert into remain values(null,11,'物业费',0);
insert into remain values(null,12,'水费',0);
insert into remain values(null,12,'电费',0);
insert into remain values(null,12,'天然气费',0);
insert into remain values(null,12,'物业费',0);
insert into remain values(null,13,'水费',0);
insert into remain values(null,13,'电费',0);
insert into remain values(null,13,'天然气费',0);
insert into remain values(null,13,'物业费',0);
insert into remain values(null,14,'水费',0);
insert into remain values(null,14,'电费',0);
insert into remain values(null,14,'天然气费',0);
insert into remain values(null,14,'物业费',0);
insert into remain values(null,15,'水费',0);
insert into remain values(null,15,'电费',0);
insert into remain values(null,15,'天然气费',0);
insert into remain values(null,15,'物业费',0);
insert into remain values(null,16,'水费',0);
insert into remain values(null,16,'电费',0);
insert into remain values(null,16,'天然气费',0);
insert into remain values(null,16,'物业费',0);
insert into remain values(null,17,'水费',0);
insert into remain values(null,17,'电费',0);
insert into remain values(null,17,'天然气费',0);
insert into remain values(null,17,'物业费',0);
insert into remain values(null,18,'水费',0);
insert into remain values(null,18,'电费',0);
insert into remain values(null,18,'天然气费',0);
insert into remain values(null,18,'物业费',0);
insert into remain values(null,19,'水费',0);
insert into remain values(null,19,'电费',0);
insert into remain values(null,19,'天然气费',0);
insert into remain values(null,19,'物业费',0);
insert into remain values(null,20,'水费',0);
insert into remain values(null,20,'电费',0);
insert into remain values(null,20,'天然气费',0);
insert into remain values(null,20,'物业费',0);
insert into remain values(null,21,'水费',0);
insert into remain values(null,21,'电费',0);
insert into remain values(null,21,'天然气费',0);
insert into remain values(null,21,'物业费',0);
insert into remain values(null,22,'水费',0);
insert into remain values(null,22,'电费',0);
insert into remain values(null,22,'天然气费',0);
insert into remain values(null,22,'物业费',0);
insert into remain values(null,23,'水费',0);
insert into remain values(null,23,'电费',0);
insert into remain values(null,23,'天然气费',0);
insert into remain values(null,23,'物业费',0);
insert into remain values(null,24,'水费',0);
insert into remain values(null,24,'电费',0);
insert into remain values(null,24,'天然气费',0);
insert into remain values(null,24,'物业费',0);
insert into remain values(null,25,'水费',0);
insert into remain values(null,25,'电费',0);
insert into remain values(null,25,'天然气费',0);
insert into remain values(null,25,'物业费',0);
insert into remain values(null,26,'水费',0);
insert into remain values(null,26,'电费',0);
insert into remain values(null,26,'天然气费',0);
insert into remain values(null,26,'物业费',0);
insert into remain values(null,27,'水费',0);
insert into remain values(null,27,'电费',0);
insert into remain values(null,27,'天然气费',0);
insert into remain values(null,27,'物业费',0);
insert into remain values(null,28,'水费',0);
insert into remain values(null,28,'电费',0);
insert into remain values(null,28,'天然气费',0);
insert into remain values(null,28,'物业费',0);
insert into remain values(null,29,'水费',0);
insert into remain values(null,29,'电费',0);
insert into remain values(null,29,'天然气费',0);
insert into remain values(null,29,'物业费',0);
insert into remain values(null,30,'水费',0);
insert into remain values(null,30,'电费',0);
insert into remain values(null,30,'天然气费',0);
insert into remain values(null,30,'物业费',0);
insert into remain values(null,31,'水费',0);
insert into remain values(null,31,'电费',0);
insert into remain values(null,31,'天然气费',0);
insert into remain values(null,31,'物业费',0);
insert into remain values(null,32,'水费',0);
insert into remain values(null,32,'电费',0);
insert into remain values(null,32,'天然气费',0);
insert into remain values(null,32,'物业费',0);
insert into remain values(null,33,'水费',0);
insert into remain values(null,33,'电费',0);
insert into remain values(null,33,'天然气费',0);
insert into remain values(null,33,'物业费',0);
insert into remain values(null,34,'水费',0);
insert into remain values(null,34,'电费',0);
insert into remain values(null,34,'天然气费',0);
insert into remain values(null,34,'物业费',0);
insert into remain values(null,35,'水费',0);
insert into remain values(null,35,'电费',0);
insert into remain values(null,35,'天然气费',0);
insert into remain values(null,35,'物业费',0);
insert into remain values(null,36,'水费',0);
insert into remain values(null,36,'电费',0);
insert into remain values(null,36,'天然气费',0);
insert into remain values(null,36,'物业费',0);
insert into remain values(null,37,'水费',0);
insert into remain values(null,37,'电费',0);
insert into remain values(null,37,'天然气费',0);
insert into remain values(null,37,'物业费',0);
insert into remain values(null,38,'水费',0);
insert into remain values(null,38,'电费',0);
insert into remain values(null,38,'天然气费',0);
insert into remain values(null,38,'物业费',0);
insert into remain values(null,39,'水费',0);
insert into remain values(null,39,'电费',0);
insert into remain values(null,39,'天然气费',0);
insert into remain values(null,39,'物业费',0);
insert into remain values(null,40,'水费',0);
insert into remain values(null,40,'电费',0);
insert into remain values(null,40,'天然气费',0);
insert into remain values(null,40,'物业费',0);
insert into remain values(null,41,'水费',0);
insert into remain values(null,41,'电费',0);
insert into remain values(null,41,'天然气费',0);
insert into remain values(null,41,'物业费',0);
insert into remain values(null,42,'水费',0);
insert into remain values(null,42,'电费',0);
insert into remain values(null,42,'天然气费',0);
insert into remain values(null,42,'物业费',0);
insert into remain values(null,43,'水费',0);
insert into remain values(null,43,'电费',0);
insert into remain values(null,43,'天然气费',0);
insert into remain values(null,43,'物业费',0);
insert into remain values(null,44,'水费',0);
insert into remain values(null,44,'电费',0);
insert into remain values(null,44,'天然气费',0);
insert into remain values(null,44,'物业费',0);
insert into remain values(null,45,'水费',0);
insert into remain values(null,45,'电费',0);
insert into remain values(null,45,'天然气费',0);
insert into remain values(null,45,'物业费',0);
insert into remain values(null,46,'水费',0);
insert into remain values(null,46,'电费',0);
insert into remain values(null,46,'天然气费',0);
insert into remain values(null,46,'物业费',0);
insert into remain values(null,47,'水费',0);
insert into remain values(null,47,'电费',0);
insert into remain values(null,47,'天然气费',0);
insert into remain values(null,47,'物业费',0);
insert into remain values(null,48,'水费',0);
insert into remain values(null,48,'电费',0);
insert into remain values(null,48,'天然气费',0);
insert into remain values(null,48,'物业费',0);
insert into remain values(null,49,'水费',0);
insert into remain values(null,49,'电费',0);
insert into remain values(null,49,'天然气费',0);
insert into remain values(null,49,'物业费',0);
insert into remain values(null,50,'水费',0);
insert into remain values(null,50,'电费',0);
insert into remain values(null,50,'天然气费',0);
insert into remain values(null,50,'物业费',0);
insert into remain values(null,51,'水费',0);
insert into remain values(null,51,'电费',0);
insert into remain values(null,51,'天然气费',0);
insert into remain values(null,51,'物业费',0);
insert into remain values(null,52,'水费',0);
insert into remain values(null,52,'电费',0);
insert into remain values(null,52,'天然气费',0);
insert into remain values(null,52,'物业费',0);
insert into remain values(null,53,'水费',0);
insert into remain values(null,53,'电费',0);
insert into remain values(null,53,'天然气费',0);
insert into remain values(null,53,'物业费',0);
insert into remain values(null,54,'水费',0);
insert into remain values(null,54,'电费',0);
insert into remain values(null,54,'天然气费',0);
insert into remain values(null,54,'物业费',0);
insert into remain values(null,55,'水费',0);
insert into remain values(null,55,'电费',0);
insert into remain values(null,55,'天然气费',0);
insert into remain values(null,55,'物业费',0);
insert into remain values(null,56,'水费',0);
insert into remain values(null,56,'电费',0);
insert into remain values(null,56,'天然气费',0);
insert into remain values(null,56,'物业费',0);
insert into remain values(null,57,'水费',0);
insert into remain values(null,57,'电费',0);
insert into remain values(null,57,'天然气费',0);
insert into remain values(null,57,'物业费',0);
insert into remain values(null,58,'水费',0);
insert into remain values(null,58,'电费',0);
insert into remain values(null,58,'天然气费',0);
insert into remain values(null,58,'物业费',0);
insert into remain values(null,59,'水费',0);
insert into remain values(null,59,'电费',0);
insert into remain values(null,59,'天然气费',0);
insert into remain values(null,59,'物业费',0);
insert into remain values(null,60,'水费',0);
insert into remain values(null,60,'电费',0);
insert into remain values(null,60,'天然气费',0);
insert into remain values(null,60,'物业费',0);
insert into remain values(null,61,'水费',0);
insert into remain values(null,61,'电费',0);
insert into remain values(null,61,'天然气费',0);
insert into remain values(null,61,'物业费',0);
insert into remain values(null,62,'水费',0);
insert into remain values(null,62,'电费',0);
insert into remain values(null,62,'天然气费',0);
insert into remain values(null,62,'物业费',0);
insert into remain values(null,63,'水费',0);
insert into remain values(null,63,'电费',0);
insert into remain values(null,63,'天然气费',0);
insert into remain values(null,63,'物业费',0);
insert into remain values(null,64,'水费',0);
insert into remain values(null,64,'电费',0);
insert into remain values(null,64,'天然气费',0);
insert into remain values(null,64,'物业费',0);
insert into remain values(null,65,'水费',0);
insert into remain values(null,65,'电费',0);
insert into remain values(null,65,'天然气费',0);
insert into remain values(null,65,'物业费',0);
insert into remain values(null,66,'水费',0);
insert into remain values(null,66,'电费',0);
insert into remain values(null,66,'天然气费',0);
insert into remain values(null,66,'物业费',0);
insert into remain values(null,67,'水费',0);
insert into remain values(null,67,'电费',0);
insert into remain values(null,67,'天然气费',0);
insert into remain values(null,67,'物业费',0);
insert into remain values(null,68,'水费',0);
insert into remain values(null,68,'电费',0);
insert into remain values(null,68,'天然气费',0);
insert into remain values(null,68,'物业费',0);
insert into remain values(null,69,'水费',0);
insert into remain values(null,69,'电费',0);
insert into remain values(null,69,'天然气费',0);
insert into remain values(null,69,'物业费',0);
insert into remain values(null,70,'水费',0);
insert into remain values(null,70,'电费',0);
insert into remain values(null,70,'天然气费',0);
insert into remain values(null,70,'物业费',0);
insert into remain values(null,71,'水费',0);
insert into remain values(null,71,'电费',0);
insert into remain values(null,71,'天然气费',0);
insert into remain values(null,71,'物业费',0);
insert into remain values(null,72,'水费',0);
insert into remain values(null,72,'电费',0);
insert into remain values(null,72,'天然气费',0);
insert into remain values(null,72,'物业费',0);
insert into remain values(null,73,'水费',0);
insert into remain values(null,73,'电费',0);
insert into remain values(null,73,'天然气费',0);
insert into remain values(null,73,'物业费',0);
insert into remain values(null,74,'水费',0);
insert into remain values(null,74,'电费',0);
insert into remain values(null,74,'天然气费',0);
insert into remain values(null,74,'物业费',0);
insert into remain values(null,75,'水费',0);
insert into remain values(null,75,'电费',0);
insert into remain values(null,75,'天然气费',0);
insert into remain values(null,75,'物业费',0);
insert into remain values(null,76,'水费',0);
insert into remain values(null,76,'电费',0);
insert into remain values(null,76,'天然气费',0);
insert into remain values(null,76,'物业费',0);
insert into remain values(null,77,'水费',0);
insert into remain values(null,77,'电费',0);
insert into remain values(null,77,'天然气费',0);
insert into remain values(null,77,'物业费',0);
insert into remain values(null,78,'水费',0);
insert into remain values(null,78,'电费',0);
insert into remain values(null,78,'天然气费',0);
insert into remain values(null,78,'物业费',0);
insert into remain values(null,79,'水费',0);
insert into remain values(null,79,'电费',0);
insert into remain values(null,79,'天然气费',0);
insert into remain values(null,79,'物业费',0);
insert into remain values(null,80,'水费',0);
insert into remain values(null,80,'电费',0);
insert into remain values(null,80,'天然气费',0);
insert into remain values(null,80,'物业费',0);
insert into remain values(null,81,'水费',0);
insert into remain values(null,81,'电费',0);
insert into remain values(null,81,'天然气费',0);
insert into remain values(null,81,'物业费',0);
insert into remain values(null,82,'水费',0);
insert into remain values(null,82,'电费',0);
insert into remain values(null,82,'天然气费',0);
insert into remain values(null,82,'物业费',0);
insert into remain values(null,83,'水费',0);
insert into remain values(null,83,'电费',0);
insert into remain values(null,83,'天然气费',0);
insert into remain values(null,83,'物业费',0);
insert into remain values(null,84,'水费',0);
insert into remain values(null,84,'电费',0);
insert into remain values(null,84,'天然气费',0);
insert into remain values(null,84,'物业费',0);
insert into remain values(null,85,'水费',0);
insert into remain values(null,85,'电费',0);
insert into remain values(null,85,'天然气费',0);
insert into remain values(null,85,'物业费',0);
insert into remain values(null,86,'水费',0);
insert into remain values(null,86,'电费',0);
insert into remain values(null,86,'天然气费',0);
insert into remain values(null,86,'物业费',0);
insert into remain values(null,87,'水费',0);
insert into remain values(null,87,'电费',0);
insert into remain values(null,87,'天然气费',0);
insert into remain values(null,87,'物业费',0);
insert into remain values(null,88,'水费',0);
insert into remain values(null,88,'电费',0);
insert into remain values(null,88,'天然气费',0);
insert into remain values(null,88,'物业费',0);
insert into remain values(null,89,'水费',0);
insert into remain values(null,89,'电费',0);
insert into remain values(null,89,'天然气费',0);
insert into remain values(null,89,'物业费',0);
insert into remain values(null,90,'水费',0);
insert into remain values(null,90,'电费',0);
insert into remain values(null,90,'天然气费',0);
insert into remain values(null,90,'物业费',0);
insert into remain values(null,91,'水费',0);
insert into remain values(null,91,'电费',0);
insert into remain values(null,91,'天然气费',0);
insert into remain values(null,91,'物业费',0);
insert into remain values(null,92,'水费',0);
insert into remain values(null,92,'电费',0);
insert into remain values(null,92,'天然气费',0);
insert into remain values(null,92,'物业费',0);
insert into remain values(null,93,'水费',0);
insert into remain values(null,93,'电费',0);
insert into remain values(null,93,'天然气费',0);
insert into remain values(null,93,'物业费',0);
insert into remain values(null,94,'水费',0);
insert into remain values(null,94,'电费',0);
insert into remain values(null,94,'天然气费',0);
insert into remain values(null,94,'物业费',0);
insert into remain values(null,95,'水费',0);
insert into remain values(null,95,'电费',0);
insert into remain values(null,95,'天然气费',0);
insert into remain values(null,95,'物业费',0);
insert into remain values(null,96,'水费',0);
insert into remain values(null,96,'电费',0);
insert into remain values(null,96,'天然气费',0);
insert into remain values(null,96,'物业费',0);
insert into remain values(null,97,'水费',0);
insert into remain values(null,97,'电费',0);
insert into remain values(null,97,'天然气费',0);
insert into remain values(null,97,'物业费',0);
insert into remain values(null,98,'水费',0);
insert into remain values(null,98,'电费',0);
insert into remain values(null,98,'天然气费',0);
insert into remain values(null,98,'物业费',0);
insert into remain values(null,99,'水费',0);
insert into remain values(null,99,'电费',0);
insert into remain values(null,99,'天然气费',0);
insert into remain values(null,99,'物业费',0);
insert into remain values(null,100,'水费',0);
insert into remain values(null,100,'电费',0);
insert into remain values(null,100,'天然气费',0);
insert into remain values(null,100,'物业费',0);
insert into remain values(null,101,'水费',0);
insert into remain values(null,101,'电费',0);
insert into remain values(null,101,'天然气费',0);
insert into remain values(null,101,'物业费',0);
insert into remain values(null,102,'水费',0);
insert into remain values(null,102,'电费',0);
insert into remain values(null,102,'天然气费',0);
insert into remain values(null,102,'物业费',0);
insert into remain values(null,103,'水费',0);
insert into remain values(null,103,'电费',0);
insert into remain values(null,103,'天然气费',0);
insert into remain values(null,103,'物业费',0);
insert into remain values(null,104,'水费',0);
insert into remain values(null,104,'电费',0);
insert into remain values(null,104,'天然气费',0);
insert into remain values(null,104,'物业费',0);
insert into remain values(null,105,'水费',0);
insert into remain values(null,105,'电费',0);
insert into remain values(null,105,'天然气费',0);
insert into remain values(null,105,'物业费',0);
insert into remain values(null,106,'水费',0);
insert into remain values(null,106,'电费',0);
insert into remain values(null,106,'天然气费',0);
insert into remain values(null,106,'物业费',0);
insert into remain values(null,107,'水费',0);
insert into remain values(null,107,'电费',0);
insert into remain values(null,107,'天然气费',0);
insert into remain values(null,107,'物业费',0);
insert into remain values(null,108,'水费',0);
insert into remain values(null,108,'电费',0);
insert into remain values(null,108,'天然气费',0);
insert into remain values(null,108,'物业费',0);
insert into remain values(null,109,'水费',0);
insert into remain values(null,109,'电费',0);
insert into remain values(null,109,'天然气费',0);
insert into remain values(null,109,'物业费',0);
insert into remain values(null,110,'水费',0);
insert into remain values(null,110,'电费',0);
insert into remain values(null,110,'天然气费',0);
insert into remain values(null,110,'物业费',0);
insert into remain values(null,111,'水费',0);
insert into remain values(null,111,'电费',0);
insert into remain values(null,111,'天然气费',0);
insert into remain values(null,111,'物业费',0);
insert into remain values(null,112,'水费',0);
insert into remain values(null,112,'电费',0);
insert into remain values(null,112,'天然气费',0);
insert into remain values(null,112,'物业费',0);
insert into remain values(null,113,'水费',0);
insert into remain values(null,113,'电费',0);
insert into remain values(null,113,'天然气费',0);
insert into remain values(null,113,'物业费',0);
insert into remain values(null,114,'水费',0);
insert into remain values(null,114,'电费',0);
insert into remain values(null,114,'天然气费',0);
insert into remain values(null,114,'物业费',0);
insert into remain values(null,115,'水费',0);
insert into remain values(null,115,'电费',0);
insert into remain values(null,115,'天然气费',0);
insert into remain values(null,115,'物业费',0);
insert into remain values(null,116,'水费',0);
insert into remain values(null,116,'电费',0);
insert into remain values(null,116,'天然气费',0);
insert into remain values(null,116,'物业费',0);
insert into remain values(null,117,'水费',0);
insert into remain values(null,117,'电费',0);
insert into remain values(null,117,'天然气费',0);
insert into remain values(null,117,'物业费',0);
insert into remain values(null,118,'水费',0);
insert into remain values(null,118,'电费',0);
insert into remain values(null,118,'天然气费',0);
insert into remain values(null,118,'物业费',0);
insert into remain values(null,119,'水费',0);
insert into remain values(null,119,'电费',0);
insert into remain values(null,119,'天然气费',0);
insert into remain values(null,119,'物业费',0);
insert into remain values(null,120,'水费',0);
insert into remain values(null,120,'电费',0);
insert into remain values(null,120,'天然气费',0);
insert into remain values(null,120,'物业费',0);
insert into remain values(null,121,'水费',0);
insert into remain values(null,121,'电费',0);
insert into remain values(null,121,'天然气费',0);
insert into remain values(null,121,'物业费',0);
insert into remain values(null,122,'水费',0);
insert into remain values(null,122,'电费',0);
insert into remain values(null,122,'天然气费',0);
insert into remain values(null,122,'物业费',0);
insert into remain values(null,123,'水费',0);
insert into remain values(null,123,'电费',0);
insert into remain values(null,123,'天然气费',0);
insert into remain values(null,123,'物业费',0);
insert into remain values(null,124,'水费',0);
insert into remain values(null,124,'电费',0);
insert into remain values(null,124,'天然气费',0);
insert into remain values(null,124,'物业费',0);
insert into remain values(null,125,'水费',0);
insert into remain values(null,125,'电费',0);
insert into remain values(null,125,'天然气费',0);
insert into remain values(null,125,'物业费',0);
insert into remain values(null,126,'水费',0);
insert into remain values(null,126,'电费',0);
insert into remain values(null,126,'天然气费',0);
insert into remain values(null,126,'物业费',0);
insert into remain values(null,127,'水费',0);
insert into remain values(null,127,'电费',0);
insert into remain values(null,127,'天然气费',0);
insert into remain values(null,127,'物业费',0);
insert into remain values(null,128,'水费',0);
insert into remain values(null,128,'电费',0);
insert into remain values(null,128,'天然气费',0);
insert into remain values(null,128,'物业费',0);
insert into remain values(null,129,'水费',0);
insert into remain values(null,129,'电费',0);
insert into remain values(null,129,'天然气费',0);
insert into remain values(null,129,'物业费',0);
insert into remain values(null,130,'水费',0);
insert into remain values(null,130,'电费',0);
insert into remain values(null,130,'天然气费',0);
insert into remain values(null,130,'物业费',0);
insert into remain values(null,131,'水费',0);
insert into remain values(null,131,'电费',0);
insert into remain values(null,131,'天然气费',0);
insert into remain values(null,131,'物业费',0);
insert into remain values(null,132,'水费',0);
insert into remain values(null,132,'电费',0);
insert into remain values(null,132,'天然气费',0);
insert into remain values(null,132,'物业费',0);
insert into remain values(null,133,'水费',0);
insert into remain values(null,133,'电费',0);
insert into remain values(null,133,'天然气费',0);
insert into remain values(null,133,'物业费',0);
insert into remain values(null,134,'水费',0);
insert into remain values(null,134,'电费',0);
insert into remain values(null,134,'天然气费',0);
insert into remain values(null,134,'物业费',0);
insert into remain values(null,135,'水费',0);
insert into remain values(null,135,'电费',0);
insert into remain values(null,135,'天然气费',0);
insert into remain values(null,135,'物业费',0);
insert into remain values(null,136,'水费',0);
insert into remain values(null,136,'电费',0);
insert into remain values(null,136,'天然气费',0);
insert into remain values(null,136,'物业费',0);
insert into remain values(null,137,'水费',0);
insert into remain values(null,137,'电费',0);
insert into remain values(null,137,'天然气费',0);
insert into remain values(null,137,'物业费',0);
insert into remain values(null,138,'水费',0);
insert into remain values(null,138,'电费',0);
insert into remain values(null,138,'天然气费',0);
insert into remain values(null,138,'物业费',0);
insert into remain values(null,139,'水费',0);
insert into remain values(null,139,'电费',0);
insert into remain values(null,139,'天然气费',0);
insert into remain values(null,139,'物业费',0);
insert into remain values(null,140,'水费',0);
insert into remain values(null,140,'电费',0);
insert into remain values(null,140,'天然气费',0);
insert into remain values(null,140,'物业费',0);
insert into remain values(null,141,'水费',0);
insert into remain values(null,141,'电费',0);
insert into remain values(null,141,'天然气费',0);
insert into remain values(null,141,'物业费',0);
insert into remain values(null,142,'水费',0);
insert into remain values(null,142,'电费',0);
insert into remain values(null,142,'天然气费',0);
insert into remain values(null,142,'物业费',0);
insert into remain values(null,143,'水费',0);
insert into remain values(null,143,'电费',0);
insert into remain values(null,143,'天然气费',0);
insert into remain values(null,143,'物业费',0);
insert into remain values(null,144,'水费',0);
insert into remain values(null,144,'电费',0);
insert into remain values(null,144,'天然气费',0);
insert into remain values(null,144,'物业费',0);
insert into remain values(null,145,'水费',0);
insert into remain values(null,145,'电费',0);
insert into remain values(null,145,'天然气费',0);
insert into remain values(null,145,'物业费',0);
insert into remain values(null,146,'水费',0);
insert into remain values(null,146,'电费',0);
insert into remain values(null,146,'天然气费',0);
insert into remain values(null,146,'物业费',0);
insert into remain values(null,147,'水费',0);
insert into remain values(null,147,'电费',0);
insert into remain values(null,147,'天然气费',0);
insert into remain values(null,147,'物业费',0);
insert into remain values(null,148,'水费',0);
insert into remain values(null,148,'电费',0);
insert into remain values(null,148,'天然气费',0);
insert into remain values(null,148,'物业费',0);
insert into remain values(null,149,'水费',0);
insert into remain values(null,149,'电费',0);
insert into remain values(null,149,'天然气费',0);
insert into remain values(null,149,'物业费',0);
insert into remain values(null,150,'水费',0);
insert into remain values(null,150,'电费',0);
insert into remain values(null,150,'天然气费',0);
insert into remain values(null,150,'物业费',0);
insert into remain values(null,151,'水费',0);
insert into remain values(null,151,'电费',0);
insert into remain values(null,151,'天然气费',0);
insert into remain values(null,151,'物业费',0);
insert into remain values(null,152,'水费',0);
insert into remain values(null,152,'电费',0);
insert into remain values(null,152,'天然气费',0);
insert into remain values(null,152,'物业费',0);
insert into remain values(null,153,'水费',0);
insert into remain values(null,153,'电费',0);
insert into remain values(null,153,'天然气费',0);
insert into remain values(null,153,'物业费',0);
insert into remain values(null,154,'水费',0);
insert into remain values(null,154,'电费',0);
insert into remain values(null,154,'天然气费',0);
insert into remain values(null,154,'物业费',0);
insert into remain values(null,155,'水费',0);
insert into remain values(null,155,'电费',0);
insert into remain values(null,155,'天然气费',0);
insert into remain values(null,155,'物业费',0);
insert into remain values(null,156,'水费',0);
insert into remain values(null,156,'电费',0);
insert into remain values(null,156,'天然气费',0);
insert into remain values(null,156,'物业费',0);
insert into remain values(null,157,'水费',0);
insert into remain values(null,157,'电费',0);
insert into remain values(null,157,'天然气费',0);
insert into remain values(null,157,'物业费',0);
insert into remain values(null,158,'水费',0);
insert into remain values(null,158,'电费',0);
insert into remain values(null,158,'天然气费',0);
insert into remain values(null,158,'物业费',0);
insert into remain values(null,159,'水费',0);
insert into remain values(null,159,'电费',0);
insert into remain values(null,159,'天然气费',0);
insert into remain values(null,159,'物业费',0);
insert into remain values(null,160,'水费',0);
insert into remain values(null,160,'电费',0);
insert into remain values(null,160,'天然气费',0);
insert into remain values(null,160,'物业费',0);
insert into remain values(null,161,'水费',0);
insert into remain values(null,161,'电费',0);
insert into remain values(null,161,'天然气费',0);
insert into remain values(null,161,'物业费',0);
insert into remain values(null,162,'水费',0);
insert into remain values(null,162,'电费',0);
insert into remain values(null,162,'天然气费',0);
insert into remain values(null,162,'物业费',0);
insert into remain values(null,163,'水费',0);
insert into remain values(null,163,'电费',0);
insert into remain values(null,163,'天然气费',0);
insert into remain values(null,163,'物业费',0);
insert into remain values(null,164,'水费',0);
insert into remain values(null,164,'电费',0);
insert into remain values(null,164,'天然气费',0);
insert into remain values(null,164,'物业费',0);
insert into remain values(null,165,'水费',0);
insert into remain values(null,165,'电费',0);
insert into remain values(null,165,'天然气费',0);
insert into remain values(null,165,'物业费',0);
insert into remain values(null,166,'水费',0);
insert into remain values(null,166,'电费',0);
insert into remain values(null,166,'天然气费',0);
insert into remain values(null,166,'物业费',0);
insert into remain values(null,167,'水费',0);
insert into remain values(null,167,'电费',0);
insert into remain values(null,167,'天然气费',0);
insert into remain values(null,167,'物业费',0);
insert into remain values(null,168,'水费',0);
insert into remain values(null,168,'电费',0);
insert into remain values(null,168,'天然气费',0);
insert into remain values(null,168,'物业费',0);
insert into remain values(null,169,'水费',0);
insert into remain values(null,169,'电费',0);
insert into remain values(null,169,'天然气费',0);
insert into remain values(null,169,'物业费',0);
insert into remain values(null,170,'水费',0);
insert into remain values(null,170,'电费',0);
insert into remain values(null,170,'天然气费',0);
insert into remain values(null,170,'物业费',0);
insert into remain values(null,171,'水费',0);
insert into remain values(null,171,'电费',0);
insert into remain values(null,171,'天然气费',0);
insert into remain values(null,171,'物业费',0);
insert into remain values(null,172,'水费',0);
insert into remain values(null,172,'电费',0);
insert into remain values(null,172,'天然气费',0);
insert into remain values(null,172,'物业费',0);
insert into remain values(null,173,'水费',0);
insert into remain values(null,173,'电费',0);
insert into remain values(null,173,'天然气费',0);
insert into remain values(null,173,'物业费',0);
insert into remain values(null,174,'水费',0);
insert into remain values(null,174,'电费',0);
insert into remain values(null,174,'天然气费',0);
insert into remain values(null,174,'物业费',0);
insert into remain values(null,175,'水费',0);
insert into remain values(null,175,'电费',0);
insert into remain values(null,175,'天然气费',0);
insert into remain values(null,175,'物业费',0);
insert into remain values(null,176,'水费',0);
insert into remain values(null,176,'电费',0);
insert into remain values(null,176,'天然气费',0);
insert into remain values(null,176,'物业费',0);
insert into remain values(null,177,'水费',0);
insert into remain values(null,177,'电费',0);
insert into remain values(null,177,'天然气费',0);
insert into remain values(null,177,'物业费',0);
insert into remain values(null,178,'水费',0);
insert into remain values(null,178,'电费',0);
insert into remain values(null,178,'天然气费',0);
insert into remain values(null,178,'物业费',0);
insert into remain values(null,179,'水费',0);
insert into remain values(null,179,'电费',0);
insert into remain values(null,179,'天然气费',0);
insert into remain values(null,179,'物业费',0);
insert into remain values(null,180,'水费',0);
insert into remain values(null,180,'电费',0);
insert into remain values(null,180,'天然气费',0);
insert into remain values(null,180,'物业费',0);
insert into remain values(null,181,'水费',0);
insert into remain values(null,181,'电费',0);
insert into remain values(null,181,'天然气费',0);
insert into remain values(null,181,'物业费',0);
insert into remain values(null,182,'水费',0);
insert into remain values(null,182,'电费',0);
insert into remain values(null,182,'天然气费',0);
insert into remain values(null,182,'物业费',0);
insert into remain values(null,183,'水费',0);
insert into remain values(null,183,'电费',0);
insert into remain values(null,183,'天然气费',0);
insert into remain values(null,183,'物业费',0);
insert into remain values(null,184,'水费',0);
insert into remain values(null,184,'电费',0);
insert into remain values(null,184,'天然气费',0);
insert into remain values(null,184,'物业费',0);
insert into remain values(null,185,'水费',0);
insert into remain values(null,185,'电费',0);
insert into remain values(null,185,'天然气费',0);
insert into remain values(null,185,'物业费',0);
insert into remain values(null,186,'水费',0);
insert into remain values(null,186,'电费',0);
insert into remain values(null,186,'天然气费',0);
insert into remain values(null,186,'物业费',0);
insert into remain values(null,187,'水费',0);
insert into remain values(null,187,'电费',0);
insert into remain values(null,187,'天然气费',0);
insert into remain values(null,187,'物业费',0);
insert into remain values(null,188,'水费',0);
insert into remain values(null,188,'电费',0);
insert into remain values(null,188,'天然气费',0);
insert into remain values(null,188,'物业费',0);
insert into remain values(null,189,'水费',0);
insert into remain values(null,189,'电费',0);
insert into remain values(null,189,'天然气费',0);
insert into remain values(null,189,'物业费',0);
insert into remain values(null,190,'水费',0);
insert into remain values(null,190,'电费',0);
insert into remain values(null,190,'天然气费',0);
insert into remain values(null,190,'物业费',0);
insert into remain values(null,191,'水费',0);
insert into remain values(null,191,'电费',0);
insert into remain values(null,191,'天然气费',0);
insert into remain values(null,191,'物业费',0);
insert into remain values(null,192,'水费',0);
insert into remain values(null,192,'电费',0);
insert into remain values(null,192,'天然气费',0);
insert into remain values(null,192,'物业费',0);
insert into remain values(null,193,'水费',0);
insert into remain values(null,193,'电费',0);
insert into remain values(null,193,'天然气费',0);
insert into remain values(null,193,'物业费',0);
insert into remain values(null,194,'水费',0);
insert into remain values(null,194,'电费',0);
insert into remain values(null,194,'天然气费',0);
insert into remain values(null,194,'物业费',0);
insert into remain values(null,195,'水费',0);
insert into remain values(null,195,'电费',0);
insert into remain values(null,195,'天然气费',0);
insert into remain values(null,195,'物业费',0);
insert into remain values(null,196,'水费',0);
insert into remain values(null,196,'电费',0);
insert into remain values(null,196,'天然气费',0);
insert into remain values(null,196,'物业费',0);
insert into remain values(null,197,'水费',0);
insert into remain values(null,197,'电费',0);
insert into remain values(null,197,'天然气费',0);
insert into remain values(null,197,'物业费',0);
insert into remain values(null,198,'水费',0);
insert into remain values(null,198,'电费',0);
insert into remain values(null,198,'天然气费',0);
insert into remain values(null,198,'物业费',0);
insert into remain values(null,199,'水费',0);
insert into remain values(null,199,'电费',0);
insert into remain values(null,199,'天然气费',0);
insert into remain values(null,199,'物业费',0);
insert into remain values(null,200,'水费',0);
insert into remain values(null,200,'电费',0);
insert into remain values(null,200,'天然气费',0);
insert into remain values(null,200,'物业费',0);
insert into remain values(null,201,'水费',0);
insert into remain values(null,201,'电费',0);
insert into remain values(null,201,'天然气费',0);
insert into remain values(null,201,'物业费',0);
insert into remain values(null,202,'水费',0);
insert into remain values(null,202,'电费',0);
insert into remain values(null,202,'天然气费',0);
insert into remain values(null,202,'物业费',0);
insert into remain values(null,203,'水费',0);
insert into remain values(null,203,'电费',0);
insert into remain values(null,203,'天然气费',0);
insert into remain values(null,203,'物业费',0);
insert into remain values(null,204,'水费',0);
insert into remain values(null,204,'电费',0);
insert into remain values(null,204,'天然气费',0);
insert into remain values(null,204,'物业费',0);
insert into remain values(null,205,'水费',0);
insert into remain values(null,205,'电费',0);
insert into remain values(null,205,'天然气费',0);
insert into remain values(null,205,'物业费',0);
insert into remain values(null,206,'水费',0);
insert into remain values(null,206,'电费',0);
insert into remain values(null,206,'天然气费',0);
insert into remain values(null,206,'物业费',0);
insert into remain values(null,207,'水费',0);
insert into remain values(null,207,'电费',0);
insert into remain values(null,207,'天然气费',0);
insert into remain values(null,207,'物业费',0);
insert into remain values(null,208,'水费',0);
insert into remain values(null,208,'电费',0);
insert into remain values(null,208,'天然气费',0);
insert into remain values(null,208,'物业费',0);
insert into remain values(null,209,'水费',0);
insert into remain values(null,209,'电费',0);
insert into remain values(null,209,'天然气费',0);
insert into remain values(null,209,'物业费',0);
insert into remain values(null,210,'水费',0);
insert into remain values(null,210,'电费',0);
insert into remain values(null,210,'天然气费',0);
insert into remain values(null,210,'物业费',0);
insert into remain values(null,211,'水费',0);
insert into remain values(null,211,'电费',0);
insert into remain values(null,211,'天然气费',0);
insert into remain values(null,211,'物业费',0);
insert into remain values(null,212,'水费',0);
insert into remain values(null,212,'电费',0);
insert into remain values(null,212,'天然气费',0);
insert into remain values(null,212,'物业费',0);
insert into remain values(null,213,'水费',0);
insert into remain values(null,213,'电费',0);
insert into remain values(null,213,'天然气费',0);
insert into remain values(null,213,'物业费',0);
insert into remain values(null,214,'水费',0);
insert into remain values(null,214,'电费',0);
insert into remain values(null,214,'天然气费',0);
insert into remain values(null,214,'物业费',0);
insert into remain values(null,215,'水费',0);
insert into remain values(null,215,'电费',0);
insert into remain values(null,215,'天然气费',0);
insert into remain values(null,215,'物业费',0);
insert into remain values(null,216,'水费',0);
insert into remain values(null,216,'电费',0);
insert into remain values(null,216,'天然气费',0);
insert into remain values(null,216,'物业费',0);
insert into remain values(null,217,'水费',0);
insert into remain values(null,217,'电费',0);
insert into remain values(null,217,'天然气费',0);
insert into remain values(null,217,'物业费',0);
insert into remain values(null,218,'水费',0);
insert into remain values(null,218,'电费',0);
insert into remain values(null,218,'天然气费',0);
insert into remain values(null,218,'物业费',0);
insert into remain values(null,219,'水费',0);
insert into remain values(null,219,'电费',0);
insert into remain values(null,219,'天然气费',0);
insert into remain values(null,219,'物业费',0);
insert into remain values(null,220,'水费',0);
insert into remain values(null,220,'电费',0);
insert into remain values(null,220,'天然气费',0);
insert into remain values(null,220,'物业费',0);
insert into remain values(null,221,'水费',0);
insert into remain values(null,221,'电费',0);
insert into remain values(null,221,'天然气费',0);
insert into remain values(null,221,'物业费',0);
insert into remain values(null,222,'水费',0);
insert into remain values(null,222,'电费',0);
insert into remain values(null,222,'天然气费',0);
insert into remain values(null,222,'物业费',0);
insert into remain values(null,223,'水费',0);
insert into remain values(null,223,'电费',0);
insert into remain values(null,223,'天然气费',0);
insert into remain values(null,223,'物业费',0);
insert into remain values(null,224,'水费',0);
insert into remain values(null,224,'电费',0);
insert into remain values(null,224,'天然气费',0);
insert into remain values(null,224,'物业费',0);
insert into remain values(null,225,'水费',0);
insert into remain values(null,225,'电费',0);
insert into remain values(null,225,'天然气费',0);
insert into remain values(null,225,'物业费',0);
insert into remain values(null,226,'水费',0);
insert into remain values(null,226,'电费',0);
insert into remain values(null,226,'天然气费',0);
insert into remain values(null,226,'物业费',0);
insert into remain values(null,227,'水费',0);
insert into remain values(null,227,'电费',0);
insert into remain values(null,227,'天然气费',0);
insert into remain values(null,227,'物业费',0);
insert into remain values(null,228,'水费',0);
insert into remain values(null,228,'电费',0);
insert into remain values(null,228,'天然气费',0);
insert into remain values(null,228,'物业费',0);
insert into remain values(null,229,'水费',0);
insert into remain values(null,229,'电费',0);
insert into remain values(null,229,'天然气费',0);
insert into remain values(null,229,'物业费',0);
insert into remain values(null,230,'水费',0);
insert into remain values(null,230,'电费',0);
insert into remain values(null,230,'天然气费',0);
insert into remain values(null,230,'物业费',0);
insert into remain values(null,231,'水费',0);
insert into remain values(null,231,'电费',0);
insert into remain values(null,231,'天然气费',0);
insert into remain values(null,231,'物业费',0);
insert into remain values(null,232,'水费',0);
insert into remain values(null,232,'电费',0);
insert into remain values(null,232,'天然气费',0);
insert into remain values(null,232,'物业费',0);
insert into remain values(null,233,'水费',0);
insert into remain values(null,233,'电费',0);
insert into remain values(null,233,'天然气费',0);
insert into remain values(null,233,'物业费',0);
insert into remain values(null,234,'水费',0);
insert into remain values(null,234,'电费',0);
insert into remain values(null,234,'天然气费',0);
insert into remain values(null,234,'物业费',0);
insert into remain values(null,235,'水费',0);
insert into remain values(null,235,'电费',0);
insert into remain values(null,235,'天然气费',0);
insert into remain values(null,235,'物业费',0);
insert into remain values(null,236,'水费',0);
insert into remain values(null,236,'电费',0);
insert into remain values(null,236,'天然气费',0);
insert into remain values(null,236,'物业费',0);
insert into remain values(null,237,'水费',0);
insert into remain values(null,237,'电费',0);
insert into remain values(null,237,'天然气费',0);
insert into remain values(null,237,'物业费',0);
insert into remain values(null,238,'水费',0);
insert into remain values(null,238,'电费',0);
insert into remain values(null,238,'天然气费',0);
insert into remain values(null,238,'物业费',0);
insert into remain values(null,239,'水费',0);
insert into remain values(null,239,'电费',0);
insert into remain values(null,239,'天然气费',0);
insert into remain values(null,239,'物业费',0);
insert into remain values(null,240,'水费',0);
insert into remain values(null,240,'电费',0);
insert into remain values(null,240,'天然气费',0);
insert into remain values(null,240,'物业费',0);
insert into remain values(null,241,'水费',0);
insert into remain values(null,241,'电费',0);
insert into remain values(null,241,'天然气费',0);
insert into remain values(null,241,'物业费',0);
insert into remain values(null,242,'水费',0);
insert into remain values(null,242,'电费',0);
insert into remain values(null,242,'天然气费',0);
insert into remain values(null,242,'物业费',0);
insert into remain values(null,243,'水费',0);
insert into remain values(null,243,'电费',0);
insert into remain values(null,243,'天然气费',0);
insert into remain values(null,243,'物业费',0);
insert into remain values(null,244,'水费',0);
insert into remain values(null,244,'电费',0);
insert into remain values(null,244,'天然气费',0);
insert into remain values(null,244,'物业费',0);
insert into remain values(null,245,'水费',0);
insert into remain values(null,245,'电费',0);
insert into remain values(null,245,'天然气费',0);
insert into remain values(null,245,'物业费',0);
insert into remain values(null,246,'水费',0);
insert into remain values(null,246,'电费',0);
insert into remain values(null,246,'天然气费',0);
insert into remain values(null,246,'物业费',0);
insert into remain values(null,247,'水费',0);
insert into remain values(null,247,'电费',0);
insert into remain values(null,247,'天然气费',0);
insert into remain values(null,247,'物业费',0);
insert into remain values(null,248,'水费',0);
insert into remain values(null,248,'电费',0);
insert into remain values(null,248,'天然气费',0);
insert into remain values(null,248,'物业费',0);
insert into remain values(null,249,'水费',0);
insert into remain values(null,249,'电费',0);
insert into remain values(null,249,'天然气费',0);
insert into remain values(null,249,'物业费',0);
insert into remain values(null,250,'水费',0);
insert into remain values(null,250,'电费',0);
insert into remain values(null,250,'天然气费',0);
insert into remain values(null,250,'物业费',0);
insert into remain values(null,251,'水费',0);
insert into remain values(null,251,'电费',0);
insert into remain values(null,251,'天然气费',0);
insert into remain values(null,251,'物业费',0);
insert into remain values(null,252,'水费',0);
insert into remain values(null,252,'电费',0);
insert into remain values(null,252,'天然气费',0);
insert into remain values(null,252,'物业费',0);
insert into remain values(null,253,'水费',0);
insert into remain values(null,253,'电费',0);
insert into remain values(null,253,'天然气费',0);
insert into remain values(null,253,'物业费',0);
insert into remain values(null,254,'水费',0);
insert into remain values(null,254,'电费',0);
insert into remain values(null,254,'天然气费',0);
insert into remain values(null,254,'物业费',0);
insert into remain values(null,255,'水费',0);
insert into remain values(null,255,'电费',0);
insert into remain values(null,255,'天然气费',0);
insert into remain values(null,255,'物业费',0);
insert into remain values(null,256,'水费',0);
insert into remain values(null,256,'电费',0);
insert into remain values(null,256,'天然气费',0);
insert into remain values(null,256,'物业费',0);
insert into remain values(null,257,'水费',0);
insert into remain values(null,257,'电费',0);
insert into remain values(null,257,'天然气费',0);
insert into remain values(null,257,'物业费',0);
insert into remain values(null,258,'水费',0);
insert into remain values(null,258,'电费',0);
insert into remain values(null,258,'天然气费',0);
insert into remain values(null,258,'物业费',0);
insert into remain values(null,259,'水费',0);
insert into remain values(null,259,'电费',0);
insert into remain values(null,259,'天然气费',0);
insert into remain values(null,259,'物业费',0);
insert into remain values(null,260,'水费',0);
insert into remain values(null,260,'电费',0);
insert into remain values(null,260,'天然气费',0);
insert into remain values(null,260,'物业费',0);
insert into remain values(null,261,'水费',0);
insert into remain values(null,261,'电费',0);
insert into remain values(null,261,'天然气费',0);
insert into remain values(null,261,'物业费',0);
insert into remain values(null,262,'水费',0);
insert into remain values(null,262,'电费',0);
insert into remain values(null,262,'天然气费',0);
insert into remain values(null,262,'物业费',0);
insert into remain values(null,263,'水费',0);
insert into remain values(null,263,'电费',0);
insert into remain values(null,263,'天然气费',0);
insert into remain values(null,263,'物业费',0);
insert into remain values(null,264,'水费',0);
insert into remain values(null,264,'电费',0);
insert into remain values(null,264,'天然气费',0);
insert into remain values(null,264,'物业费',0);
insert into remain values(null,265,'水费',0);
insert into remain values(null,265,'电费',0);
insert into remain values(null,265,'天然气费',0);
insert into remain values(null,265,'物业费',0);
insert into remain values(null,266,'水费',0);
insert into remain values(null,266,'电费',0);
insert into remain values(null,266,'天然气费',0);
insert into remain values(null,266,'物业费',0);
insert into remain values(null,267,'水费',0);
insert into remain values(null,267,'电费',0);
insert into remain values(null,267,'天然气费',0);
insert into remain values(null,267,'物业费',0);
insert into remain values(null,268,'水费',0);
insert into remain values(null,268,'电费',0);
insert into remain values(null,268,'天然气费',0);
insert into remain values(null,268,'物业费',0);
insert into remain values(null,269,'水费',0);
insert into remain values(null,269,'电费',0);
insert into remain values(null,269,'天然气费',0);
insert into remain values(null,269,'物业费',0);
insert into remain values(null,270,'水费',0);
insert into remain values(null,270,'电费',0);
insert into remain values(null,270,'天然气费',0);
insert into remain values(null,270,'物业费',0);
insert into remain values(null,271,'水费',0);
insert into remain values(null,271,'电费',0);
insert into remain values(null,271,'天然气费',0);
insert into remain values(null,271,'物业费',0);
insert into remain values(null,272,'水费',0);
insert into remain values(null,272,'电费',0);
insert into remain values(null,272,'天然气费',0);
insert into remain values(null,272,'物业费',0);
insert into remain values(null,273,'水费',0);
insert into remain values(null,273,'电费',0);
insert into remain values(null,273,'天然气费',0);
insert into remain values(null,273,'物业费',0);
insert into remain values(null,274,'水费',0);
insert into remain values(null,274,'电费',0);
insert into remain values(null,274,'天然气费',0);
insert into remain values(null,274,'物业费',0);
insert into remain values(null,275,'水费',0);
insert into remain values(null,275,'电费',0);
insert into remain values(null,275,'天然气费',0);
insert into remain values(null,275,'物业费',0);
insert into remain values(null,276,'水费',0);
insert into remain values(null,276,'电费',0);
insert into remain values(null,276,'天然气费',0);
insert into remain values(null,276,'物业费',0);
insert into remain values(null,277,'水费',0);
insert into remain values(null,277,'电费',0);
insert into remain values(null,277,'天然气费',0);
insert into remain values(null,277,'物业费',0);
insert into remain values(null,278,'水费',0);
insert into remain values(null,278,'电费',0);
insert into remain values(null,278,'天然气费',0);
insert into remain values(null,278,'物业费',0);
insert into remain values(null,279,'水费',0);
insert into remain values(null,279,'电费',0);
insert into remain values(null,279,'天然气费',0);
insert into remain values(null,279,'物业费',0);
insert into remain values(null,280,'水费',0);
insert into remain values(null,280,'电费',0);
insert into remain values(null,280,'天然气费',0);
insert into remain values(null,280,'物业费',0);
insert into remain values(null,281,'水费',0);
insert into remain values(null,281,'电费',0);
insert into remain values(null,281,'天然气费',0);
insert into remain values(null,281,'物业费',0);
insert into remain values(null,282,'水费',0);
insert into remain values(null,282,'电费',0);
insert into remain values(null,282,'天然气费',0);
insert into remain values(null,282,'物业费',0);
insert into remain values(null,283,'水费',0);
insert into remain values(null,283,'电费',0);
insert into remain values(null,283,'天然气费',0);
insert into remain values(null,283,'物业费',0);
insert into remain values(null,284,'水费',0);
insert into remain values(null,284,'电费',0);
insert into remain values(null,284,'天然气费',0);
insert into remain values(null,284,'物业费',0);
insert into remain values(null,285,'水费',0);
insert into remain values(null,285,'电费',0);
insert into remain values(null,285,'天然气费',0);
insert into remain values(null,285,'物业费',0);
insert into remain values(null,286,'水费',0);
insert into remain values(null,286,'电费',0);
insert into remain values(null,286,'天然气费',0);
insert into remain values(null,286,'物业费',0);
insert into remain values(null,287,'水费',0);
insert into remain values(null,287,'电费',0);
insert into remain values(null,287,'天然气费',0);
insert into remain values(null,287,'物业费',0);
insert into remain values(null,288,'水费',0);
insert into remain values(null,288,'电费',0);
insert into remain values(null,288,'天然气费',0);
insert into remain values(null,288,'物业费',0);
insert into remain values(null,289,'水费',0);
insert into remain values(null,289,'电费',0);
insert into remain values(null,289,'天然气费',0);
insert into remain values(null,289,'物业费',0);
insert into remain values(null,290,'水费',0);
insert into remain values(null,290,'电费',0);
insert into remain values(null,290,'天然气费',0);
insert into remain values(null,290,'物业费',0);
insert into remain values(null,291,'水费',0);
insert into remain values(null,291,'电费',0);
insert into remain values(null,291,'天然气费',0);
insert into remain values(null,291,'物业费',0);
insert into remain values(null,292,'水费',0);
insert into remain values(null,292,'电费',0);
insert into remain values(null,292,'天然气费',0);
insert into remain values(null,292,'物业费',0);
insert into remain values(null,293,'水费',0);
insert into remain values(null,293,'电费',0);
insert into remain values(null,293,'天然气费',0);
insert into remain values(null,293,'物业费',0);
insert into remain values(null,294,'水费',0);
insert into remain values(null,294,'电费',0);
insert into remain values(null,294,'天然气费',0);
insert into remain values(null,294,'物业费',0);
insert into remain values(null,295,'水费',0);
insert into remain values(null,295,'电费',0);
insert into remain values(null,295,'天然气费',0);
insert into remain values(null,295,'物业费',0);
insert into remain values(null,296,'水费',0);
insert into remain values(null,296,'电费',0);
insert into remain values(null,296,'天然气费',0);
insert into remain values(null,296,'物业费',0);
insert into remain values(null,297,'水费',0);
insert into remain values(null,297,'电费',0);
insert into remain values(null,297,'天然气费',0);
insert into remain values(null,297,'物业费',0);
insert into remain values(null,298,'水费',0);
insert into remain values(null,298,'电费',0);
insert into remain values(null,298,'天然气费',0);
insert into remain values(null,298,'物业费',0);
insert into remain values(null,299,'水费',0);
insert into remain values(null,299,'电费',0);
insert into remain values(null,299,'天然气费',0);
insert into remain values(null,299,'物业费',0);
insert into remain values(null,300,'水费',0);
insert into remain values(null,300,'电费',0);
insert into remain values(null,300,'天然气费',0);
insert into remain values(null,300,'物业费',0);
insert into remain values(null,301,'水费',0);
insert into remain values(null,301,'电费',0);
insert into remain values(null,301,'天然气费',0);
insert into remain values(null,301,'物业费',0);
insert into remain values(null,302,'水费',0);
insert into remain values(null,302,'电费',0);
insert into remain values(null,302,'天然气费',0);
insert into remain values(null,302,'物业费',0);
insert into remain values(null,303,'水费',0);
insert into remain values(null,303,'电费',0);
insert into remain values(null,303,'天然气费',0);
insert into remain values(null,303,'物业费',0);
insert into remain values(null,304,'水费',0);
insert into remain values(null,304,'电费',0);
insert into remain values(null,304,'天然气费',0);
insert into remain values(null,304,'物业费',0);
insert into remain values(null,305,'水费',0);
insert into remain values(null,305,'电费',0);
insert into remain values(null,305,'天然气费',0);
insert into remain values(null,305,'物业费',0);
insert into remain values(null,306,'水费',0);
insert into remain values(null,306,'电费',0);
insert into remain values(null,306,'天然气费',0);
insert into remain values(null,306,'物业费',0);
insert into remain values(null,307,'水费',0);
insert into remain values(null,307,'电费',0);
insert into remain values(null,307,'天然气费',0);
insert into remain values(null,307,'物业费',0);
insert into remain values(null,308,'水费',0);
insert into remain values(null,308,'电费',0);
insert into remain values(null,308,'天然气费',0);
insert into remain values(null,308,'物业费',0);
insert into remain values(null,309,'水费',0);
insert into remain values(null,309,'电费',0);
insert into remain values(null,309,'天然气费',0);
insert into remain values(null,309,'物业费',0);
insert into remain values(null,310,'水费',0);
insert into remain values(null,310,'电费',0);
insert into remain values(null,310,'天然气费',0);
insert into remain values(null,310,'物业费',0);
insert into remain values(null,311,'水费',0);
insert into remain values(null,311,'电费',0);
insert into remain values(null,311,'天然气费',0);
insert into remain values(null,311,'物业费',0);
insert into remain values(null,312,'水费',0);
insert into remain values(null,312,'电费',0);
insert into remain values(null,312,'天然气费',0);
insert into remain values(null,312,'物业费',0);
insert into remain values(null,313,'水费',0);
insert into remain values(null,313,'电费',0);
insert into remain values(null,313,'天然气费',0);
insert into remain values(null,313,'物业费',0);
insert into remain values(null,314,'水费',0);
insert into remain values(null,314,'电费',0);
insert into remain values(null,314,'天然气费',0);
insert into remain values(null,314,'物业费',0);
insert into remain values(null,315,'水费',0);
insert into remain values(null,315,'电费',0);
insert into remain values(null,315,'天然气费',0);
insert into remain values(null,315,'物业费',0);

insert into product_category values(null,'时令蔬菜'); -- 1
insert into product_category values(null,'新鲜水果'); -- 2
insert into product_category values(null,'酒水乳品'); -- 3
insert into product_category values(null,'日用百货'); -- 4
insert into product_category values(null,'优惠活动'); -- 5
insert into product_category values(null,'其它类型'); -- 6

insert into product_unit values(null,'个');
insert into product_unit values(null,'本');
insert into product_unit values(null,'箱');
insert into product_unit values(null,'斤');
insert into product_unit values(null,'提');
insert into product_unit values(null,'打');
insert into product_unit values(null,'桶');
insert into product_unit values(null,'袋');
insert into product_unit values(null,'瓶');
insert into product_unit values(null,'盒');
insert into product_unit values(null,'只');
insert into product_unit values(null,'条');
insert into product_unit values(null,'罐');
insert into product_unit values(null,'张');
insert into product_unit values(null,'块');
insert into product_unit values(null,'把');
insert into product_unit values(null,'台');
insert into product_unit values(null,'部');
insert into product_unit values(null,'支');

insert into express_item_category values(null,'文件票证'); -- 1
insert into express_item_category values(null,'数码产品'); -- 2
insert into express_item_category values(null,'日用品'); -- 3
insert into express_item_category values(null,'生鲜水果'); -- 4
insert into express_item_category values(null,'休闲食品'); -- 5
insert into express_item_category values(null,'服装鞋帽'); -- 6
insert into express_item_category values(null,'工艺品'); -- 7
insert into express_item_category values(null,'珠宝首饰'); -- 8
insert into express_item_category values(null,'其它'); -- 9




insert into product values('20206300001','康师傅冰红茶',3.50,'瓶',3);
insert into product values('20206300002','红富士苹果',6.80,'斤',2);
insert into product values('20206300003','火龙果',5.0,'个',2);
insert into product values('20206300004','番茄',3.60,'斤',1);
insert into product values('20206300005','蒜苔',2.80,'斤',1);
insert into product values('20206300006','相印抽纸三连包',13.90,'提',4);
insert into product values('20206300007','Angel健身房游泳次卡',23.80,'张',5);
insert into product values('20206300008','木本造型男士烫发超值套餐',98.0,'张',5);
insert into product values('20206300009','云南白药牙膏',9.80,'支',4);

#update product set unit_price='9.70' where product_id='20206300009';
#select * from product where product_id='20206300009';

insert into shopping_order values(null,'Gsg',default,now());
-- select * from shopping_order_detail;
insert into shopping_order_detail values(null,1,'20206300001','康师傅冰红茶',3.5,2,'瓶',7);
insert into shopping_order_detail values(null,1,'20206300002','红富士苹果',6.8,1,'斤',6.8);
insert into shopping_order_detail values(null,1,'20206300006','相印抽纸三连包',13.9,2,'提',27.8);



insert into shopping_order values(null,'Czy',default,now());
insert into shopping_order_detail values(null,2,'20206300009','Angel健身房游泳次卡',23.8,3,'张',71.4);
insert into shopping_order_detail values(null,2,'20206300007','云南白药牙膏',9.8,10,'支',98);


insert into courier values(null,'张三风','13517115276');
insert into courier values(null,'李四虎','17316575412');
insert into courier values(null,'王老五','13439880877');
insert into courier values(null,'赵小云','13764333943');
insert into courier values(null,'关云常','18018412549');

insert into express_address values(null,'Apare',0,'梁言','15589259345','山西','太原','小店','030006','平阳路103号');
insert into express_address values(null,'Apare',0,'郭绍捷','13903729722','山西','太原','小店','030006','建设南路34号');
insert into express_address values(null,'Apare',1,'许智超','18235151277','湖北','武汉','洪山','430070','华中师范大学东区学生宿舍16栋');
insert into express_address values(null,'Apare',1,'许智超','18235151277','湖北','武汉','洪山','430070','华中师范大学南湖校区学生宿舍4栋');


insert into logistic values(null,default,now(),'Apare','许智超',3,'18235151277','湖北','武汉','洪山','430070','华中师范大学南湖校区学生宿舍4栋','梁言','15589259345','山西','太原','小店','030006','平阳路103号',31.80,1,default,now(),default);
insert into logistic_state values(null,1,'2021-01-03 11:32:12','已经分配给快递员张三风处理，联系电话:13517115276');
insert into logistic_state values(null,1,'2021-01-03 14:12:23','已经由武昌转运中心发出');
insert into logistic_state values(null,1,'2021-01-04 19:34:58','已经到达郑州转运中心');
insert into logistic_state values(null,1,'2021-01-04 22:31:09','已经由郑州转运中心发出');
insert into logistic_state values(null,1,'2021-01-05 17:01:32','到达太原转运中心');
insert into logistic_state values(null,1,'2021-01-05 20:31:00','到达太原平阳路快递点');
insert into logistic_state values(null,1,'2021-01-05 20:31:00','太原平阳路快递点，取货码为E0105-23-0236');
insert into logistic_state values(null,1,'2021-01-06 12:45:01','梁言已签收');

select * from logistic;