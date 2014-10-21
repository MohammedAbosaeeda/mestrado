package lukas.device;

import keso.driver.tricore.tc1796b.TricorePWR;

public final class Stopwatch {
	public static final int NUM_CHANNELS=20;

	private static int[] startvalues = new int[NUM_CHANNELS];
	private static int[] stopvalues = new int[NUM_CHANNELS];

	public static final void init(int freq) {
		int modfreq = TricorePWR.getSystemFrequency();
		int desired_freq=1;
		int gpta_divider=0;
		int i;

		for(i=0; desired_freq<freq; i++)
			desired_freq *= 1000;

		for(i=0; i<14; i++) {
			if((gpta_divider = (modfreq >> i) / desired_freq) > 0x3FF)
				continue;
			break;
		}

		gpta_divider = 0x3FF - gpta_divider;

		TricorePWR.unlockSystemRegisters();
		TricoreGPTA.gpta.and32(
				TricoreGPTA.GPTAV2_OFS_MODULE_HDR+TricoreGPTA.MODULE_OFS_CLC, ~1);

		TricoreGPTA.gpta.and32(TricoreGPTA.GPTAV2_OFS_FDR, ~0x20000000);
		TricoreGPTA.gpta.and32(TricoreGPTA.GPTAV2_OFS_FDR, ~0x3000F3FF);
		TricoreGPTA.gpta.or32(
				TricoreGPTA.GPTAV2_OFS_FDR, (1 << 14) | gpta_divider);
		TricorePWR.lockSystemRegisters();

		TricoreGPTA.gpta.or32(
				TricoreGPTA.GPTAV2_OFS_GT+TricoreGPTA.GPTAV2_OFS_GTCTR, 0x20);
		TricoreGPTA.gpta.and32(TricoreGPTA.GPTAV2_OFS_CKBCTR, ~0xF);
		TricoreGPTA.gpta.or32(TricoreGPTA.GPTAV2_OFS_CKBCTR, i);

		TricoreGPTA.gpta.or32(TricoreGPTA.GPTAV2_OFS_EDCTR, 0x101);
	}

	public static void start(byte channel) {
		startvalues[channel] =
			TricoreGPTA.gpta.get32(
					TricoreGPTA.GPTAV2_OFS_GT+TricoreGPTA.GPTAV2_OFS_GTTIM)
			& 0x00FFFFFF;
	}

	public static int stop(byte channel) {
		stopvalues[channel] =
			TricoreGPTA.gpta.get32(
					TricoreGPTA.GPTAV2_OFS_GT+TricoreGPTA.GPTAV2_OFS_GTTIM)
			& 0x00FFFFFF;

		if(stopvalues[channel]>=startvalues[channel])
			return stopvalues[channel]-startvalues[channel];
		return (0x00FFFFFF-startvalues[channel]+stopvalues[channel]);
	}
}
