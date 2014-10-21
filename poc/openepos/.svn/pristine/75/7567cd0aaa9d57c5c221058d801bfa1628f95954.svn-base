#include "types.h"

/*
extern "C" 
{
*/ 
    #include "config.h"
    // #include "unix/native.h"
    #include "native.h"
    #include "error.h"
    #include "nvmtypes.h" // nvm_stack_t
    
    #include "native_dmec.h"

    // #include <stdio.h>
/*
}
*/ 

// #include <cassert>

#include "stack.h"
#include "heap.h"

/*
extern "C"
{
*/ 
    /* If I include stack.h g++ claims about "nvmfile.h:53: error: ISO C++ forbids zero-size array ‘class_hdr’" */
    // extern unsigned int stack_pop(void); // I don't know why but using nvm_stack_t stack_pop(void) does not work.
    // extern nvm_int_t stack_pop_int(void);
    // extern void stack_push(unsigned int);
    // extern void print_stack();
    
    // unsigned int heap_alloc(bool fieldref, unsigned int size);
/*
}
*/ 

#include "native_mediator.hh"

// XXX
#if 0
/// TODO remove this and use includes...
class Picture
{
};

class PictureMotionCounterpart
{
};

class PictureMotionEstimator
{
public:
    PictureMotionCounterpart* match(Picture* currentPicture, Picture* referencePicture) { return 0; }
    
    static PictureMotionEstimator * getInstance(unsigned int pictureWidth,
    		unsigned int pictureHeight,
    		unsigned int max_reference_pictures) { return 0; }
};


class TestSupport
{
public:    
    static Picture* createPicture(unsigned int width, unsigned int height, unsigned int dataSet) 
    { return 0; }
    
    static void testPictureMotionCounterpart(PictureMotionCounterpart* pmc,
			unsigned int pictureWidth, unsigned int pictureHeight,
			Picture* currentPicture,
			Picture* referencePicture) {}
};

///
#endif
// XXX

// #include "../../../../app/dmec/include/traits.h"
#include "../../app/dmec/include/traits.h"
// #include "../../../../app/dmec/include/test_support.h"
#include "../../app/dmec/include/test_support.h"

extern System::OStream cout;

#define ENABLE_DEBUG_OBJECTS_TRACKER 0

#if ENABLE_DEBUG_OBJECTS_TRACKER // debug
/*! Auxiliary (just for debug and trace support) ---------------------------- */
class ObjectsTracker
{
    //! mplr stands for MPL Reference - Maganed Programming Language Reference
    unsigned int __mplr_pictureMotionEstimator;
    unsigned int __mplr_currentPicture;
    unsigned int __mplr_referencePicture;
    unsigned int __mplr_pictureMotionCounterpart;
    
public:
    void setMPLR_Picture(unsigned int mplrPicture)
    {
        // Assumes the first picture registered is the current picture.
        if (! __seted_mplr_curretPicture)
        {
            __seted_mplr_curretPicture = true;
            __mplr_currentPicture = mplrPicture;
        }
        else
        {
            __mplr_referencePicture = mplrPicture;
        }
    }
    
    
    void setMPLR_PictureMotionEstimator(unsigned int mplr)
    {
        __mplr_pictureMotionEstimator = mplr;
    }


    void setMPLR_PictureMotionCounterpart(unsigned int mplr)
    {
        __mplr_pictureMotionCounterpart = mplr;
    }


    void printMPLR_References()
    {
        #if 0
        printf("pme: %u, cp: %u, rp: %u, pmc: %u (0 means not setted)\n",
                __mplr_pictureMotionEstimator,
                __mplr_currentPicture,
                __mplr_referencePicture,
                __mplr_pictureMotionCounterpart);
        #endif
    }
    
    
    ObjectsTracker()
    {
        __seted_mplr_curretPicture = false;
        __mplr_pictureMotionEstimator = 0;
        __mplr_currentPicture = 0;
        __mplr_referencePicture = 0;
        __mplr_pictureMotionCounterpart = 0;
    }
    
private:
    bool __seted_mplr_curretPicture;
};

