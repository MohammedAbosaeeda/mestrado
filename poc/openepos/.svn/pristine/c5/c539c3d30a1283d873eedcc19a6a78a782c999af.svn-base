// OpenEPOS EMote2ARM_Battery Mediator Implementation

#include <battery.h>
#include <adc.h>
#include <mach/emote2arm/buck_regulator.h>

__BEGIN_SYS

EMote2ARM_Battery * EMote2ARM_Battery::system_battery;

__END_SYS

__USING_SYS

EMote2ARM_Battery ::EMote2ARM_Battery() :
		_adc() {
	db<EMote2ARM_Battery>(TRC) << "EMote2ARM_Battery::EMote2ARM_Battery()\n";
}

const unsigned short EMote2ARM_Battery::get() {
	db<EMote2ARM_Battery>(TRC) << "EMote2ARM_Battery::get()\n";

	return _adc.get();
}

const unsigned short EMote2ARM_Battery::sample() {
	db<EMote2ARM_Battery>(TRC) << "EMote2ARM_Battery::sample()\n";

	return read_to_voltage(get());
}

const unsigned short EMote2ARM_Battery::charge() {
	db<EMote2ARM_Battery>(TRC) << "EMote2ARM_Battery::charge()\n";

	return read_to_charge(get());
}

EMote2ARM_Battery & EMote2ARM_Battery::sys_batt() {
	db<EMote2ARM_Battery>(TRC) << "EMote2ARM_Battery::sys_batt()\n";

	return *system_battery;
}
const unsigned short EMote2ARM_Battery::read_to_voltage(unsigned short value) {
	db<EMote2ARM_Battery>(TRC) << "EMote2ARM_Battery::read_to_voltage(" << value
			<< ")\n";

	if (value <= 0u)
		return 0;

	return (battery_reference * adc_max) / value;
}

const unsigned short EMote2ARM_Battery::read_to_charge(unsigned short value) {
	db<EMote2ARM_Battery>(TRC) << "EMote2ARM_Battery::read_to_charge(" << value
			<< ")\n";

	//"Dummy" linear model
	return 100 - (100 * (value - ADC_3p3v) / (ADC_1p8v - ADC_3p3v));
}

void EMote2ARM_Battery::check_buck() {
	db<EMote2ARM_Battery>(TRC) << "EMote2ARM_Battery::check_buck()\n";

	check_buck(get());
}

/* A future implementation should use ADCs threshold feature to avoid pooling of the battery voltage */
void EMote2ARM_Battery::check_buck(unsigned short read) {
	db<EMote2ARM_Battery>(TRC) << "EMote2ARM_Battery::check_buck(" << read
			<< ")\n";

	if ((read > buck_disable_adc_threshold)
			&& (!EMote2ARM_Buck_Regulator::is_in_bypass())) {
		EMote2ARM_Buck_Regulator::enter_bypass();
	} else if ((read < buck_enable_adc_threshold)
			&& (EMote2ARM_Buck_Regulator::is_in_bypass())) {
		EMote2ARM_Buck_Regulator::leave_bypass();
	}
}

const void EMote2ARM_Battery::battery_over_threshold_set(unsigned short value) {
	db<EMote2ARM_Battery>(TRC)
			<< "EMote2ARM_Battery::battery_over_threshold_set(" << value
			<< ")\n";

	// TODO: set 0x0200 in ?
}

const void EMote2ARM_Battery::battery_over_threshold_enable() {
	db<EMote2ARM_Battery>(TRC)
			<< "EMote2ARM_Battery::battery_over_threshold_enable()\n";

	// TODO: set 0x0200 in ?
}

const void EMote2ARM_Battery::battery_over_threshold_disable() {
	db<EMote2ARM_Battery>(TRC)
			<< "EMote2ARM_Battery::battery_over_threshold_disable()\n";

	// TODO: clear 0x0200 in ?
}

const void EMote2ARM_Battery::battery_under_threshold_set(
		unsigned short value) {
	db<EMote2ARM_Battery>(TRC)
			<< "EMote2ARM_Battery::battery_under_threshold_set(" << value
			<< ")\n";

	// TODO: clear 0x0200 in ?
}

const void EMote2ARM_Battery::battery_under_threshold_enable() {
	db<EMote2ARM_Battery>(TRC)
			<< "EMote2ARM_Battery::battery_under_threshold_enable()\n";

	// TODO: set 0x0100 in ?
}

const void EMote2ARM_Battery::battery_under_threshold_disable() {
	db<EMote2ARM_Battery>(TRC)
			<< "EMote2ARM_Battery::battery_under_threshold_disable()\n";

	// TODO: clear 0x0100 in ?
}
