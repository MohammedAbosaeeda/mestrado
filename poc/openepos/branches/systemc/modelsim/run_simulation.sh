echo "Simulating $1 > ($1).txt/vcd"
cat common/sim_setup.do > tmp.do
cat common/sim_comp.do >> tmp.do
cat $1 >> tmp.do
echo "vcd file $1.vcd" >> tmp.do
echo "vcd add -r -file $1.vcd /*" >> tmp.do
echo "run -all" >> tmp.do
echo "vcd flush trace.vcd" >> tmp.do 
cat common/sim_run.do >> tmp.do
vsim < tmp.do > $1.txt
