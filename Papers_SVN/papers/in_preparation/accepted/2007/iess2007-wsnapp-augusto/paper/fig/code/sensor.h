parclass SensorNode
{
    public:
	SensorNode(int node, string machine) @{ od.url(machine);};

	async seq void setLEDs(char val);
	sync conc int getTemperature();

};
