#include <stdio.h>

template <class T>
inline int min(T a, T b){
	return ( a<b ? a : b );
}

template <class T>
inline int max(T a, T b){
	return ( a>b ? a : b );
}

struct Pair{
	char src, dst;
	int index;
	int rssi[100];
	Pair *next;

	Pair(){
		next = NULL; index = 0;
	}

	void init(char src, char dst){
		this->src = src; this->dst = dst;
		char filename[] = { 'd','a','t','a','/',min(src,dst), max(src,dst), 0};
		FILE *arq = fopen(filename, "r");
		if(arq){
			int a, b; int &x = min(src,dst) == src ? a : b;
			for(int i=0; i<100; i++){
				fscanf(arq,"%d %d", &a, &b);
				rssi[i] = x;
			}
			fclose(arq);
		} /*else {
			printf("poutzzz! %s doesn't exist\n", filename);
		}*/
	}

	int get_rssi(char src, char dst){
		return rssi[index++%100];
	}
};

struct Pair_List{
	Pair *first;

	Pair_List(){
		first = NULL;
	}

	Pair *get(char src, char dst){
		Pair *x;
		for(x=first;x!=NULL;x=x->next)
			if(src == x->src && dst == x->dst)
				return x;
		
		return NULL;
	}

	Pair *create_pair(char src, char dst){
		Pair *nw = new Pair;
		nw->init(src,dst);
		nw->next = first;
		first = nw;
	}
};

inline void toCapital(char &c){
	if(c >= 'a' && c <= 'z') c += 'A' - 'a';
}

int rssi_value(char src, char dst){
	static Pair_List pl;

	toCapital(src); toCapital(dst);
	Pair *p = pl.get(src, dst);
	
	if(p == NULL)
		p = pl.create_pair(src, dst);
	
	if(p == NULL) return -1;	
	return p->get_rssi(src,dst);
}

/*
int main(){
	for(int i=0; i<15; i++){
		//printf("%d\n", rssi_value('C'+i, 'A'));
		char c = 'a'+i; toCapital(c);
		printf("%c ", c);
	} printf("\n");
}*/


