public class MediatorWeavelet extends Weavelet {
  // ...
  public boolean affectMethod(IMClass clazz, 
    IMMethod method, 
    Coder coder) throws CompileException 
  {
    if (method.termed("operation01(II)I")) {       
      coder.addln("return operation(inner, a, b);");
      return true;            
    }
    // ...
    return false;
  }
}
