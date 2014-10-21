/**(c)

  Copyright (C) 2005 Christian Wawersich

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi@cs.fau.de for more info.

  (c)**/

package keso.compiler;

import java.util.Hashtable;
import java.util.Vector;
import java.io.File;
import java.io.FileReader;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.IOException;

import keso.classfile.datatypes.BCBasicDatatype;
import keso.compiler.kni.JoinPointChecker;

import keso.compiler.config.*;
import keso.compiler.config.parser.ConfigReader;
import keso.compiler.imcode.IMDomain;
import keso.compiler.imcode.IMGlobalHeap;
import keso.compiler.imcode.IMPortalService;

import keso.util.Debug;
import keso.util.DecoratedNames;

final public class BuilderOptions {

	public static final int DEFAULT_TARGET = -1;
	public static final int GENERIC_TARGET = 0;
	public static final int TRICORE_TARGET = 1;
	public static final int LINUX_TARGET   = 2;
	public static final int AVR_TARGET     = 3;
	public static final int H8300_TARGET   = 4;
	public static final int ARM_TARGET     = 5;
	public static final int LINUX_TCC_TARGET = 6;
	public static final int LINUX_JOSEK_TARGET = 7;
	public static final int EPOS_TARGET = 8;

	private static final String[] target_names = {
		"GENERIC",
		"TRICORE",
		"LINUX",
		"AVR",
		"H8300",
		"ARM",
		"LINUX_TCC",
		"LINUX_JOSEK",
		"EPOS",
	};

	private static final String[] type_size_index = {
		"void",
		"jbyte", "jchar",
		"jshort",
		"jint", "jfloat",
		"jlong", "jdouble",
		"object_t*",
	};

	private static final int[] type_size_value = new int [] {
		0,
		1, 1,
		2,
		4, 4,
		8, 8,
		-1,
	};

	public static final String OBJ_T 	= "object_t*";
	public static final String VOID_T 	= "void";
	public static final String BOOLEAN_T  	= "jbyte";
	public static final String BYTE_T  	= "jbyte";
	public static final String CHAR_T  	= "jchar";
	public static final String SHORT_T  	= "jshort";
	public static String INT_T   	= "jint";
	public static String LONG_T  	= "jlong";
	public static final String FLOAT_T 	= "jfloat";
	public static String DOUBLE_T 	= "jdouble";

	private String sourcepath   = ".";
	private String outputpath   = "./tmp";
	private String[] modulpaths = null;
	private String kesosrcpath  = ".";
	private String classtree    = null;
	private String jdk_classpath = "";
	private String override_cc  = null;
	private String[] allModules;

	private WorldDefinition  worldDef = null;
	private SystemDefinition systemDef = null;
	private IMGlobalHeap	 global_heap = null;
	private Vector resources;
	private Vector alarms;
	private Vector isrs;


	private int target_arch_opt = GENERIC_TARGET;
	private int target_arch = GENERIC_TARGET;

	private boolean smartFileUpdate = true;
	private boolean quite	  = false;
	private boolean verbose   = false;
	private boolean very_verbose  = false;
	private boolean debug	  = false;
	private boolean dump_bc	  = false;
	private boolean prune_CT  = true;
	private boolean buildimage= true;
	private boolean dbg_syms  = true;
	private boolean create_CFG = false;
	private boolean fast_method_alias = false;
	private boolean precompile = false;
	private boolean translate = false;

	private boolean generic_exceptions  = true;
	private boolean pedantic = false;

	/*
	 * default option to tune the method in-line operation
	 */
	private boolean inline_methods      = true;
	private boolean inline_rvalue       = true;
	private int	inline_costs 	    = 80;
	private int	inline_costs_single = 1000;

	private Hashtable extras = new Hashtable();
	private Hashtable verbose_classes = null;
	private String current_class = "xxx";
	private String current_phase = null;
	private int current_class_id = 0;

	private String bootmodules = null;

	private IMDomain[] domains;
	private JoinPointChecker joinPoints;

	public BuilderOptions(String argv[]) {
		BuilderOptions.gopts = this;
		parseArguments(argv);
		if (this.hasOption("int8")) {
			INT_T   	= "jbyte";
			LONG_T  	= "jshort";
		}
		if (this.hasOption("int16")) {
			INT_T   	= "jshort";
			LONG_T  	= "jint";
		}
		if (this.hasOption("no_double")) {
			DOUBLE_T  	= "jfloat";
		}
		joinPoints = new JoinPointChecker(this, null);
	}

