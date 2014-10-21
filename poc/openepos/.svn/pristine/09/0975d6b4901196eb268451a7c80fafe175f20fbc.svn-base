----------------------------------------------------------------------------------
-- Company: LISHA
-- Engineer: Hugo Marcondes
-- 
-- Create Date:    16:49:12 07/03/2007 
-- Design Name: HW/SW Hybrid Components
-- Module Name:    priority_scheduler - Behavioral 
--
-- Dependencies: 
--
-- Revision: 
-- Revision 0.01 - File Created
-- Additional Comments: 
----------------------------------------------------------------------------------
library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;
use work.PriorityScheduler_h.all;

entity PriorityScheduler_sc is
				
    Port ( p_clk 				:in   std_logic;
           p_reset 			:in   std_logic;
           p_command 		:in   std_logic_vector (0 to 3);
			  p_priority 		:in   std_logic_vector (0 to 15);
			  p_parameter 		:in   std_logic_vector (0 to 31);
           p_return		 	:out  std_logic_vector (0 to 31);
           p_status 			:out  std_logic_vector (0 to 5);
           p_interrupt 		:out  std_logic
			);
			
end PriorityScheduler_sc;

architecture Behavioral of PriorityScheduler_sc is	
begin
   	
   	scheduler: PriorityScheduler
   	generic map(
   		C_MAX_THREADS => 8,
   		C_DWIDTH      => 32
   	)
   	port map(
   		p_clk         => p_clk,
   		p_reset       => p_reset,
   		p_command     => p_command,
   		p_priority    => p_priority,
   		p_parameter   => p_parameter,
   		p_return      => p_return,
   		p_status      => p_status,
   		p_interrupt   => p_interrupt
   	);
   	
   	
end Behavioral;

