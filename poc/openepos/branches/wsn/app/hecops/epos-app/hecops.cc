//#define _EPOS_
#ifdef  _EPOS_
#include <display.h>
#include <nic.h>
__USING_SYS
#endif

#include "localiza.h"
#include "util.h"

NIC nic;

void anchor(char id, long x, long y){
	Node *n; printf("%c (%l,%l)\n", id, x, y);
	Message m(EVERYBODY, n=new Node(id, x, y));
	while(true){
		nic.send(0,0,&m,sizeof(m)); /// Anchors have to broadcast their positions
		unsigned char *src; Message msg;
		src = ((unsigned char*)n);
		while(!nic.receive(src, src, &msg, sizeof(msg)));
		if(is_anchor(msg.src)){ /// If it receives another anchor's position, it will take note of RSSI in a message and broadcast it (for calibration purposes)
			msg.n.rssi = msg.rssi; Message mg(id, EVERYBODY, &msg.n);
			nic.send(0,0,&mg,sizeof(mg));
		}
		usleep(100000);
	}
}

#ifndef _EPOS_
extern void data_received(char, int, int);
#endif

void mobile(char id){
	Message msg, *m; Node me(id,0,0);
	NodeList nl; long x=-1, y=-1;
	unsigned char *src= (unsigned char*)&me;
	int confidence;
	const long rise = 100; long factor=rise;
	long soma=0; int i=0;

	while(true){
		while (!(nic.receive(src, src, &msg, sizeof(msg)) &&  msg.dst == EVERYBODY));
		if(msg.src == msg.n.id){ /// If message information is about the sender, it's added to the list of nodes with known coordinates
			 msg.n.rssi = msg.rssi;
			//if(nl.insert(msg.n)){
			nl.insert(msg.n);
				int n = min(3,nl.get_size());
				Node *node = nl.first;
				if(n == 1) { /// When there is just one node, an initial - and possibly quite wrong - estimation is setted
					x = node->x + msg.rssi * factor / rise;
					y = node->y + msg.rssi * factor / rise;
					confidence = nl.first->confidence * 3 / 4;
				} else { /// If more than one node is known, the estimated coordinates will be calculated
					long _x[n], _y[n], _rssi[n];
					confidence = 0;
					for(int i=0; i<n; i++){
						_x[i] = node->x;
						_y[i] = node->y;
						long c; int ctri;
						if(node->dev == -1){
							c = factor;
							ctri = 0;
						}else{
							c = node->dev;
							if(node->id_tri) ctri = nl.get(node->id_tri)->confidence;
							if(ctri < 80) ctri = 0;
						}
						_rssi[i] = node->rssi*c/rise;
						confidence += (node->confidence * 3 + ctri) / 4;
						node = node->next;
					}
					Node res;
					calculate(_x,_y,_rssi,n,&res);
					x = res.x; y = res.y;
				}
				confidence = confidence * 4 / 15;
				//printf("%c (%l,%l)\terror=%l\tconf=%d\n", id, x, y, id=='d'?distance(x,y,1000l,500l):distance(x,y,0l,1000l),confidence);
#ifndef _EPOS_
				data_received(id, (int)x, (int)y); /// just for drawing
#endif
				me.x = x; me.y = y; me.confidence = confidence;
				msg.src = id; msg.dst = EVERYBODY;
				msg.n = me;
				nic.send(0,0,&msg,sizeof(msg));
			//}
		}else{ /// If message information is not about the sender, triangulation will be verified
			Node *no = nl.get(msg.src);
			if(no){
				long dist = distance(no->x, no->y, msg.n.x, msg.n.y);
				factor = dist*rise/msg.n.rssi;
				soma += factor; i++;
				factor = soma/i;
				//printf("### %l\n", factor);
				if(dist / 2 > distance(msg.n.x, msg.n.y, x, y)){ /// If triangulation is constated, deviation will be corrected
					//printf("## Ó! Deu triângulo entre %c(%l,%l), %c(%l,%l) e %c(%l,%l)\n",
					//		no->id, no->x, no->y, msg.n.id, msg.n.x, msg.n.y, id, x, y);
					no = nl.get(msg.n.id);
					no->dev = factor;
					no->id_tri = msg.src;
				}
			}
		}
	}
}


#ifdef _EPOS_
int main(){
	mobile('a');
//	anchor('X', 10, 10);
//	return 0;
}
#endif

