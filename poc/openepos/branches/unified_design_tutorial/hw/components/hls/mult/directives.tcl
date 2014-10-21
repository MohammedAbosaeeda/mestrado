project new

options set Output/OutputVHDL true
options set Output/OutputVerilog true
options set Output/OutputSystemC true
#options set Input/SearchPath ../../src

solution file add top_level.cc -type C++

directive set -REGISTER_IDLE_SIGNAL false
directive set -IDLE_SIGNAL {}
directive set -FSM_ENCODING none
directive set -REG_MAX_FANOUT 0
directive set -NO_X_ASSIGNMENTS true
directive set -SAFE_FSM false
directive set -RESET_CLEARS_ALL_REGS true
directive set -ASSIGN_OVERHEAD 0
directive set -DESIGN_GOAL area
directive set -OLD_SCHED false
directive set -PIPELINE_RAMP_UP true
directive set -COMPGRADE fast
directive set -SPECULATE true
directive set -MERGEABLE true
directive set -REGISTER_THRESHOLD 256
directive set -MEM_MAP_THRESHOLD 32
directive set -UNROLL no
directive set -CLOCK_OVERHEAD 20.000000
directive set -OPT_CONST_MULTS -1
directive set -PRESERVE_STRUCTS true
go analyze

directive set -CLOCK_NAME clk
directive set -START_FLAG {}
directive set -DONE_FLAG {}
directive set -TRANSACTION_DONE_SIGNAL false
directive set -DESIGN_HIERARCHY top_level
directive set -CLOCK_NAME clk
directive set -CLOCKS {clk {-CLOCK_PERIOD 10.0 -CLOCK_EDGE rising -CLOCK_UNCERTAINTY 0.0 -CLOCK_HIGH_TIME 5.0 -RESET_SYNC_NAME rst -RESET_ASYNC_NAME arst_n -RESET_KIND sync -RESET_SYNC_ACTIVE high -RESET_ASYNC_ACTIVE low -ENABLE_NAME {} -ENABLE_ACTIVE high}}
directive set -TECHLIBS {{Xilinx_accel_VIRTEX-6-2.lib Xilinx_accel_VIRTEX-6-2} {mgc_Xilinx-VIRTEX-6-2_beh_psr.lib {{mgc_Xilinx-VIRTEX-6-2_beh_psr part 6VLX240TFF1156}}} {ram_Xilinx-VIRTEX-6-2_PIPE.lib ram_Xilinx-VIRTEX-6-2_PIPE} {rom_Xilinx-VIRTEX-6-2.lib rom_Xilinx-VIRTEX-6-2} {rom_Xilinx-VIRTEX-6-2_SYNC_regin.lib rom_Xilinx-VIRTEX-6-2_SYNC_regin} {rom_Xilinx-VIRTEX-6-2_SYNC_regout.lib rom_Xilinx-VIRTEX-6-2_SYNC_regout} {ram_Xilinx-VIRTEX-6-2_RAMDB.lib ram_Xilinx-VIRTEX-6-2_RAMDB} {ram_Xilinx-VIRTEX-6-2_RAMSB.lib ram_Xilinx-VIRTEX-6-2_RAMSB}}
go compile

directive set /top_level/iid:rsc -MAP_TO_MODULE {[DirectInput]}
directive set /top_level/tx_ch:rsc -MAP_TO_MODULE mgc_ioport.mgc_out_stdreg_wait
directive set /top_level/rx_ch:rsc -MAP_TO_MODULE mgc_ioport.mgc_in_wire_wait
go architect

go extract
application exit 0
