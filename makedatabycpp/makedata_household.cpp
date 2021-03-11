#include <bits/stdc++.h>
using namespace std;
int main(void) {
	freopen("household_insert.txt","w",stdout);
	srand(time(NULL));
	for(int i=1;i<=7;++i) //building_number
		for(int j=1;j<=3;++j) //unit_number
			for(int k=1;k<=5;++k) //floor_number
				for(int h=1;h<=3;++h) //room_number
					printf("insert into household(building_number,unit_number,room_number,parmanent_resident) values(%d,%d,%d0%d,%d);\n",i,j,k,h,rand()%5+1);
	
	return 0;
} 
