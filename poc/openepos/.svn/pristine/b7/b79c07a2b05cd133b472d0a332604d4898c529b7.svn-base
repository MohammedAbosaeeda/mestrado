/**(c)

  Copyright (C) 2005-2007 Christian Wawersich 

  This file is part of the KESO Operating System.

  It is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

  Please contact wawi.fau.de for more info.

  (c)**/

package benchmark;

import keso.core.Task;
import keso.core.TimeStamp;
import test.DebugOut;

public class BogoMIPS extends Task {

	private final static int WARMUP_CYCLES = 30000;
	private final static int BENCHMARK_TURNS = 50000000;
	private final static int BENCHMARK_TURNS2 = 100000;

	private int bench_field = 0;
	private static int bench_sfield =4;

	public int vcall(int i) {
		return i;
	}

	// 35 instructions in the loop
	private final static int BENCHMARK_INS = 35;
	private long compute_bogo_mips(int turns) {
		Object bobj;
		
		long start = System.currentTimeMillis();

		for (int i=0;i<turns;i++) {

			// get_field aload_0 iload iadd istore
			int j = i + bench_field;

			// aload_0 put_field iload
			bench_field = j;

			if (bench_sfield==(j&4)) {
				bobj =  new BogoMIPS();
			} else {
				bobj = null;
			}

			if (bobj!=null && bobj instanceof Task) {
				j = ((BogoMIPS)bobj).vcall(j);
			}
		}

		return System.currentTimeMillis()-start;
	}

	// 35 instructions in the loop
	public void benchmark2() {
		Object bobj;
		
		DebugOut.println("run benchmark 2"); 

		long start = TimeStamp.readCounter();

		for (int i=0;i<BENCHMARK_TURNS2;i++) {

			// get_field aload_0 iload iadd istore
			int j = i + bench_field;

			// aload_0 put_field iload
			bench_field = j;

			if (bench_sfield==(j&4)) {
				bobj =  new BogoMIPS();
			} else {
				bobj = null;
			}

			if (bobj!=null && bobj instanceof Task) {
				j = ((BogoMIPS)bobj).vcall(j);
			}
		}

		long time = TimeStamp.toMicros(TimeStamp.readCounter()-start);
		
		DebugOut.print("time: ");
		DebugOut.print(time);
		DebugOut.println(" micros");
	}

	public void launch() {

		for (int i=0;i<WARMUP_CYCLES;i++) 
			compute_bogo_mips(1);

		// not implemented in KESO!
		//try { Thread.sleep(10); } catch (Exception ex) {} 

		DebugOut.println(" started");
		long time = compute_bogo_mips(BENCHMARK_TURNS);
		DebugOut.print("time: ");
		DebugOut.print(time);
		DebugOut.print(" ms");
		if (time!=0) { 

			long bogo_mips = BENCHMARK_INS * BENCHMARK_TURNS / (1000*time);

			/*
			   System.err.print("time: ");
			   System.err.print(time);
			   System.err.print(" ms = ");
			   System.err.print(bogo_mips);
			   System.err.println(" mips");
			   */

			DebugOut.print(" (");
			DebugOut.print(bogo_mips);
			DebugOut.print(" mips)");
		}
		DebugOut.println();
	}

	public static void main(String args[]) {
		BogoMIPS bench = new BogoMIPS();
		bench.launch();
	}
}
