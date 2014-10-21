library ieee;
use ieee.std_logic_1164.all;

entity platform is
    port (
        rst             : in std_logic;
        clk             : in std_logic;
        uart_rx         : in std_logic;
        uart_tx         : out std_logic;
        gpio_leds       : out std_logic_vector(7 downto 0);
        switches        : in std_logic_vector(7 downto 0);
        leds            : out std_logic_vector(3 downto 0);
        buttons         : in std_logic_vector(3 downto 0)
    );
end platform;

architecture rtl of platform is
    signal rst_s        : std_logic;
    signal gpio_i_s     : std_logic_vector(31 downto 0);
    signal gpio_o_s     : std_logic_vector(31 downto 0);
    signal ext_int_s    : std_logic_vector(7 downto 0);
begin
    u_axi4_reset_control : entity work.axi4_reset_control
        port map (
            clk_i       => clk,
            ext_reset_i => not rst,
            axi_reset_o => rst_s
        );
    
    u_basic : entity  work.basic
        generic map (
            CLK_FREQ    => 50_000_000
        )
        port map (
            clk_i       => clk,
            reset_i     => rst_s,
            uart_tx_o   => uart_tx,
            uart_rx_i   => uart_rx,
            uart_baud_o => open,
            gpio_i      => gpio_i_s,
            gpio_o      => gpio_o_s,
            ext_int_i   => ext_int_s
        );
    
    gpio_i_s(11 downto 0)   <= buttons & switches;
    gpio_i_s(31 downto 12)  <= (others => '0');
    gpio_leds               <= gpio_o_s(7 downto 0);
    leds                    <= gpio_o_s(11 downto 8);
    ext_int_s(3 downto 0)   <= buttons;
    ext_int_s(7 downto 4)   <= (others => '0');
end rtl;
