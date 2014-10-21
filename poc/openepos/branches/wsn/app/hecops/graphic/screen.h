#ifndef _SCREEN_H_
#define _SCREEN_H_

#include <X11/Xlib.h>
#include <iostream.h>
#include <unistd.h>

enum{
	WHITE  = 0xFFFFFF,
	BLACK  = 0x000000,
	RED    = 0xFF0000,
	GREEN  = 0x00FF00,
	BLUE   = 0x0000FF,
	YELLOW = 0xFFFF00
};

class screen{
private:
	Display *display;
	Window window;
	GC gc;

public:
	int width;
	int heigth;

	screen(int, int, char*);
	screen();
	void set_color(int);
	void draw_rectangle(int x, int y, int w, int h);
	void draw_circle(int x, int y, int d);
	void draw_string(char *, int x, int y);
	void draw_line(int, int, int, int);
	void close();
	void flush();
};

#endif
