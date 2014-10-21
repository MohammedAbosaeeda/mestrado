#include "graphic.h"

inline int max(int a, int b){
	return ( a>b ? a : b);
}

inline int min(int a, int b){
	return ( a<b ? a : b );
}

void graphic::insert_node(char c, int x, int y, node_type t){
	node *i, *n = new node(c, x, y, t);
	for(i = list; i!=NULL; i = i->next){
		if(c == i->id){
			if(x == i->x && y == i->y && t == i->type)
				return;
			remove_node(c);
		}
	}

	xmax = max(xmax, x); xmin = min(xmin, x);
	ymax = max(ymax, y); ymin = min(ymin, y);
	n->next = list;
	list = n;
}

void graphic::recalculate_borders(){
/*	node *i;
	ymax = xmax = -99999;
	xmin = ymin =  99999;
	for(i = list; i!=NULL; i = i->next){
		xmax = max(xmax, i->x); xmin = min(xmin, i->x);
		ymax = max(ymax, i->y); ymin = min(ymin, i->y);
	}
*/}

void graphic::remove_node(char id){
	node *i, *pre = NULL;
	for(i = list; i!=NULL; i = i->next){
		if(id == i->id){
			if(pre == NULL)
				list = i->next;
			else
				pre->next = i->next;
			break;
		}
		pre = i;
	}
	delete i;
	s->set_color(BLACK);
	s->draw_rectangle(0,0,s->width, s->heigth);
	recalculate_borders();
	set_coordinates_system();
}

int graphic::adjustx(int x){
	x -= xmin;
	return (int)(x*dx) + margem;
}

int graphic::adjusty(int y){
	y -= ymin;
	return s->heigth - (int)(y*dy) - margem;
}

void graphic::circle(node *n, int d, int color){
	s->set_color(color);
	s->draw_circle(adjustx(n->x), adjusty(n->y), d);
}

void graphic::set_coordinates_system(){
	dx = (float)(s->width  - (margem<<1)) / (xmax - xmin);
	dy = (float)(s->heigth - (margem<<1)) / (ymax - ymin);
}

void graphic::draw_nodes(){
	node *n;
	static int mobile_mode = true;
	for(n = list; n!=NULL; n = n->next) if(n->type == MOBILE) circle(n, node_size, BLACK);
	draw_grid();
	for(n = list; n!=NULL; n = n->next){
		char str[] = { n->id, '\0' };
		switch(n->type){
			case LANDMARK:
				circle(n, node_size, BLUE);
				s->set_color(YELLOW);
				break;
			case MOBILE:
				if(mobile_mode){
					circle(n, node_size, RED);
					circle(n, node_size-10, BLACK);
					circle(n, node_size-18, RED);
				}else{
					//circle(n, node_size, BLACK);
					circle(n, node_size-18, RED);
				}
				s->set_color(BLACK);
				break;
			case MARK:
				s->set_color(YELLOW);
				s->draw_rectangle(adjustx(n->x)-7, adjusty(n->y)-7, 15, 15);
				s->set_color(BLACK);
				break;
		}
		s->draw_string(str, adjustx(n->x)-2, adjusty(n->y)+5);
	}
	mobile_mode = ! mobile_mode;
}

void graphic::draw_grid(){
	s->set_color(0x9F9F9F);

	int iter = s->width/100;
	int i, x=xmin, xd=(xmax-xmin)/iter, y, yd;
	char str[10];
	for(i=0;i<=iter;i++){
		sprintf(str, "%d", x);
		s->draw_string(str, adjustx(x)-3, s->heigth-7);
		s->draw_line(adjustx(x), adjusty(ymin), adjustx(x), adjusty(ymax));
		x += xd;
	}

	iter = s->heigth/100;
	y = ymin; yd = (ymax-ymin)/iter;
	for(i=0;i<=iter;i++){
		sprintf(str, "%d", y);
		s->draw_string(str, 10, adjusty(y)+5);
		s->draw_line(adjustx(xmin), adjusty(y), adjustx(xmax), adjusty(y));
		y += yd;
	}
}

void graphic::draw(){
	set_coordinates_system();
	draw_nodes();
	s->flush();
}
