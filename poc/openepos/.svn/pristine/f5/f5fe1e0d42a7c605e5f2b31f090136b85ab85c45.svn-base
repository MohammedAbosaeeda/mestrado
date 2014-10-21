package test;

import keso.core.*;
import java.util.*;

class TR_GPIO implements MemoryMappedObject {

	/* Output */
	MT_U32 gpio_out; 

	/* Output modification */
	MT_U32 gpio_omr;

	MT_U32 reserved_1;
	MT_U32 reserved_2;

	/* I/O control 0,4,8 and 12 (!) */
	MT_U32 gpio_iocr_0;
	MT_U32 gpio_iocr_1;
	MT_U32 gpio_iocr_2;
	MT_U32 gpio_iocr_3;

	MT_U32 reserved_3;

	/* Input */
	MT_U32 gpio_in;

	MT_U32 reserved_4;
	MT_U32 reserved_5;
	MT_U32 reserved_6;
	MT_U32 reserved_7;
	MT_U32 reserved_8;
	MT_U32 reserved_9;

	/* Pad driver mode */
	MT_U32 gpio_pdr;

	MT_U32 reserved_10;
	MT_U32 reserved_11;
	MT_U32 reserved_12;

	/* Emergency stop (not all ports) */
	MT_U32 gpio_esr;

	/* MT_U32             reserved5[43]; */
}

public class MemoryTypes extends Task {

	private void memoryObj() {
		Memory gpio = MemoryService.allocStaticDeviceMemory(0xf0000c00, 21*4);

		gpio.set32(0, 5);
		int value = gpio.get32(9);
	}

	private void memoryMappedObj() {
		TR_GPIO gpio = (TR_GPIO)MemoryService.mapStaticDeviceMemory(0xf0000c00,"test/TR_GPIO");

		gpio.gpio_out.set(5);
		int value = gpio.gpio_in.get();
	}

	/**
	 * Das geht alles ist jedoch teuer!
	 */
	private void memoryMappedObj_NotToDo() {
		TR_GPIO gpio = (TR_GPIO)MemoryService.mapStaticDeviceMemory(0xf0000c00,"test/TR_GPIO");

		MT_U32 out = gpio.gpio_out;
		out.set(5);

		Vector vec = new Vector();
		vec.addElement(out);
		((MT_U32)vec.elementAt(0)).set(6);

	}

	public void memoryAdjust() {
		Memory src = MemoryService.allocStaticMemory(128);
		Memory dst = MemoryService.allocStaticMemoryHandle();

		src.set8(32,(byte)42);

		MemoryService.adjustMemory(src,32,dst,32);

		int v = dst.get8(0);

		MemoryService.releaseMemory(dst);
	}

	public void launch() {

		memoryObj();

		memoryMappedObj();

		memoryMappedObj_NotToDo();

		memoryAdjust(); 

		TaskService.terminate();
	}
}
