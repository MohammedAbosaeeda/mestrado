package josek;
import java.io.*;

public class CodeHook_osc_ArrayOptimizerDef extends CodeHook_Standard {
	public void fireImmediately() {
		for (int i = 0; i < conf.getArrayOptimizerCount(); i++) {
			if (conf.getArrayOptimizer(i).generateArray()) writeSingStr();
		}
	}
}