ObjectsTracker __objectsTracker = ObjectsTracker();
#endif


/* -------------------------------------------------------------------------- */


/*! Picture ----------------------------------------------------------------- */
class NativePicture;

class NativePictureMap
{
struct Pair
{
    unsigned int k;
    NativePicture* v;
};

public:
    static NativePictureMap* getInstance()
    {
        if (! __instance)
        {
            __instance = new NativePictureMap();
        }

        return __instance;
    }

public:

    void put(unsigned int key, NativePicture* value)
    {
        __theMap[__theIndex].k = key;
        __theMap[__theIndex].v = value;
        __theIndex++;
    }

    // Linear complexity O(n)
    NativePicture* get(unsigned int key)
    {
        NativePicture* np = 0;
        for (int i = 0; i < 2; i++)
        {
            if (key == __theMap[i].k)
            {
                np = __theMap[i].v;
            }
        }
        
        // assert(np);
        return np;
    }


private:
    Pair __theMap[2]; /* As long I know that there are at most 2 pictures. */ 
    unsigned int __theIndex;

private:
    NativePictureMap() 
    {
        __theIndex = 0;
    }

private:
    static NativePictureMap* __instance;
    
};

NativePictureMap* NativePictureMap::__instance = 0;

/*! NOTE: This DMEC version still uses MEC_Picture on PictureMotionEstimator
 * (Coordinator) interface. So using MEC_Picture instead of Picture. */
class NativePicture : public ObjectBasedNativeMediator<MEC_Picture, nvm_stack_t>
{
public:
    NativePicture(nvm_stack_t mplReference, MEC_Picture* pic)
    {
        #if 0 // debug
        printf("NativePicture::mplReference: %u, this: %x\n", mplReference, (unsigned int) this);
        #endif
        
        #if ENABLE_DEBUG_OBJECTS_TRACKER // debug
        __objectsTracker.setMPLR_Picture(mplReference);
        #endif
        
        __inner = pic;
        NativePictureMap::getInstance()->put(mplReference, this);
    }
};


void __native_picture_new()
{
    unsigned int id = heap_alloc(false, 1);
    unsigned int val = NVM_TYPE_HEAP | id;
    
    #if 0 // debug
    printf("==>NativeDMEC::native_picture_new\n");
    print_stack();
    printf("...going to create a Picture id: %u, val:%u\n", id, val);
    printf("...sizeof(nvm_stack_t): %u, \n", sizeof(nvm_stack_t));
    #endif
    
    // createPicture(int width, int height, int dataSet);
    /* At this momment the stack has 
     * s[top-2] == 176 // width
     * s[top-1] == 144 // height
     * s[top]   == 0   // dataSet
     * 
     * but should have after the execution of this method:
     *
     * s[top-4] == val // picture's reference 
     * s[top-3] == val // picture's reference
     * s[top-2] == 176 // width
     * s[top-1] == 144 // height
     * s[top]   == 0   // dataSet 
     * 
     * */
    
    unsigned int dataSet = stack_pop();
    unsigned int height  = stack_pop(); 
    unsigned int width   = stack_pop();
    
    stack_push(val);
    stack_push(val); /// dup
    
    stack_push(width);
    stack_push(height);
    stack_push(dataSet);
    
    #if 0 // debug
    print_stack();
    #endif
}



