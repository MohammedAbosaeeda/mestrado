/* C to C++ wrapper for TestSupport class */
#include "../include/test_support.h"

#if LINUX
#include <stdlib.h>
#endif

extern "C" 
{
    #include "../include/c_test_support.h"
}

void* testSupport_createPicture(unsigned int width, unsigned int height, unsigned int dataSet)
{
    return (void*) TestSupport::createPicture(width, height, dataSet);
}


void testSupport_testPictureMotionCounterpart(void* pmc,
    unsigned int pictureWidth, unsigned int pictureHeight,
    void* currentPicture,
	void* referencePicture)
{
    TestSupport::testPictureMotionCounterpart(
        static_cast<PictureMotionCounterpart*>(pmc),
        pictureWidth,
        pictureHeight,
        static_cast<MEC_Picture*>(currentPicture),
        static_cast<MEC_Picture*>(referencePicture));
}