	/**
	 * This Method is the right place to register weavelets. It is called
	 * while building the class store.
	 */
	public void registerWeavelets(ClassStore repository) {
		joinPoints.reset();
		joinPoints.registerWeavelet(new keso.compiler.kni.Object(this,repository));
		joinPoints.registerWeavelet(new keso.compiler.kni.DebugOutWeavelet(this,repository));
		joinPoints.registerWeavelet(new keso.compiler.kni.TaskService(this,repository));
		joinPoints.registerWeavelet(new keso.compiler.kni.Task(this,repository));
		joinPoints.registerWeavelet(new keso.compiler.kni.InterruptService(this,repository));
		joinPoints.registerWeavelet(new keso.compiler.kni.ResourceService(this,repository));
		joinPoints.registerWeavelet(new keso.compiler.kni.EventService(this,repository));
		joinPoints.registerWeavelet(new keso.compiler.kni.AlarmService(this,repository));
		joinPoints.registerWeavelet(new keso.compiler.kni.MemoryService(this,repository));
		joinPoints.registerWeavelet(new keso.compiler.kni.MemoryTypes(this,repository));
		joinPoints.registerWeavelet(new keso.compiler.kni.Memory(this,repository));
		joinPoints.registerWeavelet(new keso.compiler.kni.PortalServiceWeavelet(this,repository));
		joinPoints.registerWeavelet(new keso.compiler.kni.Config(this,repository));
		joinPoints.registerWeavelet(new keso.compiler.kni.Tricore(this,repository));
		joinPoints.registerWeavelet(new keso.compiler.kni.SystemServiceWeavelet(this, repository));
		joinPoints.registerWeavelet(new keso.compiler.kni.SystemTimeWeavelet(this, repository));
		joinPoints.registerWeavelet(new keso.compiler.kni.TimeStampWeavelet(this, repository));
		joinPoints.registerWeavelet(new keso.compiler.kni.PosixIOWeavelet(this, repository));
		
        /* SmallVector application */
        // joinPoints.registerWeavelet(new keso.compiler.kni.SmallVectorWeavelet(this, repository)); // by: mkl
        
        /* EPOS HW-mediators */
        joinPoints.registerWeavelet(new keso.compiler.kni.UART_Weavelet(this, repository)); // by: mkl
        joinPoints.registerWeavelet(new keso.compiler.kni.NIC_Weavelet(this, repository)); // by: mkl
        joinPoints.registerWeavelet(new keso.compiler.kni.TemperatureSensorWeavelet(this, repository)); // by: mkl
        
        /* EPOS Abstractions */
        joinPoints.registerWeavelet(new keso.compiler.kni.ThreadWeavelet(this, repository)); // by: mkl
        joinPoints.registerWeavelet(new keso.compiler.kni.PeriodicThreadWeavelet(this, repository)); // by: mkl
        
        /* DMEC Application C++ based Weavelets */
        /*
        joinPoints.registerWeavelet(new keso.compiler.kni.ObjectBasedCppPictureMotionEstimatorWeavelet(this, repository)); // by: mkl
        joinPoints.registerWeavelet(new keso.compiler.kni.ObjectBasedCppPictureWeavelet(this, repository)); // by: mkl
        joinPoints.registerWeavelet(new keso.compiler.kni.ObjectBasedCppPictureMotionCounterpartWeavelet(this, repository)); // by: mkl
        joinPoints.registerWeavelet(new keso.compiler.kni.ObjectBasedCppNativeTestSupportWeavelet(this, repository)); // by: mkl
        */

        /* DMEC Application C based Weavelets */
        joinPoints.registerWeavelet(new keso.compiler.kni.OBA_C_PME_Weavelet(this, repository)); // by: mkl
        joinPoints.registerWeavelet(new keso.compiler.kni.OBA_C_P_Weavelet(this, repository)); // by: mkl
        joinPoints.registerWeavelet(new keso.compiler.kni.OBA_C_PMC_Weavelet(this, repository)); // by: mkl
        joinPoints.registerWeavelet(new keso.compiler.kni.OBA_C_NTS_Weavelet(this, repository)); // by: mkl

	}

	/**
	 * Returns the directory name where we can find the source classes.
	 */
	public String getClassDir() {
		return "classes";
	}

	/**
	 * Returns the directory name where we can find the JDK classes.
	 */
	public String getJDKClassPath() {
		return jdk_classpath;
	}

	/**
	 * Returns the directory name where we can find the Java source files.
	 */
	public String[] getModulPaths() {
		return modulpaths;
	}


	/**
	 * Returns the directory name where we can find the Java source files.
	 */
	public String[] getAllModulPaths() {
		return allModules;
	}

	/**
	 * Returns true if we use a linked list of local object references on
	 * the method stack.
	 */
	public boolean hasLinkedListOfLocalReferences() {
		if (this.hasOption("_X_no_llrefs")) return false;
		return global_heap.needStackScan();
	}

	/**
	 * Returns true if we want to prune the class tree.
	 */
	public boolean pruneClassTree() {
		return prune_CT;
	}

	final public boolean doBuildImage() {
		return buildimage;
	}

