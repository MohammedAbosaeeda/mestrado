/* THIS FILE IS AUTO GENERATED BY KESO! DON'T EDIT */

/* Class: dmec/PictureMotionEstimator */

#ifndef __c5_PictureMotionEstimator_H__
#define __c5_PictureMotionEstimator_H__ 1
#include <keso_types.h>

#define C5_PICTUREMOTIONESTIMATOR_ID ((class_id_t)5)
#define C5_PICTUREMOTIONESTIMATOR_OBJ(_obj_) ((c5_PictureMotionEstimator_t*)(_obj_))

/* class methods prototypes */
/* match(Ldmec/Picture;Ldmec/Picture;)Ldmec/PictureMotionCounterpart; */
object_t* c5_PictureMotionEstimator_m1_match(object_t* obj0,object_t* obj1,object_t* obj2);
#include <app_include/c_picture_motion_estimator.h>
/* <init>(III)V */
void c5_PictureMotionEstimator_m2__init_(object_t* obj0,jint i1,jint i2,jint i3);

/* object data */
typedef struct {
/* c5_PictureMotionEstimator */
/* c1_Object */
OBJECT_HEADER
/* c1_Object */
	void* __inner;
	void* __pmcWrapper;
/* c5_PictureMotionEstimator */
} c5_PictureMotionEstimator_t;

#endif
