create_pblock pblock_0
set_property HD.RECONFIGURABLE true [get_cells u0_recfg_mod]
add_cells_to_pblock [get_pblocks pblock_0]  [get_cells -quiet [list u0_recfg_mod]]
resize_pblock [get_pblocks pblock_0] -add {SLICE_X18Y0:SLICE_X25Y49}
resize_pblock [get_pblocks pblock_0] -add {DSP48_X1Y0:DSP48_X1Y19}
resize_pblock [get_pblocks pblock_0] -add {RAMB18_X1Y0:RAMB18_X1Y19}
resize_pblock [get_pblocks pblock_0] -add {RAMB36_X1Y0:RAMB36_X1Y9}
set_property RESET_AFTER_RECONFIG 1 [get_pblocks pblock_0]

#create_pblock pblock_1
#set_property HD.RECONFIGURABLE true [get_cells u1_recfg_mod]
#add_cells_to_pblock [get_pblocks pblock_1]  [get_cells -quiet [list u1_recfg_mod]]
#resize_pblock [get_pblocks pblock_1] -add {SLICE_X84Y29:SLICE_X109Y46}
#resize_pblock [get_pblocks pblock_1] -add {DSP48_X3Y12:DSP48_X4Y17}
#resize_pblock [get_pblocks pblock_1] -add RAMB18_X4Y12:RAMB18_X5Y17}
#resize_pblock [get_pblocks pblock_1] -add {RAMB36_X4Y6:RAMB36_X5Y8}
#set_property RESET_AFTER_RECONFIG 1 [get_pblocks pblock_1]
