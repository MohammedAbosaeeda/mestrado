# Reconfigurable partition
create_pblock pblock_1
set_property HD.RECONFIGURABLE true [get_cells u_recfg_mod]
add_cells_to_pblock [get_pblocks pblock_1]  [get_cells -quiet [list u_recfg_mod]]
resize_pblock [get_pblocks pblock_1] -add {SLICE_X18Y0:SLICE_X25Y49}
resize_pblock [get_pblocks pblock_1] -add {DSP48_X1Y0:DSP48_X1Y19}
resize_pblock [get_pblocks pblock_1] -add {RAMB18_X1Y0:RAMB18_X1Y19}
resize_pblock [get_pblocks pblock_1] -add {RAMB36_X1Y0:RAMB36_X1Y9}
set_property RESET_AFTER_RECONFIG 1 [get_pblocks pblock_1]

# LED
set_property PACKAGE_PIN T22 [get_ports led]
set_property IOSTANDARD LVCMOS33 [get_ports led]
