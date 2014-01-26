public class OBA_C_PME_Weavelet extends Weavelet {
    // ...
    public boolean affectMethod(IMClass clazz, IMMethod method, Coder coder) 
throws CompileException 
    {
        if (method.termed(
"match(Ldmec/Picture;Ldmec/Picture;)Ldmec/PictureMotionCounterpart;")) 
        {            
            IMMethodFrame frame = method.getMethodFrame();
            IMSlot[] arguments = frame.getMethodArguments();
            assert(arguments.length == 3);
            IMSlot thiz = arguments[0];
            IMSlot currentPicture = arguments[1];
            IMSlot referencePicture = arguments[2];

            String _thiz = 
                WeaveletUtility.mountArg("PictureMotionEstimator*", thiz);
            String _currentPicture = 
                WeaveletUtility.mountArg("MEC_Picture*", currentPicture);
            String _referencePicture = 
                WeaveletUtility.mountArg("MEC_Picture*", referencePicture);

            String _args = _thiz + ", " + ", " + _currentPicture + ", " + 
                _referencePicture;
            coder.addln(_thiz + "pme_match(" + _args + ");");
            coder.addln("return " + thiz + "->__pmc;");
             
            return true;
        }

        // ...


        return false;
    }
}
