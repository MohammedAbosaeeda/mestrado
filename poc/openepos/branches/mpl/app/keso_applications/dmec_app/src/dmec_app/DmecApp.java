/* Author: Mateus Krepsky Ludwich. */

package dmec_app;

import java.util.ArrayList;
import java.util.List;

import keso.core.Task;
import test.DebugOut;


import dmec.PictureMotionEstimator;
import dmec.PictureMotionCounterpart;
import dmec.Picture;
import dmec.NativeTestSupport;

public class DmecApp extends Task {
    
	public void launch() {
        DebugOut.println("DMEC APP for KESO is alive!");
		
        int pictureWidth = 176;
        int pictureHeight = 144;
        
        PictureMotionEstimator pme;
        pme = new PictureMotionEstimator(pictureWidth, pictureHeight, 1);
        
        Picture currentPicture = NativeTestSupport.createPicture(pictureWidth, pictureHeight, 0);
        Picture referencePicture = NativeTestSupport.createPicture(pictureWidth, pictureHeight, 1);
        
        PictureMotionCounterpart pmc;        
        pmc = pme.match(currentPicture, referencePicture);
        
        NativeTestSupport.testPictureMotionCounterpart(pmc, pictureWidth, pictureHeight, currentPicture, referencePicture);
        
        DebugOut.println("done: OK");
    }

}

