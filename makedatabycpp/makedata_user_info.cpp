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