	private boolean verbose_class() {
		if (verbose_classes==null) return false;
		if (verbose_classes.get(current_class)!=null) return true;
		if (verbose_classes.get("c"+current_class_id)!=null) return true;
		if (current_phase==null) return false;
		if (verbose_classes.get("c"+current_class_id+current_phase)!=null) return true;
		return false;
	}

	public boolean vvverbose() {
		if (verbose_class()) return true;
		return false;
	}

	public void vvverbose(Object msg) {
		if (verbose_class()) Debug.message(msg.toString());
	}

	public void vverbose(Object msg) {
		if (very_verbose) Debug.message(msg.toString());
		if (verbose_class()) Debug.message(msg.toString());
	}

	public void verbose_hr() {
		verbose("===============================================");
	}

	public void verbose(Object msg) {
		if (verbose) Debug.message(msg.toString());
		if (verbose_class()) Debug.message(msg.toString());
	}

	public void phase(Object msg) {
		if (quite) return;
		Debug.message("=========== "+msg.toString()+" ===========");
	}

	public void info(Object msg) {
		Debug.message("info: "+msg.toString());
	}

	/**
	 * Shows a compiler warning, only in very verbose mode.
	 */
	public void vvwarn(Object msg) {
		if (very_verbose) warn(msg);
	}

	/**
	 * Shows a compiler warning, only in verbose mode.
	 */
	public void vwarn(Object msg) {
		if (verbose) warn(msg);
	}

	/**
	 * Shows a compiler warning.
	 */
	public void warn(Object msg) {
		Debug.message("warning: "+msg.toString());
	}

	public void todo(Object msg) {
		if (this.hasOption("todo")) {
			Debug.message("TODO: "+msg.toString());
			return;
		}
		vwarn("TODO: "+msg.toString());
	}

	public void stackTrace() {
		try {
			throw new CompileException();
		} catch (CompileException ex) {
			ex.printStackTrace();
		}
	}

	public void fatal(Object msg) throws CompileException {
		throw new CompileException(msg.toString());
	}

	/**
	 * In normal a cases critiical() throws an exception, but
	 * it is possible to override this behavour with -X:ignore_critical.
	 */
	public void critical(Object msg) throws CompileException {
		if (this.hasOption("ignore_critical") || !prune_CT) {
			warn(msg);
		} else {
			throw new CompileException(msg.toString());
		}
	}

	public void setCurrentClass(String name, int class_id) {
		current_class = name;
		current_class_id = class_id;
		current_phase = null;
	}

	public void setCurrentClass(String name, int class_id, String phase) {
		current_class = name;
		current_class_id = class_id;
		current_phase = phase;
	}

	public boolean fastMethodAlias() { return fast_method_alias; }

	public boolean hasVerbose() { return verbose; }

	public boolean hasGenericExp() { return generic_exceptions; }

	public boolean hasDebugCore() { return debug; }

	public boolean hasDbgSymbols() { return dbg_syms; }

	public boolean hasDumpBC() { return dump_bc; }

	public boolean getPreCompile() {
		return this.precompile || !this.translate;
	}

	public boolean getTranslate() {
		return this.translate || !this.precompile;
	}

	public boolean doInlineCalls() { return inline_methods; }

	public int maxInlineCosts() { return inline_costs; }

	public int maxInlineCostsSingle() {
		return (this.hasOption("no_inline_single") ? inline_costs : inline_costs_single);
	}

	public boolean doInlineRValues() { return inline_rvalue; }

	public boolean useGCCExtentions() {
		if (this.hasOption("no_gcc_extentsions")) return false;
		return true;
	}

	public boolean createCFG() { return create_CFG; }

	public boolean createDomTree() { return this.hasOption("dom"); }

	public boolean createUMLDiagram() { return this.hasOption("uml"); }

	public String declareConst() {
		return " __const__ ";
	}

	public boolean hasOption(String name) {
		if (name.equals("_X_dbg_astack") && !hasOption("ssa_astack")) return false;
		if (name.startsWith("ssa_") && !hasOption("ssa")) return false;
		if (extras==null) return false;
		if (extras.get(name)==null) return false;
		return true;
	}

	private void setTarget(int target) {
		if (target==DEFAULT_TARGET) {
			target_arch = target_arch_opt;
		} else {
			target_arch = target;
		}
	}

	public static int getTargetByName(String target_name) {
		for (int i=0;i<target_names.length;i++) {
			if (target_name.toUpperCase().equals(target_names[i])) return i;
		}
		return DEFAULT_TARGET;
	}

	public boolean isSingleTaskSystem() {
		switch (target_arch) {
			case AVR_TARGET:
			case H8300_TARGET:
			case LINUX_TARGET:
				return true;
		}
		return false;
	}

	public boolean isSingleDomainSystem() throws CompileException {
		return systemDef.getDomains().size()==1;
	}

	public boolean useExceptionStrings() {
		switch (target_arch) {
			case AVR_TARGET:
			case H8300_TARGET:
				return false;
		}
		return true;
	}

