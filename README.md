## <font color="purple">后疫情时代下的社区管理系统</font>
<font face="华文楷体" size="5"> 答辩人：**许智超**</font>

系统入口: [http://119.45.242.193:8080/admin_login](http://119.45.242.193:8080/admin_login)

>可以有四个身份登录：社区管理员，普通住户，外来游客(临时申请进入)， 与社区合作单位的党员(查看并报名"下沉"活动)
管理员登陆：
用户名：许智超
密码：xzc

>住户登录：
用户名：zcy
密码：zcy

>游客临时登录：均可，建议用如下
姓名：吕方
身份证号：422156198902276588

>党员登录可以账号：
姓名：赵梓华
身份证号：420100198911212574

---
### 一、背景介绍
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font face="宋体">2020年底，新冠肺炎在世界范围内爆发。武汉封城，全国人们齐心协力，在党和政府的领导下与病毒和疫情展开了搏斗。在这场灾难中，很多人失去了宝贵的生命……所幸的是，我们终于控制住了疫情，开始复工、复学。截止到2021年3月10号，全国各省确诊患者患者降低到了两位数。</font>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font face="宋体">但是，在如今的后疫情时代，我们仍要做出常态化的防控。学校、社区是人员流动最多的地方。很多社区出入登记还是手写记录，小区居民日常交水电费，查看社区公告、寄快递等必要的活动难免出现人数聚集的现象。</font>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font face="宋体">我们希望设计这样一个**系统**，给外来人员进出社区一个线上预约的通道，并且使得居民可以通过线上的方式，最大化简化日常的衣食住行，给居民和社区管理者带来方便，同时降低了人员聚集的程度，更利于防控疫情。</font>
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210311183301773.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQwNTMxNDc5,size_16,color_FFFFFF,t_70)

---
### 二、系统主要功能模块简介
1. **住户模块**：对社区的常住人口进行信息等级和管理
2. **缴费模块**：居民可以登录系统线上查询自己的水、电、气的余额和欠费情况
3. **公告模块**：社区居委会可以通过系统发布社区公告，方便信息发布和传达
4. **报修模块**：社区居民可以通过系统报修模块对故障的家用设备进行报修，社区可以帮助联系维修人员
5. **投诉模块**：这是一个居民可以向社区反映情况的模块，如施工声音大，收费不合理等等。也可以借这个模块进行中高人员返乡的上报。
6. **停车管理**: 用于进行居民车辆管理，社区停车场管理
7. **外来人员临时出入预约**：这个模块提供了对外预约的接口，外来人员可以以游客身份登录，填写个人信息以及申请出入的时间、出入的车辆。小区管理员可以进行审核。审核通过后方可在规定时间段内进出并进行进出。
8. **“党员下沉”志愿活动系统**：社区和周边单位党支部合作，发布志愿活动，合作单位的党员可以登录系统进行报名。系统记录党员的参与情况，可以计算累计时长。如“抗疫物资搬运活动”，“社区小学省作业辅导活动”……
9. **快递物流**：社区对快递物流公司进行招标和合作，通过系统线上下单，上门取货的方式方便居民寄快递，通过系统查看物流信息和取货码。

---
### 三、项目亮点
1. 后端使用SpringBoot框架
2. 数据库采用MySQL 8
3. 前端使用BootStrap
4. 对需求进行分析，确定模块，设计数据库并进行模式分解。共26张表
5. 运用较多的触发器保证数据一致性，运用存储过程封装较为复杂的业务逻辑
6. 涉及较复杂的SQL查询
7. 代码行数超过5万行
8. 代码规范，可拓展性强
9. 使用Ajax在前后端之间传递数据。并且操作后无需手动刷新网页
10. 使用cpp批量生成合法数据，用于单元测试和效果演示 
11. 项目已经部署到云服务器上

