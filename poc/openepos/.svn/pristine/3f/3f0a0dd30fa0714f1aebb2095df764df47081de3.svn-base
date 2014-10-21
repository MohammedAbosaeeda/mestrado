--------------------------------------------------------------------------------
-- Company: LISHA
-- Engineer: Hugo Marcondes
--
-- Create Date:   10:23:47 07/17/2007
-- Design Name:   PriorityScheduler
-- Module Name:   E:/UFSC/Mestrado/Implementation/Priority_Scheduler/reschedule_testbench.vhd
-- Project Name:  priority_scheduler
--
-- VHDL Test Bench Created by ISE for module: PriorityScheduler
--------------------------------------------------------------------------------
LIBRARY ieee;
USE ieee.std_logic_1164.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;
USE ieee.std_logic_unsigned.all;
USE ieee.numeric_std.ALL;

ENTITY reschedule_testbench_vhd IS
END reschedule_testbench_vhd;

ARCHITECTURE behavior OF reschedule_testbench_vhd IS 

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
	constant C_CMD_INT_ACK:  		 std_logic_vector(0 to 3) := "1010";
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
		
		-- Place stimulus here
		wait for 5 ns;
		s_reset <= '1';
		wait for PERIOD; -- Wait next cycle
		-- Insert several Threads
		s_reset <= '0';
		wait for PERIOD; -- Wait next cycle
		
		-- Setup reschedule
		s_parameter <= conv_std_logic_vector(30, 32);
		s_command <= C_CMD_SET_QUANTUM;
		wait for PERIOD;
		s_command <= C_CMD_NOP;
		wait until s_status(0) = '1'; -- Wait until command done;
		wait for PERIOD;
		
		-- Create Thread(8,31)
		s_parameter <= conv_std_logic_vector(8, 32);
		s_priority <= conv_std_logic_vector(65535, 16);
		s_command <= C_CMD_CREATE;
		wait for PERIOD;
		s_command <= C_CMD_NOP;
		wait until s_status(0) = '1'; -- Wait until command done;
		wait for PERIOD;
      s_parameter <= conv_std_logic_vector(8, 32);
		s_priority <= conv_std_logic_vector(65535, 16);
		s_command <= C_CMD_INSERT;
		wait for PERIOD;
		s_command <= C_CMD_NOP;
		wait until s_status(0) = '1'; -- Wait until command done;
		wait for PERIOD;
		
		-- Create Thread(7,15) (running)
		s_parameter <= conv_std_logic_vector(7, 32);
		s_priority <= conv_std_logic_vector(15, 16);
		s_command <= C_CMD_CREATE;
		wait for PERIOD;
		s_command <= C_CMD_NOP;
		wait until s_status(0) = '1'; -- Wait until command done;
		wait for PERIOD;
		-- Set Running
		s_parameter <= conv_std_logic_vector(7, 32);
		s_command <= C_CMD_UPDATE_RUNNING;
		wait for PERIOD;
		s_command <= C_CMD_NOP;
		wait until s_status(0) = '1'; -- Wait until command done;
		wait for PERIOD;

		-- Create Thread(7,15)
		s_parameter <= conv_std_logic_vector(6, 32);
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
		
		-- Enable Scheduling
		s_command <= C_CMD_ENABLE;
		wait for PERIOD;
		s_command <= C_CMD_NOP;
		wait until s_status(0) = '1'; -- Wait until command done;
		wait for PERIOD;
		
		for i in 1 to 10 loop
		
			-- Wait Interrupt;
			if s_interrupt = '0' then
				wait until s_interrupt = '1';
			end if;
			wait for PERIOD;
	
			-- Disable Scheduling
			s_command <= C_CMD_DISABLE;
			wait for PERIOD;
			s_command <= C_CMD_NOP;
			wait until s_status(0) = '1'; -- Wait until command done;
			wait for PERIOD;
			-- ACK INTERRUPT
			s_command <= C_CMD_INT_ACK;
			wait for PERIOD;
			s_command <= C_CMD_NOP;
			wait until s_status(0) = '1'; -- Wait until command done;
			wait for PERIOD;
			
			-- Insert (7,15)
			s_parameter <= conv_std_logic_vector(7, 32);
			s_priority <= conv_std_logic_vector(15, 16);
			s_command <= C_CMD_INSERT;
			wait for PERIOD;
			s_command <= C_CMD_NOP;
			wait until s_status(0) = '1'; -- Wait until command done;
			wait for PERIOD;
			
			-- Remove Head
			s_command <= C_CMD_REMOVE_HEAD;
			wait for PERIOD;
			s_command <= C_CMD_NOP;
			wait until s_status(0) = '1'; -- Wait until command done;
			wait for PERIOD;
			
			-- Set Running
			s_parameter <= conv_std_logic_vector(6, 32);
			s_command <= C_CMD_UPDATE_RUNNING;
			wait for PERIOD;
			s_command <= C_CMD_NOP;
			wait until s_status(0) = '1'; -- Wait until command done;
			wait for PERIOD;
			
			-- Enable Scheduling
			s_command <= C_CMD_ENABLE;
			wait for PERIOD;
			s_command <= C_CMD_NOP;
			wait until s_status(0) = '1'; -- Wait until command done;
			wait for PERIOD;
			
			-- Wait Interrupt;
			if s_interrupt = '0' then
				wait until s_interrupt = '1';
			end if;
			wait for PERIOD;
	
			-- Disable Scheduling
			s_command <= C_CMD_DISABLE;
			wait for PERIOD;
			s_command <= C_CMD_NOP;
			wait until s_status(0) = '1'; -- Wait until command done;
			wait for PERIOD;
			-- ACK INTERRUPT
			s_command <= C_CMD_INT_ACK;
			wait for PERIOD;
			s_command <= C_CMD_NOP;
			wait until s_status(0) = '1'; -- Wait until command done;
			wait for PERIOD;
			
			-- Insert (7,15)
			s_parameter <= conv_std_logic_vector(6, 32);
			s_priority <= conv_std_logic_vector(15, 16);
			s_command <= C_CMD_INSERT;
			wait for PERIOD;
			s_command <= C_CMD_NOP;
			wait until s_status(0) = '1'; -- Wait until command done;
			wait for PERIOD;
			
			-- Remove Head
			s_command <= C_CMD_REMOVE_HEAD;
			wait for PERIOD;
			s_command <= C_CMD_NOP;
			wait until s_status(0) = '1'; -- Wait until command done;
			wait for PERIOD;
			
			-- Set Running
			s_parameter <= conv_std_logic_vector(7, 32);
			s_command <= C_CMD_UPDATE_RUNNING;
			wait for PERIOD;
			s_command <= C_CMD_NOP;
			wait until s_status(0) = '1'; -- Wait until command done;
			wait for PERIOD;
			
			-- Enable Scheduling
			s_command <= C_CMD_ENABLE;
			wait for PERIOD;
			s_command <= C_CMD_NOP;
			wait until s_status(0) = '1'; -- Wait until command done;
			wait for PERIOD;
	
		end loop;
				
		wait; -- will wait forever
	end process;

end;