	/**
	 *
	 * 8 bit micro controller have more restrictions.
	 *    - max. object size is 256 byte.
	 */
	public boolean is8BitController() {
		switch (target_arch) {
			case AVR_TARGET:
			case H8300_TARGET:
				return true;
		}
		return false;
	}

	/**
	 * There are two ways for to distinguish a stack frame reference
	 * from a object reference.
	 *
	 * 1. Use a marker. Set the lowes bit in the stack frame reference.
	 *
	 * 2. Use a range. Align all stack in a seperate memory area and
	 *    use a range check for the test.
	 *
	 */
	public boolean useLLRefMarker() {
		if (isTriCore() && !this.hasOption("ssa_astack")) {
			return false;
		}
		return true;
	}

	public boolean useLLRefMarker2() {
		if (!useLLRefMarker()) return false;
		if (this.hasOption("_X_llref_mark2")) return true;
		if (is8BitController()) return true;
		return false;
	}

	public boolean hasRDTSC() {
		if (this.hasOption("no_rdtsc")) return false;
		switch (target_arch) {
			case LINUX_TARGET:
			case LINUX_JOSEK_TARGET:
			case EPOS_TARGET:
				return true;
		}
		return false;
	}

	public static int getTypeSize(String cType) {
		int i=0;

		for (;i<type_size_index.length;i++) {
			if (type_size_index[i].equals(cType)) break;
		}

		if (i==type_size_index.length) throw new RuntimeException("unknown type");

		int size = type_size_value[i];

		if (size==-1) return getOpts().targetAddrSize();

		return size;
	}

	public String targetAddrType() {
		switch (target_arch) {
			case AVR_TARGET:
			case H8300_TARGET:
				return "jshort";
		}
		return "jint";
	}

	public int targetAddrSize() {
		switch (target_arch) {
			case AVR_TARGET:
			case H8300_TARGET:
				return 2;
		}
		return 4;
	}

	public int targetAddrSizeShift() {
		switch (target_arch) {
			case AVR_TARGET:
			case H8300_TARGET:
				return 1; /* 2 Byte */
		}
		return 2; /* 4 Byte */
	}

	public String getCC() {
		if (override_cc!=null) return override_cc;
		switch (target_arch) {
			case TRICORE_TARGET:
				return "tricore-gcc";
			case AVR_TARGET:
				return "avr-gcc";
			case H8300_TARGET:
				return "/usr/h8300-hitachi-hms/bin/h8300-hitachi-hms-gcc";
		}
		return "gcc";
	}

	public String getObjVarName() {
		return "OBJS_KESO";
	}

	public String getCFlagsVarName() {
		switch (target_arch) {
			case TRICORE_TARGET:
				return "CC_OPT_USER";
		}
		return "CFLAGS";
	}

	public String getStrip() {
		switch (target_arch) {
			case TRICORE_TARGET:
				return "tricore-strip";
			case AVR_TARGET:
				return "avr-strip";
			case H8300_TARGET:
				return "/usr/h8300-hitachi-hms/bin/h8300-hitachi-hms-strip";
		}
		return "strip";
	}

	public String getObjSize() {
		switch (target_arch) {
			case TRICORE_TARGET:
				return "tricore-size";
			case AVR_TARGET:
				return "avr-size";
			case H8300_TARGET:
				return "/usr/h8300-hitachi-hms/bin/h8300-hitachi-hms-size";
		}
		return "size";
	}

	public String getObjCopy() {
		switch (target_arch) {
			case TRICORE_TARGET:
				return "tricore-objcopy";
			case AVR_TARGET:
				return "avr-objcopy";
			case H8300_TARGET:
				return "/usr/h8300-hitachi-hms/bin/h8300-hitachi-hms-objcopy";
		}
		return "objcopy";
	}

	public String getAR() {
		switch (target_arch) {
			case TRICORE_TARGET:
				return "tricore-ar";
			case AVR_TARGET:
				return "avr-ar";
		}
		return "ar";
	}

	public String getDebugFormat() {
		if (this.hasOption("dwarf-2")) return "dwarf-2";
		switch (target_arch) {
			case AVR_TARGET:
				return "stabs";
			default:
				return "";
		}
	}

	public String getProcessorType() {
		if (systemDef==null || systemDef.getProcessorType()==null) {
			warn("missing processor type! use default.\n");
			if (isAVR()) return "atmega8535";
			return "tc1796";
		} else {
			return systemDef.getProcessorType();
		}
	}


