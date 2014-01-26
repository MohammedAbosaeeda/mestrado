public class DmecApp extends Task {
  public void launch() {
    DebugOut.println("DMEC APP is alive!");
    int width = 1920; int height = 1088; int maxRefPic = 1;
    PictureMotionEstimator pme = new PictureMotionEstimator(width, height, maxRefPic);
            
    Picture currentPicture = TestSupport.createPicture(width, height);
    Picture[] list0 = new Picture[2];
    for (int i = 0; i < list0.length; i++) {
   	  list0[i] = TestSupport.createPicture(width, height);
    }
    
    PictureMotionCounterpart pmc = pme.match(currentPicture, list0);    
    
    TestSupport.testPMC(pmc, width, height, currentPicture, list0);
    DebugOut.println("done: OK");
  }
}

