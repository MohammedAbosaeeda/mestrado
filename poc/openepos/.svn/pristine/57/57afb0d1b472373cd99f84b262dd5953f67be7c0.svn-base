#define LINUX 0

#if LINUX
#include "../../dmec/include/picture_motion_estimator_coordinator.h"
#include "../../dmec/include/test_support.h"
#include "../../dmec/include/md5_utility.h"

#else
#include "../include/picture_motion_estimator_coordinator.h"
#include "../include/test_support.h"
#include "../include/md5_utility.h"

#endif

#if LINUX
extern "C" 
{
    #include <lua.h>
    #include <lauxlib.h>
    #include <lualib.h>
}

#else
#include <lua/lua.h>
#include <lua/lauxlib.h>
#include <lua/lualib.h>

#endif


/* new_native_pme(width, height, maxRefPic) */
int new_native_pme(lua_State* L)
{
    // cout << "new_native_pme\n";
    
    unsigned int width = lua_tonumber(L, 1);
    unsigned int height = lua_tonumber(L, 2);
    unsigned int maxRefPic = 1;
    
    // printf("w: %u, h: %u, max: %u\n", width, height, maxRefPic); 
    
    // pme_getInstance(width, height, maxRefPic); // while compiling with g++, C to C++ wrappers are not necessary
    void* inner = PictureMotionEstimator::getInstance(width, height, maxRefPic);
    // printf("created pme: %x\n", (unsigned int) inner); 
    lua_pushlightuserdata(L, inner);
    
    return 1;
}


/* pme_native_match(self.inner, currentPicture.inner, referencePicture.inner) */
int pme_native_match(lua_State* L)
{
    //cout << "pme_native_match\n";
    
    PictureMotionEstimator* pme = static_cast<PictureMotionEstimator*>(lua_touserdata(L, 1));
    MEC_Picture* currentPicture = static_cast<MEC_Picture*>(lua_touserdata(L, 2));
    MEC_Picture* referencePicture = static_cast<MEC_Picture*>(lua_touserdata(L, 3));
    
    // printf("using pme: %x\n", (unsigned int) pme); 
    // printf("using currentPicture: %x\n", (unsigned int) currentPicture);
    // printf("using referencePicture: %x\n", (unsigned int) referencePicture);
    
    PictureMotionCounterpart* pmc;
    pmc = pme->match(currentPicture, referencePicture);
    
    // printf("PMC created by match: %x\n", (unsigned int) pmc);
    
    lua_pushlightuserdata(L, static_cast<void*>(pmc));
    
    return 1;
}


/* createPicture(width, height, dataSet) */
int NativeTestSupport_createPicture(lua_State* L)
{
    // cout << "NativeTestSupport_createPicture\n";
    
    unsigned int width = lua_tonumber(L, 1);
    unsigned int height = lua_tonumber(L, 2);
    unsigned int dataSet = lua_tonumber(L, 3);

    // cout << "...width: " << width << " height: " << height << " dataSet " << dataSet << "\n";

    MEC_Picture* pic = TestSupport::createPicture(width, height, dataSet);
    
    // printf("created picture: %x\n", (unsigned int) pic); 
    // cout << "...created picture: ";
    // MD5_Utility::printSum(SerializableMEC_Picture::serialize(pic));
    // cout << "\n";
    
    lua_pushlightuserdata(L, pic);
    
    return 1;
}


/* testPictureMotionCounterpart(pmc, width, height, currentPicture, referencePicture) */
int NativeTestSupport_testPictureMotionCounterpart(lua_State* L)
{
    // cout << "NativeTestSupport_testPictureMotionCounterpart\n";
    
    PictureMotionCounterpart* pmc = static_cast<PictureMotionCounterpart*>(lua_touserdata(L, 1));
    unsigned int width = lua_tonumber(L, 2);
    unsigned int height = lua_tonumber(L, 3);
    MEC_Picture* currentPicture = static_cast<MEC_Picture*>(lua_touserdata(L, 4));
    MEC_Picture* referencePicture = static_cast<MEC_Picture*>(lua_touserdata(L, 5));
   
   
    /*
    printf("...PMC recovered by testPMC: %x\n", (unsigned int) pmc);
    cout << "...current picture: ";
    MD5_Utility::printSum(SerializableMEC_Picture::serialize(currentPicture));
    cout << "\n";
    cout << "...reference picture: ";
    MD5_Utility::printSum(SerializableMEC_Picture::serialize(referencePicture));
    cout << "\n";
    */
   
    TestSupport::testPictureMotionCounterpart(pmc, width, height, currentPicture, referencePicture);
   
    return 0;
}


void register_native_functions(lua_State* L)
{
    lua_register(L, "new_native_pme", new_native_pme);
    lua_register(L, "NativeTestSupport_createPicture", NativeTestSupport_createPicture);
    lua_register(L, "pme_native_match", pme_native_match);
    lua_register(L, "NativeTestSupport_testPictureMotionCounterpart", NativeTestSupport_testPictureMotionCounterpart);
}
