--------------------------------------------------------------------------------
-- Company: LISHA
-- Engineer: Hugo Marcondes
--
-- Create Date:   09:28:33 07/08/2007
-- Design Name:   PriorityScheduler
-- Module Name:   E:/UFSC/Mestrado/Implementation/Priority_Scheduler/priorityscheduler_testbench.vhd
-- Project Name:  priority_scheduler
-- 
-- VHDL Test Bench Created by ISE for module: PriorityScheduler
--------------------------------------------------------------------------------
LIBRARY ieee;
USE ieee.std_logic_1164.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;
USE ieee.std_logic_unsigned.all;
USE ieee.numeric_std.ALL;


entity scheduler_testbench_vhd is
end scheduler_testbench_vhd;

architecture behavior of scheduler_testbench_vhd is 

   constant PERIOD : time := 20 ns;
   constant DUTY_CYCLE : real := 0.5;
   constant OFFSET : time := 200 ns;
	constant C_CMD_CREATE:			 std_logic_vector(0 to 3) := "0001";
	constant C_CMD_DESTROY:			 std_logic_vector(0 to 3) := "0010";
	constant C_CMD_INSERT:			 std_logic_vector(0 to 3) := "0011";
	constant C_CMD_REMOVE:			 std_logic_vector(0 to 3) := "0100";
	constant C_CMD_REMOVE_HEAD:	 std_logic_vector(0 to 3) := "0101";
	constant C_CMD_UPDATE_RUNNING: std_logic_vector(0 to 3) := "0110";
	constant C_CMD_SET_QUANTUM:    std_logic_vector(0 to 3) := "0111";	
	constant C_CMD_ENABLE:			 std_logic_vector(0 to 3) := "1000";
	constant C_CMD_DISABLE:  		 std_logic_vector(0 to 3) := "1001";
	constant C_CMD_GETID:			 std_logic_vector(0 to 3) := "1011";
   constant C_CMD_GETHEAD:			 std_logic_vector(0 to 3) := "1100";
	constant C_CMD_NOP:  			 std_logic_vector(0 to 3) := "0000";

	-- Component Declaration for the Unit Under Test (UUT)
	component Scheduler
	port(
		p_clk : in std_logic;
		p_reset : in std_logic;
		p_command : in std_logic_vector(0 to 3);
		p_priority : in std_logic_vector(0 to 15);
		p_parameter : in std_logic_vector(0 to 31);
		p_interrupt : out std_logic;          
		p_return : out std_logic_vector(0 to 31);
		p_status : out std_logic_vector(0 to 3)
		);
	end component;

	--Inputs
	signal s_clk :  std_logic := '0';
	signal s_reset :  std_logic := '0';
	signal s_interrupt :  std_logic := '0';
	signal s_command :  std_logic_vector(0 to 3) := (others=>'0');
	signal s_priority :  std_logic_vector(0 to 15) := (others=>'0');
	signal s_parameter :  std_logic_vector(0 to 31) := (others=>'0');

	--Outputs
	signal s_return :  std_logic_vector(0 to 31);
	signal s_status :  std_logic_vector(0 to 3);

