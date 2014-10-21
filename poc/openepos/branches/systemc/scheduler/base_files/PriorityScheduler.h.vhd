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

package PriorityScheduler_h is

component PriorityScheduler is

	 Generic ( C_MAX_THREADS:	 	integer 	:= 8;
				  C_DWIDTH:				integer	:= 32
				);
				
    Port ( p_clk 				:in   std_logic;
           p_reset 			:in   std_logic;
           p_command 		:in   std_logic_vector (0 to 3);
			  p_priority 		:in   std_logic_vector (0 to 15);
			  p_parameter 		:in   std_logic_vector (0 to C_DWIDTH-1);
           p_return		 	:out  std_logic_vector (0 to C_DWIDTH-1);
           p_status 			:out  std_logic_vector (0 to 5);
           p_interrupt 		:out  std_logic
			);
			
end component PriorityScheduler;

end package PriorityScheduler_h;
