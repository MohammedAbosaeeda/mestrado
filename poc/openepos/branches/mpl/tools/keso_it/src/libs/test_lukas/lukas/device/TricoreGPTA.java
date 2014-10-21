package lukas.device;

import keso.core.*;
public final class TricoreGPTA {
	public static final int GPTA0_BASE=0xf0001800;
	public static final int N_GPTA=2;
	public static final int GPTAV2_SIZE=512*4;

	/* tricore_gptav2_t members */
	public static final int GPTAV2_OFS_MODULE_HDR=0;
	public static final int GPTAV2_OFS_FDR = 3*4;
	public static final int GPTAV2_OFS_CKBCTR=54*4;
	public static final int GPTAV2_OFS_GT=56*4;
	public static final int GPTAV2_OFS_EDCTR=256*4;

	/* tricore_gpta2gt_t members */
	public static final int GPTAV2_OFS_GTCTR=0;
	public static final int GPTAV2_OFS_GTTIM=2*4;

	/* tricore_module_t members */
	public static final int MODULE_OFS_CLC=0;

	public static final Memory gpta =
		MemoryService.allocStaticDeviceMemory(GPTA0_BASE, GPTAV2_SIZE*2);
}
