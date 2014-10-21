// OpenEPOS EMote2ARM Sensors Mediator Declarations
// TODO: Needs review (is it realy a mediator?)

#ifndef __emote2arm_sensor_h
#define __emote2arm_sensor_h

#include <sensor.h>
#include <system/meta.h>
#include <traits.h>

#include "../common/thermistor.h"
#include "../common/adxl345.h"
#include "../common/microphone.h"

__BEGIN_SYS

class EMote2ARM_Temperature_Sensor: public Temperature_Sensor_Common {
private:
	typedef Traits<EMote2ARM_Temperature_Sensor>::SENSORS SENSORS;
	static const unsigned int UNITS = SENSORS::Length;

public:
	EMote2ARM_Temperature_Sensor() {
		_dev = new Meta_Temperature_Sensor<SENSORS>::Get<0>::Result;
	}
	template<unsigned int UNIT>
	EMote2ARM_Temperature_Sensor(unsigned int u) {
		_dev = new typename Meta_Temperature_Sensor<SENSORS>::Get<UNIT>::Result(
				UNIT);
	}

	~EMote2ARM_Temperature_Sensor() {
		delete _dev;
	}

	int sample() {
		return _dev->sample();
	}

	int get() {
		return _dev->get();
	}

	bool enable() {
		return _dev->enable();
	}

	void disable() {
		_dev->disable();
	}

	bool data_ready() {
		return _dev->data_ready();
	}

	static void init();

private:
	Meta_Temperature_Sensor<SENSORS>::Base * _dev;
};

class EMote2ARM_Audio_Sensor: public Audio_Sensor_Common {
private:
	typedef Traits<EMote2ARM_Audio_Sensor>::SENSORS SENSORS;
	static const unsigned int UNITS = SENSORS::Length;

public:
	EMote2ARM_Audio_Sensor() {
		_dev = new Meta_Audio_Sensor<SENSORS>::Get<0>::Result;
	}
	template<unsigned int UNIT>
	EMote2ARM_Audio_Sensor(unsigned int u) {
		_dev = new typename Meta_Audio_Sensor<SENSORS>::Get<UNIT>::Result(
				UNIT);
	}

	~EMote2ARM_Audio_Sensor() {
		delete _dev;
	}

	int sample() {
		return _dev->sample();
	}

	int get() {
		return _dev->get();
	}

	bool enable() {
		return _dev->enable();
	}

	void disable() {
		_dev->disable();
	}

	bool data_ready() {
		return _dev->data_ready();
	}

	static void init();

private:
	Meta_Audio_Sensor<SENSORS>::Base * _dev;
};

class EMote2ARM_Accelerometer: public Accelerometer_Common {
private:
	typedef Traits<EMote2ARM_Accelerometer>::SENSORS SENSORS;
	static const unsigned int UNITS = SENSORS::Length;

public:
	EMote2ARM_Accelerometer() {
		_dev = new Meta_Accelerometer<SENSORS>::Get<0>::Result;
	}
	template<unsigned int UNIT>
	EMote2ARM_Accelerometer(unsigned int u) {
		_dev = new typename Meta_Accelerometer<SENSORS>::Get<UNIT>::Result(
				UNIT);
	}

	~EMote2ARM_Accelerometer() {
		delete _dev;
	}

	int sample_x() {
		return _dev->sample_x();
	}

	int get_x() {
		return _dev->get_x();
	}

	bool enable_x() {
		return _dev->enable_x();
	}

	void disable_x() {
		_dev->disable_x();
	}

	bool data_ready_x() {
		return _dev->data_ready_x();
	}

	int sample_y() {
		return _dev->sample_y();
	}

	int get_y() {
		return _dev->get_y();
	}

	bool enable_y() {
		return _dev->enable_y();
	}

	void disable_y() {
		_dev->disable_y();
	}

	bool data_ready_y() {
		return _dev->data_ready_y();
	}

	int sample_z() {
		return _dev->sample_z();
	}

	int get_z() {
		return _dev->get_z();
	}

	bool enable_z() {
		return _dev->enable_z();
	}

	void disable_z() {
		_dev->disable_z();
	}

	bool data_ready_z() {
		return _dev->data_ready_z();
	}

	static void init();

private:
	Meta_Accelerometer<SENSORS>::Base * _dev;
};

__END_SYS

#endif

