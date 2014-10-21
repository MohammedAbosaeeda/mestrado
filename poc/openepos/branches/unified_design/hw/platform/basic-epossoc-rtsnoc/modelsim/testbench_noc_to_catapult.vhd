library ieee;
use ieee.std_logic_1164.all;
use ieee.std_logic_arith.all;
use ieee.math_real.log2;
use ieee.math_real.ceil;
USE ieee.std_logic_unsigned.ALL;
library std;
use std.env.all;
use STD.textio.all;



entity testbench_noc_to_catapult is
end testbench_noc_to_catapult;

architecture Behavioral of testbench_noc_to_catapult is
    
    -- RTSNoC constants
        -- Dimension of the network and router address.
    constant NET_SIZE_X         : integer := 1;
    constant NET_SIZE_Y         : integer := 1;
    constant NET_SIZE_X_LOG2    : integer := 1; -- it should be "integer(ceil(log2(real(NET_SIZE_X))))" when NET_SIZE_X >= 2
    constant NET_SIZE_Y_LOG2    : integer := 1; -- it should be "integer(ceil(log2(real(NET_SIZE_Y))))" when NET_SIZE_Y >= 2
    constant ROUTER_X           : std_logic_vector(NET_SIZE_X_LOG2-1 downto 0) := "0";
    constant ROUTER_Y           : std_logic_vector(NET_SIZE_Y_LOG2-1 downto 0) := "0";
    constant NET_DATA_WIDTH     : integer := 56;
    constant ROUTER_N_PORTS     : integer := 8;
    constant NET_BUS_SIZE       : integer := NET_DATA_WIDTH+(2*NET_SIZE_X_LOG2)+(2*NET_SIZE_Y_LOG2)+6;
    constant RMI_MSG_SIZE     : integer := 80;
    constant IID_SIZE     : integer := 8;
    --constant NET_BUS_SIZE_INV   : integer := 64 - NET_BUS_SIZE;
        -- Local addresses (clockwise: nn, ne, ee, se, ss, sw, ww, nw)
    type router_addr_type is array (0 to ROUTER_N_PORTS-1) of std_logic_vector(2 downto 0);
    constant ROUTER_ADDRS   : router_addr_type := 
            ("000", "001", "010", "011", "100", "101", "110", "111");
    constant ROUTER_NN  : integer := 0;
    constant ROUTER_NE  : integer := 1;
    constant ROUTER_EE  : integer := 2;
    constant ROUTER_SE  : integer := 3;
    constant ROUTER_SS  : integer := 4;
    constant ROUTER_SW  : integer := 5;
    constant ROUTER_WW  : integer := 6;
    constant ROUTER_NW  : integer := 7;
        -- NoC node addressess
    constant NOC_SW_NODE_ADDR       : integer := ROUTER_NE;
    constant NODE_ECHO_NODE_P0_ADDR : integer := ROUTER_NN;
    constant NODE_ECHO_NODE_P1_ADDR : integer := ROUTER_SS;
    constant NODE_MULT_NODE_ADDR    : integer := ROUTER_WW;
    
    -- 
    -- Declarations
    --

    COMPONENT ROUTER
        GENERIC (
            p_X         : integer := conv_integer(ROUTER_X);
            p_Y         : integer := conv_integer(ROUTER_Y);
            p_DATA      : integer := NET_DATA_WIDTH;                    
            p_SIZE_X    : integer := NET_SIZE_X_LOG2;
            p_SIZE_Y    : integer := NET_SIZE_Y_LOG2);  
        PORT(
            o_TESTE     : out std_logic_vector(NET_BUS_SIZE-1 downto 0);
            i_CLK       : IN std_logic;
            i_RST       : IN std_logic;
            i_DIN_NN    : IN std_logic_vector(NET_BUS_SIZE-1 downto 0);
            i_WR_NN     : IN std_logic;
            i_RD_NN     : IN std_logic;
            i_DIN_NE    : IN std_logic_vector(NET_BUS_SIZE-1 downto 0);
            i_WR_NE     : IN std_logic;
            i_RD_NE     : IN std_logic;
            i_DIN_EE    : IN std_logic_vector(NET_BUS_SIZE-1 downto 0);
            i_WR_EE     : IN std_logic;
            i_RD_EE     : IN std_logic;
            i_DIN_SE    : IN std_logic_vector(NET_BUS_SIZE-1 downto 0);
            i_WR_SE     : IN std_logic;
            i_RD_SE     : IN std_logic;
            i_DIN_SS    : IN std_logic_vector(NET_BUS_SIZE-1 downto 0);
            i_WR_SS     : IN std_logic;
            i_RD_SS     : IN std_logic;
            i_DIN_SW    : IN std_logic_vector(NET_BUS_SIZE-1 downto 0);
            i_WR_SW     : IN std_logic;
            i_RD_SW     : IN std_logic;
            i_DIN_WW    : IN std_logic_vector(NET_BUS_SIZE-1 downto 0);
            i_WR_WW     : IN std_logic;
            i_RD_WW     : IN std_logic;
            i_DIN_NW    : IN std_logic_vector(NET_BUS_SIZE-1 downto 0);
            i_WR_NW     : IN std_logic;
            i_RD_NW     : IN std_logic;          
            o_DOUT_NN   : OUT std_logic_vector(NET_BUS_SIZE-1 downto 0);
            o_WAIT_NN   : OUT std_logic;
            o_ND_NN     : OUT std_logic;
            o_DOUT_NE   : OUT std_logic_vector(NET_BUS_SIZE-1 downto 0);
            o_WAIT_NE   : OUT std_logic;
            o_ND_NE     : OUT std_logic;
            o_DOUT_EE   : OUT std_logic_vector(NET_BUS_SIZE-1 downto 0);
            o_WAIT_EE   : OUT std_logic;
            o_ND_EE     : OUT std_logic;
            o_DOUT_SE   : OUT std_logic_vector(NET_BUS_SIZE-1 downto 0);
            o_WAIT_SE   : OUT std_logic;
            o_ND_SE     : OUT std_logic;
            o_DOUT_SS   : OUT std_logic_vector(NET_BUS_SIZE-1 downto 0);
            o_WAIT_SS   : OUT std_logic;
            o_ND_SS     : OUT std_logic;
            o_DOUT_SW   : OUT std_logic_vector(NET_BUS_SIZE-1 downto 0);
            o_WAIT_SW   : OUT std_logic;
            o_ND_SW     : OUT std_logic;
            o_DOUT_WW   : OUT std_logic_vector(NET_BUS_SIZE-1 downto 0);
            o_WAIT_WW   : OUT std_logic;
            o_ND_WW     : OUT std_logic;
            o_DOUT_NW   : OUT std_logic_vector(NET_BUS_SIZE-1 downto 0);
            o_WAIT_NW   : OUT std_logic;
            o_ND_NW     : OUT std_logic
        );
    END COMPONENT;
    
    component rtsnoc_axi4lite_reset is
        port (
            axi_rst_i   : in std_logic;
            noc_reset_o : out std_logic    
        );
    end component;
        
    component Mult_Node_RTL is
        generic (
            X           : std_logic_vector(NET_SIZE_Y_LOG2-1 downto 0) := "0";
            Y           : std_logic_vector(NET_SIZE_Y_LOG2-1 downto 0) := "0";
            LOCAL_ADDR  : std_logic_vector(2 downto 0) := ROUTER_ADDRS(NODE_MULT_NODE_ADDR);
            SIZE_X      : integer := NET_SIZE_X_LOG2;
            SIZE_Y      : integer := NET_SIZE_Y_LOG2;
            SIZE_DATA   : integer := NET_DATA_WIDTH;
            RMI_MSG_SIZE: integer := RMI_MSG_SIZE;
            IID_SIZE    : integer := IID_SIZE);
            
       port(
            clk_i   : in std_logic;
            rst_i   : in std_logic;
            din_o   : out std_logic_vector(NET_BUS_SIZE-1 downto 0);
            dout_i  : in std_logic_vector(NET_BUS_SIZE-1 downto 0);
            wr_o    : out std_logic;
            rd_o    : out std_logic;
            wait_i  : in std_logic;
            nd_i    : in std_logic;
            iid_i   : in std_logic_vector((IID_SIZE*2)-1 downto 0));
    end component Mult_Node_RTL;
       
    -- RTSNoC signals
    signal sig_noc_reset    : std_logic;
        --Ports of the router (clockwise: nn, ne, ee, se, ss, sw, ww, nw) 
    type noc_array_of_stdlogic is array(0 to ROUTER_N_PORTS-1) of std_logic;
    type noc_array_of_stdvec38 is array(0 to ROUTER_N_PORTS-1) of std_logic_vector(NET_BUS_SIZE-1 downto 0);
    signal sig_noc_din   : noc_array_of_stdvec38;
    signal sig_noc_dout  : noc_array_of_stdvec38;
    signal sig_noc_wr    : noc_array_of_stdlogic;
    signal sig_noc_rd    : noc_array_of_stdlogic;
    signal sig_noc_wait  : noc_array_of_stdlogic;
    signal sig_noc_nd    : noc_array_of_stdlogic;

       
    -- HLS nodes signals
    signal mult_node_iid   :  std_logic_vector((IID_SIZE*2)-1 downto 0);
    
    
    --Sim
    
    signal clk_i       : std_logic;
    signal reset_i     : std_logic;
    
    constant N_PKTS : integer := 216;
    
    type pkt is
    record
         data : std_logic_vector(NET_BUS_SIZE-1 downto 0);
         compare : std_logic_vector(NET_BUS_SIZE-1 downto 0);
         tx : std_logic;
    end record;
    type pkt_array_type is array(0 to N_PKTS-1) of pkt;

    signal pkts : pkt_array_type;
    
    function to_string(sv: Std_Logic_Vector) return string is
    use Std.TextIO.all;
    variable bv: bit_vector(sv'range) := to_bitvector(sv);
    variable lp: line;
  begin
    write(lp, bv);
    return lp.all;
  end;
    
    
begin

    -- -----------------------------------------------------
    -- RTSNoC router
    -- -----------------------------------------------------
    
    rtsnoc_router: ROUTER 
        GENERIC MAP (
            p_X         => conv_integer(ROUTER_X),
            p_Y         => conv_integer(ROUTER_Y),
            p_DATA      => NET_DATA_WIDTH,                    
            p_SIZE_X    => NET_SIZE_X_LOG2,
            p_SIZE_Y    => NET_SIZE_Y_LOG2)
        PORT MAP(
            o_TESTE     => open,
            i_CLK       => clk_i,
            i_RST       => sig_noc_reset,
            -- NORTH
            i_DIN_NN    => sig_noc_din(ROUTER_NN),
            o_DOUT_NN   => sig_noc_dout(ROUTER_NN),
            i_WR_NN     => sig_noc_wr(ROUTER_NN),
            i_RD_NN     => sig_noc_rd(ROUTER_NN),
            o_WAIT_NN   => sig_noc_wait(ROUTER_NN),
            o_ND_NN     => sig_noc_nd(ROUTER_NN),
            -- NORTHEAST
            i_DIN_NE    => sig_noc_din(ROUTER_NE),
            o_DOUT_NE   => sig_noc_dout(ROUTER_NE),
            i_WR_NE     => sig_noc_wr(ROUTER_NE),
            i_RD_NE     => sig_noc_rd(ROUTER_NE),
            o_WAIT_NE   => sig_noc_wait(ROUTER_NE),
            o_ND_NE     => sig_noc_nd(ROUTER_NE),
            -- EAST
            i_DIN_EE    => sig_noc_din(ROUTER_EE),
            o_DOUT_EE   => sig_noc_dout(ROUTER_EE),
            i_WR_EE     => sig_noc_wr(ROUTER_EE),
            i_RD_EE     => sig_noc_rd(ROUTER_EE),
            o_WAIT_EE   => sig_noc_wait(ROUTER_EE),
            o_ND_EE     => sig_noc_nd(ROUTER_EE),
            -- SOUTHEAST
            i_DIN_SE    => sig_noc_din(ROUTER_SE),
            o_DOUT_SE   => sig_noc_dout(ROUTER_SE),
            i_WR_SE     => sig_noc_wr(ROUTER_SE),
            i_RD_SE     => sig_noc_rd(ROUTER_SE),
            o_WAIT_SE   => sig_noc_wait(ROUTER_SE),
            o_ND_SE     => sig_noc_nd(ROUTER_SE),
            -- SOUTH
            i_DIN_SS    => sig_noc_din(ROUTER_SS),
            o_DOUT_SS   => sig_noc_dout(ROUTER_SS),
            i_WR_SS     => sig_noc_wr(ROUTER_SS),
            i_RD_SS     => sig_noc_rd(ROUTER_SS),
            o_WAIT_SS   => sig_noc_wait(ROUTER_SS),
            o_ND_SS     => sig_noc_nd(ROUTER_SS),
            -- SOUTHWEST
            i_DIN_SW    => sig_noc_din(ROUTER_SW),
            o_DOUT_SW   => sig_noc_dout(ROUTER_SW),
            i_WR_SW     => sig_noc_wr(ROUTER_SW),
            i_RD_SW     => sig_noc_rd(ROUTER_SW),
            o_WAIT_SW   => sig_noc_wait(ROUTER_SW),
            o_ND_SW     => sig_noc_nd(ROUTER_SW),
            -- WEST
            i_DIN_WW    => sig_noc_din(ROUTER_WW),
            o_DOUT_WW   => sig_noc_dout(ROUTER_WW),
            i_WR_WW     => sig_noc_wr(ROUTER_WW),
            i_RD_WW     => sig_noc_rd(ROUTER_WW),
            o_WAIT_WW   => sig_noc_wait(ROUTER_WW),
            o_ND_WW     => sig_noc_nd(ROUTER_WW),
            -- NORTHWEST
            i_DIN_NW    => sig_noc_din(ROUTER_NW),
            o_DOUT_NW   => sig_noc_dout(ROUTER_NW),
            i_WR_NW     => sig_noc_wr(ROUTER_NW),
            i_RD_NW     => sig_noc_rd(ROUTER_NW),
            o_WAIT_NW   => sig_noc_wait(ROUTER_NW),
            o_ND_NW     => sig_noc_nd(ROUTER_NW) 
    );
    
    rtsnoc_reset : rtsnoc_axi4lite_reset
        port map (
            axi_rst_i   => reset_i,
            noc_reset_o => sig_noc_reset
        );
     
    -- ------------------------------------------------------------
    -- NODE Mult
    -- ------------------------------------------------------------
    -- const unsigned char Resource_Table<Add,0>::IID[Traits<Add>::n_ids] = {0};
    -- const unsigned char Resource_Table<Mult,0>::IID[Traits<Mult>::n_ids] = {0,Resource_Table<Add,0>::IID[0]};
    mult_node_iid <= "0000000000000000"; 
    mult_node : Mult_Node_RTL
        generic map (
            X           => "0",
            Y           => "0",
            LOCAL_ADDR  => ROUTER_ADDRS(NODE_MULT_NODE_ADDR),
            SIZE_X      => NET_SIZE_X_LOG2,
            SIZE_Y      => NET_SIZE_Y_LOG2,
            SIZE_DATA   => NET_DATA_WIDTH,
            RMI_MSG_SIZE => RMI_MSG_SIZE,
            IID_SIZE    => IID_SIZE)
        port map(
            -- System signals
            clk_i       => clk_i,
            rst_i       => sig_noc_reset,
            -- NoC signals
            din_o   => sig_noc_din(NODE_MULT_NODE_ADDR),
            dout_i  => sig_noc_dout(NODE_MULT_NODE_ADDR),
            wr_o    => sig_noc_wr(NODE_MULT_NODE_ADDR),
            rd_o    => sig_noc_rd(NODE_MULT_NODE_ADDR),
            wait_i  => sig_noc_wait(NODE_MULT_NODE_ADDR),
            nd_i    => sig_noc_nd(NODE_MULT_NODE_ADDR),
            
            -- IID
            iid_i  => mult_node_iid
        );       
        
    -- simulation
    clk_process: process
    begin
        clk_i <= '1';
        wait for 10 ns;
        clk_i <= '0';
        wait for 10 ns;
    end process;
    
    
    tb : process
    begin
        report "Inicio";
        
pkts(0).tx <= '0'; pkts(0).data <= "000010011000101000000000000000000000000000000000000000000011110000";
pkts(1).tx <= '0'; pkts(1).data <= "000010011000101000000000000000001000000000000000000000000000000000";
pkts(2).tx <= '0'; pkts(2).data <= "000010011000101000000000000000001000000000000000000000000000000000";
pkts(3).tx <= '1'; pkts(3).data <= "001100000100101000000000000000001100000000000000000000000000000000";
pkts(4).tx <= '0'; pkts(4).data <= "000010011000101000000000000000000000000000000000000000000011110000";
pkts(5).tx <= '0'; pkts(5).data <= "000010011000101000000000000000001000000000000000000000000000000000";
pkts(6).tx <= '0'; pkts(6).data <= "000010011000101000000000000000001000000000000000000000000000001010";
pkts(7).tx <= '1'; pkts(7).data <= "001100000100101000000000000000001100000000000000000000000000000000";
pkts(8).tx <= '0'; pkts(8).data <= "000010011000101000000000000000000000000000000000000000000011110000";
pkts(9).tx <= '0'; pkts(9).data <= "000010011000101000000000000000001000000000000000000000000000001010";
pkts(10).tx <= '0'; pkts(10).data <= "000010011000101000000000000000001000000000000000000000000000000000";
pkts(11).tx <= '1'; pkts(11).data <= "001100000100100111000000000000000000000000000000000000000011110000";
pkts(12).tx <= '1'; pkts(12).data <= "001100000100100111000000000000001000000000000000000000000000000000";
pkts(13).tx <= '1'; pkts(13).data <= "001100000100100111000000000000001000000000000000000000000000000000";
pkts(14).tx <= '0'; pkts(14).data <= "000010011000100111000000000000001100000000000000000000000000000000";
pkts(15).tx <= '1'; pkts(15).data <= "001100000100100111000000000000000000000000000000000000000011110000";
pkts(16).tx <= '1'; pkts(16).data <= "001100000100100111000000000000001000000000000000000000000000000000";
pkts(17).tx <= '1'; pkts(17).data <= "001100000100100111000000000000001000000000000000000000000000000000";
pkts(18).tx <= '0'; pkts(18).data <= "000010011000100111000000000000001100000000000000000000000000000000";
pkts(19).tx <= '1'; pkts(19).data <= "001100000100100111000000000000000000000000000000000000000011110000";
pkts(20).tx <= '1'; pkts(20).data <= "001100000100100111000000000000001000000000000000000000000000000000";
pkts(21).tx <= '1'; pkts(21).data <= "001100000100100111000000000000001000000000000000000000000000000000";
pkts(22).tx <= '0'; pkts(22).data <= "000010011000100111000000000000001100000000000000000000000000000000";
pkts(23).tx <= '1'; pkts(23).data <= "001100000100100111000000000000000000000000000000000000000011110000";
pkts(24).tx <= '1'; pkts(24).data <= "001100000100100111000000000000001000000000000000000000000000000000";
pkts(25).tx <= '1'; pkts(25).data <= "001100000100100111000000000000001000000000000000000000000000000000";
pkts(26).tx <= '0'; pkts(26).data <= "000010011000100111000000000000001100000000000000000000000000000000";
pkts(27).tx <= '1'; pkts(27).data <= "001100000100100111000000000000000000000000000000000000000011110000";
pkts(28).tx <= '1'; pkts(28).data <= "001100000100100111000000000000001000000000000000000000000000000000";
pkts(29).tx <= '1'; pkts(29).data <= "001100000100100111000000000000001000000000000000000000000000000000";
pkts(30).tx <= '0'; pkts(30).data <= "000010011000100111000000000000001100000000000000000000000000000000";
pkts(31).tx <= '1'; pkts(31).data <= "001100000100100111000000000000000000000000000000000000000011110000";
pkts(32).tx <= '1'; pkts(32).data <= "001100000100100111000000000000001000000000000000000000000000000000";
pkts(33).tx <= '1'; pkts(33).data <= "001100000100100111000000000000001000000000000000000000000000000000";
pkts(34).tx <= '0'; pkts(34).data <= "000010011000100111000000000000001100000000000000000000000000000000";
pkts(35).tx <= '1'; pkts(35).data <= "001100000100100111000000000000000000000000000000000000000011110000";
pkts(36).tx <= '1'; pkts(36).data <= "001100000100100111000000000000001000000000000000000000000000000000";
pkts(37).tx <= '1'; pkts(37).data <= "001100000100100111000000000000001000000000000000000000000000000000";
pkts(38).tx <= '0'; pkts(38).data <= "000010011000100111000000000000001100000000000000000000000000000000";
pkts(39).tx <= '1'; pkts(39).data <= "001100000100100111000000000000000000000000000000000000000011110000";
pkts(40).tx <= '1'; pkts(40).data <= "001100000100100111000000000000001000000000000000000000000000000000";
pkts(41).tx <= '1'; pkts(41).data <= "001100000100100111000000000000001000000000000000000000000000000000";
pkts(42).tx <= '0'; pkts(42).data <= "000010011000100111000000000000001100000000000000000000000000000000";
pkts(43).tx <= '1'; pkts(43).data <= "001100000100100111000000000000000000000000000000000000000011110000";
pkts(44).tx <= '1'; pkts(44).data <= "001100000100100111000000000000001000000000000000000000000000000000";
pkts(45).tx <= '1'; pkts(45).data <= "001100000100100111000000000000001000000000000000000000000000000000";
pkts(46).tx <= '0'; pkts(46).data <= "000010011000100111000000000000001100000000000000000000000000000000";
pkts(47).tx <= '1'; pkts(47).data <= "001100000100100111000000000000000000000000000000000000000011110000";
pkts(48).tx <= '1'; pkts(48).data <= "001100000100100111000000000000001000000000000000000000000000000000";
pkts(49).tx <= '1'; pkts(49).data <= "001100000100100111000000000000001000000000000000000000000000000000";
pkts(50).tx <= '0'; pkts(50).data <= "000010011000100111000000000000001100000000000000000000000000000000";
pkts(51).tx <= '1'; pkts(51).data <= "001100000100101000000000000000001100000000000000000000000000000000";
pkts(52).tx <= '0'; pkts(52).data <= "000010011000101000000000000000000000000000000000000000000011110000";
pkts(53).tx <= '0'; pkts(53).data <= "000010011000101000000000000000001000000000000000000000000000000001";
pkts(54).tx <= '0'; pkts(54).data <= "000010011000101000000000000000001000000000000000000000000000001010";
pkts(55).tx <= '1'; pkts(55).data <= "001100000100100111000000000000000000000000000000000000000011110000";
pkts(56).tx <= '1'; pkts(56).data <= "001100000100100111000000000000001000000000000000000000000000000000";
pkts(57).tx <= '1'; pkts(57).data <= "001100000100100111000000000000001000000000000000000000000000001010";
pkts(58).tx <= '0'; pkts(58).data <= "000010011000100111000000000000001100000000000000000000000000001010";
pkts(59).tx <= '1'; pkts(59).data <= "001100000100101000000000000000001100000000000000000000000000001010";
pkts(60).tx <= '0'; pkts(60).data <= "000010011000101000000000000000000000000000000000000000000011110000";
pkts(61).tx <= '0'; pkts(61).data <= "000010011000101000000000000000001000000000000000000000000000001010";
pkts(62).tx <= '0'; pkts(62).data <= "000010011000101000000000000000001000000000000000000000000000000001";
pkts(63).tx <= '1'; pkts(63).data <= "001100000100100111000000000000000000000000000000000000000011110000";
pkts(64).tx <= '1'; pkts(64).data <= "001100000100100111000000000000001000000000000000000000000000000000";
pkts(65).tx <= '1'; pkts(65).data <= "001100000100100111000000000000001000000000000000000000000000000001";
pkts(66).tx <= '0'; pkts(66).data <= "000010011000100111000000000000001100000000000000000000000000000001";
pkts(67).tx <= '1'; pkts(67).data <= "001100000100100111000000000000000000000000000000000000000011110000";
pkts(68).tx <= '1'; pkts(68).data <= "001100000100100111000000000000001000000000000000000000000000000001";
pkts(69).tx <= '1'; pkts(69).data <= "001100000100100111000000000000001000000000000000000000000000000001";
pkts(70).tx <= '0'; pkts(70).data <= "000010011000100111000000000000001100000000000000000000000000000010";
pkts(71).tx <= '1'; pkts(71).data <= "001100000100100111000000000000000000000000000000000000000011110000";
pkts(72).tx <= '1'; pkts(72).data <= "001100000100100111000000000000001000000000000000000000000000000010";
pkts(73).tx <= '1'; pkts(73).data <= "001100000100100111000000000000001000000000000000000000000000000001";
pkts(74).tx <= '0'; pkts(74).data <= "000010011000100111000000000000001100000000000000000000000000000011";
pkts(75).tx <= '1'; pkts(75).data <= "001100000100100111000000000000000000000000000000000000000011110000";
pkts(76).tx <= '1'; pkts(76).data <= "001100000100100111000000000000001000000000000000000000000000000011";
pkts(77).tx <= '1'; pkts(77).data <= "001100000100100111000000000000001000000000000000000000000000000001";
pkts(78).tx <= '0'; pkts(78).data <= "000010011000100111000000000000001100000000000000000000000000000100";
pkts(79).tx <= '1'; pkts(79).data <= "001100000100100111000000000000000000000000000000000000000011110000";
pkts(80).tx <= '1'; pkts(80).data <= "001100000100100111000000000000001000000000000000000000000000000100";
pkts(81).tx <= '1'; pkts(81).data <= "001100000100100111000000000000001000000000000000000000000000000001";
pkts(82).tx <= '0'; pkts(82).data <= "000010011000100111000000000000001100000000000000000000000000000101";
pkts(83).tx <= '1'; pkts(83).data <= "001100000100100111000000000000000000000000000000000000000011110000";
pkts(84).tx <= '1'; pkts(84).data <= "001100000100100111000000000000001000000000000000000000000000000101";
pkts(85).tx <= '1'; pkts(85).data <= "001100000100100111000000000000001000000000000000000000000000000001";
pkts(86).tx <= '0'; pkts(86).data <= "000010011000100111000000000000001100000000000000000000000000000110";
pkts(87).tx <= '1'; pkts(87).data <= "001100000100100111000000000000000000000000000000000000000011110000";
pkts(88).tx <= '1'; pkts(88).data <= "001100000100100111000000000000001000000000000000000000000000000110";
pkts(89).tx <= '1'; pkts(89).data <= "001100000100100111000000000000001000000000000000000000000000000001";
pkts(90).tx <= '0'; pkts(90).data <= "000010011000100111000000000000001100000000000000000000000000000111";
pkts(91).tx <= '1'; pkts(91).data <= "001100000100100111000000000000000000000000000000000000000011110000";
pkts(92).tx <= '1'; pkts(92).data <= "001100000100100111000000000000001000000000000000000000000000000111";
pkts(93).tx <= '1'; pkts(93).data <= "001100000100100111000000000000001000000000000000000000000000000001";
pkts(94).tx <= '0'; pkts(94).data <= "000010011000100111000000000000001100000000000000000000000000001000";
pkts(95).tx <= '1'; pkts(95).data <= "001100000100100111000000000000000000000000000000000000000011110000";
pkts(96).tx <= '1'; pkts(96).data <= "001100000100100111000000000000001000000000000000000000000000001000";
pkts(97).tx <= '1'; pkts(97).data <= "001100000100100111000000000000001000000000000000000000000000000001";
pkts(98).tx <= '0'; pkts(98).data <= "000010011000100111000000000000001100000000000000000000000000001001";
pkts(99).tx <= '1'; pkts(99).data <= "001100000100100111000000000000000000000000000000000000000011110000";
pkts(100).tx <= '1'; pkts(100).data <= "001100000100100111000000000000001000000000000000000000000000001001";
pkts(101).tx <= '1'; pkts(101).data <= "001100000100100111000000000000001000000000000000000000000000000001";
pkts(102).tx <= '0'; pkts(102).data <= "000010011000100111000000000000001100000000000000000000000000001010";
pkts(103).tx <= '1'; pkts(103).data <= "001100000100101000000000000000001100000000000000000000000000001010";
pkts(104).tx <= '0'; pkts(104).data <= "000010011000101000000000000000000000000000000000000000000011110000";
pkts(105).tx <= '0'; pkts(105).data <= "000010011000101000000000000000001000000000000000000000000000001010";
pkts(106).tx <= '0'; pkts(106).data <= "000010011000101000000000000000001000000000000000000000000000000101";
pkts(107).tx <= '1'; pkts(107).data <= "001100000100100111000000000000000000000000000000000000000011110000";
pkts(108).tx <= '1'; pkts(108).data <= "001100000100100111000000000000001000000000000000000000000000000000";
pkts(109).tx <= '1'; pkts(109).data <= "001100000100100111000000000000001000000000000000000000000000000101";
pkts(110).tx <= '0'; pkts(110).data <= "000010011000100111000000000000001100000000000000000000000000000101";
pkts(111).tx <= '1'; pkts(111).data <= "001100000100100111000000000000000000000000000000000000000011110000";
pkts(112).tx <= '1'; pkts(112).data <= "001100000100100111000000000000001000000000000000000000000000000101";
pkts(113).tx <= '1'; pkts(113).data <= "001100000100100111000000000000001000000000000000000000000000000101";
pkts(114).tx <= '0'; pkts(114).data <= "000010011000100111000000000000001100000000000000000000000000001010";
pkts(115).tx <= '1'; pkts(115).data <= "001100000100100111000000000000000000000000000000000000000011110000";
pkts(116).tx <= '1'; pkts(116).data <= "001100000100100111000000000000001000000000000000000000000000001010";
pkts(117).tx <= '1'; pkts(117).data <= "001100000100100111000000000000001000000000000000000000000000000101";
pkts(118).tx <= '0'; pkts(118).data <= "000010011000100111000000000000001100000000000000000000000000001111";
pkts(119).tx <= '1'; pkts(119).data <= "001100000100100111000000000000000000000000000000000000000011110000";
pkts(120).tx <= '1'; pkts(120).data <= "001100000100100111000000000000001000000000000000000000000000001111";
pkts(121).tx <= '1'; pkts(121).data <= "001100000100100111000000000000001000000000000000000000000000000101";
pkts(122).tx <= '0'; pkts(122).data <= "000010011000100111000000000000001100000000000000000000000000010100";
pkts(123).tx <= '1'; pkts(123).data <= "001100000100100111000000000000000000000000000000000000000011110000";
pkts(124).tx <= '1'; pkts(124).data <= "001100000100100111000000000000001000000000000000000000000000010100";
pkts(125).tx <= '1'; pkts(125).data <= "001100000100100111000000000000001000000000000000000000000000000101";
pkts(126).tx <= '0'; pkts(126).data <= "000010011000100111000000000000001100000000000000000000000000011001";
pkts(127).tx <= '1'; pkts(127).data <= "001100000100100111000000000000000000000000000000000000000011110000";
pkts(128).tx <= '1'; pkts(128).data <= "001100000100100111000000000000001000000000000000000000000000011001";
pkts(129).tx <= '1'; pkts(129).data <= "001100000100100111000000000000001000000000000000000000000000000101";
pkts(130).tx <= '0'; pkts(130).data <= "000010011000100111000000000000001100000000000000000000000000011110";
pkts(131).tx <= '1'; pkts(131).data <= "001100000100100111000000000000000000000000000000000000000011110000";
pkts(132).tx <= '1'; pkts(132).data <= "001100000100100111000000000000001000000000000000000000000000011110";
pkts(133).tx <= '1'; pkts(133).data <= "001100000100100111000000000000001000000000000000000000000000000101";
pkts(134).tx <= '0'; pkts(134).data <= "000010011000100111000000000000001100000000000000000000000000100011";
pkts(135).tx <= '1'; pkts(135).data <= "001100000100100111000000000000000000000000000000000000000011110000";
pkts(136).tx <= '1'; pkts(136).data <= "001100000100100111000000000000001000000000000000000000000000100011";
pkts(137).tx <= '1'; pkts(137).data <= "001100000100100111000000000000001000000000000000000000000000000101";
pkts(138).tx <= '0'; pkts(138).data <= "000010011000100111000000000000001100000000000000000000000000101000";
pkts(139).tx <= '1'; pkts(139).data <= "001100000100100111000000000000000000000000000000000000000011110000";
pkts(140).tx <= '1'; pkts(140).data <= "001100000100100111000000000000001000000000000000000000000000101000";
pkts(141).tx <= '1'; pkts(141).data <= "001100000100100111000000000000001000000000000000000000000000000101";
pkts(142).tx <= '0'; pkts(142).data <= "000010011000100111000000000000001100000000000000000000000000101101";
pkts(143).tx <= '1'; pkts(143).data <= "001100000100100111000000000000000000000000000000000000000011110000";
pkts(144).tx <= '1'; pkts(144).data <= "001100000100100111000000000000001000000000000000000000000000101101";
pkts(145).tx <= '1'; pkts(145).data <= "001100000100100111000000000000001000000000000000000000000000000101";
pkts(146).tx <= '0'; pkts(146).data <= "000010011000100111000000000000001100000000000000000000000000110010";
pkts(147).tx <= '1'; pkts(147).data <= "001100000100101000000000000000001100000000000000000000000000110010";
pkts(148).tx <= '0'; pkts(148).data <= "000010011000101000000000000000000000000000000000000000000011110000";
pkts(149).tx <= '0'; pkts(149).data <= "000010011000101000000000000000001000000000000000000000000000000101";
pkts(150).tx <= '0'; pkts(150).data <= "000010011000101000000000000000001000000000000000000000000000001010";
pkts(151).tx <= '1'; pkts(151).data <= "001100000100100111000000000000000000000000000000000000000011110000";
pkts(152).tx <= '1'; pkts(152).data <= "001100000100100111000000000000001000000000000000000000000000000000";
pkts(153).tx <= '1'; pkts(153).data <= "001100000100100111000000000000001000000000000000000000000000001010";
pkts(154).tx <= '0'; pkts(154).data <= "000010011000100111000000000000001100000000000000000000000000001010";
pkts(155).tx <= '1'; pkts(155).data <= "001100000100100111000000000000000000000000000000000000000011110000";
pkts(156).tx <= '1'; pkts(156).data <= "001100000100100111000000000000001000000000000000000000000000001010";
pkts(157).tx <= '1'; pkts(157).data <= "001100000100100111000000000000001000000000000000000000000000001010";
pkts(158).tx <= '0'; pkts(158).data <= "000010011000100111000000000000001100000000000000000000000000010100";
pkts(159).tx <= '1'; pkts(159).data <= "001100000100100111000000000000000000000000000000000000000011110000";
pkts(160).tx <= '1'; pkts(160).data <= "001100000100100111000000000000001000000000000000000000000000010100";
pkts(161).tx <= '1'; pkts(161).data <= "001100000100100111000000000000001000000000000000000000000000001010";
pkts(162).tx <= '0'; pkts(162).data <= "000010011000100111000000000000001100000000000000000000000000011110";
pkts(163).tx <= '1'; pkts(163).data <= "001100000100100111000000000000000000000000000000000000000011110000";
pkts(164).tx <= '1'; pkts(164).data <= "001100000100100111000000000000001000000000000000000000000000011110";
pkts(165).tx <= '1'; pkts(165).data <= "001100000100100111000000000000001000000000000000000000000000001010";
pkts(166).tx <= '0'; pkts(166).data <= "000010011000100111000000000000001100000000000000000000000000101000";
pkts(167).tx <= '1'; pkts(167).data <= "001100000100100111000000000000000000000000000000000000000011110000";
pkts(168).tx <= '1'; pkts(168).data <= "001100000100100111000000000000001000000000000000000000000000101000";
pkts(169).tx <= '1'; pkts(169).data <= "001100000100100111000000000000001000000000000000000000000000001010";
pkts(170).tx <= '0'; pkts(170).data <= "000010011000100111000000000000001100000000000000000000000000110010";
pkts(171).tx <= '1'; pkts(171).data <= "001100000100101000000000000000001100000000000000000000000000110010";
pkts(172).tx <= '0'; pkts(172).data <= "000010011000101000000000000000000000000000000000000000000011110000";
pkts(173).tx <= '0'; pkts(173).data <= "000010011000101000000000000000001000000000000000000000000000001010";
pkts(174).tx <= '0'; pkts(174).data <= "000010011000101000000000000000001000000000000000000000000000001010";
pkts(175).tx <= '1'; pkts(175).data <= "001100000100100111000000000000000000000000000000000000000011110000";
pkts(176).tx <= '1'; pkts(176).data <= "001100000100100111000000000000001000000000000000000000000000000000";
pkts(177).tx <= '1'; pkts(177).data <= "001100000100100111000000000000001000000000000000000000000000001010";
pkts(178).tx <= '0'; pkts(178).data <= "000010011000100111000000000000001100000000000000000000000000001010";
pkts(179).tx <= '1'; pkts(179).data <= "001100000100100111000000000000000000000000000000000000000011110000";
pkts(180).tx <= '1'; pkts(180).data <= "001100000100100111000000000000001000000000000000000000000000001010";
pkts(181).tx <= '1'; pkts(181).data <= "001100000100100111000000000000001000000000000000000000000000001010";
pkts(182).tx <= '0'; pkts(182).data <= "000010011000100111000000000000001100000000000000000000000000010100";
pkts(183).tx <= '1'; pkts(183).data <= "001100000100100111000000000000000000000000000000000000000011110000";
pkts(184).tx <= '1'; pkts(184).data <= "001100000100100111000000000000001000000000000000000000000000010100";
pkts(185).tx <= '1'; pkts(185).data <= "001100000100100111000000000000001000000000000000000000000000001010";
pkts(186).tx <= '0'; pkts(186).data <= "000010011000100111000000000000001100000000000000000000000000011110";
pkts(187).tx <= '1'; pkts(187).data <= "001100000100100111000000000000000000000000000000000000000011110000";
pkts(188).tx <= '1'; pkts(188).data <= "001100000100100111000000000000001000000000000000000000000000011110";
pkts(189).tx <= '1'; pkts(189).data <= "001100000100100111000000000000001000000000000000000000000000001010";
pkts(190).tx <= '0'; pkts(190).data <= "000010011000100111000000000000001100000000000000000000000000101000";
pkts(191).tx <= '1'; pkts(191).data <= "001100000100100111000000000000000000000000000000000000000011110000";
pkts(192).tx <= '1'; pkts(192).data <= "001100000100100111000000000000001000000000000000000000000000101000";
pkts(193).tx <= '1'; pkts(193).data <= "001100000100100111000000000000001000000000000000000000000000001010";
pkts(194).tx <= '0'; pkts(194).data <= "000010011000100111000000000000001100000000000000000000000000110010";
pkts(195).tx <= '1'; pkts(195).data <= "001100000100100111000000000000000000000000000000000000000011110000";
pkts(196).tx <= '1'; pkts(196).data <= "001100000100100111000000000000001000000000000000000000000000110010";
pkts(197).tx <= '1'; pkts(197).data <= "001100000100100111000000000000001000000000000000000000000000001010";
pkts(198).tx <= '0'; pkts(198).data <= "000010011000100111000000000000001100000000000000000000000000111100";
pkts(199).tx <= '1'; pkts(199).data <= "001100000100100111000000000000000000000000000000000000000011110000";
pkts(200).tx <= '1'; pkts(200).data <= "001100000100100111000000000000001000000000000000000000000000111100";
pkts(201).tx <= '1'; pkts(201).data <= "001100000100100111000000000000001000000000000000000000000000001010";
pkts(202).tx <= '0'; pkts(202).data <= "000010011000100111000000000000001100000000000000000000000001000110";
pkts(203).tx <= '1'; pkts(203).data <= "001100000100100111000000000000000000000000000000000000000011110000";
pkts(204).tx <= '1'; pkts(204).data <= "001100000100100111000000000000001000000000000000000000000001000110";
pkts(205).tx <= '1'; pkts(205).data <= "001100000100100111000000000000001000000000000000000000000000001010";
pkts(206).tx <= '0'; pkts(206).data <= "000010011000100111000000000000001100000000000000000000000001010000";
pkts(207).tx <= '1'; pkts(207).data <= "001100000100100111000000000000000000000000000000000000000011110000";
pkts(208).tx <= '1'; pkts(208).data <= "001100000100100111000000000000001000000000000000000000000001010000";
pkts(209).tx <= '1'; pkts(209).data <= "001100000100100111000000000000001000000000000000000000000000001010";
pkts(210).tx <= '0'; pkts(210).data <= "000010011000100111000000000000001100000000000000000000000001011010";
pkts(211).tx <= '1'; pkts(211).data <= "001100000100100111000000000000000000000000000000000000000011110000";
pkts(212).tx <= '1'; pkts(212).data <= "001100000100100111000000000000001000000000000000000000000001011010";
pkts(213).tx <= '1'; pkts(213).data <= "001100000100100111000000000000001000000000000000000000000000001010";
pkts(214).tx <= '0'; pkts(214).data <= "000010011000100111000000000000001100000000000000000000000001100100";
pkts(215).tx <= '1'; pkts(215).data <= "001100000100101000000000000000001100000000000000000000000001100100";
        
        
        reset_i <= '0';
        wait for 1000 ns;
        
        report "Reset";
        sig_noc_wr(NOC_SW_NODE_ADDR) <= '0';
        sig_noc_rd(NOC_SW_NODE_ADDR) <= '0';
        sig_noc_din(NOC_SW_NODE_ADDR) <= conv_std_logic_vector(0,66);       
        
        reset_i <= '1';
        
        report "Dando um tempo..";
        wait for 1 ms;
        
        
        report "Sync";
        wait until rising_edge(clk_i);
        
        report "Iniciando";
        
        for i in 0 to N_PKTS-1 loop
            sig_noc_wr(NOC_SW_NODE_ADDR) <= '0';
            sig_noc_rd(NOC_SW_NODE_ADDR) <= '0';
            
            report "Pkt(" & integer'image(i) & ")";
            
            if(pkts(i).tx = '0') then
                report "Transmiting " & to_string(pkts(i).data);
                while sig_noc_wait(NOC_SW_NODE_ADDR)='1' loop
                    wait until rising_edge(clk_i);
                end loop;
                sig_noc_wr(NOC_SW_NODE_ADDR) <= '1';
                sig_noc_din(NOC_SW_NODE_ADDR) <= pkts(i).data;
                wait until rising_edge(clk_i);
                sig_noc_wr(NOC_SW_NODE_ADDR) <= '0';
            else
                report "Waiting Pkt";
                while sig_noc_nd(NOC_SW_NODE_ADDR)='0' loop
                    wait until rising_edge(clk_i);
                end loop;
                sig_noc_rd(NOC_SW_NODE_ADDR) <= '1';
                pkts(i).compare <= sig_noc_dout(NOC_SW_NODE_ADDR);
                wait until rising_edge(clk_i);
                sig_noc_rd(NOC_SW_NODE_ADDR) <= '0';
                report "Received " & to_string(pkts(i).compare);
            end if;          
            wait until rising_edge(clk_i);    
        end loop;
        
        report "Comparando";
        
        for i in 0 to N_PKTS-1 loop            
            if(pkts(i).tx = '1') then
                if (pkts(i).compare /= pkts(i).data) then
                    report "Erro no pkt " & integer'image(i) & " valor " & to_string(pkts(i).compare) & " deveria ser " & to_string(pkts(i).data);
                    finish(0);             
                end if;
            end if;                      
        end loop;
        
        report "fim";
        

        finish(0);
    end process;
        
    
end Behavioral;
