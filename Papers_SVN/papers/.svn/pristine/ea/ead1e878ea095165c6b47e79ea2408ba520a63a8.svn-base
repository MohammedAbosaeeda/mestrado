public boolean affectMethod(IMClass clazz, 
  IMMethod method, 
  Coder coder) 
throws CompileException 
{
  // ...
  if (method.termed("operation()V")) {
    coder.addln("enter();");
    coder.addln("HW_Mediator::operation();");
    coder.addln("leave();");
    return true;
  }
  // ...
}

