/* Author: Mateus Krepsky Ludwich - mateus@lisha.ufsc.br */
/* Don't forgot to register this Weavelet in BuilderOptions. */

package keso.compiler.kni;

import keso.compiler.*;
import keso.compiler.imcode.*;
import keso.compiler.backend.*;

public class UART_Weavelet extends Weavelet {

    public UART_Weavelet(BuilderOptions opts, ClassStore repository) {
        super(opts, repository);
        
        // Here the base directory seems to be src/classes/
        joinPoints = new String[] { "test/UART.*", "test/UART.<CLASS>" }; 
    }

    public boolean affectMethod(IMClass clazz, IMMethod method, Coder coder) throws CompileException {
        // System.out.println("\n AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA \n");
        
        coder.local_add("#include <epos_uart_c.h>");
        
        if (method.termed("get()C")) {
            // System.out.println("\n HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH \n");
            coder.addln("return EPOS_UART_get(((c4_UART_t *) obj0)->eposUART);"); /* It is not necessary to convert char to jchar (return of get) because jchar is a signed char already. */
            return true;
        }
        
        else if (method.termed("put(C)V")) {
            // System.out.println("\n EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE \n");
            coder.addln("EPOS_UART_put(((c4_UART_t *) obj0)->eposUART, c1);");
            return true;
        }
        
        else if (method.termed("<init>(IIIII)V")) {
            //System.out.println("\n WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW \n");
            //object_t* obj0,jint i1,jint i2,jint i3,jint i4,jint i5
            //
            
            coder.addln("((c4_UART_t *) obj0)->eposUART = new_EPOS_UART(i1, i2, i3, i4, i5);");
            return true;
        }
        
        return false;
    }
    
    public boolean addFields(IMClass clazz, Coder coder, StringBuffer raw_fields) throws  CompileException {
        //System.out.println("\n IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII \n");
        raw_fields.append("\tvoid * eposUART;\n");
		return true;
	}
    
    // This protect a method body from analayses and translation process.
    public boolean ignoreMethodBody(IMClass clazz, IMMethod method) throws CompileException {
        return true;
    }

}