begin
	-- Instantiate the Unit Under Test (UUT)
	uut: Scheduler port map(
		p_clk => s_clk,
		p_reset => s_reset,
		p_command => s_command,
		p_priority => s_priority,
		p_parameter => s_parameter,
		p_return => s_return,
		p_status => s_status,
		p_interrupt => s_interrupt
	);
	
	--Clock Generator.
   clock_process: process
   begin
		wait for OFFSET;
		clock_loop : loop
			 s_clk <= '0';
			 wait for (PERIOD - (PERIOD * DUTY_CYCLE));
			 s_clk <= '1';
			 wait for (PERIOD * DUTY_CYCLE);
		end loop clock_loop;
   end process;

	test_bench: process
	begin
		-- Wait 100 ns for global reset to finish
		wait for OFFSET;
		wait for 5 ns;
		
		-- Place stimulus here
		s_reset <= '1';
		wait for PERIOD; -- Wait next cycle
		-- Insert several Threads
		s_reset <= '0';
		wait for PERIOD; -- Wait next cycle
		
		-- Setup reschedule
		s_parameter <= conv_std_logic_vector(10, 32);
		s_command <= C_CMD_SET_QUANTUM;
		wait for PERIOD;
		s_command <= C_CMD_NOP;
		wait until s_status(0) = '1'; -- Wait until command done;
		wait for PERIOD;
		s_command <= C_CMD_ENABLE;
		wait for PERIOD;
		s_command <= C_CMD_NOP;
		wait until s_status(0) = '1'; -- Wait until command done;
		wait for PERIOD;
		
		
		-- Create Thread(40,4)
		s_parameter <= conv_std_logic_vector(40, 32);
		s_priority <= conv_std_logic_vector(4, 16);
		s_command <= C_CMD_CREATE;
		wait for PERIOD;
		s_command <= C_CMD_NOP;
		wait until s_status(0) = '1'; -- Wait until command done;
		wait for PERIOD;
      s_parameter <= conv_std_logic_vector(8, 32);
		s_priority <= conv_std_logic_vector(4, 16);
		s_command <= C_CMD_INSERT;
		wait for PERIOD;
		s_command <= C_CMD_NOP;
		wait until s_status(0) = '1'; -- Wait until command done;
		wait for PERIOD;
		
		-- Create Thread(10,1)
		s_parameter <= conv_std_logic_vector(10, 32);
		s_priority <= conv_std_logic_vector(1, 16);
		s_command <= C_CMD_CREATE;
		wait for PERIOD;
		s_command <= C_CMD_NOP;
		wait until s_status(0) = '1'; -- Wait until command done;
		wait for PERIOD;
		s_parameter <= conv_std_logic_vector(7, 32);
		s_priority <= conv_std_logic_vector(1, 16);
		s_command <= C_CMD_INSERT;
		wait for PERIOD;
		s_command <= C_CMD_NOP;
		wait until s_status(0) = '1'; -- Wait until command done;
		wait for PERIOD;

		-- Create Thread(80,8)
		s_parameter <= conv_std_logic_vector(80, 32);
		s_priority <= conv_std_logic_vector(8, 16);
		s_command <= C_CMD_CREATE;
		wait for PERIOD;
		s_command <= C_CMD_NOP;
		wait until s_status(0) = '1'; -- Wait until command done;
		wait for PERIOD;
		s_parameter <= conv_std_logic_vector(6, 32);
		s_priority <= conv_std_logic_vector(8, 16);
		s_command <= C_CMD_INSERT;
		wait for PERIOD;
		s_command <= C_CMD_NOP;
		wait until s_status(0) = '1'; -- Wait until command done;
		wait for PERIOD;

		-- Create Thread(50,5)
		s_parameter <= conv_std_logic_vector(50, 32);
		s_priority <= conv_std_logic_vector(5, 16);
		s_command <= C_CMD_CREATE;
		wait for PERIOD;
		s_command <= C_CMD_NOP;
		wait until s_status(0) = '1'; -- Wait until command done;
		wait for PERIOD;
		s_parameter <= conv_std_logic_vector(5, 32);
		s_priority <= conv_std_logic_vector(5, 16);
		s_command <= C_CMD_INSERT;
		wait for PERIOD;
		s_command <= C_CMD_NOP;
		wait until s_status(0) = '1'; -- Wait until command done;
		wait for PERIOD;


		-- Create Thread(30,3)
		s_parameter <= conv_std_logic_vector(30, 32);
		s_priority <= conv_std_logic_vector(3, 16);
		s_command <= C_CMD_CREATE;
		wait for PERIOD;
		s_command <= C_CMD_NOP;
		wait until s_status(0) = '1'; -- Wait until command done;
		wait for PERIOD;
		s_parameter <= conv_std_logic_vector(4, 32);
		s_priority <= conv_std_logic_vector(3, 16);
		s_command <= C_CMD_INSERT;
		wait for PERIOD;
		s_command <= C_CMD_NOP;
		wait until s_status(0) = '1'; -- Wait until command done;
		wait for PERIOD;

		
		-- Create Thread(60,6)
		s_parameter <= conv_std_logic_vector(60, 32);
		s_priority <= conv_std_logic_vector(6, 16);
		s_command <= C_CMD_CREATE;
		wait for PERIOD;
		s_command <= C_CMD_NOP;
		wait until s_status(0) = '1'; -- Wait until command done;
		wait for PERIOD;
		s_parameter <= conv_std_logic_vector(3, 32);
		s_priority <= conv_std_logic_vector(6, 16);
		s_command <= C_CMD_INSERT;
		wait for PERIOD;
		s_command <= C_CMD_NOP;
		wait until s_status(0) = '1'; -- Wait until command done;
		wait for PERIOD;

		
		-- Create Thread(20,2)
		s_parameter <= conv_std_logic_vector(20, 32);
		s_priority <= conv_std_logic_vector(2, 16);
		s_command <= C_CMD_CREATE;
		wait for PERIOD;
		s_command <= C_CMD_NOP;
		wait until s_status(0) = '1'; -- Wait until command done;
		wait for PERIOD;
		s_parameter <= conv_std_logic_vector(2, 32);
		s_priority <= conv_std_logic_vector(2, 16);
		s_command <= C_CMD_INSERT;
		wait for PERIOD;
		s_command <= C_CMD_NOP;
		wait until s_status(0) = '1'; -- Wait until command done;
		wait for PERIOD;

		
		-- Create Thread(70,7)
		s_parameter <= conv_std_logic_vector(70, 32);
		s_priority <= conv_std_logic_vector(7, 16);
		s_command <= C_CMD_CREATE;
		wait for PERIOD;
		s_command <= C_CMD_NOP;
		wait until s_status(0) = '1'; -- Wait until command done;
		wait for PERIOD;
		s_parameter <= conv_std_logic_vector(1, 32);
		s_priority <= conv_std_logic_vector(7, 16);
		s_command <= C_CMD_INSERT;
		wait for PERIOD;
		s_command <= C_CMD_NOP;
		wait until s_status(0) = '1'; -- Wait until command done;
		wait for PERIOD;
		
		-- Create Thread(90,9) -- Should exit with error
		s_parameter <= conv_std_logic_vector(90, 32);
		s_priority <= conv_std_logic_vector(9, 16);
		s_command <= C_CMD_CREATE;
		wait for PERIOD;
		s_command <= C_CMD_NOP;
		wait until s_status(0) = '1'; -- Wait until command done;
		wait for PERIOD;
		
		-- Remove Thread(5)
		s_parameter <= conv_std_logic_vector(5, 32);
		s_command <= C_CMD_DESTROY;
		wait for PERIOD;
		s_command <= C_CMD_NOP;
		wait until s_status(0) = '1'; -- Wait until command done;
		wait for PERIOD;

		-- Create Thread(90,9) -- Now should be OK!
		s_parameter <= conv_std_logic_vector(90, 32);
		s_priority <= conv_std_logic_vector(9, 16);
		s_command <= C_CMD_CREATE;
		wait for PERIOD;
		s_command <= C_CMD_NOP;
		wait until s_status(0) = '1'; -- Wait until command done;
		wait for PERIOD;
		s_parameter <= conv_std_logic_vector(5, 32);
		s_priority <= conv_std_logic_vector(9, 16);
		s_command <= C_CMD_INSERT;
		wait for PERIOD;
		s_command <= C_CMD_NOP;
		wait until s_status(0) = '1'; -- Wait until command done;
		wait for PERIOD;

		-- Remove HEAD element
		s_command <= C_CMD_REMOVE_HEAD;
		wait for PERIOD;
		s_command <= C_CMD_NOP;
		wait until s_status(0) = '1'; -- Wait until command done;
		wait for PERIOD;
		
		-- Remove Thread(4)
		s_parameter <= conv_std_logic_vector(4, 32);
		s_command <= C_CMD_REMOVE;
		wait for PERIOD;
		s_command <= C_CMD_NOP;
		wait until s_status(0) = '1'; -- Wait until command done;
		wait for PERIOD;

		-- Remove tail Thread(5)
      s_parameter <= conv_std_logic_vector(5, 32);
		s_command <= C_CMD_REMOVE;
		wait for PERIOD;
		s_command <= C_CMD_NOP;
		wait until s_status(0) = '1'; -- Wait until command done;
		wait for PERIOD;
		
		-- Remove Thread(8)
      s_parameter <= conv_std_logic_vector(8, 32);
		s_command <= C_CMD_REMOVE;
		wait for PERIOD;
		s_command <= C_CMD_NOP;
		wait until s_status(0) = '1'; -- Wait until command done;
		wait for PERIOD;

		-- Insert head Thread(7)
      s_parameter <= conv_std_logic_vector(7, 32);
		s_priority <= conv_std_logic_vector(1, 16);
		s_command <= C_CMD_INSERT;
		wait for PERIOD;
		s_command <= C_CMD_NOP;
		wait until s_status(0) = '1'; -- Wait until command done;
		wait for PERIOD;
		
		-- Insert Thread(8)
      s_parameter <= conv_std_logic_vector(8, 32);
		s_priority <= conv_std_logic_vector(4, 16);
		s_command <= C_CMD_INSERT;
		wait for PERIOD;
		s_command <= C_CMD_NOP;
		wait until s_status(0) = '1'; -- Wait until command done;
		wait for PERIOD;
		
		-- Remove Tail Thread(6)
      s_parameter <= conv_std_logic_vector(6, 32);
		s_command <= C_CMD_REMOVE;
		wait for PERIOD;
		s_command <= C_CMD_NOP;
		wait until s_status(0) = '1'; -- Wait until command done;
		wait for PERIOD;
		
		-- Remove Head Thread(7)
      s_parameter <= conv_std_logic_vector(7, 32);
		s_command <= C_CMD_REMOVE;
		wait for PERIOD;
		s_command <= C_CMD_NOP;
		wait until s_status(0) = '1'; -- Wait until command done;
		wait for PERIOD;
		
		-- Remove All Threads
		s_parameter <= conv_std_logic_vector(2, 32);
		s_command <= C_CMD_REMOVE;
		wait for PERIOD;
		s_command <= C_CMD_NOP;
		wait until s_status(0) = '1'; -- Wait until command done;
		wait for PERIOD;
		s_parameter <= conv_std_logic_vector(3, 32);
		s_command <= C_CMD_REMOVE;
		wait for PERIOD;
		s_command <= C_CMD_NOP;
		wait until s_status(0) = '1'; -- Wait until command done;
		wait for PERIOD;
		s_parameter <= conv_std_logic_vector(8, 32);
		s_command <= C_CMD_REMOVE;
		wait for PERIOD;
		s_command <= C_CMD_NOP;
		wait until s_status(0) = '1'; -- Wait until command done;
		wait for PERIOD;
		s_parameter <= conv_std_logic_vector(1, 32);
		s_command <= C_CMD_REMOVE;
		wait for PERIOD;
		s_command <= C_CMD_NOP;
		wait until s_status(0) = '1'; -- Wait until command done;
		wait for PERIOD;
		
		-- Insert All Threads
		s_parameter <= conv_std_logic_vector(1, 32);
		s_priority <= conv_std_logic_vector(7, 16);
		s_command <= C_CMD_INSERT;
		wait for PERIOD;
		s_command <= C_CMD_NOP;
		wait until s_status(0) = '1'; -- Wait until command done;
		wait for PERIOD;
		s_parameter <= conv_std_logic_vector(2, 32);
		s_priority <= conv_std_logic_vector(2, 16);
		s_command <= C_CMD_INSERT;
		wait for PERIOD;
		s_command <= C_CMD_NOP;
		wait until s_status(0) = '1'; -- Wait until command done;
		wait for PERIOD;
		s_parameter <= conv_std_logic_vector(3, 32);
		s_priority <= conv_std_logic_vector(6, 16);
		s_command <= C_CMD_INSERT;
		wait for PERIOD;
		s_command <= C_CMD_NOP;
		wait until s_status(0) = '1'; -- Wait until command done;
		wait for PERIOD;
		s_parameter <= conv_std_logic_vector(4, 32);
		s_priority <= conv_std_logic_vector(3, 16);
		s_command <= C_CMD_INSERT;
		wait for PERIOD;
		s_command <= C_CMD_NOP;
		wait until s_status(0) = '1'; -- Wait until command done;
		wait for PERIOD;
		s_parameter <= conv_std_logic_vector(5, 32);
		s_priority <= conv_std_logic_vector(9, 16);
		s_command <= C_CMD_INSERT;
		wait for PERIOD;
		s_command <= C_CMD_NOP;
		wait until s_status(0) = '1'; -- Wait until command done;
		wait for PERIOD;
		s_parameter <= conv_std_logic_vector(6, 32);
		s_priority <= conv_std_logic_vector(8, 16);
		s_command <= C_CMD_INSERT;
		wait for PERIOD;
		s_command <= C_CMD_NOP;
		wait until s_status(0) = '1'; -- Wait until command done;
		wait for PERIOD;
		s_parameter <= conv_std_logic_vector(7, 32);
		s_priority <= conv_std_logic_vector(1, 16);
		s_command <= C_CMD_INSERT;
		wait for PERIOD;
		s_command <= C_CMD_NOP;
		wait until s_status(0) = '1'; -- Wait until command done;
		wait for PERIOD;
		s_parameter <= conv_std_logic_vector(8, 32);
		s_priority <= conv_std_logic_vector(4, 16);
		s_command <= C_CMD_INSERT;
		wait for PERIOD;
		s_command <= C_CMD_NOP;
		wait until s_status(0) = '1'; -- Wait until command done;
		wait for PERIOD;
		
		-- Destroy All threads
		s_parameter <= conv_std_logic_vector(1, 32);
		s_command <= C_CMD_DESTROY;
		wait for PERIOD;
		s_command <= C_CMD_NOP;
		wait until s_status(0) = '1'; -- Wait until command done;
		wait for PERIOD;
		s_parameter <= conv_std_logic_vector(2, 32);
		s_command <= C_CMD_DESTROY;
		wait for PERIOD;
		s_command <= C_CMD_NOP;
		wait until s_status(0) = '1'; -- Wait until command done;
		wait for PERIOD;
		s_parameter <= conv_std_logic_vector(3, 32);
		s_command <= C_CMD_DESTROY;
		wait for PERIOD;
		s_command <= C_CMD_NOP;
		wait until s_status(0) = '1'; -- Wait until command done;
		wait for PERIOD;
		s_parameter <= conv_std_logic_vector(4, 32);
		s_command <= C_CMD_DESTROY;
		wait for PERIOD;
		s_command <= C_CMD_NOP;
		wait until s_status(0) = '1'; -- Wait until command done;
		wait for PERIOD;
		s_parameter <= conv_std_logic_vector(5, 32);
		s_command <= C_CMD_DESTROY;
		wait for PERIOD;
		s_command <= C_CMD_NOP;
		wait until s_status(0) = '1'; -- Wait until command done;
		wait for PERIOD;
		s_parameter <= conv_std_logic_vector(6, 32);
		s_command <= C_CMD_DESTROY;
		wait for PERIOD;
		s_command <= C_CMD_NOP;
		wait until s_status(0) = '1'; -- Wait until command done;
		wait for PERIOD;
		s_parameter <= conv_std_logic_vector(7, 32);
		s_command <= C_CMD_DESTROY;
		wait for PERIOD;
		s_command <= C_CMD_NOP;
		wait until s_status(0) = '1'; -- Wait until command done;
		wait for PERIOD;
		s_parameter <= conv_std_logic_vector(8, 32);
		s_command <= C_CMD_DESTROY;
		wait for PERIOD;
		s_command <= C_CMD_NOP;
		wait until s_status(0) = '1'; -- Wait until command done;
		wait for PERIOD;
		
		-- Simulate and cooperative scheduling (thread A and B, main)
		-- Create Thread(10 - main) - 8
		s_parameter <= conv_std_logic_vector(10, 32);
		s_priority <= conv_std_logic_vector(15, 16);
		s_command <= C_CMD_CREATE;
		wait for PERIOD;
		s_command <= C_CMD_NOP;
		wait until s_status(0) = '1'; -- Wait until command done;
		wait for PERIOD;
      s_parameter <= conv_std_logic_vector(8, 32);
		s_priority <= conv_std_logic_vector(15, 16);
		s_command <= C_CMD_INSERT;
		wait for PERIOD;
		s_command <= C_CMD_NOP;
		wait until s_status(0) = '1'; -- Wait until command done;
		wait for PERIOD;
		
		-- Create Thread(20 - idle) - 7
		s_parameter <= conv_std_logic_vector(20, 32);
		s_priority <= conv_std_logic_vector(31, 16);
		s_command <= C_CMD_CREATE;
		wait for PERIOD;
		s_command <= C_CMD_NOP;
		wait until s_status(0) = '1'; -- Wait until command done;
		wait for PERIOD;
		s_parameter <= conv_std_logic_vector(7, 32);
		s_priority <= conv_std_logic_vector(31, 16);
		s_command <= C_CMD_INSERT;
		wait for PERIOD;
		s_command <= C_CMD_NOP;
		wait until s_status(0) = '1'; -- Wait until command done;
		wait for PERIOD;
		
		-- Create Thread(11 - a) - 6 
		s_parameter <= conv_std_logic_vector(11, 32);
		s_priority <= conv_std_logic_vector(15, 16);
		s_command <= C_CMD_CREATE;
		wait for PERIOD;
		s_command <= C_CMD_NOP;
		wait until s_status(0) = '1'; -- Wait until command done;
		wait for PERIOD;
		s_parameter <= conv_std_logic_vector(6, 32);
		s_priority <= conv_std_logic_vector(15, 16);
		s_command <= C_CMD_INSERT;
		wait for PERIOD;
		s_command <= C_CMD_NOP;
		wait until s_status(0) = '1'; -- Wait until command done;
		wait for PERIOD;
		
		-- Create Thread(12 - b) - 5
		s_parameter <= conv_std_logic_vector(12, 32);
		s_priority <= conv_std_logic_vector(15, 16);
		s_command <= C_CMD_CREATE;
		wait for PERIOD;
		s_command <= C_CMD_NOP;
		wait until s_status(0) = '1'; -- Wait until command done;
		wait for PERIOD;
		s_parameter <= conv_std_logic_vector(5, 32);
		s_priority <= conv_std_logic_vector(15, 16);
		s_command <= C_CMD_INSERT;
		wait for PERIOD;
		s_command <= C_CMD_NOP;
		wait until s_status(0) = '1'; -- Wait until command done;
		wait for PERIOD;
		
		-- Search Thread ID
		s_parameter <= conv_std_logic_vector(11, 32);
		s_command <= C_CMD_GETID;
		wait for PERIOD;
		s_command <= C_CMD_NOP;
		wait until s_status(0) = '1'; -- Wait until command done;
		wait for PERIOD;
		
		--GET HEAD
		s_command <= C_CMD_GETHEAD;
		wait for PERIOD;
		s_command <= C_CMD_NOP;
		wait until s_status(0) = '1'; -- Wait until command done;
		wait for PERIOD;
		
		--Suspend Main
		s_parameter <= conv_std_logic_vector(8, 32);
		s_command <= C_CMD_REMOVE;
		wait for PERIOD;
		s_command <= C_CMD_NOP;
		wait until s_status(0) = '1'; -- Wait until command done;
		wait for PERIOD;

		s_command <= C_CMD_REMOVE_HEAD;
		wait for PERIOD;
		s_command <= C_CMD_NOP;
		wait until s_status(0) = '1'; -- Wait until command done;
		wait for PERIOD;			
		
		for i in 1 to 1 loop

			s_command <= C_CMD_REMOVE_HEAD;
			wait for PERIOD;
			s_command <= C_CMD_NOP;
			wait until s_status(0) = '1'; -- Wait until command done;
			wait for PERIOD;
			s_parameter <= conv_std_logic_vector(6, 32);
			s_priority <= conv_std_logic_vector(15, 16);
			s_command <= C_CMD_INSERT;
			wait for PERIOD;
			s_command <= C_CMD_NOP;
			wait until s_status(0) = '1'; -- Wait until command done;
			wait for PERIOD;
			
			s_command <= C_CMD_REMOVE_HEAD;
			wait for PERIOD;
			s_command <= C_CMD_NOP;
			wait until s_status(0) = '1'; -- Wait until command done;
			wait for PERIOD;
			s_parameter <= conv_std_logic_vector(5, 32);
			s_priority <= conv_std_logic_vector(15, 16);
			s_command <= C_CMD_INSERT;
			wait for PERIOD;
			s_command <= C_CMD_NOP;
			wait until s_status(0) = '1'; -- Wait until command done;
			wait for PERIOD;
			
		end loop;
		
		wait; -- will wait forever
	end process;

end;