void __native_picture_invoke(u08_t mref)
{
    #if 0 // debug
    printf("==>NativeDMEC::native_picture_invoke\n");
    #endif
    
    if (mref == NATIVE_METHOD_INIT_PICTURE) {
        #if 0 // debug
        printf("\t...going to call new Picture\n");
        print_stack();
        #endif

        // picID/this, width, height, dataSet  
        unsigned int dataSet = stack_pop_int();
        unsigned int height = stack_pop_int();
        unsigned int width = stack_pop_int();
        unsigned int java_id_picture = stack_pop();
        
        #if 0 // debug
        printf("dataSet: %u\n", dataSet);
        printf("height: %u\n", height);
        printf("width: %u\n", width);
        printf("java_id_picture: %u\n", java_id_picture);
        #endif
        
        #if 0 // debug
        assert(height == 144);
        assert(width == 176);
        #endif
        
        /*! NOTE: This DMEC version still uses MEC_Picture on PictureMotionEstimator
         * (Coordinator) interface. So using MEC_Picture instead of Picture. */
        MEC_Picture* picture = TestSupport::createPicture(width, height, dataSet);
        
        new NativePicture(java_id_picture, picture);
    }
    else {
        error(ERROR_NATIVE_UNKNOWN_METHOD);
    }

}

/* -------------------------------------------------------------------------- */


/*! PictureMotionCounterpart ------------------------------------------------ */
class NativePictureMotionCounterpart;

class NativePictureMotionCounterpartMap
{
public:
    // Singleton access
    static NativePictureMotionCounterpartMap* getInstance()
    {
        if (! __instance)
        {
            __instance = new NativePictureMotionCounterpartMap();
        }

        return __instance;
    }

public:
    // General Methods
    void put(unsigned int key, NativePictureMotionCounterpart* value)
    {
        __theMap = value;
    }

    
    NativePictureMotionCounterpart* get(unsigned int key)
    {
        return __theMap;
    }


private:
    // Instance variables
    NativePictureMotionCounterpart* __theMap; /* As long I know 
                                                PictureMotionCounterpart is a 
                                                singleton there is no need for 
                                                a real mapping here.
                                            */ 
private:
    NativePictureMotionCounterpartMap() {} // Private Constructor

private:
    static NativePictureMotionCounterpartMap* __instance; // Singleton
    
};

NativePictureMotionCounterpartMap* NativePictureMotionCounterpartMap::__instance = 0;


class NativePictureMotionCounterpart : public ObjectBasedNativeMediator<PictureMotionCounterpart, nvm_stack_t>
{
public:
    /*! Normally a native object wrapper does not change its "inner" object, 
     * but NativePictureMotionCounterpart is special...
     */
    void set_inner(PictureMotionCounterpart* pmc)
    {
        #if 0 // debug
        printf("NativePictureMotionCounterpart::set_inner, pmc: %x\n", (unsigned int) pmc);
        #endif
        
        __inner = pmc;
    }
    
    /*! As PictureMotionCounterpart is a singleton and all...
     * not creating a Java object for it (i.e. it does not have e real mpl reference)
     * 
     * Maybe change it latter in order to be more conformant to EBG and 
     * automatic generation, but for now it is OK.
     */
    NativePictureMotionCounterpart()
    {
        NativePictureMotionCounterpartMap::getInstance()->put(0, this);
    }
    
};

void native_picture_motion_counterpart_invoke(u08_t mref)
{
    /// TODO
    #if 0 // debug
    printf("NativeDMEC::native_picture_motion_counterpart_invoke\n");
    #endif
    
    if (mref == NATIVE_METHOD_INIT_PMC) {
        #if 0 // debug
        printf("\t...going to call new PictureMotionCounterpart\n");        
        #endif
    }
    else {
        error(ERROR_NATIVE_UNKNOWN_METHOD);
    }
}

/* -------------------------------------------------------------------------- */


/*! PictureMotionEstimator -------------------------------------------------- */
class NativePictureMotionEstimator;

class NativePictureMotionEstimatorMap
{
public:
    static NativePictureMotionEstimatorMap* getInstance()
    {
        if (! __instance)
        {
            __instance = new NativePictureMotionEstimatorMap();
        }

        return __instance;
    }

public:

