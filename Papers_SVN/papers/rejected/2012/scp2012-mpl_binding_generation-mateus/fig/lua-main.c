int main()
{	
  lua_State* L = lua_open();
  luaL_openlibs(L);

  luaL_loadfile(L, "main.lua");
  lua_pcall(L, 0, 0, 0);

  lua_close(L);

  return 0;
}
