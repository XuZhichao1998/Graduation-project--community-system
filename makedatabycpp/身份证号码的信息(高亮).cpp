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
void setColor(WORD F,WORD B);//����һ�����������ı���ɫ
int main()
{
	system("title ���֤�Ų�ѯ��Ϣdesigned_by_���ǳ�"); 
	system("color 0E");
	char IDnumber[30],buf[30];
	int year,month,day,district,sign,flag,area_number;
	string province,area,sex;
	
	cout<<"������18λ�����֤���룺\n";
	
	while(1)
	{ 	
		setColor(FOREGROUND_BLUE|FOREGROUND_GREEN|FOREGROUND_INTENSITY,0);
		scanf("%s",IDnumber);
		if(IDnumber[17]=='x') IDnumber[17] = 'X';
		flag = judge(IDnumber);
		if(flag){ 
			sscanf(IDnumber,"%6s",buf);
			area_number = atoi(buf);    //���֤��ǰ��λ�����������ĵ�����Ϣ 
			sscanf(IDnumber,"%2s",buf); //��ֱ�� district = area_number % 10000�� 
			district = atoi(buf);       //���֤����ǰ��λ������ʡ�����������ر������� 
			
			sscanf(IDnumber,"%*6s%4s",buf);
			year = atoi(buf);
			sscanf(IDnumber,"%*10s%2s",buf);
			month = atoi(buf);
			sscanf(IDnumber,"%*12s%2s",buf);
			day = atoi(buf);
			sscanf(IDnumber,"%*16s%1s",buf);
			if((buf[0]-'0')%2) sex = "��";
			else sex = "Ů";
			bool flag_date = JudgeDate(year,month,day);
			if(!flag_date) {setColor(FOREGROUND_RED|FOREGROUND_RED|FOREGROUND_INTENSITY,0);cout<<"���ڲ��Ϸ���\n";cout<<"������������룺\n";continue;}
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
			setColor(FOREGROUND_GREEN|FOREGROUND_GREEN|FOREGROUND_INTENSITY,0);
			cout<<"�����֤�����Ӧ������Ϣ���£�\n";
			setColor(FOREGROUND_BLUE|FOREGROUND_RED|FOREGROUND_INTENSITY,0);
			printf("---------------------------------------\n");
			setColor(FOREGROUND_BLUE|FOREGROUND_RED|FOREGROUND_GREEN|FOREGROUND_INTENSITY,0);
			cout<<" �Ա�"<<sex<<endl;
			printf(" �������£�%d��%02d��%02d��",year,month,day);
			if(!flag_date)cout<<"������"; 
			cout<<endl;
			
			time_t timep;
		    struct tm *p;
		    time (&timep);
		    p=gmtime(&timep);
		    int currentYear = 1900+p->tm_year; 

			printf(" ���䣺%d\n",currentYear-year); 
			cout<<" ������"<<province<<endl;
			setColor(FOREGROUND_BLUE|FOREGROUND_RED|FOREGROUND_INTENSITY,0);
			printf("---------------------------------------\n");
		}
		else {cout<<"������������룺\n";setColor(FOREGROUND_BLUE|FOREGROUND_GREEN|FOREGROUND_INTENSITY,0);continue;} 
		cout<<endl;
		setColor(FOREGROUND_GREEN|FOREGROUND_RED|FOREGROUND_INTENSITY,0);
		cout<<"�����������֤���밴1��2(2������)���˳��밴0��";
		setColor(FOREGROUND_BLUE|FOREGROUND_GREEN|FOREGROUND_INTENSITY,0);
		int nn;cin>>nn;
		if(!nn) break;
		
		setColor(FOREGROUND_RED|FOREGROUND_GREEN|FOREGROUND_INTENSITY,0);
		if(nn==2){
			system("cls");
			system("color 0E");
		} 
		cout<<"���������18λ�����֤���룺\n"; 
	} 
	setColor(FOREGROUND_GREEN|FOREGROUND_GREEN|FOREGROUND_INTENSITY,0);cout<<"��";
	setColor(FOREGROUND_BLUE|FOREGROUND_RED|FOREGROUND_INTENSITY,0);  cout<<"��";
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
	if(leng != 18) {setColor(FOREGROUND_RED|FOREGROUND_RED|FOREGROUND_INTENSITY,0);cout<<"����������֤����Ϊ"<<leng<<"λ��ӦΪ18λ��\n";return false;}
	else
	{
		if(!(num[17]>='0'&&num[17]<='9'||num[17]=='x'||num[17]=='X')) {cout<<"���һλ�������\n"; return false;}
		for(int i=0;i<17;i++)
		{
			if(num[i]<'0'||num[i]>'9') {cout<<"�������\n";return false;}	
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
		default: cout<<"����\n";return false;
	}
		if(ch[17]!=a){setColor(FOREGROUND_RED|FOREGROUND_RED|FOREGROUND_INTENSITY,0);cout<<"���ݼ��㣬У�������ĩβӦΪ"<<a<<"��\n";return false;}
	
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
	cout<<"��л����ʹ�ã��ٻᣡ\n";
	system("pause");
	return true;
}
void setColor(WORD F,WORD B)//����һ�����������ı���ɫ
{ 
	HANDLE hc = GetStdHandle(STD_OUTPUT_HANDLE);
	SetConsoleTextAttribute(hc,F|B);
}
