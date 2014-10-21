/* Author: Mateus Krepsky Ludwich - mateus@lisha.ufsc.br */

package keso.compiler.kni;

import keso.compiler.*;
import keso.compiler.imcode.*;
import keso.compiler.backend.*;

public class SmallVectorWeavelet extends Weavelet {

    // Apparently is necessary overwrite the Weavelet constructor 
    public SmallVectorWeavelet(BuilderOptions opts, ClassStore repository) {
        super(opts, repository);
        
        // Here the base directory seems to be src/classes/
        joinPoints = new String[] { "test/SmallVector.*" }; 
    }

    /*
    public boolean affectInvokeStatic(IMInvoke node, IMMethod caller, IMMethod callee, IMNode args[], Coder coder) throws CompileException {
        return true;
    }
    */
    
    public boolean affectMethod(IMClass clazz, IMMethod method, Coder coder) throws CompileException {
        if (method.termed("sum([I[I[I)V")) {
            coder.local_add("#include \"int_array.h\"\n");
            coder.local_add("extern SmallVector_sum(int * destination, int * sourceA, int * sourceB, int length);");
            
            coder.addln("jint arrayLength;");
            coder.addln("jint * destination;");
            coder.addln("jint * sourceA;");
            coder.addln("jint * sourceB;");
            coder.addln("\n");
            coder.addln("arrayLength = ((array_t*) obj1)->size;");
            coder.addln("destination = ((int_array_t*) obj0)->data;");
            coder.addln("sourceA = ((int_array_t*) obj1)->data;");
            coder.addln("sourceB = ((int_array_t*) obj2)->data;");
            coder.addln("SmallVector_sum((int *) destination, (int *) sourceA, (int *) sourceB, (int) arrayLength);");
            
            return true;
        }
        
        return false;
	}
    
    // This protect a method body from analayses and translation process.
    public boolean ignoreMethodBody(IMClass clazz, IMMethod method) throws CompileException {
		return true;
	}

}