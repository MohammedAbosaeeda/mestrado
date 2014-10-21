library ieee;
use ieee.std_logic_1164.all;
use ieee.numeric_std.all;
use ieee.math_real.all;

entity platform is
    port (
        ps_srstb        : in std_logic;
        ps_clk          : in std_logic;
        ps_porb         : in std_logic;
        ddr_clk         : inout std_logic;
        ddr_clk_n       : inout std_logic;
        ddr_cke         : inout std_logic;
        ddr_cs_n        : inout std_logic;
        ddr_ras_n       : inout std_logic;
        ddr_cas_n       : inout std_logic;
        ddr_web_pin     : out std_logic;
        ddr_bankaddr    : inout std_logic_vector(2 downto 0);
        ddr_addr        : inout std_logic_vector(14 downto 0);
        ddr_odt         : inout std_logic;
        ddr_drstb       : inout std_logic;
        ddr_dq          : inout std_logic_vector(31 downto 0);
        ddr_dm          : inout std_logic_vector(3 downto 0);
        ddr_dqs         : inout std_logic_vector(3 downto 0);
        ddr_dqs_n       : inout std_logic_vector(3 downto 0);
        ddr_vrn         : inout std_logic;
        ddr_vrp         : inout std_logic;
        gpio            : inout std_logic_vector(59 downto 0);
        mio             : inout std_logic_vector(53 downto 0)
    );
end platform;

