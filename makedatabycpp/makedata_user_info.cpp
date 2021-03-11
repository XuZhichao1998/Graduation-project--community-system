#include <bits/stdc++.h>
using namespace std;
int province[] = {11,12,13,14,15,21,22,23,31,32,33,34,35,36,37,41,42,43,44,45,46,50,51,52,53,54,61,62,63,64,65,71,81,82};
int monthday[] = {0,31,28,31,30,31,30,31,31,30,31,30,31};
/*
��Ǯ�������֣����
��������������
������������ʩ�š�
�ײ��ϻ�����κ�ս���
��л��������ˮ��¡�
�����˸��ɷ����ɡ�
³Τ������ﻨ����
����Ԭ����ۺ��ʷ�ơ�
�����Ѧ���׺�������
�����ޱϣ�����������
*/
string LastName[] = {
	"��","Ǯ","��","��","��","��","֣","��","��","��","��","��","��","��","��","��",
	"��","��","��","��","��","��","ʩ","��","��","��","��","��","��","κ","��","��",
	"��","л","��","��","��","ˮ","�","��","��","��","��","��","��","��","��","��",
	"��","��","��","��" 
};
string BoyFirstName[] = {"��","ɽ","��","��","��","��","��","��","��","��","��","��","��","��","��","��","��",
	"Ԫ","ȫ","��","ʤ","ѧ","��","��","��","��","��","��","��","��","��","��","��","��","��" 
}; 
string GirlFirstName[] = {"��","�","��","ܰ","��","ٻ","��","�","�","��","��","��","�","��","��","Ө","��",
	"��","��","��","��","��","��","Ҷ","��","��","��","�","��","��","��","��","ɺ","ޱ","��" 
};
string getName(int op)
{
	int len = rand()%2+1;
	string name = LastName[rand()%52];
	if(op==0) //Ů������ 
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
string getUid() //����һ���û��� 
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
string getPhoneNum() //����һ��11λ���ֻ�����
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
		//�������� 
		case 11:province = "������";break;	case 12:province = "�����";break;	case 13:province = "�ӱ�ʡ";break;
		case 14:province = "ɽ��ʡ";break;	case 15:province = "���ɹ�������";break;
		//�������� 
		case 21:province = "����ʡ";break;	case 22:province = "����ʡ";break;	case 23:province = "������ʡ";break;
		//�������� 
		case 31:province = "�Ϻ���";break;	case 32:province = "����ʡ";break;	case 33:province = "�㽭ʡ";break;	
		case 34:province = "����ʡ";break; 	case 35:province = "����ʡ";break;	case 36:province = "����ʡ";break;	
		case 37:province = "ɽ��ʡ";break;	
		//���е��� 
		case 41:province = "����ʡ";break;	case 42:province = "����ʡ";break;	case 43:province = "����ʡ";break;
		//���ϵ���
		case 44:province = "�㶫ʡ";break;	case 46:province = "����ʡ";break;  case 45:province = "����׳��������";break;
		//���ϵ��� 
		case 51:province = "�Ĵ�ʡ";break;	case 52:province = "����ʡ";break;	case 50:province = "������";break;
		case 53:province = "����ʡ";break;	case 54:province = "����������";break;
		//�������� 
		case 61:province = "����ʡ";break;	case 62:province = "����ʡ";break;	case 63:province = "�ຣʡ";break;
		case 64:province = "���Ļ���������";break;	case 65:province = "�½�ά���������";break;
		//�ر����
		case 71:province = "̨�����";break;	case 81:province = "����ر�������";break;	case 82:province = "�����ر�������";break; 
		default:province = "δ֪"; 
	}
	return province;
}
char getCheckDigit(string ch) { //�������֤�ŵ�У��λ 
	int sign; 
	sign = ((ch[0]-'0')*7 + (ch[1]-'0')*9 + (ch[2]-'0')*10 + (ch[3]-'0')*5 + (ch[4]-'0')*8 + (ch[5]-'0')*4 + (ch[6]-'0')*2
	     + (ch[7]-'0')*1 + (ch[8]-'0')*6 + (ch[9]-'0')*3 + (ch[10]-'0')*7 + (ch[11]-'0')*9+ (ch[12]-'0')*10+(ch[13]-'0')*5
		 + (ch[14]-'0')*8+ (ch[15]-'0')*4+ (ch[16]-'0')*2) % 11;
	if(sign==10) return 'X';
	else return (char)(sign+'0');
}
string getIdentity() //����һ����ȷ�����֤�� 
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
		if(num%2) //�� 
		{
			sex = "��";
			uName = getName(1); 
		}	
		else //Ů 
		{
			sex = "Ů";
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
