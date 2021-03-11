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
