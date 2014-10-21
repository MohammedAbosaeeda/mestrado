/**(c)

  Copyright (C) 2007 Christian Wawersich 

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi@cs.fau.de for more info.

  (c)**/

package keso.compiler.kni;

import keso.compiler.*;
import keso.compiler.imcode.*;
import keso.compiler.backend.*;
import keso.classfile.datatypes.*;

import java.util.Hashtable;
import java.util.Enumeration;

public class TimeStampWeavelet extends Weavelet {

	private Hashtable symbols = new Hashtable();

	public TimeStampWeavelet(BuilderOptions opts, ClassStore repository) {
		super(opts, repository);
		joinPoints = new String[] {
			"keso/core/TimeStamp.readCounter()J",
			"keso/core/TimeStamp.toMicros(J)J",
			"benchmark/Timer.init()V",
			"benchmark/Timer.startPoint(Ljava/lang/String;)V",
			"benchmark/Timer.endPoint(Ljava/lang/String;)V",
			"benchmark/Timer.endPointSum(Ljava/lang/String;)V",
			"benchmark/Timer.endPointAverage(Ljava/lang/String;I)V",
			"benchmark/Timer.report()V",
		};
	}

	public void require(int domainID, String className, String methodName) {

	}

	public IMNode affectIMInvoke(IMInvoke self, IMMethod method, IMMethod callee,
			IMNode obj, IMNode args[]) throws CompileException {

		/* Hier werden nur die Parameter fuer Timer.report() eingesammelt */

		if (callee.termed("endPoint(Ljava/lang/String;)V")) {
			String symbol = assert_symbol(args[0],"endPoint symbol");
			symbols.put(symbol,symbol);
		}

		if (callee.termed("endPointSum(Ljava/lang/String;)V")) {
			String symbol = assert_symbol(args[0],"endPointSum symbol");
			symbols.put(symbol,symbol);
		}

		if (callee.termed("endPointAverage(Ljava/lang/String;I)V")) {
			String symbol = assert_symbol(args[0],"endPointAverage symbol");
			IMIConstant runs = assert_iconst(args[1],"endPointAverage runs");
			symbols.put(symbol,runs);
		}

		return self;
	}

	public boolean affectMethod(IMClass clazz, IMMethod method, Coder coder) throws CompileException {
		if (method.termed("toMicros(J)J")) {
			coder.addln("/* add -DKESO_TICKS_MICRO=value to your makefile! */");
			coder.add("return ((");
			method.getArg(BCBasicDatatype.LONG, 0).translate(coder);	
			coder.addln("/KESO_TICKS_MICRO)<<1);");
			return true;
		} 
		if (method.termed("readCounter()J")) {
			if (opts.hasRDTSC()) {
				coder.addln("unsigned long long d;");
				codeRDTSC(coder,"d","");
				coder.addln("return (jlong)(d>>1);");
				return true;
			}
		}
		return false;	
	}

