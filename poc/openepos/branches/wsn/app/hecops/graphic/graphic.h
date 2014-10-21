#ifndef _GRAPHIC_H_
#define _GRAPHIC_H_

#include "screen.h"

typedef enum { LANDMARK, MOBILE, MARK } node_type;

class node{
public:
	char id;
	int x;
	int y;
	node_type type;
	node *next;

	node(char id, int x, int y, node_type type){
		this->id   = id;
		this->x    = x;
		this->y    = y;
		this->type = type;
		next = NULL;
	}
};

class graphic{
private:
	screen *s;
	node *list;
	float dx, dy;
	int margem;
	int xmin, xmax, ymin, ymax;
	int node_size;

	int adjustx(int);
	int adjusty(int);
	void circle(node*, int, int);
	void recalculate_borders();
	void set_coordinates_system();
	void draw_nodes();
	void draw_grid();

public:
	graphic(screen *s){
		this->s = s;
		list = NULL;
		margem = 40;
		node_size = 40;
		xmin = ymin = 10000;
		xmax = ymax = -10000;
	}

	void insert_node(char, int, int, node_type);
	void remove_node(char);
	void replace_node();
	void draw();
};

#endif
