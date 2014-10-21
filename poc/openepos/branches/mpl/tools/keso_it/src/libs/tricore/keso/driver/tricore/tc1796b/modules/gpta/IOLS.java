package keso.driver.tricore.tc1796b.modules.gpta;

import keso.core.*;

/**
 * Tricore I/O Line Sharing Unit.
 *
 * @author mike
 */

public final class IOLS {
	/**
	 * GPTA0 and GPTA1 have 52 mplex regs,
	 * LCTA2 has only 40
	 */
	private static Memory gptaMap;
	public static final int OFS_MRACTL  = 0x38;
	public static final int OFS_MRADIN  = 0x3c;
	public static final int OFS_MRADOUT = 0x40;

	private static int[] multiplexer_regs;

	public static final void program() {
		if(multiplexer_regs==null) return;
	
		gptaMap.and32(OFS_MRACTL, ~1); // clear MAEN
		gptaMap.or32(OFS_MRACTL, 2);   // set WCRES

		for(int i=multiplexer_regs.length-1; i>=0; i--)
			gptaMap.set32(OFS_MRADIN, multiplexer_regs[i]);

		gptaMap.or32(OFS_MRACTL, 1);   // set MAEN
		//print();
		gptaMap=null;
		multiplexer_regs=null;
	}
/*
	public static void print() {
		gptaMap.and32(OFS_MRACTL, ~1); // clear MAEN
		gptaMap.or32(OFS_MRACTL, 2);   // set WCRES

		for(int i=multiplexer_regs.length-1; i>=0; i--) {
			gptaMap.set32(OFS_MRADIN, multiplexer_regs[i]);
			test.DebugOut.print(i);
			test.DebugOut.print(": ");
			test.DebugOut.println(gptaMap.get32(OFS_MRADOUT));
		}

		gptaMap.or32(OFS_MRACTL, 1);   // set MAEN
	}
*/
	public static final void initclear(Memory gptaMap) {
		IOLS.gptaMap = gptaMap;
		if(gptaMap == GPTA.lcta2)
			multiplexer_regs = new int[40];
		else multiplexer_regs = new int[52];
	}

	public static final void init(Memory gptaMap) {
		initclear(gptaMap);
		gptaMap.and32(OFS_MRACTL, ~1); // clear MAEN
		gptaMap.or32(OFS_MRACTL, 2);   // set WCRES

		for(int i=multiplexer_regs.length-1; i>=0; i--) {
			gptaMap.set32(OFS_MRADIN, 0);
			multiplexer_regs[i] = gptaMap.get32(OFS_MRADOUT);
		}
	}

	private static final int LIMCRL0_INDEX=8;
	private static final int OMCRL0_INDEX=24;

	public static final byte IOG0= (byte) 0;
	public static final byte IOG1= (byte) 1;
	public static final byte IOG2= (byte) 2;
	public static final byte IOG3= (byte) 3;
	public static final byte IOG4= (byte) 4;
	public static final byte IOG5= (byte) 5;
	public static final byte IOG6= (byte) 6;
	public static final byte OG0= (byte) 7;
	public static final byte OG1= (byte) 8;
	public static final byte OG2= (byte) 9;
	public static final byte OG3= (byte) 10;
	public static final byte OG4= (byte) 11;
	public static final byte OG5= (byte) 12;
	public static final byte OG6= (byte) 13;

	public static final byte MASK_GTCG0= (byte) 0;
	public static final byte MASK_GTCG1= (byte) 0;
	public static final byte MASK_GTCG2= (byte) 0;
	public static final byte MASK_GTCG3= (byte) 0;
	public static final byte MASK_LTCG0= (byte) 1;
	public static final byte MASK_LTCG1= (byte) 1;
	public static final byte MASK_LTCG2= (byte) 1;
	public static final byte MASK_LTCG3= (byte) 1;
	public static final byte MASK_LTCG4= (byte) 2;
	public static final byte MASK_LTCG5= (byte) 2;
	public static final byte MASK_LTCG6= (byte) 2;
	public static final byte MASK_LTCG7= (byte) 2;

	/**
	 * Be sure to use one of the MASK_ constants for gtcmask.
	 */
	public static final void connectOutput(byte gtcmask, byte gtcline, byte iogno, byte iogline) {
		if(multiplexer_regs==null) return;

		
		int arrindex = OMCRL0_INDEX + iogno*2;
		int mask;
		
		// adjust if LCTA2
		if(multiplexer_regs.length==40) {
			if(iogno>4)	arrindex -= 10;
			else arrindex -= 8;
		}
		
		if(iogline > 3) {
			arrindex++; // OMCRHx
			iogline -= 4;
		}
		mask = ~(0xff << (iogline*8));
		multiplexer_regs[arrindex] &= mask;
		mask = gtcline | (gtcmask<<4);
		mask <<= iogline*8;
		multiplexer_regs[arrindex] |= mask;
	}

	public static final byte LIMG_MASK_IOG0= (byte) 0;
	public static final byte LIMG_MASK_IOG1= (byte) 0;
	public static final byte LIMG_MASK_IOG2= (byte) 0;
	public static final byte LIMG_MASK_IOG3= (byte) 0;
	public static final byte LIMG_MASK_IOG4= (byte) 1;
	public static final byte LIMG_MASK_IOG5= (byte) 1;
	public static final byte LIMG_MASK_IOG6= (byte) 1;
	public static final byte LIMG_MASK_GTCG0= (byte) 2;
	public static final byte LIMG_MASK_GTCG1= (byte) 2;
	public static final byte LIMG_MASK_GTCG2= (byte) 2;
	public static final byte LIMG_MASK_GTCG3= (byte) 2;
	public static final byte LIMG_MASK_CLOCK= (byte) 3;
	public static final byte LIMG_MASK_PDL= (byte) 4;
	public static final byte LIMG_MASK_INT= (byte) 4;
	public static final byte LIMG_MASK_FPC= (byte) 4;

	public static final void connectLTCInput(byte iogmask, byte iogline, byte ltcno, byte ltcline) {
		if(multiplexer_regs==null) return;

		int arrindex = LIMCRL0_INDEX + ltcno*2;
		int mask;
		if(ltcline > 3) {
			arrindex++; // LIMCRHx
			ltcline -= 4;
		}

		// adjust if LCTA2
		if(multiplexer_regs.length==40)
			arrindex -= 8;

		mask = ~(0xff << (ltcline*8));
		multiplexer_regs[arrindex] &= mask;
		mask = iogline | (iogmask<<4) | 0x80;
		mask <<= ltcline * 8;	
		multiplexer_regs[arrindex] |= mask;
	}
}
