#include <lua/lauxlib.h>
#include <lua/lualib.h>

#include <utility/ostream.h>

extern void register_native_functions(lua_State* L); /* All lua program that uses native functions should implement this. */

const char* LUA_APP = 
#include "lua_applications/dmec_app_lua.h"
;

System::OStream cout;


int main()
{
    cout << "This is Lua!\n";
    
	int error = 0;
	lua_State *L = lua_open(); //open the virtual machine
	luaL_openlibs(L); //open the libraries you'll need
	
    /* Registering native functions */
    register_native_functions(L);
    
    const char* app = LUA_APP; //get your Lua application
    error = luaL_loadbuffer(L, app, strlen(app), 0); //compile it
	if (error) {
        cout << "lua COMPILATION ERROR\n";
    }
    /*
    else {
        cout << "lua COMPILATION OK\n";
    }
    */
    
    
    error += lua_pcall(L, 0, 0, 0); //call it (ORIGINAL)
    // error += luaL_dostring(L, app); // by MKL
    if (error) {
        cout << "lua RUNTIME ERROR\n";
    }
    
    cout << "CALLL OK\n";
    // while (true);    
	
    if (error > 0) lua_pop(L, 1); //assert nothing's wrong
	
    lua_close(L); //close the virtual machine
	
    cout << "Bye\n";
    while (true);
    
    return 0;
}
