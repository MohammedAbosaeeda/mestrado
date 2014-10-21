set outputdir ./build
file mkdir $outputdir

# Static part
read_bd ps/ps.bd

set_property part xc7z020clg484-1 [current_project]
set_property board em.avnet.com:zynq:zed:c [current_project]

generate_target all [get_files ps/ps.bd]

read_vhdl platform.vhd

foreach dir [list ../../../mach/common/amba \
                  ../../../mach/common/amba_wishbone \
                  ../../../mach/common/rtsnoc_router \
                  ../../../mach/common/rtsnoc_router/router \
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

synth_design -flatten_hierarchy rebuilt -top platform
write_checkpoint -force $outputdir/static_synth.dcp

# Reconfigurable part
synth_design -mode out_of_context -top rtsnoc_echo -generic SOC_SIZE_X=1 -generic SOC_SIZE_Y=1 -generic NOC_DATA_WIDTH=56 -generic P0_ADDR=6 -generic P1_ADDR=4
write_checkpoint -force $outputdir/rtsnoc_echo_synth.dcp

open_checkpoint $outputdir/static_synth.dcp
read_xdc platform.xdc
read_checkpoint -cell u0_recfg_mod $outputdir/rtsnoc_echo_synth.dcp
opt_design
place_design
route_design
write_checkpoint -force $outputdir/u0_rtsnoc_echo_routed.dcp

update_design -cell u0_recfg_mod -black_box
write_checkpoint -force $outputdir/static_routed.dcp

write_bitstream -force static

#open_checkpoint $outputdir/static_routed.dcp
#lock_design -level routing
#read_checkpoint -cell u1_recfg_mod $outputdir/rtsnoc_echo_synth.dcp
##opt_design
#place_design
#route_design
#write_checkpoint -force $outputdir/config_add_routed.dcp
#write_checkpoint -force -cell u1_recfg_mod $outputdir/recfg_mod_add_route_design.dcp

#pr_verify -full_check $outputdir/config_add_routed.dcp $outputdir/u0_rtsnoc_echo_routed.dcp -file $outputdir/pr_verify.log

#read_checkpoint $outputdir/config_add_routed.dcp
#write_bitstream -force config_add_routed

read_checkpoint $outputdir/u0_rtsnoc_echo_routed.dcp
write_bitstream -force u0_rtsnoc_echo
