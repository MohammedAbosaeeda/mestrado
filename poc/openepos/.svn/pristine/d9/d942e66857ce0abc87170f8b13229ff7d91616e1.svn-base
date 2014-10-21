#clear stuff
dataset clear
profile clear
vdel -all

#create work library
vlib work

#compile vhdl/verilog files for mixed simulation
vcom ../../mach/common/rtsnoc_router/router/ARBITER_MACHINE.vhd
vcom ../../mach/common/rtsnoc_router/router/COMPARE.vhd
vcom ../../mach/common/rtsnoc_router/router/crossbar.vhd
vcom ../../mach/common/rtsnoc_router/router/FLOW_CONTROL.vhd
vcom ../../mach/common/rtsnoc_router/router/INPUT_INTERFACE.vhd
vcom ../../mach/common/rtsnoc_router/router/OUTPUT_INTERFACE.vhd
vcom ../../mach/common/rtsnoc_router/router/PIPELINE.vhd
vcom ../../mach/common/rtsnoc_router/router/PRIORITY.vhd
vcom ../../mach/common/rtsnoc_router/router/QUEUE.vhd
vcom ../../mach/common/rtsnoc_router/router/ROUTER.vhd
vlog ../../mach/common/rtsnoc_router/rtsnoc_wishbone_proxy.v
vlog ../../mach/common/rtsnoc_router/rtsnoc_axi4lite_proxy.v
vlog ../../mach/common/rtsnoc_router/rtsnoc_axi4lite_reset.v
vlog ../../mach/common/rtsnoc_router/rtsnoc_echo_sm.v
vlog ../../mach/common/rtsnoc_router/rtsnoc_echo.v
vlog ../../mach/common/rtsnoc_router/rtsnoc_to_wishbone_master.v
vlog ../../mach/common/rtsnoc_router/wishbone_slave_to_rtsnoc.v
vlog ../../mach/common/rtsnoc_router/axi4lite_slave_to_rtsnoc.v
vlog ../../mach/common/rtsnoc_router/rtsnoc_to_axi4lite_master.v
vlog ../../mach/common/rtsnoc_router/rtsnoc_to_achannel.v

vlog ../../mach/common/catapult/rtl_mgc_ioport_v2001.v
vlog ../../mach/common/catapult/rtl_mgc_ioport.v

vlog ../../framework/catapult/top_level/mult/hls/rtl.v
vlog ../../framework/catapult/top_level/mult/rtl.v

#toplevel
vcom testbench_noc_to_catapult.vhd

#load/dump/run vhdl design
set StdArithNoWarnings 1
set NumericStdNoWarnings 1

vsim -L unisim -L unisims_ver -L xilinxcorelib  work.testbench_noc_to_catapult

vcd file trace_dec.vcd
vcd add -r -file trace_dec.vcd /*

set StdArithNoWarnings 1
set NumericStdNoWarnings 1

run -all
