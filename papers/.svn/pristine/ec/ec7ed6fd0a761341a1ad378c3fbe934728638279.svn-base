<machine name="ORSoC">
  <synthesis interconnection="wishbone"/>
  
  <processor name="OR1200" clock="300000000" 
      word_size="32" endianess="big" 
      synthesizable="true"/>

  <memory name="sram" base="0" size="0x100000" 
      synthesizable="true">
    <trait name="readwrite" type="bool" value="true"/>
    <trait name="adr_i_hi" type="int"  value="11"/>
    <trait name="adr_i_lo" type="int"  value="2"/>
    <trait name="tga_i" type="int"  value="0"/>
    <trait name="tgc_i" type="int"  value="0"/>
    <trait name="tgd_i" type="int"  value="0"/>  
    <trait name="lock_i" type="int"  value="0"/>
    <trait name="err_o" type="int"  value="0"/>
    <trait name="rty_o" type="int"  value="0"/>
  </memory>

  <bus name="wishbone" synthesizable="true">
    <trait name="endofburst" type="int"  value="111"/>
    <trait name="dat_size" type="int"  value="32"/>
    <trait name="adr_size" type="int"  value="32"/>
    <trait name="muxtype" type="int"  value="ANDOR"/>
    <trait name="classic" type="int"  value="000"/>
    <trait name="tga_bits" type="int"  value="2"/>
    <trait name="tgc_bits" type="int"  value="3"/>
    <trait name="tgd_bits" type="int"  value="0"/>
    <trait name="rename_tga" type="int"  value="BTE"/>
    <trait name="rename_tgc" type="int"  value="CTI"/>
    <trait name="rename_tgd" type="int"  value="TGD"/>
    <trait name="signal_groups" type="int"  value="1"/>
  </bus>

  <device name="UART" bus=""wishbone" class="Serial" 
      synthesizable="true"/>
</machine>
