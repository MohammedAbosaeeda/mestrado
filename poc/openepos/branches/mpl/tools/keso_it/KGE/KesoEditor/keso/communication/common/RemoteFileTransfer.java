package keso.communication.common;

import java.io.FileOutputStream;

abstract public class RemoteFileTransfer {

	public static void transfer(String path, IRemoteFile remotefile) {
		try {
			FileOutputStream out = new FileOutputStream(path);
			remotefile.open();
		 	
		 	byte [] data;
		 	while ((data = remotefile.nextBlock()) != null) {
		 		out.write(data);
		 	}
		 	out.close();
		 	remotefile.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
