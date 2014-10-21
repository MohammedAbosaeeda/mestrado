/**(c)

  Copyright (C) 2005 Christian Wawersich 

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi.fau.de for more info.

  (c)**/

package keso.compiler; 

import keso.util.Debug; 
import java.io.*;

final public class Main {
	static class NoExit extends java.lang.SecurityManager {
		public NoExit() {

		}
		public void checkCreateClassLoader() { } 
		public void checkAccess(Thread g) { }
		public void checkAccess(ThreadGroup g) { }
		public void checkExec(String cmd) { }
		public void checkLink(String lib) { }
		public void checkRead(FileDescriptor fd) { }
		public void checkRead(String file) { }
		public void checkRead(String file, Object context) { }
		public void checkWrite(FileDescriptor fd) { }
		public void checkWrite(String file) { }
		public void checkDelete(String file) { }
		public void checkConnect(String host, int port) { }
		public void checkConnect(String host, int port, Object context) { }
		public void checkListen(int port) { }
		public void checkAccept(String host, int port) { }
		public void checkMulticast(java.net.InetAddress maddr) { }
		public void checkMulticast(java.net.InetAddress maddr, byte ttl) { }
		public void checkPropertiesAccess() { }
		public void checkPropertyAccess(String key) { }
		public void checkPropertyAccess(String key, String def) { }
		public boolean checkTopLevelWindow(Object window) { return true; }
		public void checkPrintJobAccess() { }
		public void checkSystemClipboardAccess() { }
		public void checkAwtEventQueueAccess() { }
		public void checkPackageAccess(String pkg) { }
		public void checkPackageDefinition(String pkg) { }
		public void checkSetFactory() { }
		public void checkMemberAccess(Class clazz, int which) { }
		public void checkSecurityAccess(String provider) { }

		public void checkExit(int status) {
			throw new NoExitException(status);
		}

		public void checkPermission(java.security.Permission perm) {
		}
	}

	public static void main(String[] argv) {
		try {
			BuilderOptions opts = new BuilderOptions(argv);
			System.setSecurityManager(new NoExit());
			Builder builder=new Builder(opts);
            
			if (opts.getPreCompile()) {
				// compile all source files into class files
				builder.preCompileModules();
			}
		
			if (opts.getTranslate()) {
				builder.computeServices(); 

				builder.writeFirstLevelMakefile();
			}
		
			opts.parseFirstSystemDefinition();
			do {
				if (opts.getPreCompile()) {
					// read source files and compile it into
					// class files
					builder.copySourceFiles();
				}

				if (opts.getTranslate()) {	
					// read class files from disk
					// and prune the class tree
					builder.loadModules();
					
					// pre execute class constructors and
					// parse method data
					builder.loadClasses();

					// compute the class type info 
					builder.computeClassTypeInfo();

					// compute vtables once
					builder.computeVTables();

					// inline methods
					// and analyseCallGraph the 1. time
					builder.inlineMethodCalls();

					// perform constant folding, this will
					// analyseCallGraph 2. time
					// omit unused methods 
					// analyseCallGraph 3. time
					// omit unused fields
					builder.performConstantFolding();

					// write class tree if needed
					builder.writeClassTree();

					// write control flow graph if needed
					builder.writeCFG();

					// write UML Diagrams if needed
					builder.writeUMLDiagram();
					builder.writeClassList();

					// write dominator trees
					builder.writeDomTree();

					// translate byte code to C
					// analyseCallGraph last time
					builder.translate();
				}

			} while (opts.parseNextSystemDefinition());

			if (opts.doBuildImage())
				builder.callMake();

		} catch (Exception ex) {
			ex.printStackTrace();
			System.exit(-1);
		}
	}
}
