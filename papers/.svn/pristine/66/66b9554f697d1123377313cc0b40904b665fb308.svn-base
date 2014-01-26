Battery:     battery charge
I[modes]:    array of current for each mode
E[events]:   array of energy  for each event
C[counters]: array of integer for each event
time_begin:  timestamp
mode      :  operating mode


procedure energy_migration_update()
    now = SystemTime();
    //Equation 1
    Battery -= (now - time_begin) * I[mode];
    time_begin = now;
end procedure;

procedure energy_event_update(event)
    //Equation 2
    Battery -= E[event] * C[event];
    C[event] = 0;
end procedure;

procedure energy_update_total()
    for each device do
        //Equation 3
        for each event of device do
            energy_event_update(event);
        energy_migration_update();
    end for;

    Battery_Charge = from battery voltage model;
    //Equation 4
    Battery = max(Battery_Voltage, Battery);
end procedure;
