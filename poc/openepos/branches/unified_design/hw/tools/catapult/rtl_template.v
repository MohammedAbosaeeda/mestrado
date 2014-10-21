
module XXNAMEXX_Node_RTL (
    clk_i, rst_i,
    
    din_o, wr_o, rd_o, dout_i, wait_i, nd_i,
    
    iid_i,
    
    db_rx_vz, db_rx_lz, db_tx_vz, db_tx_lz
    );
   
    parameter X = 1'b1;
    parameter Y = 1'b1;
    parameter LOCAL_ADDR = 3'b101;
    parameter SIZE_X = 1; //Log2
    parameter SIZE_Y = 1; //Log2
    parameter SIZE_DATA = 56;
    parameter RMI_MSG_SIZE = 80;
    parameter IID_SIZE = 8;
    
    localparam IID_N_IDS = XXNIDSXX; 
    
    localparam BUS_SIZE = SIZE_DATA+(2*SIZE_X)+(2*SIZE_Y)+6;
    
    
    //Ports
    input clk_i;
    input rst_i;
    
    //RTSNoC
    output [BUS_SIZE-1:0] din_o;
    output wr_o;
    output rd_o;
    input [BUS_SIZE-1:0] dout_i;
    input wait_i;
    input nd_i;
    
    input [(IID_SIZE*IID_N_IDS)-1:0] iid_i;
    
    //Debug
    output db_rx_vz;
    output db_rx_lz;
    output db_tx_vz;
    output db_tx_lz;
    
    // /////////////////////////////////////////
    wire [RMI_MSG_SIZE-1:0] rx_ch_z;
    wire rx_ch_vz;
    wire rx_ch_lz;
    wire [RMI_MSG_SIZE-1:0] tx_ch_z;
    wire tx_ch_vz;
    wire tx_ch_lz;
    
    rtsnoc_to_achannel#(
        .X(X),
        .Y(Y),
        .LOCAL_ADDR(LOCAL_ADDR),
        .SIZE_X(SIZE_X), //Log2
        .SIZE_Y(SIZE_Y), //Log2
        .SIZE_DATA(SIZE_DATA),
        .RMI_MSG_SIZE(RMI_MSG_SIZE)
    )
    to_achannel (
    	.clk_i(clk_i),
    	.rst_i(rst_i),
    	.din_o(din_o),
    	.wr_o(wr_o),
    	.rd_o(rd_o),
    	.dout_i(dout_i),
    	.wait_i(wait_i),
    	.nd_i(nd_i),
    	.rx_ch_z_o(rx_ch_z),
    	.rx_ch_vz_o(rx_ch_vz),
    	.rx_ch_lz_i(rx_ch_lz),
    	.tx_ch_z_i(tx_ch_z),
    	.tx_ch_vz_o(tx_ch_vz),
    	.tx_ch_lz_i(tx_ch_lz)
    );
    
    XXNAMEXX_Node node(
    	.rx_ch_rsc_z(rx_ch_z),
    	.rx_ch_rsc_vz(rx_ch_vz),
    	.rx_ch_rsc_lz(rx_ch_lz),
    	.tx_ch_rsc_z(tx_ch_z),
    	.tx_ch_rsc_vz(tx_ch_vz),
    	.tx_ch_rsc_lz(tx_ch_lz),
    	.iid(iid_i),
    	.clk(clk_i),
    	.rst(rst_i)
    );
    
    // Debug
    assign db_rx_lz = rx_ch_lz;
    assign db_rx_vz = rx_ch_vz;
    assign db_tx_lz = tx_ch_lz;
    assign db_tx_vz = tx_ch_vz;
            
endmodule