	public boolean affectInvokeStatic(IMInvoke node, IMMethod caller, IMMethod callee,
			IMNode args[], Coder coder) throws CompileException {
		if (callee.termed("init()V")) {
			if (opts.hasRDTSC()) {
				if (!opts.hasOption("_X_no_error_correction")) {
					coder.global_header_add("extern unsigned long keso_dp;\n");
					coder.global_add("unsigned long keso_dp=0;\n");
			
					coder.global_header_add("extern unsigned long long keso_dp_tmp;\n");
					coder.global_add("unsigned long long keso_dp_tmp;\n");

					coder.addln("{");
					coder.addln("\tint i;");
					coder.addln("\tunsigned long min=0xffffffff;");
					coder.addln("\tfor (i=0;i<4;i++) {");
					coder.addln("\tunsigned long long d;");
					codeInstrFlush(coder);
					codeRDTSC(coder,"keso_dp_tmp","");
					codeInstrFlush(coder);
					codeInstrFlush(coder);
					codeRDTSC(coder,"d","");
					coder.addln("\tmin = min < (unsigned long)(d-keso_dp_tmp) ? min : (unsigned long)(d-keso_dp_tmp);");
					codeInstrFlush(coder);
					coder.addln("\t}");
					coder.addln("\tkeso_dp=min;");
					coder.add("}");
				}
			}
			return true;
		}
		if (callee.termed("startPoint(Ljava/lang/String;)V")) {
			String symbol = assert_symbol(args[0],"startPoint symbol");
			if (opts.hasRDTSC()) {

				coder.global_header_add("extern unsigned long long keso_mp_tmp_");
				coder.global_header_add(symbol);
				coder.global_header_add(";\n");
				coder.global_header_add("extern unsigned long long keso_mp_");
				coder.global_header_add(symbol);
				coder.global_header_add(";\n");

				coder.global_add("unsigned long long keso_mp_tmp_");
				coder.global_add(symbol);
				coder.global_add(";\n");
				coder.global_add("unsigned long long keso_mp_");
				coder.global_add(symbol);
				coder.global_add(";\n");

				codeInstrFlush(coder);
				codeRDTSC(coder,"keso_mp_tmp_",symbol);
				codeInstrFlush(coder);

				return true;
			}
			/* remove call not in use */
			return true;
		}
		if (callee.termed("endPoint(Ljava/lang/String;)V")) {
			String symbol = assert_symbol(args[0],"endPoint symbol");
			if (opts.hasRDTSC()) {
				coder.addln("{");
			        coder.addln("\tunsigned long long d;");
				codeInstrFlush(coder);
				codeRDTSC(coder,"d","");
				coder.add("\tkeso_mp_");
				coder.add(symbol);
				coder.add("=(d-keso_mp_tmp_");
				coder.add(symbol);
				if (opts.hasOption("_X_no_error_correction")) coder.addln(");");
				else coder.addln(")-keso_dp;");
				codeInstrFlush(coder);
				coder.add("}");
				return true;
			}
			/* remove call not in use */
			return true;
		}
		if (callee.termed("endPointSum(Ljava/lang/String;)V") || 
				callee.termed("endPointAverage(Ljava/lang/String;I)V")) {
			String symbol = assert_symbol(args[0],"endPoint symbol");
			if (opts.hasRDTSC()) {
				coder.addln("{");
			        coder.addln("\tunsigned long long d;");
				codeInstrFlush(coder);
				codeRDTSC(coder,"d","");
				coder.add("\tkeso_mp_");
				coder.add(symbol);
				coder.add("=keso_mp_");
				coder.add(symbol);
				coder.add("+(d-keso_mp_tmp_");
				coder.add(symbol);
				if (opts.hasOption("_X_no_error_correction")) coder.addln(");");
				else coder.addln(")-keso_dp;");
				codeInstrFlush(coder);
				coder.add("}");
				return true;
			}
			/* remove call not in use */
			return true;
		}
		if (callee.termed("report()V")) {
			if (opts.hasRDTSC()) {
				coder.local_add("#include <stdio.h>\n");
				coder.addln("{");
				coder.addln("\tprintf(\"\\n\");");
				Enumeration e = symbols.keys();
				while (e.hasMoreElements()) {
					String sym = (String)e.nextElement();
					java.lang.Object obj = symbols.get(sym);
					coder.add("\tprintf(\"");
					coder.add(sym);
					if (obj instanceof IMIConstant) {
						IMIConstant iconst = (IMIConstant) obj;
						coder.add(" (average)\\t= %llu cpu cycles\\n\"");
						coder.add(",(keso_mp_");
						coder.add(sym);
						coder.add("/");
						coder.add(iconst.getIntValue());
						coder.add(")");
					} else {
						coder.add("\\t= %llu cpu cycles");
						coder.add("\\n\",keso_mp_");
						coder.add(sym);
					}
					coder.addln(");");
				}
				if (!opts.hasOption("_X_no_error_correction")) 
					coder.addln("\tprintf(\"error correction value = %lu\\n\",keso_dp);");
				coder.addln("}");
				return true;
			}
			/* remove call not in use */
			return true;
		}
		return false;
	}

	public boolean ignoreMethodBody(IMClass clazz, IMMethod method) throws CompileException {
		return true;
	}

	private void codeRDTSC(Coder coder, String prefix, String symbol) throws CompileException {
		if (opts.hasOption("_X_rdtsc_IA64")) {
			/* We cannot use "=A", since this would use %rax on x86_64 */
			coder.add("\t{register unsigned long _eax,_edx;");
			coder.add("__asm__ __volatile__ (\"rdtsc\" : \"=a\" (_eax), \"=d\" (_edx));");
			coder.add(prefix);
			coder.add(symbol);
			coder.addln("=(_edx<<32)|_eax;}");
		} else {
			coder.add("\t__asm__ __volatile__ (\"rdtsc\" : \"=A\" (");
			coder.add(prefix);
			coder.add(symbol);
			coder.addln("));");
		}
	}

	private void codeInstrFlush(Coder coder) throws CompileException {
		coder.addln("\t__asm__ __volatile__ (\"xorl %eax, %eax\");");
		coder.add("\t__asm__ __volatile__ (\"cpuid\"");
		coder.addln(" : : : \"%eax\", \"%edx\", \"%ebx\", \"%ecx\", \"%edi\", \"%esi\" );");
	}
}
