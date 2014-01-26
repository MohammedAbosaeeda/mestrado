public boolean affectMethod(IMClass clazz, 
  IMMethod method, 
  Coder coder) 
throws CompileException 
{
  // ...
  if (method.termed("m1(C)V")) {
    coder.addln("obj0->eposHWMediator->m1(c1);");
    return true;
  }
  // ...
}

