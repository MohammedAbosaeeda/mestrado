class ADC {
//...

static const unsigned int div_resistor = 10000; // 10 kohm    
static const unsigned int adc_max_value = 1023; // 10-bits ADC

public:
    float getResistance() {
        return div_resistor * ((float)adc_max_value / this->read() - 1);
    }

//...
};

class Thermistor {
//...

static const float A = 0.0010750492; 
static const float B = 0.00027028218;   
static const float C = 0.00000014524838;
static const float Correction = 2.37;
static const float Kelvin_to_Celsius = -273.15;

public:

float sample() {
    float logR = logf(adc.getResistance());
    float T = (1 / (A + B*logR + C*logR*logR*logR)) + Correction;
    if(mode == CELSIUS) return T + Kelvin_to_Celsius;
    else return T;
}

};
