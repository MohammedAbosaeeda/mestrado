#include <stdio.h>
int main(int argn, char **arg){
	if(argn != 2){
		printf( "Usage: %s <filename>\n", arg[0]);
		return 1;
	}
	FILE *arq = fopen(arg[1],"r");
	int v[600];
	int i;
	for(i=0;!feof(arq) && i<600;i++){
		fscanf(arq, "%d",v+i);
	}
	
	int n=i-1, soma=0;
	for(i=0;i<n;i++)
		soma += v[i];
	printf("%d\n", soma/n);
}
