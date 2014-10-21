#include "wrapper.h"
#include "hecops.cc"

void *teste_thread(void *me){
	unsigned char src, prot;
	int rssi;
	Message m((int)me, EVERYBODY);
	bool x;
	x= nic.send(&src, &prot, &m, sizeof(m));
	printf("%c send has returned %s\n", (int)me, x ? "true" : "false");
	for(int i = 0; i<5; i++){
		bool y;
		y = nic.receive(&src, &src, &m, sizeof(m));
		
		if(y) printf("(%c) received data from %c\n", (int)me, m.src);
		else  printf("%c receive has returned false\n", (int)me);
	}
	pthread_exit(NULL);
}

struct anchor_parameters{
	char id;
	int x, y;
	
	anchor_parameters(char id, int x, int y){
		this->id = id;
		this->x  = x;
		this->y  = y;
	}
};
void *anchor_thread(void *x){
	anchor_parameters *ap = (anchor_parameters*) x;
	printf("Anchor node '%c' created\n", ap->id);
	anchor(ap->id, ap->x, ap->y);
}

extern void *graph_thread(void *);
void *mobile_thread(void *id){
	printf("Mobile node '%c' created\n", (int)id);
	mobile((int)id);
}

extern void mark_node(char, int, int);
int main(){
	pthread_t thds[9];

	pthread_t graphic;
	pthread_create(&graphic, NULL, graph_thread, NULL);

	pthread_create(thds+0, NULL, anchor_thread, (void*)new anchor_parameters('A', 1000,    0));
	pthread_create(thds+1, NULL, anchor_thread, (void*)new anchor_parameters('C',    0,    0));
	pthread_create(thds+2, NULL, anchor_thread, (void*)new anchor_parameters('H',  500, 1000));
	
	//pthread_create(thds+3, NULL, mobile_thread, (void*)('i'));
	pthread_create(thds+3, NULL, mobile_thread, (void*)('d'));
	//pthread_create(thds+5, NULL, mobile_thread, (void*)('b'));
	//pthread_create(thds+6, NULL, mobile_thread, (void*)('e'));
	pthread_create(thds+4, NULL, mobile_thread, (void*)('f'));
	pthread_create(thds+5, NULL, mobile_thread, (void*)('g'));

	usleep(100000);
	data_received('A', 1000,    0);
	//mark_node    ('B',  500,    0);
	data_received('C',    0,    0);
	mark_node    ('D', 1000,  500);
	//mark_node    ('E',  500,  500);
	mark_node    ('F',    0,  500);
	mark_node    ('G', 1000, 1000);
	data_received('H',  500, 1000);
	//mark_node    ('I',    0, 1000);

	for(int i=0; i<4; i++)
		pthread_join(thds[i], NULL);
}