    void put(unsigned int key, NativePictureMotionEstimator* value)
    {
        __theMap = value;
    }

    
    NativePictureMotionEstimator* get(unsigned int key)
    {
        return __theMap;
    }


private:
    NativePictureMotionEstimator* __theMap; /* As long I know 
                                                PictureMotionEstimator is a 
                                                singleton there is no need for 
                                                a real mapping here.
                                            */ 

private:
    NativePictureMotionEstimatorMap()
    {
        //__theMap = new std::map<MPL_Reference, NativeReference>();
    }

private:
    static NativePictureMotionEstimatorMap* __instance;
    
};

NativePictureMotionEstimatorMap* NativePictureMotionEstimatorMap::__instance = 0;

class NativePictureMotionEstimator : public ObjectBasedNativeMediator<PictureMotionEstimator, nvm_stack_t>
{
public:
    NativePictureMotionEstimator(nvm_stack_t mplReference, unsigned int pictureWidth, unsigned int pictureHeight)
    {
        #if 0 // debug
        printf("NativePictureMotionEstimator::mplReference: %u, this: %x\n", mplReference, (unsigned int) this);
        #endif
        
        #if ENABLE_DEBUG_OBJECTS_TRACKER // debug
        __objectsTracker.setMPLR_PictureMotionEstimator(mplReference);
        #endif
        
        __nativePMC = new NativePictureMotionCounterpart();
        __inner = PictureMotionEstimator::getInstance(pictureWidth, pictureHeight, 1);
        NativePictureMotionEstimatorMap::getInstance()->put(mplReference, this);
    }

    /* In the native PictureMotionEstimator, for performance reasons,
     * the match method does not creates a PictureMotionCounterpart (PMC) each
     * time it is invoked.
     * Instead of that, it returns a PMC which is a attribute of
     * PictureMotionEstimator.
     * 
     * This class and method follows the same principle.
     *
    */
    NativePictureMotionCounterpart* match(NativePicture* currentPicture, NativePicture* referencePicture)
    {
        __nativePMC->set_inner(__inner->match(currentPicture->inner(), referencePicture->inner()));
        return __nativePMC;
    }

private:
    NativePictureMotionCounterpart* __nativePMC;

};


void native_picture_motion_estimator_new()
{
    unsigned int id = heap_alloc(false, 1);
    unsigned int val = NVM_TYPE_HEAP | id;
    
    #if 0 // debug
    cout << "==>NativeDMEC::native_picture_motion_estimator_new\n";
    #endif
    #if 0 // debug
    printf("==>NativeDMEC::native_picture_motion_estimator_new\n");
    print_stack();
    printf("...going to create a PictureMotionEstimator id: %u, val:%u\n", id, val);
    printf("...sizeof(nvm_stack_t): %u, \n", sizeof(nvm_stack_t));
    #endif
    
    stack_push(val);
    
    #if 0 // debug
    print_stack();
    #endif
}


void native_picture_motion_estimator_invoke(u08_t mref)
{
    #if 0 // debug
    printf("==>NativeDMEC::native_picture_motion_estimator_invoke\n");
    #endif
    
    if (mref == NATIVE_METHOD_INIT_PME) {
        /*
        printf("\t...going to call new PictureMotionEstimator\n");
        print_stack();
        printf("...pop-0: %u\n", stack_pop_int());
        print_stack();
        printf("...pop-1: %u\n", stack_pop_int());
        print_stack();
        printf("...pop-2: %u\n", (unsigned int) stack_pop());
        print_stack();
        printf("...pop-3: %u\n", stack_pop());
        printf("...pop-4: %u\n", stack_pop());
        printf("...pop-5: %u\n", stack_pop());
        printf("...pop-6: %u\n", stack_pop());
        */
        
        #if 0 // debug
        print_stack();
        #endif
        
        unsigned int pictureHeight = stack_pop_int();
        unsigned int pictureWidth = stack_pop_int();
        unsigned int java_id_pme = stack_pop();
        
        #if 0 // debug
        assert(pictureHeight == 144);
        assert(pictureWidth == 176);
        assert(java_id_pme == 2147483649U);
        assert(sizeof(nvm_stack_t) == 4); // 32 bits
        #endif
        
        //printf("...java_id_pme: %u\n", java_id_pme);
        
        new NativePictureMotionEstimator(java_id_pme, pictureWidth, pictureHeight);
        
        #if 0 // debug
        print_stack();
        #endif
    }
    else if (mref == NATIVE_METHOD_PME_MATCH) {
        #if 0 // debug
        printf("\t...going to call PictureMotionEstimator::match\n");
        print_stack();
        #endif
        
        NativePicture* referencePicture = NativePictureMap::getInstance()->get(stack_pop());
        NativePicture* currentPicture = NativePictureMap::getInstance()->get(stack_pop());
        NativePictureMotionEstimator* npme = NativePictureMotionEstimatorMap::getInstance()->get(stack_pop());
        
        npme->match(currentPicture, referencePicture);
        
        #if 0 // debug
        print_stack();
        #endif
        
    }
    else {
        error(ERROR_NATIVE_UNKNOWN_METHOD);
    }

}