	public String getCFlags() {
		String flags="";

		flags = "-DTARGET_"+target_names[target_arch];

		if (this.hasOption("c++"))
			flags = flags+" -x c++";

		if (target_arch==LINUX_JOSEK_TARGET) {
			flags = flags+" -ansi -pthread -D_XOPEN_SOURCE=600 -g" + getDebugFormat() + " ";
		} else if (target_arch==EPOS_TARGET) {
			flags = flags+" -ansi -g" + getDebugFormat() + " ";
		} else {
			flags = flags+" -ansi -static -g" + getDebugFormat() + " ";
		}

		if (this.hasOption("gprof")) {
			flags = flags+"-pg ";
		}

		if (pedantic)
			flags+="-pedantic ";

		if (this.hasOption("O0") || this.hasOption("O1")) {
			// flags += "-O0"; breaks ProOSEK startup code!
			flags += "-O1 ";
		} else if (target_arch == LINUX_JOSEK_TARGET || target_arch==EPOS_TARGET) {
			flags += "-O3 ";
		} else {
			flags += "-Os -freorder-blocks ";
		}

		if (this.hasOption("gcc-short-double")) {
			flags += "-fshort-double ";
		}

		if (this.hasOption("gcc-stack-check")) {
			flags += "-fstack-check ";
		}

		if (this.hasOption("gcc-fnct-section")) {
			flags+="-ffunction-sections -fdata-sections ";
		}

		if (this.hasOption("gcc-no-stdlibs")) {
			flags += "-nodefaultlibs -nostdlib ";
		}

		switch (target_arch) {
			case LINUX_TARGET:
				//return flags+"-nostartfiles -Wall -Wno-strict-aliasing -falign-functions=1";
				return flags+"-Wall -nodefaultlibs -nostdlib -Wno-strict-aliasing -falign-functions=1";
			case LINUX_JOSEK_TARGET:
				//return flags+"-nostartfiles -Wall -Wno-strict-aliasing -falign-functions=1";
				//return flags+"-Wall -nodefaultlibs -nostdlib -Wno-strict-aliasing -falign-functions=1";
				//return flags+"-Wall -Wno-strict-aliasing -falign-functions=1";
				return flags+"-Wall -Wno-strict-aliasing";
			case TRICORE_TARGET:
				//return flags+"-nostartfiles -falign-functions=4 -Wall -mcpu=" + getProcessorType();
				return flags+"-nostartfiles -Wall -mcpu=" + getProcessorType();
			case AVR_TARGET:
				try {
					if(systemDef.getStack().getAttribute("StackChecks").valueString().compareToIgnoreCase("true")==0)
						flags+= "-mkeso-stack-check ";
					if(getSysDef().getStack().getAttribute("Size").valueInt() < 256)
						flags+= "-mtiny-stack ";
			} catch (NullPointerException e) { }
				return flags + "-Wall -ffreestanding -funsigned-bitfields -fshort-enums -fpack-struct -I/usr/avr/include";
				//return flags + "-Wall -funsigned-bitfields -fshort-enums -fpack-struct";
		}
		return flags+"-Wall ";
	}

	public String getLinkerOptions() {
		StringBuffer linker = new StringBuffer();;

		if (this.hasOption("gcc-fnct-section"))
			linker.append("--gc-sections ");

		switch (target_arch) {
			case TRICORE_TARGET:
				/* cludge: we also need the -relax linker option here. Since
				 * the ProOSEK Makefiles do not permit the specification of
				 * user defined linker flags, we add them here
				 */
				linker.append(getCorePath());
				linker.append("/ramresident-gnu.ldscript -relax ");
		}

		if (linker.length()==0)
				return null;

		return linker.toString();
	}

	public String getLDFlags() {
		switch (target_arch) {
			case EPOS_TARGET:
			case TRICORE_TARGET:
				return "-mcpu=" + getProcessorType() + " ";
			case AVR_TARGET:
				return "-mmcu=" + getProcessorType() + " ";
		}
		return "";
	}

	public String getCoreLibName() {
		switch (target_arch) {
			case LINUX_TARGET:
				return "KESO-i386-linux";
			case EPOS_TARGET:
			case LINUX_JOSEK_TARGET:
				/* core lib is not used */
				return null;
			case TRICORE_TARGET:
				return "KESO-tricore-osek";
			case AVR_TARGET:
				return "KESO-avr-mdsa";
			case H8300_TARGET:
				return "KESO-h8300-plain";
		}
		return "FIXME-UNKNOWN-LIB";
	}

	public String getCoreLib() {
		switch (target_arch) {
			case LINUX_TARGET:
			case LINUX_JOSEK_TARGET:
			case EPOS_TARGET:
				return "libKESO-i386-linux.a";
			case TRICORE_TARGET:
				return "libKESO-tricore-osek.a";
			case AVR_TARGET:
				return "libKESO-avr-mdsa.a";
			case H8300_TARGET:
				return "libKESO-h8300-plain.a";
		}
		return "libKESO.a";
	}

