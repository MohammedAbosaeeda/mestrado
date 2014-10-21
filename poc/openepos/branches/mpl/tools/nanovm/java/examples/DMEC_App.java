/* Author: Mateus Krepsky Ludwich. */

import nanovm.dmec.PictureMotionEstimator;
import nanovm.dmec.PictureMotionCounterpart;
import nanovm.dmec.Picture;
import nanovm.dmec.TestSupport;

public class DMEC_App {
    
	public static void main(String[] args) {
        System.out.println("DMEC APP for NanoVM is alive!");
		
        int pictureWidth = 176;
        int pictureHeight = 144;
        
        PictureMotionEstimator pme;
        pme = new PictureMotionEstimator(pictureWidth, pictureHeight);
        
        Picture currentPicture = TestSupport.createPicture(pictureWidth, pictureHeight, 0);
        Picture referencePicture = TestSupport.createPicture(pictureWidth, pictureHeight, 1);
        
        PictureMotionCounterpart pmc;        
        pmc = pme.match(currentPicture, referencePicture);
        
        TestSupport.testPictureMotionCounterpart(pmc, pictureWidth, pictureHeight, currentPicture, referencePicture);
        
        System.out.println("done: OK");
    }

}
