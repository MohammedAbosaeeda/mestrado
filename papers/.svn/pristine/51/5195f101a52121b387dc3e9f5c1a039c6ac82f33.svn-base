public class DmecApp extends Task {
  public void launch() {
    DebugOut.println("DMEC APP is alive!");

    int pictureWidth = 176;
    int pictureHeight = 144;
    int maxReferencePictures = 1;

    PictureMotionEstimator pme;
    pme = new PictureMotionEstimator(pictureWidth, 
            pictureHeight, 
            maxReferencePictures);
    
    
    Picture currentPicture;
    currentPicture = 
        TestSupport.createPicture(pictureWidth,
                            pictureHeight);

    final int list0Size = 2;    
    Picture[] list0 = new Picture[list0Size];        
    for (int i = 0; i < list0Size; i++) {
   	  list0[i] = 
        TestSupport.createPicture(pictureWidth, 
                            pictureHeight);
    }
    
    PictureMotionCounterpart pmc;
    pmc = pme.match(currentPicture, list0);
        
    TestSupport.testPMC(pmc, 
        pictureWidth, 
        pictureHeight, 
        currentPicture, 
        list0);
  
    DebugOut.println("done: OK");
  }
}