	public boolean smartFileUpdate(File file, String new_value) {
		try {
			if (!smartFileUpdate) return true;
			if (file==null || !file.canRead()) return true;
			int size = (int)file.length();
			char[] buf = new char[size];
			FileReader old_file = new FileReader(file);
			old_file.read(buf);
			String old_value = new String(buf);
			if (old_value.equals(new_value)) return false;
		} catch (IOException ex) {
			verbose("smartFileUpdate: "+ex);
		}
		return true;
	}

	public int getTarget() {
		return target_arch;
	}

	public boolean isTarget(int target) {
		return (target_arch==target);
	}

	public boolean isAVR() {
		return (target_arch==AVR_TARGET);
	}

	public boolean isTriCore() {
		return (target_arch==TRICORE_TARGET);
	}

	public boolean isLinux() {
		return (target_arch==LINUX_TARGET);
	}

	public String getOutputClassTree() {
		return classtree;
	}

	public String[] getSourcePaths() {
		return split(sourcepath,':');
	}

	public String getFirstLevelOutputPath() {
		return outputpath;
	}

	public String getOutputPath() {
		return outputpath + "/" + getSystemName();
	}

	public String getSystemName() {
		if (systemDef==null) return "unknown";
		return systemDef.getIdentifier();
	}

	public int getCurrentSystemID() {
		if (systemDef == null) {
			return -1;
		}
		return systemDef.getSystemID();
	}

	public String getCorePath() {
		return kesosrcpath + "/kore/";
	}

	public JoinPointChecker getJoinPoints() {
		return joinPoints;
	}

	public boolean hasLittleEndianMemory() {
		return hasLittleEndianMemory(target_arch);
	}

	public static boolean hasLittleEndianMemory(int target_arch) {
		switch (target_arch) {
			case LINUX_TARGET:
			case LINUX_JOSEK_TARGET:
			case EPOS_TARGET:
			case AVR_TARGET:
			case TRICORE_TARGET:
				return true;
			default:
				return true;
		}
	}

	private void usage() {
		System.err.println("\nusage:\tjino [-[no]build] [-v[v]] [-v:class] [-cfg] [-ct/classtree] [-OF] [-pedantic]");
		System.err.println("\t[-[no]prune] [-[no]inline] [-inline_costs:value:value] [-X:option[:option]]");
		System.err.println("\t[-sp source-path] [-kesosrcpath path] [-def/f config-file]");
	}

