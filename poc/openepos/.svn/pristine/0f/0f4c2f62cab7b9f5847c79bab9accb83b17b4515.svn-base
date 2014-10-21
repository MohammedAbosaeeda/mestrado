package josek;
import java.io.*;

public class CodeHook_osh_ArrayOptimizerDef extends CodeHook_Standard {
	public void fireImmediately() {
		for (int i = 0; i < conf.getArrayOptimizerCount(); i++) {
			conf.getArrayOptimizer(i).generateDefine();
			writeSingStr();
		}
	}
}