/* -------------------------------------------------------------------------- */


/*! TestSupport ------------------------------------------------------------- */
void native_test_support_invoke(u08_t mref)
{
    /// TODO
    #if 0 // debug
    printf("NativeDMEC::native_test_support_invoke\n");
    #endif
    
    if (mref == NATIVE_METHOD_INIT_TEST_SUPPORT) {
        #if 0 // debug
        printf("\t...going to call new TestSuport\n");
        #endif
    }
    else if (mref == NATIVE_METHOD_TS_CREATE_PICTURE) {
        #if 0 // debug
        cout << ("NativeDMEC::native_test_support_invoke\n\t...going to call createPicture\n");
        #endif        
        #if 0 // debug
        printf("\t...going to call createPicture\n");
        #endif
        
        __native_picture_new();
        __native_picture_invoke(NATIVE_METHOD_INIT_PICTURE);
    }
    else if (mref == NATIVE_METHOD_TS_TEST_PMC) {
        /* void testPMC(PictureMotionCounterpart pmc, int pw, int ph, Picture cp, Picture rp) */
        
        
        #if ENABLE_DEBUG_OBJECTS_TRACKER // debug
        printf("\t...going to call testPictureMotionCounterpart\n");
        print_stack();
        __objectsTracker.printMPLR_References();
        #endif
        
        /*! NOTE: This DMEC version still uses MEC_Picture on PictureMotionEstimator
         * (Coordinator) interface. So using MEC_Picture instead of Picture. */
        MEC_Picture* referencePicture  = NativePictureMap::getInstance()->get(stack_pop())->inner();
        MEC_Picture* currentPicture    = NativePictureMap::getInstance()->get(stack_pop())->inner();
        
        unsigned int pictureHeight = stack_pop();
        unsigned int pictureWidth  = stack_pop(); 
        PictureMotionCounterpart* pmc = NativePictureMotionCounterpartMap::getInstance()->get(0)->inner();
        
        // Picture* pic1 = DMEC_Mapper::getInstance()->getPicture(thizPictureRef1);
        // Picture* pic2 = DMEC_Mapper::getInstance()->getPicture(thizPictureRef2);
        
        #if 0 // debug
        // assert(__objectsTracker.getMPLR_PictureMotionCounterpart() == 0);
        assert(pictureWidth = 176);
        assert(pictureHeight = 144);
        // assert(__objectsTracker.getMPLR_currentPicture() == 2147483650);
        #endif
        
        /*! In theory should exist a NativeTestSupport, but that was "optimized".
         * Anyway this not influenciates on time measurement of match...
         */ 
        TestSupport::testPictureMotionCounterpart(pmc, 
            pictureWidth, pictureHeight, 
            currentPicture, referencePicture);
    }
    else {
        error(ERROR_NATIVE_UNKNOWN_METHOD);
    }
}

/* -------------------------------------------------------------------------- */
