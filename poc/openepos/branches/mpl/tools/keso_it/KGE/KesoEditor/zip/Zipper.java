package zip;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;


public class Zipper {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		if (args.length > 2) {
			if (args[0].compareToIgnoreCase("zip") == 0) {
				(new Zipper()).zip(args);
			} else if (args[0].compareToIgnoreCase("unzip") == 0) {
				(new Zipper()).unzip(args);
			}
		} else {
			System.err.println("usage: <option> <zip-filename> [[<file> | <directory>]+ | <directory>]");
			System.err.println("option: <zip> | <unzip>");
		}
	}
	
	public Vector getAllFiles(String directory, String filename) {
		Vector files = new Vector();
		File f = new File(directory, filename);
		
		if (f.isFile()) {
			files.add(filename);
		} else if (f.isDirectory()) {
			String [] list = f.list();
			for (int i = 0; list != null && i < list.length; i++) {
				if (list[i].equalsIgnoreCase(".metadata") ||
						list[i].equalsIgnoreCase(".") ||
						list[i].equalsIgnoreCase("..")) {
					continue;
				}
				if (filename.trim().length() == 0) {
					files.addAll(getAllFiles(directory, list[i]));
				} else {
					files.addAll(getAllFiles(directory,
						filename + System.getProperty("file.separator") + list[i]));
				}
			}
		}
		
		return files;
	}
	
	private String getAbsolutePath(String path) {		
		String result = null;
		try {
			result =(new File(path)).getAbsoluteFile().getCanonicalFile().getParent();
		} catch(Exception e) {
			result = null;
		}
		
		return result;
	}
	
	private String getName(String path) {
		String result = null;
		try {
			result =(new File(path)).getAbsoluteFile().getCanonicalFile().getName();
		} catch(Exception e) {
			result = null;
		}
		return result;
	}
	
	public void zip(String [] args) throws IOException {
		Vector filestozip = new Vector();
		for (int i = 2; i  < args.length; i++) {
			String path = this.getAbsolutePath(args[i]);
			String name = this.getName(args[i]);
			if (name != null) {
				filestozip.add(this.getAllFiles(path, name));
			}
		}

		
		
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(args[1]));
		int ptr = 1;
		byte [] data = new byte[4096];
		out.setMethod(ZipOutputStream.DEFLATED);
		
		for (Iterator i = filestozip.iterator(); i.hasNext(); ) {
			Vector files = (Vector) i.next();
			ptr++;
			String absolutepath = this.getAbsolutePath(args[ptr]);
			for (Iterator j = files.iterator(); j.hasNext(); ) {
				try {
					String filename = (String) j.next();
					ZipEntry entry = new ZipEntry(filename);
					FileInputStream in = new FileInputStream(absolutepath + System.getProperty("file.separator") + filename);
					//System.out.println("Zip: " + filename);
					
					int read = 0;
					out.putNextEntry(entry);
					while((read = in.read(data, 0, data.length)) != -1) {
						out.write(data, 0, read);
					}
					out.closeEntry();
					in.close();
				} catch(Exception e) {
					
				}
			}
			
		}
			out.close();
		
	}
	
	public void unzip(String [] args) throws IOException {
		byte [] data = new byte[4096];
		int read;
		ZipFile zipfile = new ZipFile(args[1]);
		String directory = args[2];
		for (Enumeration e = zipfile.entries(); e.hasMoreElements(); ) {
			ZipEntry entry = (ZipEntry) e.nextElement();
			
			
			if (!entry.isDirectory()) {
				File file = new File(entry.getName());
				String relativdirectory = file.getParent(); 
				
				File dir = new File(directory + System.getProperty("file.separator") + ((relativdirectory != null)? relativdirectory : ""));
				dir.mkdirs();
			} else {
				File dir = new File(directory + System.getProperty("file.separator") + entry.getName());
				dir.mkdirs();
				continue;
			}
			
			InputStream zipin = zipfile.getInputStream(entry);
			OutputStream output = new BufferedOutputStream(new FileOutputStream(directory + System.getProperty("file.separator") + entry.getName()));
			
			while ((read = zipin.read(data)) >= 0) {
				output.write(data, 0, read);
			}
				
			output.close();
			zipin.close();
			
		}
		zipfile.close();
	}

}
