/**(c)
 *
 * Copyright (C) 2005 Christian Wawersich
 *
 * This file is part of the KESO Operating System.
 *
 * It is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * Please contact wawi@cs.fau.de for more info.
 *
 * (c)**/

package keso.compiler.backend;

import keso.compiler.BuilderOptions;

import java.io.*;

import java.util.Vector;
import java.util.Hashtable;
import java.util.Enumeration;


class MakeRule {
    BuilderOptions opts;
    String alias;
    String target;
    String source;
    String depends;
    
    MakeRule(BuilderOptions opts, String alias, String depends) {
        this.opts = opts;
        this.alias = alias;
        this.target = alias+".o";
        this.source = alias+".c";
        this.depends = depends;
    }
    
    MakeRule(BuilderOptions opts, String target, String source, String depends) {
        this.opts = opts;
        this.target = target;
        this.source = source;
        this.depends = depends;
    }
    
    String getTarget() {
        return target;
    }

    String getSource() {
        return source;
    }
    
    private void writeDotRule(PrintStream out, String alias, String comment, String target_fmt) {
        out.print("\n\t@echo ");
        out.print(comment);
        out.print(' ');
        out.print(alias);
        out.print('.');
        out.print(target_fmt);
        out.print(' ');
        out.print("\n\t@dot -T");
        out.print(target_fmt);
        out.print(" -o ");
        out.print(alias);
        out.print('.');
        out.print(target_fmt);
        out.print(' ');
        out.print(alias);
        out.print(".dot");
    }

    public void writeDotRule(PrintStream out) throws IOException {
	    if (alias==null) return;
	    if (opts.createCFG()) {
		    out.print(alias);
		    out.print(".ps: ");
		    out.print(source);
		    out.print(" ");
		    out.print(alias);
		    out.print(".dot");
		    writeDotRule(out,alias,"CFG","ps");
	    }
	    if (opts.hasOption("dom")) {
		    out.print("dom_");
		    out.print(alias);
		    out.print(".ps: ");
		    out.print(source);
		    out.print(" dom_");
		    out.print(alias);
		    out.print(".dot");
		    writeDotRule(out,"dom_"+alias,"DOM","ps");
	    }
    }
    
    /**
     * Write Makefilerule to out stream
     * <target>: <source>
     * 	@echo OBJ <target>
     * 	@$(CC) $(CFLAGS) -c -o <target> <source>
     */
    void writeRule(PrintStream out) throws IOException {
	    writeRule(out,""); 
    }

    void writeRule(PrintStream out, String ext_depends) throws IOException {
        out.print(target);
        out.print(": ");
        out.print(source);
        out.print(" ");
        out.print(depends);
	if (!ext_depends.equals("")) {
		out.print(" ");
		out.print(ext_depends);
	}
        if (alias !=null && opts.createCFG()) {
            writeDotRule(out,alias,"CFG","ps");
        }
        if (alias !=null && opts.hasOption("dom")) {
            writeDotRule(out,"dom_"+alias,"DOM","ps");
        }
        out.print("\n\t@echo OBJ ");
        out.print(target);
        out.print("\n\t@$(CC) $(" + opts.getCFlagsVarName() + ") -c -o ");
        out.print(target);
        out.print(" ");
        out.print(source);
    }
}

