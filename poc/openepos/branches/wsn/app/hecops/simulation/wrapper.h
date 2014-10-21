#ifndef _WRAPPER_H_
#define _WRAPPER_H_

#define NULL 0
#include <pthread.h>

#include <fcntl.h>
#include <unistd.h>

class Display{
private:
	int fd;
public:
	Display(){
		fd = open("/dev/stdout", O_WRONLY);
		if(fd == -1) _exit(1);
	}

	void putc(char c){
		write(fd, &c, 1);
	}
};

#include "util.h"

void memcpy(void *dst, void *src, int size){
	char *d = (char*)dst; char *s = (char*)src;
	if(size > 0) while(size--) *d++ = *s++;
}

#include <stdlib.h>
#include "localiza.h"
int rssi_value(char src, char dst);
class NIC{
private:
	int buffer_size;
	Message *buffer;

	int reads;
	int threshold;

	int turn;
	int free;

	pthread_mutex_t nic_mutex;
public:
	NIC(){
		buffer_size = 10;
		buffer = new Message[buffer_size];

		reads = 0;
		threshold = 5;

		turn = -1;
		free = 0;

		pthread_mutex_init(&nic_mutex, NULL);

		srand(time(0));
	}

	bool send(unsigned char *src, unsigned char *prot, void *msg, int size){
		if(free == -1)  return false;
		pthread_mutex_lock(&nic_mutex);
		memcpy(buffer+free, msg, size);
		if(turn == -1) turn = free;
		int next = (free + 1) % buffer_size;
		free = next!=turn ? next : -1;
		pthread_mutex_unlock(&nic_mutex);
		usleep(100000);
		return true;
	}

	bool receive(unsigned char *src, unsigned char *prot, void *msg, int size){
		if(turn == -1) return false;
		bool ret_value = true;

		pthread_mutex_lock(&nic_mutex);
		reads++;
		memcpy(msg, buffer+turn, size);
		float error = (1.1 - ((rand()%200)/1000.0)) * 3.0;
		Message *m=(Message*)msg;
		if(src) {
			Node *n = (Node*)src;
			if(m->src == n->id) ret_value = false;
			if(n->id >= 'a' && n->id <= 'z'){ n->x = 40l; n->y = 60l; }
			*((int*)msg) = (int)(distance(n->x, n->y, m->n.x, m->n.y) * error);
			*((int*)msg) = rssi_value(m->n.id,n->id);
		}

		if(reads == threshold){
			if(free == -1) free = turn;
			int next = (turn+1) % buffer_size;
			turn = next!=free ? next : -1;
			reads = 0;
		}
		pthread_mutex_unlock(&nic_mutex);
		usleep(100000);
		return ret_value;
	}
};

#endif
