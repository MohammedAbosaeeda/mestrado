/*! @file
 *  @brief EPOS ML310 Run-Time %System Information
 *
 *  CVS Log for this file:
 *  \verbinclude include/mach/ml310/info_h.log
 */
#ifndef __ml310_info_h
#define __ml310_info_h

#include <system/info.h>

__BEGIN_SYS
/// This class defines the run-time information, used during the system initialization process.
template<>
struct System_Info<ML310>
{
private:
    typedef unsigned int LAddr; ///< Defines the Logical Address type
    typedef unsigned int PAddr; ///< Defines the Physical Address type
    typedef unsigned int Size;  ///< Defines the Size type

public:
    /// Defines the information we have at boot time (built up by MKBI)
    struct Boot_Map
    {
	PAddr mem_base;          ///< Memory base address
	PAddr mem_top;           ///< Memory top address
	PAddr io_mem_base;       ///< I/O Memory base address
	PAddr io_mem_top;        ///< I/O Memory top address
	short node_id;           ///< Local node id in SAN (-1 => RARP) 
	short n_nodes;           ///< Number of nodes in SAN (-1 => dynamic) 
	Size img_size;           ///< Boot image size (in bytes)
	int setup_offset;        ///< Image offsets (-1 => not present) 
	int init_offset; 	 ///< Init offsets (-1 => not present) 
	int system_offset;       ///< System offsets (-1 => not present) 
	int application_offset;  ///< Application offsets (-1 => not present) 
	int extras_offset;       ///< Extras offsets (-1 => not present) 
    };

    /// Defines the Physical Memory Map (This map is built up by SETUP)
    struct Physical_Memory_Map
    {
	PAddr mem_base;      ///< Memory base address
	PAddr mem_top;       ///< Memory top address
	PAddr io_mem_base;   ///< I/O Memory base address
	PAddr io_mem_top;    ///< I/O Memory top address
 	PAddr ext_base;      ///< Boot Image EXTRA segment base address
 	PAddr ext_top;       ///< Boot Image EXTRA segment top address
	PAddr int_vec;       ///< Interrupt Vector
	PAddr gdt;           ///< GDT
	PAddr sys_pt;        ///< System Page Table
	PAddr sys_pd;        ///< System Page Directory
	PAddr sys_info;      ///< System Info
	PAddr phy_mem_pts;   ///< Page tables to map the whole physical memory
	PAddr io_mem_pts;    ///< Page tables to map the I/O address space 
	PAddr sys_code;      ///< OS Code Segment
	PAddr sys_data;      ///< OS Data Segment
	PAddr sys_stack;     ///< OS Stack Segment
	PAddr free1_base;    ///< First free memory chunk base address
	PAddr free1_top;     ///< First free memory chunk top address
	PAddr free2_base;    ///< Second free memory chunk base address
	PAddr free2_top;     ///< Second free memory chunk top address
    };

    /// Defines the Logical Memory Map (This map is built up by SETUP)
    struct Logical_Memory_Map
    {
	LAddr app_entry;     ///< First application's entry point
    };

    /// Defines the Load Map (This map is built up by SETUP)
    struct Load_Map {
	bool  has_stp;	    ///< Flags the presence of a loadable setup in the image.
	bool  has_ini;	    ///< Flags the presence of an loadable init in the image.
	bool  has_sys;	    ///< Flags the presence of a loadable system in the image.
	bool  has_app;	    ///< Flags the presence of a loadable application in the image.
	bool  has_ext;	    ///< Flags the presence of a loadable extras in the image.
	LAddr stp_entry;    ///< Setup entry point address
	Size  stp_segments; ///< Number of segments in setup
	LAddr stp_code;     ///< Address of setup code section
	Size  stp_code_size;///< Size of setup code section
	LAddr stp_data;     ///< Address of setup data section
	Size  stp_data_size;///< Size of setup data section
	LAddr ini_entry;    ///< Init entry point address
	Size  ini_segments; ///< Number of segments in init
	LAddr ini_code;     ///< Address of init code section
	Size  ini_code_size;///< Size of system code section
	LAddr ini_data;     ///< Address of init data section
	Size  ini_data_size;///< Size of init data section
	LAddr sys_entry;    ///< System entry point address
	Size  sys_segments; ///< Number of segments in system
	LAddr sys_code;     ///< Address of system code section
	Size  sys_code_size;///< Size of system code section
	LAddr sys_data;     ///< Address of system data section
	Size  sys_data_size;///< Size of system data section
	LAddr sys_stack;    ///< Address of system stack
	Size  sys_stack_size;///< Size of system stack
	LAddr app_entry;    ///< Application entry point address
	Size  app_segments; ///< Number of segments in application
	LAddr app_code;     ///< Address of application code section
	Size  app_code_size;///< Size of application code section
	LAddr app_data;     ///< Address of application data section
	Size  app_data_size;///< Size of application data section
	PAddr ext;          ///< Physical address of the extras
	Size  ext_size;     ///< Size of the extras
    };

public:
    Boot_Map bm;
    Physical_Memory_Map pmm;
    Logical_Memory_Map lmm;
    Load_Map lm;
};

__END_SYS

#endif
