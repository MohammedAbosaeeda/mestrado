/**(c)

  Copyright (C) 2006 Christian Wawersich

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi@cs.fau.de for more info.

  (c)**/

package keso.core;

/**
 * This class provides the global name service for the inter-domain-communication.
 */
public final class PortalService {

	/**
	 * return a proxy object for the remote service. The service import
	 * must be descript in the kesorc file.
	 */
	public static Portal lookup(String name) { return null; }

	/**
	 * return the domain local service object. The service object must be
	 * descript in the kesorc file and is created on domain startup.
	 */
	public static Service getService(String name, String classname) { return null; }

	/**
	 * call the packet handler of a remote service
	 */
	public static void handlePackets(String service, String network) {}

}
