/* Author: Mateus Krepsky Ludwich */

package nanovm.dmec;

public class PictureMotionEstimator {
	
	public PictureMotionEstimator(int pictureWidth, int pictureHeight) {
		/* We do nothing here! It will be handle by the binding. */
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
	native public PictureMotionCounterpart match(Picture currentPicture, Picture referencePicture);

}
