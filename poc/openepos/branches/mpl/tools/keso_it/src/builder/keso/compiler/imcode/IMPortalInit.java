/**(c)
 *
 * Copyright (C) 2007 Christian Wawersich
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

package keso.compiler.imcode; 

import keso.compiler.*;
import keso.compiler.backend.Coder;

/**
 * This class defines the init methods of remote services and classes. 
 */
public interface IMPortalInit {
       
    public void translate(Coder coder) throws CompileException;
   
}
