<family name="CPU" type="mediator" class="dissociated">
 <interface>
  <constructor/>
  <method name="clock" return="Hertz" qualifiers="class"/>
  <method name="int_enable" qualifiers="class"/>
  <method name="int_disable" qualifiers="class"/>
  <method name="switch_context" qualifiers="class">
   <parameter name="current" type="Context * volatile *"/>
   <parameter name="next" type="Context * volatile"/>
   ...
 </interface>
 <common>
  <type name="Reg8" type="synonym" 
	value="unsigned char"/>
  <type name="Reg16" type="synonym" 
	value="unsigned short"/>
  <type name="Reg32" type="synonym" 
	value="unsigned long"/>
  <type name="Reg64" type="synonym" 
	value="unsigned long long"/>
 </common>
 <member name="IA32" type="exclusive">
  <method name="clock" return="Hertz"/>
  <method name="int_enable"/>
  <method name="int_disable"/>
  <method name="switch_context" qualifiers="class">
   <parameter name="current" 
	type="Context * volatile *"/>
   <parameter name="next" type="Context * volatile"/>
  </method>
  ...
 <member/>
 <member name="OR1200" type="exclusive">
  <method name="clock" return="Hertz"/>
  <method name="int_enable"/>
  <method name="int_disable"/>
  <method name="switch_context" qualifiers="class">
   <parameter name="current" 
	type="Context * volatile *"/>
   <parameter name="next" type="Context * volatile"/>
  </method>
  ...
 <member/>
</family>
