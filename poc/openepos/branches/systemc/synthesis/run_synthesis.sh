cat agility_opt > run_edif.tmp
cat agility_opt_vhdl > run_vhdl.tmp
cat $1 >> run_edif.tmp
cat $1 >> run_vhdl.tmp
sh run_vhdl.tmp
sh run_edif.tmp
cp output output.edif
