// EPOS-- LEON2 Ethernet NIC Mediator Initialization

#include <system/kmalloc.h>
#include <machine.h>
#include <mach/leon2/ethernet.h>

__BEGIN_SYS

int LEON2_Ethernet::init(unsigned int unit, System_Info * si)
{
    db<Init, LEON2_Ethernet>(TRC) << "LEON2_Ethernet::init(unit=" << unit << ")\n";

//     // Scan the PCI bus for device
//     PC_PCI::Locator loc = PC_PCI::scan(PCI_VENDOR_ID, PCI_DEVICE_ID, unit);
//     if(!loc) {
// 	db<Init, LEON2_Ethernet>(WRN) << "LEON2_Ethernet::init: PCI scan failed!\n";
// 	return 1;
//     }

//     // Try to enable IO regions and bus master
//     PC_PCI::command(loc, PC_PCI::command(loc) 
// 		    | PC_PCI::COMMAND_IO | PC_PCI::COMMAND_MASTER);

//     // Get the config space header and check it we got IO and MASTER
//     PC_PCI::Header hdr;
//     PCI::header(loc, &hdr);
//     if(!hdr) {
// 	db<Init, LEON2_Ethernet>(WRN) << "LEON2_Ethernet::init: PCI header failed!\n";
// 	return 1;
//     }
//     db<Init, LEON2_Ethernet>(INF) << "LEON2_Ethernet::init: PCI header=" << hdr << "}\n";
//     if(!(hdr.command & PC_PCI::COMMAND_IO))
// 	db<Init, LEON2_Ethernet>(WRN) << "LEON2_Ethernet::init: I/O unaccessible!\n";
//     if(!(hdr.command & PC_PCI::COMMAND_MASTER))
// 	db<Init, LEON2_Ethernet>(WRN) << "LEON2_Ethernet::init: not master capable!\n";

//     // Get I/O base port
//     IO_Port io_port = hdr.region[PCI_REG_IO].phy_addr;
//     db<Init, LEON2_Ethernet>(INF) << "LEON2_Ethernet::init: I/O port at " 
// 			   << (void *)(int)io_port << "\n";

//     // Get I/O irq
//     IO_Irq irq = hdr.interrupt_line;
//     db<Init, LEON2_Ethernet>(INF) << "LEON2_Ethernet::init: PCI interrut pin "
// 			   << hdr.interrupt_pin << " routed to IRQ "
// 			   << hdr.interrupt_line << "\n";

//     // Allocate a DMA Buffer for init block, rx and tx rings
//     DMA_Buffer * dma_buf = new(kmalloc(sizeof(MMU::DMA_Buffer))) 
// 	DMA_Buffer(DMA_BUFFER_SIZE);

//     // Initialize the device
//     LEON2_Ethernet * dev = new (kmalloc(sizeof(LEON2_Ethernet)))
// 	LEON2_Ethernet(unit, io_port, irq, dma_buf);

	// Register the device
// 	new (kmalloc(sizeof(PC_Device)))
// 	    PC_Device(Type2Id<LEON2_Ethernet>::ID, i, nic, PC::irq2int(irq));
 //    _devices[unit].in_use = false;
//     _devices[unit].device = dev;
//     _devices[unit].interrupt = Machine::irq2int(irq);
    
//     // Install interrupt handler
//     PC::int_vector(PC::irq2int(irq), &int_handler);

//     // Enable interrupts for device
//     IC::enable(irq);

    return 0;
}

__END_SYS
