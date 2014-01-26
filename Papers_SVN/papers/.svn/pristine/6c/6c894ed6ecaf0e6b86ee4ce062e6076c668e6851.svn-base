void native_picture_motion_estimator_invoke(u08_t mref) 
{
  // ...
  else if (mref == NATIVE_METHOD_PME_MATCH) {
    NativePicture* referencePicture = 
      NativePictureMap::getInstance()->get(stack_pop());
    NativePicture* currentPicture = 
      NativePictureMap::getInstance()->get(stack_pop());
    NativePictureMotionEstimator* npme = 
      NativePictureMotionEstimatorMap::getInstance()->get(stack_pop());
        
    npme->match(currentPicture, referencePicture);        
  }
  else {
      error(ERROR_NATIVE_UNKNOWN_METHOD);
  }
}

NativePictureMotionCounterpart* match(NativePicture* currentPicture, 
NativePicture* referencePicture)
{
  __nativePMC->set_inner(
    __inner->match(currentPicture->inner(), referencePicture->inner()));
  return __nativePMC;
}

