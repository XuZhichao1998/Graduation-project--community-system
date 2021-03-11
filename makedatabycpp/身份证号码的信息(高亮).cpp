#include <iostream>
#include <cstring>
#include <cstdio>
#include <string>
#include <stdlib.h>
#include <windows.h>
#include <time.h> 
using namespace std;
bool judge(char * num);
bool runnian(int y);
bool JudgeDate(int y,int m,int d);
void setColor(WORD F,WORD B);//定义一个函数设置文本颜色
int main()
{
	system("title 身份证号查询信息designed_by_许智超"); 
	system("color 0E");
	char IDnumber[30],buf[30];
	int year,month,day,district,sign,flag,area_number;
	string province,area,sex;
	
	cout<<"请输入18位的身份证号码：\n";
	
	while(1)
	{ 	
		setColor(FOREGROUND_BLUE|FOREGROUND_GREEN|FOREGROUND_INTENSITY,0);
		scanf("%s",IDnumber);
		if(IDnumber[17]=='x') IDnumber[17] = 'X';
		flag = judge(IDnumber);
		if(flag){ 
			sscanf(IDnumber,"%6s",buf);
			area_number = atoi(buf);    //身份证号前六位，代表完整的地区信息 
			sscanf(IDnumber,"%2s",buf); //或直接 district = area_number % 10000； 
			district = atoi(buf);       //身份证号码前两位，代表省或自治区、特别行政区 
			
			sscanf(IDnumber,"%*6s%4s",buf);
			year = atoi(buf);
			sscanf(IDnumber,"%*10s%2s",buf);
			month = atoi(buf);
			sscanf(IDnumber,"%*12s%2s",buf);
			day = atoi(buf);
			sscanf(IDnumber,"%*16s%1s",buf);
			if((buf[0]-'0')%2) sex = "男";
			else sex = "女";
			bool flag_date = JudgeDate(year,month,day);
			if(!flag_date) {setColor(FOREGROUND_RED|FOREGROUND_RED|FOREGROUND_INTENSITY,0);cout<<"日期不合法！\n";cout<<"请检查后重新输入：\n";continue;}
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
			setColor(FOREGROUND_GREEN|FOREGROUND_GREEN|FOREGROUND_INTENSITY,0);
			cout<<"该身份证号码对应公民信息如下：\n";
			setColor(FOREGROUND_BLUE|FOREGROUND_RED|FOREGROUND_INTENSITY,0);
			printf("---------------------------------------\n");
			setColor(FOREGROUND_BLUE|FOREGROUND_RED|FOREGROUND_GREEN|FOREGROUND_INTENSITY,0);
			cout<<" 性别："<<sex<<endl;
			printf(" 出生年月：%d年%02d月%02d日",year,month,day);
			if(!flag_date)cout<<"（错误）"; 
			cout<<endl;
			
			time_t timep;
		    struct tm *p;
		    time (&timep);
		    p=gmtime(&timep);
		    int currentYear = 1900+p->tm_year; 

			printf(" 年龄：%d\n",currentYear-year); 
			cout<<" 地区："<<province<<endl;
			setColor(FOREGROUND_BLUE|FOREGROUND_RED|FOREGROUND_INTENSITY,0);
			printf("---------------------------------------\n");
		}
		else {cout<<"请检查后重新输入：\n";setColor(FOREGROUND_BLUE|FOREGROUND_GREEN|FOREGROUND_INTENSITY,0);continue;} 
		cout<<endl;
		setColor(FOREGROUND_GREEN|FOREGROUND_RED|FOREGROUND_INTENSITY,0);
		cout<<"继续输入身份证号请按1或2(2会清屏)，退出请按0：";
		setColor(FOREGROUND_BLUE|FOREGROUND_GREEN|FOREGROUND_INTENSITY,0);
		int nn;cin>>nn;
		if(!nn) break;
		
		setColor(FOREGROUND_RED|FOREGROUND_GREEN|FOREGROUND_INTENSITY,0);
		if(nn==2){
			system("cls");
			system("color 0E");
		} 
		cout<<"请继续输入18位的身份证号码：\n"; 
	} 
	setColor(FOREGROUND_GREEN|FOREGROUND_GREEN|FOREGROUND_INTENSITY,0);cout<<"再";
	setColor(FOREGROUND_BLUE|FOREGROUND_RED|FOREGROUND_INTENSITY,0);  cout<<"会";
	setColor(FOREGROUND_RED|FOREGROUND_RED|FOREGROUND_INTENSITY,0); cout<<"!\n";
	setColor(FOREGROUND_BLUE|FOREGROUND_RED|FOREGROUND_GREEN,0);
	system("pause");
	return 0;
}

bool judge(char * num)
{	
	char * ch = num;
	int leng = strlen(num);
	bool flag1,flag2;
	if(leng != 18) {setColor(FOREGROUND_RED|FOREGROUND_RED|FOREGROUND_INTENSITY,0);cout<<"您输入的身份证号码为"<<leng<<"位！应为18位！\n";return false;}
	else
	{
		if(!(num[17]>='0'&&num[17]<='9'||num[17]=='x'||num[17]=='X')) {cout<<"最后一位输入错误！\n"; return false;}
		for(int i=0;i<17;i++)
		{
			if(num[i]<'0'||num[i]>'9') {cout<<"输入错误！\n";return false;}	
		}	
	} 
	int sign; 
	sign = ((ch[0]-'0')*7 + (ch[1]-'0')*9 + (ch[2]-'0')*10 + (ch[3]-'0')*5 + (ch[4]-'0')*8 + (ch[5]-'0')*4 + (ch[6]-'0')*2
	     + (ch[7]-'0')*1 + (ch[8]-'0')*6 + (ch[9]-'0')*3 + (ch[10]-'0')*7 + (ch[11]-'0')*9+ (ch[12]-'0')*10+(ch[13]-'0')*5
		 + (ch[14]-'0')*8+ (ch[15]-'0')*4+ (ch[16]-'0')*2) % 11;
	char a;
	switch(sign)
	{
		case 0: a = '1';break;	case 1: a = '0';break;	case 2: a = 'X';break;	case 3: a = '9';break;	case 4: a = '8';break;		
		case 5: a = '7';break;	case 6: a = '6';break;	case 7: a = '5';break;	case 8: a = '4';break;	case 9: a = '3';break;	
		case 10:a = '2';break;		
		default: cout<<"错了\n";return false;
	}
		if(ch[17]!=a){setColor(FOREGROUND_RED|FOREGROUND_RED|FOREGROUND_INTENSITY,0);cout<<"根据计算，校验码错误，末尾应为"<<a<<"！\n";return false;}
	
return true;
}

bool runnian(int y)
{
	if(y%400==0||y%100!=0&&y%4==0) return true;
	return false;
}

bool JudgeDate(int y,int m,int d)
{
	if(y<0||m<0||d<0) return false;
	if(m<0||m>12) return false; 
	if(runnian(y)&&m==2){
		if(d>29)return false;
		else return true;	
	}
	if(!runnian(y)&&m==2){
		if(d>28) return false;
		else return true;
	}
	if(m==1||m==3||m==5||m==7||m==8||m==10||m==12)
	{
		if(d>31)return false;
		else return true;
	}
	if(m==4||m==6||m==9||m==11){
		if(d>30) return false;
		else return true;
	}
	cout<<"感谢您的使用！再会！\n";
	system("pause");
	return true;
}
void setColor(WORD F,WORD B)//定义一个函数设置文本颜色
{ 
	HANDLE hc = GetStdHandle(STD_OUTPUT_HANDLE);
	SetConsoleTextAttribute(hc,F|B);
}
