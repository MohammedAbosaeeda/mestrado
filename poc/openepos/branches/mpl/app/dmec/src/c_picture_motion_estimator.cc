/* C to C++ wrapper for PictureMotionEstimator class */
#include "../include/picture_motion_estimator_coordinator.h"

#if LINUX
#include <stdlib.h>
#else
#include <utility/ostream.h>
#endif

extern "C" 
{
    #include "../include/c_picture_motion_estimator.h"
}

void* pme_getInstance(unsigned int pictureWidth,
    		unsigned int pictureHeight,
    		unsigned int max_reference_pictures)
{
    return (void*) PictureMotionEstimator::getInstance(pictureWidth,
    		pictureHeight,
    		max_reference_pictures);
}


void* pme_match(void* thiz, void* currentPicture, void* referencePicture)
{
#if DUMMY_ME
	ASMV("nop"::);
	return 0;
#else
	// System::kout << "c2cpp match\n";
    
    PictureMotionCounterpart* pmc;
    pmc = static_cast<PictureMotionEstimator*>(thiz)->match(
        static_cast<MEC_Picture*>(currentPicture), 
        static_cast<MEC_Picture*>(referencePicture));
   
    return (void*) pmc;
#endif

}
