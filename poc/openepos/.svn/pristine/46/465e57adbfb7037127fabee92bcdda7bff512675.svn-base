/* Author: Mateus Krepsky Ludwich - mateus@lisha.ufsc.br */

package keso.compiler.kni;

import keso.compiler.*;
import keso.compiler.imcode.*;
import keso.compiler.backend.*;

public class ThreadWeavelet extends Weavelet {

    public ThreadWeavelet(BuilderOptions opts, ClassStore repository) {
        super(opts, repository);

        // Here the base directory seems to be src/classes/
        joinPoints = new String[] { "test/Thread.*","test/Thread.<CLASS>" };
    }

    public boolean affectMethod(IMClass clazz, IMMethod method, Coder coder) throws CompileException {
        if (method.termed("join()V")) {
            coder.addln("EPOS_Thread_join(obj0);");

            return true;
        }
        else if (method.termed("start()V")) {
            coder.addln("EPOS_Thread_resume(obj0);");

            return true;
        }
        else if (method.termed("sleep(J)V")) {
            coder.addln("EPOS_Alarm_delay(l0);");

            return true;
        }

        else if (method.termed("<init>()V")) {
            coder.local_add("int entryPointEPOS_Thread(object_t * obj0) {\n    c19_Thread_m4__entry(obj0);\n    return 0;\n}\n");
    
            coder.addln("((c19_Thread_t *) obj0)->eposThread = new_EPOS_Thread_OneArg(entryPointEPOS_Thread, obj0);");

            return true;
        }


        return false;
    }

    // This protect a method body from analysis and translation process.
    public boolean ignoreMethodBody(IMClass clazz, IMMethod method) throws CompileException {
        return true;
    }

    public boolean addFields(IMClass clazz, Coder coder, StringBuffer raw_fields) throws  CompileException {
        raw_fields.append("\tvoid * eposThread;\n");
		return true;
	}

}