![在这里插入图片描述](https://img-blog.csdnimg.cn/20210311194302566.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQwNTMxNDc5,size_16,color_FFFFFF,t_70)
**部分SQL展示**：
```sql
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
```

```sql
-- 查询所有党员服务总时长，按照时长第一关键字降序，id第二关键字升序排
SELECT m.id, m.full_name, IFNULL(sum(actual_duration),0) AS tot_hour 
FROM party_member_info m LEFT OUTER JOIN activity_participation p ON (p.vid = m.id)
WHERE p.attendance = 'Yes'
GROUP BY (m.id) 
ORDER BY tot_hour DESC, m.id
Limit 3;
```
```sql
SELECT pid, project_name, content, begin_time, registration_deadline,recruitment_numbers, requirement, project_status, 
CASE WHEN (
	SELECT COUNT(*)
	FROM activity_registration ar 
	WHERE volunteer_id = '140106199812212558' 
		AND sa.pid = ar.pid
	) > 0 
	THEN '已报名' 
	ELSE '未报名' 
	END AS 'hasRegist'
FROM service_activity sa
ORDER BY pid DESC
LIMIT 0, 10;
```

```sql
-- 创建存储过程，参数为活动编号pid，统计剩余招募名额，并且按照先来先得的原则对
-- 待审核的报名者进行一键通过
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
```
---

![在这里插入图片描述](https://img-blog.csdnimg.cn/20210311195510483.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQwNTMxNDc5,size_16,color_FFFFFF,t_70)
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210311195807544.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQwNTMxNDc5,size_16,color_FFFFFF,t_70)
**生成住户信息的CPP文件：**
```cpp
#include <bits/stdc++.h>
using namespace std;
int province[] = {11,12,13,14,15,21,22,23,31,32,33,34,35,36,37,41,42,43,44,45,46,50,51,52,53,54,61,62,63,64,65,71,81,82};
int monthday[] = {0,31,28,31,30,31,30,31,31,30,31,30,31};
/*
赵钱孙李，周吴郑王。
冯陈褚卫，蒋沈韩杨。
朱秦尤许，何吕施张。
孔曹严华，金魏陶姜。
戚谢邹喻，柏水窦章。
云苏潘葛，奚范彭郎。
鲁韦昌马，苗凤花方。
俞任袁柳，酆鲍史唐。
费廉岑薛，雷贺倪汤。
滕殷罗毕，郝邬安常。
*/
string LastName[] = {
	"赵","钱","孙","李","周","吴","郑","王","冯","陈","褚","卫","蒋","沈","韩","杨",
	"朱","秦","尤","许","何","吕","施","张","孔","曹","严","华","金","魏","陶","姜",
	"戚","谢","邹","喻","柏","水","窦","章","云","苏","潘","葛","奚","范","彭","郎",
	"孟","曾","刘","罗" 
};
string BoyFirstName[] = {"江","山","海","祖","昌","世","伯","仲","叔","季","昆","旭","浩","贵","福","生","龙",
	"元","全","国","胜","学","祥","才","发","武","新","利","清","飞","子","杰","昌","诚","梁" 
}; 
string GirlFirstName[] = {"娜","娆","雨","馨","嘉","倩","茜","娅","楠","如","嫣","玉","姝","婉","婷","莹","艳",
	"瑞","凡","琼","勤","珍","莉","叶","秋","仪","瑶","瑾","艺","幂","梦","怡","珊","薇","丹" 
};
string getName(int op)
{
	int len = rand()%2+1;
	string name = LastName[rand()%52];
	if(op==0) //女性名字 
	{
		while(len--)
			name += GirlFirstName[rand()%35];
	}
	else
	{
		while(len--)
			name += BoyFirstName[rand()%35];	
	}
	return name;
}
string getUid() //生成一个用户名 
{
	int len = rand()%5+3;
	string uid = "";
	uid += (char)(rand()%26+'A');
	for(int i=1;i<len;++i)
		uid += (char)(rand()%26+'a');
	return uid; 
}
string getPwd()
{
	int len = rand()%15+6;
	string pwd = "";
	for(int i=0;i<len;++i)
	{
		int val = rand()%94+33;
		if(val==96||val==92||val==34||val==39)
		{
			val = 97;
		}
		pwd += (char)(val);
	}
	return pwd;
}
string getPhoneNum() //生成一个11位的手机号码
{
	// /^1(?:3\d|4[4-9]|5[0-35-9]|6[67]|7[013-8]|8\d|9\d)\d{8}$/
	string phone = "1";
	int p2 = rand()%7+3;
	phone += to_string(p2);
	int p3 = 0;
	switch (p2) {
		case 3:case 8:case 9: phone += (char)(rand()%10+'0');break;
		case 4: phone += (char)(rand()%6+4+'0');break;
		case 5: 
			p3 = rand()%9;
			if(p3>=4) ++p3;
			phone += (char)(p3+'0');
			break;
		case 6:phone += (char)(rand()%2+6+'0');break;
		case 7:
			int p3 = rand()%8;
			if(p3>=2) ++p3;
			phone += (char)(p3+'0');
			break;
	} 
	for(int i=4;i<=11;++i)
		phone += (char)(rand()%10+'0');
	return phone;
} 
bool isLeap(int y) 
{
	return y%400==0||(y%4==0&&y%100!=0);
}
string getProvince(int district)
{
	string province = ""; 
	switch(district){
		//华北地区 
		case 11:province = "北京市";break;	case 12:province = "天津市";break;	case 13:province = "河北省";break;
		case 14:province = "山西省";break;	case 15:province = "内蒙古自治区";break;
		//东北地区 
		case 21:province = "辽宁省";break;	case 22:province = "吉林省";break;	case 23:province = "黑龙江省";break;
		//华东地区 
		case 31:province = "上海市";break;	case 32:province = "江苏省";break;	case 33:province = "浙江省";break;	
		case 34:province = "安徽省";break; 	case 35:province = "福建省";break;	case 36:province = "江西省";break;	
		case 37:province = "山东省";break;	
		//华中地区 
		case 41:province = "河南省";break;	case 42:province = "湖北省";break;	case 43:province = "湖南省";break;
		//华南地区
		case 44:province = "广东省";break;	case 46:province = "海南省";break;  case 45:province = "广西壮族自治区";break;
		//西南地区 
		case 51:province = "四川省";break;	case 52:province = "贵州省";break;	case 50:province = "重庆市";break;
		case 53:province = "云南省";break;	case 54:province = "西藏自治区";break;
		//西北地区 
		case 61:province = "陕西省";break;	case 62:province = "甘肃省";break;	case 63:province = "青海省";break;
		case 64:province = "宁夏回族自治区";break;	case 65:province = "新疆维吾尔自治区";break;
		//特别地区
		case 71:province = "台湾地区";break;	case 81:province = "香港特别行政区";break;	case 82:province = "澳门特别行政区";break; 
		default:province = "未知"; 
	}
	return province;
}
char getCheckDigit(string ch) { //生成身份证号的校验位 
	int sign; 
	sign = ((ch[0]-'0')*7 + (ch[1]-'0')*9 + (ch[2]-'0')*10 + (ch[3]-'0')*5 + (ch[4]-'0')*8 + (ch[5]-'0')*4 + (ch[6]-'0')*2
	     + (ch[7]-'0')*1 + (ch[8]-'0')*6 + (ch[9]-'0')*3 + (ch[10]-'0')*7 + (ch[11]-'0')*9+ (ch[12]-'0')*10+(ch[13]-'0')*5
		 + (ch[14]-'0')*8+ (ch[15]-'0')*4+ (ch[16]-'0')*2) % 11;
	if(sign==10) return 'X';
	else return (char)(sign+'0');
}
string getIdentity() //生成一个正确的身份证号 
{
	int cnt = sizeof(province)/sizeof(int);
	int pid = province[rand()%cnt];
	string ans = to_string(pid);
	ans += '0';
	ans += (char)('0'+rand()%5+1);
	ans += '0';
	ans += (char)('0'+rand()%7+1);
	int year = rand()%35+1965;
	int month = rand()%12+1;
	int day = rand()%monthday[month]+1;
	if(isLeap(year)&&month==2)
		day = rand()%29+1;
	string strMonth;
	string strDay;
	if(month<10) 
		strMonth = string("0")+to_string(month);
	else 
		strMonth = to_string(month);
	if(day<10)
		strDay = string("0")+to_string(day);
	else 
		strDay = to_string(day);
	ans += to_string(year) + strMonth + strDay;
	for(int i=0;i<3;++i)
		ans += (char)(rand()%10+'0');
	char checkDigit = getCheckDigit(ans);
	ans += checkDigit;
	return ans;
}
string getEmail(string uid)
{
	string email = "";
	int type = rand()%3;
	if(type==0)
	{
		int len = rand()%2+9;
		while(len--)
			email += (char)(rand()%10+'0');
		email += "@qq.com";
	}
	else
	{
		if(type==1) email = uid+"@163.com";
		else email = uid + "@126.com"; 
	}
	return email;
}
int main(void)
{
	srand(time(NULL));
	for(int i=1;i<=30;++i)
		cout<<getPhoneNum()<<endl;
	return 0;
	freopen("user_info_insert.txt","w",stdout);
	string uid,uName,pwd,idNum,sex,phoneNum,email;
	map<string,int> mpUid;
	map<string,int> mpUName;
	map<string,int> mpPwd;
	map<string,int> mpIdNum;
	map<string,int> mpPhone;
	map<string,int> mpEmail;
	for(int i=53;i<=129;++i)
	{
		uid = getUid();
		idNum = getIdentity();
		pwd = getPwd();
		phoneNum = getPhoneNum();
		email = getEmail(uid);
		int num = idNum[16]-'0';
		if(num%2) //男 
		{
			sex = "男";
			uName = getName(1); 
		}	
		else //女 
		{
			sex = "女";
			uName = getName(0); 
		}
		if(mpUid.count(uid)||mpUName.count(uName)||mpPwd.count(pwd)||mpIdNum.count(idNum)||mpPhone.count(phoneNum)||mpEmail.count(email))
		{
			--i; continue;
		}
		char cuid[20],cuName[20],cpwd[50],cidNum[20],csex[20],cphoneNum[20],cemail[100];
		strcpy(cuid,uid.c_str());
		strcpy(cuName,uName.c_str());
		strcpy(cpwd,pwd.c_str());
		strcpy(cidNum,idNum.c_str());
		strcpy(csex,sex.c_str());
		strcpy(cphoneNum,phoneNum.c_str());
		strcpy(cemail,email.c_str());
		mpUid[uid] = 1;
		mpUName[uName] = 1;
		mpPwd[pwd] = 1;
		mpIdNum[idNum] = 1;
		mpPhone[phoneNum] = 1;
		mpEmail[email] = 1;
		printf("insert into user_info values('%s','%s','%s','%s','%s','%s','%s',%d);\n",cuid,cuName,cpwd,cidNum,csex,cphoneNum,cemail,i);
	}	
	return 0;	
} 
```

**生成车辆信息的CPP**：
```cpp
#include <bits/stdc++.h>
using namespace std;
string getCarNum()
{
	string ans = "鄂";
	string str = "AAAAAABCDEFGHJKLMNRSAA";
	int len = str.length();
	int index = rand()%len;
	ans += str[index]; //确定车牌号的市
	for(int i=1;i<=5;++i)
	{
		ans += (char)(rand()%10+'0');	
	} 
	return ans;
}
int main(void) {
	freopen("parking_insert.txt","w",stdout);
	srand(time(NULL));
	map<string,int> mp;
	for(int i=1;i<=20;++i)
	{
		for(int j=1;j<=15;++j)
		{
			string desc = to_string(i)+"排"+to_string(j)+"号";
			char cDesc[20];
			strcpy(cDesc,desc.c_str()); 
			int op = rand()%7;
			if(op<=4)
			{
				printf("insert into parking values(null,'%s',default);\n",cDesc);	
			} 
			else 
			{
				string id;
				while(1)
				{
					id = getCarNum();
					if(mp.count(id)) continue;
					else break;
				}	
				mp[id] = 1;
				char cid[100];
				strcpy(cid,id.c_str());
				printf("insert into parking values(null,'%s','%s');\n",cDesc,cid);
			}	
		}
	}
	fclose(stdout);	
	return 0;
} 
```

许智超
2021年1月14日

---





