set outputdir ./build
file mkdir $outputdir

# Static part
create_project -in_memory

set_property part xc7z020clg484-1 [current_project]
set_property board em.avnet.com:zynq:zed:c [current_project]
set_property target_language VHDL [current_project]

source ps.tcl

generate_target all [get_files .srcs/sources_1/bd/ps/ps.bd]

read_vhdl platform.vhd

foreach dir [list ../../../mach/common/amba \
		  ../../../mach/epossoc/rtsnoc \
		  ../../../mach/common/amba_wishbone \
		  ../../../mach/common/rtsnoc_router \
		  ../../../mach/common/rtsnoc_router/router \
		  ../../../arch/mips32/plasma \
		  ../../../mach/common/simple_uart \
		  ../../../mach/common/gpio \
		  ../../../mach/common/simple_pic \
		  ../../../mach/common/simple_timer \
		  ../../../mach/common/comp_manager \
		  ../ \
		  ] {
    foreach file [list [glob -nocomplain $dir/*.v]] {
	if {$file != ""} {
	    read_verilog -library work $file
	}
    }

    foreach file [list [glob -nocomplain $dir/*.vhd]] {
	if {$file != ""} {
	    read_vhdl -library work $file
	}
    }
}

foreach file [list ../../../mach/common/coregen/ram_amba_128k.xco \
		   ../../../mach/common/coregen/ram_amba_1024k.xco \
		   ] {
    import_ip $file
    upgrade_ip [get_ips [file tail [file rootname $file]]]
    synth_ip [get_ips [file tail [file rootname $file]]]
}

synth_design -flatten_hierarchy rebuilt -top platform
write_checkpoint -force $outputdir/platform_synth.dcp

# Reconfigurable part
open_checkpoint $outputdir/platform_synth.dcp

# DTMF_Detector
foreach dir [list ../../../framework/catapult/top_level/dtmf_detector \
                  ../../../mach/common/rtsnoc_router \
                  ../../../framework/catapult/top_level/dtmf_detector/Catapult/toplevel.v1 \

                  ] {
    foreach file [list [glob -nocomplain $dir/*.v]] {
        if {$file != ""} {
            read_verilog -library work $file
        }
    }
}

synth_design -mode out_of_context -top DTMF_Detector_Node_RTL -generic X=0 -generic Y=0 -generic LOCAL_ADDR=7 -generic SIZE_X=1 -generic SIZE_Y=1 -generic SIZE_DATA=56 -generic RMI_MSG_SIZE=80 -generic IID_SIZE=8
write_checkpoint -force $outputdir/recfg_mod_dtmf_detector_synth.dcp

# ADPCM_Codec
foreach dir [list ../../../framework/catapult/top_level/adpcm_codec \
                  ../../../framework/catapult/top_level/adpcm_codec/Catapult/toplevel.v1 \

                  ] {
    foreach file [list [glob -nocomplain $dir/*.v]] {
        if {$file != ""} {
            read_verilog -library work $file
        }
    }
}

synth_design -mode out_of_context -top ADPCM_Codec_Node_RTL -generic X=0 -generic Y=0 -generic LOCAL_ADDR=7 -generic SIZE_X=1 -generic SIZE_Y=1 -generic SIZE_DATA=56 -generic RMI_MSG_SIZE=80 -generic IID_SIZE=8
write_checkpoint -force $outputdir/recfg_mod_adpcm_codec_synth.dcp

open_checkpoint $outputdir/platform_synth.dcp
read_xdc platform.xdc
read_checkpoint -cell u_rsp/u_recfg_mod $outputdir/recfg_mod_adpcm_codec_synth.dcp
opt_design
power_opt_design
place_design
phys_opt_design
route_design
write_checkpoint -force $outputdir/config_adpcm_codec_routed.dcp
write_checkpoint -force -cell u_rsp/u_recfg_mod $outputdir/recfg_mod_adpcm_codec_route_design.dcp
update_design -cell u_rsp/u_recfg_mod -black_box
write_checkpoint -force $outputdir/static_routed.dcp

open_checkpoint $outputdir/static_routed.dcp
lock_design -level routing
read_checkpoint -cell u_rsp/u_recfg_mod $outputdir/recfg_mod_dtmf_detector_synth.dcp
opt_design
place_design
route_design
write_checkpoint -force $outputdir/config_dtmf_detector_routed.dcp
write_checkpoint -force -cell u_rsp/u_recfg_mod $outputdir/recfg_mod_dtmf_detector_route_design.dcp

pr_verify -full_check $outputdir/config_dtmf_detector_routed.dcp $outputdir/config_adpcm_codec_routed.dcp -file $outputdir/pr_verify.log

read_checkpoint $outputdir/config_dtmf_detector_routed.dcp
write_bitstream -force $outputdir/config_dtmf_detector_routed

read_checkpoint $outputdir/config_adpcm_codec_routed.dcp
write_bitstream -force $outputdir/config_adpcm_codec_routed

exit
