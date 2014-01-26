void sensor_main() {
    // ...
    sensor = new Temperature_Sensor();
    link = new Communicator(SENSOR_SINK);
    Imprecise_Thread sensing(&mandatory_sensor, 
	&optional_sensor, Criterion(150e3,170e3,INFINITE,0));
    Periodic_Thread  communication(&send_data, 
	Criterion(20e3,170e3,INFINITE,150e3), SUSPENDED);
}

int mandatory_sensor() {
    while(1) {
	// ... 
	temperature = sensor->sample();
	Imprecise_Thread::wait_next();
    }
}

int optional_sensor() {
    while(1) {
	// ... 
	for (int i = 1; i <= MAX_SAMPLES; i++){
	    sample = sensor->sample();
	    temperature = 
		(temperature * i + sample) / (i+1);
	}
	Optional_Thread::wait_next();
    }
}

int send_data() {
    while(1) {
	// ... 
	link->write(temperature);
	Periodic_Thread::wait_next();
    }
}