architecture structure of platform is
    constant N_SLAVES           : integer                                       := 1;
    constant N_SLAVES_LOG2      : integer                                       := integer(ceil(log2(real(N_SLAVES))));
    constant SLAVE0_ADDR_W      : integer                                       := 20;
    constant SLAVE0_ADDR        : std_logic_vector(SLAVE0_ADDR_W-1 downto 0)    := x"41280";

    constant NET_SIZE_X         : integer := 1;
    constant NET_SIZE_Y         : integer := 1;
    constant NET_SIZE_X_LOG2    : integer := 1;
    constant NET_SIZE_Y_LOG2    : integer := 1;
    constant NET_DATA_WIDTH     : integer := 56;
    constant NET_BUS_SIZE       : integer := NET_DATA_WIDTH+(2*NET_SIZE_X_LOG2)+(2*NET_SIZE_Y_LOG2)+6;
    constant PROC_NOC_BUS_RATE  : integer := 2;
    constant ROUTER_N_PORTS     : integer := 8;
    constant ROUTER_NN          : integer := 0;
    constant ROUTER_NE          : integer := 1;
    constant ROUTER_EE          : integer := 2;
    constant ROUTER_SE          : integer := 3;
    constant ROUTER_SS          : integer := 4;
    constant ROUTER_SW          : integer := 5;
    constant ROUTER_WW          : integer := 6;
    constant ROUTER_NW          : integer := 7;
    constant ROUTER_X           : integer := 0;
    constant ROUTER_Y           : integer := 0;

    type noc_array_of_stdlogic is array(0 to ROUTER_N_PORTS-1) of std_logic;
    type noc_array_of_stdvec38 is array(0 to ROUTER_N_PORTS-1) of std_logic_vector(NET_BUS_SIZE-1 downto 0);

    component ps is
        port (
            ps_srstb        : in std_logic;
            ps_clk          : in std_logic;
            ps_porb         : in std_logic;
            ddr_clk         : inout std_logic;
            ddr_clk_n       : inout std_logic;
            ddr_cke         : inout std_logic;
            ddr_cs_n        : inout std_logic;
            ddr_ras_n       : inout std_logic;
            ddr_cas_n       : inout std_logic;
            ddr_web_pin     : out std_logic;
            ddr_bankaddr    : inout std_logic_vector(2 downto 0);
            ddr_addr        : inout std_logic_vector(14 downto 0);
            ddr_odt         : inout std_logic;
            ddr_drstb       : inout std_logic;
            ddr_dq          : inout std_logic_vector(31 downto 0);
            ddr_dm          : inout std_logic_vector(3 downto 0);
            ddr_dqs         : inout std_logic_vector(3 downto 0);
            ddr_dqs_n       : inout std_logic_vector(3 downto 0);
            ddr_vrn         : inout std_logic;
            ddr_vrp         : inout std_logic;
            fclk_rst0_n     : out std_logic;
            fclk_clk0       : out std_logic;
            gpio            : inout std_logic_vector(59 downto 0);
            mio             : inout std_logic_vector(53 downto 0);
            m_axi_awaddr    : out std_logic_vector(31 downto 0);
            m_axi_awvalid   : out std_logic;
            m_axi_awready   : in std_logic;
            m_axi_wdata     : out std_logic_vector(31 downto 0);
            m_axi_wstrb     : out std_logic_vector(3 downto 0);
            m_axi_wvalid    : out std_logic;
            m_axi_wready    : in std_logic;
            m_axi_bresp     : in std_logic_vector(1 downto 0);
            m_axi_bvalid    : in std_logic;
            m_axi_bready    : out std_logic;
            m_axi_araddr    : out std_logic_vector(31 downto 0);
            m_axi_arvalid   : out std_logic;
            m_axi_arready   : in std_logic;
            m_axi_rdata     : in std_logic_vector(31 downto 0);
            m_axi_rresp     : in std_logic_vector(1 downto 0);
            m_axi_rvalid    : in std_logic;
            m_axi_rready    : out std_logic;
            m_axi_arprot    : out std_logic_vector(2 downto 0);
            m_axi_awprot    : out std_logic_vector(2 downto 0)
        );
    end component;

    attribute box_type          : string;
    attribute box_type of ps    : component is "user_black_box";

    signal fclk_clk0_s          : std_logic;
    signal fclk_rst0_n_s        : std_logic;

    signal m_axi_awaddr_s       : std_logic_vector(31 downto 0);
    signal m_axi_awvalid_s      : std_logic;
    signal m_axi_awready_s      : std_logic;
    signal m_axi_wdata_s        : std_logic_vector(31 downto 0);
    signal m_axi_wstrb_s        : std_logic_vector(3 downto 0);
    signal m_axi_wvalid_s       : std_logic;
    signal m_axi_wready_s       : std_logic;
    signal m_axi_bresp_s        : std_logic_vector(1 downto 0);
    signal m_axi_bvalid_s       : std_logic;
    signal m_axi_bready_s       : std_logic;
    signal m_axi_araddr_s       : std_logic_vector(31 downto 0);
    signal m_axi_arvalid_s      : std_logic;
    signal m_axi_arready_s      : std_logic;
    signal m_axi_rdata_s        : std_logic_vector(31 downto 0);
    signal m_axi_rresp_s        : std_logic_vector(1 downto 0);
    signal m_axi_rvalid_s       : std_logic;
    signal m_axi_rready_s       : std_logic;
    signal m_axi_arprot_s       : std_logic_vector(2 downto 0);
    signal m_axi_awprot_s       : std_logic_vector(2 downto 0);

    signal s_axi_awvalid_s      : std_logic_vector(N_SLAVES-1 downto 0);
    signal s_axi_awready_s      : std_logic_vector(N_SLAVES-1 downto 0);
    signal s_axi_awaddr_s       : std_logic_vector((N_SLAVES*32)-1 downto 0);
    signal s_axi_awprot_s       : std_logic_vector((N_SLAVES*3)-1 downto 0);
    signal s_axi_wvalid_s       : std_logic_vector(N_SLAVES-1 downto 0);
    signal s_axi_wready_s       : std_logic_vector(N_SLAVES-1 downto 0);
    signal s_axi_wdata_s        : std_logic_vector((N_SLAVES*32)-1 downto 0);
    signal s_axi_wstrb_s        : std_logic_vector((N_SLAVES*4)-1 downto 0);
    signal s_axi_bvalid_s       : std_logic_vector(N_SLAVES-1 downto 0);
    signal s_axi_bready_s       : std_logic_vector(N_SLAVES-1 downto 0);
    signal s_axi_bresp_s        : std_logic_vector((N_SLAVES*2)-1 downto 0);
    signal s_axi_arvalid_s      : std_logic_vector(N_SLAVES-1 downto 0);
    signal s_axi_arready_s      : std_logic_vector(N_SLAVES-1 downto 0);
    signal s_axi_araddr_s       : std_logic_vector((N_SLAVES*32)-1 downto 0);
    signal s_axi_arprot_s       : std_logic_vector((N_SLAVES*3)-1 downto 0);
    signal s_axi_rvalid_s       : std_logic_vector(N_SLAVES-1 downto 0);
    signal s_axi_rready_s       : std_logic_vector(N_SLAVES-1 downto 0);
    signal s_axi_rdata_s        : std_logic_vector((N_SLAVES*32)-1 downto 0);
    signal s_axi_rresp_s        : std_logic_vector((N_SLAVES*2)-1 downto 0);

    signal noc_din_s            : noc_array_of_stdvec38;
    signal noc_dout_s           : noc_array_of_stdvec38;
    signal noc_wr_s             : noc_array_of_stdlogic;
    signal noc_rd_s             : noc_array_of_stdlogic;
    signal noc_wait_s           : noc_array_of_stdlogic;
    signal noc_nd_s             : noc_array_of_stdlogic;
    signal noc_rst_s            : std_logic;
