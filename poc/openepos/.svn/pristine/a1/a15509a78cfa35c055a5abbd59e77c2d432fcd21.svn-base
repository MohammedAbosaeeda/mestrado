#ifdef COMPILEFORLINUX
#define LINUX 1
#else
#define LINUX 0
#endif


#if LINUX
extern "C" 
{
    #include <lua.h>
    #include <lauxlib.h>
    #include <lualib.h>
}
#endif

int native_sum(lua_State* L)
{
    int a = lua_tonumber(L, 1);
    int b = lua_tonumber(L, 2);
    
    lua_pushnumber(L, a + b);  /* push result */
    return 1;  /* number of results */
}


void register_native_functions(lua_State* L)
{
    lua_register(L, "native_sum", native_sum);
}