	private void parseArguments(String[] argv) {
		// allow GenerateKesoClasses to circumvent BuilderOptions
		if (argv==null) return;

		String systemDefFile = "kesorc";

		for (int i=0;i<argv.length;i++) {
			String arg = argv[i];

			if (arg.equals("-help")||arg.equals("--help")) {
				usage();
				System.exit(0);
			}

			if ((arg.equals("-def")||arg.equals("-f")) && (i+1)<argv.length ) {
				systemDefFile = argv[++i];
				File test = new File(systemDefFile);
				if (!test.exists()) {
					warn("config file = \""+systemDefFile+"\" not found!");
					systemDefFile="kesorc";
				}
				continue;
			}

			if ((arg.equals("-sourcepath")||arg.equals("-sp")) && (i+1)<argv.length ) {
				sourcepath = argv[++i];
				if (verbose) Debug.out.println("source path = "+sourcepath);
				continue;
			}

			if (arg.equals("-jdk") && (i+1)<argv.length ) {
				jdk_classpath = argv[++i];
				if (verbose) Debug.out.println("jdk path = "+jdk_classpath);
				continue;
			}

			if (arg.equals("-bootmodules") && (i+1)<argv.length ) {
				bootmodules = argv[++i];
				if (verbose) Debug.out.println("bootmodules = "+bootmodules);
				continue;
			}

			if ((arg.equals("-output")||arg.equals("-op")) && (i+1)<argv.length ) {
				outputpath = argv[++i];
				if (verbose) Debug.out.println("output path = "+outputpath);
				continue;
			}

			if (arg.equals("-kesosrcpath") && (i+1)<argv.length ) {
				kesosrcpath = argv[++i];
				if (verbose) Debug.out.println("kesosrcpath = "+kesosrcpath);
				continue;
			}

			if (arg.equals("-build")) {
				buildimage=true;
				continue;
			}

			if (arg.equals("-nobuild")) {
				buildimage=false;
				continue;
			}

			if (arg.equals("-prune")) {
				prune_CT = true;
				continue;
			}

			if (arg.equals("-noprune")) {
				prune_CT = false;
				continue;
			}

			if (arg.equals("-q")) {
				quite = true;
				continue;
			}

			if (arg.equals("-v")) {
				verbose = true;
				continue;
			}

			if (arg.equals("-vv")) {
				very_verbose = true;
				verbose = true;
				continue;
			}

			if (arg.startsWith("-v:")) {
				if (verbose_classes==null) verbose_classes = new Hashtable();
				String [] ext = split(arg,':');
				for (int n=1;n<ext.length;n++) verbose_classes.put(ext[n],ext[n]);
				Debug.out.println("v-options = "+arg);
				continue;
			}

			if (arg.startsWith("-cc:")) {
				String [] ext = split(arg,':');
				if (ext.length>1) override_cc = ext[1];
				if (verbose) Debug.out.println("compiler = "+override_cc);
				continue;
			}

			if (arg.equals("-inline")) {
				inline_methods = true;
				continue;
			}

			if (arg.equals("-noinline")) {
				inline_methods = false;
				continue;
			}

			if (arg.equals("-dump")) {
				dump_bc = true;
				continue;
			}

			if (arg.equals("-cfg")) {
				create_CFG = true;
				continue;
			}

			if ((arg.equals("-classtree") || arg.equals("-ct")) && (i+1)<argv.length ) {
				classtree = argv[++i];
				if (verbose) Debug.out.println("output class tree = "+classtree);
				continue;
			}

			if (arg.equals("-debug") || arg.equals("-g")) {
				debug = true;
				dbg_syms = true;
				continue;
			}

			if (arg.equals("-pedantic")) {
				pedantic = true;
				continue;
			}

			if (arg.equals("-OF")) {
				dbg_syms = false;
				continue;
			}


			if (arg.equals("-tricore")) {
				target_arch_opt = TRICORE_TARGET;
				continue;
			}

			if (arg.equals("-avr")) {
				target_arch_opt = AVR_TARGET;
				continue;
			}

			if (arg.equals("-h8300")) {
				// Lego MindStorm :-)
				target_arch_opt = H8300_TARGET;
				continue;
			}

			if (arg.equals("-arm")) {
				target_arch_opt = ARM_TARGET;
				continue;
			}

			if (arg.equals("-linux")) {
				target_arch_opt = LINUX_TARGET;
				continue;
			}


			if (arg.startsWith("-inline_costs:")) {
				String [] ext = split(arg,':');
				inline_costs = Integer.parseInt(ext[1]);
				if (ext.length>2) inline_costs_single = Integer.parseInt(ext[2]);
				if (verbose) Debug.out.println("inline costs = "+inline_costs+":"+inline_costs_single);
				inline_methods = true;
				continue;
			}

			if (arg.startsWith("-inline_costs=")) {
				String [] ext = split(arg,'=');
				inline_costs = Integer.parseInt(ext[1]);
				if (verbose) Debug.out.println("inline costs = "+inline_costs);
				inline_methods = true;
				continue;
			}

			if (arg.startsWith("-X")) {
				String [] ext = split(arg,':');
				for (int n=1;n<ext.length;n++) extras.put(ext[n],ext[n]);
				if (this.hasOption("inline__all_")) {
					extras.put("inline_clinit","inline_clinit");
					extras.put("inline_task","inline_task");
					extras.put("inline_isr","inline_isr");
				}
				if (is8BitController()) {
					extras.put("stack8","stack8");
				}
				if (verbose) Debug.out.println("options = "+arg);
				continue;
			}

			if (arg.equals("-precompile")) {
				this.precompile = true;
				continue;
			}

			if (arg.equals("-translate")) {
				this.translate = true;
				continue;
			}

			Debug.out.println("Unknown option: \""+arg+"\"");
			System.exit(-1);
		}

		if (verbose) Debug.out.println("definition = "+systemDefFile);
		try {
			worldDef = ConfigReader.parseDefinition(systemDefFile);
			parseServices();
			parseFirstSystemDefinition();
		} catch (Exception e) {
			System.err.print("Failed to parse config file: ");
			System.err.println(e.toString());
			usage();
			System.exit(-1);
		}

		if (systemDef==null) {
			Debug.out.println("System definition missing!");
			Debug.out.println("please set KESORC to the right definition");
			System.exit(-1);
		}
	}

	public Attribut[] getClusterAttrs() {
		return worldDef.getAllAttributes();
	}


	private void parseServices() throws CompileException {
		if (worldDef == null) {
			throw new CompileException("No world definition");
		}

		Attribut[] worldAttrs = worldDef.getAllAttributes();

		Vector modules = new Vector();

		if (bootmodules != null) {
			String[] mods = bootmodules.split(":");
			for (int i = 0; i < mods.length; ++i) {
				modules.add(mods[i]);
			}
		}


		/*
		 * Search for all networks, services and imports
		 *
		 */

		for (int i = 0; i < worldAttrs.length; ++i) {
			if (worldAttrs[i] instanceof NetworkDefinition) {

				ServiceManager.instance.addNetwork((NetworkDefinition) worldAttrs[i]);

			} else if (worldAttrs[i] instanceof SystemDefinition) {

				SystemDefinition sys = (SystemDefinition) worldAttrs[i];

				/* Modules */
				StringAttr mods = (StringAttr)sys.getAttribute("Modules");

				if (mods != null) {
					String src = mods.valueString();
					if (bootmodules!=null) src = bootmodules+":"+src;
					String[] modNames = src.split(":");

					for (int j = 0; j < modNames.length; ++j) {
						if (!modules.contains(modNames[j])) {
							modules.add(modNames[j]);
						}
					}
				}

				Vector domains = sys.getDomains();

				for (int j = 0; j < domains.size(); ++j) {
					DomainDefinition dom = (DomainDefinition) domains.elementAt(j);

					ServiceManager.instance.addServices(dom.getServices());
					ServiceManager.instance.addImports(dom.getImports());
				}
			}
		}

		allModules = new String[modules.size()];

		for (int i = 0; i < allModules.length; ++i) {
			allModules[i] = (String) modules.elementAt(i);
		}

		ServiceManager.instance.buildDependencyTree();
	}

