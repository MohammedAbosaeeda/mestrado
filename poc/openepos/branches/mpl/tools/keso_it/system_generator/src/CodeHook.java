package josek;
import java.io.*;

public interface CodeHook {
	public boolean fire(String condition);
	public void fireImmediately();
	public void changedFile(BufferedWriter file);
}

