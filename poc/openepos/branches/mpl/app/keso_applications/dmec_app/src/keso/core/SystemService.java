/**(c)

  Copyright (C) 2005 Christian Wawersich 

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi@cs.fau.de for more info.

  (c)**/

package keso.core;

/**
 *
 * This class is affected by the SystemServiceWeavelet!
 */
public final class SystemService {
    public static int getSystemIdentifier() {
        return -1;
    }
    public static int getIntegerConstant(String name) {
        return 0;
    }
}