	public WorldDefinition getWorldDefinition() {
		return worldDef;
	}

	public boolean parseFirstSystemDefinition() throws CompileException {
		worldDef.rewindSystemDef();
		return parseNextSystemDefinition();
	}

	public boolean parseNextSystemDefinition() throws CompileException {
		systemDef = worldDef.nextSystemDef();
		if (systemDef==null) return false;

		DecoratedNames.reset();
		global_heap=null;

		Attribut target_attr = systemDef.getAttribute("Target");
		if (target_attr==null) setTarget(DEFAULT_TARGET);
		else setTarget(getTargetByName(target_attr.valueString()));

		Vector ddefs=systemDef.getDomains();
		resources = new Vector();
		alarms = new Vector();
		isrs = new Vector();

		for (int j=0; j<ddefs.size(); j++) {
			DomainDefinition ddef = (DomainDefinition) ddefs.elementAt(j);
			resources.addAll(ddef.getResources());
			alarms.addAll(ddef.getAlarms());
			isrs.addAll(ddef.getISRs());
		}

		/* Modules */
		StringAttr src_path = (StringAttr)systemDef.getAttribute("Modules");
		if (src_path!=null) {
			String src = src_path.valueString();
			if (bootmodules!=null) src = bootmodules+":"+src;
			verbose("libs/"+src);
			modulpaths=src.split(":");
		} else {
			if (bootmodules!=null) modulpaths=bootmodules.split(":");
			warn("Source modules missing!");
			System.exit(-1);
		}

		return true;
	}

	public static String typeToString(int bctype) {
		switch (bctype) {
			case BCBasicDatatype.INT:
				return INT_T;
			case BCBasicDatatype.LONG:
				return LONG_T;
			case BCBasicDatatype.FLOAT:
				return FLOAT_T;
			case BCBasicDatatype.DOUBLE:
				return DOUBLE_T;
			case BCBasicDatatype.REFERENCE:
				return OBJ_T;
			case BCBasicDatatype.BYTE:
				return BYTE_T;
			case BCBasicDatatype.CHAR:
				return CHAR_T;
			case BCBasicDatatype.SHORT:
				return SHORT_T;
			case BCBasicDatatype.BOOLEAN:
				return BOOLEAN_T;
			case BCBasicDatatype.RETURN_ADDRESS:
				return "void*";
			default:
				return "void";
		}
	}

	private static BuilderOptions gopts = null;

	public static void setGlobalOptions(BuilderOptions opts) { gopts = opts; }
	public static BuilderOptions getOpts() { return gopts; }

	private static String[] split(String src, char delim) {
		Vector v = new Vector();

		if (src==null) return null;

		int s=0;
		for (int p=0;p<src.length();p++) {
			if (src.charAt(p)==delim) {
				v.add(src.substring(s,p));
				s=p+1;
			}
		}
		v.add(src.substring(s,src.length()));

		String[] ret = new String[v.size()];
		for (int i=0;i<ret.length;i++) { ret[i] = (String)v.elementAt(i); }

		return ret;
	}

	public IMDomain[] getDomains() throws CompileException {
		return IMDomain.createDomains(getSystemName(), this);
	}

	public SystemDefinition getSysDef() { return systemDef; }

	public void setupGlobalHeap() throws CompileException {
		global_heap = new IMGlobalHeap(this);
	}

	public IMGlobalHeap getGlobalHeap() {
		if (global_heap==null) {
			warn("Heap not defined yet!");
			throw new Error();
		}
		return global_heap;
	}

	public Vector getSysResource() { return resources; }
	public Vector getSysAlarms() { return alarms; }
	public Vector getSysISRs() { return isrs; }


	/*
	 * Since tasks may be added by other sources than kesorc,
	 * this Vector cannot be created once only after reading
	 * the config.
	 */
	public Vector getSysTasks() {
		Vector tasks = new Vector();
		Vector ddefs=systemDef.getDomains();
		for (int j=0; j<ddefs.size(); j++) {
			DomainDefinition ddef = (DomainDefinition) ddefs.elementAt(j);
			tasks.addAll(ddef.getTasks());
		}
		return tasks;
	}
}
