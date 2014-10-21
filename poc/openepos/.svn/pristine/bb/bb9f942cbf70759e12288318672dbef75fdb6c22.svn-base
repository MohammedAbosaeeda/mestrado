#compile vhdl files for mixed simulation
vcom ../scheduler/base_files/*.vhd
vcom ../synthesis/*.vhd

#export vhdl modules for systemc
scgenmod PriorityScheduler_sc > priority_scheduler_sc.h
scgenmod Scheduler > scheduler.h

#compile top level systemc file
#scheduler
sccom -g ../scheduler/toplevel_simulation.cc
sccom -link

#fir
sccom -g ../fir/fir_rtl/fir_data.cc
sccom -g ../fir/fir_rtl/fir_fsm.cc
sccom -g ../fir/common/fir_coefs.cc
sccom -g ../fir/fir_hl/fir.cc
sccom -g ../fir/testbench/display.cc
sccom -g ../fir/testbench/stimulus.cc
sccom -g ../fir/testbench/testbench.cc
sccom -g ../fir/toplevel_simulation.cc
sccom -link

