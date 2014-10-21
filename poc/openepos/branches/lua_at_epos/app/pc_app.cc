#include <lua/lauxlib.h>
#include <lua/lualib.h>
#include <lua/apps.h> //define your Lua program in this header

int main()
{
	int error;
	lua_State *L = lua_open(); //open the virtual machine
	luaL_openlibs(L); //open the libraries you'll need
	char * app = RT; //get your Lua application
	error = luaL_loadbuffer(L, app, strlen(app), 0); //compile it
	error += lua_pcall(L, 0, 0, 0); //call it
	if (error > 0) lua_pop(L, 1); //assert nothing's wrong
	lua_close(L); //close the virtual machine
	return 0;
}
