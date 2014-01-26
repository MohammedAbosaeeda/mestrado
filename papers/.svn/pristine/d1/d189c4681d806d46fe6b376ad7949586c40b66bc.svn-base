int pme_native_match(lua_State* L)
{
  PictureMotionEstimator* pme = 
    static_cast<PictureMotionEstimator*>(lua_touserdata(L, 1));

  MEC_Picture* currentPicture = 
    static_cast<MEC_Picture*>(lua_touserdata(L, 2));
  
  MEC_Picture* referencePicture = 
    static_cast<MEC_Picture*>(lua_touserdata(L, 3));

  PictureMotionCounterpart* pmc;
  pmc = pme->match(currentPicture, referencePicture);

  lua_pushlightuserdata(L, static_cast<void*>(pmc));

  return 1;
}
