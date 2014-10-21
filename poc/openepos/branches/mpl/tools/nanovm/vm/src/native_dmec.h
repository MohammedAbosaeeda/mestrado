/* Author: Mateus Krepsky Ludwich */

#ifndef NATIVE_DMEC_H
#define NATIVE_DMEC_H

/* PictureMotionEstimator */
void native_picture_motion_estimator_new();
void native_picture_motion_estimator_invoke(u08_t mref);

/* Picture */
// void native_picture_new(); // removed from the public interface
// void native_picture_invoke(u08_t mref); // removed from the public interface


/* PictureMotionCounterpart */
void native_picture_motion_counterpart_invoke(u08_t mref);

/* TestSupport */
void native_test_support_invoke(u08_t mref);


#endif /* NATIVE_DMEC_H */