begin
    u_ps : ps
        port map (
            ps_srstb        => ps_srstb,
            ps_clk          => ps_clk,
            ps_porb         => ps_porb,
            ddr_clk         => ddr_clk,
            ddr_clk_n       => ddr_clk_n,
            ddr_cke         => ddr_cke,
            ddr_cs_n        => ddr_cs_n,
            ddr_ras_n       => ddr_ras_n,
            ddr_cas_n       => ddr_cas_n,
            ddr_web_pin     => ddr_web_pin,
            ddr_bankaddr    => ddr_bankaddr,
            ddr_addr        => ddr_addr,
            ddr_odt         => ddr_odt,
            ddr_drstb       => ddr_drstb,
            ddr_dq          => ddr_dq,
            ddr_dm          => ddr_dm,
            ddr_dqs         => ddr_dqs,
            ddr_dqs_n       => ddr_dqs_n,
            ddr_vrn         => ddr_vrn,
            ddr_vrp         => ddr_vrp,
            gpio            => gpio,
            mio             => mio,
            fclk_rst0_n     => fclk_rst0_n_s,
            fclk_clk0       => fclk_clk0_s,
            m_axi_awaddr    => m_axi_awaddr_s,
            m_axi_awvalid   => m_axi_awvalid_s,
            m_axi_awready   => m_axi_awready_s,
            m_axi_wdata     => m_axi_wdata_s,
            m_axi_wstrb     => m_axi_wstrb_s,
            m_axi_wvalid    => m_axi_wvalid_s,
            m_axi_wready    => m_axi_wready_s,
            m_axi_bresp     => m_axi_bresp_s,
            m_axi_bvalid    => m_axi_bvalid_s,
            m_axi_bready    => m_axi_bready_s,
            m_axi_araddr    => m_axi_araddr_s,
            m_axi_arvalid   => m_axi_arvalid_s,
            m_axi_arready   => m_axi_arready_s,
            m_axi_rdata     => m_axi_rdata_s,
            m_axi_rresp     => m_axi_rresp_s,
            m_axi_rvalid    => m_axi_rvalid_s,
            m_axi_rready    => m_axi_rready_s,
            m_axi_arprot    => m_axi_arprot_s,
            m_axi_awprot    => m_axi_awprot_s
        );

    u_axi4lite_decoder : entity work.axi4lite_decoder
        generic map (
            SW          => N_SLAVES,
            SW_LOG2     => N_SLAVES_LOG2,
            S0_ADDR_W   => SLAVE0_ADDR_W,
            S0_ADDR     => to_integer(unsigned(SLAVE0_ADDR)),
            S1_ADDR_W   => 0,
            S1_ADDR     => 0,
            S2_ADDR_W   => 0,
            S2_ADDR     => 0,
            S3_ADDR_W   => 0,
            S3_ADDR     => 0,
            S4_ADDR_W   => 0,
            S4_ADDR     => 0,
            S5_ADDR_W   => 0,
            S5_ADDR     => 0,
            S6_ADDR_W   => 0,
            S6_ADDR     => 0,
            S7_ADDR_W   => 0,
            S7_ADDR     => 0
        )
        port map (
            m_awvalid_i => m_axi_awvalid_s,
            m_awready_o => m_axi_awready_s,
            m_awaddr_i  => m_axi_awaddr_s,
            m_awprot_i  => m_axi_awprot_s,
            m_wvalid_i  => m_axi_wvalid_s,
            m_wready_o  => m_axi_wready_s,
            m_wdata_i   => m_axi_wdata_s,
            m_wstrb_i   => m_axi_wstrb_s,
            m_bvalid_o  => m_axi_bvalid_s,
            m_bready_i  => m_axi_bready_s,
            m_bresp_o   => m_axi_bresp_s,
            m_arvalid_i => m_axi_arvalid_s,
            m_arready_o => m_axi_arready_s,
            m_araddr_i  => m_axi_araddr_s,
            m_arprot_i  => m_axi_arprot_s,
            m_rvalid_o  => m_axi_rvalid_s,
            m_rready_i  => m_axi_rready_s,
            m_rdata_o   => m_axi_rdata_s,
            m_rresp_o   => m_axi_rresp_s,
            s_awvalid_o => s_axi_awvalid_s,
            s_awready_i => s_axi_awready_s,
            s_awaddr_o  => s_axi_awaddr_s,
            s_awprot_o  => s_axi_awprot_s,
            s_wvalid_o  => s_axi_wvalid_s,
            s_wready_i  => s_axi_wready_s,
            s_wdata_o   => s_axi_wdata_s,
            s_wstrb_o   => s_axi_wstrb_s,
            s_bvalid_i  => s_axi_bvalid_s,
            s_bready_o  => s_axi_bready_s,
            s_bresp_i   => s_axi_bresp_s,
            s_arvalid_o => s_axi_arvalid_s,
            s_arready_i => s_axi_arready_s,
            s_araddr_o  => s_axi_araddr_s,
            s_arprot_o  => s_axi_arprot_s,
            s_rvalid_i  => s_axi_rvalid_s,
            s_rready_o  => s_axi_rready_s,
            s_rdata_i   => s_axi_rdata_s,
            s_rresp_i   => s_axi_rresp_s
        );

    u_rtsnoc_axi4lite_proxy : entity work.rtsnoc_axi4lite_proxy
        generic map (
            NOC_LOCAL_ADR       => ROUTER_NN,
            NOC_X               => ROUTER_X,
            NOC_Y               => ROUTER_Y,
            SOC_SIZE_X          => NET_SIZE_X_LOG2,
            SOC_SIZE_Y          => NET_SIZE_Y_LOG2,
            NOC_DATA_WIDTH      => NET_DATA_WIDTH,
            PROC_NOC_BUS_RATE   => PROC_NOC_BUS_RATE
        )
        port map (
            clk_i               => fclk_clk0_s,
            axi_rst_i           => fclk_rst0_n_s,
            noc_rst_i           => noc_rst_s,
            axi_awaddr_i        => s_axi_awaddr_s,
            axi_awvalid_i       => s_axi_awvalid_s(0),
            axi_awready_o       => s_axi_awready_s(0),
            axi_wdata_i         => s_axi_wdata_s,
            axi_wstrb_i         => s_axi_wstrb_s,
            axi_wvalid_i        => s_axi_wvalid_s(0),
            axi_wready_o        => s_axi_wready_s(0),
            axi_bresp_o         => s_axi_bresp_s,
            axi_bvalid_o        => s_axi_bvalid_s(0),
            axi_bready_i        => s_axi_bready_s(0),
            axi_araddr_i        => s_axi_araddr_s,
            axi_arvalid_i       => s_axi_arvalid_s(0),
            axi_arready_o       => s_axi_arready_s(0),
            axi_rdata_o         => s_axi_rdata_s,
            axi_rresp_o         => s_axi_rresp_s,
            axi_rvalid_o        => s_axi_rvalid_s(0),
            axi_rready_i        => s_axi_rready_s(0),
            noc_din_o           => noc_din_s(ROUTER_NN),
            noc_dout_i          => noc_dout_s(ROUTER_NN),
            noc_wr_o            => noc_wr_s(ROUTER_NN),
            noc_rd_o            => noc_rd_s(ROUTER_NN),
            noc_wait_i          => noc_wait_s(ROUTER_NN),
            noc_nd_i            => noc_nd_s(ROUTER_NN),
            noc_int_o           => open
        );

    u_router : entity work.router
        generic map (
            P_X         => ROUTER_X,
            P_Y         => ROUTER_Y,
            P_DATA      => NET_DATA_WIDTH,
            P_SIZE_X    => NET_SIZE_X_LOG2,
            P_SIZE_Y    => NET_SIZE_Y_LOG2
        )
        port map(
            o_teste     => open,
            i_clk       => fclk_clk0_s,
            i_rst       => noc_rst_s,
            i_din_nn    => noc_din_s(ROUTER_NN),
            o_dout_nn   => noc_dout_s(ROUTER_NN),
            i_wr_nn     => noc_wr_s(ROUTER_NN),
            i_rd_nn     => noc_rd_s(ROUTER_NN),
            o_wait_nn   => noc_wait_s(ROUTER_NN),
            o_nd_nn     => noc_nd_s(ROUTER_NN),
            i_din_ne    => noc_din_s(ROUTER_NE),
            o_dout_ne   => noc_dout_s(ROUTER_NE),
            i_wr_ne     => noc_wr_s(ROUTER_NE),
            i_rd_ne     => noc_rd_s(ROUTER_NE),
            o_wait_ne   => noc_wait_s(ROUTER_NE),
            o_nd_ne     => noc_nd_s(ROUTER_NE),
            i_din_ee    => noc_din_s(ROUTER_EE),
            o_dout_ee   => noc_dout_s(ROUTER_EE),
            i_wr_ee     => noc_wr_s(ROUTER_EE),
            i_rd_ee     => noc_rd_s(ROUTER_EE),
            o_wait_ee   => noc_wait_s(ROUTER_EE),
            o_nd_ee     => noc_nd_s(ROUTER_EE),
            i_din_se    => noc_din_s(ROUTER_SE),
            o_dout_se   => noc_dout_s(ROUTER_SE),
            i_wr_se     => noc_wr_s(ROUTER_SE),
            i_rd_se     => noc_rd_s(ROUTER_SE),
            o_wait_se   => noc_wait_s(ROUTER_SE),
            o_nd_se     => noc_nd_s(ROUTER_SE),
            i_din_ss    => noc_din_s(ROUTER_SS),
            o_dout_ss   => noc_dout_s(ROUTER_SS),
            i_wr_ss     => noc_wr_s(ROUTER_SS),
            i_rd_ss     => noc_rd_s(ROUTER_SS),
            o_wait_ss   => noc_wait_s(ROUTER_SS),
            o_nd_ss     => noc_nd_s(ROUTER_SS),
            i_din_sw    => noc_din_s(ROUTER_SW),
            o_dout_sw   => noc_dout_s(ROUTER_SW),
            i_wr_sw     => noc_wr_s(ROUTER_SW),
            i_rd_sw     => noc_rd_s(ROUTER_SW),
            o_wait_sw   => noc_wait_s(ROUTER_SW),
            o_nd_sw     => noc_nd_s(ROUTER_SW),
            i_din_ww    => noc_din_s(ROUTER_WW),
            o_dout_ww   => noc_dout_s(ROUTER_WW),
            i_wr_ww     => noc_wr_s(ROUTER_WW),
            i_rd_ww     => noc_rd_s(ROUTER_WW),
            o_wait_ww   => noc_wait_s(ROUTER_WW),
            o_nd_ww     => noc_nd_s(ROUTER_WW),
            i_din_nw    => noc_din_s(ROUTER_NW),
            o_dout_nw   => noc_dout_s(ROUTER_NW),
            i_wr_nw     => noc_wr_s(ROUTER_NW),
            i_rd_nw     => noc_rd_s(ROUTER_NW),
            o_wait_nw   => noc_wait_s(ROUTER_NW),
            o_nd_nw     => noc_nd_s(ROUTER_NW)
        );

    u_rtsnoc_echo : entity work.rtsnoc_echo
        generic map (
            SOC_SIZE_X      => NET_SIZE_X_LOG2,
            SOC_SIZE_Y      => NET_SIZE_Y_LOG2,
            NOC_DATA_WIDTH  => NET_DATA_WIDTH,
            P0_ADDR         => ROUTER_WW,
            P1_ADDR         => ROUTER_SS
        )
        port map(
            clk_i           => fclk_clk0_s,
            rst_i           => noc_rst_s,
            p0_din_o        => noc_din_s(ROUTER_WW),
            p0_dout_i       => noc_dout_s(ROUTER_WW),
            p0_wr_o         => noc_wr_s(ROUTER_WW),
            p0_rd_o         => noc_rd_s(ROUTER_WW),
            p0_wait_i       => noc_wait_s(ROUTER_WW),
            p0_nd_i         => noc_nd_s(ROUTER_WW),
            p1_din_o        => noc_din_s(ROUTER_SS),
            p1_dout_i       => noc_dout_s(ROUTER_SS),
            p1_wr_o         => noc_wr_s(ROUTER_SS),
            p1_rd_o         => noc_rd_s(ROUTER_SS),
            p1_wait_i       => noc_wait_s(ROUTER_SS),
            p1_nd_i         => noc_nd_s(ROUTER_SS)
        );

    noc_rst_s <= not fclk_rst0_n_s;
end architecture structure;
