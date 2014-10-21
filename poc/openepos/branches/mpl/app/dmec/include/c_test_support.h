/* C to C++ wrapper for TestSupport class */

void* testSupport_createPicture(unsigned int width, unsigned int height, unsigned int dataSet);

void testSupport_testPictureMotionCounterpart(void* pmc,
    unsigned int pictureWidth, unsigned int pictureHeight,
    void* currentPicture,
	void* referencePicture);
