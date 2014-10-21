/**(c)

  Copyright (C) 2005 Christian Wawersich 

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi@cs.fau.de for more info.

  (c)**/

package keso.compiler.config;

import keso.compiler.*;
import keso.compiler.config.parser.ConfigReader;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;

import java.lang.Exception;

import java.util.Vector;

/**
 * Executable Class that will generate parts of the Keso java
 * libs with information from kesorc.
 */
public class GenerateKesoClasses {

	private static SystemDefinition sdef;
	
	private static interface Template {
		public void genCode(BufferedWriter srcfile) throws Exception;
		public String getGeneratedClassName();
		public boolean isIdentifierLine(String line);
	}
	
	private static class EventsTemplate implements Template {
		public String getGeneratedClassName() { return "Events"; }
		public boolean isIdentifierLine(String line) {
			return (line.compareTo("/*KESO_INSERT_EVENT_DEFINITIONS*/")==0);
		}
		
		public void genCode(BufferedWriter srcfile) throws Exception {
			Vector events = GenerateKesoClasses.sdef.getEvents();

			for(int i=0; i<events.size(); i++) {
				EventDefinition ev = (EventDefinition) events.elementAt(i);
				srcfile.write("\tpublic static final int ");
				srcfile.write(ev.ident);
				srcfile.write(" = 0x");
				srcfile.write(java.lang.Integer.toHexString(ev.getMask()));
				srcfile.write(";");
			}
		}
	}

	private static class AppmodesTemplate implements Template {
		public String getGeneratedClassName() { return "Appmodes"; }
		public boolean isIdentifierLine(String line) {
			return (line.compareTo("/*KESO_INSERT_APPMODE_DEFINITIONS*/")==0);
		}
		
		public void genCode(BufferedWriter srcfile) throws Exception {
			Vector appmodes = GenerateKesoClasses.sdef.getAppmodes();

			for(int i=0; i<appmodes.size(); i++) {
				AppmodeDefinition am = (AppmodeDefinition) appmodes.elementAt(i);
				writeIntConstant(srcfile, am.ident, i+1);
			}
		}
	}

	private static class CountersTemplate implements Template {
		public String getGeneratedClassName() { return "Counters"; }
		public boolean isIdentifierLine(String line) {
			return (line.compareTo("/*KESO_INSERT_COUNTER_DEFINITIONS*/")==0);
		}
		
		public void genCode(BufferedWriter srcfile) throws Exception {
			Vector counters = GenerateKesoClasses.sdef.getCounters();

			/* FIXME
			 * I'm not sure how the ProOSEK configurator calculates the values of the
			 * System timer. For now i guess that its the properties of the first
			 * defined counter or all zero if there is no counter defined
			 */
			int osmaxallowedvalue=0, osticksperbase=0, osmincycle=0, ostickduration=0;
			
			for(int i=0; i<counters.size(); i++) {
				CounterDefinition cnt = (CounterDefinition) counters.elementAt(i);
				int maxallowedvalue, ticksperbase, mincycle, tickduration;
				
				try {
					maxallowedvalue = Integer.parseInt(cnt.getAttribute("MAXALLOWEDVALUE").valueString());
					ticksperbase = Integer.parseInt(cnt.getAttribute("TICKSPERBASE").valueString());
					mincycle = Integer.parseInt(cnt.getAttribute("MINCYCLE").valueString());
					tickduration = Integer.parseInt(cnt.getAttribute("TIME_IN_NS").valueString());
				} catch (Exception e) {
					throw new CompileException("Counter " + cnt.getIdentifier() + " has a required property not defined");
				}
						
				if(i==0) {
					osmaxallowedvalue=-maxallowedvalue;
					osticksperbase=ticksperbase;
					osmincycle=mincycle;
					ostickduration=tickduration;
				}
				
				writeIntConstant(srcfile, "OSTICKSPERBASE_"+cnt.ident, ticksperbase);
				writeIntConstant(srcfile, "OSMAXALLOWEDVALUE_"+cnt.ident, maxallowedvalue);
				writeIntConstant(srcfile, "OSMINCYCLE_"+cnt.ident, mincycle);
				srcfile.write("\n");
			}
			writeIntConstant(srcfile, "OSTICKSPERBASE", osticksperbase);
			writeIntConstant(srcfile, "OSMAXALLOWEDVALUE", osmaxallowedvalue);
			writeIntConstant(srcfile, "OSMINCYCLE", osmincycle);
			writeIntConstant(srcfile, "OSTICKDURATION", ostickduration);
		}
	}

	private static void writeIntConstant(BufferedWriter srcfile, String name, int value) throws Exception {
		srcfile.write("\tpublic static final int ");
		srcfile.write(name);
		srcfile.write(" = 0x");
		srcfile.write(Integer.toHexString(value));
		srcfile.write(";\n");
	}
	
	private static void printUsage() {
		System.err.println("Usage:\n To generate sources from templates run");
		System.err.println("  java keso.compiler.config.GenerateKesoClasses <basedir> <path to kesorc> <templatedir> <sourcedir> <stampdir>");
		System.err.println(" basedir should be an absolute path but does not have to.");
		System.err.println(" all others arguments must be relative to basedir.");
	}
	
	public static void main(String[] args) {
		if(args.length != 5) {
			printUsage();
			System.exit(-1);
		}

		String basedir = args[0];
		String kclfile = args[1];
		String tmplate = args[2];
		String srcdir  = args[3];

		WorldDefinition wdef = null;

		String config_file_name = basedir+"/"+kclfile;
		File config_file = new File(config_file_name);
		
		if (!config_file.exists()) {
			System.err.println("Config file "+config_file_name+" not found!");
			System.exit(-1);
		}

		try {
			// read kesorc
			BuilderOptions opts = new BuilderOptions(null);
			wdef = ConfigReader.parseDefinition(config_file_name);
			while ((sdef = wdef.nextSystemDef())!=null) {
				generateSource(basedir, tmplate, srcdir, new EventsTemplate());
				generateSource(basedir, tmplate, srcdir, new CountersTemplate());
				generateSource(basedir, tmplate, srcdir, new AppmodesTemplate());
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	public static void generateCoreClasses(BuilderOptions opts) throws Exception {
		WorldDefinition wdef = opts.getWorldDefinition();
		wdef.rewindSystemDef();
		while ((sdef = wdef.nextSystemDef())!=null) {
			generateSource(".", "templates", "libs", new EventsTemplate());
			generateSource(".", "templates", "libs", new CountersTemplate());
			generateSource(".", "templates", "libs", new AppmodesTemplate());
		}
	}

	private static void generateSource(String basedir, String tmplate, String srcdir , Template generator) throws Exception {
		// Generate Events.java from template 
		System.out.println("Generating keso.core."+generator.getGeneratedClassName());

		File gen_class_file = new File(basedir+"/"+srcdir+"/core/keso/core/"+generator.getGeneratedClassName()+".java");

		BufferedReader rtemplate = 
			new BufferedReader(new java.io.FileReader(basedir+"/"+tmplate+"/"+generator.getGeneratedClassName()+".java.template"));
		BufferedWriter wsrcfile = 
			new BufferedWriter(new java.io.FileWriter(gen_class_file));

		while (true) {
			String line = rtemplate.readLine();
			if (line == null) break;

			if(generator.isIdentifierLine(line)) {
				generator.genCode(wsrcfile);
			} else {
				wsrcfile.write(line);
			}
			wsrcfile.newLine();
		}
		rtemplate.close();
		wsrcfile.close();
	}
}
