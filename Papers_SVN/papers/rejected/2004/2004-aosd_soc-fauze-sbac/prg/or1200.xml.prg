<family name="CPU" type="mediator" 
	class="dissociated">
  ...
  <member name="OR1200">
    <method name="clock" return="Hertz"/>
    <method name="int_enable"/>
    <method name="int_disable"/>
    <method name="switch_context" qualifiers="class">
      <parameter name="current" 
	  type="Context * volatile *"/>
      <parameter name="next" type="Context * volatile"/>
    </method>
    ...
    <feature name="synthesizable" value="true">
      <dependency name="::CPU_IP::OR1200_I" 
	  value="true"/>
      <dependency name="::CPU_IP::OR1200_D" 
	  value="true"/>  
    </feature> 
  </member>
  ...
</family>
