#include <bits/stdc++.h>
using namespace std;
string freeType[] = {"ˮ��","���","��Ȼ����","��ҵ��"}; 
int main(void)
{
	freopen("remain_insert.txt","w",stdout);
	srand(time(NULL));
	//insert into remain values(null,1,'ˮ��',0);
	for(int i=1;i<=315;++i)
	{
		for(int j=0;j<4;++j)
		{
			string free = freeType[j];
			char cfree[20];
			strcpy(cfree,free.c_str());
			printf("insert into remain values(null,%d,'%s',0);\n",i,cfree);	
		}	
	} 
	fclose(stdout);
	
	return 0;	
} 
