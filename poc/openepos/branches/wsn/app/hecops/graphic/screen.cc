#include "screen.h"

screen::screen(int w, int h, char *wtitle){
	width  = w;
	heigth = h;

	int xpos = 0, ypos = 0;
	display = XOpenDisplay(NULL);
	window  = XCreateSimpleWindow(display, DefaultRootWindow(display), xpos, ypos, width, heigth, 0, WHITE, BLACK);
	XStoreName(display, window, wtitle);
	XSelectInput(display, window, ButtonPressMask | ButtonReleaseMask | PointerMotionMask | ExposureMask | KeyPressMask | StructureNotifyMask);
	XMapRaised(display, window);
	XMapWindow(display, window);
	gc = XCreateGC(display, window, 0, 0);
	XSetForeground(display, gc, WHITE);
	XClearWindow(display, window);
	XSync(display, 0);
	usleep(10000);
}

screen::screen(){
	screen(500,500,"-");
}

void screen::close(){
	XDestroyWindow(display, window);
	XCloseDisplay(display);
}

void screen::set_color(int color){
	XSetForeground(display, gc, color);
}

void screen::draw_circle(int x, int y, int d){
	XFillArc(display, window, gc, x-d/2, y-d/2, d, d, 0, 360<<6);
}

void screen::flush(){
	XFlush(display);
}

void screen::draw_string(char *s, int x, int y){
	XDrawString(display, window, gc, x, y, s, strlen(s));
}

void screen::draw_line(int x1, int y1, int x2, int y2){
	XDrawLine(display, window, gc, x1, y1, x2, y2);
}

void screen::draw_rectangle(int x, int y, int w, int h){
	XFillRectangle(display, window, gc, x, y, w, h);
}
