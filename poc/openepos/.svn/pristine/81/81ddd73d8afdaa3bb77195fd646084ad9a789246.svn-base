extern "C" 
{
    #include <lua.h>
    #include <lauxlib.h>
    #include <lualib.h>
}

#include <cstring> // strlen

#include <iostream>

using namespace std;

extern void register_native_functions(lua_State* L); /* All lua program that uses native functions should implement this. */

const char* LUA_APP = 
// #include "hello_epos_lua.h" // Include your lua application HERE!
// #include "adder_ffi_lua.h"
#include "dmec_app_lua.h"
// #include "original_lua_programs/dmec_app_lua.h"
// #include "adder_pure_lua.h"
;


static void l_message (const char *pname, const char *msg) {
  if (pname) fprintf(stderr, "%s: ", pname);
  fprintf(stderr, "%s\n", msg);
  fflush(stderr);
}

static int report (lua_State *L, int status) {
  if (status && !lua_isnil(L, -1)) {
    const char *msg = lua_tostring(L, -1);
    if (msg == NULL) msg = "(error object is not a string)";
    l_message("progname", msg);
    lua_pop(L, 1);
  }
  return status;
}

int main()
{
    cout << "This is Lua!\n";
    
	int error = 0;
	lua_State *L = lua_open(); //open the virtual machine
	luaL_openlibs(L); //open the libraries you'll need
	
    register_native_functions(L);
    
    const char* app = LUA_APP; //get your Lua application
    
    error = luaL_loadbuffer(L, app, strlen(app), 0); //compile it
	if (error) {
        cout << "lua COMPILATION ERROR\n";
    }
     
    
    
    error = lua_pcall(L, 0, 0, 0);
    /* Other options (for them there is no need to call luaL_loadbuffer first) */
    //error = luaL_dostring(L, LUA_APP); 
    //error = luaL_dofile(L, "original_lua_programs/dmec_app.lua");
    
    if (error) {
        switch (error) {
            case LUA_ERRMEM:        
                cout << "Lua: Memory allocation ERROR\n";
                break;            
            case LUA_ERRRUN:
                cout << "Lua: RUNTIME ERROR\n";
                break;
            //case LUA_ERRERR:
            //    cout << "Lua: error while running the message handle\n";
            //    break;
            // case LUA_ERRGCMM:
            //    cout << "Lua: garbage collector metamethod error\n";
            //    break;    
            default:
                cout << "Lua: unknown error!\n";
                break;                
        }
        
        report(L, error);        
    }
    
	
    if (error) lua_pop(L, 1); //assert nothing's wrong
	
    lua_close(L); //close the virtual machine
	
    cout << "Bye\n";
    
    return 0;
}
