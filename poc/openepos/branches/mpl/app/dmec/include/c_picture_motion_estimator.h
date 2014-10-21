/* C to C++ wrapper for PictureMotionEstimator class */

/* Retuns the singleton for PictureMotionEstimator */
void* pme_getInstance(unsigned int pictureWidth,
    		unsigned int pictureHeight,
    		unsigned int max_reference_pictures);
    		
    		
void* pme_match(void* thiz, void* currentPicture, void* referencePicture);

