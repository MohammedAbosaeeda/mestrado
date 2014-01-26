<family name="CPU_IP" type="hardware" 
	class="dissociated">
  <interface>
  </interface>
  <common>
  </common>

  <member name="OR1200_I">
    <feature name="interconnection" value="wishbone"/>
    <trait name="readwrite/" type="bool" value="false"/>
    <trait name="lock_o"     type="int"  value="0"/>
    <trait name="tga_o"      type="int"  value="1"/>
    <trait name="tgc_o"      type="int"  value="1"/> 
    ...
  </member>

  <member name="OR1200_D">
    <feature name="interconnection" value="wishbone"/>
    <trait name="readwrite " type="bool" value="true"/>
    <trait name="lock_o"     type="int"  value="0"/>
    <trait name="tga_o"      type="int"  value="1"/>
    <trait name="tgc_o"      type="int"  value="1"/>
    ...
  </member>  
  ...
</family>
