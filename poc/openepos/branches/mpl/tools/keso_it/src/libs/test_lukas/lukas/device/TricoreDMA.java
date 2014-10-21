package lukas.device;

import keso.core.*;
public final class TricoreDMA {
	public static final int DMA_BASE=0xf0003c00;
	public static final int DMAT_SIZE=192*4;
	public static final int DMA_OFS_SYSSRC=163*4;

	public static final Memory dma  =
		MemoryService.allocStaticDeviceMemory(DMA_BASE, DMAT_SIZE);
	public static final int DMA_SYSSRC_NtoI(int n) { return 4-n; }
}
