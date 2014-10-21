#include <pthread.h>
#include <math.h>
#include "screen.h"
#include "graphic.h"

pthread_mutex_t mutex_queue;

void *read_serial(void *graph){
	graphic *g = (graphic*)graph;

	FILE *arq = fopen("/dev/ttyUSB1", "r");

	char c; int x,y,i=0;
	while(1){
		fscanf(arq, "%c %d %d", &c, &x, &y);
		if((c == 'A' || c == 'B' || c == 'C' || c == 'X') && x>-100 && y>-100 && x<100 && y<100){
			i++;
			printf("%c %5d %5d            ", c, x, y);
			if(i%3 == 0) printf("\n");
			pthread_mutex_lock(&mutex_queue);
			if(c >= 'a' && c <= 'z')
				g->insert_node(c, x, y, MOBILE);
			else
				g->insert_node(c, x, y, LANDMARK);
			pthread_mutex_unlock(&mutex_queue);
		}
	}
	pthread_exit(NULL);
}

inline double dist(int x1, int y1, int x2, int y2){
	return pow(pow(x1-x2,2)+pow(y1-y2,2),0.5);
}

graphic *gr;
void data_received(char id, int x, int y){
	pthread_mutex_lock(&mutex_queue);
//	char name[] = {'r', 'e', 's', '/', id, 0};
	if(id >= 'a' && id <= 'z'){
		gr->insert_node(id, x, y, MOBILE);
/*		FILE *arq = fopen(name, "a");
		if(id == 'g') 
			fprintf(arq, "%f\t%f\n", dist(x,y,1000,1000), dist(x,y,1000,1000)*100/dist(1000,1000,0,0));
		else if(id == 'd') 
			fprintf(arq, "%f\t%f\n", dist(x,y,1000, 500), dist(x,y,1000, 500)*100/dist(1000, 500,0,0));
		else if(id == 'f') 
			fprintf(arq, "%f\t%f\n", dist(x,y,   0, 500), dist(x,y,   0, 500)*100/dist(   0, 500,0,0));
	fclose(arq);
*/
	}else
		gr->insert_node(id, x, y, LANDMARK);
	pthread_mutex_unlock(&mutex_queue);
}

void mark_node(char id, int x, int y){
	pthread_mutex_lock(&mutex_queue);
	gr->insert_node(id, x, y, MARK);
	pthread_mutex_unlock(&mutex_queue);
}

void *graph_thread(void *x){
	screen screen(800,600, "LOCALIZATION ALGORITHM");
	graphic g(&screen);
	gr = &g;
	pthread_mutex_init(&mutex_queue, NULL);
	while(1){
		pthread_mutex_lock(&mutex_queue);
		g.draw();
		pthread_mutex_unlock(&mutex_queue);
		usleep(300000);
	}
}

/*int main(){
	screen screen(800,600, "LOCALIZATION ALGORITHM");
	graphic g(&screen);	

	pthread_mutex_init(&mutex_queue, NULL);
	pthread_t serial_thread;
	pthread_create(&serial_thread, NULL, read_serial, &g);

	while(1){
		pthread_mutex_lock(&mutex_queue);
		g.draw();
		pthread_mutex_unlock(&mutex_queue);
		usleep(300000);
	}
	pthread_exit(NULL);
}*/
